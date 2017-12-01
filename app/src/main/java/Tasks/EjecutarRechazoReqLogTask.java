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
 * Created by dvillanueva on 27/11/2017.
 */

public class EjecutarRechazoReqLogTask extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... strings) {
        String result = "ERROR";

        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8086/SOAPLYS?wsdl";
        final String METHOD_NAME = "EjecutarRechazo";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("compania", strings[0]);
        request.addProperty("nroreq", strings[1]);
        request.addProperty("razonrechazo", strings[2]);
        request.addProperty("descripcionrechazo", strings[3]);



        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try {
            transporte.call(SOAP_ACTION, envelope);

            SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
            String res = resultado_xml.toString();
            Log.i("Result Ejecutando Rechazo Req Log.> ", res);
            result = res;


        } catch (Exception e) {
            result = e.getMessage();
            Log.i("Error  Ejecutando  Rechazo Req Log> ", e.getMessage());
        }

        return result;
    }
}
