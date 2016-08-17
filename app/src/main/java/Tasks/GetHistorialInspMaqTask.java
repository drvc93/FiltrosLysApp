package Tasks;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import DataBase.CentroCostoDB;
import DataBase.HistorialInspMaqDB;
import Util.Constans;

/**
 * Created by dvillanueva on 16/08/2016.
 */
public class GetHistorialInspMaqTask extends AsyncTask<String,String,ArrayList<HistorialInspMaqDB>> {
    ArrayList<HistorialInspMaqDB> result;
    @Override
    protected ArrayList<HistorialInspMaqDB> doInBackground(String... strings) {
        ArrayList<HistorialInspMaqDB> listHistorial  = new ArrayList<HistorialInspMaqDB>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetHistorialInspMaq";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("accion", strings[0]);
        request.addProperty("maquina", strings[1]);
        request.addProperty("centrocosto", strings[2]);
        request.addProperty("fechaIni", strings[3]);
        request.addProperty("fechaFin", strings[4]);

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
            Log.i("result  get  historial ",resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);

                HistorialInspMaqDB ht = new HistorialInspMaqDB();

                ht.setCentro_costo(ic.getProperty(0).toString());
                ht.setCod_maquina(ic.getProperty(1).toString());
                ht.setComentario(ic.getProperty(2).toString());
                ht.setEstado(ic.getProperty(3).toString());
                ht.setFecha(ic.getProperty(4).toString());
                ht.setFrecuencia(ic.getProperty(5).toString());
                ht.setNumero(ic.getProperty(6).toString());
                ht.setUsuario(ic.getProperty(7).toString());



                listHistorial.add(ht);
                //  Log.i("Usuario Nro > ", String.valueOf(i));
            }
            if (resSoap.getPropertyCount() > 0) {
                result = listHistorial;

            }

        } catch (Exception e) {
            Log.i("AsynckTask Historial  error---", e.getMessage());
            // result = null;
        }

        return result;
    }

}
