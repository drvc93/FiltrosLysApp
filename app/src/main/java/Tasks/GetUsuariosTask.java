package Tasks;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import DataBase.MenuDB;
import DataBase.UsuarioDB;
import Util.Constans;

/**
 * Created by dvillanueva on 05/08/2016.
 */
public class GetUsuariosTask  extends AsyncTask<String,String,ArrayList<UsuarioDB>> {

    ArrayList<UsuarioDB> result ;
    ProgressDialog progressDialog;

    public  GetUsuariosTask  (ProgressDialog progressDialog){

        this.progressDialog = progressDialog;
    }

    @Override
    protected ArrayList<UsuarioDB> doInBackground(String... strings) {
        ArrayList<UsuarioDB> listUsers  = new ArrayList<UsuarioDB>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetUsuarios";
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
            Log.i("result  get usuarios ",resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);

                UsuarioDB us = new UsuarioDB();

                us.setClave(ic.getProperty(0).toString());
                us.setCodigoUsuario(ic.getProperty(1).toString());
                us.setEstado(ic.getProperty(2).toString());
                us.setFlagmantto(ic.getProperty(3).toString());
                us.setNombre(ic.getProperty(4).toString());
                us.setNroPersona(ic.getProperty(5).toString());

                listUsers.add(us);
              //  Log.i("Usuario Nro > ", String.valueOf(i));
            }
            if (resSoap.getPropertyCount() > 0) {
                result = listUsers;

            }

        } catch (Exception e) {
            Log.i("AsynckTask GetUsers error---", e.getMessage());
            result = null;
        }

        return result;


    }


}
