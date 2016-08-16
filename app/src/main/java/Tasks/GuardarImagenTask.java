package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import Util.Constans;

/**
 * Created by dvillanueva on 15/08/2016.
 */
public class GuardarImagenTask extends AsyncTask<String,String,String> {
    private byte[] imBytes ;
    private String filename;
    public  GuardarImagenTask(byte[]  imBytes, String filename){

        this.imBytes= imBytes;
        this.filename=filename;
    }
    @Override
    protected String doInBackground(String... strings) {
        String result ="";
        //String urlserver = params[2];
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL=  Constans.UrlServer;
        final String METHOD_NAME = "saveImage";
        final String SOAP_ACTION = NAMESPACE+METHOD_NAME;

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo info = new PropertyInfo();
        info.type= MarshalBase64.BYTE_ARRAY_CLASS;
        info.name="imgeByte";
        info.setValue(imBytes);
        Log.i("File name AsynGuardarimg param complete ==>>" , filename);
        request.addProperty(info);

        request.addProperty("fileName", filename);
        Log.i("File name AsynGuardarimg param substring ==>>" , filename);

        SoapSerializationEnvelope envelope =  new SoapSerializationEnvelope(SoapEnvelope.VER11);
        new MarshalBase64().register(envelope);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);

            SoapPrimitive resultado_xml =(SoapPrimitive)envelope.getResponse();
            String res = resultado_xml.toString();

            result=res;


        }
        catch (Exception e)
        {
            result = e.getMessage();
        }
        Log.i("mensaje", result);
        return result;
    }
}
