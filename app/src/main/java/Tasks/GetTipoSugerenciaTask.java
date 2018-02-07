package Tasks;

import android.os.AsyncTask;
import android.util.Log;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;
import Model.TMATipoSugerencia;
import Util.Constans;

public class GetTipoSugerenciaTask extends AsyncTask<String,String,ArrayList<TMATipoSugerencia>> {
    ArrayList<TMATipoSugerencia>result;
    @Override
    protected ArrayList<TMATipoSugerencia> doInBackground(String... strings) {
        ArrayList<TMATipoSugerencia> listF  = new ArrayList<>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        final String METHOD_NAME = "GetListTipoSugerencia";
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
            Log.i("result get tipo sugerencia ",resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);
                TMATipoSugerencia  f = new TMATipoSugerencia();
                f.setC_tiposugerencia(ic.getPrimitivePropertyAsString("c_tiposugerencia"));
                f.setC_descripcion(ic.getPrimitivePropertyAsString("c_descripcion"));
                f.setC_estado(ic.getPrimitivePropertyAsString("c_estado"));
                listF.add(f);
            }
            if (resSoap.getPropertyCount() > 0)
            {
                result = listF;
            }
        } catch (Exception e) {
            Log.i("AsynckTask GetTipoSugerencia error---", e.getMessage());
        }
        return result;
    }
}
