package com.filtroslys.filtroslysapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import DataBase.AccesosDB;
import DataBase.ConstasDB;
import DataBase.MenuDB;
import DataBase.ProdMantDataBase;
import DataBase.UsuarioDB;
import Model.DatosAuditoria;
import Model.EventoAuditoriaAPP;
import Model.IMEMovil;
import Model.Parametros;
import Tasks.GetAccesosDataTask;
import Tasks.GetDefaultCompaniaTask;
import Tasks.GetIMEIMovilesTask;
import Tasks.GetListParametrosSistTask;
import Tasks.GetMenuDataTask;
import Tasks.GetTipoIpTask;
import Tasks.GetUsuariosTask;
import Tasks.InsertEventAuditoriaTask;
import Tasks.InsertIMEMovilTask;
import Tasks.InsertarDatosAuditoriaTask;
import Tasks.RefrescarBaseDeDatosTask;
import Tasks.SincronizarAccesosTask;
import Tasks.ValidacionUsuarioIMEITask;
import Tasks.VerificarIMEIRegistradaTask;
import Util.Constans;
import Util.Internet;
import spencerstudios.com.fab_toast.FabToast;

public class Login extends AppCompatActivity  {

    //VARIABLES
    TextView lblModoConexion;
    String GenFile, CompDefault;
    String ProximaFechaVerficacion;
    Button btnIngresar;
    EditText txtUser, txtPassword;
    ActionBar actionBar;
    int currentapiVersion;
    SharedPreferences preferences;
    String sModoTrabajo;
    String codUser;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return true;
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        CrearCarpetasFotos();
        preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
        GenFile = preferences.getString("GenFile", null);
        CompDefault = preferences.getString("CompDefault", null);
        ProximaFechaVerficacion =preferences.getString("FechaExp", null);

        boolean stickyMode = true;
        if (GenFile == null) {

            ActivityCompat.requestPermissions(Login.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA, Manifest.permission.REQUEST_INSTALL_PACKAGES, Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_CONTACTS,Manifest.permission.READ_SMS},
                    1);
        }
        if (CompDefault == null) {
            GetDefaultCompania();
        }

        File FileCon = new File(Environment.getExternalStorageDirectory() +File.separator+"con.txt" );
        if (!FileCon.exists()) {
            CrearndoArchivosConfig();
        }

        currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            actionBar = getSupportActionBar();
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        //copyFile();
        //  ShowDialogAlert();
        // instanciando controles
        btnIngresar =  findViewById(R.id.btnIngresarLogin);
        txtPassword =  findViewById(R.id.txtPassword);
        txtUser =  findViewById(R.id.txtUser);
        lblModoConexion = findViewById(R.id.txtModoConexion);
        txtUser.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        //***********************

        sModoTrabajo = preferences.getString("TipoConexion", null);
        if (sModoTrabajo == null)
            lblModoConexion.setText("Se esta trabajando en modo Externo.");
        else if (sModoTrabajo.equals("E"))
            lblModoConexion.setText("Se esta trabajando en modo Externo.");
        else if (sModoTrabajo.equals("L"))
            lblModoConexion.setText("Se esta trabajando en modo Local.");
        txtUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (currentapiVersion >= 20) {
                    actionBar.hide();
                    getWindow().setStatusBarColor(Color.parseColor("#fc0101"));
                }
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {

                if (!VerificarConexionInternet() && !checkDataBase()){
                    FabToast.makeText(Login.this, "Para poder iniciar debe tener conexion a internet.", FabToast.LENGTH_LONG, FabToast.ERROR,  FabToast.POSITION_DEFAULT).show();
                    return;
                }
                String Sync, resultVerfOn="";
                String currentDateandTime = GetFechaActual();
                ProdMantDataBase db = new ProdMantDataBase(Login.this);
                String user = txtUser.getText().toString();
                String pass = txtPassword.getText().toString();
                //Sync = preferences.getString("Sync", null);
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String numerocelular = telephonyManager.getLine1Number();
                String seriechip = telephonyManager.getSimSerialNumber();
                String imei_movil = telephonyManager.getDeviceId() ;
                Log.e("ID SUSCRIB ", telephonyManager.getSubscriberId());
                Log.e("IMEI MOVIL ", imei_movil);
                DatosAuditoria d = new DatosAuditoria();
                d.setC_imei(imei_movil);
                d.setC_usuario(user);
                d.setC_codIntApp(user);
                d.setC_origen("APPMOVILYS");
                d.setC_movil(numerocelular);
                d.setC_seriechip(seriechip);
                d.setC_tipo("C");

                if (VerificarConexionInternet()){
                    preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
                    String fechaExp  = preferences.getString("FechaExp", null);
                    if (checkDataBase()&& !GetFlagVeerficiar(fechaExp)){
                        GuardarAuditoriaOffline(d);
                        if (db.AutenticarUsuario(user, pass.toUpperCase())){
                            if (db.ValidarEstadoImei(d.getC_imei())){
                                if (db.ValidarIMEINumeroMovil(d.getC_imei(),d.getC_movil()) || db.ValidarIMEISerialChip(d.getC_imei(),d.getC_seriechip())){
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("UserCod", user);
                                    editor.commit();
                                    if (checkDataBase()&& !db.UsuarioTieneAccesos(user.trim())){
                                        codUser= user.trim();
                                        IMEMovil datImei  = GetObjIMEIOnline(imei_movil,seriechip,numerocelular,user);
                                        ShowDialogAlert(datImei);
                                        return;
                                    }
                                    else if (checkDataBase()&& db.UsuarioTieneAccesos(user.trim())){
                                        codUser= user.trim();
                                        Intent i = new Intent(Login.this, MenuPrincipal.class);
                                        startActivity(i);
                                        return;
                                    }



                                }
                                else {
                                    if (!VerificarBloqueoAnonimo()) {
                                        FabToast.makeText(Login.this, "No puede ingresar .", FabToast.LENGTH_LONG, FabToast.ERROR, FabToast.POSITION_DEFAULT).show();
                                        return;
                                    }
                                    else {
                                        Intent i = new Intent(Login.this,MenuPrincipal.class);
                                        startActivity(i);
                                        return;
                                    }

                                }
                            }
                            else {
                                FabToast.makeText(Login.this, "No puede ingresar porque su número de IMEI se encuntra inactiva.", FabToast.LENGTH_LONG, FabToast.ERROR, FabToast.POSITION_DEFAULT).show();
                                return;
                            }

                        }

                    }

                    IMEMovil datImei  = GetObjIMEIOnline(imei_movil,seriechip,numerocelular,user);
                    GuardarAuditoriaOnline(d);
                    AsyncTask<String,String,String> asyncTaskVerificacionLinea;
                    ValidacionUsuarioIMEITask validacionUsuarioIMEITask =  new ValidacionUsuarioIMEITask();
                    if (checkDataBase()){
                        EnviarAuditoriasLocales();
                        EnviarEventosAuditoriasLocales();
                    }
                    try {
                        asyncTaskVerificacionLinea = validacionUsuarioIMEITask.execute(user,pass,imei_movil,numerocelular,seriechip);
                        resultVerfOn = asyncTaskVerificacionLinea.get();
                        if (resultVerfOn.equals("OK")){
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("UserCod", user);
                            editor.commit();
                            if (!checkDataBase()){
                                codUser= user.trim();
                                ShowDialogAlert(datImei);
                            }
                            else if (checkDataBase()&& !db.UsuarioTieneAccesos(user.trim())){
                                codUser= user.trim();
                                ShowDialogAlert(datImei);
                            }
                            else if (checkDataBase()&& db.UsuarioTieneAccesos(user.trim())){
                                codUser= user.trim();
                                Intent i = new Intent(Login.this, MenuPrincipal.class);
                                startActivity(i);
                            }
                        }
                        else {
                            if (resultVerfOn.equals("ANONIMO")){
                            }
                            else {
                                FabToast.makeText(Login.this, resultVerfOn, FabToast.LENGTH_LONG, FabToast.ERROR, FabToast.POSITION_DEFAULT).show();
                                 return;
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    GuardarAuditoriaOffline(d);
                    if (db.AutenticarUsuario(user, pass.toUpperCase())){
                            if (db.ValidarEstadoImei(d.getC_imei())){
                                if (db.ValidarIMEINumeroMovil(d.getC_imei(),d.getC_movil()) || db.ValidarIMEISerialChip(d.getC_imei(),d.getC_seriechip())){
                                    Intent i = new Intent(Login.this,MenuPrincipal.class);
                                    startActivity(i);
                                }
                                else {
                                    if (!VerificarBloqueoAnonimo()) {
                                        FabToast.makeText(Login.this, "No puede ingresar .", FabToast.LENGTH_LONG, FabToast.ERROR, FabToast.POSITION_DEFAULT).show();
                                        return;
                                    }
                                    else {
                                        Intent i = new Intent(Login.this,MenuPrincipal.class);
                                        startActivity(i);
                                    }

                                }
                            }
                            else {
                                FabToast.makeText(Login.this, "No puede ingresar porque su número de IMEI se encuntra inactiva.", FabToast.LENGTH_LONG, FabToast.ERROR, FabToast.POSITION_DEFAULT).show();
                                return;
                            }

                    }
                }

            }
        });

    }


    public  boolean VerificarBloqueoAnonimo(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        ProdMantDataBase  db = new ProdMantDataBase(getApplicationContext());
        String fechaParam = db.ObtenerParametroFecha("FMAXIMETMP",Constans.NroConpania);
        Date date1 =null,date2 =null;
        try {
             date1 = sdf.parse(GetFechaActual());
             date2 = sdf.parse(fechaParam);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date1.after(date2);
    }

    public  void  EnviarEventosAuditoriasLocales(){
        ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
        ArrayList<EventoAuditoriaAPP>  listEvents =  db.EventosAuditoriaPorEnviar();
        String resultInserEvent = "";
        if (listEvents!=null  && listEvents.size()>0){
            Log.i("lista de eventos auditoria por enviar count " , String.valueOf(listEvents.size()));
            for (int i = 0; i < listEvents.size() ; i++) {
                EventoAuditoriaAPP itemEvent = listEvents.get(i);
                AsyncTask<String,String,String>asyncTaskEventosAud ;
                InsertEventAuditoriaTask  insertEventAuditoriaTask = new InsertEventAuditoriaTask();
                insertEventAuditoriaTask.context = Login.this;
                try {
                    asyncTaskEventosAud = insertEventAuditoriaTask.execute(itemEvent.getC_origen(),itemEvent.getC_imei(),itemEvent.getC_movil(),
                            itemEvent.getC_seriechip(),itemEvent.getD_hora(),itemEvent.getC_accion(),itemEvent.getC_usuario(),itemEvent.getC_codIntApp());

                    resultInserEvent = asyncTaskEventosAud.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if (resultInserEvent.equals("OK")){
                    db.EliminarEventoAuidtoriaAPPLocal(String.valueOf( itemEvent.getN_correlativo()));
                }
            }


        }
    }
    public  IMEMovil GetObjIMEIOnline (String Imei, String serialChip,String Numero, String sUsuario){
        String sreultVer = "";
        IMEMovil objResult = new IMEMovil() ;
        AsyncTask <String,String,String> asyncTaskVerfRestroIMEI ;
        VerificarIMEIRegistradaTask verificarIMEIRegistradaTask  = new VerificarIMEIRegistradaTask();
        try {
            asyncTaskVerfRestroIMEI = verificarIMEIRegistradaTask.execute(Imei);
            sreultVer =  asyncTaskVerfRestroIMEI.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (Integer.valueOf(sreultVer)>0){
            ArrayList<IMEMovil> listImeis = new ArrayList<>();
            AsyncTask  <String,String,ArrayList<IMEMovil>> asyncTaskListIMEIs;
            GetIMEIMovilesTask getIMEIMovilesTask = new GetIMEIMovilesTask();
            try {
                asyncTaskListIMEIs = getIMEIMovilesTask.execute(Imei);
                listImeis = asyncTaskListIMEIs.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            objResult = listImeis.get(0);
        }
        else {
            IMEMovil im  = new IMEMovil();
            String resultInsertMov="";
            String currentDateandTime = GetFechaActual();
            im.setC_imei(Imei);
            im.setC_tipo("X");
            im.setC_numero(Numero);
            im.setC_usuarioreg(sUsuario);
            im.setD_fechareg(currentDateandTime);
            im.setC_estado("A");
            im.setC_ultimousuario(sUsuario);
            im.setD_ultimafechamodificacion(currentDateandTime);
            im.setC_seriechip(serialChip);

            InsertIMEMovilTask insertIMEMovilTask = new InsertIMEMovilTask();
            AsyncTask <String,String,String> asyncTaskInsertMovil;
            try {
            asyncTaskInsertMovil = insertIMEMovilTask.execute(im.getC_imei(),im.getC_tipo(),im.getC_numero(),im.getC_usuarioreg(),im.getC_estado(),
                                                                im.getC_ultimousuario(),im.getD_ultimafechamodificacion(),im.getC_seriechip());
                resultInsertMov = asyncTaskInsertMovil.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            if (resultInsertMov.equals("OK")){
                objResult = im;
            }
        }

        return  objResult ;
    }

    public void  GuardarAuditoriaOnline(DatosAuditoria auditoria){
        String result = "";
        String currentDateandTime =GetFechaActual();
        AsyncTask<String,String,String> asyncTaskInsertAudi;
        InsertarDatosAuditoriaTask insertarDatosAuditoriaTask = new InsertarDatosAuditoriaTask();
        try {
        asyncTaskInsertAudi = insertarDatosAuditoriaTask.execute(auditoria.getC_origen(),auditoria.getC_codIntApp(),auditoria.getC_tipo(),
                                                                 auditoria.getC_imei(),auditoria.getC_movil(), auditoria.getC_usuario(),
                                                                 currentDateandTime,auditoria.getC_seriechip());

            result =   asyncTaskInsertAudi.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.i("result envio auditoria" , result);
    }

    public void GuardarAuditoriaOffline (DatosAuditoria aud){
        String currentDateandTime = GetFechaActual();
        String result = "";
        aud.setD_fechaserv(currentDateandTime);
        aud.setD_hora(currentDateandTime);
        aud.setC_enviado("N");

        ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
        long  reg_id =  db.InsertMaDatosAuditoria(aud);
        Log.i("reg id MA_AUDITORIAAPP" , String.valueOf(reg_id));


    }

    public  void  EnviarAuditoriasLocales (){
        ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
        ArrayList<DatosAuditoria> listaAud = db.ListaAuditoriasPorEnviar();

        if (listaAud!= null || listaAud.size()>0)
        {
            for (int i = 0; i < listaAud.size() ; i++)
            {
                DatosAuditoria auditoria = listaAud.get(i);
                String result = "";

                String currentDateandTime = GetFechaActual();
                AsyncTask<String,String,String> asyncTaskInsertAudi;
                InsertarDatosAuditoriaTask insertarDatosAuditoriaTask = new InsertarDatosAuditoriaTask();
                try {
                    asyncTaskInsertAudi = insertarDatosAuditoriaTask.execute(auditoria.getC_origen(),auditoria.getC_codIntApp(),auditoria.getC_tipo(),
                            auditoria.getC_imei(),auditoria.getC_movil(), auditoria.getC_usuario(),
                            currentDateandTime,auditoria.getC_seriechip());

                    result =   asyncTaskInsertAudi.get();
                    if (result.equals("OK")){
                        db.EliminarAudotiriaLocal(String.valueOf( auditoria.getN_correlativo()));
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public  boolean  VerificarConexionInternet (){

       return  Internet.ConexionInternet(Login.this);
    }

    public  void  GetDefaultCompania (){
    String sComp =  "";
    AsyncTask <String,String,String>  asyncTask ;
        GetDefaultCompaniaTask getDefaultCompaniaTask =  new GetDefaultCompaniaTask();

        try {
            asyncTask  = getDefaultCompaniaTask.execute();
            sComp = asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(sComp)){
            CreateCustomToast("Falta configurar la compania por default",Constans.icon_warning,Constans.layot_warning);
            return;
        }
        else  {
            Log.i("CompDefault  > " , sComp);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("CompDefault",sComp);
            editor.commit();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    CrearndoArchivosConfig();
                else
                //    Toast.makeText(Login.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                return;
            }


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.Volver) {

            super.onBackPressed();
            return true;
        }
        if (id == R.id.Config) {

            SeleccionarModoTrabajo();
        }
        return super.onOptionsItemSelected(item);
    }


    public  void  ShowDialogAlert (final IMEMovil dataImei){



        new AlertDialog.Builder(Login.this)
                .setTitle("Advertencia")
                .setMessage("Para ingresar a la aplicación  móvil , primero se debe realizar la sincronizacion")
                .setIcon(R.drawable.icn_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        FabToast.makeText(Login.this, "Vamos a sincronizar, espere por favor no presione nada...", FabToast.LENGTH_LONG, FabToast.INFORMATION, FabToast.POSITION_DEFAULT).show();

                        SincMenuAcceso( dataImei);
                       // dialogInterface.dismiss();
                    }
                }).show();


    }

    public ProgressDialog CreateProgressDialog (String  title , String msj, int icon ){

        ProgressDialog  progress= new ProgressDialog(Login.this);
         progress.setMessage(msj);
        progress.setTitle(title);
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIcon(icon);

        return  progress;

    }

    public  String GetFechaActual (){
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("EST"));
        result = sdf.format(new Date()) ;
        Log.i("Fecha actucual",  result)  ;
        return  result;
    }

    public  void SincMenuAcceso(IMEMovil dataImei)  {

        ProgressDialog progressDialog;
        int icn = (R.drawable.icn_sync_48);
        String resultRefresh = "";
        RefrescarBaseDeDatosTask refrescarBaseDeDatosTask = new RefrescarBaseDeDatosTask() ;
        AsyncTask<String,String,String >asyncTaskRefresh ;
        try {
            asyncTaskRefresh = refrescarBaseDeDatosTask.execute();
            resultRefresh = (String)asyncTaskRefresh.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (resultRefresh.equals("OK")){

        }else {
            CreateCustomToast("Error al actualizar información: "+ resultRefresh,Constans.icon_error,Constans.layout_error);
           return;
        }


        // INSERT MENUS IN SQLITE

        GetMenuDataTask getMenuDataTask = new GetMenuDataTask();
        AsyncTask<String,String,ArrayList<MenuDB>> asyncTask;
        ArrayList<MenuDB> menuDBs= new ArrayList<MenuDB>();
        //ProdMantDataBase db =  new ProdMantDataBase(Login.this);
        //db.deleteTables();

        try {
            asyncTask = getMenuDataTask.execute();
            menuDBs= (ArrayList<MenuDB>)asyncTask.get();



        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // INSERT USUARIOS IN SQLITE

        ArrayList<UsuarioDB> listaUsers = new ArrayList<UsuarioDB>();
        GetUsuariosTask getUsuariosTask = new GetUsuariosTask();
        AsyncTask<String,String,ArrayList<UsuarioDB>> asyncTaskUsers ;

        try {
            asyncTaskUsers =  getUsuariosTask.execute();
            listaUsers = (ArrayList<UsuarioDB>) asyncTaskUsers.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //INSERT ACCESOS IN SQLITE
        ArrayList<AccesosDB> accesosDBs = new ArrayList<>();
       //
        GetAccesosDataTask getAccesosDataTask = new GetAccesosDataTask();
        AsyncTask<String,String,ArrayList<AccesosDB>> asyncTaskAccesos;

        try {

            asyncTaskAccesos = getAccesosDataTask.execute(codUser.trim().toUpperCase());
            accesosDBs = asyncTaskAccesos.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        progressDialog= new ProgressDialog(Login.this);
        progressDialog.setTitle("Sincronizando");
        progressDialog.setMessage("Sincronizando accesos .. espere por favor..");
        progressDialog.setIcon(R.drawable.icn_sync_48);
        SincronizarAccesosTask sincronizarAccesosTask = new SincronizarAccesosTask(Login.this,accesosDBs,menuDBs,listaUsers,progressDialog,codUser,dataImei);
        AsyncTask<Void,Void,Void> asyncTaskSincroAccesos ;
        asyncTaskSincroAccesos = sincronizarAccesosTask.execute();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Sync","Si");
        editor.commit();

        GetListParametrosSistTask  getListParametrosSistTask= new GetListParametrosSistTask();
        AsyncTask<String,String,ArrayList<Parametros>> asyncTaskListaParams ;
        ArrayList<Parametros> listParams = new ArrayList<>();
        try {
            asyncTaskListaParams = getListParametrosSistTask.execute();
            listParams = asyncTaskListaParams.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listParams!= null && listParams.size()>0){
            ProdMantDataBase db = new ProdMantDataBase(Login.this);
            for (int i = 0; i < listParams.size() ; i++) {
                Parametros prm = listParams.get(i);
                if (db.ExiteParametro(prm.getC_parametrocodigo(),prm.getC_compania())>1){
                    db.UpdateParametro(prm);
                }
                else  {
                    db.InsertParametro(prm);
                }
            }
        }

        Intent i = new Intent(Login.this, MenuPrincipal.class);
        startActivity(i);
    }

    public void copyFile()
    {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//com.filtroslys.filtroslysapp//databases//"+ConstasDB.DB_NAME;
                String backupDBPath = ConstasDB.DB_NAME;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
        }
    }

    public void   CreateCustomToast (String msj, int icon,int backgroundLayout ){

        LayoutInflater infator = getLayoutInflater();
        View layout =infator.inflate(R.layout.toast_alarm_success, (ViewGroup) findViewById(R.id.toastlayout));
        TextView toastText = layout.findViewById(R.id.txtDisplayToast);
        ImageView imgIcon =  layout.findViewById(R.id.imgToastSucc);
        LinearLayout parentLayout = layout.findViewById(R.id.toastlayout);
        imgIcon.setImageResource(icon);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            parentLayout.setBackgroundDrawable( getResources().getDrawable(backgroundLayout) );
        } else {
            parentLayout.setBackground( getResources().getDrawable(backgroundLayout));
        }


        toastText.setText(msj);
        Toast toast = new Toast(Login.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }


    public void SeleccionarModoTrabajo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        CharSequence[] data = {"Local", "Externo"};

        builder.setTitle("Modo de trabajo")


                .setSingleChoiceItems(data, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }

                })

                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String Ipserver = "";
                        String tipoconexion = "";
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        if (selectedPosition == 0) {
                            Ipserver = Constans.ReaderFileLocal();
                            tipoconexion = "L";

                        } else {

                            Ipserver = Constans.ReaderFileExterno();
                            tipoconexion = "E";
                        }
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("TipoConexion",tipoconexion);
                        editor.commit();

                        Constans.SetConexion("P", Ipserver);
                        int i = android.os.Process.myPid();
                        android.os.Process.killProcess(i);
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })

                .show();

    }

    public  void  CrearndoArchivosConfig (){

        String  ipLocal = ""  ;
        String  ipExt   = "";

        Constans.ifExistCreateFile("CON","");

        AsyncTask <String,String,String> asyncTaskLocal   ;
        AsyncTask <String,String,String> asyncTaskExt ;

        GetTipoIpTask ipTaskLocal  = new GetTipoIpTask();
        GetTipoIpTask ipTaskExt  =  new GetTipoIpTask();


        try {
            asyncTaskLocal  = ipTaskLocal.execute("LO");
            ipLocal = asyncTaskLocal.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            asyncTaskExt  = ipTaskExt.execute("EX");
            ipExt =  ipTaskExt.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Constans.ifExistCreateFile("LO",ipLocal);
        Constans.ifExistCreateFile("EX",ipExt);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("GenFile","SI");
        editor.commit();
        int i = android.os.Process.myPid();
        android.os.Process.killProcess(i);
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    public  void  CrearCarpetasFotos (){
        File folderRG = new File(Environment.getExternalStorageDirectory() +File.separator+Constans.Carpeta_foto_RG );
        if (!folderRG.exists()) {
            folderRG.mkdirs();
        }

        File folderQJ = new File(Environment.getExternalStorageDirectory() +File.separator+Constans.Carpeta_foto_QJ );
        if (!folderQJ.exists()) {
            folderQJ.mkdirs();
        }

        File folderSG = new File(Environment.getExternalStorageDirectory() +File.separator+Constans.Carpeta_foto_SG );
        if (!folderSG.exists()) {
            folderSG.mkdirs();
        }
    }


    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        String dirdb = getApplicationInfo().dataDir+"/databases/"+ ConstasDB.DB_NAME;
       // String dirdb = "//data//com.filtroslys.filtroslysapp//databases//"+ConstasDB.DB_NAME;
       // String dirdb = getApplicationInfo().dataDir+"/"+ ConstasDB.DB_NAME;
       File f = new File(dirdb);
       if (f.exists()){
           return true ;
       }
       else {
           return  false;
       }
    }

    public  boolean GetFlagVeerficiar(String FechaExpiracion){
        boolean bresult = false;
        SharedPreferences.Editor editor = preferences.edit();

        if (FechaExpiracion == null || TextUtils.isEmpty(FechaExpiracion)){
            Log.i("FechaExp", "nulo  ");
            editor.putString("FechaExp", GetFechaActual());
            editor.commit();
            bresult=false;
            return true;
        }

        ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
        int  horasExp = db.ObtenerParametroNumero("TMPSINCRON","00100000");

        String dateInString = FechaExpiracion;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dateInString));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.HOUR, horasExp);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date resultdate = new Date(c.getTimeInMillis());
        dateInString = sdf.format(resultdate);
        Log.i("Fecha entrada y salida " , FechaExpiracion + " | "+dateInString);

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date1 =null,date2 =null;
        try {
            date1 = sdf.parse(GetFechaActual());
            date2 = sdf.parse(dateInString);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        if (date1.after(date2)){
            editor.putString("FechaExp", GetFechaActual());
            editor.commit();
            bresult =true;

        }
        else {
            bresult = false;

        }

        Log.e("result flag verificar " , String.valueOf(bresult));

        return bresult;
    }




}
