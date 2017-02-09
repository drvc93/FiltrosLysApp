package Util;

import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

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

    public static void SetConexion(String LocalOExt, String con) {

        String filename = "";
        if (LocalOExt.equals("E")) {
            filename = "ext.txt";
        } else if (LocalOExt.equals("L")) {
            filename = "local.txt";
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
            File myFile = new File(Environment.getExternalStorageDirectory() + File.separator + "ext.txt");
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
            File myFile = new File(Environment.getExternalStorageDirectory() + File.separator + "local.txt");
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

    public static void ifExistCreateFile() {

        boolean value;
        try {

            String redval = ReaderFileLocal();
            if (redval.toString() == null || redval == "") {
                //  File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.txt");

                String data = "http://100.100.100.57:8080/LysWsRest/SOAPLYS?wsdl/";


                File myFile = new File(Environment.getExternalStorageDirectory() + File.separator + "local.txt");
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
            if (redvalext.toString() == null || redvalext == "") {
                //  File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.txt");

                String data = "http://190.187.181.56:80/LysWsRest/SOAPLYS?wsdl/";


                File myFile = new File(Environment.getExternalStorageDirectory() + File.separator + "ext.txt");
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
            if (con.toString() == null || con == "") {
                //  File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.txt");

                String data = "http://100.100.100.57:8080/LysWsRest/SOAPLYS?wsdl/";


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
