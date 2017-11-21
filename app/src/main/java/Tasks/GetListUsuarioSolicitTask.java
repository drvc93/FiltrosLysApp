package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.SolicitudServicio;
import Model.UsuarioSolicitante;
import Util.Constans;

/**
 * Created by dvillanueva on 20/11/2017.
 */

public class GetListUsuarioSolicitTask extends AsyncTask<String,String,ArrayList<UsuarioSolicitante> > {

    ArrayList<UsuarioSolicitante> result;
    @Override
    protected ArrayList<UsuarioSolicitante> doInBackground(String... strings) {
        ArrayList<UsuarioSolicitante> listaUsuarios = new ArrayList<UsuarioSolicitante>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "ListUsuarioSolcit";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("compania", strings[0]);




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

                UsuarioSolicitante u = new UsuarioSolicitante();

                u.setC_nombreCompleto(ic.getProperty(0).toString());
                u.setC_numerodocumento(ic.getProperty(1).toString());
                u.setN_persona( Integer.valueOf( ic.getProperty(2).toString()));


                listaUsuarios.add(u);


                //  Log.i("Usuario Nro > ", String.valueOf(i));
            }
            if (resSoap.getPropertyCount() > 0) {
                result = listaUsuarios;

            }

        } catch (Exception e) {
            Log.i("AsynckTask Lista Usuario Solicit Error > ", e.getMessage());
            // result = null;
        }

        return result;
    }
}
