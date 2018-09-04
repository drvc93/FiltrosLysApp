package Tasks;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.animation.PathInterpolator;
import android.widget.Toast;

import java.util.ArrayList;

import DataBase.CentroCostoDB;
import DataBase.ConstasDB;
import DataBase.DBHelper;
import DataBase.InspeccionDB;
import DataBase.MaquinaDB;
import DataBase.PeriodoInspeccionDB;
import DataBase.ProdMantDataBase;
import DataBase.TipoRevisionGBD;
import Model.EventoAuditoriaAPP;
import Model.OpcionConsulta;
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
import Util.Constans;
import Util.Funciones;
import spencerstudios.com.fab_toast.FabToast;

/**
 * Created by dvillanueva on 08/08/2016.
 */
public class SincronizarMaestrosTask extends AsyncTask<Void,Void,Void> {

    ProgressDialog progressDialog ;
    SharedPreferences preferences;
    Context context ;
    ArrayList<MaquinaDB> listMaquina  ;
    ArrayList<PeriodoInspeccionDB> listPeriodos;
    ArrayList<InspeccionDB> listInspecciones;
    ArrayList<CentroCostoDB>listCcosto;
    ArrayList<TipoRevisionGBD> listTipoRevision;
    public ArrayList<TMACliente> listClientes;
    public ArrayList<TMAFalla> listFallas;
    public ArrayList<TMAMarca> listMarcas;
    public ArrayList<TMAModelo> listModelo;
    public ArrayList<TMAPruebaLab> listPruebaLab;
    public ArrayList<TMATipoReclamo> listTipoReclamo;
    public ArrayList<TMAVendedor> listVendedor;
    public ArrayList<TMACalificacionQueja> listCalifQJ;
    public ArrayList<TMATipoCalificacionQueja> listTipoCalifQJ;
    public ArrayList<TMAMedioRecepcion> listMedioRecQJ;
    public ArrayList<TMAAccionesTomar> listAccionQJ;
    public ArrayList<TMANotificacionQueja> listNotiQJ;
    public ArrayList<TMATipoSugerencia> listTipoSug;
    public ArrayList<TMATemaCapacitacion> listTemaCap;
    public ArrayList<TMADireccionCli> listDirecCli;
    public  ArrayList<OpcionConsulta> listOpcion ;
    String codUser = "";

    public SincronizarMaestrosTask(Context context, ProgressDialog progressDialog, ArrayList<MaquinaDB> listMaquina,
                                   ArrayList<PeriodoInspeccionDB> listPeriodos, ArrayList<InspeccionDB> listInspecciones, ArrayList<CentroCostoDB> listCcosto,
                                   ArrayList<TipoRevisionGBD> listTipoRevision) {
        this.context = context;
        this.listMaquina = listMaquina;
        this.progressDialog = progressDialog;
        this.listPeriodos = listPeriodos;
        this.listCcosto = listCcosto;
        this.listInspecciones = listInspecciones;
        this.listTipoRevision = listTipoRevision;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        listClientes= new ArrayList<>();
        listFallas = new ArrayList<>();
        listMarcas = new ArrayList<>();
        listModelo = new ArrayList<>();
        listPruebaLab = new ArrayList<>();
        listTipoReclamo = new ArrayList<>();
        listVendedor = new ArrayList<>();
        listCalifQJ = new ArrayList<>();
        listTipoCalifQJ = new ArrayList<>();
        listMedioRecQJ = new ArrayList<>();
        listAccionQJ  = new ArrayList<>();
        listNotiQJ = new ArrayList<>();
        listTipoSug = new ArrayList<>();
        listTemaCap = new ArrayList<>();
        listDirecCli = new ArrayList<>();
        codUser = preferences.getString("UserCod", null);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ProdMantDataBase prodMantDataBase = new ProdMantDataBase(context);

        // delete tables
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase dbBase = dbHelper.getWritableDatabase();
        dbBase.execSQL("DELETE FROM "+ ConstasDB.TABLA_MTP_MAQUINAS_NAME);
        dbBase.execSQL("DELETE FROM "+ ConstasDB.TABLA_MTP_PERIODO_INSPECCION_NAME);
        dbBase.execSQL("DELETE FROM "+ ConstasDB.TABLA_MTP_INSPECCION_NAME);
        dbBase.execSQL("DELETE FROM "+ ConstasDB.TABLA_MTP_CENTROCOSTO_NAME);
        dbBase.execSQL("DELETE FROM " + ConstasDB.TABLA_MTP_TIPOREVISION_NAME);
        dbBase.execSQL("DELETE FROM " + ConstasDB.TMA_CLIENTES_NAME);
        dbBase.execSQL("DELETE FROM " + ConstasDB.TMA_FALLA_NAME);
        dbBase.execSQL("DELETE FROM " + ConstasDB.TMA_MODELO_NAME);
        dbBase.execSQL("DELETE FROM " + ConstasDB.TMA_MARCA_NAME);
        dbBase.execSQL("DELETE FROM " + ConstasDB.TMA_PRUEBALAB_NAME);
        dbBase.execSQL("DELETE FROM " + ConstasDB.TMA_TIPORECLAMO_NAME);
        dbBase.execSQL("DELETE FROM " + ConstasDB.TMA_VENDEDOR_NAME);
        dbBase.execSQL("DELETE FROM " + ConstasDB.TMA_CALIFICACIONQUEJA_NAME);
        dbBase.execSQL("DELETE FROM " + ConstasDB.TMA_TIPOCALIFICACIONQUEJA_NAME);
        dbBase.execSQL("DELETE FROM " + ConstasDB.TMA_MEDIORECEPCION_NAME);
        dbBase.execSQL("DELETE FROM " + ConstasDB.TMA_ACCIONESTOMAR_NAME);
        dbBase.execSQL("DELETE FROM " + ConstasDB.TMA_NOTIFICAQUEJA_NAME);
        dbBase.execSQL("DELETE FROM " + ConstasDB.TMA_TIPOSUGERENCIA_NAME);
        dbBase.execSQL("DELETE FROM " + ConstasDB.TMA_TEMACAPACITACION_NAME);
        dbBase.execSQL("DELETE FROM " + ConstasDB.TMA_DIRECCIONCLI_NAME);
        dbBase.execSQL("DELETE FROM " + ConstasDB.MA_OPCIONCONSULTA_NAME);

        dbBase.close();
        // prodMantDataBase.deleteTableMaquina();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        @SuppressLint("MissingPermission") String numerocelular = telephonyManager.getLine1Number();
        @SuppressLint("MissingPermission")String seriechip = telephonyManager.getSimSerialNumber();
        @SuppressLint("MissingPermission") String imei_movil = telephonyManager.getDeviceId();
        EventoAuditoriaAPP event = new EventoAuditoriaAPP();
        event.setC_enviado("N");
        event.setC_movil(numerocelular);
        event.setC_imei(imei_movil);
        event.setC_seriechip(seriechip);
        event.setC_codIntApp(codUser);
        event.setC_usuario(codUser);
        event.setC_origen(Constans.OrigenAPP_Auditoria);
        //*****
        if (listMaquina!=null && listMaquina.size()>0){
            event.setC_accion("[SINCRONIZÓ MAESTRO MAQUINAS]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0  ; i<listMaquina.size();i++){

                prodMantDataBase.InsertMaquina(listMaquina.get(i));

            }

        }

        if (listPeriodos!=null && listPeriodos.size()>0){
            event.setC_accion("[SINCRONIZÓ MAESTRO PERIODOS]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0 ; i<listPeriodos.size();i++){

                prodMantDataBase.InjsertPeriodos(listPeriodos.get(i));
            }
        }

        if (listInspecciones!=null && listInspecciones.size()>0){
            event.setC_accion("[SINCRONIZÓ MAESTRO INSPECCIONES]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0; i < listInspecciones.size() ; i++) {

                prodMantDataBase.InsertInspeccion(listInspecciones.get(i));
            }
        }

        if (listCcosto !=null && listCcosto.size()>0){
            event.setC_accion("[SINCRONIZÓ MAESTRO CENTRO COSTOS]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0; i < listCcosto.size(); i++) {

                prodMantDataBase.InsertCentroCosto(listCcosto.get(i));
            }

        }

        if (listTipoRevision != null && listTipoRevision.size() > 0) {
            event.setC_accion("[SINCRONIZÓ MAESTRO TIPO REVISIÓN]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0; i < listTipoRevision.size(); i++) {
                prodMantDataBase.InsertTipoRevision(listTipoRevision.get(i));
            }
        }

        if (listClientes!= null && listClientes.size()>0){
            event.setC_accion("[SINCRONIZÓ MAESTRO CLIENTES]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            prodMantDataBase.InsertMasivoClientes(listClientes);

        }

        if (listFallas!=null && listFallas.size()>0){
            event.setC_accion("[SINCRONIZÓ MAESTRO FALLAS LAB.]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0; i <  listFallas.size(); i++) {
                prodMantDataBase.InsertTMAFallas(listFallas.get(i));
            }
        }

        if ( listMarcas!=null && listMarcas.size()>0){
            event.setC_accion("[SINCRONIZÓ MAESTRO MARCAS]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0; i < listMarcas.size() ; i++) {
                prodMantDataBase.InsertTMAMarcas(listMarcas.get(i));
            }
        }

        if (listModelo != null && listModelo.size()>0){
            event.setC_accion("[SINCRONIZÓ MAESTRO MODELOS]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
             prodMantDataBase.InsertMasivoModelos(listModelo);
        }

        if ( listPruebaLab != null && listPruebaLab.size()>0){
            event.setC_accion("[SINCRONIZÓ MAESTRO PRUEBAS LAB]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0; i < listPruebaLab.size() ; i++) {
                prodMantDataBase.InsertTMAPruebasLab(listPruebaLab.get(i));
            }
        }
        if (listTipoReclamo!= null && listTipoReclamo.size()>0){
            event.setC_accion("[SINCRONIZÓ MAESTRO TIPO RECLAMO]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0; i <  listTipoReclamo.size(); i++) {
                prodMantDataBase.InsertTMATipoReclamo(listTipoReclamo.get(i));
            }
        }

        if (listVendedor!=null && listVendedor.size()>0){
            event.setC_accion("[SINCRONIZÓ MAESTRO VENDEDORES]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0; i < listVendedor.size() ; i++) {
               prodMantDataBase.InsertTMAVendedor(listVendedor.get(i));
            }
        }
        if (listCalifQJ!=null && listCalifQJ.size()>0){
            event.setC_accion("[SINCRONIZÓ MAESTRO CALIFICACION QUEJA]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0; i < listCalifQJ.size() ; i++) {
                prodMantDataBase.InsertTMACalificacionQJ(listCalifQJ.get(i));
            }
        }

        if (listTipoCalifQJ!=null && listTipoCalifQJ.size()>0){
            event.setC_accion("[SINCRONIZÓ MAESTRO TIPO  CALIFICACION QUEJA]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0; i < listTipoCalifQJ.size() ; i++) {
                prodMantDataBase.InsertTMATipoCalificacionQJ(listTipoCalifQJ.get(i));
            }
        }

        if (listMedioRecQJ!=null && listMedioRecQJ.size()>0){
            event.setC_accion("[SINCRONIZÓ MAESTRO MEDIO RECEPCION QUEJA]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0; i < listMedioRecQJ.size() ; i++) {
                prodMantDataBase.InsertTMAMedioRecQJ(listMedioRecQJ.get(i));
            }
        }

        if (listAccionQJ!=null && listAccionQJ.size()>0){
            event.setC_accion("[SINCRONIZÓ MAESTRO ACCION QUEJAS]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0; i < listAccionQJ.size() ; i++) {
                prodMantDataBase.InsertTMAAccionTomarQJ(listAccionQJ.get(i));
            }
        }

        if (listNotiQJ!=null && listNotiQJ.size()>0){
            event.setC_accion("[SINCRONIZÓ MAESTRO NOTIFICACION QUEJA]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0; i < listNotiQJ.size() ; i++) {
                prodMantDataBase.InsertTMANotificacionQJ(listNotiQJ.get(i));
            }
        }
        if (listTipoSug!=null && listTipoSug.size()>0){
            event.setC_accion("[SINCRONIZÓ MAESTRO TIPO SUGERENCIA]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0; i < listTipoSug.size() ; i++) {
                prodMantDataBase.InsertTMATipoSugerencia(listTipoSug.get(i));
            }
        }

        if (listTemaCap!=null && listTemaCap.size()>0){
            event.setC_accion("[SINCRONIZÓ MAESTRO TEMA CAPACITACION]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0; i < listTemaCap.size() ; i++) {
                prodMantDataBase.InsertTMATemaCapacitacion(listTemaCap.get(i));
            }
        }

        if (listDirecCli!=null && listDirecCli.size()>0){
            event.setC_accion("[SINCRONIZÓ MAESTRO DIRECCIONES CLIENTES]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            prodMantDataBase.InsertMasiviDireccionCliente(listDirecCli);
        }

        if (listOpcion!=null && listOpcion.size()>0) {
            event.setC_accion("[SINCRONIZÓ OPCIONES CONSULTAS]");
            event.setD_hora(Funciones.GetFechaActual());
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0; i < listOpcion.size() ; i++) {
                prodMantDataBase.InsertarOpcionConsulta(listOpcion.get(i));
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        FabToast.makeText(context, "Se sincronizo correctamente.", FabToast.LENGTH_LONG, FabToast.SUCCESS,  FabToast.POSITION_DEFAULT).show();
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setCancelable(false);
        //progressDialog.setIndeterminate(false);
        progressDialog.show();
    }
}
