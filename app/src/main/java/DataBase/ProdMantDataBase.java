package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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
        contentValues.put(ConstasDB.MTP_INSP_MAQ_DET_PORCEN_INSP,det.getCod_inspeccion());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_DET_ESTADO,det.getEstado());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_DET_COMENTARIO,det.getComentario());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_DET_RUTA_FOTO,det.getRutaFoto());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_DET_ULT_USER,det.getUltimoUser());
        contentValues.put(ConstasDB.MTP_INSP_MAQ_DET_ULT_FECHA_MOD,det.getUltimaFechaMod());

        return  contentValues;


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
