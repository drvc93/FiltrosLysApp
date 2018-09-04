package Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Date;

import DataBase.ProdMantDataBase;
import Model.EventoAuditoriaAPP;

/**
 * Creado por dvillanueva el  06/03/2018 (FiltrosLysApp).
 */

public class Funciones {

    static  String FechaHoraActual = GetFechaActual();

    public  static String GetFechaActual (){
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("EST"));
        result = sdf.format(new Date()) ;
        Log.i("Fecha actucual",  result)  ;
        return  result;
    }

    public  static  String DatosTelefono(String tipoDato , Context context){
        String result = "";
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        @SuppressLint("MissingPermission") String numerocelular = telephonyManager.getLine1Number();
        @SuppressLint("MissingPermission")String seriechip = telephonyManager.getSimSerialNumber();
        @SuppressLint("MissingPermission") String imei_movil = telephonyManager.getDeviceId();

        switch (tipoDato){
            case "IMEI":
                result = imei_movil;
                break;
            case "SIM":
                result = seriechip;
                break;
            case "NUMERO" :
                result = numerocelular;
                break;
        }

        return  result;
    }

    public static  long InsertarTablaEventoApp (Context context,String accion){
        SharedPreferences preferences;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String  codUser = preferences.getString("UserCod", null);
        long result = 0;
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
        event.setD_hora(Funciones.GetFechaActual());
        event.setC_accion(accion);
        event.setC_usuario(codUser);
        ProdMantDataBase prodMantDataBase = new ProdMantDataBase(context);
        result = prodMantDataBase.InsertEventoAuidtoriaAPP(event);
        Log.i("ID insert evento app",String.valueOf(result));

        return  result;

    }
}
