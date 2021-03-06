package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.UsuarioCompania;
import Util.Constans;

/**
 * Creado por dvillanueva el  30/01/2018 (FiltrosLysApp).
 */

public class GetCompUserComercialTask  extends AsyncTask<String,String,ArrayList<UsuarioCompania>> {
    ArrayList<UsuarioCompania> result;
    public  String msj  = "OK";
    @Override
    protected ArrayList<UsuarioCompania> doInBackground(String... strings) {
        ArrayList<UsuarioCompania> listaCompanias = new ArrayList<UsuarioCompania>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetUsuarioCompComercial";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("usuario", strings[0]);
        Log.i("usuario log" , strings[0]);



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
            Log.i("result  compania x usuario comercial ", resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);

                UsuarioCompania u = new UsuarioCompania();

                u.setC_compania(ic.getProperty(0).toString());
                u.setC_nombres(ic.getProperty(1).toString());
                listaCompanias.add(u);

            }
            if (resSoap.getPropertyCount() > 0) {
                result = listaCompanias;

            }

        } catch (Exception e) {
            Log.i("AsynckTask CompaniaXUsuario Comercial  Error > ", e.getMessage());
            if (e.getMessage().indexOf("to connect")>0){
                msj = "Error de conexion al servidor, revise la configuración de conexión.";
            }
            // result = null;
        }

        return result;
    }
}
