package Util;

import android.app.Activity;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import Model.QuejaCliente;
import spencerstudios.com.fab_toast.FabToast;

public class QuejaClienteAdapter extends ArrayAdapter<QuejaCliente>{

    private Context  context ;
    private ArrayList<QuejaCliente> data ;
    private int layoutResourceId;
    ProdMantDataBase db ;

    public QuejaClienteAdapter(@NonNull Context context, int resource, ArrayList<QuejaCliente> data) {
        super(context,resource,data);
        this.context = context;
        this.data = data;
        this.layoutResourceId = resource;
        this.db = new ProdMantDataBase(context);
    }

    public static class ViewHolder{
        TextView lblNroQueja;
        TextView lblFechaRegQJ;
        TextView lblClienteQJ;
        TextView lblMedioRecQJ;
        TextView lblEstadoQJ;
        TextView lblDescripcionQJ;
        TextView lblEstadoFinQJ;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String sEstadoDescrip="",sFormatFecha="" ,sEstadoFinDescrip = "";
        ViewHolder viewHolder;
        QuejaCliente oEnt = data.get(position);
        if (convertView==null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.lblNroQueja =  convertView.findViewById(R.id.lblNroQueja);
            viewHolder.lblFechaRegQJ = convertView.findViewById(R.id.lblFechaRegQJ);
            viewHolder.lblClienteQJ = convertView.findViewById(R.id.lblClienteQJ);
            viewHolder.lblMedioRecQJ = convertView.findViewById(R.id.lblMedioRecQJ);
            viewHolder.lblEstadoQJ = convertView.findViewById(R.id.lblEstadoQJ);
            viewHolder.lblDescripcionQJ = convertView.findViewById(R.id.lblDescripcionQJ);
            viewHolder.lblEstadoFinQJ = convertView.findViewById(R.id.lblEstadoQJInvFin);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        switch (oEnt.getC_estado()){
            case "A":
                sEstadoDescrip ="Activas";
                break;
            case "AN":
                sEstadoDescrip ="Anuladas";
                break;
        }

        switch (oEnt.getC_estadofin()){
            case "PI":
                sEstadoFinDescrip = "Por Investigar";
                break;
            case  "PC":
                sEstadoFinDescrip = "Por Cerrar";
                break;
            case "CE":
                sEstadoFinDescrip = "Cerrada" ;
                break;
        }


        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = parseador.parse(oEnt.getD_fechareg().substring(0,10));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sFormatFecha =(formateador.format(date));

        viewHolder.lblNroQueja.setText(String.valueOf(oEnt.getC_queja()));
        viewHolder.lblFechaRegQJ.setText(sFormatFecha);
        viewHolder.lblEstadoQJ.setText(sEstadoDescrip);
        viewHolder.lblClienteQJ.setText(String.valueOf(oEnt.getN_cliente()) + " | " + db.GetNombreClienteXCod(String.valueOf(oEnt.getN_cliente())) );
        viewHolder.lblMedioRecQJ.setText( db.MedioRecepcionDesc(oEnt.getC_mediorecepcion()));
        viewHolder.lblDescripcionQJ.setText(oEnt.getC_desqueja());
        viewHolder.lblEstadoFinQJ.setText(sEstadoFinDescrip);
        return convertView;
    }

    public QuejaCliente getObject (int pos){
        return  data.get(pos);
    }

    public void RemoveItem (int pos) {
        long nResult = 0;
        nResult = db.DeleteQuejaCliente(data.get(pos));
        if (nResult>0)
        {
            data.remove(pos);
            FabToast.makeText(context, "Se eliminó correctamente el registro.", FabToast.LENGTH_LONG, FabToast.SUCCESS, FabToast.POSITION_DEFAULT).show();
        }
    }
}
