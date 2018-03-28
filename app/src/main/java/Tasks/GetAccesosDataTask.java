package Tasks;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.filtroslys.filtroslysapp.MenuPrincipal;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import DataBase.AccesosDB;
import Util.Constans;

/**
 * Created by dvillanueva on 04/08/2016.
 */
public class GetAccesosDataTask extends AsyncTask<String,String,ArrayList<AccesosDB>> {

    ProgressDialog progressDialog ;
    Context context;


    ArrayList<AccesosDB> result;

    public GetAccesosDataTask() {
        super();
    }

    @Override
    protected ArrayList<AccesosDB> doInBackground(String... strings) {



        ArrayList<AccesosDB> accesosDBs = new ArrayList<AccesosDB>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        final String METHOD_NAME = "GetDataAccesos";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("usuario", strings[0]);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.bodyIn;


            Log.i("Accesos DB DATA > ", resSoap.toString());
            //lstProjects = new ArrayList<Parametros>();
            int num_projects = resSoap.getPropertyCount();

            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);

                AccesosDB acceso = new AccesosDB();
                acceso.setAcceso(ic.getProperty(0).toString());
                acceso.setAppCodigo(ic.getProperty(1).toString());
                acceso.setEliminar(ic.getProperty(2).toString());
                acceso.setModificar(ic.getProperty(3).toString());
                acceso.setNivelAcc(ic.getProperty(4).toString());
                acceso.setNuevo(ic.getProperty(5).toString());
                acceso.setOtros(ic.getProperty(6).toString());
                acceso.setUsuario(ic.getProperty(7).toString());
                accesosDBs.add(acceso);
            }
            if (resSoap.getPropertyCount() > 0) {
                result = accesosDBs;

            }

        } catch (Exception e) {
            Log.i("ERROR JAVA LIST WS MENUDB ---", e.getMessage());
            result = null;
        }


        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }



    public void timerDelayRemoveDialog(long time, final Dialog d){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
                Intent i = new Intent(context , MenuPrincipal.class);
                context.startActivity(i);
            }
        }, time);
    }
}
