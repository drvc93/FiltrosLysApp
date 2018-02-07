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
 * Creado por dvillanueva el  05/02/2018 (FiltrosLysApp).
 */

public class GuardarFotoRGTask extends AsyncTask<String,String,String> {
    private byte[] imBytes ;
    private String filename;
    private  String sTipo ;
    public  GuardarFotoRGTask(byte[]  imBytes, String filename,String sTipo){

        this.imBytes= imBytes;
        this.filename=filename;
        this.sTipo = sTipo ;
    }
    @Override
    protected String doInBackground(String... strings) {
        String result ="";
        //String urlserver = params[2];
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL=  Constans.UrlServer;
        final String METHOD_NAME = "GuardarFotoReclamoG";
        final String SOAP_ACTION = NAMESPACE+METHOD_NAME;

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo info = new PropertyInfo();
        info.type= MarshalBase64.BYTE_ARRAY_CLASS;
        info.name="imgeByte";
        info.setValue(imBytes);
        request.addProperty(info);
        request.addProperty("fileName", filename);
        request.addProperty("tipo", sTipo);
        Log.i("Arraay byte task foto rg" , imBytes.toString());
        Log.i("File name FotoRG param substring ==>>" , filename);

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
        Log.i("mensaje  guardar FotoRG", result);
        return result;
    }

}
