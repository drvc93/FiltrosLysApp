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
 * Creado por dvillanueva el  01/03/2018 (FiltrosLysApp).
 */

public class ValidacionUsuarioIMEITask extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... strings) {
        String result = "0";

        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        final String METHOD_NAME = "ValidacionUsuarioIMEI";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("usuario", strings[0]);
        request.addProperty("clave", strings[1]);
        request.addProperty("imei", strings[2]);
        request.addProperty("numeromov", strings[3]);
        request.addProperty("seriechip", strings[4]);


        Log.i("usuario", strings[0]);
        Log.i("clave", strings[1]);
        Log.i("imei", strings[2]);
        Log.i("numeromov", strings[3]);
        Log.i("seriechip", strings[4]);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
            String res = resultado_xml.toString();
            Log.i("Result Validacion Usuario IMEI Online> ", res);
            result = res;

        } catch (Exception e) {
            result = e.getMessage();
            Log.i("Error   Validacion Usuario IMEI Online > ", e.getMessage());
        }

        return result;
    }
}
