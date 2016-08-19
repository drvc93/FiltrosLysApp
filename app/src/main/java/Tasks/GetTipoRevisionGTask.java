package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import DataBase.PeriodoInspeccionDB;
import DataBase.TipoRevisionGBD;
import Util.Constans;

/**
 * Created by dvillanueva on 19/08/2016.
 */
public class GetTipoRevisionGTask extends AsyncTask<String, String, ArrayList<TipoRevisionGBD>> {
    ArrayList<TipoRevisionGBD> result;

    @Override
    protected ArrayList<TipoRevisionGBD> doInBackground(String... strings) {

        ArrayList<TipoRevisionGBD> listTipoRevision = new ArrayList<TipoRevisionGBD>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetTipoRevisionG";
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
            Log.i("result  get tipo revision ", resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);

                TipoRevisionGBD pt = new TipoRevisionGBD();
                pt.setCod_tiporevision(ic.getProperty(0).toString());
                pt.setDescripcion(ic.getProperty(1).toString());
                pt.setEstado(ic.getProperty(2).toString());
                pt.setUltFechaMod(ic.getProperty(3).toString());
                pt.setUltimoUsuario(ic.getProperty(4).toString());

                listTipoRevision.add(pt);
                //  Log.i("Usuario Nro > ", String.valueOf(i));
            }
            if (resSoap.getPropertyCount() > 0) {
                result = listTipoRevision;

            }

        } catch (Exception e) {
            Log.i("AsynckTask getTipoRevision error---", e.getMessage());
            result = null;
        }

        return result;
    }
}
