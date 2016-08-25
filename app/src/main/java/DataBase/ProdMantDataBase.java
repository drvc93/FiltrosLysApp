package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.filtroslys.filtroslysapp.InspeccionGen;

import java.util.ArrayList;

import Model.InspeccionGenCabecera;
import Model.InspeccionGenDetalle;
import Model.InspeccionMaqCabecera;
import Model.InspeccionMaqDetalle;
import Model.Menu;
import Model.Permisos;
import Model.SubMenu;
import Model.SubMenuBotones;

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
         boolean result  = false;
         String query  = "SELECT * FROM  MTP_USUARIO where c_codigousuario = '"+user+"' and c_clave ='"+clave+"'";
          this.OpenWritableDB();
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            result = true;

        }

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

        String query = "SELECT MAX(n_correlativo) FROM MTP_INSPECCIONGENERAL_CAB ";
        this.OpenWritableDB();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                if (c.getString(0) != null) {
                    String var = c.getString(0);
                    res = Integer.valueOf(var);
                }
            }
        }

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

    public ArrayList<HistorialInspGenDB> GetHistorialInsGenpList(String accion, String tipoInsp, String fechaIni, String fechFin) {


        String query = "";
        ArrayList<HistorialInspGenDB> list = new ArrayList<HistorialInspGenDB>();
        this.OpenWritableDB();
        Cursor c;
        switch (accion) {

            case "1":
                query = "SELECT n_correlativo  , case c_tipoinspeccion when   'OT' THEN  'OTROS'  ELSE 'MAQUINA' END TipoIns,c_maquina,c_centrocosto,c_usuarioinspeccion,substr(d_fechainspeccion ,0,11) fecha,c_comentario ,c_estado "
                        + "FROM  MTP_INSPECCIONGENERAL_CAB where c_tipoinspeccion  ='" + tipoInsp + "'";
                break;
            case "2":
                query = "SELECT n_correlativo  , case c_tipoinspeccion when   'OT' THEN  'OTROS'  ELSE 'MAQUINA' END TipoIns,c_maquina,c_centrocosto,c_usuarioinspeccion,substr(d_fechainspeccion ,0,11) fecha,c_comentario ,c_estado" +
                        " FROM  MTP_INSPECCIONGENERAL_CAB where c_tipoinspeccion  ='" + tipoInsp + "' and datetime (substr(d_fechainspeccion,7,4 )||'-'|| substr(d_fechainspeccion,1,2 )|| '-' ||substr(d_fechainspeccion,4,2 ) ) between '" + fechaIni + "'  and '" + fechFin + "'";
                break;
            case "3":
                query = "SELECT n_correlativo  , case c_tipoinspeccion when   'OT' THEN  'OTROS'  ELSE 'MAQUINA' END TipoIns,c_maquina,c_centrocosto,c_usuarioinspeccion,substr(d_fechainspeccion ,0,11) fecha,c_comentario ,c_estado" +
                        " FROM  MTP_INSPECCIONGENERAL_CAB where  datetime (substr(d_fechainspeccion,7,4 )||'-'|| substr(d_fechainspeccion,1,2 )|| '-' ||substr(d_fechainspeccion,4,2 ) ) between '" + fechaIni + "'  and '" + fechFin + "'";
                break;
            case "4":
                query = "SELECT n_correlativo  , case c_tipoinspeccion when   'OT' THEN  'OTROS'  ELSE 'MAQUINA' END TipoIns,c_maquina,c_centrocosto,c_usuarioinspeccion,substr(d_fechainspeccion ,0,11) fecha,c_comentario ,c_estado" +
                        " FROM  MTP_INSPECCIONGENERAL_CAB ";
                break;


        }

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

    public void deleteTables() {

        this.OpenWritableDB();
        db.execSQL("DELETE FROM " + ConstasDB.TABLA_MTP_MENUS_NAME);
        db.execSQL("DELETE FROM " + ConstasDB.TABLA_MTP_ACCESO_NAME);
        db.execSQL("DELETE FROM " + ConstasDB.TABLA_MTP_USUARIO_NAME);
        db.execSQL("DELETE FROM " + ConstasDB.TABLA_MTP_MAQUINAS_NAME);
        db.execSQL("DELETE FROM " + ConstasDB.TABLA_MTP_PERIODO_INSPECCION_NAME);
        db.execSQL("DELETE FROM " + ConstasDB.TABLA_MTP_INSPECCION_NAME);
       /// db.execSQL("DELETE FROM " + ConstasDB.TABLA_MTP_INSPECCIONMAQUINA_CAB_NAME);
        this.CloseDB();
    }




}
