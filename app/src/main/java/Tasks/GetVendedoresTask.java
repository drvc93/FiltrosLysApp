package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.TMATipoReclamo;
import Model.TMAVendedor;
import Util.Constans;

/**
 * Creado por dvillanueva el  23/01/2018 (FiltrosLysApp).
 */

public class GetVendedoresTask extends AsyncTask<String,String,ArrayList<TMAVendedor>> {
    ArrayList<TMAVendedor> result;
    @Override
    protected ArrayList<TMAVendedor> doInBackground(String... strings) {
        ArrayList<TMAVendedor> listF  = new ArrayList<>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetListaVendedores";
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
            Log.i("result get vendedores ",resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);
                TMAVendedor  v = new TMAVendedor();
                v.setC_ciasecundaria(ic.getPrimitivePropertyAsString("c_ciasecundaria"));
                v.setC_compania(ic.getProperty(1).toString());
                v.setC_estado(ic.getProperty(2).toString());
                v.setC_nombres(ic.getProperty(3).toString());
                v.setC_vendedor(ic.getProperty(4).toString());
                listF.add(v);
            }
            if (resSoap.getPropertyCount() > 0)
            {
                result = listF;
            }
        } catch (Exception e) {
            Log.i("AsynckTask get  vendedores error---", e.getMessage());
        }
        return result;
    }
}
