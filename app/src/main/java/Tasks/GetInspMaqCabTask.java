package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.InspeccionGenCabecera;
import Model.InspeccionMaqCabecera;
import Util.Constans;

/**
 * Created by dvillanueva on 25/08/2016.
 */
public class GetInspMaqCabTask extends AsyncTask<String, String, ArrayList<InspeccionMaqCabecera>> {
    ArrayList<InspeccionMaqCabecera> result;

    @Override
    protected ArrayList<InspeccionMaqCabecera> doInBackground(String... strings) {
        ArrayList<InspeccionMaqCabecera> listCab = new ArrayList<InspeccionMaqCabecera>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetInspeccionMaqCabCor";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("correlativo", strings[0]);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.bodyIn;
            int num_count = resSoap.getPropertyCount();
            Log.i("result  get inspecciones  maq cab ", resSoap.toString());
            for (int i = 0; i < num_count; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);

                InspeccionMaqCabecera cab = new InspeccionMaqCabecera();
                cab.setComentario(ic.getProperty(0).toString());
                cab.setCompania(ic.getProperty(1).toString());
                cab.setCondicionMaq(ic.getProperty(2).toString());
                cab.setEstado(ic.getProperty(3).toString());
                cab.setCodMaquina(ic.getProperty(4).toString());
                cab.setPeriodoInsp(ic.getProperty(5).toString());
                cab.setUsuarioInsp(ic.getProperty(6).toString());
                cab.setFechFinInsp(ic.getProperty(7).toString());
                cab.setFechaInicioInsp(ic.getProperty(8).toString());
                cab.setCorrlativo(ic.getProperty(9).toString());

                listCab.add(cab);


            }
            if (resSoap.getPropertyCount() > 0) {
                result = listCab;

            }

        } catch (Exception e) {
            Log.i("AsynckTask inspecciones maq cab error---", e.getMessage());
            // result = null;
        }

        return result;
    }
}
