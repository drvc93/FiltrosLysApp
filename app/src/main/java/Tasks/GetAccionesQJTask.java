package Tasks;

import android.os.AsyncTask;
import android.util.Log;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;
import Model.TMAAccionesTomar;
import Util.Constans;

public class GetAccionesQJTask extends AsyncTask<String,String,ArrayList<TMAAccionesTomar>> {
    ArrayList<TMAAccionesTomar> result;
    @Override
    protected ArrayList<TMAAccionesTomar> doInBackground(String... strings) {
        ArrayList<TMAAccionesTomar> LstData  = new ArrayList<>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;

        final String METHOD_NAME = "GetListAccionesTomar";
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
            Log.i("result get acciones queja ",resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);
                TMAAccionesTomar oEnt = new TMAAccionesTomar();
                oEnt.setC_codaccion(ic.getPrimitivePropertyAsString("c_codaccion"));
                oEnt.setC_descripcion(ic.getPrimitivePropertyAsString("c_descripcion"));
                oEnt.setC_estado(ic.getPrimitivePropertyAsString("c_estado"));
                LstData.add(oEnt);
            }
            if (resSoap.getPropertyCount() > 0){
                result = LstData;
            }
        } catch (Exception e) {
            Log.i("AsynckTask getAcciones error---", e.getMessage());
        }
        return result;
    }
}
