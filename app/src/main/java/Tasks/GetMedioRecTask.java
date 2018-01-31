package Tasks;

import android.os.AsyncTask;
import android.util.Log;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;
import Model.TMAMedioRecepcion;
import Util.Constans;

public class GetMedioRecTask extends AsyncTask<String,String,ArrayList<TMAMedioRecepcion>> {
    ArrayList<TMAMedioRecepcion> result;
    @Override
    protected ArrayList<TMAMedioRecepcion> doInBackground(String... strings) {
        ArrayList<TMAMedioRecepcion> LstData  = new ArrayList<>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;

        final String METHOD_NAME = "GetListMedioRecepcion";
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
            Log.i("result get medio recepcion queja ",resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);
                TMAMedioRecepcion oEnt = new TMAMedioRecepcion();
                oEnt.setC_mediorecepcion(ic.getPrimitivePropertyAsString("c_mediorecepcion"));
                oEnt.setC_descripcion(ic.getPrimitivePropertyAsString("c_descripcion"));
                oEnt.setC_estado(ic.getPrimitivePropertyAsString("c_estado"));
                LstData.add(oEnt);
            }
            if (resSoap.getPropertyCount() > 0){
                result = LstData;
            }
        } catch (Exception e) {
            Log.i("AsynckTask getMedioRecepcion error---", e.getMessage());
        }
        return result;
    }
}
