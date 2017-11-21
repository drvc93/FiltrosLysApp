package Util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.filtroslys.filtroslysapp.R;

import java.util.ArrayList;

import Model.SolicitudServicio;

/**
 * Created by dvillanueva on 17/11/2017.
 */

public class SolicitudServAdapter extends ArrayAdapter<SolicitudServicio> {

    private Context context;
    private ArrayList<SolicitudServicio> data;
    private int resourceID;


    public SolicitudServAdapter(@NonNull Context context, int resource, @NonNull ArrayList<SolicitudServicio> data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
        this.resourceID = resource;

    }

    public static class ViewHolder {
        TextView lblSolocitante ;
        TextView lblPrioridad ;
        TextView lblMaq ;
        TextView lblCCosto ;
        TextView lblNroSolic ;
        TextView lblTipoSolc ;
        TextView lblEstado ;
        TextView lblFechaReg ;
        TextView lblDescripFalla ;
        TextView lblDescrProblema;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder ;
        SolicitudServicio  s = data.get(position);

        if (convertView ==null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resourceID,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.lblSolocitante = (TextView) convertView.findViewById(R.id.lblSolicitanteItem);
            viewHolder.lblPrioridad = (TextView) convertView.findViewById(R.id.lblPrioridad);
            viewHolder.lblMaq = (TextView) convertView.findViewById(R.id.lblMaquina);
            viewHolder.lblCCosto = (TextView) convertView.findViewById(R.id.lblCentroCosto);
            viewHolder.lblNroSolic = (TextView) convertView.findViewById(R.id.lblNroSolcitud);
            viewHolder.lblTipoSolc = (TextView) convertView.findViewById(R.id.lblTipoSolicitud);
            viewHolder.lblEstado = (TextView) convertView.findViewById(R.id.lblEstado);
            viewHolder.lblFechaReg = (TextView) convertView.findViewById(R.id.lblFechaReg);
            viewHolder.lblDescripFalla = (TextView) convertView.findViewById(R.id.lblDescFalla);
            viewHolder.lblDescrProblema = (TextView) convertView.findViewById(R.id.lblDescProblem);
            convertView.setTag(viewHolder);

        }
        else  {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.lblSolocitante.setText(s.getC_usuariosolicit());
        viewHolder.lblPrioridad.setText(s.getC_prioridad());
        viewHolder.lblMaq.setText(s.getC_maquina());
        viewHolder.lblCCosto.setText(s.getC_ccostonomb());
        viewHolder.lblNroSolic.setText(String.valueOf(s.getN_solicitud()));
        viewHolder.lblTipoSolc.setText(s.getC_tiposolcitud());
        viewHolder.lblEstado.setText(s.getC_estado());
        viewHolder.lblFechaReg.setText(s.getC_fechareg());
        viewHolder.lblDescrProblema.setText( "Descrip.Problema: " + s.getC_descriproblema());
        viewHolder.lblDescripFalla.setText("Descrip.Falla: " + s.getC_descfalla());

        return  convertView;

    }

    public SolicitudServicio GetItem (int position){
        return  data.get(position);
    }
}
