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
 * Creado por dvillanueva el  02/03/2018 (FiltrosLysApp).
 */

public class InsertIMEMovilTask extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... strings) {
        String result = "0";

        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        final String METHOD_NAME = "InsertIMEIMovil";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("imei", strings[0]);
        request.addProperty("tipo", strings[1]);
        request.addProperty("numero", strings[2]);
        request.addProperty("usuarioreg", strings[3]);
        request.addProperty("estado", strings[4]);
        request.addProperty("ultusuario", strings[5]);
        request.addProperty("ultfecha", strings[6]);
        request.addProperty("seriechip", strings[7]);

        Log.i("imei", strings[0]);
        Log.i("tipo", strings[1]);
        Log.i("numero", strings[2]);
        Log.i("usuarioreg", strings[3]);
        Log.i("estado", strings[4]);
        Log.i("ultusuario", strings[5]);
        Log.i("ultfecha", strings[6]);
        Log.i("seriechip", strings[7]);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
            String res = resultado_xml.toString();
            Log.i("Result Insert IMEI Movil> ", res);
            result = res;

        } catch (Exception e) {
            result = e.getMessage();
            Log.i("Error  Insert IMEI Movil > ", e.getMessage());
        }

        return result;
    }
}
