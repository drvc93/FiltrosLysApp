package com.filtroslys.filtroslysapp;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
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
import DataBase.CentroCostoDB;
import DataBase.InspeccionDB;
import DataBase.MaquinaDB;
import DataBase.MenuDB;
import DataBase.PeriodoInspeccionDB;
import DataBase.ProdMantDataBase;
import DataBase.TipoRevisionGBD;
import DataBase.UsuarioDB;
import Model.EventoAuditoriaAPP;
import Model.IMEMovil;
import Model.OpcionConsulta;
import Model.SubMenuBotones;
import Model.TMAAccionesTomar;
import Model.TMACalificacionQueja;
import Model.TMACliente;
import Model.TMADireccionCli;
import Model.TMAFalla;
import Model.TMAMarca;
import Model.TMAMedioRecepcion;
import Model.TMAModelo;
import Model.TMANotificacionQueja;
import Model.TMAPruebaLab;
import Model.TMATemaCapacitacion;
import Model.TMATipoCalificacionQueja;
import Model.TMATipoReclamo;
import Model.TMATipoSugerencia;
import Model.TMAVendedor;
import Tasks.GetAccesosDataTask;
import Tasks.GetAccionesQJTask;
import Tasks.GetCalifiQJTask;
import Tasks.GetCentroCostoTask;
import Tasks.GetClientesTask;
import Tasks.GetDireccionCliTask;
import Tasks.GetFallasTask;
import Tasks.GetIMEIMovilesTask;
import Tasks.GetInspeccionesTask;
import Tasks.GetMaquinasTask;
import Tasks.GetMarcasTask;
import Tasks.GetMedioRecTask;
import Tasks.GetMenuDataTask;
import Tasks.GetModelosTask;
import Tasks.GetNotificacionQJTask;
import Tasks.GetOpcionesConsultaTask;
import Tasks.GetPeriodosInspTask;
import Tasks.GetPruebasLabTask;
import Tasks.GetTemaCapacitacionTask;
import Tasks.GetTipoCalifiQJTask;
import Tasks.GetTipoReclamosTask;
import Tasks.GetTipoRevisionGTask;
import Tasks.GetTipoSugerenciaTask;
import Tasks.GetUsuariosTask;
import Tasks.GetVendedoresTask;
import Tasks.RefrescarBaseDeDatosTask;
import Tasks.SincronizarAccesosTask;
import Tasks.SincronizarMaestrosTask;
import Util.Constans;
import Util.Funciones;
import Util.Internet;
import spencerstudios.com.fab_toast.FabToast;

public class MenuOpciones extends AppCompatActivity {

    String codMaq, NomMaq;
    SharedPreferences preferences;
    String codPadre, codHijo;
    String codUser, resultBarCode;
    public static final int REQUEST_CODE = 0x0000c0de;
    ListView LVOpciones;
    ArrayList<SubMenuBotones> listOpciones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_opciones);
        preferences = PreferenceManager.getDefaultSharedPreferences(MenuOpciones.this);
        LVOpciones = (ListView) findViewById(R.id.LVopcion);

        codHijo = getIntent().getExtras().getString("codHijo");
        codPadre = getIntent().getExtras().getString("codPadre");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MenuOpciones.this);
        codUser = preferences.getString("UserCod", null);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        if (codPadre != null && codHijo != null) {

            Log.i("cod Padre > ", codPadre);
            Log.i("cod  hijo >", codHijo);
            listOpciones = GetOpciones(codPadre, codHijo);
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

    public ArrayList<SubMenuBotones> GetOpciones(String codPadre, String smenu) {
        ProdMantDataBase db = new ProdMantDataBase(MenuOpciones.this);

        ArrayList<SubMenuBotones> result = db.getSubBotones(codPadre, smenu, codUser);


        return result;

    }


    public void GetMaquinaBarCode(String barcode) {

        ProdMantDataBase db = new ProdMantDataBase(MenuOpciones.this);
        MaquinaDB m = db.GetMAquinaPorCodigo(barcode);
        if (m == null) {
            CreateCustomToast("No se encontro una maquina con el codigo de barra", Constans.icon_error, Constans.layout_error);
        } else {
            String msj = "Maquina encontrada : " + m.getC_descripcion();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("CodMaquina", m.getC_maquina());
            editor.putString("NomMaquina", m.getC_descripcion());
            editor.putString("FamMaquina", m.getC_familiainspeccion());
            editor.commit();


            NomMaq = m.getC_descripcion();
            codMaq = m.getC_maquina();
            CreateCustomToast(msj, Constans.icon_succes, Constans.layout_success);
        }


    }

    public void CreateButtons() {

        OpcionesAdapter opcionesAdapter = new OpcionesAdapter(MenuOpciones.this, R.layout.layout_lista_opciones, listOpciones);
        LVOpciones.setAdapter(opcionesAdapter);
    }


    // Adapter  listview

    public class OpcionesAdapter extends ArrayAdapter<SubMenuBotones> {


        public OpcionesAdapter(Context context, int resource, ArrayList<SubMenuBotones> data) {
            super(context, resource, data);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view = convertView;

            if (view == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                view = vi.inflate(R.layout.layout_lista_opciones, null);
            }

            SubMenuBotones subMenuBotones = getItem(position);
            Button btnOpcList = view.findViewById(R.id.btnListOpciones);
            if (btnOpcList != null) {

                btnOpcList.setText(subMenuBotones.getDescripcion());
            }

            btnOpcList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("Boton Seleciconado ====>codPadre : ", listOpciones.get(position).getCodPadre());
                    Log.i("Boton Seleciconado ====>submenu : ", listOpciones.get(position).getCodSubmenu());
                    Log.i("Boton Seleciconado ====>cod Boton : ", listOpciones.get(position).getCodMenuBoton());
                    SubMenuBotones sbmenu = listOpciones.get(position);
                    IrActivity(sbmenu);
                }
            });

            return view;
        }
    }

    public void IrActivity(SubMenuBotones sb) {
        ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
        String var_concatenado = sb.getCodPadre() + sb.getCodSubmenu() + sb.getCodMenuBoton();
        EventoAuditoriaAPP event = new EventoAuditoriaAPP();
        event.setC_enviado("N");
        event.setC_movil(Funciones.DatosTelefono("NUMERO",getApplicationContext()));
        event.setC_imei((Funciones.DatosTelefono("IMEI",getApplicationContext())));
        event.setC_seriechip((Funciones.DatosTelefono("SIM",getApplicationContext())));
        event.setC_origen(Constans.OrigenAPP_Auditoria);
        event.setD_hora(Funciones.GetFechaActual());
        event.setC_usuario(codUser);
        event.setC_codIntApp(codUser);
        event.setC_accion("[CLICK BOTON : "+sb.getDescripcion().trim()+"]");
        db.InsertEventoAuidtoriaAPP(event);

        Log.i("Contaenado nivel ==> ", var_concatenado);

        if (var_concatenado.equals("010101")) {

            Intent intentScan = new Intent(Constans.BS_PACKAGE + ".SCAN");
            intentScan.putExtra("PROMPT_MESSAGE", "Enfoque entre 9 y 11 cm. apuntado el codigo de barras de la maquina");
            startActivityForResult(intentScan, REQUEST_CODE);
        }
        if (var_concatenado.equals("010102")) {
            if (codMaq == null && NomMaq == null) {
                CreateCustomToast("Primero de escanear el codigo de barras", Constans.icon_error, Constans.layout_error);
            } else {
                Intent intent = new Intent(getApplicationContext(), InspeccionMaq.class);
                intent.putExtra("tipoMant", "NEW");
                startActivity(intent);
            }
        }
        if (var_concatenado.equals("010103")) {
            Intent intent = new Intent(MenuOpciones.this, InspeccionMaqListLinea.class);
            intent.putExtra("TipoSync", "Online");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
        if (var_concatenado.equals("010104")) {
            Intent intent = new Intent(MenuOpciones.this, InspeccionMaqListLinea.class);
            intent.putExtra("TipoSync", "Offline");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }

        if (var_concatenado.equals("010201")) {
            Intent intent = new Intent(MenuOpciones.this, InspeccionGen.class);
            intent.putExtra("tipoMant", "NEW");
            startActivity(intent);
        }
        if (var_concatenado.equals("010202")) {
            Intent intent = new Intent(MenuOpciones.this, InspeccionGenListLinea.class);
            intent.putExtra("tipoSincro", "Online");
            // intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
        if (var_concatenado.equals("010203")) {
            Intent intent = new Intent(MenuOpciones.this, InspeccionGenListLinea.class);
            intent.putExtra("tipoSincro", "Offline");
            startActivity(intent);
        }
        /*Agregado el 10 de nov 2017 */
        if (var_concatenado.equals("010301")) {
            Intent intent = new Intent(MenuOpciones.this, ListaSolServicios.class);
            startActivity(intent);
        }
        if (var_concatenado.equals("020101")) {
            Intent intent = new Intent(MenuOpciones.this, ListaReqLog.class);
            startActivity(intent);
        }

        if (var_concatenado.equals("030101")) {

            AlertSyncro("MAESTROS");
        }

        if (var_concatenado.equals("030102")) {

            AlertSyncro("ACCESOS");
        }

        if (var_concatenado.equals("030201")) {

            Intent intent = new Intent(this, Descargas.class);
            startActivity(intent);
        }
        if (var_concatenado.equals("040101")) {

            Intent intent = new Intent(MenuOpciones.this, Configuracion.class);
            startActivity(intent);
        }

        if (var_concatenado.equals("050101")) {
            Intent intent = new Intent(getApplicationContext(), SelecCliente.class);
            //intent.putExtra("Operacion","NEW");
            startActivity(intent);
        }

        if (var_concatenado.equals("050501")) {
            Intent intent = new Intent(getApplicationContext(), DatosGenRG.class);
            intent.putExtra("Operacion", "NEW");
            startActivity(intent);
        }
        if (var_concatenado.equals("050502")) {
            Intent intent = new Intent(getApplicationContext(), ListaReclamoGarantia.class);
            startActivity(intent);
        }

        if (var_concatenado.equals("050201")) {
            Intent intent = new Intent(getApplicationContext(), DatosGenQueja.class);
            intent.putExtra("AccionQJ", "NEW");
            startActivity(intent);
        }
        if (var_concatenado.equals("050202")) {
            Intent intent = new Intent(getApplicationContext(), ListaQuejaCliente.class);
            startActivity(intent);
        }

        //Regisrtro Sugerencia
        if (var_concatenado.equals("050301")) {
            Intent intent = new Intent(getApplicationContext(), DatosGenSugerencia.class);
            intent.putExtra("Operacion", "NEW");
            intent.putExtra("TipoInfo", "SU");
            startActivity(intent);
        }
        if (var_concatenado.equals("050302")) {
            Intent intent = new Intent(getApplicationContext(), ListaSugerenciaCliente.class);
            intent.putExtra("TipoInfo", "SU");
            startActivity(intent);
        }

        //Capaccitacion
        if (var_concatenado.equals("050401")) {
            Intent intent = new Intent(getApplicationContext(), DatosGenCapacitacion.class);
            intent.putExtra("Operacion", "NEW");
            startActivity(intent);
        }
        if (var_concatenado.equals("050402")) {
            Intent intent = new Intent(getApplicationContext(), ListaCapacitacionCliente.class);
            startActivity(intent);
        }

        //Solicitud Compra Especial
        if (var_concatenado.equals("050601")) {
            Intent intent = new Intent(getApplicationContext(), DatosGenSugerencia.class);
            intent.putExtra("Operacion", "NEW");
            intent.putExtra("TipoInfo", "SE");
            startActivity(intent);
        }

        if (var_concatenado.equals("050602")) {
            Intent intent = new Intent(getApplicationContext(), ListaSugerenciaCliente.class);
            intent.putExtra("TipoInfo", "SE");
            startActivity(intent);
        }

        //Solicitud Material Publicitario
        if (var_concatenado.equals("050701")) {
            Intent intent = new Intent(getApplicationContext(), DatosGenSugerencia.class);
            intent.putExtra("Operacion", "NEW");
            intent.putExtra("TipoInfo", "MP");
            startActivity(intent);
        }

        if (var_concatenado.equals("050702")) {
            Intent intent = new Intent(getApplicationContext(), ListaSugerenciaCliente.class);
            intent.putExtra("TipoInfo", "MP");
            startActivity(intent);
        }

        //Consutla Tecnica
        if (var_concatenado.equals("050801")) {
            Intent intent = new Intent(getApplicationContext(), DatosGenSugerencia.class);
            intent.putExtra("Operacion", "NEW");
            intent.putExtra("TipoInfo", "CT");
            startActivity(intent);
        }

        if (var_concatenado.equals("050802")) {
            Intent intent = new Intent(getApplicationContext(), ListaSugerenciaCliente.class);
            intent.putExtra("TipoInfo", "CT");
            startActivity(intent);
        }

        if (var_concatenado.equals("050901")) {
            Intent intent = new Intent(getApplicationContext(), AvanceVend.class);

            startActivity(intent);
        }
        if (var_concatenado.equals("050902")) {
            Intent intent = new Intent(getApplicationContext(), DepTecnico.class);

            startActivity(intent);
        }
    }

    public void AlertSyncro(final String tipoSincronizacion) {
        String mensaje;
        if (tipoSincronizacion.equals("MAESTROS")) {
            mensaje = "¿Desea sincronizar las tablas maestros?";

        } else {
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
                                } else {

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


    public  boolean  VerificarConexionInternet (){

        return  Internet.ConexionInternet(this);
    }

    public void SincMenuAcceso() {

        if (!VerificarConexionInternet()){
            FabToast.makeText(this, "Para poder sincronizar debe tener conexión a internet.", FabToast.LENGTH_LONG, FabToast.ERROR,  FabToast.POSITION_DEFAULT).show();
            return;
        }

        ProgressDialog progressDialog;

        String resultRefresh = "";
        RefrescarBaseDeDatosTask refrescarBaseDeDatosTask = new RefrescarBaseDeDatosTask();
        AsyncTask<String, String, String> asyncTaskRefresh;
        try {
            asyncTaskRefresh = refrescarBaseDeDatosTask.execute();
            resultRefresh =  asyncTaskRefresh.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (resultRefresh.equals("OK")) {

        } else {
            CreateCustomToast("Error al actualizar información: " + resultRefresh, Constans.icon_error, Constans.layout_error);
            return;
        }
        // INSERT MENUS IN SQLITE
        GetMenuDataTask getMenuDataTask = new GetMenuDataTask();
        AsyncTask<String, String, ArrayList<MenuDB>> asyncTask;
        ArrayList<MenuDB> menuDBs = new ArrayList<>();
        ProdMantDataBase db = new ProdMantDataBase(MenuOpciones.this);

        try {
            asyncTask = getMenuDataTask.execute();
            menuDBs = (ArrayList<MenuDB>) asyncTask.get();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // INSERT USUARIOS IN SQLITE

        ArrayList<UsuarioDB> listaUsers = new ArrayList<UsuarioDB>();
        GetUsuariosTask getUsuariosTask = new GetUsuariosTask();
        AsyncTask<String, String, ArrayList<UsuarioDB>> asyncTaskUsers;

        try {
            asyncTaskUsers = getUsuariosTask.execute();
            listaUsers =  asyncTaskUsers.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //INSERT ACCESOS IN SQLITE
        ArrayList<AccesosDB> accesosDBs = new ArrayList<>();
        //
        GetAccesosDataTask getAccesosDataTask = new GetAccesosDataTask();
        AsyncTask<String, String, ArrayList<AccesosDB>> asyncTaskAccesos;

        try {
            asyncTaskAccesos = getAccesosDataTask.execute(codUser);
            accesosDBs = asyncTaskAccesos.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        assert telephonyManager != null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        String imei_movil = telephonyManager.getDeviceId();
        IMEMovil objResult = null;
        ArrayList<IMEMovil> listImeis = new ArrayList<>();
        AsyncTask  <String,String,ArrayList<IMEMovil>> asyncTaskListIMEIs;
        GetIMEIMovilesTask getIMEIMovilesTask = new GetIMEIMovilesTask();
        try {
            asyncTaskListIMEIs = getIMEIMovilesTask.execute(imei_movil);
            listImeis = asyncTaskListIMEIs.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        objResult = listImeis.get(0);

        if (db.ExiteIMEI(objResult.getC_imei())>0){
            db.UpdateIMEIMovil(objResult);
        }
        else {
            db.InsertIMEIMovil(objResult);
        }


        progressDialog= new ProgressDialog(MenuOpciones.this);
        progressDialog.setTitle("Sincronizando");
        progressDialog.setMessage("Sincronizando accesos .. espere por favor..");
        progressDialog.setIcon(R.drawable.icn_sync_48);
        SincronizarAccesosTask sincronizarAccesosTask = new SincronizarAccesosTask(MenuOpciones.this,accesosDBs,menuDBs,listaUsers,progressDialog,codUser,objResult);
        AsyncTask<Void,Void,Void> asyncTaskSincroAccesos ;
        asyncTaskSincroAccesos = sincronizarAccesosTask.execute();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Sync","Si");
        editor.commit();
    }
    public  void  SincronizacionMaestros (){

        String resultRefresh = "";
        RefrescarBaseDeDatosTask refrescarBaseDeDatosTask = new RefrescarBaseDeDatosTask() ;
        AsyncTask<String,String,String >asyncTaskRefresh ;
        try {
            asyncTaskRefresh = refrescarBaseDeDatosTask.execute();
            resultRefresh = asyncTaskRefresh.get();
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

        ProgressDialog progressDialogo = new ProgressDialog(MenuOpciones.this);
        progressDialogo.setMessage("Estamos sincronizando espere por favor...");
        progressDialogo.setTitle("Sincronización");
        progressDialogo.setIcon(R.drawable.icn_sync_48);

        // Arrays variables
        ArrayList<MaquinaDB> listMaquinas;

        // get list maquinas
      listMaquinas = new ArrayList<MaquinaDB>();
        GetMaquinasTask getmaquinasTask = new GetMaquinasTask();
        getmaquinasTask.context = MenuOpciones.this;
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
            listPeriodos = asyncTaskPeriodos.get();
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
            listInspecciones = asyncTaskInspecciones.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ArrayList<CentroCostoDB> listCentroCosto = new ArrayList<CentroCostoDB>();
        AsyncTask<String,String,ArrayList<CentroCostoDB>> asyncTaskCentroCosto ;
        GetCentroCostoTask getCentroCostoTask  = new GetCentroCostoTask();

        try {
            asyncTaskCentroCosto = getCentroCostoTask.execute();
            listCentroCosto = (ArrayList<CentroCostoDB>) asyncTaskCentroCosto.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //  list tipo revision
        AsyncTask<String, String, ArrayList<TipoRevisionGBD>> asyncTipoRevision;
        GetTipoRevisionGTask getTipoRevisionGTask = new GetTipoRevisionGTask();
        ArrayList<TipoRevisionGBD> listTipoRevision = new ArrayList<TipoRevisionGBD>();

        try {
            asyncTipoRevision = getTipoRevisionGTask.execute();
            listTipoRevision = (ArrayList<TipoRevisionGBD>) asyncTipoRevision.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // Lista de clientes
        AsyncTask<String,String,ArrayList<TMACliente>> asyncTaskClientes ;
        GetClientesTask getClientesTask = new GetClientesTask();
        ArrayList<TMACliente>listClientes = new ArrayList<>();
        try {
            asyncTaskClientes = getClientesTask.execute();
            listClientes = asyncTaskClientes.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // lista de fallas
        AsyncTask<String,String,ArrayList<TMAFalla>> asyncTaskFalla;
        GetFallasTask getFallasTask = new GetFallasTask();
        ArrayList<TMAFalla> listaFallas =  new ArrayList<>();
        try {
            asyncTaskFalla = getFallasTask.execute();
            listaFallas = asyncTaskFalla.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // lista marcas
        AsyncTask<String,String,ArrayList<TMAMarca>> asyncTaskMarcas ;
        GetMarcasTask getMarcasTask = new GetMarcasTask();
        ArrayList<TMAMarca>  listaMarca = new ArrayList<>() ;
        try {
            asyncTaskMarcas = getMarcasTask.execute();
            listaMarca = asyncTaskMarcas.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // lista modelos
        AsyncTask<String,String,ArrayList<TMAModelo>>  asyncTaskModelos;
        GetModelosTask getModelosTask = new GetModelosTask() ;
        ArrayList<TMAModelo> listModelos  = new ArrayList<>();
        try {
            asyncTaskModelos  = getModelosTask.execute();
            listModelos = asyncTaskModelos.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // lista  pruebas lab
        AsyncTask<String,String,ArrayList<TMAPruebaLab>> asyncTaskPruebasLab;
        GetPruebasLabTask  getPruebasLabTask = new GetPruebasLabTask();
        ArrayList<TMAPruebaLab> listPruebasLab =  new ArrayList<>() ;
        try {
            asyncTaskPruebasLab = getPruebasLabTask.execute();
            listPruebasLab  = asyncTaskPruebasLab.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // LISTA TIPO RECLAMO
        AsyncTask<String,String,ArrayList<TMATipoReclamo>> asyncTaskTipoReclamos ;
        GetTipoReclamosTask  getTipoReclamosTask = new GetTipoReclamosTask();
        ArrayList<TMATipoReclamo> listTipoReclamo = new ArrayList<>();
        try {
            asyncTaskTipoReclamos = getTipoReclamosTask.execute();
            listTipoReclamo = asyncTaskTipoReclamos.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // Lista Vendedores
        AsyncTask<String,String,ArrayList<TMAVendedor>> asyncTaskVendedor ;
        GetVendedoresTask  getVendedoresTask = new GetVendedoresTask();
        ArrayList<TMAVendedor> listVendedor = new ArrayList<>();
        try {
            asyncTaskVendedor = getVendedoresTask.execute();
            listVendedor = asyncTaskVendedor.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //   LISTA CALIFICACION
        ArrayList<TMACalificacionQueja> calaficaciones = new ArrayList<>();
        //
        GetCalifiQJTask getCalifiQJTask = new GetCalifiQJTask();
        AsyncTask<String,String,ArrayList<TMACalificacionQueja>> asyncTaskCalificacion;

        try {
            asyncTaskCalificacion = getCalifiQJTask.execute();
            calaficaciones = asyncTaskCalificacion.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // Lista tipo calificacion
        ArrayList<TMATipoCalificacionQueja> tipocalaficaciones = new ArrayList<>();

        GetTipoCalifiQJTask getCalifiQJTask1 = new GetTipoCalifiQJTask();
        AsyncTask<String,String,ArrayList<TMATipoCalificacionQueja>> asyncTaskTipoCalificacion;

        try {
            asyncTaskTipoCalificacion = getCalifiQJTask1.execute();
            tipocalaficaciones = asyncTaskTipoCalificacion.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //  lista medio recepcion
        ArrayList<TMAMedioRecepcion> listMedioRecepcion = new ArrayList<>();
        //
        GetMedioRecTask getMedioRecTask = new GetMedioRecTask();
        AsyncTask<String,String,ArrayList<TMAMedioRecepcion>> asyncTaskMedioRecepcion;

        try {
            asyncTaskMedioRecepcion = getMedioRecTask.execute();
            listMedioRecepcion = asyncTaskMedioRecepcion.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // lista acciones rq
        ArrayList<TMAAccionesTomar> listAccionesRQ = new ArrayList<>();
        //
        GetAccionesQJTask getAccionesQJTask = new GetAccionesQJTask();
        AsyncTask<String,String,ArrayList<TMAAccionesTomar>> asyncTaskAccionesQueja;

        try {
            asyncTaskAccionesQueja = getAccionesQJTask.execute();
            listAccionesRQ = asyncTaskAccionesQueja.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // lista de notificaciones rq
        ArrayList<TMANotificacionQueja> listNotificacion = new ArrayList<>();
        //
        GetNotificacionQJTask getNotificacionQJTask = new GetNotificacionQJTask();
        AsyncTask<String,String,ArrayList<TMANotificacionQueja>> asyncTaskNotifi;

        try {
            asyncTaskNotifi = getNotificacionQJTask.execute();
            listNotificacion = asyncTaskNotifi.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // lista de Tipo Sugerencia
        ArrayList<TMATipoSugerencia> listTipoSugerencia = new ArrayList<>();
        GetTipoSugerenciaTask getTipoSugerenciaTask = new GetTipoSugerenciaTask();
        AsyncTask<String,String,ArrayList<TMATipoSugerencia>> asyncTaskTipoSug;

        try {
            asyncTaskTipoSug = getTipoSugerenciaTask.execute();
            listTipoSugerencia = asyncTaskTipoSug.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // lista de Tema Capacitacion
        ArrayList<TMATemaCapacitacion> listTemaCapacitacion = new ArrayList<>();
        GetTemaCapacitacionTask getTemaCapacitacionTask = new GetTemaCapacitacionTask();
        AsyncTask<String,String,ArrayList<TMATemaCapacitacion>> asyncTaskTemaCap;

        try {
            asyncTaskTemaCap = getTemaCapacitacionTask.execute();
            listTemaCapacitacion = asyncTaskTemaCap.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // lista de Direccion Cliente
        ArrayList<TMADireccionCli> listDireccionCli = new ArrayList<>();
        GetDireccionCliTask getDireccionCliTask = new GetDireccionCliTask();
        AsyncTask<String,String,ArrayList<TMADireccionCli>> asyncTaskDirCli;

        try {
            asyncTaskDirCli = getDireccionCliTask.execute();
            listDireccionCli = asyncTaskDirCli.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // lista de opciones consulta
        ArrayList<OpcionConsulta> listaOpciones  = new ArrayList<>();
        GetOpcionesConsultaTask getOpcionesConsultaTask  = new GetOpcionesConsultaTask();
        AsyncTask<String,String,ArrayList<OpcionConsulta>> asyncTaskListOpc;
        try {
            asyncTaskListOpc = getOpcionesConsultaTask.execute(Constans.NroConpania);
            listaOpciones = asyncTaskListOpc.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Log.i("Pre ejecuion de maestroTask",".");
        // progress dialog with asynctask
        AsyncTask<Void,Void,Void> asyncMaestros;
        SincronizarMaestrosTask sincroMaestrosTask = new SincronizarMaestrosTask(MenuOpciones.this, progressDialogo, listMaquinas, listPeriodos, listInspecciones, listCentroCosto, listTipoRevision);
        sincroMaestrosTask.listClientes = listClientes;
        sincroMaestrosTask.listFallas = listaFallas;
        sincroMaestrosTask.listMarcas  = listaMarca;
        sincroMaestrosTask.listModelo = listModelos;
        sincroMaestrosTask.listPruebaLab = listPruebasLab;
        sincroMaestrosTask.listTipoReclamo = listTipoReclamo;
        sincroMaestrosTask.listVendedor = listVendedor;
        sincroMaestrosTask.listCalifQJ = calaficaciones;
        sincroMaestrosTask.listTipoCalifQJ = tipocalaficaciones;
        sincroMaestrosTask.listMedioRecQJ = listMedioRecepcion;
        sincroMaestrosTask.listAccionQJ = listAccionesRQ;
        sincroMaestrosTask.listNotiQJ = listNotificacion;
        sincroMaestrosTask.listTipoSug = listTipoSugerencia;
        sincroMaestrosTask.listTemaCap = listTemaCapacitacion;
        sincroMaestrosTask.listDirecCli = listDireccionCli;
        sincroMaestrosTask.listOpcion = listaOpciones ;
         asyncMaestros = sincroMaestrosTask.execute();
        Log.i("Post ejecuion de maestroTask",".");
    }

    public void   CreateCustomToast (String msj, int icon,int backgroundLayout ){

        LayoutInflater infator = getLayoutInflater();
        View layout =infator.inflate(R.layout.toast_alarm_success, (ViewGroup) findViewById(R.id.toastlayout));
        TextView toastText = layout.findViewById(R.id.txtDisplayToast);
        ImageView imgIcon = layout.findViewById(R.id.imgToastSucc);
        LinearLayout parentLayout = layout.findViewById(R.id.toastlayout);
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
