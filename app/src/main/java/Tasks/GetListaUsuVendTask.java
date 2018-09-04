package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.UsuarioPromotor;
import Util.Constans;

/**
 * Creado por dvillanueva el  19/04/2018 (FiltrosLysApp).
 */

public class GetListaUsuVendTask extends AsyncTask<String,String,ArrayList<UsuarioPromotor>> {
    ArrayList<UsuarioPromotor> result ;
    @Override
    protected ArrayList<UsuarioPromotor> doInBackground(String... strings) {
        ArrayList<UsuarioPromotor> LstData = new ArrayList<>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        final String METHOD_NAME = "GetListaUsuariosVend";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("compania", strings[0]);
        request.addProperty("periodo", strings[1]);
        request.addProperty("usuario", strings[2]);
        request.addProperty("undnego", strings[3]);
        Log.i("compania", strings[0]);
        Log.i("periodo", strings[1]);
        Log.i("usuario", strings[2]);
        Log.i("undnego", strings[3]);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.bodyIn;

            int num_projects = resSoap.getPropertyCount();
            Log.i("result get lista usuarios vendedores  ", resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);
                UsuarioPromotor oEnt = new UsuarioPromotor();
                oEnt.setC_usuario(ic.getPrimitivePropertyAsString("c_usuario"));
                oEnt.setC_nombusuario(ic.getPrimitivePropertyAsString("c_nombusuario"));
                LstData.add(oEnt);
            }
            if (resSoap.getPropertyCount() > 0) {
                result = LstData;
            }

        } catch (Exception e) {
            Log.i("error task  lista usuarios vendedores ---", e.getMessage());
        }

        return  LstData;
    }
}
