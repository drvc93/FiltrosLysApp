package Util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.filtroslys.filtroslysapp.R;

import junit.framework.TestResult;

import java.util.ArrayList;
import java.util.zip.Inflater;

import DataBase.HistorialInspMaqDB;

/**
 * Created by dvillanueva on 16/08/2016.
 */
public class HistorialInspMaqAdapater extends ArrayAdapter<HistorialInspMaqDB> {
    private Context  context ;
    private ArrayList<HistorialInspMaqDB> data ;

    private int layoutResourceId;

    public HistorialInspMaqAdapater(Context context, int resource, ArrayList<HistorialInspMaqDB>data) {
        super(context, resource,data);
        this.context = context;
        this.data=data;
        this.layoutResourceId = resource;

    }

    public  static  class  ViewHolder{
        TextView lblNumero;
        TextView lblFecha;
        TextView lblMaquina ;
        TextView lblCentroC ;
        TextView lblFrecuencia ;
        TextView lblUsuario ;
        TextView lblObserv ;
        TextView lblEstado;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        HistorialInspMaqDB h = data.get(position);

        if (convertView==null){

            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.lblNumero = (TextView) convertView.findViewById(R.id.txtHMNro);
            viewHolder.lblFecha = (TextView) convertView.findViewById(R.id.txtHMFecha);
            viewHolder.lblMaquina = (TextView) convertView.findViewById(R.id.txtHMMaq);
            viewHolder.lblCentroC = (TextView)convertView.findViewById(R.id.txtHMCentroC);
            viewHolder.lblFrecuencia = (TextView)convertView.findViewById(R.id.txtHMFrec);
            viewHolder.lblUsuario = (TextView)convertView.findViewById(R.id.txtHMUsuario);
            viewHolder.lblObserv = (TextView)convertView.findViewById(R.id.txtHMobservaciones);
            viewHolder.lblEstado = (TextView) convertView.findViewById(R.id.txtHMestado);

            convertView.setTag(viewHolder);

        }
        else  {
           viewHolder = (ViewHolder)convertView.getTag();
        }



        viewHolder.lblNumero.setText(h.getNumero());
        viewHolder.lblFecha.setText(h.getFecha());
        viewHolder.lblMaquina.setText(h.getCod_maquina());
        viewHolder.lblCentroC.setText(h.getCentro_costo());
        viewHolder.lblFrecuencia.setText(h.getFrecuencia());
        viewHolder.lblUsuario.setText(h.getUsuario());
        viewHolder.lblObserv.setText(h.getComentario());
        viewHolder.lblEstado.setText(h.getEstado());

        return convertView;
    }
}
