package Util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.filtroslys.filtroslysapp.R;

import java.util.ArrayList;
import java.util.List;

import Model.RequisicionLogCab;

/**
 * Created by dvillanueva on 22/11/2017.
 */

public class ReqLogisticaAdapter extends ArrayAdapter<RequisicionLogCab> {
    private Context context;
    private ArrayList<RequisicionLogCab> data;
    private int resourceID;

    public ReqLogisticaAdapter(@NonNull Context context, int resource, @NonNull ArrayList<RequisicionLogCab> data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
        this.resourceID = resource;
    }

    public static class ViewHolder {
        TextView lblNroRqueLog ;
        TextView lblCCosto ;
        TextView lblFecgaCreacion ;
        TextView  lblUsuarioCreacion;
        TextView lblPrioridad ;
        TextView lblEstado ;
        TextView lblCometario ;
        TextView lblClasificacion ;
        TextView lblRepStock ;
        TextView lblAlmacen ;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder  viewHolder ;
        RequisicionLogCab  r = data.get(position);
        if (convertView == null){

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resourceID,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.lblNroRqueLog = (TextView) convertView.findViewById(R.id.lblNroReqLog);
            viewHolder.lblCCosto = (TextView)convertView.findViewById(R.id.lblCCostoRL);
            viewHolder.lblFecgaCreacion = (TextView)convertView.findViewById(R.id.lblFechaCRL);
            viewHolder.lblUsuarioCreacion = (TextView)convertView.findViewById(R.id.lblUsuarioCreaRL);
            viewHolder.lblPrioridad = (TextView)convertView.findViewById(R.id.lblPrioridadRL);
            viewHolder.lblEstado = (TextView)convertView.findViewById(R.id.lblEstadoRL);
            viewHolder.lblCometario = (TextView)convertView.findViewById(R.id.lblCometarioRL);
            viewHolder.lblAlmacen = (TextView)convertView.findViewById(R.id.lblAlmacen);
            viewHolder.lblClasificacion = (TextView)convertView.findViewById(R.id.lblClasificacion);
            viewHolder.lblRepStock = (TextView)convertView.findViewById(R.id.lblReposicionRL);
            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.lblNroRqueLog.setText(r.getC_numeroreq());
        viewHolder.lblCCosto.setText(r.getC_ccostonomb());
        viewHolder.lblFecgaCreacion.setText(r.getC_fechacreacion());
        viewHolder.lblUsuarioCreacion.setText(r.getC_usuariocreacion());
        viewHolder.lblPrioridad.setText(r.getC_prioridadnomb());
        viewHolder.lblEstado.setText(r.getC_estadonomb());
        viewHolder.lblCometario.setText(r.getC_cometario());
        viewHolder.lblRepStock.setText(r.getC_reposicionstock());
        viewHolder.lblClasificacion.setText(r.getC_clasificacion());
        viewHolder.lblAlmacen.setText(r.getC_almacennomb());
        return convertView;

    }

    public RequisicionLogCab GetItem (int position){
        return  data.get(position);
    }
}
