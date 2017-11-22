package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.EmpAsigSolicitud;
import Util.Constans;

/**
 * Created by dvillanueva on 21/11/2017.
 */

public class GetEmpleadosSolicitudTask extends AsyncTask<String,String,ArrayList<EmpAsigSolicitud>> {

    ArrayList<EmpAsigSolicitud> result;
    @Override
    protected ArrayList<EmpAsigSolicitud> doInBackground(String... strings) {
        ArrayList<EmpAsigSolicitud> listaEmpleados = new ArrayList<EmpAsigSolicitud>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetEmpleadosAsig";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("compania", strings[0]);
        request.addProperty("nrosolicitud", strings[1]);




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
            //  Log.i("result  get  historial gen ", resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);

                EmpAsigSolicitud e = new EmpAsigSolicitud();

                e.setC__nombreempleado(ic.getProperty(0).toString());
                e.setC_compEmpleado(ic.getProperty(1).toString());
                e.setC_compania(ic.getProperty(2).toString());
                e.setC_ultimousuario(ic.getProperty(3).toString());
                e.setN_empleado( Integer.valueOf( ic.getProperty(4).toString()));
                e.setN_secuencia( Integer.valueOf( ic.getProperty(5).toString()));
                e.setN_secuencia( Integer.valueOf( ic.getProperty(6).toString()));
                listaEmpleados.add(e);

            }
            if (resSoap.getPropertyCount() > 0) {
                result = listaEmpleados;

            }

        } catch (Exception e) {
            Log.i("AsynckTask Empleados Asig.  Error > ", e.getMessage());
            // result = null;
        }

        return result;
    }
}
