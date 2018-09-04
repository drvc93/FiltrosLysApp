package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.OpcionConsulta;
import Util.Constans;

/**
 * Creado por dvillanueva el  13/07/2018 (FiltrosLysApp).
 */

public class GetOpcionesConsultaTask extends AsyncTask<String,String,ArrayList<OpcionConsulta>> {
    ArrayList<OpcionConsulta> result ;
    @Override
    protected ArrayList<OpcionConsulta> doInBackground(String... strings) {
        ArrayList<OpcionConsulta> LstData = new ArrayList<>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        final String METHOD_NAME = "GetOpcionesConsulta";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("compania", strings[0]);
        Log.i("compania", strings[0]);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.bodyIn;

            int num_projects = resSoap.getPropertyCount();
            Log.i("result get lista opcion consulta  ", resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);
                OpcionConsulta oEnt = new OpcionConsulta();
                oEnt.setC_compania(ic.getPrimitivePropertyAsString("c_compania"));
                oEnt.setC_tipo(ic.getPrimitivePropertyAsString("c_tipo"));
                oEnt.setC_numero(ic.getPrimitivePropertyAsString("c_numero"));
                oEnt.setC_descripcion(ic.getPrimitivePropertyAsString("c_descripcion"));
                oEnt.setC_exportable(ic.getPrimitivePropertyAsString("c_exportable"));
                LstData.add(oEnt);
            }
            if (resSoap.getPropertyCount() > 0) {
                result = LstData;
            }

        } catch (Exception e) {
            Log.i("error task get lista opcion consulta ---", e.getMessage());
        }

        return  LstData;
    }
}
