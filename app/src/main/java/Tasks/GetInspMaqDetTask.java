package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.InspeccionMaqCabecera;
import Model.InspeccionMaqDetalle;
import Util.Constans;

/**
 * Created by dvillanueva on 25/08/2016.
 */
public class GetInspMaqDetTask extends AsyncTask<String, String, ArrayList<InspeccionMaqDetalle>> {
    ArrayList<InspeccionMaqDetalle> result;

    @Override
    protected ArrayList<InspeccionMaqDetalle> doInBackground(String... strings) {
        ArrayList<InspeccionMaqDetalle> listDet = new ArrayList<InspeccionMaqDetalle>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetInspeccionMaqDetCor";
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

                InspeccionMaqDetalle det = new InspeccionMaqDetalle();
                det.setComentario(ic.getProperty(0).toString());
                det.setCompania(ic.getProperty(1).toString());
                det.setEstado(ic.getProperty(2).toString());
                det.setCod_inspeccion(ic.getProperty(3).toString());
                det.setRutaFoto(ic.getProperty(4).toString());
                det.setTipo_inspecicon(ic.getProperty(5).toString());
                det.setUltimoUser(ic.getProperty(6).toString());
                det.setUltimaFechaMod(ic.getProperty(7).toString());
                det.setCorrelativo(ic.getProperty(8).toString());
                det.setLinea(ic.getProperty(9).toString());
                det.setPorcentInspec(ic.getProperty(10).toString());
                det.setPorcentMax(ic.getProperty(11).toString());
                det.setPorcentMin(ic.getProperty(12).toString());
                listDet.add(det);


            }
            if (resSoap.getPropertyCount() > 0) {
                result = listDet;

            }

        } catch (Exception e) {
            Log.i("AsynckTask inspecciones maq det error---", e.getMessage());
            // result = null;
        }

        return result;
    }
}
