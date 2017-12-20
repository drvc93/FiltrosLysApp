package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.InspeccionGenCabecera;
import Model.InspeccionGenDetalle;
import Util.Constans;

/**
 * Created by dvillanueva on 24/08/2016.
 */
public class GetInspGenDetTask extends AsyncTask<String, String, ArrayList<InspeccionGenDetalle>> {
    ArrayList<InspeccionGenDetalle> result;

    @Override
    protected ArrayList<InspeccionGenDetalle> doInBackground(String... strings) {
        ArrayList<InspeccionGenDetalle> listInspG = new ArrayList<InspeccionGenDetalle>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetInspeccionGenCDetCor";
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
            Log.i("result  get inspecciones  cab ", resSoap.toString());
            for (int i = 0; i < num_count; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);


                InspeccionGenDetalle d = new InspeccionGenDetalle();
                d.setComentario(ic.getPrimitivePropertyAsString("c_comentario"));
                d.setCompania(ic.getProperty(1).toString());
                d.setFlagadictipo(ic.getProperty(2).toString());
                d.setRutaFoto(ic.getProperty(3).toString());
                d.setTipoRevision(ic.getProperty(4).toString());
                d.setUltUsuario(ic.getProperty(5).toString());
                d.setUltFechaMod(ic.getProperty(6).toString());
                d.setCorrelativo(ic.getProperty(7).toString());
                d.setLinea(ic.getProperty(8).toString());
                listInspG.add(d);


            }
            if (resSoap.getPropertyCount() > 0) {
                result = listInspG;

            }

        } catch (Exception e) {
            Log.i("AsynckTask inspecciones gen cab error---", e.getMessage());
            // result = null;
        }

        return result;
    }
}
