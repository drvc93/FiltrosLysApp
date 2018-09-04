package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.IMEMovil;
import Util.Constans;

/**
 * Creado por dvillanueva el  02/03/2018 (FiltrosLysApp).
 */

public class GetIMEIMovilesTask extends AsyncTask<String,String,ArrayList<IMEMovil>> {
    ArrayList<IMEMovil> result;
    @Override
    protected ArrayList<IMEMovil> doInBackground(String... strings) {
        ArrayList<IMEMovil> LstData = new ArrayList<IMEMovil>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        final String METHOD_NAME = "GetListIMEIMoviles";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("imei", strings[0]);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.bodyIn;

            int num_projects = resSoap.getPropertyCount();
            Log.i("result get lista imei ", resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);

                IMEMovil oEnt = new IMEMovil();
                oEnt.setC_estado(ic.getPrimitivePropertyAsString("c_estado"));
                oEnt.setC_imei(ic.getPrimitivePropertyAsString("c_imei"));
                oEnt.setC_numero(ic.getPrimitivePropertyAsString("c_numero"));
                oEnt.setC_seriechip(ic.getPrimitivePropertyAsString("c_seriechip"));
                oEnt.setC_tipo(ic.getPrimitivePropertyAsString("c_tipo"));
                oEnt.setC_ultimousuario(ic.getPrimitivePropertyAsString("c_ultimousuario"));
                oEnt.setC_usuarioreg(ic.getPrimitivePropertyAsString("c_usuarioreg"));
                oEnt.setD_fechareg(ic.getPrimitivePropertyAsString("d_fechareg"));
                oEnt.setD_ultimafechamodificacion(ic.getPrimitivePropertyAsString("d_ultimafechamodificacion"));
                LstData.add(oEnt);
            }
            if (resSoap.getPropertyCount() > 0) {
                result = LstData;
            }

        } catch (Exception e) {
            Log.i("AsynckTask lista imei ---", e.getMessage());
        }
        return result;
    }
}
