package Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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

/**
 * Created by dvillanueva on 08/08/2016.
 */
public class SincronizarMaestrosTask extends AsyncTask<Void,Void,Void> {

    ProgressDialog progressDialog ;
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

        dbBase.close();
        // prodMantDataBase.deleteTableMaquina();

        //*****

        if (listMaquina!=null && listMaquina.size()>0){

            for (int i = 0  ; i<listMaquina.size();i++){

                prodMantDataBase.InsertMaquina(listMaquina.get(i));

            }

        }

        if (listPeriodos!=null && listPeriodos.size()>0){

            for (int i = 0 ; i<listPeriodos.size();i++){

                prodMantDataBase.InjsertPeriodos(listPeriodos.get(i));
            }
        }

        if (listInspecciones!=null && listInspecciones.size()>0){

            for (int i = 0; i < listInspecciones.size() ; i++) {

                prodMantDataBase.InsertInspeccion(listInspecciones.get(i));
            }
        }

        if (listCcosto !=null && listCcosto.size()>0){

            for (int i = 0; i < listCcosto.size(); i++) {

                prodMantDataBase.InsertCentroCosto(listCcosto.get(i));
            }

        }

        if (listTipoRevision != null && listTipoRevision.size() > 0) {

            for (int i = 0; i < listTipoRevision.size(); i++) {
                prodMantDataBase.InsertTipoRevision(listTipoRevision.get(i));
            }
        }

        if (listClientes!= null && listClientes.size()>0){
            for (int i = 0; i < listClientes.size() ; i++) {
                prodMantDataBase.InsertTMACliente(listClientes.get(i));
            }

        }

        if (listFallas!=null && listFallas.size()>0){
            for (int i = 0; i <  listFallas.size(); i++) {
                prodMantDataBase.InsertTMAFallas(listFallas.get(i));
            }
        }

        if ( listMarcas!=null && listMarcas.size()>0){
            for (int i = 0; i < listMarcas.size() ; i++) {
                prodMantDataBase.InsertTMAMarcas(listMarcas.get(i));
            }
        }

        if (listModelo != null && listModelo.size()>0){
            for (int i = 0; i  < listModelo.size() ; i++) {
                prodMantDataBase.InsertTMAModelo(listModelo.get(i));
            }

        }

        if ( listPruebaLab != null && listPruebaLab.size()>0){
            for (int i = 0; i < listPruebaLab.size() ; i++) {
                prodMantDataBase.InsertTMAPruebasLab(listPruebaLab.get(i));
            }
        }
        if (listTipoReclamo!= null && listTipoReclamo.size()>0){
            for (int i = 0; i <  listTipoReclamo.size(); i++) {
                prodMantDataBase.InsertTMATipoReclamo(listTipoReclamo.get(i));
            }
        }

        if (listVendedor!=null && listVendedor.size()>0){
            for (int i = 0; i < listVendedor.size() ; i++) {
               prodMantDataBase.InsertTMAVendedor(listVendedor.get(i));
            }
        }
        if (listCalifQJ!=null && listCalifQJ.size()>0){
            for (int i = 0; i < listCalifQJ.size() ; i++) {
                prodMantDataBase.InsertTMACalificacionQJ(listCalifQJ.get(i));
            }
        }

        if (listTipoCalifQJ!=null && listTipoCalifQJ.size()>0){
            for (int i = 0; i < listTipoCalifQJ.size() ; i++) {
                prodMantDataBase.InsertTMATipoCalificacionQJ(listTipoCalifQJ.get(i));
            }
        }

        if (listMedioRecQJ!=null && listMedioRecQJ.size()>0){
            for (int i = 0; i < listMedioRecQJ.size() ; i++) {
                prodMantDataBase.InsertTMAMedioRecQJ(listMedioRecQJ.get(i));
            }
        }

        if (listAccionQJ!=null && listAccionQJ.size()>0){
            for (int i = 0; i < listAccionQJ.size() ; i++) {
                prodMantDataBase.InsertTMAAccionTomarQJ(listAccionQJ.get(i));
            }
        }

        if (listNotiQJ!=null && listNotiQJ.size()>0){
            for (int i = 0; i < listNotiQJ.size() ; i++) {
                prodMantDataBase.InsertTMANotificacionQJ(listNotiQJ.get(i));
            }
        }
        if (listTipoSug!=null && listTipoSug.size()>0){
            for (int i = 0; i < listTipoSug.size() ; i++) {
                prodMantDataBase.InsertTMATipoSugerencia(listTipoSug.get(i));
            }
        }

        if (listTemaCap!=null && listTemaCap.size()>0){
            for (int i = 0; i < listTemaCap.size() ; i++) {
                prodMantDataBase.InsertTMATemaCapacitacion(listTemaCap.get(i));
            }
        }

        if (listDirecCli!=null && listDirecCli.size()>0){
            for (int i = 0; i < listDirecCli.size() ; i++) {
                prodMantDataBase.InsertTMADireccionCli(listDirecCli.get(i));
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        Toast.makeText(context, "Sincronizacioón terminada", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setCancelable(false);
        //progressDialog.setIndeterminate(false);
        progressDialog.show();
    }
}
