package Util;

import android.os.Environment;
import android.util.Log;
import com.filtroslys.filtroslysapp.R;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Creado por dvillanueva el  04/08/2016 (FiltrosLysApp).
 */
public class Constans {

    public static String BS_PACKAGE = "com.google.zxing.client.android";
    public static String UrlServer = ReaderFielIpServer();//"http://10.0.2.2:8086/SOAPLYS?wsdl";
    public static String UrlServerExterno = ReaderFileExterno();
    public static String UrlServerLocal = ReaderFileLocal();  //"http://10.0.2.2:8086/SOAPLYS?wsdl";
    public static String NameSpaceWS = "http://SOAP/";
    public static int icon_error = R.drawable.ic_error_white_24dp;
    public static  int layout_error = R.drawable.toast_error_shape;
    public static  int icon_warning = R.drawable.ic_warning_black_24dp;
    public static  int layot_warning = R.drawable.toast_warning_shape;
    public static  int layout_success = R.drawable.lienar_layout_border;
    public static  int icon_succes = R.drawable.ic_done_24dp;
    public static  String Carpeta_foto= "LysConfig/Fotos/";
    public static  String Carpeta_foto_RG= "LysConfig/FotosRG/";
    public static  String Carpeta_foto_SG= "LysConfig/FotosSG/";
    public static  String Carpeta_foto_QJ= "LysConfig/FotosQJ/";
    public static  String Carpeta_foto_CP= "LysConfig/FotosCP/";
    public static final String NroConpania = "00100000";
    private static  final  String FolderConfig ="appConfig";
    public static  final  String IPDefault  ="100.100.100.57:8080" ;//"190.187.181.56:80";// "100.100.100.57:8080" ;// "190.187.181.56:80";
    private static  final  String FolderWs =  GetNombreServicioWeb() ;// "LysWsRest" ;
    public static  final  String IpDescarga = "190.187.181.56";//"190.187.181.56";// "100.100.100.186:8080";
    public static  final  String OrigenAPP_Auditoria = "APPMOVILYS";
    public static  final  String FolderRepVent = "LysConfig/RepVent/";
    public static  final  String FolderDatosCli = "LysConfig/DatosCli/";
    public static void SetConexion(String LocalOExt, String con) {

        String filename = "";
        switch (LocalOExt) {
            case "E":
                filename = FolderConfig + File.separator + "ext.txt";
                break;
            case "L":
                filename = FolderConfig + File.separator + "local.txt";
                break;
            case "P":

                filename = "con.txt";
                break;
        }
        try {
            Clear();
            File root = new File(Environment.getExternalStorageDirectory() + File.separator + filename);
            FileWriter writer = new FileWriter(root);
            writer.append(con);
            writer.flush();
            writer.close();
        } catch (Exception e) {
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
                String data = "http://"+sIP+"/"+FolderWs+"/SOAPLYS?wsdl";
                Log.i("Ip local   : " , data );
                File myFile = new File(Environment.getExternalStorageDirectory() + File.separator + FolderConfig +File.separator + "local.txt");
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(data);
                myOutWriter.close();
                fOut.close();
            }
            String redvalext = ReaderFileExterno();
            if ((redvalext.toString() == null || redvalext == "") && TipoIp.equals("EX") ) {
                String data = "http://"+sIP+"/"+FolderWs+"/SOAPLYS?wsdl";
                Log.i("Ip Ext   : " , data );
                File myFile = new File(Environment.getExternalStorageDirectory() +  File.separator + FolderConfig +File.separator + "ext.txt");
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(data);
                myOutWriter.close();
                fOut.close();

            }

            String con = ReaderFielIpServer();
            if ((con == null || con == "") && TipoIp.equals("CON") ) {
                String data = "http://"+IPDefault+"/"+FolderWs+"/SOAPLYS?wsdl";
                Log.i("Ip CON   : " , data );
                File myFile = new File(Environment.getExternalStorageDirectory() + File.separator + "con.txt");
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(data);
                myOutWriter.close();
                fOut.close();
            }
        } catch (IOException e) {


        }


    }


    private static String GetNombreServicioWeb() {
        String sRuta = Environment.getExternalStorageDirectory() + File.separator + FolderConfig;
        String sFile =  sRuta + File.separator + "webNS.txt";
        String sNameSWeb = "LysWsRest";

        try {
            File myFile = new File(sFile);
            if (!myFile.exists()) {
                myFile = new File(sRuta);
                if (myFile.list() == null)
                    myFile.mkdirs();

                myFile = new File(sFile);
                myFile.createNewFile();

                FileOutputStream Fout = new FileOutputStream(myFile);
                OutputStreamWriter Osw = new OutputStreamWriter(Fout);
                sNameSWeb = "LysWsRest";
                Osw.append(sNameSWeb);
                Osw.flush();
                Osw.close();
                Fout.close();
            }else{

                FileInputStream Fin = new FileInputStream(myFile);
                InputStreamReader archivo = new InputStreamReader(Fin);
                BufferedReader br = new BufferedReader(archivo);
                String sValor = br.readLine();
                if (sValor != null){
                    sNameSWeb = sValor;
                }
                br.close();
                archivo.close();
            }
            myFile = null;
        } catch (IOException e) {
        }
        return sNameSWeb;
    }






}
