package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.TMAFalla;
import Model.TMAMarca;
import Util.Constans;

/**
 * Creado por dvillanueva el  23/01/2018 (FiltrosLysApp).
 */

public class GetMarcasTask extends AsyncTask<String,String,ArrayList<TMAMarca>> {
    ArrayList<TMAMarca> result ;
    @Override
    protected ArrayList<TMAMarca> doInBackground(String... strings) {
        ArrayList<TMAMarca> listF  = new ArrayList<>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetListMarcas";
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
            Log.i("result get marcas ",resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);
                TMAMarca  m = new TMAMarca();
                m.setC_descripcion(ic.getProperty(0).toString());
                m.setC_estado(ic.getProperty(1).toString());
                m.setC_marca(ic.getProperty(2).toString());
                listF.add(m);
            }
            if (resSoap.getPropertyCount() > 0)
            {
                result = listF;
            }
        } catch (Exception e) {
            Log.i("AsynckTask GetMarcas error---", e.getMessage());
        }
        return result;
    }
}
