package Tasks;

import android.os.AsyncTask;
import android.util.Log;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;
import Model.TMANotificacionQueja;
import Util.Constans;

public class GetNotificacionQJTask extends AsyncTask<String,String,ArrayList<TMANotificacionQueja>> {
    ArrayList<TMANotificacionQueja> result;
    @Override
    protected ArrayList<TMANotificacionQueja> doInBackground(String... strings) {
        ArrayList<TMANotificacionQueja> LstData  = new ArrayList<>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;

        final String METHOD_NAME = "GetListNotificacionQueja";
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
            Log.i("result get Notificacion queja ",resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);
                TMANotificacionQueja oEnt = new TMANotificacionQueja();
                oEnt.setC_notificacion(ic.getPrimitivePropertyAsString("c_notificacion"));
                oEnt.setC_descripcion(ic.getPrimitivePropertyAsString("c_descripcion"));
                oEnt.setC_envianot(ic.getPrimitivePropertyAsString("c_envianot"));
                oEnt.setC_usuarionot(ic.getPrimitivePropertyAsString("c_usuarionot"));
                oEnt.setC_estado(ic.getPrimitivePropertyAsString("c_estado"));
                LstData.add(oEnt);
            }
            if (resSoap.getPropertyCount() > 0){
                result = LstData;
            }
        } catch (Exception e) {
            Log.i("AsynckTask getNotificacion error---", e.getMessage());
        }
        return result;
    }
}
