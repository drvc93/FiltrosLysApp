package com.filtroslys.filtroslysapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import DataBase.ProdMantDataBase;
import Model.TMACliente;
import Model.UsuarioCompania;
import Tasks.GenRepDptoTecnicoTask;
import Tasks.GetCompUserComercialTask;
import Util.Constans;
import spencerstudios.com.fab_toast.FabToast;

public class DepTecnico extends AppCompatActivity {

    Spinner spCompania ,spConsulta ;
    SharedPreferences preferences;
    String codUser;
    WebView wbDeptec;
    EditText  txtCodCliente , txtRazonSocial ;
    String sFlagGen , sPathFile;
    String sRutaArchivo= "";
    Button btnGenRepDtoTec;
    private static final String TEL_PREFIX = "tel:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dep_tecnico);
        spCompania = findViewById(R.id.spCompaniaDTec);
        spConsulta = findViewById(R.id.spConsultaDTec);
        txtCodCliente = findViewById(R.id.txtClienteCodDT);
        wbDeptec = findViewById(R.id.webViweDepTec);
        btnGenRepDtoTec = findViewById(R.id.btnGenRepDepTec);
        txtRazonSocial = findViewById(R.id.txtRazonSocialDT);
        txtRazonSocial.setEnabled(false);
        preferences = PreferenceManager.getDefaultSharedPreferences(DepTecnico.this);
        codUser = preferences.getString("UserCod", null);
        LoadSpinerCompania();
        LoadSpinerConsulta();
        wbDeptec.getSettings().setJavaScriptEnabled(true);
        wbDeptec.getSettings().setSaveFormData(true);
        wbDeptec.getSettings().setBuiltInZoomControls(true);
        wbDeptec.setWebViewClient(new DepTecnico.MyWebViewClient());
        sFlagGen = "N";
        setTitle("Consulta Cliente");

        btnGenRepDtoTec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenerarReporteHTML();
            }
        });

        txtCodCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogBuscarCliente();
            }
        });

        txtCodCliente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ProdMantDataBase db =  new ProdMantDataBase(getApplicationContext());
                String codCli  = s.toString();
                String sCompania;
                sCompania = spCompania.getSelectedItem().toString();
                if (TextUtils.isEmpty(s.toString())){
                    return;
                }
                if (TextUtils.isEmpty(sCompania)) {
                    FabToast.makeText(DepTecnico.this, "Debe seleccionar una compania.", FabToast.LENGTH_LONG, FabToast.WARNING, FabToast.POSITION_DEFAULT).show();
                    return;
                }
                sCompania = sCompania.substring(0,sCompania.indexOf("|")-1).trim();

                if (db.ExisteCliente(sCompania,codCli)>0) {
                    txtRazonSocial.setText(db.ObtenerRazonSocialCliente(sCompania,codCli));
                }
                else {
                    if(! TextUtils.isEmpty(s.toString())  && s.toString().length()>3){
                        if (db.ExisteCliente(sCompania,codCli)==0) {
                            FabToast.makeText(DepTecnico.this, "Cliente no existe", FabToast.LENGTH_LONG, FabToast.WARNING, FabToast.POSITION_DEFAULT).show();
                            return;
                        }
                    }

                    txtRazonSocial.setText("");
                }
            }
        });
    }

    public  void  GenerarReporteHTML () {
        String sCompania, sCia, sCliente, sConsulta , sHTML="";
        GenRepDptoTecnicoTask genRepDptoTecnicoTask = new GenRepDptoTecnicoTask();
        AsyncTask<String, String, String> asyncTaskGenRep;
        sCompania = spCompania.getSelectedItem().toString();
        sCompania = sCompania.substring(0,sCompania.indexOf("|")-1).trim();
        sConsulta = spConsulta.getSelectedItem().toString().substring(0,2);
        sCliente = txtCodCliente.getText().toString();
        ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());


        if (TextUtils.isEmpty(sCliente)) {
            FabToast.makeText(DepTecnico.this, "Debe ingresar un codigo de cliente", FabToast.LENGTH_LONG, FabToast.WARNING, FabToast.POSITION_DEFAULT).show();
            return;
        }
        else {
            if (db.ExisteCliente(sCompania,sCliente)==0){
                FabToast.makeText(DepTecnico.this, "El codigo de cliente ingresado no existe.", FabToast.LENGTH_LONG, FabToast.WARNING, FabToast.POSITION_DEFAULT).show();
                return;
            }
        }

        if (!sConsulta.equals("") && !TextUtils.isEmpty(sConsulta) ) {

            try {
                asyncTaskGenRep = genRepDptoTecnicoTask.execute(sCompania, "LYS", sCliente, sConsulta);
                sHTML = asyncTaskGenRep.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (TextUtils.isEmpty(sHTML)) {
                FabToast.makeText(DepTecnico.this, "No se pudo generar reporte.", FabToast.LENGTH_LONG, FabToast.WARNING, FabToast.POSITION_DEFAULT).show();
                return;
            } else {
                Log.i("resultado html", sHTML);
                Log.i("resultado html", sHTML.substring(0,5).toUpperCase());
                if (sHTML.substring(0,5).toUpperCase().equals("ERROR")) {
                    FabToast.makeText(DepTecnico.this,  sHTML.substring(6,sHTML.length()-1), FabToast.LENGTH_LONG, FabToast.WARNING, FabToast.POSITION_DEFAULT).show();
                    return;
                }
                GenFileHtml(sHTML,sConsulta,sCliente);
                sFlagGen = "S";
            }
        }


    }

    public  void  GenFileHtml (String sHtml, String sConsulta, String sCliente){
        String nomFile = "";
        File folderHtml = new File(Environment.getExternalStorageDirectory() +File.separator+Constans.FolderDatosCli );
        if (!folderHtml.exists()) {
            folderHtml.mkdirs();
        }
        nomFile = "DatosCliente_"+sCliente+".html";

        String path = Environment.getExternalStorageDirectory() +File.separator+Constans.FolderDatosCli;
        File file = new File(path, nomFile);

        try {
            FileOutputStream out = new FileOutputStream(file);
            byte[] data = sHtml.getBytes();
            out.write(data);
            out.close();
            Log.e("Archivo guardado", "File Save : " + file.getPath());

            sPathFile = path+nomFile;
            sFlagGen = "S";
            String urlHtml =   "file:///"+path+"/"+nomFile;
            sRutaArchivo  = urlHtml;
            Log.i("URL HTML" , urlHtml);
            wbDeptec.loadUrl(urlHtml);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void LoadSpinerCompania() {
        ArrayList<UsuarioCompania> listComp = new ArrayList<>();
        AsyncTask<String,String,ArrayList<UsuarioCompania>> asyncTaskUserComp;
        GetCompUserComercialTask getCompUserComercialTask = new GetCompUserComercialTask();
        ArrayList<String> dataSpinerComp = new ArrayList<String>() ;
        try {
            asyncTaskUserComp = getCompUserComercialTask.execute(codUser);
            listComp =  asyncTaskUserComp.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listComp.size()>0){
            for (int i = 0; i <  listComp.size(); i++) {
                UsuarioCompania  u = listComp.get(i);
                dataSpinerComp.add(u.getC_compania() + " | "+u.getC_nombres());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, dataSpinerComp);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCompania.setAdapter(adapter);
        }
    }

    public  void LoadSpinerConsulta (){
        ArrayList<String> dataSpiner = new ArrayList<String>() ;
        ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
        dataSpiner = db.ListaOpcionesConsulta("DT", Constans.NroConpania);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, dataSpiner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spConsulta.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_shared, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.shared) {

            CompartirFile();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public  void  CompartirFile (){


        if (sPathFile.equals("N")){
            FabToast.makeText(DepTecnico.this, "No se generó ningun reporte.", FabToast.LENGTH_LONG, FabToast.WARNING, FabToast.POSITION_DEFAULT).show();
            return;
        }
        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        File fileWithinMyDir = new File(sPathFile);

        if(fileWithinMyDir.exists()) {
            intentShareFile.setType("application/pdf");
            intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse(sRutaArchivo));

            intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
                    "Compartir...");
            intentShareFile.putExtra(Intent.EXTRA_TEXT, "Compartir...");

            startActivity(Intent.createChooser(intentShareFile, "Compartir"));
        }

    }

    private class MyWebViewClient extends WebViewClient
    {
        @Override
        //show the web page in webview but not in web browser
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.startsWith(TEL_PREFIX)) {
                Log.i("URL WEB" , url);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }
            if ( url.startsWith("https://www.google.com"))
            {
                //String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
                return true;
            }
            Log.i("URL Otro" , url);
            return false;
        }
    }

    public ArrayAdapter<String> getAdapterBuscarCliente(String sFilterText){
        ProdMantDataBase db = new ProdMantDataBase(DepTecnico.this);
        ArrayList<TMACliente> clientes = db.GetSelectClientes(sFilterText);
        ArrayList<String> data = new ArrayList<>();
        if (clientes.size()>0){
            for (int i = 0; i < clientes.size() ; i++) {
                TMACliente c = clientes.get(i);
                data.add(String.valueOf(c.n_cliente)+ " | " + c.getC_razonsocial());

            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                data );
        Log.i("size adapter  " , String.valueOf(data.size()));
        return  arrayAdapter;
    }

    public  void  AlertDialogBuscarCliente () {
        final Dialog dialog  = new Dialog(DepTecnico.this);
        dialog.setContentView(R.layout.dialog_buscar_cliente);
        dialog.setTitle("Buscar Cliente");
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button btnSearch = dialog.findViewById(R.id.btnSearchCliente);
        Button btnCancel = dialog.findViewById(R.id.btnCancelSearchCli);
        final EditText txtSearchCliente = dialog.findViewById(R.id.txtSearchCliente);
        final ListView lvBuscarClientes  = dialog.findViewById(R.id.lvSearchCliente);
        lvBuscarClientes.setAdapter(getAdapterBuscarCliente("%"));

        lvBuscarClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sCodCli = lvBuscarClientes.getItemAtPosition(position).toString();
                sCodCli = sCodCli.substring(0,sCodCli.indexOf("|")-1).trim();
                txtCodCliente.setText(sCodCli);
                //txtClienteCodNom.setText(lvBuscarClientes.getItemAtPosition(position).toString());
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvBuscarClientes.setAdapter(getAdapterBuscarCliente(txtSearchCliente.getText().toString().toUpperCase()));
            }
        });

        dialog.show();
    }

}
