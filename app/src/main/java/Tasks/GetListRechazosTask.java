package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.Rechazos;
import Util.Constans;

/**
 * Created by dvillanueva on 24/11/2017.
 */

public class GetListRechazosTask extends AsyncTask<String,String,ArrayList<Rechazos>> {

    ArrayList<Rechazos> result ;
    @Override
    protected ArrayList<Rechazos> doInBackground(String... strings) {
        ArrayList<Rechazos> listRechazos  = new ArrayList<Rechazos>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "ListaRechazos";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        //request.addProperty("CodUsuario", strings[0]);

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
            Log.i("result  get maquinas ",resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);

                Rechazos re = new Rechazos();

                re.setC_descripcion(ic.getProperty(0).toString());
                re.setC_razonrechazo(ic.getProperty(1).toString());
                listRechazos.add(re);
                //  Log.i("Usuario Nro > ", String.valueOf(i));
            }
            if (resSoap.getPropertyCount() > 0) {
                result = listRechazos;

            }

        } catch (Exception e) {
            Log.i("AsynckTask lista rechazos error---", e.getMessage());
            // result = null;
        }

        return result;
    }
}
