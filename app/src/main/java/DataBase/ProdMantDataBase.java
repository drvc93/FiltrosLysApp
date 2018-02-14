package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.filtroslys.filtroslysapp.InspeccionGen;
import com.filtroslys.filtroslysapp.ListaReclamoGarantia;
import com.filtroslys.filtroslysapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Model.CapacitacionCliente;
import Model.DocsCapacitacion;
import Model.DocsQuejaCliente;
import Model.DocsReclamoGarantia;
import Model.DocsSugerencia;
import Model.InspeccionGenCabecera;
import Model.InspeccionGenDetalle;
import Model.InspeccionMaqCabecera;
import Model.InspeccionMaqDetalle;
import Model.Menu;
import Model.Permisos;
import Model.QuejaCliente;
import Model.ReclamoGarantia;
import Model.SubMenu;
import Model.SubMenuBotones;
import Model.SugerenciaCliente;
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
 * Created by dvillanueva on 04/08/2016.
 */
public class ProdMantDataBase {

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public ProdMantDataBase(Context context) {
        dbHelper = new DBHelper(context);
    }

    private void OpenReadableDB() {
        db = dbHelper.getReadableDatabase();

    }

    private void OpenWritableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void CloseDB() {
        if (db != null) {
            db.close();
        }
    }

    private ContentValues MenuContentValues(MenuDB menuDB) {

        ContentValues cv = new ContentValues();
        cv.put(ConstasDB.MTP_MEN_COD, menuDB.getAppCodigo());
        cv.put(ConstasDB.MTP_MEN_N1, menuDB.getNivel1());
        cv.put(ConstasDB.MTP_MEN_N2, menuDB.getNivel2());
        cv.put(ConstasDB.MTP_MEN_N3, menuDB.getNivel3());
        cv.put(ConstasDB.MTP_MEN_N4, menuDB.getNivel4());
        cv.put(ConstasDB.MTP_MEN_N5, menuDB.getNivel5());
        cv.put(ConstasDB.MTP_MEN_C_NOMM, menuDB.getNombreMenu());
        cv.put(ConstasDB.MTP_MEN_C_DESCP, menuDB.getDescripcion());
        return cv;
    }


    private  ContentValues UsuariosContentValues (UsuarioDB us){

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstasDB.MTP_USUARIO_COD,us.getCodigoUsuario());
        contentValues.put(ConstasDB.MTP_USUARIO_NOM, us.getNombre());
        contentValues.put(ConstasDB.MTP_USUARIO_CLAV, us.getClave());
        contentValues.put(ConstasDB.MTP_USUARIO_NRO,us.getNroPersona());
        contentValues.put(ConstasDB.MTP_USUARIO_EST,us.getEstado());
        contentValues.put(ConstasDB.MTP_USUARIO_FLAG,us.getFlagmantto());

        return  contentValues;

    }

    private ContentValues AccesosContentValues(AccesosDB accesosDB) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstasDB.MTP_ACC_C_USUARIO, accesosDB.getUsuario());
        contentValues.put(ConstasDB.MTP_ACC_C_APP, accesosDB.getAppCodigo());
        contentValues.put(ConstasDB.MTP_ACC_C_NIV, accesosDB.getNivelAcc());
        contentValues.put(ConstasDB.MTP_ACC_C_ACC, accesosDB.getAcceso());
        contentValues.put(ConstasDB.MTP_ACC_C_NUE, accesosDB.getNuevo());
        contentValues.put(ConstasDB.MTP_ACC_C_MOD, accesosDB.getModificar());
        contentValues.put(ConstasDB.MTP_ACC_C_ELI, accesosDB.getEliminar());
        contentValues.put(ConstasDB.MTP_ACC_C_OTRO, accesosDB.getOtros());
        return contentValues;

    }

    public  ContentValues MaquinasContentValues (MaquinaDB maquinaDB){

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstasDB.MTP_MAQUINA_COMP,maquinaDB.getC_ompania());
        contentValues.put(ConstasDB.MTP_MAQUINA_COD_MAQUINA,maquinaDB.getC_maquina());
        contentValues.put(ConstasDB.MTP_MAQUINA_DESCRP,maquinaDB.getC_descripcion());
        contentValues.put(ConstasDB.MTP_MAQUINA_COD_BARRAS,maquinaDB.getC_codigobarras());
        contentValues.put(ConstasDB.MTP_MAQUINA_FAM_INSP,maquinaDB.getC_familiainspeccion());
        contentValues.put(ConstasDB.MTP_MAQUINA_CENTRO_COS,maquinaDB.getC_centrocosto());
        contentValues.put(ConstasDB.MTP_MAQUINA_ESTADO,maquinaDB.getC_estado());
        contentValues.put(ConstasDB.MTP_MAQUINNA_ULT_USER,maquinaDB.getC_ultimousuario());
        contentValues.put(ConstasDB.MTP_MAQUINA_ULTIMAFECHAMOD,maquinaDB.getD_ultimafechamodificacion());

        return  contentValues;

    }
    public ContentValues PeriodosContentValues (PeriodoInspeccionDB periodo){

        ContentValues  contentValues = new ContentValues();
        contentValues.put(ConstasDB.MTP_PERIODO_INSPECCION_COD_PER, periodo.getC_periodoinspeccion());
        contentValues.put(ConstasDB.MTP_PERIODO_INSPECCION_DESCRP,periodo.getC_descripcion());
        contentValues.put(ConstasDB.MTP_PERIODO_INSPECCION_ESTADO,periodo.getC_estado());
        contentValues.put(ConstasDB.MTP_PERIODO_INSPECCION_ULT_USER,periodo.getC_ultimousuario());
        contentValues.put(ConstasDB.MTP_MAQUINA_ULTIMAFECHAMOD,periodo.getD_ultimafechamodificacion());
        return  contentValues;
    }

    public  ContentValues InspeccionesContentValues (InspeccionDB inp){

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstasDB.MTP_INSPECCION_COD_INSP,inp.getC_inspeccion());
        contentValues.put(ConstasDB.MTP_INSPECCION_DESCRIPCION,inp.getC_descripcion());
        contentValues.put(ConstasDB.MTP_INSPECCION_TIPO_INS,inp.getC_tipoinspeccion());
        contentValues.put(ConstasDB.MTP_INSPECCION_PORC_MIN,inp.getN_porcentajeminimo());
        contentValues.put(ConstasDB.MTP_INSPECCION_PORC_MAX,inp.getN_porcentajemaximo());
        contentValues.put(ConstasDB.MTP_INSPECCION_FAM_INP,inp.getC_familiainspeccion());
        contentValues.put(ConstasDB.MTP_INSPECCION_PER_INSP,inp.getC_periodoinspeccion());
        contentValues.put(ConstasDB.MTP_INSPECCION_ESTADO,inp.getC_estado());
        contentValues.put(ConstasDB.MTP_INSPECCION_ULT_USER,inp.getC_ultimousuario());
        contentValues.put(ConstasDB.MTP_INSPECCION_ULT_FECHA,inp.getD_ultimafechamodificacion());
        return  contentValues;

    }

    public  ContentValues InspeccionMaquinaCabValues (InspeccionMaqCabecera inpCAB){

        ContentValues contentValues = new ContentValues( );
        contentValues.put(ConstasDB.MTP_INSP_MAQ_CAB_COMPANIA , inpCAB.getCompania());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_CAB_CORRELATIVO,inpCAB.getCorrlativo());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_CAB_COD_MAQUINA,inpCAB.getCodMaquina());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_CAB_CONDICION_MAQUINA,inpCAB.getCondicionMaq());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_CAB_COMENTARIO,inpCAB.getComentario());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_CAB_ESTADO,inpCAB.getEstado());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_CAB_FECHA_INI_INSP,inpCAB.getFechaInicioInsp());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_CAB_FECHA_FIN_INSP,inpCAB.getFechFinInsp());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_CAB_PERIODO_INSP,inpCAB.getPeriodoInsp());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_CAB_USUARIO_INS,inpCAB.getUsuarioInsp());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_CAB_USUARIO_ENV,inpCAB.getUsuarioEnv());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_CAB_FECHA_ENV,inpCAB.getFechaEnv());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_CAB_ULT_USER,inpCAB.getUltUsuairo());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_CAB_ULT_FECHA_MOD,inpCAB.getUltFechaMod());

        return  contentValues;
    }

    public  ContentValues  InspeccionMaquinaDetValues (InspeccionMaqDetalle det){
        ContentValues  contentValues = new ContentValues();
        contentValues.put(ConstasDB.MTP_INSP_MAQ_DET_COMPANIA,det.getCompania());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_DET_CORRELATIVO, det.getCorrelativo());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_DET_LINEA,det.getLinea());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_DET_COD_INSP,det.getCod_inspeccion());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_DET_TIP_INSP,det.getTipo_inspecicon());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_DET_PORCENT_MIN,det.getPorcentMin());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_DET_PORCENT_MAX,det.getPorcentMax());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_DET_PORCEN_INSP,det.getPorcentInspec());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_DET_ESTADO,det.getEstado());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_DET_COMENTARIO,det.getComentario());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_DET_RUTA_FOTO,det.getRutaFoto());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_DET_ULT_USER,det.getUltimoUser());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_DET_ULT_FECHA_MOD,det.getUltimaFechaMod());

        return  contentValues;


    }

    public  ContentValues CentroCostoContentValues(CentroCostoDB c){
         ContentValues contentValues= new ContentValues();
         contentValues.put(ConstasDB.MTP_CENTRO_C_COMPANIA,c.getC_compania());
        contentValues.put(ConstasDB.MTP_CENTRO_C_COD_CCOSTO,c.getC_centrocosto());
        contentValues.put(ConstasDB.MTP_CENTRO_C_DESCRIPCION, c.getC_descripcion());
        contentValues.put(ConstasDB.MTP_CENTRO_C_ESTADO,c.getC_estado());
        return  contentValues;

    }

    public ContentValues TipoRevisionContentValues(TipoRevisionGBD t) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstasDB.MTP_TIPOREVISION_COD, t.getCod_tiporevision());
        contentValues.put(ConstasDB.MTP_TIPOREVISION_DESCRIPCION, t.getDescripcion());
        contentValues.put(ConstasDB.MTP_TIPOREVISION_ESTADO, t.getEstado());
        contentValues.put(ConstasDB.MTP_TIPOREVISION_ULT_USER, t.getUltimoUsuario());
        contentValues.put(ConstasDB.MTP_TIPOREVISION_ULT_FECHAMOD, t.getUltFechaMod());

        return contentValues;
    }

    public ContentValues InspGenCabContentValues(InspeccionGenCabecera cab) {
        Log.i("content values fehca", cab.getFechaInsp());
       // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       // cab.setFechaInsp(dateFormat.format(cab.getFechaInsp()));
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstasDB.MTP_INSP_GEN_CAB_COMPANIA, cab.getCompania());
        contentValues.put(ConstasDB.MTP_INSP_GEN_CAB_CORRELATIVO, cab.getCorrelativo());
        contentValues.put(ConstasDB.MTP_INSP_GEN_CAB_TIP_INSP, cab.getTipoInspeccion());
        contentValues.put(ConstasDB.MTP_INSP_GEN_CAB_COD_MAQ, cab.getCod_maquina());
        contentValues.put(ConstasDB.MTP_INSP_GEN_CAB_COD_CCOSTO, cab.getCentroCosto());
        contentValues.put(ConstasDB.MTP_INSP_GEN_CAB_COD_COMENTARIO, cab.getComentario());
        contentValues.put(ConstasDB.MTP_INSP_GEN_CAB_USUARIO_INSP, cab.getUsuarioInsp());
        contentValues.put(ConstasDB.MTP_INSP_GEN_CAB_FECHA_INSP, cab.getFechaInsp());
        contentValues.put(ConstasDB.MTP_INSP_GEN_CAB_ESTADO, cab.getEstado());
        contentValues.put(ConstasDB.MTP_INSP_GEN_CAB_USUARIO_ENV, cab.getUsuarioEnvio());
        contentValues.put(ConstasDB.MTP_INSP_GEN_CAB_FECHA_ENV, cab.getFechaEnvia());
        contentValues.put(ConstasDB.MTP_INSP_GEN_CAB_ULT_USER, cab.getUltUsuario());
        contentValues.put(ConstasDB.MTP_INSP_GEN_CAB_FECHA_MOD, cab.getUltFechaMod());

        return contentValues;
    }


    public ContentValues InspGenDetContentValues(InspeccionGenDetalle det) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstasDB.MTP_INSP_GEN_DET_COMPANIA, det.getCompania());
        contentValues.put(ConstasDB.MTP_INSP_GEN_DET_CORRELATIVO, det.getCorrelativo());
        contentValues.put(ConstasDB.MTP_INSP_GEN_DET_LINEA, det.getLinea());
        contentValues.put(ConstasDB.MTP_INSP_GEN_DET_COMENTARIO, det.getComentario());
        contentValues.put(ConstasDB.MTP_INSP_GEN_DET_RUTA_FOTO, det.getRutaFoto());
        contentValues.put(ConstasDB.MTP_INSP_GEN_DET_ULT_USUARIO, det.getUltUsuario());
        contentValues.put(ConstasDB.MTP_INSP_GEN_DET_ULT_FECHA_MOD, det.getUltFechaMod());
        contentValues.put(ConstasDB.MTP_INSP_GEN_DET_TIPO_REVISION, det.getTipoRevision());
        contentValues.put(ConstasDB.MTP_INSP_GEN_DET_FLAG_ADIC, det.getFlagadictipo());
        return contentValues;

    }

    public  ContentValues TMAClientesContentValues(TMACliente c){
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_compania", c.getC_compania());
        contentValues.put("n_cliente", c.getN_cliente());
        contentValues.put("c_razonsocial",c.getC_razonsocial());
        return  contentValues;

    }
    public  Long InsertTMACliente (TMACliente tmaCli){


        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TMA_CLIENTES_NAME, null, TMAClientesContentValues(tmaCli));
        this.CloseDB();
        return rowid;
    }

    public void InsertMasivoClientes(ArrayList<TMACliente> list) {
        Log.i("INICIO INSERT MASIVO","  CLIENTES");
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (int i = 0; i <  list.size(); i++) {
                values = TMAClientesContentValues(list.get(i));
                db.insert(ConstasDB.TMA_CLIENTES_NAME , null , values);
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            Log.i("FIN INSERT MASIVO","  CLIENTES");
        }
    }

    public  void  InsertMasiviDireccionCliente (ArrayList<TMADireccionCli> list){
        Log.i("INICIO INSERT MASIVO","  Direccion Cli");
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (int i = 0; i <  list.size(); i++) {
                values = TMADireccionCliContentValues(list.get(i));
                db.insert(ConstasDB.TMA_DIRECCIONCLI_NAME , null , values);
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            Log.i("FIN INSERT MASIVO"," DIRECCION  CLIENTES");
        }
    }

    public  void  InsertMasivoModelos(ArrayList<TMAModelo> list){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (int i = 0; i <  list.size(); i++) {
                values = TMAModeloContentValues(list.get(i));
                db.insert(ConstasDB.TMA_MODELO_NAME , null , values);
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }



    public  ContentValues TMAFallasContentValues(TMAFalla f){
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_codigo",f.getC_codigo());
        contentValues.put("c_descripcion",f.getC_descripcion());
        return contentValues;
    }

    public  long InsertTMAFallas(TMAFalla fa) {
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TMA_FALLA_NAME, null, TMAFallasContentValues(fa));
        this.CloseDB();
        return rowid;
    }


    public  ContentValues TMAVendedorContentValues (TMAVendedor vendedor){
        ContentValues  contentValues = new ContentValues();
        contentValues.put("c_compania", vendedor.getC_compania());
        contentValues.put("c_vendedor",vendedor.getC_vendedor());
        contentValues.put("c_ciasecundaria",vendedor.getC_ciasecundaria());
        contentValues.put("c_nombres",vendedor.getC_nombres());
        contentValues.put("c_estado",vendedor.getC_estado());
        return  contentValues;
    }
    public  long InsertTMAVendedor(TMAVendedor v) {
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TMA_VENDEDOR_NAME, null, TMAVendedorContentValues(v));
        this.CloseDB();
        return rowid;
    }



    public  ContentValues TMATipoReclamoContenValues(TMATipoReclamo tipoReclamo){
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_tiporeclamo", tipoReclamo.getC_tiporeclamo());
        contentValues.put("c_descripcion",tipoReclamo.getC_descripcion());
        contentValues.put("c_estado",tipoReclamo.getC_estado());
        contentValues.put("c_tipo", tipoReclamo.getC_tipo());
        return contentValues;
    }

    public  long InsertTMATipoReclamo(TMATipoReclamo tipoReclamo){
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TMA_TIPORECLAMO_NAME, null, TMATipoReclamoContenValues(tipoReclamo));
        this.CloseDB();
        return rowid;
    }

    public ContentValues TMAPruebaLabsContentValues (TMAPruebaLab pruebaLab){
        ContentValues contentValues =  new ContentValues();
        contentValues.put("c_codigo",pruebaLab.getC_codigo());
        contentValues.put("c_descripcion", pruebaLab.getC_descripcion());
        contentValues.put("c_tipo",pruebaLab.getC_tipo());
        contentValues.put("c_unidadcodigo",pruebaLab.getC_unidadcodigo());
        return  contentValues;
    }

    public  long InsertTMAPruebasLab(TMAPruebaLab pruebaLab){
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TMA_PRUEBALAB_NAME, null, TMAPruebaLabsContentValues(pruebaLab));
        this.CloseDB();
        return rowid;
    }

    public  ContentValues TMAModeloContentValues(TMAModelo modelo){
        ContentValues contentValues =  new ContentValues();
        contentValues.put("c_marca", modelo.getC_marca());
        contentValues.put("c_modelo",modelo.getC_modelo());
        contentValues.put("c_descripcion",modelo.getC_descripcion());
        contentValues.put("c_estado",modelo.getC_estado());
        return contentValues ;
    }

    public  long InsertTMAModelo (TMAModelo modelo){
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TMA_MODELO_NAME, null, TMAModeloContentValues(modelo));
        this.CloseDB();
        return rowid;
    }

    public  ContentValues TMAMarcaContentValues (TMAMarca marca ){
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_marca", marca.getC_marca());
        contentValues.put("c_descripcion",marca.getC_descripcion());
        contentValues.put("c_estado",marca.getC_estado());
        return contentValues;
    }

    public  long InsertTMAMarcas( TMAMarca marca) {
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TMA_MARCA_NAME, null, TMAMarcaContentValues(marca));
        this.CloseDB();
        return rowid;
    }


    public  ContentValues ReclamoGarantiaContentValues (ReclamoGarantia rg){
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_compania",rg.getC_compania());
        contentValues.put("n_correlativo",rg.getN_correlativo());
        contentValues.put("d_fecha",rg.getD_fecha());
        contentValues.put("n_cliente",rg.getN_cliente());
        contentValues.put("c_codproducto",rg.getC_codproducto());
        contentValues.put("c_lote",rg.getC_lote());
        contentValues.put("c_procedencia",rg.getC_procedencia());
        contentValues.put("n_qtyingreso",rg.getN_qtyingreso());
        contentValues.put("c_vendedor",rg.getC_vendedor());
        contentValues.put("c_estado",rg.getC_estado());
        contentValues.put("c_usuario",rg.getC_usuario());
        contentValues.put("c_lote1",rg.getC_lote1());
        contentValues.put("c_lote2",rg.getC_lote2());
        contentValues.put("c_lote3",rg.getC_lote3());
        contentValues.put("n_cantlote1",rg.getN_cantlote1());
        contentValues.put("n_cantlote2",rg.getN_cantlote2());
        contentValues.put("n_cantlote3",rg.getN_cantlote3());
        contentValues.put("c_codmarca",rg.getC_codmarca());
        contentValues.put("c_codmodelo",rg.getC_codmodelo());
        contentValues.put("n_pyear",rg.getN_pyear());
        contentValues.put("c_tiempouso",rg.getC_tiempouso());
        contentValues.put("c_facturaRef",rg.getC_facturaRef());
        contentValues.put("c_prediagnostico",rg.getC_prediagnostico());
        contentValues.put("n_formato",rg.getN_formato());
        contentValues.put("d_fechaformato",rg.getD_fechaformato());
        contentValues.put("c_obscliente",rg.getC_obscliente());
        contentValues.put("c_flagvisita",rg.getC_flagvisita());
        contentValues.put("c_tiporeclamo",rg.getC_tiporeclamo());
        contentValues.put("c_reembcliente",rg.getC_reembcliente());
        contentValues.put("c_placavehic",rg.getC_placavehic());
        contentValues.put("n_montoreembcli",rg.getN_montoreembcli());
        contentValues.put("c_monedareembcli",rg.getC_monedareembcli());
        contentValues.put("c_pruebalab1",rg.getC_pruebalab1());
        contentValues.put("c_pruebalab2",rg.getC_pruebalab2());
        contentValues.put("c_pruebalab3",rg.getC_pruebalab3());
        contentValues.put("c_ensayo01",rg.getC_ensayo01());
        contentValues.put("c_ensayo02",rg.getC_ensayo02());
        contentValues.put("c_ensayo03",rg.getC_ensayo03());
        contentValues.put("c_ensayo04",rg.getC_ensayo04());
        contentValues.put("c_ensayo05",rg.getC_ensayo05());
        contentValues.put("c_enviado",rg.getC_enviado());
        return  contentValues ;

    }

    public long InsertReclamoGarantia(ReclamoGarantia rg) {
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.MCO_RECLAMO_GARANTIA_NAME, null, ReclamoGarantiaContentValues(rg));
        this.CloseDB();
        return rowid;

    }

    public long UpdateReclamoGarantia( ReclamoGarantia  rg){
        this.OpenWritableDB();
        long rowid = db.update(ConstasDB.MCO_RECLAMO_GARANTIA_NAME, ReclamoGarantiaContentValues(rg),"_id="+rg.getN_correlativo(),null);
        this.CloseDB();
        return rowid;
    }


    public long InsertInspGenDet(InspeccionGenDetalle det) {
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TABLA_MTP_INSPECCIONGENERAL_DET_NAME, null, InspGenDetContentValues(det));
        this.CloseDB();
        return rowid;

    }

    public long InsertInspGenCab(InspeccionGenCabecera insp) {
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TABLA_MTP_INSPECCIONGENERAL_CAB_NAME, null, InspGenCabContentValues(insp));

        this.CloseDB();
        return rowid;


    }

    public long InsertTipoRevision(TipoRevisionGBD tp) {

        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TABLA_MTP_TIPOREVISION_NAME, null, TipoRevisionContentValues(tp));
        this.CloseDB();
        return rowid;
    }

    public  long InsertCentroCosto (CentroCostoDB c){

        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TABLA_MTP_CENTROCOSTO_NAME, null,CentroCostoContentValues(c));
        this.CloseDB();
        return  rowid;
    }

    public  long InsertInspecciomMaqDet (InspeccionMaqDetalle det) {

        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TABLA_MTP_INSPECCIONMAQUINA_DET_NAME,null,InspeccionMaquinaDetValues(det));
        this.CloseDB();
        return  rowid;
    }

    public  long InsertInspeccionMaqCab (InspeccionMaqCabecera inspeccionMaqCabecera){

        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TABLA_MTP_INSPECCIONMAQUINA_CAB_NAME,null,InspeccionMaquinaCabValues(inspeccionMaqCabecera));
        this.CloseDB();
        if (rowid > 0) {
            rowid = Long.valueOf(inspeccionMaqCabecera.getCorrlativo());
        }
        return  rowid;
    }

    public  long InsertInspeccion(InspeccionDB inp){
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TABLA_MTP_INSPECCION_NAME,null,InspeccionesContentValues(inp));
        this.CloseDB();
        return  rowid;
    }

    public  long InjsertPeriodos (PeriodoInspeccionDB periodo){

        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TABLA_MTP_PERIODO_INSPECCION_NAME,null,PeriodosContentValues(periodo));
        this.CloseDB();
        return  rowid;
    }
    public  long InsertMaquina (MaquinaDB mq){
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TABLA_MTP_MAQUINAS_NAME,null,MaquinasContentValues(mq));
        this.CloseDB();
        return  rowid;

    }

    public  long InsertUsuatios (UsuarioDB us){

        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TABLA_MTP_USUARIO_NAME,null,UsuariosContentValues(us));
        this.CloseDB();
        return  rowid;
    }
    public long InsetrtMenus(MenuDB menuDB) {

        this.OpenWritableDB();
        long rowID = db.insert(ConstasDB.TABLA_MTP_MENUS_NAME, null, MenuContentValues(menuDB));
        this.CloseDB();

        return rowID;

    }

    public long InsertAccesos(AccesosDB accesosDB) {

        this.OpenWritableDB();
        long rowID = db.insert(ConstasDB.TABLA_MTP_ACCESO_NAME, null, AccesosContentValues(accesosDB));

        this.CloseDB();
        return rowID;
    }

    public  boolean AutenticarUsuario (String user , String clave ){
        Log.i("Clave y usuario " , user  + " | "+ clave);
         boolean result  = false;
         String query  = "SELECT * FROM  MTP_USUARIO where c_codigousuario = '"+user+"' and c_clave ='"+clave+"'";
          this.OpenWritableDB();
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            result = true;
            Log.i("Paso cursosr " , "") ;
        }

        Log.i("Autenticar Us."+String.valueOf(result),"");

        return  result;
    }


    public  int Correlativo (){
        int res = 0;

        String query = "SELECT MAX(n_correlativo) FROM MTP_INSPECCIONMAQUINA_CAB ";
        this.OpenWritableDB();
        Cursor c = db.rawQuery(query,null);
        if (c != null) {
            while (c.moveToNext()) {
                if (c.getString(0) != null) {
                    String var = c.getString(0);
                    res = Integer.valueOf(var);
                }
            }
        }
        res = res+1;

        return  res;
    }

    public int CorrelativoInspGen() {
        int res = 0;

        String query = "SELECT n_correlativo FROM MTP_INSPECCIONGENERAL_CAB ORDER BY  n_correlativo  desc ";
        this.OpenWritableDB();
        Cursor c = db.query("MTP_INSPECCIONGENERAL_CAB", new String [] {"MAX(n_correlativo)"}, null, null, null, null,null) ;
        if (c != null) {

            if (c.getCount() > 0) {
                c.moveToFirst();
                if (TextUtils.isEmpty(c.getString(0))){
                    res = 0;
                }
                else  {
                    res = Integer.parseInt(c.getString(0));
                }

            }
            else {
                res =  0 ;
            }
        }
        Log.i("correlativo sqlite" , String.valueOf(res));
        res = res + 1;

        return res;
    }


    public  ArrayList<CentroCostoDB> GetCemtroCostos (){

        String query  = "SELECT * FROM MTP_CENTROCOSTO";
        ArrayList<CentroCostoDB> lisCentroC = new ArrayList<CentroCostoDB>();
        this.OpenWritableDB();
        Cursor c = db.rawQuery(query,null);
        while (c.moveToNext()){
            CentroCostoDB costo = new CentroCostoDB();
             costo.setC_compania( c.getString(1));
            costo.setC_centrocosto(c.getString(2));
            costo.setC_descripcion(c.getString(3));
            costo.setC_estado(c.getString(4));
            lisCentroC.add(costo);

        }

        return  lisCentroC;

    }

    public  ArrayList<MaquinaDB> GetMaquinasALL (){

        String query = "SELECT * FROM MTP_MAQUINAS";
        ArrayList<MaquinaDB> lisMaq = new ArrayList<MaquinaDB>();
        this.OpenWritableDB();
        Cursor c = db.rawQuery(query,null);
        while (c.moveToNext()){

            MaquinaDB m = new MaquinaDB();
            m.setC_ompania(c.getString(1));
            m.setC_maquina(c.getString(2));
            m.setC_descripcion(c.getString(3));
            m.setC_codigobarras(c.getString(4));
            m.setC_familiainspeccion(c.getString(5));
            m.setC_centrocosto(c.getString(6));
            m.setC_estado(c.getString(7));
            m.setC_ultimousuario(c.getString(8));
            m.setD_ultimafechamodificacion(c.getString(9));
            lisMaq.add(m);

        }

        return  lisMaq;


    }

    public ArrayList<HistorialInspMaqDB> GetHistorialInspList(String accion, String maq, String cenctroC, String FIni, String FFin) {
        String query = "";
        ArrayList<HistorialInspMaqDB> listResul = new ArrayList<HistorialInspMaqDB>();
        this.OpenWritableDB();
        Cursor c;
        switch (accion) {
            case "1":
                query = "select cab.n_correlativo , substr(cab.d_fechaInicioInspeccion,0,11 ) Fecha, cab.c_maquina, maq.c_centrocosto, per.c_descripcion,cab.c_ultimousuario,cab.c_comentario,cab.c_estado  from MTP_INSPECCIONMAQUINA_CAB cab " +
                        "inner join  MTP_MAQUINAS maq on cab.c_maquina  = maq.c_maquina " +
                        " inner join  MTP_PERIODOINSPECCION per on per.c_periodoinspeccion = cab.c_periodoinspeccion";

                break;
            case "2":
                query = "select cab.n_correlativo , substr(cab.d_fechaInicioInspeccion,0,11 ) Fecha, cab.c_maquina, maq.c_centrocosto, per.c_descripcion,cab.c_ultimousuario,cab.c_comentario,cab.c_estado  from MTP_INSPECCIONMAQUINA_CAB cab " +
                        "inner join  MTP_MAQUINAS maq on cab.c_maquina  = maq.c_maquina " +
                        " inner join  MTP_PERIODOINSPECCION per on per.c_periodoinspeccion = cab.c_periodoinspeccion where cab.c_maquina='" + maq + "'";
                break;
            case "3":
                query = "select cab.n_correlativo , substr(cab.d_fechaInicioInspeccion,0,11 ) Fecha, cab.c_maquina, maq.c_centrocosto, per.c_descripcion,cab.c_ultimousuario,cab.c_comentario,cab.c_estado  from MTP_INSPECCIONMAQUINA_CAB cab " +
                        "inner join  MTP_MAQUINAS maq on cab.c_maquina  = maq.c_maquina " +
                        " inner join  MTP_PERIODOINSPECCION per on per.c_periodoinspeccion = cab.c_periodoinspeccion where cab.c_maquina='" + maq + "'" + " and maq.c_centrocosto='" + cenctroC + "'";
                break;
            case "4":
                query = "select cab.n_correlativo , substr(cab.d_fechaInicioInspeccion,0,11 ) Fecha, cab.c_maquina, maq.c_centrocosto, per.c_descripcion,cab.c_ultimousuario,cab.c_comentario,cab.c_estado    from MTP_INSPECCIONMAQUINA_CAB cab " +
                        "inner join  MTP_MAQUINAS maq on cab.c_maquina  = maq.c_maquina " +
                        "inner join  MTP_PERIODOINSPECCION per on per.c_periodoinspeccion = cab.c_periodoinspeccion " +
                        "where datetime (substr(cab.d_fechaInicioInspeccion,7,4 )||'-'|| substr(cab.d_fechaInicioInspeccion,1,2 )|| '-' ||substr(cab.d_fechaInicioInspeccion,4,2 ) ) between '" + FIni + "' and '" + FFin + "'";
                Log.i("fecha ini global", FIni);
                Log.i("Fecha fin global", FFin);
                break;

        }

        c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            HistorialInspMaqDB h = new HistorialInspMaqDB();
            h.setNumero(c.getString(0));
            h.setFecha(c.getString(1));
            h.setCod_maquina(c.getString(2));
            h.setCentro_costo(c.getString(3));
            h.setFrecuencia(c.getString(4));
            h.setUsuario(c.getString(5));
            h.setComentario(c.getString(6));
            h.setEstado(c.getString(7));
            listResul.add(h);
        }
        return listResul;

    }

    public ArrayList<HistorialInspGenDB> GetHistorialInsGenpList(String accion, String tipoInsp, String fechaIni, String fechFin, String sEstado) {


        String query = "";
        ArrayList<HistorialInspGenDB> list = new ArrayList<HistorialInspGenDB>();
        this.OpenWritableDB();
        Cursor c;
        Log.i("Accion query ", accion);
        switch (accion) {

            case "1":
                query = "SELECT n_correlativo  , case c_tipoinspeccion when   'OT' THEN  'OTROS'  ELSE 'MAQUINA' END TipoIns,c_maquina,c_centrocosto,c_usuarioinspeccion,substr(d_fechainspeccion ,0,11) fecha,c_comentario ,c_estado "
                        + "FROM  MTP_INSPECCIONGENERAL_CAB where c_tipoinspeccion  ='" + tipoInsp + "'"+ " and c_estado like '"+sEstado+"' order by n_correlativo";
                break;
            case "2":
                query = "SELECT n_correlativo  , case c_tipoinspeccion when   'OT' THEN  'OTROS'  ELSE 'MAQUINA' END TipoIns,c_maquina,c_centrocosto,c_usuarioinspeccion,substr(d_fechainspeccion ,0,11) fecha,c_comentario ,c_estado" +
                        " FROM  MTP_INSPECCIONGENERAL_CAB where c_tipoinspeccion  ='" + tipoInsp + "' and   d_fechainspeccion between '" + fechaIni + "'  and '" + fechFin +"'"+ " and c_estado like '"+sEstado+"' order by n_correlativo";
                break;
            case "3":
                query = "SELECT n_correlativo  , case c_tipoinspeccion when   'OT' THEN  'OTROS'  ELSE 'MAQUINA' END TipoIns,c_maquina,c_centrocosto,c_usuarioinspeccion,substr(d_fechainspeccion ,0,11) fecha,c_comentario ,c_estado" +
                        " FROM  MTP_INSPECCIONGENERAL_CAB where  d_fechainspeccion between '" + fechaIni + "'  and '" + fechFin + "'"+ " and c_estado like '"+sEstado+"' order by n_correlativo";
                break;
            case "4":
                query = "SELECT n_correlativo  , case c_tipoinspeccion when   'OT' THEN  'OTROS'  ELSE 'MAQUINA' END TipoIns,c_maquina,c_centrocosto,c_usuarioinspeccion,substr(d_fechainspeccion ,0,11) fecha,c_comentario ,c_estado"  +
                        " FROM  MTP_INSPECCIONGENERAL_CAB " +" where c_estado like '"+sEstado+"' order by n_correlativo";
                break;


        }
        Log.i("query " , query );
        c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            HistorialInspGenDB h = new HistorialInspGenDB();
            h.setNumero(c.getString(0));
            h.setTipoInsp(c.getString(1));
            h.setCodMaq(c.getString(2));
            h.setCodCosto(c.getString(3));
            h.setUsarioInp(c.getString(4));
            h.setFecha(c.getString(5));
            h.setComentario(c.getString(6));
            h.setEstado(c.getString(7));
            list.add(h);

        }

        return list;

    }
    public ArrayList<PeriodoInspeccionDB>  PeriodosInspeccionList (){
        String query = "SELECT * FROM MTP_PERIODOINSPECCION";
        ArrayList<PeriodoInspeccionDB> listResult =  new ArrayList<PeriodoInspeccionDB>();
        this.OpenWritableDB();
        Cursor  c= db.rawQuery(query,null);
        while (c.moveToNext()){
            PeriodoInspeccionDB p = new PeriodoInspeccionDB();
            p.setC_periodoinspeccion(c.getString(1));
            p.setC_descripcion(c.getString(2));
            p.setC_estado(c.getString(3));
            p.setC_ultimousuario(c.getString(4));
            p.setD_ultimafechamodificacion(c.getString(5));

            listResult.add(p);

        }
        return  listResult;
    }



    public ArrayList<SubMenu> GetMenuHijos(String codUser) {

        ArrayList<SubMenu> subMenus = new ArrayList<SubMenu>();
        String query = "SELECT  substr(ma.c_niveles, 1, 2)  as CodPadre, substr(ma.c_niveles, 3, 2)  as CosSubMenu,mn.c_descripcion,'1' estado, c_usuario FROM MTP_ACCESO ma  inner join MTP_MENUS mn on " +
                " CAST(substr(ma.c_niveles, 1, 2) AS INTEGER) = nivel1 and " +
                "CAST(substr(ma.c_niveles, 3, 2) AS INTEGER) = nivel2 and CAST(substr(ma.c_niveles, 5, 2) AS INTEGER) = nivel3 and CAST(substr(ma.c_niveles, 7, 2) AS INTEGER) = nivel4  and CAST(substr(ma.c_niveles, 9, 2) AS INTEGER) = nivel5 " +
                "where  CAST(substr(ma.c_niveles, 5, 2) AS INTEGER) = 0  and  CAST(substr(ma.c_niveles, 1, 2) AS INTEGER) >0 and  CAST(substr(ma.c_niveles, 3, 2) AS INTEGER) >0 ";
        this.OpenWritableDB();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {

            String var_value = cursor.getString(4);
            var_value = var_value.replaceAll("\\s", "");
            if (var_value.equals(codUser)) {

                SubMenu subMenu = new SubMenu(cursor.getString(1), cursor.getString(2), cursor.getString(0), "1");
                subMenus.add(subMenu);
            }
        }

        return subMenus;
    }

    public ArrayList<SubMenuBotones> getSubBotones(String codPadre, String codSubMenu, String codUser) {
        ArrayList<SubMenuBotones> result = new ArrayList<SubMenuBotones>();
        String query = "SELECT  substr(ma.c_niveles, 5, 2)  as CodBoton, substr(ma.c_niveles, 1, 2)  as CodPadre, substr(ma.c_niveles, 3, 2)  as CosSubMenu,mn.c_descripcion,'1' estado, c_usuario " +
                "FROM MTP_ACCESO ma  inner join MTP_MENUS mn on  CAST(substr(ma.c_niveles, 1, 2) AS INTEGER) = nivel1 and CAST(substr(ma.c_niveles, 3, 2) AS INTEGER) = nivel2 and CAST(substr(ma.c_niveles, 5, 2) AS INTEGER) = nivel3 and " +
                "CAST(substr(ma.c_niveles, 7, 2) AS INTEGER) = nivel4  and " +
                "CAST(substr(ma.c_niveles, 9, 2) AS INTEGER) = nivel5 " +
                "where  CAST(substr(ma.c_niveles, 3, 2) AS INTEGER) = " + codSubMenu + " and  CAST(substr(ma.c_niveles, 1, 2) AS INTEGER) = " + codPadre + " and  CAST(substr(ma.c_niveles, 5, 2) AS INTEGER) >0  \n" +
                "and CAST(substr(ma.c_niveles, 7, 2) AS INTEGER) =0 ";
        this.OpenWritableDB();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String var_value = cursor.getString(5);
            var_value = var_value.replaceAll("\\s", "");
            if (codUser.equals(var_value)) {

                SubMenuBotones botones = new SubMenuBotones(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                result.add(botones);
            }

        }

        return result;
    }

    public ArrayList<Menu> GetMenuPadre(String UserName) {

        ArrayList<Menu> result = new ArrayList<Menu>();
        String sqlQuery = "SELECT  substr(ma.c_niveles, 1, 2)  as CodPadre, mn.c_descripcion,mn.AplicacionCodigo, c_usuario FROM MTP_ACCESO ma  inner join MTP_MENUS mn on " +
                " CAST(substr(ma.c_niveles, 1, 2) AS INTEGER) = nivel1 and CAST(substr(ma.c_niveles, 3, 2) AS INTEGER) = nivel2 and " +
                "CAST(substr(ma.c_niveles, 5, 2) AS INTEGER) = nivel3 and CAST(substr(ma.c_niveles, 7, 2) AS INTEGER) = nivel4  and CAST(substr(ma.c_niveles, 9, 2) AS INTEGER) = nivel5  " +
                "where  CAST(substr(ma.c_niveles, 3, 2) AS INTEGER) =0";
        this.OpenWritableDB();
        Cursor cursor = db.rawQuery(sqlQuery, null);
        while (cursor.moveToNext()) {
            String var_value = cursor.getString(3);
            var_value = var_value.replaceAll("\\s", "");
            if (var_value.equals(UserName)) {

                Menu menu = new Menu(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                result.add(menu);

            }

        }

        return result;

    }

    public  String GetNombreClienteXCod(String codCliente){
        String result = "";
        String query = "SELECT * FROM TMA_CLIENTES where n_cliente = "+codCliente;
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            result = (cursor.getString(cursor.getColumnIndex("c_razonsocial")).trim());


        }
        return  result;
    }

    public  MaquinaDB GetMAquinaPorCodigo (String codBarras){
        String query = "SELECT * FROM MTP_MAQUINAS WHERE  C_CODIGOBARRAS ='"+codBarras+"'";
        this.OpenWritableDB();
        ArrayList<MaquinaDB> listMaquina = new ArrayList<MaquinaDB>();
        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext()){
            MaquinaDB m = new MaquinaDB();
            m.setC_ompania(cursor.getString(1));
            m.setC_maquina(cursor.getString(2));
            m.setC_descripcion(cursor.getString(3));
            m.setC_codigobarras(cursor.getString(4));
            m.setC_familiainspeccion(cursor.getString(5));
            m.setC_centrocosto(cursor.getString(6));
            m.setC_estado(cursor.getString(7));
            m.setC_ultimousuario(cursor.getString(8));
            m.setD_ultimafechamodificacion(cursor.getString(9));
            listMaquina.add(m);

        }
       MaquinaDB res=null;

       if(listMaquina.size()>0){
           res = listMaquina.get(0);
       }
        else {

       }

        return  res;

    }

    public String GetDescripcionInspPorCodigo(String codInsp) {
        String query = "select c_descripcion  from MTP_INSPECCION where c_inspeccion = '" + codInsp + "'";
        String result = "";
        this.OpenWritableDB();
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            result = c.getString(0);
        }
        this.CloseDB();
        return result;

    }

    public ArrayList<InspeccionGenDetalle> GetInspeccionGenDetallePorCorrelativo(String correlativo) {
        ArrayList<InspeccionGenDetalle> result = new ArrayList<InspeccionGenDetalle>();
        String query = "SELECT * FROM MTP_INSPECCIONGENERAL_DET where n_correlativo = '" + correlativo + "'";
        this.OpenWritableDB();
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {

            InspeccionGenDetalle d = new InspeccionGenDetalle();
            d.setCompania(c.getString(1));
            d.setCorrelativo(c.getString(2));
            d.setLinea(c.getString(3));
            d.setComentario(c.getString(4));
            d.setRutaFoto(c.getString(5));
            d.setUltUsuario(c.getString(6));
            d.setUltFechaMod(c.getString(7));
            d.setTipoRevision(c.getString(8));
            d.setFlagadictipo(c.getString(9));
            result.add(d);

        }
        this.CloseDB();
        return result;

    }


    public ArrayList<InspeccionMaqDetalle> GetInspeccionMaqDetallePorCorrelativo(String correlativo) {
        ArrayList<InspeccionMaqDetalle> listResult = new ArrayList<InspeccionMaqDetalle>();
        String query = "SELECT * FROM MTP_INSPECCIONMAQUINA_DET   where n_correlativo = '" + correlativo + "'";
        this.OpenWritableDB();
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            InspeccionMaqDetalle det = new InspeccionMaqDetalle();
            det.setCompania(c.getString(1));
            det.setCorrelativo(c.getString(2));
            det.setLinea(c.getString(3));
            det.setCod_inspeccion(c.getString(4));
            det.setTipo_inspecicon(c.getString(5));
            det.setPorcentMin(c.getString(6));
            det.setPorcentMax(c.getString(7));
            det.setPorcentInspec(c.getString(8));
            det.setEstado(c.getString(9));
            det.setComentario(c.getString(10));
            det.setRutaFoto(c.getString(11));
            det.setUltimoUser(c.getString(12));
            det.setUltimaFechaMod(c.getString(13));
            listResult.add(det);

        }
        this.CloseDB();
        return listResult;
    }

    public InspeccionGenCabecera GetInspGenCabeceraPorCorrelativo(String correlativo) {

        InspeccionGenCabecera cab = new InspeccionGenCabecera();
        String query = "SELECT * FROM MTP_INSPECCIONGENERAL_CAB where n_correlativo = '" + correlativo + "'";
        this.OpenWritableDB();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {

            cab.setCompania(cursor.getString(1));
            cab.setCorrelativo(cursor.getString(2));
            cab.setTipoInspeccion(cursor.getString(3));
            cab.setCod_maquina(cursor.getString(4));
            cab.setCentroCosto(cursor.getString(5));
            cab.setComentario(cursor.getString(6));
            cab.setUsuarioInsp(cursor.getString(7));
            cab.setFechaInsp(cursor.getString(8));
            cab.setEstado(cursor.getString(9));
            cab.setUsuarioEnvio(cursor.getString(10));
            cab.setFechaEnvia(cursor.getString(11));
            cab.setUltUsuario(cursor.getString(12));
            cab.setUltFechaMod(cursor.getString(13));

        }
        this.CloseDB();
        return cab;

    }
    public InspeccionMaqCabecera GetInspMaqCabeceraPorCorrelativo(String correlativo) {

        InspeccionMaqCabecera inp = new InspeccionMaqCabecera();
        String query = "SELECT * FROM MTP_INSPECCIONMAQUINA_CAB   where n_correlativo = '" + correlativo + "'";
        this.OpenWritableDB();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            inp.setCompania(cursor.getString(1));
            inp.setCorrlativo(cursor.getString(2));
            inp.setCodMaquina(cursor.getString(3));
            inp.setCondicionMaq(cursor.getString(4));
            inp.setComentario(cursor.getString(5));
            inp.setEstado(cursor.getString(6));
            inp.setFechaInicioInsp(cursor.getString(7));
            inp.setFechFinInsp(cursor.getString(8));
            inp.setPeriodoInsp(cursor.getString(9));
            inp.setUsuarioInsp(cursor.getString(10));
            inp.setUsuarioEnv(cursor.getString(11));
            inp.setFechaEnv(cursor.getString(12));
            inp.setUltUsuairo(cursor.getString(13));
            inp.setUltFechaMod(cursor.getString(14));

        }

        return inp;


    }

    public ArrayList<TipoRevisionGBD> GetAllTipoReivision() {
        String query = "SELECT * FROM MTP_TIPOREVISIONG";
        this.OpenWritableDB();
        ArrayList<TipoRevisionGBD> listTipoRev = new ArrayList<TipoRevisionGBD>();
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            TipoRevisionGBD t = new TipoRevisionGBD();
            t.setCod_tiporevision(c.getString(1));
            t.setDescripcion(c.getString(2));
            t.setEstado(c.getString(3));
            listTipoRev.add(t);
        }
        return listTipoRev;
    }
    public MaquinaDB GetMaquinaPorCodigoMaquina(String codMaquina) {
        String query = "SELECT * FROM MTP_MAQUINAS where c_maquina ='" + codMaquina + "'";
        this.OpenWritableDB();
        ArrayList<MaquinaDB> listMaquina = new ArrayList<MaquinaDB>();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            MaquinaDB m = new MaquinaDB();
            m.setC_ompania(cursor.getString(1));
            m.setC_maquina(cursor.getString(2));
            m.setC_descripcion(cursor.getString(3));
            m.setC_codigobarras(cursor.getString(4));
            m.setC_familiainspeccion(cursor.getString(5));
            m.setC_centrocosto(cursor.getString(6));
            m.setC_estado(cursor.getString(7));
            m.setC_ultimousuario(cursor.getString(8));
            m.setD_ultimafechamodificacion(cursor.getString(9));
            listMaquina.add(m);

        }
        MaquinaDB res = null;

        if (listMaquina.size() > 0) {
            res = listMaquina.get(0);
        } else {

        }

        return res;

    }

    public  ArrayList<InspeccionDB> GetInspecciones(String famInspeccion,String periodoInso){

        ArrayList<InspeccionDB> result = new ArrayList<InspeccionDB>();
        String query = "SELECT * FROM MTP_INSPECCION WHERE c_familiainspeccion='"+famInspeccion+"' and c_periodoinspeccion = '"+periodoInso+"'";
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()){
            InspeccionDB inp = new InspeccionDB();
            inp.setC_inspeccion(cursor.getString(1));
            inp.setC_descripcion(cursor.getString(2));
            inp.setC_tipoinspeccion(cursor.getString(3));
            inp.setN_porcentajeminimo(cursor.getString(4));
            inp.setN_porcentajemaximo(cursor.getString(5));
            inp.setC_familiainspeccion(cursor.getString(6));
            inp.setC_periodoinspeccion(cursor.getString(7));
            inp.setC_estado(cursor.getString(8));
            inp.setC_ultimousuario(cursor.getString(9));
            inp.setD_ultimafechamodificacion(cursor.getString(10));
            result.add(inp);
        }
        return result;
    }
    public  ArrayList<TMATipoReclamo> GetSelectTipoReclamo (){
        ArrayList<TMATipoReclamo> result = new ArrayList<>();
        String query = "SELECT * FROM TMA_TIPORECLAMO where c_estado = 'A'";
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
        TMATipoReclamo  tr = new TMATipoReclamo();
        tr.setC_tiporeclamo(cursor.getString(cursor.getColumnIndex("c_tiporeclamo")));
            tr.setC_descripcion(cursor.getString(cursor.getColumnIndex("c_descripcion")));
            tr.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));
            tr.setC_tipo(cursor.getString(cursor.getColumnIndex("c_tipo")));
            result.add(tr);

        }
        return  result ;
    }

    public  ArrayList<TMAMarca> GetSelectMarca (){
        ArrayList<TMAMarca> result = new ArrayList<>();
        String query = "SELECT * FROM TMA_MARCA where c_estado = 'A' ORDER BY c_descripcion";
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            TMAMarca  tr = new TMAMarca();
            tr.setC_marca(cursor.getString(cursor.getColumnIndex("c_marca")));
            tr.setC_descripcion(cursor.getString(cursor.getColumnIndex("c_descripcion")));
            tr.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));

            result.add(tr);

        }
        return  result ;
    }

    public  ArrayList<TMAModelo> GetSelectModelo (String sMarca){
        ArrayList<TMAModelo> result = new ArrayList<>();
        String query = "SELECT * FROM TMA_MODELO WHERE c_estado = 'A' and c_marca = '"+sMarca+"'";
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            TMAModelo  tr = new TMAModelo();
            tr.setC_marca(cursor.getString(cursor.getColumnIndex("c_marca")));
            tr.setC_descripcion(cursor.getString(cursor.getColumnIndex("c_descripcion")));
            tr.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));
            tr.setC_modelo(cursor.getString(cursor.getColumnIndex("c_modelo")));

            result.add(tr);

        }
        return  result ;
    }

    public  ArrayList<TMAPruebaLab> GetSelectPruebasLab (){
        ArrayList<TMAPruebaLab> result = new ArrayList<>();
        String query = "SELECT * FROM TMA_PRUEBALAB ";
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            TMAPruebaLab  p = new TMAPruebaLab();
            p.setC_codigo(cursor.getString(cursor.getColumnIndex("c_codigo")).trim());
            p.setC_descripcion(cursor.getString(cursor.getColumnIndex("c_descripcion")));
            p.setC_tipo(cursor.getString(cursor.getColumnIndex("c_tipo")));
            p.setC_unidadcodigo(cursor.getString(cursor.getColumnIndex("c_unidadcodigo")));
            result.add(p);

        }
        return  result ;
    }

    public  ArrayList<TMAFalla> GetSelectFallas (){
        ArrayList<TMAFalla> result = new ArrayList<>();
        String query = "SELECT * FROM TMA_FALLA ";
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            TMAFalla  f = new TMAFalla();
            f.setC_codigo(cursor.getString(cursor.getColumnIndex("c_codigo")).trim());
            f.setC_descripcion(cursor.getString(cursor.getColumnIndex("c_descripcion")));

            result.add(f);

        }
        return  result ;
    }


    public  ArrayList<TMAVendedor> GetSelectVendedor (){
        ArrayList<TMAVendedor> result = new ArrayList<>();
        String query = "SELECT * FROM TMA_VENDEDOR where c_estado = 'A' ";
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            TMAVendedor  v = new TMAVendedor();
            v.setC_vendedor(cursor.getString(cursor.getColumnIndex("c_vendedor")).trim());
            v.setC_nombres(cursor.getString(cursor.getColumnIndex("c_nombres")));
            v.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));
            v.setC_compania(cursor.getString(cursor.getColumnIndex("c_compania")));
            v.setC_ciasecundaria(cursor.getString(cursor.getColumnIndex("c_ciasecundaria")));
            result.add(v);

        }
        return  result ;
    }


    public  ArrayList<TMACliente> GetSelectClientes (String sFilterText)
    {
        ArrayList<TMACliente> listaclientes = new ArrayList<>();
        String query = "select c_compania, n_cliente, c_razonsocial FROM  TMA_CLIENTES ";
        if (!sFilterText.equals("%")){
            query = query + " WHERE c_razonsocial like '%"+sFilterText+"%' or n_cliente like '%"+sFilterText+"%'" ;
        }
        Log.i("queery clientes"  , query);
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()){
            TMACliente c = new TMACliente();
            c.setC_compania(cursor.getString(cursor.getColumnIndex("c_compania")));
            c.setN_cliente(cursor.getInt(cursor.getColumnIndex("n_cliente")));
            c.setC_razonsocial(cursor.getString(cursor.getColumnIndex("c_razonsocial")));
            listaclientes.add(c);

        }
        return  listaclientes;
    }

    /************************************************/
    /****************REGISTRO DE QUEJAS**************/
    /************************************************/
    //Insert Calificacion
    public ContentValues TMACalifQJContentValues(TMACalificacionQueja oEnt){
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_calificacion", oEnt.getC_calificacion());
        contentValues.put("c_descripcion",oEnt.getC_descripcion());
        contentValues.put("c_usuarioderivacion",oEnt.getC_usuarioderivacion());
        contentValues.put("c_correo",oEnt.getC_correo());
        contentValues.put("c_estado",oEnt.getC_estado());
        return contentValues ;
    }

    public long InsertTMACalificacionQJ(TMACalificacionQueja oEnt){
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TMA_CALIFICACIONQUEJA_NAME, null, TMACalifQJContentValues(oEnt));
        this.CloseDB();
        return rowid;
    }

    //Insert Tipo Calificacion
    public ContentValues TMATipoCalifQJContentValues(TMATipoCalificacionQueja oEnt){
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_tipocalificacion", oEnt.getC_tipocalificacion());
        contentValues.put("c_calificacion", oEnt.getC_calificacion());
        contentValues.put("c_descripcion",oEnt.getC_descripcion());
        contentValues.put("c_estado",oEnt.getC_estado());
        return contentValues ;
    }

    public long InsertTMATipoCalificacionQJ(TMATipoCalificacionQueja oEnt){
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TMA_TIPOCALIFICACIONQUEJA_NAME, null, TMATipoCalifQJContentValues(oEnt));
        this.CloseDB();
        return rowid;
    }

    //Insert Medio Recepcion
    public ContentValues TMAMedioRecQJContentValues(TMAMedioRecepcion oEnt){
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_mediorecepcion", oEnt.getC_mediorecepcion());
        contentValues.put("c_descripcion",oEnt.getC_descripcion());
        contentValues.put("c_estado",oEnt.getC_estado());
        return contentValues ;
    }

    public long InsertTMAMedioRecQJ(TMAMedioRecepcion oEnt){
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TMA_MEDIORECEPCION_NAME, null, TMAMedioRecQJContentValues(oEnt));
        this.CloseDB();
        return rowid;
    }

    //Insert Acciones Tomar
    public ContentValues TMAAccionTomarQJContentValues(TMAAccionesTomar oEnt){
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_codaccion", oEnt.getC_codaccion());
        contentValues.put("c_descripcion",oEnt.getC_descripcion());
        contentValues.put("c_estado",oEnt.getC_estado());
        return contentValues ;
    }

    public long InsertTMAAccionTomarQJ(TMAAccionesTomar oEnt){
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TMA_ACCIONESTOMAR_NAME, null, TMAAccionTomarQJContentValues(oEnt));
        this.CloseDB();
        return rowid;
    }

    //Insert Notificacion Queja
    public ContentValues TMANotificacionQJContentValues(TMANotificacionQueja oEnt){
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_notificacion", oEnt.getC_notificacion());
        contentValues.put("c_descripcion",oEnt.getC_descripcion());
        contentValues.put("c_envianot",oEnt.getC_envianot());
        contentValues.put("c_usuarionot",oEnt.getC_usuarionot());
        contentValues.put("c_estado",oEnt.getC_estado());
        return contentValues ;
    }

    public long InsertTMANotificacionQJ(TMANotificacionQueja oEnt){
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TMA_NOTIFICAQUEJA_NAME, null, TMANotificacionQJContentValues(oEnt));
        this.CloseDB();
        return rowid;
    }

    //Extraer Clase
    public ArrayList<TMACalificacionQueja> GetClase(){
        ArrayList<TMACalificacionQueja> oData = new ArrayList<>();
        String query = "SELECT * FROM TMA_CALIFICACIONQUEJA where c_estado = 'A' ORDER BY c_descripcion";
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            TMACalificacionQueja oEnt = new TMACalificacionQueja();
            oEnt.setC_calificacion(cursor.getString(cursor.getColumnIndex("c_calificacion")));
            oEnt.setC_descripcion(cursor.getString(cursor.getColumnIndex("c_descripcion")));
            oEnt.setC_usuarioderivacion(cursor.getString(cursor.getColumnIndex("c_usuarioderivacion")));
            oEnt.setC_correo(cursor.getString(cursor.getColumnIndex("c_correo")));
            oEnt.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));
            oData.add(oEnt);
        }
        return oData;
    }

    //Estraer SubClase
    public ArrayList<TMATipoCalificacionQueja> GetSubClase(String sClase){
        ArrayList<TMATipoCalificacionQueja> oData = new ArrayList<>();
        String query = "SELECT * FROM TMA_TIPOCALIFICACIONQUEJA WHERE c_estado = 'A' and c_calificacion = '"+sClase+"'";
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            TMATipoCalificacionQueja oEnt = new TMATipoCalificacionQueja();
            oEnt.setC_tipocalificacion(cursor.getString(cursor.getColumnIndex("c_tipocalificacion")));
            oEnt.setC_calificacion(cursor.getString(cursor.getColumnIndex("c_calificacion")));
            oEnt.setC_descripcion(cursor.getString(cursor.getColumnIndex("c_descripcion")));
            oEnt.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));
            oData.add(oEnt);
        }
        return oData;
    }

    //Extraer Medio Recepcion
    public ArrayList<TMAMedioRecepcion> GetMedioRecepcion(){
        ArrayList<TMAMedioRecepcion> oData = new ArrayList<>();
        String query = "SELECT * FROM TMA_MEDIORECEPCION where c_estado = 'A' ORDER BY c_descripcion";
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            TMAMedioRecepcion oEnt = new TMAMedioRecepcion();
            oEnt.setC_mediorecepcion(cursor.getString(cursor.getColumnIndex("c_mediorecepcion")));
            oEnt.setC_descripcion(cursor.getString(cursor.getColumnIndex("c_descripcion")));
            oEnt.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));
            oData.add(oEnt);
        }
        return oData;
    }

    public  String MedioRecepcionDesc (String sCod){
       String result ="";
        String query = "SELECT c_descripcion FROM TMA_MEDIORECEPCION where c_mediorecepcion ='"+sCod+"'";
        Log.i("query > " ,query );
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            TMAMedioRecepcion oEnt = new TMAMedioRecepcion();
            result = (cursor.getString(cursor.getColumnIndex("c_descripcion")));
        }
        return result;
    }

    //Extraer Acciones Tomar
    public ArrayList<TMAAccionesTomar> GetAccionesTomar(){
        ArrayList<TMAAccionesTomar> oData = new ArrayList<>();
        String query = "SELECT * FROM TMA_ACCIONESTOMAR where c_estado = 'A' ORDER BY c_descripcion";
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            TMAAccionesTomar oEnt = new TMAAccionesTomar();
            oEnt.setC_codaccion(cursor.getString(cursor.getColumnIndex("c_codaccion")));
            oEnt.setC_descripcion(cursor.getString(cursor.getColumnIndex("c_descripcion")));
            oEnt.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));
            oData.add(oEnt);
        }
        return oData;
    }

    //Extraer Notificacion
    public ArrayList<TMANotificacionQueja> GetNotificacion(){
        ArrayList<TMANotificacionQueja> oData = new ArrayList<>();
        String query = "SELECT * FROM TMA_NOTIFICAQUEJA where c_estado = 'A' ORDER BY c_descripcion";
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            TMANotificacionQueja oEnt = new TMANotificacionQueja();
            oEnt.setC_notificacion(cursor.getString(cursor.getColumnIndex("c_notificacion")));
            oEnt.setC_descripcion(cursor.getString(cursor.getColumnIndex("c_descripcion")));
            oEnt.setC_envianot(cursor.getString(cursor.getColumnIndex("c_envianot")));
            oEnt.setC_usuarionot(cursor.getString(cursor.getColumnIndex("c_usuarionot")));
            oEnt.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));
            oData.add(oEnt);
        }
        return oData;
    }

    //Insert QUEJA CLIENTE
    public ContentValues QuejaClienteContentValues (QuejaCliente qj){
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_compania",qj.getC_compania());
        contentValues.put("n_correlativo",qj.getN_correlativo());
        contentValues.put("c_queja","S_" + String.valueOf(qj.getN_correlativo()));
        contentValues.put("c_nroformato",qj.getC_nroformato());
        contentValues.put("n_cliente",qj.getN_cliente());
        contentValues.put("d_fechareg",qj.getD_fechareg());
        contentValues.put("c_documentoref",qj.getC_documentoref());
        contentValues.put("c_mediorecepcion",qj.getC_mediorecepcion());
        contentValues.put("c_calificacion",qj.getC_calificacion());
        contentValues.put("c_tipocalificacion",qj.getC_tipocalificacion());
        contentValues.put("c_item",qj.getC_item());
        contentValues.put("c_lote",qj.getC_lote());
        contentValues.put("n_cantidad",qj.getN_cantidad());
        contentValues.put("c_desqueja",qj.getC_desqueja());
        contentValues.put("c_observaciones",qj.getC_observaciones());
        contentValues.put("c_usuario",qj.getC_usuario());
        contentValues.put("c_estado",qj.getC_estado());
        contentValues.put("c_enviado",qj.getC_enviado());
        return contentValues ;
    }

    public long UpdareQuejaCliente( QuejaCliente  qj){
        this.OpenWritableDB();
        long rowid = db.update(ConstasDB.MCO_QUEJA_CLIENTE_NAME, QuejaClienteContentValues(qj),"_id="+qj.getN_correlativo(),null);
        this.CloseDB();
        return rowid;
    }

    public long InsertQuejaCliente(QuejaCliente qu) {
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.MCO_QUEJA_CLIENTE_NAME, null, QuejaClienteContentValues(qu));
        this.CloseDB();
        return rowid;
    }

    public ArrayList<QuejaCliente> GetListQuejaCliente (String sComp,String sCliente,String sEstado,String sFechaIni,String sFechaFin){
        ArrayList<QuejaCliente> result = new ArrayList<>();
        String query = "SELECT * FROM MCO_QUEJAS " +
                "where  d_fechareg >=  '"+sFechaIni+" 00:00:00'  and d_fechareg <= '"+sFechaFin+" 23:59:59' " +
                "and cast(n_cliente as text) like '"+sCliente+"' and c_estado like '"+sEstado+"' and c_compania  like '"+sComp+"' and c_enviado='N';";
        Log.i("query quejas " ,query);
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            QuejaCliente oEnt = new QuejaCliente();
            oEnt.setC_compania(cursor.getString(cursor.getColumnIndex("c_compania")));
            oEnt.setN_correlativo(cursor.getInt(cursor.getColumnIndex("_id")));
            oEnt.setC_queja(cursor.getString(cursor.getColumnIndex("c_queja")));
            oEnt.setC_nroformato(cursor.getString(cursor.getColumnIndex("c_nroformato")));
            oEnt.setN_cliente(cursor.getInt(cursor.getColumnIndex("n_cliente")));
            oEnt.setD_fechareg(cursor.getString(cursor.getColumnIndex("d_fechareg")));
            oEnt.setC_documentoref(cursor.getString(cursor.getColumnIndex("c_documentoref")));
            oEnt.setC_mediorecepcion(cursor.getString(cursor.getColumnIndex("c_mediorecepcion")));
            oEnt.setC_centrocosto(cursor.getString(cursor.getColumnIndex("c_centrocosto")));
            oEnt.setC_calificacion(cursor.getString(cursor.getColumnIndex("c_calificacion")));
            oEnt.setC_usuarioderiv(cursor.getString(cursor.getColumnIndex("c_usuarioderiv")));
            oEnt.setC_tipocalificacion(cursor.getString(cursor.getColumnIndex("c_tipocalificacion")));
            oEnt.setC_item(cursor.getString(cursor.getColumnIndex("c_item")));
            oEnt.setC_lote(cursor.getString(cursor.getColumnIndex("c_lote")));
            oEnt.setN_cantidad(cursor.getDouble(cursor.getColumnIndex("n_cantidad")));
            oEnt.setC_desqueja(cursor.getString(cursor.getColumnIndex("c_desqueja")));
            oEnt.setC_observaciones(cursor.getString(cursor.getColumnIndex("c_observaciones")));
            oEnt.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));
            oEnt.setC_usuario(cursor.getString(cursor.getColumnIndex("c_usuario")));
            oEnt.setC_enviado(cursor.getString(cursor.getColumnIndex("c_enviado")));
            oEnt.setC_estadofin("PI");
            result.add(oEnt);
        }
        return result;
    }

    public QuejaCliente GetQuejaClienteItem (String sCorrrelativo){
        QuejaCliente oEnt = new QuejaCliente();
        String query = "SELECT * FROM MCO_QUEJAS  where _id="+sCorrrelativo;
        Log.i("query quejas " ,query);
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {

            oEnt.setC_compania(cursor.getString(cursor.getColumnIndex("c_compania")));
            oEnt.setN_correlativo(cursor.getInt(cursor.getColumnIndex("_id")));
            oEnt.setC_queja(cursor.getString(cursor.getColumnIndex("c_queja")));
            oEnt.setC_nroformato(cursor.getString(cursor.getColumnIndex("c_nroformato")));
            oEnt.setN_cliente(cursor.getInt(cursor.getColumnIndex("n_cliente")));
            oEnt.setD_fechareg(cursor.getString(cursor.getColumnIndex("d_fechareg")));
            oEnt.setC_documentoref(cursor.getString(cursor.getColumnIndex("c_documentoref")));
            oEnt.setC_mediorecepcion(cursor.getString(cursor.getColumnIndex("c_mediorecepcion")));
            oEnt.setC_centrocosto(cursor.getString(cursor.getColumnIndex("c_centrocosto")));
            oEnt.setC_calificacion(cursor.getString(cursor.getColumnIndex("c_calificacion")));
            oEnt.setC_usuarioderiv(cursor.getString(cursor.getColumnIndex("c_usuarioderiv")));
            oEnt.setC_tipocalificacion(cursor.getString(cursor.getColumnIndex("c_tipocalificacion")));
            oEnt.setC_item(cursor.getString(cursor.getColumnIndex("c_item")));
            oEnt.setC_lote(cursor.getString(cursor.getColumnIndex("c_lote")));
            oEnt.setN_cantidad(cursor.getDouble(cursor.getColumnIndex("n_cantidad")));
            oEnt.setC_desqueja(cursor.getString(cursor.getColumnIndex("c_desqueja")));
            oEnt.setC_observaciones(cursor.getString(cursor.getColumnIndex("c_observaciones")));
            oEnt.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));
            oEnt.setC_usuario(cursor.getString(cursor.getColumnIndex("c_usuario")));
            oEnt.setC_enviado(cursor.getString(cursor.getColumnIndex("c_enviado")));
        }
        return oEnt;
    }
    /************************************************/
    /************************************************/
    /************************************************/


    public  ArrayList<ReclamoGarantia> GetListReclamosGar (String sComp,String sCliente , String sEstado,String sFechaIni , String sFechaFin ){
        ArrayList<ReclamoGarantia> result = new ArrayList<>();
        String query = "SELECT  * FROM MCO_RECLAMOGARANTIA " +
                        "where   d_fechaformato >=  '"+sFechaIni+"'  and d_fechaformato <= '"+sFechaFin+"' " +
                        "and cast(n_cliente as text) like '"+sCliente+"' and c_estado like '"+sEstado+"' and c_compania  like '"+sComp+"' and c_enviado='N';";
        Log.i("query reclamo garantia " ,query);
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            ReclamoGarantia  r = new ReclamoGarantia();
            r.setC_compania(cursor.getString(cursor.getColumnIndex("c_compania")));
            r.setN_correlativo(cursor.getInt(cursor.getColumnIndex("_id")));
            r.setD_fecha(cursor.getString(cursor.getColumnIndex("d_fecha")));
            r.setN_cliente(cursor.getInt(cursor.getColumnIndex("n_cliente")));
            r.setC_codproducto(cursor.getString(cursor.getColumnIndex("c_codproducto")));
            r.setC_lote(cursor.getString(cursor.getColumnIndex("c_lote")));
            r.setC_procedencia(cursor.getString(cursor.getColumnIndex("c_procedencia")));
            r.setN_qtyingreso(cursor.getDouble(cursor.getColumnIndex("n_qtyingreso")));
            r.setC_vendedor(cursor.getString(cursor.getColumnIndex("c_vendedor")));
            r.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));
            r.setC_usuario(cursor.getString(cursor.getColumnIndex("c_usuario")));
            r.setC_lote1(cursor.getString(cursor.getColumnIndex("c_lote1")));
            r.setC_lote2(cursor.getString(cursor.getColumnIndex("c_lote2")));
            r.setC_lote3(cursor.getString(cursor.getColumnIndex("c_lote3")));
            r.setN_cantlote1(cursor.getDouble(cursor.getColumnIndex("n_cantlote1")));
            r.setN_cantlote2(cursor.getDouble(cursor.getColumnIndex("n_cantlote2")));
            r.setN_cantlote3(cursor.getDouble(cursor.getColumnIndex("n_cantlote3")));
            r.setC_codmarca(cursor.getString(cursor.getColumnIndex("c_codmarca")));
            r.setC_codmodelo(cursor.getString(cursor.getColumnIndex("c_codmodelo")));
            r.setN_pyear(cursor.getInt(cursor.getColumnIndex("n_pyear")));
            r.setC_tiempouso(cursor.getString(cursor.getColumnIndex("c_tiempouso")));
            r.setC_facturaRef(cursor.getString(cursor.getColumnIndex("c_facturaRef")));
            r.setC_prediagnostico(cursor.getString(cursor.getColumnIndex("c_prediagnostico")));
            r.setN_formato(cursor.getInt(cursor.getColumnIndex("n_formato")));
            r.setD_fechaformato(cursor.getString(cursor.getColumnIndex("d_fechaformato")));
            r.setC_obscliente(cursor.getString(cursor.getColumnIndex("c_obscliente")));
            r.setC_flagvisita(cursor.getString(cursor.getColumnIndex("c_flagvisita")));
            r.setC_tiporeclamo(cursor.getString(cursor.getColumnIndex("c_tiporeclamo")));
            r.setC_reembcliente(cursor.getString(cursor.getColumnIndex("c_reembcliente")));
            r.setC_placavehic(cursor.getString(cursor.getColumnIndex("c_placavehic")));
            r.setN_montoreembcli(cursor.getDouble(cursor.getColumnIndex("n_montoreembcli")));
            r.setC_monedareembcli(cursor.getString(cursor.getColumnIndex("c_monedareembcli")));
            r.setC_pruebalab1(cursor.getString(cursor.getColumnIndex("c_pruebalab1")));
            r.setC_pruebalab2(cursor.getString(cursor.getColumnIndex("c_pruebalab2")));
            r.setC_pruebalab3(cursor.getString(cursor.getColumnIndex("c_pruebalab3")));
            r.setC_ensayo01(cursor.getString(cursor.getColumnIndex("c_ensayo01")));
            r.setC_ensayo02(cursor.getString(cursor.getColumnIndex("c_ensayo02")));
            r.setC_ensayo03(cursor.getString(cursor.getColumnIndex("c_ensayo03")));
            r.setC_ensayo04(cursor.getString(cursor.getColumnIndex("c_ensayo04")));
            r.setC_ensayo05(cursor.getString(cursor.getColumnIndex("c_ensayo05")));
            r.setC_enviado(cursor.getString(cursor.getColumnIndex("c_enviado")));

            result.add(r);

        }
        return  result ;
    }

    public  ReclamoGarantia GetReclamoGarantiaItem (String sId ){
        ReclamoGarantia r = new ReclamoGarantia();
        String query = "SELECT  * FROM MCO_RECLAMOGARANTIA where _id="+sId;
        Log.i("query reclamo garantia " ,query);
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            r.setC_compania(cursor.getString(cursor.getColumnIndex("c_compania")));
            r.setN_correlativo(cursor.getInt(cursor.getColumnIndex("_id")));
            r.setD_fecha(cursor.getString(cursor.getColumnIndex("d_fecha")));
            r.setN_cliente(cursor.getInt(cursor.getColumnIndex("n_cliente")));
            r.setC_codproducto(cursor.getString(cursor.getColumnIndex("c_codproducto")));
            r.setC_lote(cursor.getString(cursor.getColumnIndex("c_lote")));
            r.setC_procedencia(cursor.getString(cursor.getColumnIndex("c_procedencia")));
            r.setN_qtyingreso(cursor.getDouble(cursor.getColumnIndex("n_qtyingreso")));
            r.setC_vendedor(cursor.getString(cursor.getColumnIndex("c_vendedor")));
            r.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));
            r.setC_usuario(cursor.getString(cursor.getColumnIndex("c_usuario")));
            r.setC_lote1(cursor.getString(cursor.getColumnIndex("c_lote1")));
            r.setC_lote2(cursor.getString(cursor.getColumnIndex("c_lote2")));
            r.setC_lote3(cursor.getString(cursor.getColumnIndex("c_lote3")));
            r.setN_cantlote1(cursor.getDouble(cursor.getColumnIndex("n_cantlote1")));
            r.setN_cantlote2(cursor.getDouble(cursor.getColumnIndex("n_cantlote2")));
            r.setN_cantlote3(cursor.getDouble(cursor.getColumnIndex("n_cantlote3")));
            r.setC_codmarca(cursor.getString(cursor.getColumnIndex("c_codmarca")));
            r.setC_codmodelo(cursor.getString(cursor.getColumnIndex("c_codmodelo")));
            r.setN_pyear(cursor.getInt(cursor.getColumnIndex("n_pyear")));
            r.setC_tiempouso(cursor.getString(cursor.getColumnIndex("c_tiempouso")));
            r.setC_facturaRef(cursor.getString(cursor.getColumnIndex("c_facturaRef")));
            r.setC_prediagnostico(cursor.getString(cursor.getColumnIndex("c_prediagnostico")));
            r.setN_formato(cursor.getInt(cursor.getColumnIndex("n_formato")));
            r.setD_fechaformato(cursor.getString(cursor.getColumnIndex("d_fechaformato")));
            r.setC_obscliente(cursor.getString(cursor.getColumnIndex("c_obscliente")));
            r.setC_flagvisita(cursor.getString(cursor.getColumnIndex("c_flagvisita")));
            r.setC_tiporeclamo(cursor.getString(cursor.getColumnIndex("c_tiporeclamo")));
            r.setC_reembcliente(cursor.getString(cursor.getColumnIndex("c_reembcliente")));
            r.setC_placavehic(cursor.getString(cursor.getColumnIndex("c_placavehic")));
            r.setN_montoreembcli(cursor.getDouble(cursor.getColumnIndex("n_montoreembcli")));
            r.setC_monedareembcli(cursor.getString(cursor.getColumnIndex("c_monedareembcli")));
            r.setC_pruebalab1(cursor.getString(cursor.getColumnIndex("c_pruebalab1")));
            r.setC_pruebalab2(cursor.getString(cursor.getColumnIndex("c_pruebalab2")));
            r.setC_pruebalab3(cursor.getString(cursor.getColumnIndex("c_pruebalab3")));
            r.setC_ensayo01(cursor.getString(cursor.getColumnIndex("c_ensayo01")));
            r.setC_ensayo02(cursor.getString(cursor.getColumnIndex("c_ensayo02")));
            r.setC_ensayo03(cursor.getString(cursor.getColumnIndex("c_ensayo03")));
            r.setC_ensayo04(cursor.getString(cursor.getColumnIndex("c_ensayo04")));
            r.setC_ensayo05(cursor.getString(cursor.getColumnIndex("c_ensayo05")));
            r.setC_enviado(cursor.getString(cursor.getColumnIndex("c_enviado")));


        }
        return  r ;
    }


    public ArrayList<Permisos> GetPermisos(String codigoUser, String nivel) {

        ArrayList<Permisos> result = new ArrayList<Permisos>();
        String query = "SELECT  upper(ma.c_niveles)  as CodigoNivel, ma.c_acceso ValorEstado ,upper(mn.c_descripcion) Descripcion,c_usuario FROM MTP_ACCESO " +
                "ma  inner join MTP_MENUS mn on  CAST(substr(ma.c_niveles, 1, 2) AS INTEGER) = nivel1 and CAST(substr(ma.c_niveles, 3, 2) AS INTEGER) = nivel2 and CAST(substr(ma.c_niveles, 5, 2) AS INTEGER) = nivel3 and " +
                "CAST(substr(ma.c_niveles, 7, 2) AS INTEGER) = nivel4  and CAST(substr(ma.c_niveles, 9, 2) AS INTEGER) = nivel5 " +
                " where  CAST(substr(ma.c_niveles, 7, 2) AS INTEGER)  >0  and CAST(substr(ma.c_niveles, 5, 2) AS INTEGER) >0 and CAST(substr(ma.c_niveles, 9, 2) AS INTEGER) = 0";

        this.OpenWritableDB();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            String var_value = cursor.getString(3);
            var_value = var_value.replaceAll("\\s", "");
            String var_codNivel = cursor.getString(0);
            var_codNivel = var_codNivel.substring(0, 6);
            if (var_value.equals(codigoUser) && var_codNivel.equals(nivel)) {
                Permisos permiso = new Permisos();
                permiso.setCodigoNivel(cursor.getString(0));
                permiso.setDescripcion(cursor.getString(2));
                permiso.setValor(cursor.getString(1));
                result.add(permiso);
            }
        }

        return result;

    }
    /************************************************/
    /**************REGISTRO DE SUGERENCIA************/
    /************************************************/
    //Insert Tipo Sugerencia
    public ContentValues TMATipoSugerenciaContentValues(TMATipoSugerencia oEnt){
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_tiposugerencia", oEnt.getC_tiposugerencia());
        contentValues.put("c_descripcion",oEnt.getC_descripcion());
        contentValues.put("c_estado",oEnt.getC_estado());
        return contentValues ;
    }

    public long InsertTMATipoSugerencia(TMATipoSugerencia oEnt){
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TMA_TIPOSUGERENCIA_NAME, null, TMATipoSugerenciaContentValues(oEnt));
        this.CloseDB();
        return rowid;
    }


    //Extraer Tipo Sugerencia
    public ArrayList<TMATipoSugerencia> GetTipoSugerencia(){
        ArrayList<TMATipoSugerencia> oData = new ArrayList<>();
        String query = "SELECT * FROM TMA_TIPOSUGERENCIA where c_estado = 'A'";
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            TMATipoSugerencia oEnt = new TMATipoSugerencia();
            oEnt.setC_tiposugerencia(cursor.getString(cursor.getColumnIndex("c_tiposugerencia")));
            oEnt.setC_descripcion(cursor.getString(cursor.getColumnIndex("c_descripcion")));
            oEnt.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));
            oData.add(oEnt);
        }
        return oData;
    }

    public String GetNombreTipoSugerencia(String codSugerencia){
        String result = "";
        String query = "SELECT * FROM TMA_TIPOSUGERENCIA where c_tiposugerencia = '" + codSugerencia + "'";
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            result = (cursor.getString(cursor.getColumnIndex("c_descripcion")).trim());
        }
        return  result;
    }

    //Insert SUGERENCIA CLIENTE
    public ContentValues SugerenciaClienteContentValues (SugerenciaCliente oEnt){
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_compania",oEnt.getC_compania());
        contentValues.put("n_correlativo",oEnt.getN_correlativo());
        contentValues.put("n_identinfo",oEnt.getN_identinfo());
        contentValues.put("c_tipoinfo",oEnt.getC_tipoinfo());
        contentValues.put("n_cliente",oEnt.getN_cliente());
        contentValues.put("d_fechareg",oEnt.getD_fechareg());
        contentValues.put("c_tiposug",oEnt.getC_tiposug());
        contentValues.put("c_descripcion",oEnt.getC_descripcion());
        contentValues.put("c_observacion",oEnt.getC_observacion());
        contentValues.put("c_usuario",oEnt.getC_usuarioreg());
        contentValues.put("c_estado",oEnt.getC_estado());
        contentValues.put("c_enviado",oEnt.getC_enviado());
        return contentValues ;
    }

    public long InsertSugerenciaCliente(SugerenciaCliente oEnt) {
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.MCO_SUGERENCIA_NAME, null, SugerenciaClienteContentValues(oEnt));
        this.CloseDB();
        return rowid;
    }

    public  long UpdateSugerenciaCliente (SugerenciaCliente oEnt){
        this.OpenWritableDB();
        long rowid = db.update(ConstasDB.MCO_SUGERENCIA_NAME, SugerenciaClienteContentValues(oEnt),"_id="+oEnt.getN_correlativo(),null);
        this.CloseDB();
        return rowid;
    }

    public ArrayList<SugerenciaCliente> GetListSugerenciaCliente (String sComp,String sCliente,String sEstado,String sFechaIni,String sFechaFin, String sTipoInfo){
        ArrayList<SugerenciaCliente> result = new ArrayList<>();
        String query = "SELECT * FROM MCO_SUGERENCIA " +
                "where d_fechareg >=  '"+sFechaIni+" 00:00:00'  and d_fechareg <= '"+sFechaFin+" 23:59:59' " +
                "and cast(n_cliente as text) like '"+sCliente+"' and c_estado like '"+sEstado+"' and c_compania  like '"+sComp+"'  " +
                "and c_tipoinfo ='" + sTipoInfo + "' and c_enviado='N';";
        Log.i("query quejas " ,query);
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            SugerenciaCliente oEnt = new SugerenciaCliente();
            oEnt.setC_compania(cursor.getString(cursor.getColumnIndex("c_compania")));
            oEnt.setN_correlativo(cursor.getInt(cursor.getColumnIndex("_id")));
            oEnt.setN_cliente(cursor.getInt(cursor.getColumnIndex("n_cliente")));
            oEnt.setD_fechareg(cursor.getString(cursor.getColumnIndex("d_fechareg")));
            oEnt.setC_tiposug(cursor.getString(cursor.getColumnIndex("c_tiposug")));
            oEnt.setC_descripcion(cursor.getString(cursor.getColumnIndex("c_descripcion")));
            oEnt.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));
            oEnt.setC_usuarioreg(cursor.getString(cursor.getColumnIndex("c_usuario")));
            oEnt.setC_enviado(cursor.getString(cursor.getColumnIndex("c_enviado")));
            oEnt.setC_tipoinfo(cursor.getString(cursor.getColumnIndex("c_tipoinfo")));
            oEnt.setN_identinfo(cursor.getInt(cursor.getColumnIndex("n_identinfo")));
            oEnt.setC_observacion(cursor.getString(cursor.getColumnIndex("c_observacion")));
            result.add(oEnt);
        }
        return result;
    }
        public SugerenciaCliente GetSugerenciaItem (String  sCod){
            SugerenciaCliente oEnt = new SugerenciaCliente();
        String query = "SELECT * FROM MCO_SUGERENCIA  where _id="+sCod;
        Log.i("query MCO_SUGERENCIA  " ,query);
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            oEnt.setC_compania(cursor.getString(cursor.getColumnIndex("c_compania")));
            oEnt.setN_correlativo(cursor.getInt(cursor.getColumnIndex("_id")));
            oEnt.setN_cliente(cursor.getInt(cursor.getColumnIndex("n_cliente")));
            oEnt.setD_fechareg(cursor.getString(cursor.getColumnIndex("d_fechareg")));
            oEnt.setC_tiposug(cursor.getString(cursor.getColumnIndex("c_tiposug")));
            oEnt.setC_descripcion(cursor.getString(cursor.getColumnIndex("c_descripcion")));
            oEnt.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));
            oEnt.setC_enviado(cursor.getString(cursor.getColumnIndex("c_enviado")));
            oEnt.setC_tipoinfo(cursor.getString(cursor.getColumnIndex("c_tipoinfo")));
            oEnt.setN_identinfo(cursor.getInt(cursor.getColumnIndex("n_identinfo")));
            oEnt.setC_observacion(cursor.getString(cursor.getColumnIndex("c_observacion")));
        }
        return oEnt;
    }

    /************************************************/
    /*************REGISTRO DE CAPACITACION***********/
    /************************************************/
    //Insert Tema Capacitacion
    public ContentValues TMATemaCapacitacionContentValues(TMATemaCapacitacion oEnt){
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_temacapacitacion", oEnt.getC_temacapacitacion());
        contentValues.put("c_descripcion",oEnt.getC_descripcion());
        contentValues.put("c_estado",oEnt.getC_estado());
        return contentValues ;
    }

    public long InsertTMATemaCapacitacion(TMATemaCapacitacion oEnt){
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TMA_TEMACAPACITACION_NAME, null, TMATemaCapacitacionContentValues(oEnt));
        this.CloseDB();
        return rowid;
    }

    //Direccion Cliente
    public ContentValues TMADireccionCliContentValues(TMADireccionCli oEnt){
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_compania", oEnt.getC_compania());
        contentValues.put("n_cliente", oEnt.getN_cliente());
        contentValues.put("n_secuencia", oEnt.getN_secuencia());
        contentValues.put("c_descripcion",oEnt.getC_descripcion());
        contentValues.put("c_estado",oEnt.getC_estado());
        return contentValues ;
    }

    public long InsertTMADireccionCli(TMADireccionCli oEnt){
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.TMA_DIRECCIONCLI_NAME, null, TMADireccionCliContentValues(oEnt));
        this.CloseDB();
        return rowid;
    }

    //Extraer Tema Capacitacion
    public ArrayList<TMATemaCapacitacion> GetTemaCapacitacion(){
        ArrayList<TMATemaCapacitacion> oData = new ArrayList<>();
        String query = "SELECT * FROM TMA_TEMACAPACITACION where c_estado = 'A' order by c_descripcion";
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            TMATemaCapacitacion oEnt = new TMATemaCapacitacion();
            oEnt.setC_temacapacitacion(cursor.getString(cursor.getColumnIndex("c_temacapacitacion")));
            oEnt.setC_descripcion(cursor.getString(cursor.getColumnIndex("c_descripcion")));
            oEnt.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));
            oData.add(oEnt);
        }
        return oData;
    }

    //Extraer Direccion Cliente
    public ArrayList<TMADireccionCli> GetDireccionCli(String compania, String cliente){
        ArrayList<TMADireccionCli> oData = new ArrayList<>();
        String query = "SELECT * FROM TMA_DIRECCIONCLI " +
                "where c_compania='" + compania + "' " +
                "and n_cliente=" + cliente + " " +
                "and c_estado = 'A' order by c_descripcion";
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            TMADireccionCli oEnt = new TMADireccionCli();
            oEnt.setC_compania(cursor.getString(cursor.getColumnIndex("c_compania")));
            oEnt.setN_cliente(cursor.getInt(cursor.getColumnIndex("n_cliente")));
            oEnt.setN_secuencia(cursor.getInt(cursor.getColumnIndex("n_secuencia")));
            oEnt.setC_descripcion(cursor.getString(cursor.getColumnIndex("c_descripcion")));
            oEnt.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));
            oData.add(oEnt);
        }
        return oData;
    }

    public String GetNombreTemaCapacitacion(String codTema){
        String result = "";
        String query = "SELECT * FROM TMA_TEMACAPACITACION where c_temacapacitacion = '" + codTema + "'";
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            result = (cursor.getString(cursor.getColumnIndex("c_descripcion")).trim());
        }
        return result;
    }
    public long InsertCapacitacionCliente(CapacitacionCliente oEnt) {
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.MCO_SOLCAPACITACION_NAME, null, CapacitacionClienteContentValues(oEnt));
        this.CloseDB();
        return rowid;
    }

    public  long UpdateCapacitacion (CapacitacionCliente oEnt){
        this.OpenWritableDB();
        long rowid = db.update(ConstasDB.MCO_SOLCAPACITACION_NAME, CapacitacionClienteContentValues(oEnt),"_id="+oEnt.getN_correlativo(),null);
        this.CloseDB();
        return rowid;
    }

    //Insert SUGERENCIA CLIENTE
    public ContentValues CapacitacionClienteContentValues (CapacitacionCliente oEnt){
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_compania",oEnt.getC_compania());
        contentValues.put("n_correlativo",oEnt.getN_correlativo());
        contentValues.put("n_cliente",oEnt.getN_cliente());
        contentValues.put("d_fechareg",oEnt.getD_fechareg());
        contentValues.put("n_personas",oEnt.getN_personas());
        contentValues.put("d_fechaprob",oEnt.getD_fechaprob());
        contentValues.put("d_horaprob",oEnt.getD_horaprob());
        contentValues.put("c_lugar",oEnt.getC_lugar());
        contentValues.put("n_direccion",oEnt.getN_direccioncli());
        contentValues.put("c_direccionreg",oEnt.getC_direccionreg());
        contentValues.put("c_temacapacitacion",oEnt.getC_temacapacitacion());
        contentValues.put("c_descripciontema",oEnt.getC_descripciontema());
        contentValues.put("c_usuario",oEnt.getC_usuarioreg());
        contentValues.put("c_estado",oEnt.getC_estado());
        contentValues.put("c_enviado",oEnt.getC_enviado());
        contentValues.put("c_observacion",oEnt.getC_observacion());
        return contentValues;
    }



    public ArrayList<CapacitacionCliente> GetListCapacitacionCliente (String sComp,String sCliente,String sEstado,String sFechaIni,String sFechaFin){
        ArrayList<CapacitacionCliente> result = new ArrayList<>();
        String query = "SELECT * FROM MCO_SOLCAPACITACION " +
                "where d_fechareg >=  '"+sFechaIni+" 00:00:00'  and d_fechareg <= '"+sFechaFin+" 23:59:59' " +
                "and cast(n_cliente as text) like '"+sCliente+"' and c_estado like '"+sEstado+"' and c_compania  like '"+sComp+"' and c_enviado='N';";
        Log.i("query quejas " ,query);
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            CapacitacionCliente oEnt = new CapacitacionCliente();
            oEnt.setC_compania(cursor.getString(cursor.getColumnIndex("c_compania")));
            oEnt.setN_correlativo(cursor.getInt(cursor.getColumnIndex("_id")));
            oEnt.setN_cliente(cursor.getInt(cursor.getColumnIndex("n_cliente")));
            oEnt.setD_fechareg(cursor.getString(cursor.getColumnIndex("d_fechareg")));
            oEnt.setN_personas(cursor.getInt(cursor.getColumnIndex("n_personas")));
            oEnt.setD_fechaprob(cursor.getString(cursor.getColumnIndex("d_fechaprob")));
            oEnt.setD_horaprob(cursor.getString(cursor.getColumnIndex("d_horaprob")));
            oEnt.setC_lugar(cursor.getString(cursor.getColumnIndex("c_lugar")));
            oEnt.setN_direccioncli(cursor.getInt(cursor.getColumnIndex("n_direccion")));
            oEnt.setC_direccionreg(cursor.getString(cursor.getColumnIndex("c_direccionreg")));
            oEnt.setC_temacapacitacion(cursor.getString(cursor.getColumnIndex("c_temacapacitacion")));
            oEnt.setC_descripciontema(cursor.getString(cursor.getColumnIndex("c_descripciontema")));
            oEnt.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));
            oEnt.setC_usuarioreg(cursor.getString(cursor.getColumnIndex("c_usuario")));
            oEnt.setC_enviado(cursor.getString(cursor.getColumnIndex("c_enviado")));
            result.add(oEnt);
        }
        return result;
    }

    public CapacitacionCliente GetCapacitacionClienteItem (String sCod){
        CapacitacionCliente oEnt = new CapacitacionCliente();
        String query = "SELECT * FROM MCO_SOLCAPACITACION where _id="+sCod;
        Log.i("query MCO_SOLCAPACITACION " ,query);
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            oEnt.setC_compania(cursor.getString(cursor.getColumnIndex("c_compania")));
            oEnt.setN_correlativo(cursor.getInt(cursor.getColumnIndex("_id")));
            oEnt.setN_cliente(cursor.getInt(cursor.getColumnIndex("n_cliente")));
            oEnt.setD_fechareg(cursor.getString(cursor.getColumnIndex("d_fechareg")));
            oEnt.setN_personas(cursor.getInt(cursor.getColumnIndex("n_personas")));
            oEnt.setD_fechaprob(cursor.getString(cursor.getColumnIndex("d_fechaprob")));
            oEnt.setD_horaprob(cursor.getString(cursor.getColumnIndex("d_horaprob")));
            oEnt.setC_lugar(cursor.getString(cursor.getColumnIndex("c_lugar")));
            oEnt.setN_direccioncli(cursor.getInt(cursor.getColumnIndex("n_direccion")));
            oEnt.setC_direccionreg(cursor.getString(cursor.getColumnIndex("c_direccionreg")));
            oEnt.setC_temacapacitacion(cursor.getString(cursor.getColumnIndex("c_temacapacitacion")));
            oEnt.setC_descripciontema(cursor.getString(cursor.getColumnIndex("c_descripciontema")));
            oEnt.setC_estado(cursor.getString(cursor.getColumnIndex("c_estado")));
            oEnt.setC_usuarioreg(cursor.getString(cursor.getColumnIndex("c_usuario")));
            oEnt.setC_enviado(cursor.getString(cursor.getColumnIndex("c_enviado")));

        }
        return oEnt;
    }
    /************************************************/
    /************************************************/
    /************************************************/

    /// Doc Fotos reclamo Garantia
     public  ContentValues DocFotosReclamosGarantiaContentValues(DocsReclamoGarantia doc){
      ContentValues contentValues = new ContentValues();
      contentValues.put("c_compania",doc.getC_compania());
      contentValues.put("n_numero",doc.getN_numero());
      contentValues.put("n_linea",doc.getN_linea());
      contentValues.put("c_descripcion" ,  doc.getC_descripcion());
      contentValues.put("c_nombre_archivo", doc.getC_nombre_archivo());
      contentValues.put("c_ruta_archivo",doc.getC_ruta_archivo());
      contentValues.put("c_ultimousuario",doc.getC_ultimousuario());
      contentValues.put("d_ultimafechamodificacion",doc.getD_ultimafechamodificacion());
      return  contentValues ;

     }

     public  long InsertDocFotosReclamoGarantia(DocsReclamoGarantia doc){
         this.OpenWritableDB();
         long rowid = db.insert(ConstasDB.MCO_DOCS_RECLAMOGARANTIA_NAME, null, DocFotosReclamosGarantiaContentValues(doc));
         this.CloseDB();
         return rowid;
     }

    public ArrayList<DocsReclamoGarantia> GetListDocsRG( String correlativo){
        ArrayList<DocsReclamoGarantia> oData = new ArrayList<>();
        String query = "select * from MCO_DOCS_RECLAMOGARANTIA WHERE n_numero = "+correlativo;
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            DocsReclamoGarantia oEnt = new DocsReclamoGarantia();
            oEnt.setC_compania(cursor.getString(cursor.getColumnIndex("c_compania")));
            oEnt.setN_numero(cursor.getInt(cursor.getColumnIndex("n_numero")));
            oEnt.setN_linea(cursor.getInt(cursor.getColumnIndex("n_linea")));
            oEnt.setC_descripcion(cursor.getString(cursor.getColumnIndex("c_descripcion")));
            oEnt.setC_nombre_archivo(cursor.getString(cursor.getColumnIndex("c_nombre_archivo")));
            oEnt.setC_ruta_archivo(cursor.getString(cursor.getColumnIndex("c_ruta_archivo")));
            oEnt.setC_ultimousuario(cursor.getString(cursor.getColumnIndex("c_ultimousuario")));
            oEnt.setD_ultimafechamodificacion(cursor.getString(cursor.getColumnIndex("d_ultimafechamodificacion")));

            oData.add(oEnt);
        }
        return oData;
    }

    /// docs qieja cliente
    public  ContentValues DocsQuejaClienteContenValues(DocsQuejaCliente doc){
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_compania",doc.getC_compania());
        contentValues.put("n_queja",doc.getN_queja());
        contentValues.put("n_linea",doc.getN_linea());
        contentValues.put("c_descripcion" ,  doc.getC_descripcion());
        contentValues.put("c_nombre_archivo", doc.getC_nombre_archivo());
        contentValues.put("c_ruta_archivo",doc.getC_ruta_archivo());
        contentValues.put("c_ultimousuario",doc.getC_ultimousuario());
        contentValues.put("d_ultimafechamodificacion",doc.getD_ultimafechamodificacion());
        return  contentValues ;

    }
    public  long InsertDocFotosQuejaCliente(DocsQuejaCliente doc){
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.MCO_DOCS_QUEJACLIENTE_NAME, null, DocsQuejaClienteContenValues(doc));
        this.CloseDB();
        return rowid;
    }

    public ArrayList<DocsQuejaCliente> GetListDocsQuejaCliente( String correlativo){
        ArrayList<DocsQuejaCliente> oData = new ArrayList<>();
        String query = "select * from MCO_DOCS_QUEJACLIENTE WHERE     n_queja = "+correlativo;
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            DocsQuejaCliente oEnt = new DocsQuejaCliente();
            oEnt.setC_compania(cursor.getString(cursor.getColumnIndex("c_compania")));
            oEnt.setN_queja(cursor.getInt(cursor.getColumnIndex("n_queja")));
            oEnt.setN_linea(cursor.getInt(cursor.getColumnIndex("n_linea")));
            oEnt.setC_descripcion(cursor.getString(cursor.getColumnIndex("c_descripcion")));
            oEnt.setC_nombre_archivo(cursor.getString(cursor.getColumnIndex("c_nombre_archivo")));
            oEnt.setC_ruta_archivo(cursor.getString(cursor.getColumnIndex("c_ruta_archivo")));
            oEnt.setC_ultimousuario(cursor.getString(cursor.getColumnIndex("c_ultimousuario")));
            oEnt.setD_ultimafechamodificacion(cursor.getString(cursor.getColumnIndex("d_ultimafechamodificacion")));

            oData.add(oEnt);
        }
        return oData;
    }

    ////*** DOCS SUGERENCIA
    public  ContentValues DocsSugerenciaContentValues (DocsSugerencia doc){
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_compania",doc.getC_compania());
        contentValues.put("n_sugerencia",doc.getN_sugerencia());
        contentValues.put("n_linea",doc.getN_linea());
        contentValues.put("c_descripcion" ,  doc.getC_descripcion());
        contentValues.put("c_nombre_archivo", doc.getC_nombre_archivo());
        contentValues.put("c_ruta_archivo",doc.getC_ruta_archivo());
        contentValues.put("c_ultimousuario",doc.getC_ultimousuario());
        contentValues.put("d_ultimafechamodificacion",doc.getD_ultimafechamodificacion());
        return  contentValues ;
    }

    public  long InsertDocsSuegerencia (DocsSugerencia doc){
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.MCO_DOCS_SUGERENCIA_NAME, null, DocsSugerenciaContentValues(doc));
        this.CloseDB();
        return rowid;
    }

    //  docs capacitacion
    public  ContentValues DocsCapacitacionContentValues (DocsCapacitacion doc){
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_compania",doc.getC_compania());
        contentValues.put("n_solicitud",doc.getN_solicitud());
        contentValues.put("n_linea",doc.getN_linea());
        contentValues.put("c_descripcion" ,  doc.getC_descripcion());
        contentValues.put("c_nombre_archivo", doc.getC_nombre_archivo());
        contentValues.put("c_ruta_archivo",doc.getC_ruta_archivo());
        contentValues.put("c_ultimousuario",doc.getC_ultimousuario());
        contentValues.put("d_ultimafechamodificacion",doc.getD_ultimafechamodificacion());
        return  contentValues ;
    }

    public  long InsertDocsCapacitacion (DocsCapacitacion doc){
        this.OpenWritableDB();
        long rowid = db.insert(ConstasDB.MCO_DOCS_CAPACITACION_NAME, null, DocsCapacitacionContentValues(doc));
        this.CloseDB();
        return rowid;
    }


    public  ArrayList<DocsCapacitacion> GetListDocsCapacitacion (String correlativo ){
        ArrayList<DocsCapacitacion> oData = new ArrayList<>();
        String query = "select * from " + ConstasDB.MCO_DOCS_CAPACITACION_NAME+ " WHERE  n_solicitud = "+correlativo;
      Log.i("string quiery " , query);
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            DocsCapacitacion oEnt = new DocsCapacitacion();
            oEnt.setC_compania(cursor.getString(cursor.getColumnIndex("c_compania")));
            oEnt.setN_solicitud(cursor.getInt(cursor.getColumnIndex("n_solicitud")));
            oEnt.setN_linea(cursor.getInt(cursor.getColumnIndex("n_linea")));
            oEnt.setC_descripcion(cursor.getString(cursor.getColumnIndex("c_descripcion")));
            oEnt.setC_nombre_archivo(cursor.getString(cursor.getColumnIndex("c_nombre_archivo")));
            oEnt.setC_ruta_archivo(cursor.getString(cursor.getColumnIndex("c_ruta_archivo")));
            oEnt.setC_ultimousuario(cursor.getString(cursor.getColumnIndex("c_ultimousuario")));
            oEnt.setD_ultimafechamodificacion(cursor.getString(cursor.getColumnIndex("d_ultimafechamodificacion")));

            oData.add(oEnt);
        }
        return oData;
    }

    public  ArrayList<DocsSugerencia> GetListDocsSugerencia (String correlativo ){
        ArrayList<DocsSugerencia> oData = new ArrayList<>();
        String query = "select * from MCO_SUGERENCIA WHERE     n_correlativo = "+correlativo;
        this.OpenWritableDB();
        Cursor cursor  =db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            DocsSugerencia oEnt = new DocsSugerencia();
            oEnt.setC_compania(cursor.getString(cursor.getColumnIndex("c_compania")));
            oEnt.setN_sugerencia(cursor.getInt(cursor.getColumnIndex("n_correlativo")));
            oEnt.setN_linea(cursor.getInt(cursor.getColumnIndex("n_linea")));
            oEnt.setC_descripcion(cursor.getString(cursor.getColumnIndex("c_descripcion")));
            oEnt.setC_nombre_archivo(cursor.getString(cursor.getColumnIndex("c_nombre_archivo")));
            oEnt.setC_ruta_archivo(cursor.getString(cursor.getColumnIndex("c_ruta_archivo")));
            oEnt.setC_ultimousuario(cursor.getString(cursor.getColumnIndex("c_ultimousuario")));
            oEnt.setD_ultimafechamodificacion(cursor.getString(cursor.getColumnIndex("d_ultimafechamodificacion")));
            oData.add(oEnt);
        }
        return oData;
    }



    public boolean deleteInspMaq(String correlativo) {
        boolean res = false;
        this.OpenWritableDB();
        res = db.delete(ConstasDB.TABLA_MTP_INSPECCIONMAQUINA_CAB_NAME, ConstasDB.MTP_INSP_MAQ_CAB_CORRELATIVO + "=" + correlativo, null) > 0;
        res = db.delete(ConstasDB.TABLA_MTP_INSPECCIONMAQUINA_DET_NAME, ConstasDB.MTP_INSP_MAQ_DET_CORRELATIVO + "=" + correlativo, null) > 0;
        this.CloseDB();
        return res;
    }

    public boolean deleteInspGen(String correlativo) {

        boolean res = false;
        this.OpenWritableDB();
        res = db.delete(ConstasDB.TABLA_MTP_INSPECCIONGENERAL_CAB_NAME, ConstasDB.MTP_INSP_GEN_CAB_CORRELATIVO + "=" + correlativo, null) > 0;
        res = db.delete(ConstasDB.TABLA_MTP_INSPECCIONGENERAL_DET_NAME, ConstasDB.MTP_INSP_GEN_DET_CORRELATIVO + "=" + correlativo, null) > 0;
        this.CloseDB();
        return res;
    }

    /* public void deleteTables() {

        this.OpenWritableDB();
        db.execSQL("DELETE FROM " + ConstasDB.TABLA_MTP_MENUS_NAME);
        db.execSQL("DELETE FROM " + ConstasDB.TABLA_MTP_ACCESO_NAME);
        db.execSQL("DELETE FROM " + ConstasDB.TABLA_MTP_USUARIO_NAME);
        db.execSQL("DELETE FROM " + ConstasDB.TABLA_MTP_MAQUINAS_NAME);
        db.execSQL("DELETE FROM " + ConstasDB.TABLA_MTP_PERIODO_INSPECCION_NAME);
        db.execSQL("DELETE FROM " + ConstasDB.TABLA_MTP_INSPECCION_NAME);
        db.execSQL("DELETE FROM " + ConstasDB.TMA_CLIENTES_NAME);
        db.execSQL("DELETE FROM " + ConstasDB.TMA_FALLA_NAME);
        db.execSQL("DELETE FROM " + ConstasDB.TMA_MODELO_NAME);
        db.execSQL("DELETE FROM " + ConstasDB.TMA_MARCA_NAME);
        db.execSQL("DELETE FROM " + ConstasDB.TMA_PRUEBALAB_NAME);
        db.execSQL("DELETE FROM " + ConstasDB.TMA_TIPORECLAMO_NAME);
        db.execSQL("DELETE FROM " + ConstasDB.TMA_VENDEDOR_NAME);
        db.execSQL("DELETE FROM " + ConstasDB.MCO_RECLAMO_GARANTIA_NAME);
        this.CloseDB();
    }*/

}
