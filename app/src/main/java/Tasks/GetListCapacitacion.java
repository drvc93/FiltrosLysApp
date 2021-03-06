package Tasks;

import android.os.AsyncTask;
import android.util.Log;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;
import Model.CapacitacionCliente;
import Util.Constans;

public class GetListCapacitacion extends AsyncTask<String,String,ArrayList<CapacitacionCliente>> {
    ArrayList<CapacitacionCliente> result;
    @Override
    protected ArrayList<CapacitacionCliente> doInBackground(String... strings) {
        ArrayList<CapacitacionCliente> LstData = new ArrayList<CapacitacionCliente>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        final String METHOD_NAME = "ListCapacitacionCliente";
        final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("compania", strings[0]);
        request.addProperty("cliente", strings[1]);
        request.addProperty("estado", strings[2]);
        request.addProperty("fecharegini", strings[3]);
        request.addProperty("fecharegfin", strings[4]);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;

        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.bodyIn;

            int num_projects = resSoap.getPropertyCount();
            Log.i("result get lista capacitacion ", resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);

                CapacitacionCliente oEnt = new CapacitacionCliente();
                oEnt.setC_compania(ic.getPrimitivePropertyAsString("c_compania"));
                oEnt.setN_correlativo( Integer.valueOf( ic.getPrimitivePropertyAsString("n_correlativo")));
                oEnt.setN_cliente( Integer.valueOf( ic.getPrimitivePropertyAsString("n_cliente")));
                oEnt.setD_fecha(ic.getPrimitivePropertyAsString("d_fecha"));
                oEnt.setN_personas( Integer.valueOf( ic.getPrimitivePropertyAsString("n_personas")));
                oEnt.setD_fechaprob(ic.getPrimitivePropertyAsString("d_fechaprob"));
                oEnt.setD_horaprob(ic.getPrimitivePropertyAsString("d_horaprob"));
                oEnt.setC_lugar(ic.getPrimitivePropertyAsString("c_lugar"));
                oEnt.setN_direccioncli(Integer.valueOf(ic.getPrimitivePropertyAsString("n_direccioncli")));
                oEnt.setC_direccionreg(ic.getPrimitivePropertyAsString("c_direccionreg"));
                oEnt.setC_temacapacitacion(ic.getPrimitivePropertyAsString("c_temacapacitacion"));
                oEnt.setC_descripciontema(ic.getPrimitivePropertyAsString("c_descripciontema"));
                oEnt.setC_usuarioreg(ic.getPrimitivePropertyAsString("c_usuarioreg"));
                oEnt.setD_fechareg(ic.getPrimitivePropertyAsString("d_fechareg"));
                oEnt.setC_estado(ic.getPrimitivePropertyAsString("c_estado"));
                oEnt.setC_usuariorev(ic.getPrimitivePropertyAsString("c_usuariorev"));
                oEnt.setD_fecharev(ic.getPrimitivePropertyAsString("d_fecharev"));
                oEnt.setC_observacionrev(ic.getPrimitivePropertyAsString("c_observacionrev"));
                oEnt.setC_accionrev(ic.getPrimitivePropertyAsString("c_accionrev"));
                oEnt.setC_flagfinrev(ic.getPrimitivePropertyAsString("c_flagfinrev"));
                oEnt.setC_usuarioAN(ic.getPrimitivePropertyAsString("c_usuarioAN"));
                oEnt.setD_fechaAN(ic.getPrimitivePropertyAsString("d_fechaAN"));
                oEnt.setC_observacionAN(ic.getPrimitivePropertyAsString("c_observacionAN"));
                oEnt.setC_usuarioCE(ic.getPrimitivePropertyAsString("c_usuarioCE"));
                oEnt.setD_fechaCE(ic.getPrimitivePropertyAsString("d_fechaCE"));
                oEnt.setC_observacionCE(ic.getPrimitivePropertyAsString("c_observacionCE"));
                oEnt.setC_observacion(ic.getPrimitivePropertyAsString("c_observacion"));
                oEnt.setC_enviado("S");
                LstData.add(oEnt);
            }
            if (resSoap.getPropertyCount() > 0) {
                result = LstData;
            }

        } catch (Exception e) {
            Log.i("AsynckTask Lista capacitacion ---", e.getMessage());
        }
        return result;
    }
}
