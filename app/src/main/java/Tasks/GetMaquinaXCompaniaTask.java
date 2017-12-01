package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import DataBase.MaquinaDB;
import Util.Constans;

/**
 * Created by dvillanueva on 29/11/2017.
 */

public class GetMaquinaXCompaniaTask extends AsyncTask<String,String,ArrayList<MaquinaDB>> {
    ArrayList<MaquinaDB> result ;


    @Override
    protected ArrayList<MaquinaDB> doInBackground(String... strings) {

        ArrayList<MaquinaDB> listMaquinas  = new ArrayList<MaquinaDB>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetMaquinaXCompania";
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
            Log.i(" Maquinas X Compania ",resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);

                MaquinaDB maquinaDB = new MaquinaDB();

                maquinaDB.setC_centrocosto(ic.getProperty(0).toString());
                maquinaDB.setC_codigobarras(ic.getProperty(1).toString());
                maquinaDB.setC_descripcion(ic.getProperty(2).toString());
                maquinaDB.setC_estado(ic.getProperty(3).toString());
                maquinaDB.setC_familiainspeccion(ic.getProperty(4).toString());
                maquinaDB.setC_maquina(ic.getProperty(5).toString());
                maquinaDB.setC_ompania(ic.getProperty(6).toString());
                maquinaDB.setC_ultimousuario(ic.getProperty(7).toString());
                maquinaDB.setD_ultimafechamodificacion(ic.getProperty(8).toString());


                listMaquinas.add(maquinaDB);
                //  Log.i("Usuario Nro > ", String.valueOf(i));
            }
            if (resSoap.getPropertyCount() > 0) {
                result = listMaquinas;

            }

        } catch (Exception e) {
            Log.i(" Maquinas X Compania error---", e.getMessage());
            // result = null;
        }

        return result;

    }
}
