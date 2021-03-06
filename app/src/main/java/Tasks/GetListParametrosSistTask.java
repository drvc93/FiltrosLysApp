package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;
import Model.Parametros;
import Util.Constans;

/**
 * Creado por dvillanueva el  05/03/2018 (FiltrosLysApp).
 */

public class GetListParametrosSistTask  extends AsyncTask<String,String,ArrayList<Parametros>>{
    ArrayList<Parametros> result;
    @Override
    protected ArrayList<Parametros> doInBackground(String... strings) {
        ArrayList<Parametros> LstData = new ArrayList<>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        final String METHOD_NAME = "GetListaParametrosMov";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        //request.addProperty("compania", strings[0]);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.bodyIn;

            int num_projects = resSoap.getPropertyCount();
            Log.i("result get lista parametros sis ", resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);
                Parametros oEnt = new Parametros();
                oEnt.setC_compania(ic.getPrimitivePropertyAsString("c_compania"));
                oEnt.setC_aplicacion(  ic.getPrimitivePropertyAsString("c_aplicacion"));
                oEnt.setC_descripcion(  ic.getPrimitivePropertyAsString("c_descripcion"));
                oEnt.setC_parametrocodigo(ic.getPrimitivePropertyAsString("c_parametrocodigo"));
                oEnt.setC_texto(ic.getPrimitivePropertyAsString("c_texto"));
                oEnt.setC_ultfechamodificacion(ic.getPrimitivePropertyAsString("c_ultfechamodificacion"));
                oEnt.setC_ultusuario(ic.getPrimitivePropertyAsString("c_ultusuario"));
                oEnt.setD_fecha(ic.getPrimitivePropertyAsString("d_fecha"));
                oEnt.setN_numero( Double.valueOf( ic.getPrimitivePropertyAsString("n_numero")));
                LstData.add(oEnt);
            }
            if (resSoap.getPropertyCount() > 0) {
                result = LstData;
            }

        } catch (Exception e) {
            Log.i("error task lista parametros sis ---", e.getMessage());
        }
        return result;
    }
}
