package Tasks;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.filtroslys.filtroslysapp.MenuOpciones;

import java.util.ArrayList;

import DataBase.AccesosDB;
import DataBase.ConstasDB;
import DataBase.DBHelper;
import DataBase.MenuDB;
import DataBase.ProdMantDataBase;
import DataBase.UsuarioDB;
import Model.EventoAuditoriaAPP;
import Model.IMEMovil;
import Util.Constans;
import Util.Funciones;
import spencerstudios.com.fab_toast.FabToast;

/**
 * Created by dvillanueva on 08/08/2016.
 */
public class SincronizarAccesosTask extends AsyncTask<Void, Void, Void> {

    SharedPreferences preferences;
    ProgressDialog progressDialog;
    Context context;
    ArrayList<MenuDB> listMenu;
    ArrayList<AccesosDB> listAcceso;
    ArrayList<UsuarioDB> listUsuario;
    String usuarioSolicitud;
    IMEMovil oEImeiMovil;
    String codUser = "";

    public SincronizarAccesosTask(Context context, ArrayList<AccesosDB> listAcceso, ArrayList<MenuDB> listMenu, ArrayList<UsuarioDB> listUsuario, ProgressDialog progressDialog, String UserSolict, IMEMovil oEImeiMovil) {
        this.context = context;
        this.listAcceso = listAcceso;
        this.listMenu = listMenu;
        this.listUsuario = listUsuario;
        this.progressDialog = progressDialog;
        usuarioSolicitud = UserSolict;
        this.oEImeiMovil = oEImeiMovil;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        codUser = preferences.getString("UserCod", null);
    }

    @Override
    protected Void doInBackground(Void... string) {

        DBHelper dbHelper = new DBHelper(context);
        int contadorIMEI = 0;
        SQLiteDatabase dbBase = dbHelper.getWritableDatabase();
        dbBase.execSQL("DELETE FROM " + ConstasDB.TABLA_MTP_USUARIO_NAME);
        dbBase.execSQL("DELETE FROM " + ConstasDB.TABLA_MTP_ACCESO_NAME);
        dbBase.execSQL("DELETE FROM " + ConstasDB.TABLA_MTP_MENUS_NAME);
        dbBase.close();

        // Inserts tables
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        @SuppressLint("MissingPermission") String numerocelular = telephonyManager.getLine1Number();
        @SuppressLint("MissingPermission")String seriechip = telephonyManager.getSimSerialNumber();
        @SuppressLint("MissingPermission") String imei_movil = telephonyManager.getDeviceId();
        EventoAuditoriaAPP event = new EventoAuditoriaAPP();
        event.setC_enviado("N");
        event.setC_movil(numerocelular);
        event.setC_imei(imei_movil);
        event.setC_seriechip(seriechip);
        event.setC_origen(Constans.OrigenAPP_Auditoria);


        ProdMantDataBase prodMantDataBase = new ProdMantDataBase(context);

        if (listUsuario!=null && listUsuario.size()>0){
            event.setC_accion("[SINCRONIZÓ USUARIOS]");
            event.setD_hora(Funciones.GetFechaActual());
            event.setC_codIntApp(codUser);
            event.setC_usuario(codUser);
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0 ; i <listUsuario.size(); i++){
                listUsuario.get(i).setCodigoUsuario(listUsuario.get(i).getCodigoUsuario().trim());
                prodMantDataBase.InsertUsuatios(listUsuario.get(i));
                Log.i( "usuarios"  +String.valueOf(i),listUsuario.get(i).getCodigoUsuario() + " | " + listUsuario.get(i).getClave());
            }
        }
        if (listMenu!=null && listMenu.size()>0){
            event.setC_accion("[SINCRONIZÓ MENU]");
            event.setD_hora(Funciones.GetFechaActual());
            event.setC_codIntApp(codUser);
            event.setC_usuario(codUser);
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0 ; i<listMenu.size();i++){
                prodMantDataBase.InsetrtMenus(listMenu.get(i));
            }
        }
        if (listAcceso!=null && listAcceso.size()>0){
            event.setC_accion("[SINCRONIZÓ ACCESOS]");
            event.setD_hora(Funciones.GetFechaActual());
            event.setC_codIntApp(codUser);
            event.setC_usuario(codUser);
            prodMantDataBase.InsertEventoAuidtoriaAPP(event);
            for (int i = 0  ; i <listAcceso.size(); i++){
                if (listAcceso.get(i).getUsuario().trim().equals(usuarioSolicitud)) {
                    prodMantDataBase.InsertAccesos(listAcceso.get(i));
                }
            }
        }
        contadorIMEI = prodMantDataBase.ExiteIMEI(oEImeiMovil.getC_imei());
        if (contadorIMEI>0){
            prodMantDataBase.UpdateIMEIMovil(oEImeiMovil);
        }
        else {
            prodMantDataBase.InsertIMEIMovil(oEImeiMovil);
        }
        return  null;
    }


    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        FabToast.makeText(context, "Se sincronizo correctamente.", FabToast.LENGTH_LONG, FabToast.SUCCESS,  FabToast.POSITION_DEFAULT).show();
        progressDialog.dismiss();
    }
}
