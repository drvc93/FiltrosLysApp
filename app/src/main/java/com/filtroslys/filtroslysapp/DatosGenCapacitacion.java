package com.filtroslys.filtroslysapp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.concurrent.ExecutionException;
import DataBase.ProdMantDataBase;
import Model.CapacitacionCliente;
import Model.DocsCapacitacion;
import Model.TMACliente;
import Model.TMADireccionCli;
import Model.TMATemaCapacitacion;
import Tasks.GetCorrelativoCapacitacionTask;
import Tasks.GetFotoRG;
import Tasks.GetListaDatosFotosCPTask;
import Tasks.GuardaLocalFotoCPTask;
import Tasks.GuardarFotoRGTask;
import Tasks.InsertCapacitacionClienteTask;
import Tasks.InsertDocsCapacitacionTask;
import Tasks.TransferirCapacitacionClienteTask;
import Util.Constans;
import libs.mjn.prettydialog.PrettyDialog;
import spencerstudios.com.fab_toast.FabToast;

public class DatosGenCapacitacion extends AppCompatActivity {
    EditText txtNroCapacitacion,txtFechaRegCP,txtClienteCodNom,txtObservacionCP;
    EditText txtDetalleTema,txtFechaProbCP,txtHoraProbCP,txtNumeroPersonaCP;
    EditText txtRevisadoPorCP,txtFechaRevisadoCP,txtObservacionRevCP;
    EditText txtAccionRevisionCP,txtCerradoPorCP,txtFechaCierreCP,txtObservacionCierreCP;
    Spinner spTemaGeneralCP,spLugarCP,spDireccionCli;
    CheckBox chkFinRevisionCP;
    TextView lblAvance1,lblAvance2;
    int PantallaReal;
    ImageView btnBuscarCliente;
    CapacitacionCliente objCapacitacion;
    Button btnSiguiente,btnCancelar;
    LinearLayout ly_Datos_Generales,ly_Datos_Otros;
    int nIndicadorLayout,nDireccionGen=0;
    String codUser,sSelFechaProb,sSelHoraProb,sFechaSelec,sOperacion="VER";
    SharedPreferences preferences;
    String sDireccionReg="";
    ListView lvFotosSG;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    DocsCapacitacionAdapter docsAdapdater = null ;
    ArrayList<DocsCapacitacion> listaDocsFotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_gen_capacitacion);
        setTitle("Datos Generales");
        preferences = PreferenceManager.getDefaultSharedPreferences(DatosGenCapacitacion.this);

        btnCancelar = findViewById(R.id.btnCancelRegCP);
        btnSiguiente = findViewById(R.id.btnSigDatosCP);
        ly_Datos_Generales = findViewById(R.id.layGeneralCP);
        ly_Datos_Otros = findViewById(R.id.layOtrosCP);
        txtNroCapacitacion = findViewById(R.id.txtNroCapacitacion);
        txtFechaRegCP = findViewById(R.id.txtFechaRegCP);
        txtClienteCodNom =  findViewById(R.id.txtBSClienteCP);
        btnBuscarCliente = findViewById(R.id.btnBuscarClienteCP);
        spTemaGeneralCP = findViewById(R.id.spTemaGeneralCP);
        txtDetalleTema = findViewById(R.id.txtDetalleTema);
        txtObservacionCP = findViewById(R.id.txtObservacionCP);
        spLugarCP = findViewById(R.id.spLugarCP);
        spDireccionCli = findViewById(R.id.spDireccionCli);
        txtFechaProbCP = findViewById(R.id.txtFechaProbCP);
        txtHoraProbCP = findViewById(R.id.txtHoraProbCP);
        txtNumeroPersonaCP = findViewById(R.id.txtNumeroPersonaCP);
        chkFinRevisionCP = findViewById(R.id.chkFinRevisionCP);
        txtRevisadoPorCP = findViewById(R.id.txtRevisadoPorCP);
        txtFechaRevisadoCP = findViewById(R.id.txtFechaRevisadoCP);
        txtObservacionRevCP = findViewById(R.id.txtObservacionRevCP);
        txtAccionRevisionCP = findViewById(R.id.txtAccionRevisionCP);
        txtCerradoPorCP = findViewById(R.id.txtCerradoPorCP);
        txtFechaCierreCP =  findViewById(R.id.txtFechaCierreCP);
        txtObservacionCierreCP  = findViewById(R.id.txtObservacionCierreCP);
        lblAvance1 = findViewById(R.id.lblAvance1CP);
        lblAvance2 = findViewById(R.id.lblAvance2CP);;
        txtClienteCodNom.setEnabled(false);
        txtNumeroPersonaCP.setText("0");
        
        PantallaReal=1;
        nIndicadorLayout = 1;
        ConfigurarPantalla(nIndicadorLayout);
        codUser = preferences.getString("UserCod", null);
        sOperacion = getIntent().getStringExtra("Operacion");

        LoadSpinerTemaCap();
        LoadSpinerLugar();
        LoadSpinerDireccionCli("","0");

        if (sOperacion.equals("NEW")){
            objCapacitacion  = new CapacitacionCliente();
            if (getIntent().getStringExtra("Cliente")!=null){
                 txtClienteCodNom.setText( getIntent().getStringExtra("Cliente"));
            }
        }else if(sOperacion.equals("MOD") || sOperacion.equals("VER")){
            Gson gson = new Gson();
            String rgAsString = getIntent().getStringExtra("ObjectCP");
            objCapacitacion  = gson.fromJson(rgAsString,CapacitacionCliente.class);
            docsAdapdater = FillAdaptadorDocs(sOperacion,objCapacitacion.getC_compania(), String.valueOf( objCapacitacion.getN_correlativo()));
            SetControlsValues(objCapacitacion);
        }

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nIndicadorLayout>1){
                    nIndicadorLayout= nIndicadorLayout -1;
                    ConfigurarPantalla(nIndicadorLayout);
                }
                else {
                    DatosGenCapacitacion.super.onBackPressed();
                }
            }
        });
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sOperacion.equals("VER")){
                    if (nIndicadorLayout<2) {
                        nIndicadorLayout = nIndicadorLayout +1 ;
                        ConfigurarPantalla(nIndicadorLayout);
                    }
                    else if (nIndicadorLayout==2){
                        CerrarVisor();
                    }
                }else{
                    VaLidacionFinal();
                }
            }
        });

        btnBuscarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogBuscarCliente();
            }
        });

        txtFechaProbCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectFechaProb(txtFechaProbCP);
            }
        });
        txtFechaRegCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelecFecha(txtFechaRegCP);
            }
        });
        txtHoraProbCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectHora();
            }
        });

        spLugarCP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spLugarCP.getSelectedItemPosition()==3){
                    AlertDireccionOtros();
                    LoadSpinerDireccionCli("", "0");
                    return;
                }
                if (position!=2){

                        LoadSpinerDireccionCli("", "0");
                        return;
                }
                if (!txtClienteCodNom.getText().toString().isEmpty()) {
                    String sClient = txtClienteCodNom.getText().toString().trim();
                    sClient = sClient.substring(0, sClient.indexOf("|") - 1);
                    LoadSpinerDireccionCli(Constans.NroConpania,sClient);
                }
                else {

                    FabToast.makeText(DatosGenCapacitacion.this, "Debe seleccionar un cliente primero.", FabToast.LENGTH_LONG, FabToast.WARNING, FabToast.POSITION_DEFAULT).show();
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (nIndicadorLayout>1) {
            nIndicadorLayout = nIndicadorLayout-1;
            ConfigurarPantalla(nIndicadorLayout);
        }
        else {
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_fotos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.AddFotos) {
            AlertAgregarFotos();
        }
        return super.onOptionsItemSelected(item);
    }

    public  DocsCapacitacionAdapter  FillAdaptadorDocs(String sTipoOperacion,String sComp,String sCorrelativo){
        Log.i("operaciones  >>" , sTipoOperacion +" | " + sComp + " | "+ sCorrelativo );
        ArrayList<DocsCapacitacion> listaFotos =  new ArrayList<>();
        if (sTipoOperacion.equals("VER")){
            AsyncTask<String,String,ArrayList<DocsCapacitacion>> asyncTaskListaFotosCP;
            GetListaDatosFotosCPTask getListaDatosFotosQJTask = new GetListaDatosFotosCPTask();
            try {
                asyncTaskListaFotosCP = getListaDatosFotosQJTask.execute(sComp,sCorrelativo);
                listaFotos  = asyncTaskListaFotosCP.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (listaFotos!=null && listaFotos.size()>0){
                for (int i = 0; i <  listaFotos.size(); i++) {
                    RecuperandoImagen(listaFotos.get(i).getC_ruta_archivo());
                }
            }
            //Log.i("valor adaptador" , String.valueOf( listaFotos.size()));
        }else  if (sTipoOperacion.equals("MOD")){

            ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
            listaFotos = db.GetListDocsCapacitacion(sCorrelativo);

        }

        if (listaFotos == null){
            listaFotos =  new ArrayList<>();
        }


        docsAdapdater = new DocsCapacitacionAdapter(DatosGenCapacitacion.this, R.layout.item_fotos_reclamogar, listaFotos);
//
        return  docsAdapdater;

    }

    public void RecuperandoImagen(String filename) {
        //File filefoto = null;
        Bitmap bitmap = null;

        File fileexist = new File(Environment.getExternalStorageDirectory() +File.separator+Constans.Carpeta_foto_CP + filename);
        if (fileexist.exists()){
            return;
        }

        AsyncTask<String, String, byte[]> asynckGetFoto;
        GetFotoRG getFotoTask = new GetFotoRG();
        byte[] bytes = null;
        try {
            asynckGetFoto = getFotoTask.execute(filename, "CP");
            bytes = (byte[]) asynckGetFoto.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        AsyncTask<byte[], String, String> asyncSaveImglocal;
        GuardaLocalFotoCPTask guardarImgLocalTask = new GuardaLocalFotoCPTask(filename, bytes);

        try {
            asyncSaveImglocal = guardarImgLocalTask.execute(bytes);
            String resSaveImg = (String) asyncSaveImglocal.get();
            Log.i("result save imag local SG => ", resSaveImg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String filePath2 = Environment.getExternalStorageDirectory() +File.separator+Constans.Carpeta_foto_CP + filename;
        bitmap = BitmapFactory.decodeFile(filePath2);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            String codigo = CodigoFoto();
            GuardarFoto(imageBitmap, codigo, "Local");
            SetAdapterDocsFoto(codigo);
            Log.i("paso ", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
    }

    public void AlertAgregarFotos(){
        final Dialog dialog  = new Dialog(DatosGenCapacitacion.this);
        dialog.setContentView(R.layout.dialog_list_fotos_rg);
        dialog.setTitle("Buscar Cliente");
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button btnAddFoto = dialog.findViewById(R.id.btnAddFotoRG);
        Button btnEliminar = dialog.findViewById(R.id.btnEliminarFotoRG);
        Button btnAceptarFotos = dialog.findViewById(R.id.btnAceptarFotoRG);
        FrameLayout frameLayout = dialog.findViewById(R.id.lyFramViewFoto);
        Button btnCerrarFoto = dialog.findViewById(R.id.btnCloseViewFoto);
        ImageView imageView = dialog.findViewById(R.id.ImageViewFoto);
        lvFotosSG  = dialog.findViewById(R.id.lvFotosGuardadasRG);
        lvFotosSG.setItemsCanFocus(true);
        if (docsAdapdater != null){
            if (docsAdapdater.getCount()>0) {
                lvFotosSG.setAdapter(docsAdapdater);
            }
        }

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (docsAdapdater!=null && docsAdapdater.getCount()>0){
                    docsAdapdater.data.remove(docsAdapdater.data.size()-1);
                    docsAdapdater.notifyDataSetChanged();
                }
            }
        });

        //lvBuscarClientes.setAdapter(getAdapterBuscarCliente("%"));
        btnAddFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                //openBackCamera();
            }
        });

        btnAceptarFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public String CodigoFoto() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        String idImei = telephonyManager.getDeviceId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String sCodigoFoto =   idImei +"_"+  sdf.format(new Date());
        return sCodigoFoto ;
    }

    public  void SetAdapterDocsFoto(String sCodFoto) {
        DocsCapacitacion d = new DocsCapacitacion();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sUltFecha = sdf.format(new Date());
        d.setD_ultimafechamodificacion(sUltFecha);
        d.setC_ultimousuario(codUser);
        d.setC_nombre_archivo(sCodFoto);
        d.setC_ruta_archivo(sCodFoto + ".jpg");
        d.setC_compania(Constans.NroConpania);

        if (docsAdapdater == null) {

            docsAdapdater = new DocsCapacitacionAdapter(DatosGenCapacitacion.this, R.layout.item_fotos_reclamogar, listaDocsFotos);
            lvFotosSG.setAdapter(docsAdapdater);

        }

        docsAdapdater.add(d);
        docsAdapdater.notifyDataSetChanged();
    }

    public  void GuardarFoto(Bitmap bmp , String sCodFoto, String sTipoSincron){

        File DirExist =  new File(Environment.getExternalStorageDirectory(),  Constans.Carpeta_foto_CP);
        if (!DirExist.exists()){
            DirExist.mkdir();
        }


        String filename =   Constans.Carpeta_foto_CP+sCodFoto+".jpg";
        File sd = Environment.getExternalStorageDirectory();

        File dest = new File(sd, filename);
        Log.i("Destino",dest.getPath());
        Bitmap bitmap = bmp ;
        try {
            FileOutputStream out = new FileOutputStream(dest);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void VaLidacionFinal(){
        String sCliente,sTemaGen="",sDescTema="",sLug="",sDireccion="",sFechaReg="",sFecProb="",sObs="";
        String sHoraProb="",sPersona="0";
        String sDireccNum="0";

        sCliente = txtClienteCodNom.getText().toString().trim();
        sCliente = sCliente.indexOf("|")>0? sCliente.substring(0,sCliente.indexOf("|")-1).trim():"0";
        sDescTema = txtDetalleTema.getText().toString().trim();
        sDireccion = spDireccionCli.getSelectedItem().toString();
        sDireccion = sDireccion.substring(0,sDireccion.indexOf("|")-1);
        sPersona = txtNumeroPersonaCP.getText().toString().trim();
        sDireccNum = spDireccionCli.getSelectedItem().toString();
        sFecProb = sSelFechaProb;
        sHoraProb = sSelHoraProb;
        sObs = txtObservacionCP.getText().toString().trim();

        if (spTemaGeneralCP.getSelectedItemPosition()>0){
            sTemaGen = spTemaGeneralCP.getSelectedItem().toString();
            sTemaGen = sTemaGen.substring(0,sTemaGen.indexOf("|")-1).trim();
        }

        if (spLugarCP.getSelectedItemPosition()<=0){
            ShowMessageWindow("Debe seleccionar un lugar de capacitacion.");
            return;
        }

        if (sCliente.isEmpty() || sCliente.equals("0")){
            ShowMessageWindow("Debe seleccionar un cliente.");
            return;
        }

        if (sTemaGen.isEmpty()){
            ShowMessageWindow("Debe seleccionar Tema General.");
            return;
        }

        if (sDescTema.isEmpty()){
            ShowMessageWindow("Debe ingresar Descripcion del Tema.");
            return;
        }

        if (sFecProb.isEmpty()){
            ShowMessageWindow("Debe registrar la Fecha probable.");
            return;
        }

        if (spLugarCP.getSelectedItemPosition()==2 && spDireccionCli.getSelectedItemPosition()<=0){
            ShowMessageWindow("Debe seleccionar una direccion del cliente.");
            return;
        }
        if (spLugarCP.getSelectedItemPosition()==3 && TextUtils.isEmpty(sDireccionReg)){
            ShowMessageWindow("Debe escribir la direccion de capacitacion.");
            return;
        }

        if (sHoraProb.isEmpty()){
            ShowMessageWindow("Debe registrar la Hora probable.");
            return;
        }

        if (sPersona.isEmpty() || sPersona.equals("0")){
            ShowMessageWindow("Debe Ingresar Numero de Personas.");
            return;
        }

        DateFormat DateFrm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sFechaReg = DateFrm.format(Calendar.getInstance().getTime());

        sLug = spLugarCP.getSelectedItem().toString();
        sLug = sLug.substring(0,sLug.indexOf("|")-1).trim();
        objCapacitacion.setC_compania(Constans.NroConpania);
        objCapacitacion.setN_cliente(Integer.valueOf(sCliente));
        objCapacitacion.setD_fecha(sFechaReg);
        objCapacitacion.setC_temacapacitacion(sTemaGen);
        objCapacitacion.setC_descripciontema(sDescTema);
        objCapacitacion.setC_observacion(sObs);
        objCapacitacion.setC_lugar(sLug);
        objCapacitacion.setC_direccionreg(sDireccion);
        if (spLugarCP.getSelectedItemPosition()==2){
            objCapacitacion.setN_direccioncli(Integer.parseInt(sDireccion));
        }
        else {
            objCapacitacion.setN_direccioncli(0);
        }
        objCapacitacion.setN_personas(Integer.parseInt(sPersona));
        objCapacitacion.setC_usuarioreg(codUser);
        objCapacitacion.setD_fechareg(sFechaReg);
        objCapacitacion.setD_fechaprob(sSelFechaProb);
        objCapacitacion.setC_estado("PE");
        objCapacitacion.setD_horaprob(sSelHoraProb);
        if (spLugarCP.getSelectedItemPosition()==3){
            objCapacitacion.setC_direccionreg(sDireccionReg);
        } else if (spLugarCP.getSelectedItemPosition()==2){
            objCapacitacion.setC_direccionreg(spDireccionCli.getSelectedItem().toString().substring(spDireccionCli.getSelectedItem().toString().indexOf("|")+1));
        }
        else {
            objCapacitacion.setC_direccionreg("");
        }

        SeleccionarFormaGuardado();
    }

    public void CerrarVisor(){

    }

    public void SeleccionarFormaGuardado(){
        final CharSequence[] items = {"Solo guardar en movil","Enviar a Servidor","Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(DatosGenCapacitacion.this);
        builder.setTitle("Seleccionar opcion : ");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
               switch (item) {
                   case  0 :
                       GuardarCapacitacion("Local");
                       break;
                   case 1:
                       GuardarCapacitacion("Servidor");
                       break;
                   case 2:
                       dialog.dismiss();
               }
            }
        }).show();
    }

    public void ConfigurarPantalla (int nIndicadorPantlla){
        ly_Datos_Generales.setVisibility(View.GONE);
        ly_Datos_Otros.setVisibility(View.GONE);

        setAvance(nIndicadorPantlla);

        PantallaReal=nIndicadorPantlla;
        switch (nIndicadorPantlla){
            case 1:
                btnCancelar.setText("Cancelar");
                btnSiguiente.setText("Siguiente");
                ly_Datos_Generales.setVisibility(View.VISIBLE);
                break;
            case 2:
                btnCancelar.setText("Volver");
                btnSiguiente.setText("Finalizar");
                ly_Datos_Otros.setVisibility(View.VISIBLE);
                break;
        }
    }
    public  void ActualizarTablaLocalFotos(ArrayList<DocsCapacitacion> listFotos){

        ProdMantDataBase  db = new ProdMantDataBase(getApplicationContext());
        for (int i = 0; i < listFotos.size() ; i++) {
            DocsCapacitacion dc = listFotos.get(i);
            dc.setN_linea(i+1);
            long rowdoc =  db.InsertDocsCapacitacion(dc);
            Log.i("id row doc sg" ,String.valueOf(rowdoc));
        }

    }

    public void GuardarCapacitacion(String sTipoGuardado){
        ProdMantDataBase db = new ProdMantDataBase(DatosGenCapacitacion.this);
        long rowid =0 ;
        objCapacitacion.setC_enviado(sTipoGuardado.equals("Local")?"N":"S");
        Log.i("Lugar Capa sv > ", objCapacitacion.getC_lugar());

        if (sOperacion.equals("NEW")) {
            rowid  =   db.InsertCapacitacionCliente(objCapacitacion);
        }else if (sOperacion.equals("MOD")){
            rowid   = db.UpdateCapacitacion(objCapacitacion);
            rowid  = objCapacitacion.getN_correlativo();
        }
        if (rowid>0){
            Log.i("row id capacitacion ", String.valueOf(rowid));
            if (docsAdapdater!=null) {
                docsAdapdater.SetNumero((int) rowid);
                db.DeleteDocsCapacitacion(String.valueOf(objCapacitacion.getN_correlativo()));
                Log.i("metodo delete cap  correlativo >" , String.valueOf(objCapacitacion.getN_correlativo()) );
                ActualizarTablaLocalFotos(docsAdapdater.AllItems());
            }
            if (sTipoGuardado.equals("Local")) {
                FabToast.makeText(DatosGenCapacitacion.this, "Se guardo correctamente la informacion", FabToast.LENGTH_LONG, FabToast.SUCCESS, FabToast.POSITION_DEFAULT).show();
                super.onBackPressed();
            }
        }
        if (sTipoGuardado.equals("Servidor") && rowid>0) {
            String sCorrelativoCP = "";
            AsyncTask<String, String, String> asyncTaskCorrelativoCP;
            GetCorrelativoCapacitacionTask getCorrelativoCapTask = new GetCorrelativoCapacitacionTask();
            try {
                asyncTaskCorrelativoCP = getCorrelativoCapTask.execute();
                sCorrelativoCP = asyncTaskCorrelativoCP.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            InsertCapacitacionClienteTask insertCapacitacionClienteTask = new InsertCapacitacionClienteTask();
            AsyncTask<String,String,String> asyncTaskInsertCP;
            sCorrelativoCP = String.valueOf(Integer.valueOf(sCorrelativoCP)+1);
            String InserCP="";
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String fecha = df.format(Calendar.getInstance().getTime());
            objCapacitacion.setD_fecha(fecha);
            objCapacitacion.setD_fechareg(fecha);
            try {
                asyncTaskInsertCP = insertCapacitacionClienteTask.execute(Constans.NroConpania,sCorrelativoCP,"accion",codUser,
                        objCapacitacion.getD_fecha(),String.valueOf(objCapacitacion.getN_cliente()),
                        String.valueOf(objCapacitacion.getN_personas()),
                        objCapacitacion.getD_fechaprob(),objCapacitacion.getD_horaprob(),
                        objCapacitacion.getC_lugar(),String.valueOf(objCapacitacion.getN_direccioncli()),
                        objCapacitacion.getC_direccionreg(),objCapacitacion.getC_temacapacitacion(),
                        objCapacitacion.getC_descripciontema(),objCapacitacion.getC_estado(),
                        objCapacitacion.getC_observacion());
                InserCP = asyncTaskInsertCP.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (InserCP.equals("OK")){
                if (docsAdapdater!=null) {
                    docsAdapdater.SetNumero(Integer.valueOf(sCorrelativoCP));
                    ActualizarTablaServerFotos(docsAdapdater.AllItems());
                }
                String sResultTrasnf="";
                TransferirCapacitacionClienteTask transferirCapacitacionClienteTask = new TransferirCapacitacionClienteTask();
                AsyncTask<String,String,String> asyncTaskTransCP;
                try {
                    asyncTaskTransCP = transferirCapacitacionClienteTask.execute(Constans.NroConpania,sCorrelativoCP,"tranferir");
                    sResultTrasnf = asyncTaskTransCP.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if (sResultTrasnf.equals("OK")){
                    if (docsAdapdater!=null && docsAdapdater.getCount()>0){
                        GuardarFotoEnServer(docsAdapdater.AllItems());
                    }
                    FabToast.makeText(DatosGenCapacitacion.this, "Se guardo y sincronizo correctamente la informacion en el servidor.", FabToast.LENGTH_LONG, FabToast.SUCCESS,  FabToast.POSITION_DEFAULT).show();
                    super.onBackPressed();
                }
                else {
                    FabToast.makeText(DatosGenCapacitacion.this, "Error : " + sResultTrasnf, FabToast.LENGTH_LONG, FabToast.ERROR,  FabToast.POSITION_DEFAULT).show();
                }
            }
            else {
                FabToast.makeText(DatosGenCapacitacion.this, "Error : " + InserCP, FabToast.LENGTH_LONG, FabToast.ERROR,  FabToast.POSITION_DEFAULT).show();
            }
        }
    }

    public  void  ActualizarTablaServerFotos(ArrayList<DocsCapacitacion> listDocFotos) {
        String ressultDocInsert = "";
        for (int i = 0; i < listDocFotos.size(); i++) {
            DocsCapacitacion d = listDocFotos.get(i);
            AsyncTask<String, String, String> asyncTaskDocFotoSG;
            InsertDocsCapacitacionTask insertDocsQuejaClienteTask = new InsertDocsCapacitacionTask();
            try {
                asyncTaskDocFotoSG = insertDocsQuejaClienteTask.execute(d.getC_compania(), String.valueOf(d.getN_solicitud()), String.valueOf(i + 1), d.getC_descripcion(), d.getC_nombre_archivo(), d.getC_ruta_archivo(), codUser);
                ressultDocInsert = asyncTaskDocFotoSG.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public  void  GuardarFotoEnServer(ArrayList<DocsCapacitacion> listDocsF)
    {
        String fileCarp = Environment.getExternalStorageDirectory() +File.separator +Constans.Carpeta_foto_CP;
        if (listDocsF!=null && listDocsF.size()>0) {
            for (int i = 0; i < listDocsF.size(); i++) {
                Log.i("ruta foto rg  save server " , listDocsF.get(i).getC_ruta_archivo());
                String fileName = listDocsF.get(i).getC_ruta_archivo();
                String filePath = fileCarp + fileName;
                File file = new File(filePath);

                byte[] bytes = new byte[(int) file.length()];
                try {
                    bytes = FileUtils.readFileToByteArray(file);
                    AsyncTask asyncTask = null;
                    GuardarFotoRGTask guardarImagenTask = new GuardarFotoRGTask(bytes, listDocsF.get(i).getC_ruta_archivo(),"CP");
                    asyncTask = guardarImagenTask.execute();
                    String resp = (String) asyncTask.get();


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public String CodEstado(String descrpEstado){
        String codEstado = "";
        switch (descrpEstado){
            case "Pendiente":
                codEstado="PE";
                break;
            case "Revisado":
                codEstado="RE";
                break;
            case "Cerrado":
                codEstado="CE";
                break;
            case "Anulado":
                codEstado="AN";
                break;
        }
        return codEstado;
   }

    public String CodLugar(String descrpLugar){
        String codLugar = "";
        switch (descrpLugar){
            case "Planta Lys":
                codLugar="PL";
                break;
            case "Local Cliente":
                codLugar="LC";
                break;
            case "Otros":
                codLugar="OT";
                break;
        }
        return codLugar;
    }

    public void setAvance(int nIndic){
        switch (nIndic){
            case 1:
                lblAvance1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                lblAvance2.setBackgroundColor(getResources().getColor(R.color.avGray));
                break;
            case 2:
                lblAvance1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                lblAvance2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
        }
    }

    public  void LoadSpinerDireccionCli (String sComp , String sCliente){
        ProdMantDataBase db = new ProdMantDataBase(DatosGenCapacitacion.this);
        ArrayList<TMADireccionCli> listDir  = db.GetDireccionCli(sComp,sCliente);
        ArrayList<String> spdata = new ArrayList<>();
        spdata.add("Seleccione Direccion .....|");
        if (listDir.size()>0){
            for (int i=0;i<listDir.size();i++) {
                spdata.add(listDir.get(i).getN_secuencia() + " | " + listDir.get(i).getC_descripcion());
            }

        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,spdata);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDireccionCli.setAdapter(dataAdapter);
    }

    public void LoadSpinerTemaCap(){
        ProdMantDataBase db = new ProdMantDataBase(DatosGenCapacitacion.this);
        ArrayList<TMATemaCapacitacion> lisTemaCP  = db.GetTemaCapacitacion();
        ArrayList<String> spdata = new ArrayList<>();
        spdata.add("Seleccione Tema ....|");
        if (lisTemaCP.size()>0){
            for (int i=0;i<lisTemaCP.size();i++) {
                spdata.add(lisTemaCP.get(i).getC_temacapacitacion() + " | " + lisTemaCP.get(i).getC_descripcion());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,spdata);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spTemaGeneralCP.setAdapter(dataAdapter);
        }
    }

    public void LoadSpinerLugar(){
        ArrayList<String> spdata = new ArrayList<>();
        spdata.add("Seleccione lugar.......|");
        spdata.add("PL | Planta Lys");
        spdata.add("LC | Local Cliente");
        spdata.add("OT | Otros");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spdata);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLugarCP.setAdapter(dataAdapter);
    }

    public void AlertDialogBuscarCliente () {
        final Dialog dialog  = new Dialog(DatosGenCapacitacion.this);
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
                String sClient =  lvBuscarClientes.getItemAtPosition(position).toString();
                sClient = sClient.substring(0,sClient.indexOf("|")-1);
                txtClienteCodNom.setText(lvBuscarClientes.getItemAtPosition(position).toString());
                if (spLugarCP.getSelectedItemPosition()==2) {
                    LoadSpinerDireccionCli(Constans.NroConpania, sClient);
                }
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

    public ArrayAdapter<String> getAdapterBuscarCliente(String sFilterText){
        ProdMantDataBase db = new ProdMantDataBase(DatosGenCapacitacion.this);
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
        return arrayAdapter;
    }

    public void SetControlsValues(CapacitacionCliente oEnt){
        ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
        txtNroCapacitacion.setText(String.valueOf(oEnt.getN_correlativo()));
        txtFechaRegCP.setText(FormatearFecha(oEnt.getD_fechareg(),"yyyy-MM-dd HH:mm:ss","dd/MM/yyyy"));
        txtFechaProbCP.setText(FormatearFecha(oEnt.getD_fechaprob(),"yyyy-MM-dd","dd/MM/yyyy"));
        txtHoraProbCP.setText(FormatearFecha(oEnt.getD_horaprob(),"yyyy-MM-dd HH:mm:ss","HH:mm"));
        txtClienteCodNom.setText(String.valueOf(oEnt.getN_cliente())+ " | " + db.GetNombreClienteXCod(String.valueOf(oEnt.getN_cliente())));
        spTemaGeneralCP.setSelection(getIndexSpiner(spTemaGeneralCP,oEnt.getC_temacapacitacion()));
        txtDetalleTema.setText(oEnt.getC_descripciontema());
        txtObservacionCP.setText(oEnt.getC_observacion());
        spLugarCP.setSelection(getIndexSpiner(spLugarCP,oEnt.getC_lugar()));
        txtNumeroPersonaCP.setText(String.valueOf(oEnt.getN_personas()));
        nDireccionGen = oEnt.getN_direccioncli();
        spDireccionCli.setSelection(getIndexSpiner(spDireccionCli,String.valueOf(oEnt.getN_direccioncli())));
        sFechaSelec = oEnt.getD_fechareg();
        sSelFechaProb = oEnt.getD_fechaprob();
        sSelHoraProb = oEnt.getD_horaprob();

        if(sOperacion.equals("VER")){
            txtRevisadoPorCP.setText(oEnt.getC_usuariorev());
            txtFechaRevisadoCP.setText(oEnt.getD_fecharev());
            txtObservacionRevCP.setText(oEnt.getC_observacionrev());
            txtAccionRevisionCP.setText(oEnt.getC_accionrev());
            chkFinRevisionCP.setChecked(false);
            if(oEnt.getC_flagfinrev().equals("S")){
                chkFinRevisionCP.setChecked(true);
            }
            txtCerradoPorCP.setText(oEnt.getC_usuarioCE());
            txtFechaCierreCP.setText(oEnt.getD_fechaCE());
            txtObservacionCierreCP.setText(oEnt.getC_observacionCE());
        }
    }

    public void ShowMessageWindow (String sMessage){
        new PrettyDialog(this)
                .setTitle("Aviso")
                .setMessage(sMessage)
                .setIconTint( R.color.WarningColor)
                .show();
    }

    public String FormatearFecha(String sFecha, String FormatoEntrada, String FomarmatoSalida){
        String result = "" ;
        SimpleDateFormat parseador = new SimpleDateFormat(FormatoEntrada);
        SimpleDateFormat formateador = new SimpleDateFormat(FomarmatoSalida);
        Date date = null;
        try {
            date = parseador.parse(sFecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        result =(formateador.format(date));
        return result;
    }

    private int getIndexSpiner(Spinner spinner, String Cod){
        Log.i("Cod Spin " , Cod);
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            String val = spinner.getItemAtPosition(i).toString() ;
            val = val.substring(0,val.indexOf("|")-1);
            Log.i("val sp " , val );
            if (val.equals(Cod)){
                index = i;
                break;
            }
        }
        return index;
    }

    public void SelectHora() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.horapicker, null, false);
        final TimePicker myTimePicker = (TimePicker) view.findViewById(R.id.tmPickerHora);

        new AlertDialog.Builder(DatosGenCapacitacion.this).setView(view)
                .setTitle("Seleccionar Hora")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        String currentHourText = myTimePicker.getCurrentHour()
                                .toString();

                        String currentMinuteText = myTimePicker
                                .getCurrentMinute().toString();
                        currentHourText =  String.format("%02d", Integer.valueOf(currentHourText));
                        currentMinuteText =  String.format("%02d", Integer.valueOf(currentMinuteText));

                        txtHoraProbCP.setText(currentHourText+":"+currentMinuteText);
                        sSelHoraProb= "1900-01-01 " +currentHourText+":"+currentMinuteText+ ":00";

                    }

                }).show();
    }


    public void SelectFechaProb(final EditText txtFecha ){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.datapicker_layout, null, false);
        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);
        new AlertDialog.Builder(DatosGenCapacitacion.this).setView(view)
                .setTitle("Seleccionar Fecha")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    // @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        int month = myDatePicker.getMonth() + 1;
                        int day = myDatePicker.getDayOfMonth();
                        int year = myDatePicker.getYear();
                        String mes = String.format("%02d", month);
                        String dia = String.format("%02d", day);
                        txtFecha.setText(dia + "/" + mes + "/" + String.valueOf(year));
                        sSelFechaProb = String.valueOf(year)+"-"+mes+"-"+dia;

                        dialog.cancel();

                    }

                }).show();
    }
    public void SelecFecha(final EditText txtFecha) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.datapicker_layout, null, false);
        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);
        new AlertDialog.Builder(DatosGenCapacitacion.this).setView(view)
                .setTitle("Seleccionar Fecha")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    // @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        int month = myDatePicker.getMonth() + 1;
                        int day = myDatePicker.getDayOfMonth();
                        int year = myDatePicker.getYear();
                        String mes = String.format("%02d", month);
                        String dia = String.format("%02d", day);
                        txtFecha.setText(dia + "/" + mes + "/" + String.valueOf(year));
                        sFechaSelec = String.valueOf(year)+"-"+mes+"-"+dia;

                        dialog.cancel();

                    }

                }).show();
    }

    public void AlertDireccionOtros(){

        // creating the EditText widget programatically
        final EditText editText = new EditText(DatosGenCapacitacion.this);
        if (!sDireccionReg.isEmpty())
        {
            editText.setText(sDireccionReg);
        }
        // create the AlertDialog as final
        final AlertDialog dialog = new AlertDialog.Builder(DatosGenCapacitacion.this)
                .setMessage("Escribe la direccion aquí")
                .setTitle("Direccion")
                .setView(editText)
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        sDireccionReg = editText.getText().toString().trim();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // removes the AlertDialog in the screen
                    }
                })
                .create();

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        dialog.show();

    }

    public class DocsCapacitacionAdapter extends ArrayAdapter<DocsCapacitacion> {
        private Context context ;
        private ArrayList<DocsCapacitacion> data ;
        private int layoutResourceId;
        HashMap<Integer,String> hashDescrip;


        public DocsCapacitacionAdapter(@NonNull Context context, int resource, ArrayList<DocsCapacitacion> data) {
            super(context,resource,data);
            this.context = context;
            this.data = data;
            this.layoutResourceId = resource;
            hashDescrip = new HashMap<>();
        }


        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            ViewHolder viewHolder ;
            DocsCapacitacion doc = data.get(position);
            if (convertView == null){
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                convertView = inflater.inflate(layoutResourceId,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.txtDescripcion = convertView.findViewById(R.id.txtDescripFotoRG);
                viewHolder.lblNombreFoto = convertView.findViewById(R.id.lblNombrFotoRG);
                viewHolder.btnVer = convertView.findViewById(R.id.btnVerFotoItemRG);
                convertView.setTag(viewHolder);
            }
            else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            viewHolder.lblNombreFoto.setText(doc.getC_nombre_archivo());
            viewHolder.txtDescripcion.setText(data.get(position).getC_descripcion());
            viewHolder.txtDescripcion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertEdittextListView(position,(EditText)v);
                }
            });



            viewHolder.btnVer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VisorImagen(data.get(position).getC_ruta_archivo());
                    // lvFotosRG.setVisibility(View.GONE);
                }
            });

            return  convertView;

        }

        public ArrayList<DocsCapacitacion> AllItems () {
            return data ;
        }

        public  void SetNumero(int n){
            for (int i = 0; i < data.size() ; i++) {
                data.get(i).setN_solicitud(n);
            }
        }
    }

    public static class ViewHolder{
        TextView lblNombreFoto;
        EditText txtDescripcion;
        Button btnVer ;
    }

    public void AlertEdittextListView(final int positionObj, final EditText txtDescp){
        final EditText editText = new EditText(DatosGenCapacitacion.this);
        final AlertDialog dialog = new AlertDialog.Builder(DatosGenCapacitacion.this)
                .setMessage("Descripcion de foto")
                .setTitle("Escribir texto")
                .setView(editText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        docsAdapdater.data.get(positionObj).setC_descripcion(editText.getText().toString());
                        txtDescp.setText(editText.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .create();

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        dialog.show();

    }

    public  void  VisorImagen (String path ){

        String filePath2 = Environment.getExternalStorageDirectory() + File.separator + Constans.Carpeta_foto_CP+ path;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath2);
        Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }
        });
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bitmap);
        builder.addContentView(imageView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }
}
