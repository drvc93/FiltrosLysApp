package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import DataBase.MaquinaDB;
import Util.Constans;

/**
 * Created by dvillanueva on 15/08/2016.
 */
public class EnviarInspMaqCabTask extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... strings) {
        String result = "ERROR";

        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "InsertInspMaqCab";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("correlativo", strings[0]);
        request.addProperty("compania", strings[1]);
        request.addProperty("maquina", strings[2]);
        request.addProperty("condicionMaquina", strings[3]);
        request.addProperty("comentario" , strings[4]);
        request.addProperty("estado" , strings[5]);
        request.addProperty("fechaIniInsp" , strings[6]);
        request.addProperty("fechaFinInsp" , strings[7]);
        request.addProperty("periodoInsp" , strings[8]);
        request.addProperty("usuarioInsp" , strings[9]);
        request.addProperty("usuaruioEnv" , strings[10]);
        request.addProperty("ultimoUsuario" , strings[11]);



        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try
        {
            transporte.call(SOAP_ACTION, envelope);

            SoapPrimitive resultado_xml =(SoapPrimitive)envelope.getResponse();
            String res = resultado_xml.toString();
            Log.i("mensaje insert maquina cab", res);
            result=res;


        }
        catch (Exception e)
        {
            result = e.getMessage();
        }

        return result;
    }
}
