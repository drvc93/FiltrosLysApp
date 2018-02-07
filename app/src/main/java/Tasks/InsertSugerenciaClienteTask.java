package Tasks;

import android.os.AsyncTask;
import android.util.Log;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import Util.Constans;

public class InsertSugerenciaClienteTask extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... strings) {
        String result = "0";

        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        final String METHOD_NAME = "InsertSugerenciaCliente";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("compania", strings[0]);
        request.addProperty("correlativo", strings[1]);
        request.addProperty("accion", strings[2]);
        request.addProperty("usuario", strings[3]);
        request.addProperty("fecha", strings[4]);
        request.addProperty("cliente", strings[5]);
        request.addProperty("tiposugerencia", strings[6]);
        request.addProperty("descripcion", strings[7]);
        request.addProperty("estado", strings[8]);
        request.addProperty("tipoinfo", strings[9]);
        request.addProperty("observacion", strings[10]);

        Log.i("compania", strings[0]);
        Log.i("correlativo", strings[1]);
        Log.i("accion", strings[2]);
        Log.i("usuario", strings[3]);
        Log.i("fecha", strings[4]);
        Log.i("cliente", strings[5]);
        Log.i("tiposugerencia", strings[6]);
        Log.i("descripcion", strings[7]);
        Log.i("estado", strings[8]);
        Log.i("tipoinfo", strings[9]);
        Log.i("observacion", strings[10]);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
            String res = resultado_xml.toString();
            Log.i("Result Insert Sugerencia Cliente> ", res);
            result = res;

        } catch (Exception e) {
            result = e.getMessage();
            Log.i("Error Insert Sugerencia Cliente> ", e.getMessage());
        }

        return result;
    }
}
