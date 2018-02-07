package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.TMATemaCapacitacion;
import Util.Constans;

public class GetTemaCapacitacionTask extends AsyncTask<String,String,ArrayList<TMATemaCapacitacion>> {
    ArrayList<TMATemaCapacitacion>result;
    @Override
    protected ArrayList<TMATemaCapacitacion> doInBackground(String... strings) {
        ArrayList<TMATemaCapacitacion> listF  = new ArrayList<>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        final String METHOD_NAME = "GetListTemaCapacitacion";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.bodyIn;
            int num_projects = resSoap.getPropertyCount();
            Log.i("result get tema capacitacion ",resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);
                TMATemaCapacitacion  f = new TMATemaCapacitacion();
                f.setC_temacapacitacion(ic.getPrimitivePropertyAsString("c_temacapacitacion"));
                f.setC_descripcion(ic.getPrimitivePropertyAsString("c_descripcion"));
                f.setC_estado(ic.getPrimitivePropertyAsString("c_estado"));
                listF.add(f);
            }
            if (resSoap.getPropertyCount() > 0)
            {
                result = listF;
            }
        } catch (Exception e) {
            Log.i("AsynckTask GetTemaCapacitacion error---", e.getMessage());
        }
        return result;
    }
}
