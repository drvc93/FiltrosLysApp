package Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

import DataBase.AccesosDB;
import DataBase.ConstasDB;
import DataBase.DBHelper;
import DataBase.MenuDB;
import DataBase.ProdMantDataBase;
import DataBase.UsuarioDB;
import Util.Constans;

/**
 * Created by dvillanueva on 08/08/2016.
 */
public class SincronizarAccesosTask  extends AsyncTask<Void,Void,Void>{


    ProgressDialog progressDialog;
    Context context ;
    ArrayList<MenuDB> listMenu;
    ArrayList<AccesosDB> listAcceso;
    ArrayList<UsuarioDB> listUsuario;

    public SincronizarAccesosTask(Context context, ArrayList<AccesosDB> listAcceso, ArrayList<MenuDB> listMenu, ArrayList<UsuarioDB> listUsuario, ProgressDialog progressDialog) {
        this.context = context;
        this.listAcceso = listAcceso;
        this.listMenu = listMenu;
        this.listUsuario = listUsuario;
        this.progressDialog = progressDialog;
    }

    @Override
    protected Void doInBackground(Void... string) {

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase dbBase = dbHelper.getWritableDatabase();
        dbBase.execSQL("DELETE FROM "+ ConstasDB.TABLA_MTP_USUARIO_NAME);
        dbBase.execSQL("DELETE FROM "+ ConstasDB.TABLA_MTP_ACCESO_NAME);
        dbBase.execSQL("DELETE FROM "+ ConstasDB.TABLA_MTP_MENUS_NAME);
        dbBase.close();

        // Inserts tables

        ProdMantDataBase prodMantDataBase = new ProdMantDataBase(context);

        if (listUsuario!=null && listUsuario.size()>0){

            for (int i = 0 ; i <listUsuario.size(); i++){

                prodMantDataBase.InsertUsuatios(listUsuario.get(i));
               // publishProgress(String.valueOf(i));
            }
        }

        if (listMenu!=null && listMenu.size()>0){

            for (int i = 0 ; i<listMenu.size();i++){

                prodMantDataBase.InsetrtMenus(listMenu.get(i));
            }

        }

        if (listAcceso!=null && listAcceso.size()>0){

            for (int i = 0  ; i <listAcceso.size(); i++){

                prodMantDataBase.InsertAccesos(listAcceso.get(i));

            }

        }

        return  null;
    }


    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Toast.makeText(context, "Sincronización correcta.", Toast.LENGTH_SHORT).show();
       progressDialog.dismiss();
    }
}