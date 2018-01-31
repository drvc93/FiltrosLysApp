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
import Model.ReclamoGarantia;

/**
 * Creado por dvillanueva el  29/01/2018 (FiltrosLysApp).
 */

public class ReclamogarantiaAdapter  extends ArrayAdapter<ReclamoGarantia>{

    private Context  context ;
    private ArrayList<ReclamoGarantia> data ;
    private int layoutResourceId;
    ProdMantDataBase db ;

    public ReclamogarantiaAdapter(@NonNull Context context, int resource,ArrayList<ReclamoGarantia> data) {
        super(context, resource, data);
        this.context = context;
        this.data=data;
        this.layoutResourceId = resource;
        this.db = new ProdMantDataBase(context);
    }

    public  static  class  ViewHolder{
        TextView lblNroRG;
        TextView lblFecha;
        TextView lblCliente ;
        TextView lblFiltro ;
        TextView lblEstado ;
        TextView lblObsCliente ;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String sEstadoDescrip="" ,sFormatFecha = "";
        ViewHolder viewHolder;
        ReclamoGarantia rg = data.get(position);
        if (convertView==null){

            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.lblNroRG =  convertView.findViewById(R.id.lblItemNroReclamo);
            viewHolder.lblFecha = convertView.findViewById(R.id.lblItemFechaRG);
            viewHolder.lblCliente = convertView.findViewById(R.id.lblItemClienteRG);
            viewHolder.lblFiltro = convertView.findViewById(R.id.lblItemFIltroRG);
            viewHolder.lblEstado = convertView.findViewById(R.id.lblItemEstadoRG);
            viewHolder.lblObsCliente = convertView.findViewById(R.id.lblItemObsCli);
            convertView.setTag(viewHolder);

        }
        else  {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        switch (rg.getC_estado()){

            case "C":
                sEstadoDescrip ="Ingresado";
                break;
            case  "P":
                sEstadoDescrip ="Pendiente";
                break;
            case  "R":
                sEstadoDescrip ="En Proceso";
                break;
            case  "T":
                sEstadoDescrip ="Terminado";
                break;
            case  "A":
                sEstadoDescrip ="Anulado";
                break;
        }
       // Log.i("FFFF",rg.getD_fechaformato().substring(0,10));
        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = parseador.parse(rg.getD_fechaformato().substring(0,10));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sFormatFecha =(formateador.format(date));

        viewHolder.lblNroRG.setText(String.valueOf( rg.getN_correlativo()));
        viewHolder.lblFecha.setText(sFormatFecha);
        viewHolder.lblEstado.setText(sEstadoDescrip);
        viewHolder.lblCliente.setText(String.valueOf(rg.getN_cliente()) + " | " + db.GetNombreClienteXCod(String.valueOf(rg.getN_cliente())) );
        viewHolder.lblFiltro.setText(rg.getC_codproducto());
        viewHolder.lblObsCliente.setText(rg.getC_obscliente());
        return convertView;

    }


}
