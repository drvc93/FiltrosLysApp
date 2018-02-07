package Util;

import android.app.Activity;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.filtroslys.filtroslysapp.R;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import DataBase.ProdMantDataBase;
import Model.CapacitacionCliente;

public class CapacitacionClienteAdapter extends ArrayAdapter<CapacitacionCliente>{

    private Context  context ;
    private ArrayList<CapacitacionCliente> data ;
    private int layoutResourceId;
    ProdMantDataBase db ;

    public CapacitacionClienteAdapter(@NonNull Context context, int resource, ArrayList<CapacitacionCliente> data) {
        super(context,resource,data);
        this.context = context;
        this.data = data;
        this.layoutResourceId = resource;
        this.db = new ProdMantDataBase(context);
    }

    public static class ViewHolder{
        TextView lblNroCapacitacion;
        TextView lblFechaRegCP;
        TextView lblClienteCP;
        TextView lblFechaProbableCP;
        TextView lblHoraProbableCP;
        TextView lblLugarCP;
        TextView lblEstadoCP;
        TextView lblDescripcionCP;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String sEstadoDescrip="",sFormatFecha="",sFormatFechaPR="",sFormatFechaHP="",sLugarDesc="";
        String sTemaPrin = "";
        ViewHolder viewHolder;
        CapacitacionCliente oEnt = data.get(position);
        if (convertView==null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.lblNroCapacitacion =  convertView.findViewById(R.id.lblNroCapacitacion);
            viewHolder.lblFechaRegCP = convertView.findViewById(R.id.lblFechaRegCP);
            viewHolder.lblClienteCP = convertView.findViewById(R.id.lblClienteCP);
            viewHolder.lblFechaProbableCP = convertView.findViewById(R.id.lblFechaProbableCP);
            viewHolder.lblHoraProbableCP = convertView.findViewById(R.id.lblHoraProbableCP);
            viewHolder.lblLugarCP = convertView.findViewById(R.id.lblLugarCP);
            viewHolder.lblEstadoCP = convertView.findViewById(R.id.lblEstadoCP);
            viewHolder.lblDescripcionCP = convertView.findViewById(R.id.lblDescripcionCP);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        switch (oEnt.getC_estado()){
            case "PE":
                sEstadoDescrip ="Pendiente";
                break;
            case "RE":
                sEstadoDescrip ="Revisado";
                break;
            case "CE":
                sEstadoDescrip ="Cerrado";
                break;
            case "AN":
                sEstadoDescrip ="Anulado";
                break;
        }

        //Fecha
        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = parseador.parse(oEnt.getD_fechareg().substring(0,10));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sFormatFecha =(formateador.format(date));

        //Fecha Probable
        Log.i("fecha prob adapter" , oEnt.getD_fechaprob());
        Log.i("fecha hora adapter" , oEnt.getD_horaprob());
        SimpleDateFormat parseadorFP = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formateadorFP = new SimpleDateFormat("dd/MM/yyyy");
        Date dateFP = null;
        try {
            dateFP = parseadorFP.parse(oEnt.getD_fechaprob());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sFormatFechaPR =(formateadorFP.format(dateFP));

        //Hora Probable
        SimpleDateFormat parseadorHP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formateadorHP = new SimpleDateFormat("HH:mm");
        Date dateHP = null;
        try {
            dateHP = parseadorHP.parse(oEnt.getD_horaprob());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sFormatFechaHP =(formateadorHP.format(dateHP));

        //Lugar Capacitacion
        switch (oEnt.getC_lugar()){
            case "PL":
                sLugarDesc ="Planta Lys";
                break;
            case "LC":
                sLugarDesc ="Local Cliente";
                break;
            case "OT":
                sLugarDesc ="Otros";
                break;
        }

        //Tema Capacitacion
        sTemaPrin = db.GetNombreTemaCapacitacion(oEnt.getC_temacapacitacion());
        if(sTemaPrin.length()>0){
            sTemaPrin = sTemaPrin + "/";
        }
        sTemaPrin = sTemaPrin + oEnt.getC_descripciontema();

        viewHolder.lblNroCapacitacion.setText(String.valueOf(oEnt.getN_correlativo()));
        viewHolder.lblFechaRegCP.setText(sFormatFecha);
        viewHolder.lblEstadoCP.setText(sEstadoDescrip);
        viewHolder.lblFechaProbableCP.setText(sFormatFechaPR);
        viewHolder.lblHoraProbableCP.setText(sFormatFechaHP);
        viewHolder.lblClienteCP.setText(String.valueOf(oEnt.getN_cliente()) + " | " + db.GetNombreClienteXCod(String.valueOf(oEnt.getN_cliente())) );
        viewHolder.lblLugarCP.setText(sLugarDesc);
        viewHolder.lblDescripcionCP.setText(sTemaPrin);
        return convertView;
    }

    public  CapacitacionCliente getObject (int pos){
        return  data.get(pos);
    }


}
