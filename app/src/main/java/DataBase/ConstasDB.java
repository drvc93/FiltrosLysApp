package DataBase;

/**
 * Created by dvillanueva on 04/08/2016.
 */
public class ConstasDB {
    public static final String DB_NAME = "dbProdMant.db";
    public static final int DB_VERSION = 1;

    //TABLA MTP_ACCESO
    public static final String TABLA_MTP_ACCESO_NAME = "MTP_ACCESO";

    public static final String MTP_ACC_ID = "_id";
    public static final String MTP_ACC_C_USUARIO = "c_usuario";
    public static final String MTP_ACC_C_APP = "c_aplicacion";
    public static final String MTP_ACC_C_NIV = "c_niveles";
    public static final String MTP_ACC_C_ACC = "c_acceso";
    public static final String MTP_ACC_C_NUE = "c_nuevo";
    public static final String MTP_ACC_C_MOD = "c_modificar";
    public static final String MTP_ACC_C_ELI = "c_eliminar";
    public static final String MTP_ACC_C_OTRO = "c_otros";

    public static final String TABLA_MTP_ACCESO_SQL =
            "CREATE TABLE " + TABLA_MTP_ACCESO_NAME +
                    " ( " + MTP_ACC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MTP_ACC_C_USUARIO + " TEXT ," +
                    MTP_ACC_C_APP + " TEXT, " +
                    MTP_ACC_C_NIV + " TEXT, " +
                    MTP_ACC_C_ACC + "  TEXT, " +
                    MTP_ACC_C_NUE + " TEXT, " +
                    MTP_ACC_C_MOD + " TEXT , " +
                    MTP_ACC_C_ELI + " TEXT, " +
                    MTP_ACC_C_OTRO + " TEXT " + ");";

    // TABLA MTP_MENUS

    public static final String TABLA_MTP_MENUS_NAME = "MTP_MENUS";

    public static final String MTP_MEN_ID = "_id";
    public static final String MTP_MEN_COD = "AplicacionCodigo";
    public static final String MTP_MEN_N1 = "nivel1";
    public static final String MTP_MEN_N2 = "nivel2";
    public static final String MTP_MEN_N3 = "nivel3";
    public static final String MTP_MEN_N4 = "nivel4";
    public static final String MTP_MEN_N5 = "nivel5";
    public static final String MTP_MEN_C_NOMM = "c_nombremenu";
    public static final String MTP_MEN_C_DESCP = "c_descripcion";

    public static final String TABLA_MTP_MENUS_SQL =
            "CREATE TABLE " + TABLA_MTP_MENUS_NAME +
                    " ( " + MTP_MEN_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MTP_MEN_COD + " TEXT , " +
                    MTP_MEN_N1 + " TEXT, " +
                    MTP_MEN_N2 + " TEXT, " +
                    MTP_MEN_N3 + " TEXT, " +
                    MTP_MEN_N4 + " TEXT, " +
                    MTP_MEN_N5 + " TEXT, " +
                    MTP_MEN_C_NOMM + " TEXT, " +
                    MTP_MEN_C_DESCP + " TEXT );";
}
