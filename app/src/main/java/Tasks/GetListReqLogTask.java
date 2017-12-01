package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.RequisicionLogCab;
import Util.Constans;

/**
 * Created by dvillanueva on 22/11/2017.
 */

public class GetListReqLogTask extends AsyncTask<String,String,ArrayList<RequisicionLogCab>> {
    ArrayList<RequisicionLogCab> result;
    @Override
    protected ArrayList<RequisicionLogCab> doInBackground(String... strings) {
        ArrayList<RequisicionLogCab> listaReq = new ArrayList<RequisicionLogCab>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "ListReqLogistica";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("compania", strings[0]);
        request.addProperty("ccosto", strings[1]);
        request.addProperty("estado", strings[2]);
        request.addProperty("fechainicio", strings[3]);
        request.addProperty("fechafin", strings[4]);
        request.addProperty("nroreq", strings[5]);




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
            Log.i("result  get  lista req log ", resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);

                RequisicionLogCab r = new RequisicionLogCab();

                r.setC_almacennomb(ic.getProperty(0).toString());
                r.setC_ccosto(ic.getProperty(1).toString());
                r.setC_ccostonomb(ic.getProperty(2).toString());
                r.setC_clasificacion(ic.getProperty(3).toString());
                r.setC_cometario(ic.getProperty(4).toString());
                r.setC_compania(ic.getProperty(5).toString());
                r.setC_estado(ic.getProperty(6).toString());
                r.setC_estadonomb(ic.getProperty(7).toString());
                r.setC_fechaaprobacion(ic.getProperty(8).toString());
                r.setC_fechacreacion(ic.getProperty(9).toString());
                r.setC_numeroreq(ic.getProperty(10).toString());
                r.setC_prioridad(ic.getProperty(11).toString());
                r.setC_prioridadnomb(ic.getProperty(12).toString());
                r.setC_reposicionstock(ic.getProperty(13).toString());
                r.setC_usuarioaprobacion(ic.getProperty(14).toString());
                r.setC_usuariocreacion(ic.getProperty(15).toString());
                listaReq.add(r);


                //  Log.i("Usuario Nro > ", String.valueOf(i));
            }
            if (resSoap.getPropertyCount() > 0) {
                result = listaReq;

            }

        } catch (Exception e) {
            Log.i("AsynckTask Lista Req Logistica error---", e.getMessage());
            // result = null;
        }

        return result;
    }
}
