package com.filtroslys.filtroslysapp;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Model.RequisicionLogDet;
import Tasks.GetReqLogDetalleTask;

public class DetalleReqLog extends AppCompatActivity {

    TextView  lblNroReq  , lblEstado , lblFechaCreacion , lblUusuarioCreacion , lblCentroCosto , lblCantidadReg;
    ListView LVDetalle  ;
    String  sNroReq  , sEstado  , sFechaC , sUsuariC, sCCosto ,sCompania ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_req_log);
        lblNroReq = (TextView)findViewById(R.id.lblNroReqDet);
        lblEstado = (TextView)findViewById(R.id.lblEstadoReqDet);
        lblFechaCreacion = (TextView)findViewById(R.id.lblFechaCDet);
        lblUusuarioCreacion =  (TextView)findViewById(R.id.lblUsuarioCDet);
        lblCentroCosto = (TextView)findViewById(R.id.lblCCostoDet);
        lblCantidadReg = (TextView)findViewById(R.id.lblCantidadRegDet);
        LVDetalle = (ListView) findViewById(R.id.LVdetalleReqLog);

        sNroReq = getIntent().getExtras().getString("NroReq");
        sCCosto = getIntent().getExtras().getString("CCosto");
        sEstado = getIntent().getExtras().getString("Estado");
        sCompania = getIntent().getExtras().getString("Compania");
        sFechaC = getIntent().getExtras().getString("FCreacion");
        sUsuariC = getIntent().getExtras().getString("UCreacion");

        lblNroReq.setText(sNroReq);
        lblEstado.setText(sEstado);
        lblFechaCreacion.setText(sFechaC);
        lblUusuarioCreacion.setText(sUsuariC);
        lblCentroCosto.setText(sCCosto);

        setTitle("Requisicion Nº " + sNroReq);
        LoadDataDetalle();

    }

    public  void  LoadDataDetalle () {

        ArrayList<RequisicionLogDet> listareq = null  ;
        AsyncTask<String,String,ArrayList<RequisicionLogDet>> asyncTask ;
        GetReqLogDetalleTask getReqLogDetalleTask = new GetReqLogDetalleTask() ;
        ArrayList<String> data  = new ArrayList<String>();
        ArrayAdapter<String> adapterLV ;

        try {
            asyncTask = getReqLogDetalleTask.execute(sCompania,sNroReq);
            listareq = (ArrayList<RequisicionLogDet>) asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listareq != null && listareq.size()>0 ){
            for (int i = 0 ; i < listareq.size(); i ++){
                RequisicionLogDet det = listareq.get(i);
                Double a = Double.valueOf(det.getC_cantidadpedida());
                String cantpedidaformat = String.format("%.2f", a) ;
                String item = "ITEM : "+det.getC_item() +"  |  " + det.getC_descripcion() + "\n"  + "CANTIDAD: " + cantpedidaformat;
                data.add(item);
            }
            adapterLV=new ArrayAdapter<String>(DetalleReqLog.this, android.R.layout.simple_list_item_1,data);
            LVDetalle.setAdapter(adapterLV);
            lblCantidadReg.setText( "DETALLE ("+ String.valueOf( data.size())+" Registros)" );
        }


    }
}
