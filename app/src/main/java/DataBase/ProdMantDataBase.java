package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import Model.Menu;
import Model.Permisos;
import Model.SubMenu;

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
        this.CloseDB();
    }


}
