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
 * Created by dvillanueva on 17/08/2016.
 */
public class TransferirInspeccionTask extends AsyncTask<String, String, String> {


    @Override
    protected String doInBackground(String... strings) {
        String result = "0";

        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8086/SOAPLYS?wsdl";
        final String METHOD_NAME = "TrasnferirInsp";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("tipoIns", strings[0]);
        request.addProperty("correlativo", strings[1]);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try {
            transporte.call(SOAP_ACTION, envelope);

            SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
            String res = resultado_xml.toString();
            Log.i("mensaje insert transferir ", res);
            result = res;


        } catch (Exception e) {
            result = "0";
            Log.i("Error tranferir insp => ", e.getMessage());
        }

        return result;
    }
}
