package Tasks;

import android.os.AsyncTask;
import android.util.Log;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;
import Model.QuejaCliente;
import Util.Constans;

public class GetListQueja extends AsyncTask<String,String,ArrayList<QuejaCliente>> {
    ArrayList<QuejaCliente> result;
    @Override
    protected ArrayList<QuejaCliente> doInBackground(String... strings) {
        ArrayList<QuejaCliente> LstData = new ArrayList<QuejaCliente>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        final String METHOD_NAME = "ListQuejaCliente";
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
            Log.i("result get lista queja ", resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);

                QuejaCliente oEnt = new QuejaCliente();
                oEnt.setC_compania(ic.getPrimitivePropertyAsString("c_compania"));
                oEnt.setN_correlativo( Integer.valueOf( ic.getPrimitivePropertyAsString("n_correlativo")));
                oEnt.setC_queja(ic.getPrimitivePropertyAsString("c_queja"));
                oEnt.setN_cliente( Integer.valueOf( ic.getPrimitivePropertyAsString("n_cliente")));
                oEnt.setC_razonsocial(ic.getPrimitivePropertyAsString("c_razonsocial"));
                oEnt.setC_documentoref(ic.getPrimitivePropertyAsString("c_documentoref"));
                oEnt.setC_mediorecepcion(ic.getPrimitivePropertyAsString("c_mediorecepcion"));
                oEnt.setC_desqueja(ic.getPrimitivePropertyAsString("c_desqueja"));
                oEnt.setC_calificacion(ic.getPrimitivePropertyAsString("c_calificacion"));
                oEnt.setC_tipocalificacion(ic.getPrimitivePropertyAsString("c_tipocalificacion"));
                oEnt.setC_observaciones(ic.getPrimitivePropertyAsString("c_observaciones"));
                oEnt.setC_centrocosto(ic.getPrimitivePropertyAsString("c_centrocosto"));
                oEnt.setC_item(ic.getPrimitivePropertyAsString("c_item"));
                oEnt.setC_lote(ic.getPrimitivePropertyAsString("c_lote"));
                oEnt.setN_cantidad(Double.valueOf( ic.getPrimitivePropertyAsString("n_cantidad")));
                oEnt.setC_usuarioinvestigacion(ic.getPrimitivePropertyAsString("c_usuarioinvestigacion"));
                oEnt.setD_fechaderivacion(ic.getPrimitivePropertyAsString("d_fechaderivacion"));
                oEnt.setC_estado(ic.getPrimitivePropertyAsString("c_estado"));
                oEnt.setC_unidadneg(ic.getPrimitivePropertyAsString("c_unidadneg"));
                oEnt.setC_descripcioninvestigacion(ic.getPrimitivePropertyAsString("c_descripcioninvestigacion"));
                oEnt.setC_procede(ic.getPrimitivePropertyAsString("c_procede"));
                oEnt.setD_fecharespuesta(ic.getPrimitivePropertyAsString("d_fecharespuesta"));
                oEnt.setC_usuarioinvestigadopor(ic.getPrimitivePropertyAsString("c_usuarioinvestigadopor"));
                oEnt.setD_fechainvestigadopor(ic.getPrimitivePropertyAsString("d_fechainvestigadopor"));
                oEnt.setC_flaginvestigando(ic.getPrimitivePropertyAsString("c_flaginvestigando"));
                oEnt.setC_tipocalificacioncierre(ic.getPrimitivePropertyAsString("c_tipocalificacioncierre"));
                oEnt.setC_descripcioncierre(ic.getPrimitivePropertyAsString("c_descripcioncierre"));
                oEnt.setC_usuariocerrado(ic.getPrimitivePropertyAsString("c_usuariocerrado"));
                oEnt.setD_fechacerrado(ic.getPrimitivePropertyAsString("d_fechacerrado"));
                oEnt.setC_codaccion1(ic.getPrimitivePropertyAsString("c_codaccion1"));
                oEnt.setC_codaccion2(ic.getPrimitivePropertyAsString("c_codaccion2"));
                oEnt.setC_codaccion3(ic.getPrimitivePropertyAsString("c_codaccion4"));
                oEnt.setC_codaccion4(ic.getPrimitivePropertyAsString("c_cerrado"));
                oEnt.setC_notificacion(ic.getPrimitivePropertyAsString("c_notificacion"));
                oEnt.setD_fechareg(ic.getPrimitivePropertyAsString("d_fechareg"));
                oEnt.setC_observacionescierre(ic.getPrimitivePropertyAsString("c_observacionescierre"));
                LstData.add(oEnt);
            }
            if (resSoap.getPropertyCount() > 0) {
                result = LstData;
            }

        } catch (Exception e) {
            Log.i("AsynckTask Lista queja ---", e.getMessage());
        }
        return result;
    }
}
