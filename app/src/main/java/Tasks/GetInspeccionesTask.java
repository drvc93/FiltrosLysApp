package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import DataBase.InspeccionDB;
import Util.Constans;

/**
 * Created by dvillanueva on 10/08/2016.
 */
public class GetInspeccionesTask extends AsyncTask<String,String,ArrayList<InspeccionDB>> {
    ArrayList<InspeccionDB> result ;
    @Override
    protected ArrayList<InspeccionDB> doInBackground(String... strings) {

        ArrayList<InspeccionDB> listInspecciones  = new ArrayList<InspeccionDB>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetInspecciones";
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
            int num_count = resSoap.getPropertyCount();
            Log.i("result  get inspecciones ",resSoap.toString());
            for (int i = 0; i < num_count; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);

                InspeccionDB inspeccionesDB = new InspeccionDB();
                inspeccionesDB.setC_descripcion(ic.getProperty(0).toString());
                inspeccionesDB.setC_estado(ic.getProperty(1).toString());
                inspeccionesDB.setC_familiainspeccion(ic.getProperty(2).toString());
                inspeccionesDB.setC_inspeccion(ic.getProperty(3).toString());
                inspeccionesDB.setC_periodoinspeccion(ic.getProperty(4).toString());
                inspeccionesDB.setC_tipoinspeccion(ic.getProperty(5).toString());
                inspeccionesDB.setC_ultimousuario(ic.getProperty(6).toString());
                inspeccionesDB.setD_ultimafechamodificacion(ic.getProperty(7).toString());
                inspeccionesDB.setN_porcentajemaximo(ic.getProperty(8).toString());
                inspeccionesDB.setN_porcentajeminimo(ic.getProperty(9).toString());



                listInspecciones.add(inspeccionesDB);
                //  Log.i("Usuario Nro > ", String.valueOf(i));
            }
            if (resSoap.getPropertyCount() > 0) {
                result = listInspecciones;

            }

        } catch (Exception e) {
            Log.i("AsynckTask inspecciones error---", e.getMessage());
            // result = null;
        }

        return result;
    }
}
