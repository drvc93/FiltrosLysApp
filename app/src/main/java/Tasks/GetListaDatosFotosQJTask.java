package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.DocsQuejaCliente;
import Util.Constans;

/**
 * Creado por dvillanueva el  07/02/2018 (FiltrosLysApp).
 */

public class GetListaDatosFotosQJTask  extends AsyncTask<String,String,ArrayList<DocsQuejaCliente>> {
    ArrayList<DocsQuejaCliente> result;
    @Override
    protected ArrayList<DocsQuejaCliente> doInBackground(String... strings) {
        ArrayList<DocsQuejaCliente> LstData = new ArrayList<DocsQuejaCliente>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        final String METHOD_NAME = "GetListFotosQJ";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("compania", strings[0]);
        request.addProperty("correlativo", strings[1]);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.bodyIn;

            int num_projects = resSoap.getPropertyCount();
            Log.i("result get lista datos fotos QJ ", resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);

                DocsQuejaCliente oEnt = new DocsQuejaCliente();
                oEnt.setC_compania(ic.getPrimitivePropertyAsString("c_compania"));
                oEnt.setN_queja( Integer.valueOf( ic.getPrimitivePropertyAsString("n_queja")));
                oEnt.setN_linea( Integer.valueOf( ic.getPrimitivePropertyAsString("n_linea")));
                oEnt.setC_descripcion(ic.getPrimitivePropertyAsString("c_descripcion"));
                oEnt.setC_nombre_archivo(ic.getPrimitivePropertyAsString("c_nombre_archivo"));
                oEnt.setC_ruta_archivo(ic.getPrimitivePropertyAsString("c_ruta_archivo"));
                oEnt.setC_ultimousuario(ic.getPrimitivePropertyAsString("c_ultimousuario"));
                oEnt.setD_ultimafechamodificacion(ic.getPrimitivePropertyAsString("d_ultimafechamodificacion"));

                LstData.add(oEnt);
            }
            if (resSoap.getPropertyCount() > 0) {
                result = LstData;
            }

        } catch (Exception e) {
            Log.i("AsynckTask get lista datos fotos QJ ---", e.getMessage());
        }
        return result;
    }
}
