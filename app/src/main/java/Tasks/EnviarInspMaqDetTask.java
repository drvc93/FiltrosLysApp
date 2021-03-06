package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import Util.Constans;

/**
 * Created by dvillanueva on 15/08/2016.
 */
public class EnviarInspMaqDetTask extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... strings) {
        String result = "0";

        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8086/SOAPLYS?wsdl";
        final String METHOD_NAME = "InsertInspMaqDet";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("compania", strings[0]);
        request.addProperty("correlativo", strings[1]);
        request.addProperty("linea", strings[2]);
        request.addProperty("codInspeccion", strings[3]);
        request.addProperty("tipoInsp" , strings[4]);
        request.addProperty("porcentMin" , strings[5]);
        request.addProperty("porcentMax" , strings[6]);
        request.addProperty("porcentInsp" , strings[7]);
        request.addProperty("estado" , strings[8]);
        request.addProperty("comentario" , strings[9]);
        request.addProperty("rutafoto" , strings[10]);
        request.addProperty("ultimoUser" , strings[11]);



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
            Log.i("mensaje insert maquina det", res);
            result=res;


        }
        catch (Exception e)
        {
            result ="0";
            Log.i("Error inser insp det => " , e.getMessage());
        }

        return result;
    }
}
