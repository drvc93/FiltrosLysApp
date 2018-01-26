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



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }
}
