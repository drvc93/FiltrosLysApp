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
 * Creado por dvillanueva el  09/02/2018 (FiltrosLysApp).
 */

public class InsertDocsSugerenciaTask extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... strings) {
        String result = "0";

        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8086/SOAPLYS?wsdl";
        final String METHOD_NAME = "InsertDocsSugerencia";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("compania", strings[0]);
        request.addProperty("correlativo", strings[1]);
        request.addProperty("linea", strings[2]);
        request.addProperty("descripcion", strings[3]);
        request.addProperty("nombrearchivo", strings[4]);
        request.addProperty("rutaarchivo", strings[5]);
        request.addProperty("ultusuario", strings[6]);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try {
            transporte.call(SOAP_ACTION, envelope);

            SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
            String res = resultado_xml.toString();
            Log.i("Result Insert  Docs SG. > ", res);
            result = res;


        } catch (Exception e) {
            result = e.getMessage();
            Log.i("Error Insert  Docs SG. > ", e.getMessage());
        }

        return result;
    }
}
