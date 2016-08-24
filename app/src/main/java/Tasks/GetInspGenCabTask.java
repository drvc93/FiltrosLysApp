package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import DataBase.InspeccionDB;
import Model.InspeccionGenCabecera;
import Util.Constans;

/**
 * Created by dvillanueva on 24/08/2016.
 */
public class GetInspGenCabTask extends AsyncTask<String, String, ArrayList<InspeccionGenCabecera>> {
    ArrayList<InspeccionGenCabecera> result;

    @Override
    protected ArrayList<InspeccionGenCabecera> doInBackground(String... strings) {
        ArrayList<InspeccionGenCabecera> listInspG = new ArrayList<InspeccionGenCabecera>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "GetInspeccionGenCabCor";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("correlativo", strings[0]);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.bodyIn;
            int num_count = resSoap.getPropertyCount();
            Log.i("result  get inspecciones  cab ", resSoap.toString());
            for (int i = 0; i < num_count; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);

                InspeccionGenCabecera cab = new InspeccionGenCabecera();

                cab.setCentroCosto(ic.getProperty(0).toString());
                cab.setComentario(ic.getProperty(1).toString());
                cab.setCompania(ic.getProperty(2).toString());
                cab.setEstado(ic.getProperty(3).toString());
                cab.setCod_maquina(ic.getProperty(4).toString());
                cab.setTipoInspeccion(ic.getProperty(5).toString());
                cab.setUltUsuario(ic.getProperty(6).toString());
                cab.setUsuarioInsp(ic.getProperty(7).toString());
                cab.setFechaInsp(ic.getProperty(8).toString());
                cab.setUltFechaMod(ic.getProperty(9).toString());
                cab.setCorrelativo(ic.getProperty(10).toString());
                listInspG.add(cab);


            }
            if (resSoap.getPropertyCount() > 0) {
                result = listInspG;

            }

        } catch (Exception e) {
            Log.i("AsynckTask inspecciones gen cab error---", e.getMessage());
            // result = null;
        }

        return result;
    }
}
