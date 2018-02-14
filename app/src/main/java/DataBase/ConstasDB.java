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

    // TABLA INSPECION_MAQUINA_CAB

    public static final String TABLA_MTP_INSPECCIONMAQUINA_CAB_NAME = "MTP_INSPECCIONMAQUINA_CAB";

    public static final String MTP_INSP_MAQ_CAB_ID="_id" ;
    public static final String MTP_INSP_MAQ_CAB_COMPANIA ="c_compania" ;
    public static final String MTP_INSP_MAQ_CAB_CORRELATIVO = "n_correlativo";
    public static final String MTP_INSP_MAQ_CAB_COD_MAQUINA = "c_maquina";
    public static final String MTP_INSP_MAQ_CAB_CONDICION_MAQUINA = "c_condicionmaquina";
    public static final String MTP_INSP_MAQ_CAB_COMENTARIO = "c_comentario";
    public static final String MTP_INSP_MAQ_CAB_ESTADO = "c_estado";
    public static final String MTP_INSP_MAQ_CAB_FECHA_INI_INSP = "d_fechaInicioInspeccion";
    public static final String MTP_INSP_MAQ_CAB_FECHA_FIN_INSP = "d_fechaFinInspeccion";
    public static final String MTP_INSP_MAQ_CAB_PERIODO_INSP = "c_periodoinspeccion";
    public static final String MTP_INSP_MAQ_CAB_USUARIO_INS = "c_usuarioInspeccion";
    public static final String MTP_INSP_MAQ_CAB_USUARIO_ENV = "c_usuarioenvio";
    public static final String MTP_INSP_MAQ_CAB_FECHA_ENV = "d_fechaenvio";
    public static final String MTP_INSP_MAQ_CAB_ULT_USER="c_ultimousuario";
    public static final String MTP_INSP_MAQ_CAB_ULT_FECHA_MOD = "d_ultimafechamodificacion";
    
    public static final String TABLA_MTP_INSPECCIONMAQUINA_CAB_SQL =
            "CREATE TABLE " + TABLA_MTP_INSPECCIONMAQUINA_CAB_NAME+
                    " ( " + MTP_INSP_MAQ_CAB_ID  +" INTEGER PRIMARY KEY AUTOINCREMENT,  "+
                    MTP_INSP_MAQ_CAB_COMPANIA+ " TEXT , " +
                    MTP_INSP_MAQ_CAB_CORRELATIVO+ " TEXT , " +
                    MTP_INSP_MAQ_CAB_COD_MAQUINA+ " TEXT , " +
                    MTP_INSP_MAQ_CAB_CONDICION_MAQUINA+ " TEXT , " +
                    MTP_INSP_MAQ_CAB_COMENTARIO+ " TEXT , " +
                    MTP_INSP_MAQ_CAB_ESTADO+ " TEXT , " +
                    MTP_INSP_MAQ_CAB_FECHA_INI_INSP+ " TEXT , " +
                    MTP_INSP_MAQ_CAB_FECHA_FIN_INSP+ " TEXT , " +
                    MTP_INSP_MAQ_CAB_PERIODO_INSP + " TEXT , " +
                    MTP_INSP_MAQ_CAB_USUARIO_INS+ " TEXT , " +
                    MTP_INSP_MAQ_CAB_USUARIO_ENV+ " TEXT , " +
                    MTP_INSP_MAQ_CAB_FECHA_ENV + " TEXT , " +
                    MTP_INSP_MAQ_CAB_ULT_USER + " TEXT , " +
                    MTP_INSP_MAQ_CAB_ULT_FECHA_MOD + " TEXT );";


    //TABLA INSPECION_MAQUINA_DET
    
    public static final String TABLA_MTP_INSPECCIONMAQUINA_DET_NAME = "MTP_INSPECCIONMAQUINA_DET";
    public static final String MTP_INSP_MAQ_DET_ID= "_id";
    public static final String MTP_INSP_MAQ_DET_COMPANIA = "c_compania";
    public static final String MTP_INSP_MAQ_DET_CORRELATIVO = "n_correlativo";
    public static final String MTP_INSP_MAQ_DET_LINEA = "n_linea";
    public static final String MTP_INSP_MAQ_DET_COD_INSP = "c_inspeccion";
    public static final String MTP_INSP_MAQ_DET_TIP_INSP = "c_tipoinspeccion";
    public static final String MTP_INSP_MAQ_DET_PORCENT_MIN = "n_porcentajeminimo";
    public static final String MTP_INSP_MAQ_DET_PORCENT_MAX = "n_porcentajemaximo";
    public static final String MTP_INSP_MAQ_DET_PORCEN_INSP = "n_porcentajeinspeccion";
    public static final String MTP_INSP_MAQ_DET_ESTADO = "c_estado";
    public static final String MTP_INSP_MAQ_DET_COMENTARIO = "c_comentario";
    public static final String MTP_INSP_MAQ_DET_RUTA_FOTO = "c_rutafoto";
    public static final String MTP_INSP_MAQ_DET_ULT_USER = "c_ultimousuario";
    public static final String MTP_INSP_MAQ_DET_ULT_FECHA_MOD = "d_ultimafechamodificacion";

    public static final String TABLA_MTP_INSPECCIONESMAQUINA_DET_SQL =
            "CREATE TABLE "+ TABLA_MTP_INSPECCIONMAQUINA_DET_NAME+
                    " (  "+MTP_INSP_MAQ_DET_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,  "+
                    MTP_INSP_MAQ_DET_COMPANIA + " TEXT , " +
                    MTP_INSP_MAQ_DET_CORRELATIVO + " TEXT , " +
                    MTP_INSP_MAQ_DET_LINEA + " TEXT , " +
                    MTP_INSP_MAQ_DET_COD_INSP + " TEXT , " +
                    MTP_INSP_MAQ_DET_TIP_INSP + " TEXT , " +
                    MTP_INSP_MAQ_DET_PORCENT_MIN  + " TEXT , " +
                    MTP_INSP_MAQ_DET_PORCENT_MAX + " TEXT , " +
                    MTP_INSP_MAQ_DET_PORCEN_INSP + " TEXT , " +
                    MTP_INSP_MAQ_DET_ESTADO + " TEXT , " +
                    MTP_INSP_MAQ_DET_COMENTARIO + " TEXT , " +
                    MTP_INSP_MAQ_DET_RUTA_FOTO + " TEXT , " +
                    MTP_INSP_MAQ_DET_ULT_USER + " TEXT , " +
                    MTP_INSP_MAQ_DET_ULT_FECHA_MOD + " TEXT ); " ;

    // TABLA CENTRO COSTO

    public static final String TABLA_MTP_CENTROCOSTO_NAME = "MTP_CENTROCOSTO";
    public static final String MTP_CENTRO_C_ID = "_id";
    public static final String MTP_CENTRO_C_COMPANIA = "c_compania";
    public static final String MTP_CENTRO_C_COD_CCOSTO = "c_centrocosto";
    public static final String MTP_CENTRO_C_DESCRIPCION = "c_descripcion";
    public static final String MTP_CENTRO_C_ESTADO = "c_estado";
    public static final String TABLA_MTP_CENTROCOSTO_SQL =
            "CREATE TABLE "+ TABLA_MTP_CENTROCOSTO_NAME+
                    " ( "+MTP_CENTRO_C_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,  "+
                    MTP_CENTRO_C_COMPANIA + " TEXT , " +
                    MTP_CENTRO_C_COD_CCOSTO+ " TEXT , " +
                    MTP_CENTRO_C_DESCRIPCION+ " TEXT , " +
                    MTP_CENTRO_C_ESTADO + " TEXT ); " ;

    //tipo revision 

    public static final String TABLA_MTP_TIPOREVISION_NAME = "MTP_TIPOREVISIONG";
    public static final String MTP_TIPOREVISION_ID = "_id";
    public static final String MTP_TIPOREVISION_COD = "c_tiporevisiong";
    public static final String MTP_TIPOREVISION_DESCRIPCION = "c_descripcion";
    public static final String MTP_TIPOREVISION_ESTADO = "c_estado";
    public static final String MTP_TIPOREVISION_ULT_USER = "ultimousuario";
    public static final String MTP_TIPOREVISION_ULT_FECHAMOD = "ultimafechamodificacion";
    public static final String TABLA_MTP_TIPOREVISION_SQL =
            "CREATE TABLE " + TABLA_MTP_TIPOREVISION_NAME +
                    " ( " + MTP_TIPOREVISION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MTP_TIPOREVISION_COD + " TEXT , " +
                    MTP_TIPOREVISION_DESCRIPCION + " TEXT , " +
                    MTP_TIPOREVISION_ESTADO + " TEXT , " +
                    MTP_TIPOREVISION_ULT_USER + " TEXT , " +
                    MTP_TIPOREVISION_ULT_FECHAMOD + " TEXT ); ";


    // tabla inspeccion general cab
    public static final String TABLA_MTP_INSPECCIONGENERAL_CAB_NAME = "MTP_INSPECCIONGENERAL_CAB";
    public static final String MTP_INSP_GEN_CAB_ID = "_id";
    public static final String MTP_INSP_GEN_CAB_COMPANIA = "c_compania";
    public static final String MTP_INSP_GEN_CAB_CORRELATIVO = "n_correlativo";
    public static final String MTP_INSP_GEN_CAB_TIP_INSP = "c_tipoinspeccion";
    public static final String MTP_INSP_GEN_CAB_COD_MAQ = "c_maquina";
    public static final String MTP_INSP_GEN_CAB_COD_CCOSTO = "c_centrocosto";
    public static final String MTP_INSP_GEN_CAB_COD_COMENTARIO = "c_comentario";
    public static final String MTP_INSP_GEN_CAB_USUARIO_INSP = "c_usuarioinspeccion";
    public static final String MTP_INSP_GEN_CAB_FECHA_INSP = "d_fechainspeccion";
    public static final String MTP_INSP_GEN_CAB_ESTADO = "c_estado";
    public static final String MTP_INSP_GEN_CAB_USUARIO_ENV = "c_usuarioenvio";
    public static final String MTP_INSP_GEN_CAB_FECHA_ENV = "d_fechaenvio";
    public static final String MTP_INSP_GEN_CAB_ULT_USER = "c_ultimousuario";
    public static final String MTP_INSP_GEN_CAB_FECHA_MOD = "d_ultimafechamodificacion";

    public static final String TABLA_MTP_INSPECCIONGENERAL_CAB_SQL =
            " CREATE TABLE " + TABLA_MTP_INSPECCIONGENERAL_CAB_NAME +
                    " ( " + MTP_INSP_GEN_CAB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  " +
                    MTP_INSP_GEN_CAB_COMPANIA + " TEXT , " +
                    MTP_INSP_GEN_CAB_CORRELATIVO + " INTEGER , " +
                    MTP_INSP_GEN_CAB_TIP_INSP + " TEXT , " +
                    MTP_INSP_GEN_CAB_COD_MAQ + " TEXT , " +
                    MTP_INSP_GEN_CAB_COD_CCOSTO + " TEXT , " +
                    MTP_INSP_GEN_CAB_COD_COMENTARIO + " TEXT , " +
                    MTP_INSP_GEN_CAB_USUARIO_INSP + " TEXT , " +
                    MTP_INSP_GEN_CAB_FECHA_INSP + " TEXT , " +
                    MTP_INSP_GEN_CAB_ESTADO + " TEXT , " +
                    MTP_INSP_GEN_CAB_USUARIO_ENV + " TEXT , " +
                    MTP_INSP_GEN_CAB_FECHA_ENV + " TEXT , " +
                    MTP_INSP_GEN_CAB_ULT_USER + " TEXT , " +
                    MTP_INSP_GEN_CAB_FECHA_MOD + " TEXT );";


    // tabla inspeccion general detalle 
    public static final String TABLA_MTP_INSPECCIONGENERAL_DET_NAME = "MTP_INSPECCIONGENERAL_DET";
    public static final String MTP_INSP_GEN_DET_ID = "_id";
    public static final String MTP_INSP_GEN_DET_COMPANIA = "c_compania";
    public static final String MTP_INSP_GEN_DET_CORRELATIVO = "n_correlativo";
    public static final String MTP_INSP_GEN_DET_LINEA = "n_linea";
    public static final String MTP_INSP_GEN_DET_COMENTARIO = "c_comentario";
    public static final String MTP_INSP_GEN_DET_RUTA_FOTO = "c_rutafoto";
    public static final String MTP_INSP_GEN_DET_ULT_USUARIO = "c_ultimousuario";
    public static final String MTP_INSP_GEN_DET_ULT_FECHA_MOD = "d_ultimafechamodificacion";
    public static final String MTP_INSP_GEN_DET_TIPO_REVISION = "c_tiporevisiong";
    public static final String MTP_INSP_GEN_DET_FLAG_ADIC = "c_flagadictipo";

    public static final String TABLA_MTP_INSPECCIONGENERAL_DET_SQL =
            " CREATE TABLE " + TABLA_MTP_INSPECCIONGENERAL_DET_NAME +
                    " ( " + MTP_INSP_GEN_DET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  " +
                    MTP_INSP_GEN_DET_COMPANIA + " TEXT , " +
                    MTP_INSP_GEN_DET_CORRELATIVO + " TEXT , " +
                    MTP_INSP_GEN_DET_LINEA + " TEXT , " +
                    MTP_INSP_GEN_DET_COMENTARIO + " TEXT , " +
                    MTP_INSP_GEN_DET_RUTA_FOTO + " TEXT , " +
                    MTP_INSP_GEN_DET_ULT_USUARIO + " TEXT , " +
                    MTP_INSP_GEN_DET_ULT_FECHA_MOD + " TEXT , " +
                    MTP_INSP_GEN_DET_TIPO_REVISION + " TEXT , " +
                    MTP_INSP_GEN_DET_FLAG_ADIC + " TEXT ); ";

    // tabla TMA_MODELO
    public static final String  TMA_MODELO_NAME =  "TMA_MODELO";

    public static final String TMA_MODELO_SQL =
            "CREATE TABLE "+ TMA_MODELO_NAME +
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    " c_marca  TEXT, "+
                    "c_modelo TEXT, "+
                    "c_descripcion TEXT, "+
                    "c_estado TEXT );";

    // TABBLA TMA_CLIENTES

    public static final String TMA_CLIENTES_NAME = "TMA_CLIENTES";
    public static final String TMA_CLIENTES_SQL =
            "CREATE TABLE " + TMA_CLIENTES_NAME +
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_compania TEXT ,"+
                    "n_cliente INTEGER, "+
                    "c_razonsocial TEXT )" ;

    // tabla TMA_FALLA

    public  static  final  String TMA_FALLA_NAME = "TMA_FALLA" ;
    public  static  final  String TMA_FALL_SQL =
            "CREATE TABLE " + TMA_FALLA_NAME+
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_codigo TEXT, "+
                    "c_descripcion TEXT);";

    // TABLA TMAMarca
    public static final String TMA_MARCA_NAME = "TMA_MARCA";
    public static final String TMA_MARCA_SQL =
            "CREATE TABLE "+ TMA_MARCA_NAME+
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_marca TEXT, "+
                    "c_descripcion TEXT, "+
                    "c_estado TEXT);";

    //TABLA TMA_PRUEBALAB
    public static final String TMA_PRUEBALAB_NAME = "TMA_PRUEBALAB";
    public static final String TMA_PRUEBALAB_SQL =
            "CREATE TABLE "+ TMA_PRUEBALAB_NAME+
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_codigo TEXT, "+
                    "c_descripcion TEXT, "+
                    "c_tipo TEXT, "+
                    "c_unidadcodigo TEXT ); " ;

    // TABLA TMA_TIPORECLAMO

    public static final String TMA_TIPORECLAMO_NAME = "TMA_TIPORECLAMO";
    public static final String TMA_TIPORECLAMO_SQL =
            "CREATE TABLE " + TMA_TIPORECLAMO_NAME +
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_tiporeclamo TEXT, "+
                    "c_descripcion TEXT, "+
                    "c_estado TEXT, "+
                    "c_tipo TEXT);";

    // tabla TMA_VENDEDOR

    public static final String TMA_VENDEDOR_NAME = "TMA_VENDEDOR";
    public static final String TMA_VENDEDOR_SQL =
            "CREATE TABLE "+ TMA_VENDEDOR_NAME +
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_compania TEXT , "+
                    "c_vendedor TEXT , "+
                    "c_ciasecundaria TEXT, "+
                    "c_nombres TEXT , "+
                    "c_estado TEXT); ";

    // tabla reclamo garantia
    public  static  final String MCO_RECLAMO_GARANTIA_NAME = "MCO_RECLAMOGARANTIA";
    public static final String  MCO_RECLAMO_GARANTIA_SQL  =
            "CREATE TABLE "+ MCO_RECLAMO_GARANTIA_NAME +
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_compania TEXT, "+
                    "n_correlativo BIGINT, "+
                    "d_fecha TEXT, " +
                    "n_cliente BIGINT, "+
                    "c_codproducto TEXT, "+
                    "c_lote TEXT, "+
                    "c_procedencia TEXT, "+
                    "n_qtyingreso DECIMAL(10,5) , "+
                    "c_vendedor TEXT, "+
                    "c_estado TEXT, "+
                    "c_usuario TEXT, "+
                    " c_lote1 TEXT, "+
                    "c_lote2 TEXT, "+
                    "c_lote3 TEXT, "+
                    "n_cantlote1 DECIMAL(10,5), "+
                    "n_cantlote2 DECIMAL(10,5), " +
                    "n_cantlote3 DECIMAL(10,5), "+
                    "c_codmarca TEXT, "+
                    "c_codmodelo TEXT, "+
                    "n_pyear BIGINT , "+
                    "c_tiempouso TEXT, "+
                    "c_facturaRef TEXT, "+
                    "d_fechaFacRef TEXT, "+
                    "c_prediagnostico TEXT, " +
                    "n_formato BIGINT, "+
                    "d_fechaformato TEXT, "+
                    "c_obscliente TEXT, "+
                    "c_flagvisita TEXT, "+
                    "c_tiporeclamo TEXT, "+
                    "c_reembcliente TEXT, "+
                    "c_placavehic TEXT, "+
                    "n_montoreembcli DECIMAL(10,5), "+
                    "c_monedareembcli TEXT, "+
                    "c_pruebalab1 TEXT, "+
                    "c_pruebalab2 TEXT, "+
                    "c_pruebalab3 TEXT, "+
                    "c_ensayo01 TEXT, "+
                    "c_ensayo02 TEXT, "+
                    "c_ensayo03 TEXT, "+
                    "c_ensayo04 TEXT, "+
                    "c_ensayo05 TEXT , " +
                    "c_enviado TEXT);";

    // TABLA TMA_CALIFICACIONQUEJA
    public static final String TMA_CALIFICACIONQUEJA_NAME = "TMA_CALIFICACIONQUEJA";
    public static final String TMA_CALIFICACIONQUEJA_SQL =
            "CREATE TABLE " + TMA_CALIFICACIONQUEJA_NAME +
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_calificacion TEXT, "+
                    "c_descripcion TEXT, "+
                    "c_usuarioderivacion TEXT, "+
                    "c_correo TEXT, "+
                    "c_estado TEXT);";

    // TABLA TMA_TIPOCALIFICACIONQUEJA
    public static final String TMA_TIPOCALIFICACIONQUEJA_NAME = "TMA_TIPOCALIFICACIONQUEJA";
    public static final String TMA_TIPOCALIFICACIONQUEJA_SQL =
            "CREATE TABLE " + TMA_TIPOCALIFICACIONQUEJA_NAME +
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_tipocalificacion TEXT, "+
                    "c_calificacion TEXT, "+
                    "c_descripcion TEXT, "+
                    "c_estado TEXT);";

    // TABLA TMA_MEDIORECEPCION
    public static final String TMA_MEDIORECEPCION_NAME = "TMA_MEDIORECEPCION";
    public static final String TMA_MEDIORECEPCION_SQL =
            "CREATE TABLE " + TMA_MEDIORECEPCION_NAME +
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_mediorecepcion TEXT, "+
                    "c_descripcion TEXT, "+
                    "c_estado TEXT);";

    // TABLA TMA_ACCIONESTOMAR
    public static final String TMA_ACCIONESTOMAR_NAME = "TMA_ACCIONESTOMAR";
    public static final String TMA_ACCIONESTOMAR_SQL =
            "CREATE TABLE " + TMA_ACCIONESTOMAR_NAME +
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_codaccion TEXT, "+
                    "c_descripcion TEXT, "+
                    "c_estado TEXT);";

    // TABLA TMA_NOTIFICAQUEJA
    public static final String TMA_NOTIFICAQUEJA_NAME = "TMA_NOTIFICAQUEJA";
    public static final String TMA_NOTIFICAQUEJA_SQL =
            "CREATE TABLE " + TMA_NOTIFICAQUEJA_NAME +
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_notificacion TEXT, "+
                    "c_descripcion TEXT, "+
                    "c_envianot TEXT, "+
                    "c_usuarionot TEXT, "+
                    "c_estado TEXT);";

    // Tabla Queja Cliente
    public static final String MCO_QUEJA_CLIENTE_NAME = "MCO_QUEJAS";
    public static final String  MCO_QUEJA_CLIENTE_SQL  =
            "CREATE TABLE "+ MCO_QUEJA_CLIENTE_NAME +
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_compania TEXT, "+
                    "n_correlativo BIGINT, "+
                    "c_queja TEXT, " +
                    "c_nroformato TEXT, " +
                    "n_cliente BIGINT, "+
                    "d_fechareg TEXT, " +
                    "c_documentoref TEXT, "+
                    "c_mediorecepcion TEXT, "+
                    "c_centrocosto TEXT, "+
                    "c_calificacion TEXT, "+
                    "c_usuarioderiv TEXT, "+
                    "c_tipocalificacion TEXT, "+
                    "c_item TEXT, "+
                    "c_lote TEXT, "+
                    "n_cantidad DECIMAL(10,5) , "+
                    "c_desqueja TEXT, "+
                    "c_observaciones TEXT, "+
                    "c_usuario TEXT, "+
                    "c_estado TEXT, " +
                    "c_enviado TEXT);";
    //tabla TMA_TIPOSUGERENCIA
    public static final String TMA_TIPOSUGERENCIA_NAME = "TMA_TIPOSUGERENCIA";
    public static final String TMA_TIPOSUGERENCIA_SQL =
            "CREATE TABLE "+ TMA_TIPOSUGERENCIA_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_tiposugerencia  TEXT, "+
                    "c_descripcion TEXT, "+
                    "c_estado TEXT );";

    //tabla TMA_TEMACAPACITACION
    public static final String TMA_TEMACAPACITACION_NAME = "TMA_TEMACAPACITACION";
    public static final String TMA_TEMACAPACITACION_SQL =
            "CREATE TABLE "+ TMA_TEMACAPACITACION_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_temacapacitacion  TEXT, "+
                    "c_descripcion TEXT, "+
                    "c_estado TEXT );";

    //tabla TMA_DIRECCIONCLI
    public static final String TMA_DIRECCIONCLI_NAME = "TMA_DIRECCIONCLI";
    public static final String TMA_DIRECCIONCLI_SQL =
            "CREATE TABLE "+ TMA_DIRECCIONCLI_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_compania TEXT, "+
                    "n_cliente INTEGER, "+
                    "n_secuencia INTEGER, "+
                    "c_descripcion TEXT, "+
                    "c_estado TEXT );";

    // Tabla Sugerencia
    public static final String MCO_SUGERENCIA_NAME = "MCO_SUGERENCIA";
    public static final String MCO_SUGERENCIA_SQL  =
            "CREATE TABLE "+ MCO_SUGERENCIA_NAME +
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_compania TEXT, "+
                    "n_correlativo BIGINT, "+
                    "c_tipoinfo TEXT," +
                    "n_identinfo BIGINT," +
                    "n_cliente BIGINT, " +
                    "d_fechareg TEXT, " +
                    "c_tiposug TEXT, "+
                    "c_descripcion TEXT, "+
                    "c_observacion TEXT, "+
                    "c_usuario TEXT, "+
                    "c_estado TEXT, " +
                    "c_enviado TEXT);";

    // Tabla Capacitacion
    public static final String MCO_SOLCAPACITACION_NAME = "MCO_SOLCAPACITACION";
    public static final String MCO_SOLCAPACITACION_SQL  =
            "CREATE TABLE "+ MCO_SOLCAPACITACION_NAME +
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_compania TEXT, "+
                    "n_correlativo BIGINT, "+
                    "n_cliente BIGINT, " +
                    "d_fechareg TEXT, " +
                    "n_personas INTEGER, "+
                    "d_fechaprob TEXT, " +
                    "d_horaprob TEXT, " +
                    "c_lugar TEXT, " +
                    "n_direccion INTEGER, " +
                    "c_direccionreg TEXT, " +
                    "c_temacapacitacion TEXT, "+
                    "c_descripciontema TEXT, "+
                    "c_observacion TEXT, "+
                    "c_usuario TEXT, "+
                    "c_estado TEXT, " +
                    "c_enviado TEXT);";

    public static final String MCO_DOCS_RECLAMOGARANTIA_NAME = "MCO_DOCS_RECLAMOGARANTIA";
    public static final String MCO_DOCS_RECLAMOGARANTIA_SQL =
            "CREATE TABLE "+ MCO_DOCS_RECLAMOGARANTIA_NAME +
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_compania TEXT, "+
                    "n_numero BIGINT, " +
                    "n_linea BIGINT, " +
                    "c_descripcion TEXT, "+
                    "c_nombre_archivo TEXT, "+
                    "c_ruta_archivo TEXT, "+
                    "c_ultimousuario TEXT, "+
                    "d_ultimafechamodificacion TEXT); ";

    public static final String MCO_DOCS_QUEJACLIENTE_NAME = "MCO_DOCS_QUEJACLIENTE";
    public static final String MCO_DOCS_QUEJACLIENTE_SQL =
            "CREATE TABLE "+ MCO_DOCS_QUEJACLIENTE_NAME +
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_compania TEXT, "+
                    "n_queja BIGINT, " +
                    "n_linea BIGINT, " +
                    "c_descripcion TEXT, "+
                    "c_nombre_archivo TEXT, "+
                    "c_ruta_archivo TEXT, "+
                    "c_ultimousuario TEXT, "+
                    "d_ultimafechamodificacion TEXT); ";

    public static final String MCO_DOCS_SUGERENCIA_NAME = "MCO_DOCS_SUEGERENCIA";
    public static final String MCO_DOCS_SUGERENCIA_SQL =
            "CREATE TABLE "+ MCO_DOCS_SUGERENCIA_NAME +
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_compania TEXT, "+
                    "n_sugerencia BIGINT, " +
                    "n_linea BIGINT, " +
                    "c_descripcion TEXT, "+
                    "c_nombre_archivo TEXT, "+
                    "c_ruta_archivo TEXT, "+
                    "c_ultimousuario TEXT, "+
                    "d_ultimafechamodificacion TEXT); ";

    public static final String MCO_DOCS_CAPACITACION_NAME = "MCO_DOCS_CAPACITACION";
    public static final String MCO_DOCS_CAPACITACION_SQL =
            "CREATE TABLE "+ MCO_DOCS_CAPACITACION_NAME +
                    " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "c_compania TEXT, "+
                    "n_solicitud BIGINT, " +
                    "n_linea BIGINT, " +
                    "c_descripcion TEXT, "+
                    "c_nombre_archivo TEXT, "+
                    "c_ruta_archivo TEXT, "+
                    "c_ultimousuario TEXT, "+
                    "d_ultimafechamodificacion TEXT); ";

}







