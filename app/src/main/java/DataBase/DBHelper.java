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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }
}
