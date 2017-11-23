package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.RequisicionLogDet;
import Util.Constans;

/**
 * Created by dvillanueva on 23/11/2017.
 */

public class GetReqLogDetalleTask extends AsyncTask<String,String,ArrayList<RequisicionLogDet>> {
    ArrayList<RequisicionLogDet> result;
    @Override
    protected ArrayList<RequisicionLogDet> doInBackground(String... strings) {
        ArrayList<RequisicionLogDet> listaDet = new ArrayList<RequisicionLogDet>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetDetalleReqLog";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("compania", strings[0]);
        request.addProperty("numeroreq", strings[1]);





        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.bodyIn;


            //lstProjects = new ArrayList<Parametros>();
            int num_projects = resSoap.getPropertyCount();
            Log.i("result  get  lista req log  detalle ", resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);

                RequisicionLogDet det = new RequisicionLogDet();

                det.setC_cantidadpedida(ic.getProperty(0).toString());
                det.setC_compania(ic.getProperty(1).toString());
                det.setC_descripcion(ic.getProperty(2).toString());
                det.setC_item(ic.getProperty(3).toString());
                det.setC_stockdisponible(ic.getProperty(4).toString());
                det.setC_stockminimo(ic.getProperty(5).toString());
                listaDet.add(det);
                //  Log.i("Usuario Nro > ", String.valueOf(i));
            }
            if (resSoap.getPropertyCount() > 0) {
                result = listaDet;

            }

        } catch (Exception e) {
            Log.i("AsynckTask  Req Logistica Detalle error---", e.getMessage());
            // result = null;
        }

        return result;
    }
}
