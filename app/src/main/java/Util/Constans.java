package Util;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.filtroslys.filtroslysapp.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by dvillanueva on 04/08/2016.
 */
public class Constans {

    public static String BS_PACKAGE = "com.google.zxing.client.android";
    public static String UrlServer = ReaderFielIpServer();//"http://10.0.2.2:8086/SOAPLYS?wsdl";
    public static String UrlServerExterno = ReaderFileExterno();
    public static String UrlServerLocal = ReaderFileLocal();  //"http://10.0.2.2:8086/SOAPLYS?wsdl";
    public static String NameSpaceWS = "http://SOAP/";
    // public  static  String usercod = ReaderFileLocal();
    public static int icon_error = R.drawable.ic_error_white_24dp;
    public static  int layout_error = R.drawable.toast_error_shape;
    public static  int icon_warning = R.drawable.ic_warning_black_24dp;
    public static  int layot_warning = R.drawable.toast_warning_shape;
    public static  int layout_success = R.drawable.lienar_layout_border;
    public static  int icon_succes = R.drawable.ic_done_24dp;
    public static  String Carpeta_foto= "LysConfig/Fotos/";
    public static final String NroConpania = "00100000";
    public static  final  String FolderConfig ="appConfig";
    public static  final  String IPDefault  = "190.187.181.56:80" ;  //"100.100.100.57:8080" ;// "190.187.181.56:80";
    public static  final  String FolderWs = "LysWsRest" ;

    public static void SetConexion(String LocalOExt, String con) {

        String filename = "";
        if (LocalOExt.equals("E")) {
            filename = FolderConfig+File.separator+ "ext.txt";
        } else if (LocalOExt.equals("L")) {
             filename = FolderConfig+File.separator+ "local.txt";
        } else if (LocalOExt.equals("P")) {

            filename = "con.txt";
        }
        try {
            Clear();
            File root = new File(Environment.getExternalStorageDirectory() + File.separator + filename);
            // File gpxfile = new File(root, "tex.txt");
            FileWriter writer = new FileWriter(root);
            writer.append(con);
            writer.flush();
            writer.close();
        } catch (IOException e) {
        }
    }


    public static String ReaderFileExterno() {
        String aBuffer = "";
        try {
            File myFile = new File(Environment.getExternalStorageDirectory() +  File.separator + FolderConfig + File.separator + "ext.txt");
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
            String aDataRow = "";
            //    String aBuffer = "";
            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer += aDataRow + "\n";
            }
            myReader.close();
        } catch (IOException e) {
        }



        return aBuffer.toString().trim();

    }

    public static String ReaderFileLocal() {
        String aBuffer = "";
        try {
            File myFile = new File(Environment.getExternalStorageDirectory() + File.separator + FolderConfig+File.separator + "local.txt");
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
            String aDataRow ;
            //    String aBuffer = "";
            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer += aDataRow + "\n";
            }
            myReader.close();
        } catch (IOException e) {
        }

        return aBuffer.toString().trim();

    }

    public static String ReaderFielIpServer() {
        String aBuffer = "";
        try {
            File myFile = new File(Environment.getExternalStorageDirectory() + File.separator + "con.txt");
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
            String aDataRow = "";
            //    String aBuffer = "";
            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer += aDataRow + "\n";
            }
            myReader.close();
        } catch (IOException e) {
        }

        return aBuffer.toString().trim();

    }


    public static void Clear() {

        try {


            File myFile = new File(Environment.getExternalStorageDirectory() + File.separator + "con.txt");
            if (!myFile.exists()) {
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.write("");

                myOutWriter.close();
                fOut.close();
            }

        } catch (IOException e) {

            e.printStackTrace();
        }


    }
    public static void ifExistCreateFile(String TipoIp , String sIP) {
        try {

            File folder = new  File(Environment.getExternalStorageDirectory() + File.separator + FolderConfig );
            folder.mkdirs();

            String redval = ReaderFileLocal();
            if ( (redval.toString() == null || redval == "")  && TipoIp.equals("LO")) {
                //  File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.txt");

                String data = "http://"+sIP+"/"+FolderWs+"/SOAPLYS?wsdl/";
                Log.i("Ip local   : " , data );

                File myFile = new File(Environment.getExternalStorageDirectory() + File.separator + FolderConfig +File.separator + "local.txt");
                //    if (!myFile.exists()) {
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(data);
                myOutWriter.close();
                fOut.close();
                // }
            }
            String redvalext = ReaderFileExterno();
            if ((redvalext.toString() == null || redvalext == "") && TipoIp.equals("EX") ) {
                //  File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.txt");

                String data = "http://"+sIP+"/"+FolderWs+"/SOAPLYS?wsdl/";
                Log.i("Ip Ext   : " , data );

                File myFile = new File(Environment.getExternalStorageDirectory() +  File.separator + FolderConfig +File.separator + "ext.txt");
                //    if (!myFile.exists()) {
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(data);
                myOutWriter.close();
                fOut.close();
                // }
            }

            String con = ReaderFielIpServer();
            if ((con.toString() == null || con == "") && TipoIp.equals("CON") ) {
                //  File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.txt");

                String data = "http://"+IPDefault+"/"+FolderWs+"/SOAPLYS?wsdl/";
                Log.i("Ip CON   : " , data );

                File myFile = new File(Environment.getExternalStorageDirectory() + File.separator + "con.txt");
                //    if (!myFile.exists()) {
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(data);
                myOutWriter.close();
                fOut.close();
                // }
            }

            // Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {


        }


    }






}
