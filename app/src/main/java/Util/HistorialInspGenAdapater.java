package Util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.filtroslys.filtroslysapp.R;

import java.util.ArrayList;
import java.util.List;

import DataBase.HistorialInspGenDB;


/**
 * Created by dvillanueva on 23/08/2016.
 */
public class HistorialInspGenAdapater extends ArrayAdapter<HistorialInspGenDB> {
    private Context context;
    private ArrayList<HistorialInspGenDB> data;
    private int resourceID;
    public  String flagFormat ="N";

    public HistorialInspGenAdapater(Context context, int resource, ArrayList<HistorialInspGenDB> data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
        this.resourceID = resource;


    }

    public static class ViewHolder {
        TextView lblNumero;
        TextView lblFecha;
        TextView lblMaquina;
        TextView lblCentroC;
        TextView lbltipo;
        TextView lblUsuario;
        TextView lblObserv;
        TextView lblEstado;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        HistorialInspGenDB h = data.get(position);
        if (flagFormat.equals("S")){
            if (h.getEstado().equals("I")){
                h.setEstado("Ingresado");
            }
            else  if (h.getEstado().equals("E")){
                h.setEstado("Enviado");
            }
        }
        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resourceID, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.lblNumero = (TextView) convertView.findViewById(R.id.txtNroDetG);
            viewHolder.lblFecha = (TextView) convertView.findViewById(R.id.txtFechaDetG);
            viewHolder.lblMaquina = (TextView) convertView.findViewById(R.id.txtMaquinaDetG);
            viewHolder.lblCentroC = (TextView) convertView.findViewById(R.id.txtCentroCDetG);
            viewHolder.lbltipo = (TextView) convertView.findViewById(R.id.txtTipoDetG);
            viewHolder.lblUsuario = (TextView) convertView.findViewById(R.id.txtUsuarioDetG);
            viewHolder.lblObserv = (TextView) convertView.findViewById(R.id.txtCoemntarioDetG);
            viewHolder.lblEstado = (TextView) convertView.findViewById(R.id.txtEstadoDetG);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.lblNumero.setText(h.getNumero());
        viewHolder.lblFecha.setText(h.getFecha());
        Log.i("adapter fecha insp", h.getFecha());
        viewHolder.lblMaquina.setText(h.getCodMaq());
        viewHolder.lblCentroC.setText(h.getCodCosto());
        viewHolder.lblUsuario.setText(h.getUsarioInp());
        viewHolder.lblObserv.setText(h.getComentario());
        viewHolder.lbltipo.setText(h.getTipoInsp());
        viewHolder.lblEstado.setText(h.getEstado());
        return convertView;
    }

    public HistorialInspGenDB GetItem(int position) {

        return data.get(position);
    }


    public   String  FormatFecha (String sFecha){
        String result = "";
        result  = sFecha.substring(3,5) + "/"+ sFecha.substring(0,2) + "/"+sFecha.substring(6,10);
        return  result;
    }
}
