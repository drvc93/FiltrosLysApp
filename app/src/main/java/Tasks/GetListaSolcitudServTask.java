package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;
import Model.SolicitudServicio;
import Util.Constans;

/**
 * Created by dvillanueva on 17/11/2017.
 */

public class GetListaSolcitudServTask extends AsyncTask <String,String, ArrayList <SolicitudServicio> > {
    ArrayList<SolicitudServicio> result;
    @Override
    protected ArrayList<SolicitudServicio> doInBackground(String... strings) {
        ArrayList<SolicitudServicio> listSolicitudes = new ArrayList<SolicitudServicio>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "ListSolcitudServ";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("compania", strings[0]);
        request.addProperty("flagfecha", strings[1]);
        request.addProperty("fechaini", strings[2]);
        request.addProperty("fechafin", strings[3]);
        request.addProperty("maquina", strings[4]);
        request.addProperty("prioridad", strings[5]);
        request.addProperty("estado", strings[6]);
        request.addProperty("personasolicit", strings[7]);



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
            Log.i("result  get  historial gen ", resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);

                SolicitudServicio s = new SolicitudServicio();

                s.setC_ccosto(ic.getProperty(0).toString());
                s.setC_ccostonomb(ic.getProperty(1).toString());
                s.setC_compania(ic.getProperty(2).toString());
                s.setC_descfalla(ic.getProperty(3).toString());
                s.setC_descriproblema(ic.getProperty(4).toString());
                s.setC_estado(ic.getProperty(5).toString());
                s.setC_fechareg(ic.getProperty(6).toString());
                s.setC_maquina(ic.getProperty(7).toString());
                s.setC_maquinanomb(ic.getProperty(8).toString());
                s.setC_personasolicitud(ic.getProperty(9).toString());
                s.setC_prioridad(ic.getProperty(10).toString());
                s.setC_tiposolcitud(ic.getProperty(11).toString());
                s.setC_usuariosolicit(ic.getProperty(12).toString());
                s.setN_solicitud(Integer.valueOf(ic.getProperty(13).toString()));

                listSolicitudes.add(s);


                //  Log.i("Usuario Nro > ", String.valueOf(i));
            }
            if (resSoap.getPropertyCount() > 0) {
                result = listSolicitudes;

            }

        } catch (Exception e) {
            Log.i("AsynckTask Lista Solic.Serv error---", e.getMessage());
            // result = null;
        }

        return result;
    }
}
