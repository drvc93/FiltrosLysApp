package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.TMAMarca;
import Model.TMAModelo;
import Util.Constans;

/**
 * Creado por dvillanueva el  23/01/2018 (FiltrosLysApp).
 */

public class GetModelosTask extends AsyncTask<String,String,ArrayList<TMAModelo>> {
    ArrayList<TMAModelo> result  ;
    @Override
    protected ArrayList<TMAModelo> doInBackground(String... strings) {
        ArrayList<TMAModelo> listF  = new ArrayList<>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetListaModelos";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        //request.addProperty("CodUsuario", strings[0]);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.bodyIn;
            int num_projects = resSoap.getPropertyCount();
            Log.i("result get modelos ",resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);
                TMAModelo  m = new TMAModelo();
                m.setC_descripcion(ic.getProperty(0).toString());
                m.setC_estado(ic.getProperty(1).toString());
                m.setC_marca(ic.getPrimitivePropertyAsString("c_marca"));
                m.setC_modelo(ic.getProperty(3).toString());
                listF.add(m);
            }
            if (resSoap.getPropertyCount() > 0)
            {
                result = listF;
            }
        } catch (Exception e) {
            Log.i("AsynckTask getModelos error---", e.getMessage());
        }
        return result;
    }
}
