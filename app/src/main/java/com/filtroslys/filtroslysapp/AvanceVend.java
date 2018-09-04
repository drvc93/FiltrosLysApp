package com.filtroslys.filtroslysapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.github.florent37.expansionpanel.ExpansionLayout;
import com.rackspira.kristiawan.rackmonthpicker.RackMonthPicker;
import com.rackspira.kristiawan.rackmonthpicker.listener.DateMonthDialogListener;
import com.rackspira.kristiawan.rackmonthpicker.listener.OnCancelMonthDialogListener;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import DataBase.ProdMantDataBase;
import Model.UnidadNegocio;
import Model.UsuarioCompania;
import Model.UsuarioPromotor;
import Tasks.GenRepAvancePromotTask;
import Tasks.GetCompUserComercialTask;
import Tasks.GetListaUsuVendTask;
import Util.Constans;
import spencerstudios.com.fab_toast.FabToast;

public class AvanceVend extends AppCompatActivity {

    Spinner spCompania,spMoneda,spUnidadNeg,spPromotor,SpConsulta;
    int indexUndDef;
    EditText txtPeriodo;
    Button btnGenReport;
    private ProgressDialog pd = null;
   ExpansionLayout expansionLayout ;
    SharedPreferences preferences;
    TextView  yearMonth ;
    WebView webViewRep;
    String codUser;
    String sFlagGen , sPathFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avance_vend);
        preferences = PreferenceManager.getDefaultSharedPreferences(AvanceVend.this);
        this.pd = ProgressDialog.show(this, "Espere..", "Obteniendo Información...", true, false);
        codUser = preferences.getString("UserCod", null);
        spCompania = findViewById(R.id.spCompaniaAVC);
        spMoneda = findViewById(R.id.spMonedaAVC);
        spUnidadNeg = findViewById(R.id.spUndNegoAVC);
        spPromotor = findViewById(R.id.spVendedorAVC);
        SpConsulta = findViewById(R.id.spConsultaAVC);
        txtPeriodo = findViewById(R.id.txtPeriodoAVC);
        expansionLayout = findViewById(R.id.expansionLayout);
        btnGenReport= findViewById(R.id.btnGenRepAVC);
        expansionLayout.expand(true);
        webViewRep = findViewById(R.id.webRep);
        sPathFile = "" ;
        sFlagGen = "N";
        setTitle("Reporte Seguimiento Promotor");



        new GetUnidadNegocioTask().execute(Constans.NroConpania);
        LoadSpinerCompania();
        LoadSpinerMoneda();
        CargarPeriodoActual();
        LoadSpinerPromotorVend();
        LoadSpinerConsulta();

        webViewRep.getSettings().setJavaScriptEnabled(true);
        webViewRep.getSettings().setSaveFormData(true);
        webViewRep.getSettings().setBuiltInZoomControls(true);
        webViewRep.setWebViewClient(new MyWebViewClient());



        txtPeriodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPeriodoPicker();
            }
        });
        btnGenReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenerarReporteHTML();
            }
        });

        spUnidadNeg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LoadSpinerPromotorVend();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private class MyWebViewClient extends WebViewClient
    {
        @Override
        //show the web page in webview but not in web browser
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl (url);
            return true;
        }
    }

    public  void  GenerarReporteHTML (){

        if (spPromotor.getAdapter()==null || spPromotor.getSelectedItemPosition()<0){
            FabToast.makeText(AvanceVend.this, "Debe seleccionar un promotor/vandedor.", FabToast.LENGTH_LONG, FabToast.WARNING, FabToast.POSITION_DEFAULT).show();
            return;
        }

        String sCompania, sUndNego, sUsuario ,sPeriodo , sMoneda,sConsulta, sHTML;
        sCompania = spCompania.getSelectedItem().toString();
        sCompania = sCompania.substring(0,sCompania.indexOf("|")-1).trim();
        sUndNego = spUnidadNeg.getSelectedItem().toString();
        sUndNego = sUndNego.substring(sUndNego.indexOf("|")+1,sUndNego.length()).trim();
        sUsuario= spPromotor.getSelectedItem().toString();
        sUsuario =  sUsuario.substring(sUsuario.indexOf("|")+1,sUsuario.length()).trim();


        sPeriodo =txtPeriodo.getText().toString().substring(0,4) + txtPeriodo.getText().toString().substring(5,7);
        sMoneda = spMoneda.getSelectedItem().toString().substring(0,1);
        sMoneda = sMoneda.equals("S") ? "L":"E";
        sConsulta = SpConsulta.getSelectedItem().toString().substring(0,2);

        if (!sConsulta.equals("") && !TextUtils.isEmpty(sConsulta) ) {

            GenRepAvancePromotTask genRepAvancePromotTask = new GenRepAvancePromotTask();
            AsyncTask<String, String, String> asyncTaskGenRep;
            try {
                asyncTaskGenRep = genRepAvancePromotTask.execute(sCompania, "%", sUndNego, "%", sPeriodo, sMoneda, sUsuario, sConsulta);
                sHTML = asyncTaskGenRep.get();
                if (TextUtils.isEmpty(sHTML)) {
                    FabToast.makeText(AvanceVend.this, "No se pudo generar reporte.", FabToast.LENGTH_LONG, FabToast.WARNING, FabToast.POSITION_DEFAULT).show();
                    return;
                } else {
                    Log.i("resultado html", sHTML);
                    Log.i("resultado html", sHTML.substring(0,5).toUpperCase());
                    if (sHTML.substring(0,5).toUpperCase().equals("ERROR")) {
                        FabToast.makeText(AvanceVend.this,  sHTML.substring(6,sHTML.length()-1), FabToast.LENGTH_LONG, FabToast.WARNING, FabToast.POSITION_DEFAULT).show();
                        return;
                    }
                    GenFileHtml(sHTML,sConsulta);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public  void  GenFileHtml (String sHtml, String sConsulta){
        ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
        String nomFile = "";

        if (db.VerifiExportable("PR",sConsulta).equals("N")){
            webViewRep.loadDataWithBaseURL("", sHtml, "text/html", "utf-8", "");
            return;
        }

        File folderHtml = new File(Environment.getExternalStorageDirectory() +File.separator+Constans.FolderRepVent );
        if (!folderHtml.exists()) {
            folderHtml.mkdirs();
        }
        nomFile = "AvancePromotor_"+sConsulta+".html";

        String path = Environment.getExternalStorageDirectory() +File.separator+Constans.FolderRepVent;
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
         Log.i("URL HTML" , urlHtml);
         webViewRep.loadUrl(urlHtml);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void ShowPeriodoPicker(){
        Locale spanish = new Locale("es", "ES");
        new RackMonthPicker(this)

                .setLocale(spanish)
                .setColorTheme(R.color.colorPrimary)
                .setPositiveButton(new DateMonthDialogListener() {
                    @Override
                    public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {

                        String sMes = String.format("%02d", month);
                        String  anio = String.valueOf(year);
                        txtPeriodo.setText(anio+"-"+sMes);
                        LoadSpinerPromotorVend ();
                    }
                })
                .setNegativeButton(new OnCancelMonthDialogListener() {
                    @Override
                    public void onCancel(AlertDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public  void  LoadSpinerPromotorVend ()  {
        String sUndNego = "";
        GetListaUsuVendTask getListaUsuVendTask = new GetListaUsuVendTask();
        AsyncTask<String,String,ArrayList<UsuarioPromotor>> asyncTask ;
        ArrayList<UsuarioPromotor> LstData =   null ;
        ArrayList<String> dataCCosto = new ArrayList<String>();
        if (spCompania!= null && spCompania.getCount()>0) {
            String sTempComp = spCompania.getSelectedItem().toString();
            sTempComp = sTempComp.substring(0,sTempComp.indexOf("|")-1 );
            sTempComp = sTempComp.trim();
            if (spUnidadNeg.getSelectedItemPosition()<0){
                sUndNego = "REP";
            }
            else {
                sUndNego = spUnidadNeg.getSelectedItem().toString();
                sUndNego = TextUtils.isEmpty(sUndNego) ? "REP" :sUndNego.substring(sUndNego.indexOf("|")+1,sUndNego.length()).trim();
            }


            String sPeriodo = txtPeriodo.getText().toString().substring(0,4) + txtPeriodo.getText().toString().substring(5,7);
            Log.i("Periodo Vend " , sPeriodo);
            try {
                asyncTask = getListaUsuVendTask.execute(sTempComp,sPeriodo,codUser,sUndNego);
                LstData = asyncTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (LstData != null  && LstData.size()>0) {

                for (int i = 0; i < LstData.size(); i++) {

                    dataCCosto.add(LstData.get(i).getC_nombusuario() + " | " + LstData.get(i).getC_usuario());

                }
                ArrayAdapter<String> adapterCosto = new ArrayAdapter<String>(AvanceVend.this, android.R.layout.simple_spinner_dropdown_item, dataCCosto);
                adapterCosto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spPromotor.setAdapter(adapterCosto);
            }

            else {
                FabToast.makeText(AvanceVend.this, "No se encontro vendedores registrados en este periodo", FabToast.LENGTH_LONG, FabToast.WARNING, FabToast.POSITION_DEFAULT).show();
                spPromotor.setAdapter(null);
                return;
            }
        }
    }

    public  void  LoadSpinerMoneda(){
        ArrayList<String> dataSpinerComp = new ArrayList<String>() ;
        dataSpinerComp.add("Dolares");
        dataSpinerComp.add("Soles");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, dataSpinerComp);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMoneda.setAdapter(adapter);
    }

    public  void LoadSpinerConsulta (){
        ArrayList<String> dataSpiner = new ArrayList<String>() ;
        ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
        dataSpiner = db.ListaOpcionesConsulta("PR",Constans.NroConpania);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, dataSpiner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpConsulta.setAdapter(adapter);
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

    public  void  CargarPeriodoActual(){
      int year =   Calendar.getInstance().get(Calendar.YEAR);
      int mes = Calendar.getInstance().get(Calendar.MONTH)+1;
      String sMes = String.format("%02d", mes);
      String  anio = String.valueOf(year);
      txtPeriodo.setText(anio+"-"+sMes);
    }

    public  void  LoadSpuUndNego (ArrayList<String> data){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUnidadNeg.setAdapter(adapter);

        spUnidadNeg.setSelection(indexUndDef);
    }


    private class GetUnidadNegocioTask extends  AsyncTask<String,Void,ArrayList<UnidadNegocio>>{
        ArrayList<UnidadNegocio> result;



        @Override
        protected ArrayList<UnidadNegocio> doInBackground(String... strings) {
            ArrayList<UnidadNegocio> LstData = new ArrayList<>();
            final String NAMESPACE = Constans.NameSpaceWS;
            final String URL = Constans.UrlServer;
            final String METHOD_NAME = "GetUnidadNegUsuario";
            final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("compania", strings[0]);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);
            transporte.debug = true;

            try {
                transporte.call(SOAP_ACTION, envelope);
                SoapObject resSoap = (SoapObject) envelope.bodyIn;

                int num_projects = resSoap.getPropertyCount();
                Log.i("result get lista unidad negoc ", resSoap.toString());
                for (int i = 0; i < num_projects; i++) {
                    SoapObject ic = (SoapObject) resSoap.getProperty(i);
                    UnidadNegocio oEnt = new UnidadNegocio();
                    oEnt.setC_compania(ic.getPrimitivePropertyAsString("c_compania"));
                    oEnt.setC_unidadnegocio(  ic.getPrimitivePropertyAsString("c_unidadnegocio"));
                    oEnt.setC_descripcion(  ic.getPrimitivePropertyAsString("c_descripcion"));
                    oEnt.setC_estado(ic.getPrimitivePropertyAsString("c_estado"));
                    LstData.add(oEnt);
                }
                if (resSoap.getPropertyCount() > 0) {
                    result = LstData;
                }

            } catch (Exception e) {
                Log.i("error task lista unidad negoc  ---", e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<UnidadNegocio> unidadNegocios) {
            super.onPostExecute(unidadNegocios);
            if (unidadNegocios!=null && unidadNegocios.size()>0){
                ArrayList<String> dataUndNeg = new ArrayList<>() ;
                for (int i = 0; i < unidadNegocios.size() ; i++) {
                    UnidadNegocio un = unidadNegocios.get(i);
                    dataUndNeg.add(un.getC_descripcion().trim()+"|" + un.getC_unidadnegocio() );
                    if (un.getC_unidadnegocio().equals("REP")){
                        indexUndDef = i;
                    }
                }

                LoadSpuUndNego(dataUndNeg);

            }

            if (AvanceVend.this.pd != null) {
                AvanceVend.this.pd.dismiss();
            }
        }
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

        String sConsulta = "";
        sConsulta = SpConsulta.getSelectedItem().toString().substring(0,2);
        ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
        if(db.VerifiExportable("PR",sConsulta).equals("N")) {
            FabToast.makeText(AvanceVend.this, "No se puede exportar la consulta seleccionada.", FabToast.LENGTH_LONG, FabToast.WARNING, FabToast.POSITION_DEFAULT).show();
            return;
        }
        if (sPathFile.equals("N")){
            FabToast.makeText(AvanceVend.this, "No se generó ningun reporte.", FabToast.LENGTH_LONG, FabToast.WARNING, FabToast.POSITION_DEFAULT).show();
            return;
        }
        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        File fileWithinMyDir = new File(sPathFile);

        if(fileWithinMyDir.exists()) {
            intentShareFile.setType("application/pdf");
            intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+sPathFile));

            intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
                    "Compartir...");
            intentShareFile.putExtra(Intent.EXTRA_TEXT, "Compartir...");

            startActivity(Intent.createChooser(intentShareFile, "Compartir"));
        }

    }


}
