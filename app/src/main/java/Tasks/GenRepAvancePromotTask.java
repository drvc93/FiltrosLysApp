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
 * Creado por dvillanueva el  19/04/2018 (FiltrosLysApp).
 */

public class GenRepAvancePromotTask extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... strings)
    {
        String result = "ERROR";

        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8086/SOAPLYS?wsdl";
        final String METHOD_NAME = "GenHemtlRepVentPromo";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("compania", strings[0]);
        request.addProperty("ciasecundaria", strings[1]);
        request.addProperty("undnegoc", strings[2]);
        request.addProperty("vendedor", strings[3]);
        request.addProperty("periodo", strings[4]);
        request.addProperty("moneda", strings[5]);
        request.addProperty("usuario", strings[6]);
        request.addProperty("formato", strings[7]);

        Log.i("compania", strings[0]);
        Log.i("ciasecundaria", strings[1]);
        Log.i("undnegoc", strings[2]);
        Log.i("vendedor", strings[3]);
        Log.i("periodo", strings[4]);
        Log.i("moneda", strings[5]);
        Log.i("usuario", strings[6]);
        Log.i("formato", strings[7]);



        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try {
            transporte.call(SOAP_ACTION, envelope);

            SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
            String res = resultado_xml.toString();
            Log.i("Result HTML Rep AVC.> ", res);
            result = res;


        } catch (Exception e) {
            result = e.getMessage();
            Log.i("Error   HTML Rep AVC> ", e.getMessage());
        }

        return result;
    }
}
