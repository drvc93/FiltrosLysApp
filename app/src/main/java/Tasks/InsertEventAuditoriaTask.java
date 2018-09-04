package Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import Util.Constans;

/**
 * Creado por dvillanueva el  06/03/2018 (FiltrosLysApp).
 */

public class InsertEventAuditoriaTask extends AsyncTask<String,String,String> {
    public Context context ;
    private ProgressDialog p;;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        p= new ProgressDialog(context);
        p.setMessage("Espere...");
        p.setIndeterminate(false);
        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setCancelable(false);
        p.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        p.dismiss();
    }

    @Override

    protected String doInBackground(String... strings) {
        String result = "0";

        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        final String METHOD_NAME = "InsertEventoAuditoria";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("origen", strings[0]);
        request.addProperty("imei", strings[1]);
        request.addProperty("numero", strings[2]);
        request.addProperty("seriechip", strings[3]);
        request.addProperty("hora", strings[4]);
        request.addProperty("accion", strings[5]);
        request.addProperty("usuario", strings[6]);
        request.addProperty("codintapp", strings[7]);


        Log.i("origen", strings[0]);
        Log.i("imei", strings[1]);
        Log.i("numero", strings[2]);
        Log.i("seriechip", strings[3]);
        Log.i("ultusuario", strings[4]);
        Log.i("hora", strings[4]);
        Log.i("accion", strings[5]);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
            String res = resultado_xml.toString();
            Log.i("Result Insert Evento auditoria > ", res);
            result = res;

        } catch (Exception e) {
            result = e.getMessage();
            Log.i("Error  Insert Evento auditoria > ", e.getMessage());
        }

        return result;
    }
}
