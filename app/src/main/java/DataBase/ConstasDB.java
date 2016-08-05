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



    // tabla usuario
        public  static  final  String  TABLA_MTP_USUARIO_NAME = "MTP_USUARIO";

        public  static  final  String MTP_USUARIO_ID = "_id";
        public  static  final  String MTP_USUARIO_COD = "c_codigousuario";
        public  static final   String MTP_USUARIO_NOM = "c_nombre";
        public  static  final  String MTP_USUARIO_CLAV =  "c_clave";
        public  static  final  String MTP_USUARIO_NRO = "n_persona";
        public  static  final  String MTP_USUARIO_EST = "c_estado";
        public  static  final  String MTP_USUARIO_FLAG = "c_flagmantto";

        public  static  final  String TABLA_MTP_USUARIO_SQL =
                    "CREATE TABLE "+  TABLA_MTP_USUARIO_NAME +
                            " ( "+ MTP_USUARIO_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,  "+
                            MTP_USUARIO_COD + " TEXT , " +
                            MTP_USUARIO_NOM + " TEXT , " +
                            MTP_USUARIO_CLAV + " TEXT , " +
                            MTP_USUARIO_NRO + " TEXT , " +
                            MTP_USUARIO_EST + " TEXT , " +
                            MTP_USUARIO_FLAG + " TEXT ); " ;
}
