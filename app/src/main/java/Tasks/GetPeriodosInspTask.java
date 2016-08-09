package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import DataBase.PeriodoInspeccionDB;
import DataBase.UsuarioDB;
import Util.Constans;

/**
 * Created by dvillanueva on 09/08/2016.
 */
public class GetPeriodosInspTask extends AsyncTask<String,String,ArrayList<PeriodoInspeccionDB>> {
    ArrayList<PeriodoInspeccionDB> result ;

    @Override
    protected ArrayList<PeriodoInspeccionDB> doInBackground(String... strings) {
        ArrayList<PeriodoInspeccionDB> listPeriodo  = new ArrayList<PeriodoInspeccionDB>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetPeriodosInspeccion";
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

                PeriodoInspeccionDB  p = new PeriodoInspeccionDB();

                p.setC_descripcion(ic.getProperty(0).toString());
                p.setC_estado(ic.getProperty(1).toString());
                p.setC_periodoinspeccion(ic.getProperty(2).toString());
                p.setC_ultimousuario(ic.getProperty(3).toString());
                p.setD_ultimafechamodificacion(ic.getProperty(4).toString());

                listPeriodo.add(p);
                //  Log.i("Usuario Nro > ", String.valueOf(i));
            }
            if (resSoap.getPropertyCount() > 0) {
                result = listPeriodo;

            }

        } catch (Exception e) {
            Log.i("AsynckTask GetUsers error---", e.getMessage());
            result = null;
        }

        return result;
    }
}
