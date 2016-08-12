package Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

import DataBase.ConstasDB;
import DataBase.DBHelper;
import DataBase.InspeccionDB;
import DataBase.MaquinaDB;
import DataBase.PeriodoInspeccionDB;
import DataBase.ProdMantDataBase;

/**
 * Created by dvillanueva on 08/08/2016.
 */
public class SincronizarMaestrosTask extends AsyncTask<Void,Void,Void> {

    ProgressDialog progressDialog ;
    Context context ;
    ArrayList<MaquinaDB> listMaquina  ;
    ArrayList<PeriodoInspeccionDB> listPeriodos;
    ArrayList<InspeccionDB> listInspecciones;

    public SincronizarMaestrosTask(Context context, ProgressDialog progressDialog, ArrayList<MaquinaDB> listMaquina,
                                   ArrayList<PeriodoInspeccionDB> listPeriodos, ArrayList<InspeccionDB>listInspecciones) {
        this.context = context;
        this.listMaquina = listMaquina;
        this.progressDialog = progressDialog;
        this.listPeriodos = listPeriodos;
        this.listInspecciones = listInspecciones;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ProdMantDataBase prodMantDataBase = new ProdMantDataBase(context);

        // delete tables
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase dbBase = dbHelper.getWritableDatabase();
        dbBase.execSQL("DELETE FROM "+ ConstasDB.TABLA_MTP_MAQUINAS_NAME);
        dbBase.execSQL("DELETE FROM "+ ConstasDB.TABLA_MTP_PERIODO_INSPECCION_NAME);
        dbBase.execSQL("DELETE FROM "+ ConstasDB.TABLA_MTP_INSPECCION_NAME);
        dbBase.close();
        // prodMantDataBase.deleteTableMaquina();

        //*****

        if (listMaquina!=null && listMaquina.size()>0){

            for (int i = 0  ; i<listMaquina.size();i++){

                prodMantDataBase.InsertMaquina(listMaquina.get(i));

            }

        }

        if (listPeriodos!=null && listPeriodos.size()>0){

            for (int i = 0 ; i<listPeriodos.size();i++){

                prodMantDataBase.InjsertPeriodos(listPeriodos.get(i));
            }
        }

        if (listInspecciones!=null && listInspecciones.size()>0){

            for (int i = 0; i < listInspecciones.size() ; i++) {

                prodMantDataBase.InsertInspeccion(listInspecciones.get(i));
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        Toast.makeText(context, "Sincronizacioón terminada", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }
}