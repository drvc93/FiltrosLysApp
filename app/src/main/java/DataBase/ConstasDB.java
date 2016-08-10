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

    // tabla maquina
    public  static  final  String  TABLA_MTP_MAQUINAS_NAME = "MTP_MAQUINAS";
    public  static  final  String  MTP_MAQUINA_ID="_id";
    public  static  final  String  MTP_MAQUINA_COMP ="c_compania";
    public  static  final  String  MTP_MAQUINA_COD_MAQUINA="c_maquina";
    public  static  final  String  MTP_MAQUINA_DESCRP="c_descripcion";
    public  static  final  String  MTP_MAQUINA_COD_BARRAS="c_codigobarras";
    public  static  final  String  MTP_MAQUINA_FAM_INSP = "c_familiainspeccion";
    public  static  final  String  MTP_MAQUINA_CENTRO_COS = "c_centrocosto";
    public  static  final  String  MTP_MAQUINA_ESTADO = "c_estado";
    public  static  final  String  MTP_MAQUINNA_ULT_USER = "c_ultimousuario";
    public  static  final  String  MTP_MAQUINA_ULTIMAFECHAMOD= "d_ultimafechamodificacion";

    public  static  final  String TABLA_MTP_MAQUINAS_SQL =
            "CREATE TABLE " +TABLA_MTP_MAQUINAS_NAME+
                    " ( "+MTP_MAQUINA_ID+"   INTEGER PRIMARY KEY AUTOINCREMENT,  "+
                    MTP_MAQUINA_COMP  + " TEXT , " +
                    MTP_MAQUINA_COD_MAQUINA  + " TEXT , " +
                    MTP_MAQUINA_DESCRP  + " TEXT , " +
                    MTP_MAQUINA_COD_BARRAS + " TEXT , " +
                    MTP_MAQUINA_FAM_INSP + " TEXT , " +
                    MTP_MAQUINA_CENTRO_COS + " TEXT , " +
                    MTP_MAQUINA_ESTADO + " TEXT , " +
                    MTP_MAQUINNA_ULT_USER + " TEXT , " +
                    MTP_MAQUINA_ULTIMAFECHAMOD  + " TEXT ); " ;

    // tabla periodo inspeccion
    public  static  final  String TABLA_MTP_PERIODO_INSPECCION_NAME = "MTP_PERIODOINSPECCION";
    public  static  final  String MTP_PERIODO_INSPECCION_ID = "_id";
    public  static  final  String MTP_PERIODO_INSPECCION_COD_PER = "c_periodoinspeccion";
    public  static  final  String MTP_PERIODO_INSPECCION_DESCRP = "c_descripcion";
    public  static  final  String MTP_PERIODO_INSPECCION_ESTADO ="c_estado";
    public static   final  String MTP_PERIODO_INSPECCION_ULT_USER = "c_ultimousuario";
    public  static  final  String MTP_PERIODO_INSPECCION_ULT_FECHA = "d_ultimafechamodificacion";

    public  static  final  String TABLA_MTP_PERIODO_SQL =
            "CREATE TABLE "+ TABLA_MTP_PERIODO_INSPECCION_NAME +
                    " ( "+ MTP_PERIODO_INSPECCION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    MTP_PERIODO_INSPECCION_COD_PER + " TEXT , " +
                    MTP_PERIODO_INSPECCION_DESCRP + " TEXT , " +
                    MTP_PERIODO_INSPECCION_ESTADO+ " TEXT , " +
                    MTP_PERIODO_INSPECCION_ULT_USER + " TEXT , " +
                    MTP_PERIODO_INSPECCION_ULT_FECHA + " TEXT ); ";

    // tabla inspecciones
    public  static  final  String TABLA_MTP_INSPECCION_NAME = "MTP_INSPECCION";
    public  static  final  String MTP_INSPECCION_ID ="_id";
    public static final String MTP_INSPECCION_COD_INSP = "c_inspeccion";
    public static final String MTP_INSPECCION_DESCRIPCION = "c_descripcion";
    public static final String MTP_INSPECCION_TIPO_INS = "c_tipoinspeccion";
    public static final String MTP_INSPECCION_PORC_MIN = "n_porcentajeminimo";
    public static final String MTP_INSPECCION_PORC_MAX = "n_porcentajemaximo";
    public static final String MTP_INSPECCION_FAM_INP = "c_familiainspeccion";
    public static final String MTP_INSPECCION_PER_INSP = "c_periodoinspeccion";
    public static final String MTP_INSPECCION_ESTADO = "c_estado";
    public static final String MTP_INSPECCION_ULT_USER ="c_ultimousuario" ;
    public static final String MTP_INSPECCION_ULT_FECHA = "d_ultimafechamodificacion";
    
    public static final String TABLA_MTP_INSPECCION_SQL =
            "CREATE TABLE "+ TABLA_MTP_INSPECCION_NAME +
                    " ( " + MTP_INSPECCION_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,  "+
                     MTP_INSPECCION_COD_INSP + " TEXT , " +
                    MTP_INSPECCION_DESCRIPCION  + " TEXT , " +
                    MTP_INSPECCION_TIPO_INS + " TEXT , " +
                    MTP_INSPECCION_PORC_MIN + " TEXT , " +
                    MTP_INSPECCION_PORC_MAX + " TEXT , " +
                    MTP_INSPECCION_FAM_INP + " TEXT , " +
                    MTP_INSPECCION_PER_INSP + " TEXT , " +
                    MTP_INSPECCION_ESTADO + " TEXT , " +
                    MTP_INSPECCION_ULT_USER + " TEXT , " +
                    MTP_INSPECCION_ULT_FECHA + " TEXT ); " ;
}







