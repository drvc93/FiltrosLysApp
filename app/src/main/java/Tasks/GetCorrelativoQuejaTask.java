package Tasks;

import android.os.AsyncTask;
import android.util.Log;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import Util.Constans;

public class GetCorrelativoQuejaTask extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... strings) {
        String result = "ERROR";

        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        final String METHOD_NAME = "GetCorrelativorQueja";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try{
            transporte.call(SOAP_ACTION, envelope);
            SoapPrimitive resultado_xml =(SoapPrimitive)envelope.getResponse();
            String res = resultado_xml.toString();
            Log.i("next correlativo Queja > ", res);
            result=res;
        }catch (Exception e){
            result = e.getMessage();
        }
        return  result;
    }
}
