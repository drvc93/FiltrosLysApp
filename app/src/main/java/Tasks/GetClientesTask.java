package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;
import Model.TMACliente;
import Util.Constans;

public class GetClientesTask extends AsyncTask<String,String,ArrayList<TMACliente>> {
    ArrayList<TMACliente> result;
    @Override
    protected ArrayList<TMACliente> doInBackground(String... strings) {
        ArrayList<TMACliente> listC  = new ArrayList<>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetClientes";
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
            Log.i("result  get GetClientes ",resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);
                TMACliente c = new TMACliente();
                c.setC_compania(ic.getProperty(0).toString());
                c.setC_razonsocial(ic.getProperty(1).toString());
                c.setN_cliente( Integer.valueOf( ic.getProperty(2).toString()));
                listC.add(c);
                //  Log.i("Usuario Nro > ", String.valueOf(i));
            }
            if (resSoap.getPropertyCount() > 0)
            {
                result = listC;
            }
        } catch (Exception e) {
            Log.i("AsynckTask GetClientes error---", e.getMessage());
        }
        return result;
    }
}
