package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import DataBase.HistorialInspGenDB;
import DataBase.HistorialInspMaqDB;
import Util.Constans;

/**
 * Created by dvillanueva on 23/08/2016.
 */
public class GetHistorialInspGenTask extends AsyncTask<String, String, ArrayList<HistorialInspGenDB>> {
    ArrayList<HistorialInspGenDB> result;

    @Override
    protected ArrayList<HistorialInspGenDB> doInBackground(String... strings) {
        ArrayList<HistorialInspGenDB> listHistorial = new ArrayList<HistorialInspGenDB>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetHistorialInspGen";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("accion", strings[0]);
        request.addProperty("tipoInsp", strings[1]);
        request.addProperty("FInicio", strings[2]);
        request.addProperty("FFin", strings[3]);
        request.addProperty("estado", strings[4]);


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

                HistorialInspGenDB h = new HistorialInspGenDB();

                h.setCodCosto(ic.getProperty(0).toString());
                h.setCodMaq(ic.getProperty(1).toString());
                h.setComentario(ic.getProperty(2).toString());
                h.setEstado(ic.getProperty(3).toString());
                h.setFecha(ic.getProperty(4).toString());
                h.setNumero(ic.getProperty(5).toString());
                h.setTipoInsp(ic.getProperty(6).toString());
                h.setUsarioInp(ic.getProperty(7).toString());
                listHistorial.add(h);


                //  Log.i("Usuario Nro > ", String.valueOf(i));
            }
            if (resSoap.getPropertyCount() > 0) {
                result = listHistorial;

            }

        } catch (Exception e) {
            Log.i("AsynckTask Historial   gen error---", e.getMessage());
            // result = null;
        }

        return result;
    }
}
