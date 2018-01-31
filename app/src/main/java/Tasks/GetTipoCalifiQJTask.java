package Tasks;

import android.os.AsyncTask;
import android.util.Log;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;
import Model.TMATipoCalificacionQueja;
import Util.Constans;

public class GetTipoCalifiQJTask extends AsyncTask<String,String,ArrayList<TMATipoCalificacionQueja>> {
    ArrayList<TMATipoCalificacionQueja> result;
    @Override
    protected ArrayList<TMATipoCalificacionQueja> doInBackground(String... strings) {
        ArrayList<TMATipoCalificacionQueja> LstData  = new ArrayList<>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;

        final String METHOD_NAME = "GetListTipoCalificacionQueja";
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
            Log.i("result get Tipo calificacion queja ",resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);
                TMATipoCalificacionQueja oEnt = new TMATipoCalificacionQueja();
                oEnt.setC_tipocalificacion(ic.getPrimitivePropertyAsString("c_tipocalificacion"));
                oEnt.setC_calificacion(ic.getPrimitivePropertyAsString("c_calificacion"));
                oEnt.setC_descripcion(ic.getPrimitivePropertyAsString("c_descripcion"));
                oEnt.setC_estado(ic.getPrimitivePropertyAsString("c_estado"));
                LstData.add(oEnt);
            }
            if (resSoap.getPropertyCount() > 0){
                result = LstData;
            }
        } catch (Exception e) {
            Log.i("AsynckTask getTipoCalificacion error---", e.getMessage());
        }
        return result;
    }
}
