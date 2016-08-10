package com.filtroslys.filtroslysapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import DataBase.AccesosDB;
import DataBase.InspeccionDB;
import DataBase.MaquinaDB;
import DataBase.MenuDB;
import DataBase.PeriodoInspeccionDB;
import DataBase.ProdMantDataBase;
import DataBase.UsuarioDB;
import Model.Permisos;
import Model.SubMenuBotones;
import Tasks.GetAccesosDataTask;
import Tasks.GetInspeccionesTask;
import Tasks.GetMaquinasTask;
import Tasks.GetMenuDataTask;
import Tasks.GetPeriodosInspTask;
import Tasks.GetUsuariosTask;
import Tasks.SincronizarAccesosTask;
import Tasks.SincronizarMaestrosTask;
import Util.Constans;

public class MenuOpciones extends AppCompatActivity {

    String codMaq ,NomMaq;
    SharedPreferences preferences;
    String  codPadre , codHijo;
    String codUser,resultBarCode;
    public static final int REQUEST_CODE = 0x0000c0de;
    ListView LVOpciones;
    ArrayList<SubMenuBotones> listOpciones = new ArrayList<SubMenuBotones>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_opciones);
        preferences = PreferenceManager.getDefaultSharedPreferences(MenuOpciones.this);
        LVOpciones = (ListView) findViewById(R.id.LVopcion);

        codHijo = getIntent().getExtras().getString("codHijo");
        codPadre = getIntent().getExtras().getString("codPadre");
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        if (codPadre != null && codHijo != null ){

            Log.i("cod Padre > ", codPadre);
            Log.i("cod  hijo >", codHijo);
            listOpciones = GetOpciones(codPadre,codHijo);
            CreateButtons();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                resultBarCode = data.getStringExtra("SCAN_RESULT");
                GetMaquinaBarCode(resultBarCode);

            }
        }
    }

    public  ArrayList<SubMenuBotones> GetOpciones(String codPadre , String smenu){
        ProdMantDataBase db = new ProdMantDataBase(MenuOpciones.this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MenuOpciones.this);
        codUser = preferences.getString("UserCod",null);

        ArrayList<SubMenuBotones> result = db.getSubBotones(codPadre,smenu,codUser);


        return  result;

    }


    public void GetMaquinaBarCode (String barcode){

        ProdMantDataBase db = new ProdMantDataBase(MenuOpciones.this);
        MaquinaDB m = db.GetMAquinaPorCodigo(barcode);
        if (m==null){
            CreateCustomToast("No se encontro una maquina con el codigo de barra" ,Constans.icon_error,Constans.layout_error);
        }
        else {
            String msj = "Maquina encontrada : " + m.getC_descripcion();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("CodMaquina", m.getC_maquina());
            editor.putString("NomMaquina",m.getC_descripcion());
            editor.putString("FamMaquina",m.getC_familiainspeccion());
            editor.commit();
            NomMaq= m.getC_descripcion();
            codMaq = m.getC_maquina();
            CreateCustomToast(msj,Constans.icon_succes,Constans.layout_success);
        }


    }

    public void CreateButtons (){

        OpcionesAdapter  opcionesAdapter = new OpcionesAdapter(MenuOpciones.this,R.layout.layout_lista_opciones,listOpciones);
        LVOpciones.setAdapter(opcionesAdapter);
    }



    // Adapter  listview

    public class OpcionesAdapter  extends ArrayAdapter<SubMenuBotones> {


        public OpcionesAdapter(Context context, int resource, ArrayList<SubMenuBotones> data) {
            super(context, resource, data);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View  view = convertView;

            if (view == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                view= vi.inflate(R.layout.layout_lista_opciones, null);
            }

            SubMenuBotones subMenuBotones = getItem(position);
            Button btnOpcList =(Button) view.findViewById(R.id.btnListOpciones);
            if (btnOpcList!=null){

                btnOpcList.setText(subMenuBotones.getDescripcion());
            }

            btnOpcList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  Toast.makeText(ListaOpciones.this,String.valueOf(position),Toast.LENGTH_SHORT).show();


                    Log.i("Boton Seleciconado ====>codPadre : " , listOpciones.get(position).getCodPadre());
                    Log.i("Boton Seleciconado ====>submenu : ",listOpciones.get(position).getCodSubmenu());
                    Log.i("Boton Seleciconado ====>cod Boton : " , listOpciones.get(position).getCodMenuBoton());
                    // Log.i("Boton Seleciconado ====>codPadre: " , listOpciones.get(position).getaClass().getPackageName() );
                    SubMenuBotones sbmenu = listOpciones.get(position);
                     IrActivity(sbmenu);
                }
            });

            return  view;
        }
    }

    public  void  IrActivity (SubMenuBotones sb){
        ProdMantDataBase db = new ProdMantDataBase(MenuOpciones.this);
        ArrayList<Permisos> permiso = new ArrayList<Permisos>() ;
        String var_concatenado =  sb.getCodPadre()+sb.getCodSubmenu()+sb.getCodMenuBoton();
        Log.i("Contaenado nivel ==> ",var_concatenado);

        if (var_concatenado.equals("010101")){

            Intent intentScan = new Intent(Constans.BS_PACKAGE + ".SCAN");
            intentScan.putExtra("PROMPT_MESSAGE", "Enfoque entre 9 y 11 cm. apuntado el codigo de barras de la maquina");
            startActivityForResult(intentScan, REQUEST_CODE);

        }
        if (var_concatenado.equals("010102")){

           if (codMaq==null  && NomMaq== null){

               CreateCustomToast("Primero de escanear el codigo de barras",Constans.icon_error,Constans.layout_error);
           }
            else {

               Intent intent =  new Intent(getApplicationContext(),InspeccionMaq.class);
               startActivity(intent);


           }

        }

        if (var_concatenado.equals("030101")){

            AlertSyncro("MAESTROS");
        }

        if (var_concatenado.equals("030102")){

            AlertSyncro("ACCESOS");
        }



    }

    public void AlertSyncro(final String tipoSincronizacion) {

        String mensaje = "";
        if (tipoSincronizacion.equals("MAESTROS")){
            mensaje = "¿Desea sincronizar las tablas maestros?";

        }
        else {
            mensaje = "¿Desea sincronizar los accesos?";


        }

        new AlertDialog.Builder(MenuOpciones.this)
                .setTitle("Sincronización")
                .setMessage(mensaje)
                .setIcon(R.drawable.icn_alert)
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                 if (tipoSincronizacion.equals("MAESTROS")) {
                                     SincronizacionMaestros();
                                 }
                                else {
                                     SincMenuAcceso();
                                 }
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();
    }



    public  void SincMenuAcceso()  {

        ProgressDialog progressDialog;
        int icn = (R.drawable.icn_sync_48);


        // INSERT MENUS IN SQLITE

        GetMenuDataTask getMenuDataTask = new GetMenuDataTask();
        AsyncTask<String,String,ArrayList<MenuDB>> asyncTask;
        ArrayList<MenuDB> menuDBs= new ArrayList<MenuDB>();
        ProdMantDataBase db =  new ProdMantDataBase(MenuOpciones.this);
        db.deleteTables();

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
        ArrayList<AccesosDB> accesosDBs = new ArrayList<AccesosDB>();
        //
        GetAccesosDataTask getAccesosDataTask = new GetAccesosDataTask();
        AsyncTask<String,String,ArrayList<AccesosDB>> asyncTaskAccesos;

        try {
            asyncTaskAccesos = getAccesosDataTask.execute();
            accesosDBs = (ArrayList<AccesosDB>)asyncTaskAccesos.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        progressDialog= new ProgressDialog(MenuOpciones.this);
        progressDialog.setTitle("Sincronizando");
        progressDialog.setMessage("Sincronizando accesos .. espere por favor..");
        progressDialog.setIcon(R.drawable.icn_sync_48);
        SincronizarAccesosTask sincronizarAccesosTask = new SincronizarAccesosTask(MenuOpciones.this,accesosDBs,menuDBs,listaUsers,progressDialog);
        AsyncTask<Void,Void,Void> asyncTaskSincroAccesos ;

        asyncTaskSincroAccesos = sincronizarAccesosTask.execute();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Sync","Si");
        editor.commit();



    }


    public  void  SincronizacionMaestros (){

        ProgressDialog progressDialogo = new ProgressDialog(MenuOpciones.this);
        progressDialogo.setMessage("Estamos sincronizando espere por favor...");
        progressDialogo.setTitle("Sinconización");
        progressDialogo.setIcon(R.drawable.icn_sync_48);

        // Arrays variables

        ArrayList<MaquinaDB> listMaquinas;

        //***

        // get list maquinas
      listMaquinas = new ArrayList<MaquinaDB>();
        GetMaquinasTask getmaquinasTask = new GetMaquinasTask();
        AsyncTask asyncTaskMaquina ;

        try {
            asyncTaskMaquina = getmaquinasTask.execute();
            listMaquinas = (ArrayList<MaquinaDB>) asyncTaskMaquina.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



        // list Periodos

        ArrayList<PeriodoInspeccionDB>  listPeriodos = new ArrayList<PeriodoInspeccionDB>();
        AsyncTask<String,String,ArrayList<PeriodoInspeccionDB>> asyncTaskPeriodos;
        GetPeriodosInspTask  getPeriodosInspTask = new GetPeriodosInspTask();

        try {
            asyncTaskPeriodos= getPeriodosInspTask.execute();
            listPeriodos = (ArrayList<PeriodoInspeccionDB>)asyncTaskPeriodos.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // list Inspecciones
        ArrayList<InspeccionDB> listInspecciones = new ArrayList<InspeccionDB>();
        AsyncTask<String,String,ArrayList<InspeccionDB>> asyncTaskInspecciones;
        GetInspeccionesTask getInspeccionesTask = new GetInspeccionesTask();

        try {
            asyncTaskInspecciones = getInspeccionesTask.execute();
            listInspecciones =(ArrayList<InspeccionDB>) asyncTaskInspecciones.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        Log.i("Pre ejecuion de maestroTask",".");
        // progress dialog with asynctask
        AsyncTask<Void,Void,Void> asyncMaestros;
        SincronizarMaestrosTask sincroMaestrosTask = new SincronizarMaestrosTask(MenuOpciones.this,progressDialogo,listMaquinas,listPeriodos,listInspecciones);
         asyncMaestros = sincroMaestrosTask.execute();
        Log.i("Post ejecuion de maestroTask",".");
    }

    public void   CreateCustomToast (String msj, int icon,int backgroundLayout ){

        LayoutInflater infator = getLayoutInflater();
        View layout =infator.inflate(R.layout.toast_alarm_success, (ViewGroup) findViewById(R.id.toastlayout));
        TextView toastText = (TextView)layout.findViewById(R.id.txtDisplayToast);
        ImageView imgIcon =  (ImageView)layout.findViewById(R.id.imgToastSucc);
        LinearLayout parentLayout = ( LinearLayout)layout.findViewById(R.id.toastlayout);
        imgIcon.setImageResource(icon);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            parentLayout.setBackgroundDrawable( getResources().getDrawable(backgroundLayout) );
        } else {
            parentLayout.setBackground( getResources().getDrawable(backgroundLayout));
        }


        toastText.setText(msj);
        Toast toast = new Toast(MenuOpciones.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }
}
