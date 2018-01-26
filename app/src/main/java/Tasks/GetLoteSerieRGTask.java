package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;
import Model.EFacSerLot;
import Util.Constans;

/**
 * Creado por dvillanueva el  25/01/2018 (FiltrosLysApp).
 */

public class GetLoteSerieRGTask extends AsyncTask<String,String,ArrayList<EFacSerLot>> {
    ArrayList<EFacSerLot> result;
    @Override
    protected ArrayList<EFacSerLot> doInBackground(String... strings) {
        ArrayList<EFacSerLot> listF  = new ArrayList<>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "BuscarFiltroGarantia";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("tipo", strings[0]);
        request.addProperty("compania", strings[1]);
        request.addProperty("cliente", strings[2]);
        request.addProperty("factura", strings[3]);
        request.addProperty("item", strings[4]);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.bodyIn;
            int num_projects = resSoap.getPropertyCount();
            Log.i("result get lote serie ",resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);
                EFacSerLot  f = new EFacSerLot();
                f.setC_factRef(ic.getPrimitivePropertyAsString("c_factRef"));
                f.setC_lote(ic.getPrimitivePropertyAsString("c_lote"));
                f.setC_procedencia(ic.getPrimitivePropertyAsString("c_procedencia"));
                listF.add(f);
            }
            if (resSoap.getPropertyCount() > 0)
            {
                result = listF;
            }
        } catch (Exception e) {
            Log.i("AsynckTask  get lote serie error---", e.getMessage());
        }
        return result;
    }
}
