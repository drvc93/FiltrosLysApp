package DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import Util.Constans;

/**
 * Created by dvillanueva on 04/08/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, ConstasDB.DB_NAME, null, ConstasDB.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(ConstasDB.TABLA_MTP_ACCESO_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TABLA_MTP_MENUS_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TABLA_MTP_USUARIO_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TABLA_MTP_MAQUINAS_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TABLA_MTP_PERIODO_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TABLA_MTP_INSPECCION_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TABLA_MTP_INSPECCIONMAQUINA_CAB_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TABLA_MTP_INSPECCIONESMAQUINA_DET_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TABLA_MTP_CENTROCOSTO_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TABLA_MTP_TIPOREVISION_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TABLA_MTP_INSPECCIONGENERAL_CAB_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TABLA_MTP_INSPECCIONGENERAL_DET_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TMA_CLIENTES_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TMA_FALL_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TMA_MARCA_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TMA_MODELO_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TMA_PRUEBALAB_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TMA_TIPORECLAMO_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TMA_VENDEDOR_SQL);
        sqLiteDatabase.execSQL(ConstasDB.MCO_RECLAMO_GARANTIA_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TMA_CALIFICACIONQUEJA_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TMA_TIPOCALIFICACIONQUEJA_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TMA_MEDIORECEPCION_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TMA_ACCIONESTOMAR_SQL);
        sqLiteDatabase.execSQL(ConstasDB.MCO_QUEJA_CLIENTE_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TMA_TIPOSUGERENCIA_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TMA_TEMACAPACITACION_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TMA_DIRECCIONCLI_SQL);
        sqLiteDatabase.execSQL(ConstasDB.MCO_SUGERENCIA_SQL);
        sqLiteDatabase.execSQL(ConstasDB.MCO_SOLCAPACITACION_SQL);
        sqLiteDatabase.execSQL(ConstasDB.MCO_DOCS_RECLAMOGARANTIA_SQL);
        sqLiteDatabase.execSQL(ConstasDB.MCO_DOCS_QUEJACLIENTE_SQL);
        sqLiteDatabase.execSQL(ConstasDB.MCO_DOCS_SUGERENCIA_SQL);
        sqLiteDatabase.execSQL(ConstasDB.TMA_NOTIFICAQUEJA_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }
}
