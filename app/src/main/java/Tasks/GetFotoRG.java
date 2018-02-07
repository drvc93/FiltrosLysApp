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
 * Creado por dvillanueva el  06/02/2018 (FiltrosLysApp).
 */

public class GetFotoRG extends AsyncTask<String,String,byte[]> {
    byte[] result;
    @Override
    protected byte[] doInBackground(String... strings) {
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetFotoRG";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("filename", strings[0]);
        request.addProperty("tipo", strings[1]);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try {
            transporte.call(SOAP_ACTION, envelope);

            SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
            String res = resultado_xml.toString();
            result = res.getBytes();
            Log.i(" byte resoap > ", res);
            Log.i(" byte result convert > ", String.valueOf(result));



        } catch (Exception e) {
            Log.i("error get foto", e.getMessage());
            //result = e.getMessage();
        }

        return result;
    }
}
