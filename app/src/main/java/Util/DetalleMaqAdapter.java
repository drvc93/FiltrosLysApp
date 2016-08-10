package Util;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.filtroslys.filtroslysapp.R;

import java.util.ArrayList;
import java.util.List;

import Model.InspeccionMaqDetalle;

/**
 * Created by dvillanueva on 10/08/2016.
 */
public class DetalleMaqAdapter extends ArrayAdapter<InspeccionMaqDetalle> {

    Context  context;
    int resource ;
    ArrayList<InspeccionMaqDetalle> data;
    public DetalleMaqAdapter(Context context, int resource, ArrayList<InspeccionMaqDetalle> data) {
        super(context, resource, data);
        this.context= context;
        this.resource = resource;
        this.data = data;
    }

    private  static  class  ViewHolder {
        private TextView lblDescripInsp;
        private TextView lblTipoInsp;
        private TextView lblPorcenMin;
        private TextView lblPorcenMax;
        private EditText txtPorcentInsp;
        private Spinner   spEstado;
        private ImageView  imgFoto;
        private ImageView imgComent;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder = null;
        InspeccionMaqDetalle inp = data.get(position);
         ArrayList<String> listEstados = new ArrayList<String>();
        listEstados.add("--Selecccione--");
        listEstados.add("OK");
        listEstados.add("FALLA");


        if (convertView==null){

            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
             convertView = inflater.inflate(resource,parent,false);
            viewHolder = new ViewHolder();

            viewHolder.lblDescripInsp = (TextView)convertView.findViewById(R.id.lblInpecDescDet);
            viewHolder.lblTipoInsp = (TextView)convertView.findViewById(R.id.lblDetTipInsp);
            viewHolder.lblPorcenMin = (TextView)convertView.findViewById(R.id.lblDetPorcMin);
            viewHolder.lblPorcenMax = (TextView)convertView.findViewById(R.id.lblDetPorcMax);
            viewHolder.txtPorcentInsp = (EditText)convertView.findViewById(R.id.txtPorcInsp);
            viewHolder.txtPorcentInsp.setInputType(InputType.TYPE_CLASS_NUMBER);
            viewHolder.spEstado  = (Spinner)convertView.findViewById(R.id.spDetEsado);
            viewHolder.imgFoto = (ImageView)convertView.findViewById(R.id.imgDetFoto);
            viewHolder.imgComent = (ImageView)convertView.findViewById(R.id.imgDetComent);

        convertView.setTag(viewHolder);

        }
        else {

            viewHolder= (ViewHolder)convertView.getTag();
        }

        ArrayAdapter<String> arrayAdapterEstado = new ArrayAdapter<String>(context,R.layout.spiner_layout_estados,listEstados);
        arrayAdapterEstado.setDropDownViewResource(R.layout.spiner_layout_estados);
        viewHolder.spEstado.setAdapter(arrayAdapterEstado);

        viewHolder.lblDescripInsp.setText(inp.getDescripcionInspecion());
        viewHolder.lblPorcenMin.setText(inp.getPorcentMin());
        viewHolder.lblPorcenMax.setText(inp.getPorcentMax());
        viewHolder.lblTipoInsp.setText(inp.getTipo_inspecicon());
        Double porcent = Double.parseDouble(inp.getPorcentMax());
        if (porcent.intValue()>0){
            viewHolder.txtPorcentInsp.setEnabled(true);
            viewHolder.txtPorcentInsp.setHint("LLenar");
        }
        else {
            viewHolder.txtPorcentInsp.setEnabled(false);
            viewHolder.txtPorcentInsp.setHint("");
        }

        return convertView;
    }
}
