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
 * Created by dvillanueva on 28/11/2017.
 */

public class LoginOnlineTask extends AsyncTask<String,String,String>
{
    @Override
    protected String doInBackground(String... strings) {
        String result = "ERROR";

        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8086/SOAPLYS?wsdl";
        final String METHOD_NAME = "LoginUser";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("usuario", strings[0]);
        request.addProperty("clave", strings[1]);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try {
            transporte.call(SOAP_ACTION, envelope);

            SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
            String res = resultado_xml.toString();
            Log.i("Login result : ", res);
            result = res;


        } catch (Exception e) {
            result = "0";
            Log.i("Login Error  ", e.getMessage());
        }

        return result;
    }
}
