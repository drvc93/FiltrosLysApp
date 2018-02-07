package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.ReclamoGarantia;
import Util.Constans;

/**
 * Creado por dvillanueva el  30/01/2018 (FiltrosLysApp).
 */

public class GetListReclamoGar extends AsyncTask<String,String,ArrayList<ReclamoGarantia>> {
    ArrayList<ReclamoGarantia> result;
    @Override
    protected ArrayList<ReclamoGarantia> doInBackground(String... strings) {
        ArrayList<ReclamoGarantia> listaReq = new ArrayList<ReclamoGarantia>();
        final String NAMESPACE = Constans.NameSpaceWS;
        final String URL = Constans.UrlServer;
        //final String URL="http://10.0.2.2:8084/SOAPLYS?wsdl";
        final String METHOD_NAME = "ListReclamoGarantia";
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


            //lstProjects = new ArrayList<Parametros>();
            int num_projects = resSoap.getPropertyCount();
            Log.i("result  get  lista reclamo gar ", resSoap.toString());
            for (int i = 0; i < num_projects; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);

                ReclamoGarantia r = new ReclamoGarantia();

                r.setC_compania(ic.getPrimitivePropertyAsString("c_compania"));
                r.setN_correlativo( Integer.valueOf( ic.getPrimitivePropertyAsString("n_correlativo")));
                r.setD_fecha(ic.getPrimitivePropertyAsString("d_fecha"));
                r.setN_cliente( Integer.valueOf( ic.getPrimitivePropertyAsString("n_cliente")));
                r.setC_codproducto(ic.getPrimitivePropertyAsString("c_codproducto"));
                r.setC_lote(ic.getPrimitivePropertyAsString("c_lote"));
                r.setC_procedencia(ic.getPrimitivePropertyAsString("c_procedencia"));
                r.setN_qtyingreso(Double.valueOf( ic.getPrimitivePropertyAsString("n_qtyingreso")));
                r.setC_vendedor(ic.getPrimitivePropertyAsString("c_vendedor"));
                r.setC_estado(ic.getPrimitivePropertyAsString("c_estado"));
                r.setC_usuario(ic.getPrimitivePropertyAsString("c_usuario"));
                r.setC_lote1(ic.getPrimitivePropertyAsString("c_lote1"));
                r.setC_lote2(ic.getPrimitivePropertyAsString("c_lote2"));
                r.setC_lote3(ic.getPrimitivePropertyAsString("c_lote3"));
                r.setN_cantlote1(Double.valueOf( ic.getPrimitivePropertyAsString("n_cantlote1")));
                r.setN_cantlote2(Double.valueOf( ic.getPrimitivePropertyAsString("n_cantlote2")));
                r.setN_cantlote3(Double.valueOf( ic.getPrimitivePropertyAsString("n_cantlote3")));
                r.setC_codmarca(ic.getPrimitivePropertyAsString("c_codmarca"));
                r.setC_codmodelo(ic.getPrimitivePropertyAsString("c_codmodelo"));
                r.setN_pyear(Integer.valueOf( ic.getPrimitivePropertyAsString("n_pyear")));
                r.setC_tiempouso(ic.getPrimitivePropertyAsString("c_tiempouso"));
                r.setC_facturaRef(ic.getPrimitivePropertyAsString("c_facturaRef"));
                r.setC_prediagnostico(ic.getPrimitivePropertyAsString("c_prediagnostico"));
                r.setN_formato(Integer.valueOf( ic.getPrimitivePropertyAsString("n_formato")));
                r.setD_fechaformato(ic.getPrimitivePropertyAsString("d_fechaformato"));
                r.setC_obscliente(ic.getPrimitivePropertyAsString("c_obscliente"));
                r.setC_flagvisita(ic.getPrimitivePropertyAsString("c_flagvisita"));
                r.setC_tiporeclamo(ic.getPrimitivePropertyAsString("c_tiporeclamo"));
                r.setC_reembcliente(ic.getPrimitivePropertyAsString("c_reembcliente"));
                r.setC_placavehic(ic.getPrimitivePropertyAsString("c_placavehic"));
                r.setN_montoreembcli(Double.valueOf( ic.getPrimitivePropertyAsString("n_montoreembcli")));
                r.setC_monedareembcli(ic.getPrimitivePropertyAsString("c_monedareembcli"));
                r.setC_pruebalab1(ic.getPrimitivePropertyAsString("c_pruebalab1"));
                r.setC_pruebalab2(ic.getPrimitivePropertyAsString("c_pruebalab2"));
                r.setC_pruebalab3(ic.getPrimitivePropertyAsString("c_pruebalab3"));
                r.setC_ensayo01(ic.getPrimitivePropertyAsString("c_ensayo01"));
                r.setC_ensayo02(ic.getPrimitivePropertyAsString("c_ensayo02"));
                r.setC_ensayo03(ic.getPrimitivePropertyAsString("c_ensayo03"));
                r.setC_ensayo04(ic.getPrimitivePropertyAsString("c_ensayo04"));
                r.setC_ensayo05(ic.getPrimitivePropertyAsString("c_ensayo05"));
                r.setC_enviado("S");

                listaReq.add(r);


                //  Log.i("Usuario Nro > ", String.valueOf(i));
            }
            if (resSoap.getPropertyCount() > 0) {
                result = listaReq;

            }

        } catch (Exception e) {
            Log.i("AsynckTask Lista  reclamo gar ---", e.getMessage());
            // result = null;
        }

        return result;
    }
}
