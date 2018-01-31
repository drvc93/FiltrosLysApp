package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import Util.Constans;

public class InsertQuejaClienteTask extends AsyncTask<String,String,String> {

    @Override
    protected String doInBackground(String... strings) {
        String result = "0";

        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        final String METHOD_NAME = "InsertQuejaCliente";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("compania", strings[0]);
        request.addProperty("correlativo", strings[1]);
        request.addProperty("accion", strings[2]);
        request.addProperty("usuario", strings[3]);
        request.addProperty("nroformato", strings[4]);
        request.addProperty("cliente", strings[5]);
        request.addProperty("fecha", strings[6]);
        request.addProperty("documentoref", strings[7]);
        request.addProperty("mediorecepcion", strings[8]);
        request.addProperty("centrocosto", strings[9]);
        request.addProperty("calificacion", strings[10]);
        request.addProperty("usuarioderiv", strings[11]);
        request.addProperty("tipocalificacion", strings[12]);
        request.addProperty("item", strings[13]);
        request.addProperty("lote", strings[14]);
        request.addProperty("cantidad", strings[15]);
        request.addProperty("descqueja", strings[16]);
        request.addProperty("observaciones", strings[17]);
        request.addProperty("estado", strings[18]);

        Log.i("compania", strings[0]);
        Log.i("correlativo", strings[1]);
        Log.i("accion", strings[2]);
        Log.i("usuario", strings[3]);
        Log.i("usuario", strings[4]);
        Log.i("usuario", strings[5]);
        Log.i("usuario", strings[6]);
        Log.i("usuario", strings[7]);
        Log.i("usuario", strings[8]);
        Log.i("usuario", strings[9]);
        Log.i("usuario", strings[10]);
        Log.i("usuario", strings[11]);
        Log.i("usuario", strings[12]);
        Log.i("usuario", strings[13]);
        Log.i("usuario", strings[14]);
        Log.i("usuario", strings[15]);
        Log.i("usuario", strings[16]);
        Log.i("usuario", strings[17]);
        Log.i("usuario", strings[18]);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
            String res = resultado_xml.toString();
            Log.i("Result Insert Queja Cliente> ", res);
            result = res;

        } catch (Exception e) {
            result = e.getMessage();
            Log.i("Error Insert Queja Cliente> ", e.getMessage());
        }

        return result;
    }
}
