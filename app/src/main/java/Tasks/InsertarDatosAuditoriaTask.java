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
 * Creado por dvillanueva el  28/02/2018 (FiltrosLysApp).
 */

public class InsertarDatosAuditoriaTask extends AsyncTask<String,String,String> {


    @Override
    protected String doInBackground(String... strings) {
        String result = "0";

        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        final String METHOD_NAME = "InsertDatosAuditoria";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("origen", strings[0]);
        request.addProperty("codIntApp", strings[1]);
        request.addProperty("tipo", strings[2]);
        request.addProperty("imei", strings[3]);
        request.addProperty("movil", strings[4]);
        request.addProperty("usuario", strings[5]);
        request.addProperty("hora", strings[6]);
        request.addProperty("seriechip", strings[7]);

        Log.i("origen", strings[0]);
        Log.i("codIntApp", strings[1]);
        Log.i("tipo", strings[2]);
        Log.i("imei", strings[3]);
        Log.i("movil", strings[4]);
        Log.i("usuario", strings[5]);
        Log.i("hora", strings[6]);
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
            Log.i("Result Insert Dato Auditoria Cliente> ", res);
            result = res;

        } catch (Exception e) {
            result = e.getMessage();
            Log.i("Error  Insert Dato Auditoria> ", e.getMessage());
        }

        return result;
    }
}
