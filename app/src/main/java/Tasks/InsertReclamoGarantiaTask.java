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
 * Creado por dvillanueva el  29/01/2018 (FiltrosLysApp).
 */

public class InsertReclamoGarantiaTask  extends AsyncTask<String,String,String> {

    @Override
    protected String doInBackground(String... strings) {
        String result = "0";

        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8086/SOAPLYS?wsdl";
        final String METHOD_NAME = "InsertReclamoGarantia";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("compania", strings[0]);
        request.addProperty("correlativo", strings[1]);
        request.addProperty("accion", strings[2]);
        request.addProperty("usuario", strings[3]);
        request.addProperty("tiporeclamo", strings[4]);
        request.addProperty("fecha", strings[5]);
        request.addProperty("formato", strings[6]);
        request.addProperty("fechaformato", strings[7]);
        request.addProperty("cliente", strings[8]);
        request.addProperty("filtro", strings[9]);
        request.addProperty("lotefiltro", strings[10]);
        request.addProperty("procedencia", strings[11]);
        request.addProperty("facturaref", strings[12]);
        request.addProperty("lote1", strings[13]);
        request.addProperty("lote2", strings[14]);
        request.addProperty("lote3", strings[15]);
        request.addProperty("cantlote1", strings[16]);
        request.addProperty("cantlote2", strings[17]);
        request.addProperty("cantlote3", strings[18]);
        request.addProperty("tiempouso", strings[19]);
        request.addProperty("marca", strings[20]);
        request.addProperty("modelo", strings[21]);
        request.addProperty("year", strings[22]);
        request.addProperty("placavech", strings[23]);
        request.addProperty("obervclie", strings[24]);
        request.addProperty("pruebalab1", strings[25]);
        request.addProperty("pruebalab2", strings[26]);
        request.addProperty("pruebalab3", strings[27]);
        request.addProperty("ensayo1", strings[28]);
        request.addProperty("ensayo2", strings[29]);
        request.addProperty("ensayo3", strings[30]);
        request.addProperty("ensayo4", strings[31]);
        request.addProperty("ensayo5", strings[32]);
        request.addProperty("prediagvend", strings[33]);
        request.addProperty("prediagobse", strings[34]);
        request.addProperty("reembolsocli", strings[35]);
        request.addProperty("reembolsomto", strings[36]);
        request.addProperty("reembolsomon", strings[37]);
        request.addProperty("necesitavisita", strings[38]);

        Log.i("compania", strings[0]);
        Log.i("correlativo", strings[1]);
        Log.i("accion", strings[2]);
        Log.i("usuario", strings[3]);
        Log.i("tiporeclamo", strings[4]);
        Log.i("fecha", strings[5]);
        Log.i("formato", strings[6]);
        Log.i("fechaformato", strings[7]);
        Log.i("cliente", strings[8]);
        Log.i("filtro", strings[9]);
        Log.i("lotefiltro", strings[10]);
        Log.i("procedencia", strings[11]);
        Log.i("facturaref", strings[12]);
        Log.i("lote1", strings[13]);
        Log.i("lote2", strings[14]);
        Log.i("lote3", strings[15]);
        Log.i("cantlote1", strings[16]);
        Log.i("cantlote2", strings[17]);
        Log.i("cantlote3", strings[18]);
        Log.i("tiempouso", strings[19]);
        Log.i("marca", strings[20]);
        Log.i("modelo", strings[21]);
        Log.i("year", strings[22]);
        Log.i("placavech", strings[23]);
        Log.i("obervclie", strings[24]);
        Log.i("pruebalab1", strings[25]);
        Log.i("pruebalab2", strings[26]);
        Log.i("pruebalab3", strings[27]);
        Log.i("ensayo1", strings[28]);
        Log.i("ensayo2", strings[29]);
        Log.i("ensayo3", strings[30]);
        Log.i("ensayo4", strings[31]);
        Log.i("ensayo5", strings[32]);
        Log.i("prediagvend", strings[33]);
        Log.i("prediagobse", strings[34]);
        Log.i("reembolsocli", strings[35]);
        Log.i("reembolsomto", strings[36]);
        Log.i("reembolsomon", strings[37]);
        Log.i("necesitavisita", strings[38]);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try {
            transporte.call(SOAP_ACTION, envelope);

            SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
            String res = resultado_xml.toString();
            Log.i("Result Insert reclamo garantia> ", res);
            result = res;


        } catch (Exception e) {
            result = e.getMessage();
            Log.i("Error Insert  reclamo garantia > ", e.getMessage());
        }

        return result;
    }
}
