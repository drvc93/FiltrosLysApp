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
 * Created by dvillanueva on 22/08/2016.
 */
public class EnviarInspGenCabTask extends AsyncTask<String, String, String> {
    String result = "0";

    @Override
    protected String doInBackground(String... strings) {

        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "InsertInspGenCab";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("compania", strings[0]);
        request.addProperty("correlativo", strings[1]);
        request.addProperty("tipoInsp", strings[2]);
        request.addProperty("codMaq", strings[3]);
        request.addProperty("codCentroC", strings[4]);
        request.addProperty("comentario", strings[5]);
        request.addProperty("usuarioInsp", strings[6]);
        request.addProperty("fechaInsp", strings[7]);
        request.addProperty("estado", strings[8]);
        request.addProperty("usuarioEnv", strings[9]);
        request.addProperty("UltUsuario", strings[10]);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try {
            transporte.call(SOAP_ACTION, envelope);

            SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
            String res = resultado_xml.toString();
            Log.i("mensaje envio  insp gen cab", res);
            result = res;


        } catch (Exception e) {
            result = "0";
            Log.i("Error envio insp gen cab  => ", e.getMessage());
        }

        return result;


    }
}
