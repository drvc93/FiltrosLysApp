package com.filtroslys.filtroslysapp;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Model.EmpleadoMant;
import Tasks.GetEmpleadosMantTask;

public class AsignarEmpleado extends AppCompatActivity {
    String sEstado  , sPrioridad , sNro , sFechaReg , sCompaniaSolcit;
    TextView  lblEstado , lblPrioridad , lblNroSolicitud , lblFecha ;
    CharSequence[]  EmpleadosData ;
    Button btnAgegar , btnGuardar;
    ArrayList<String> listaEmpledosAsignados;
    ListView lvEmpleadosMant ;
    ArrayAdapter<String> adapterLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_empleado);

        listaEmpledosAsignados = new ArrayList<String>();
        sNro= getIntent().getExtras().getString("NroSolicitud");
        sFechaReg= getIntent().getExtras().getString("FechaReg");
        sEstado= getIntent().getExtras().getString("Estado");
        sPrioridad= getIntent().getExtras().getString("Prioridad");
        sCompaniaSolcit = getIntent().getExtras().getString("Compania");
        setTitle("SOLICITUD NRO :" +sNro);

        lblEstado = (TextView) findViewById(R.id.lblEstadoAE) ;
        lblPrioridad = (TextView)findViewById(R.id.lblPrioridadAE);
        lblFecha =  (TextView)findViewById(R.id.lblFechaAE);
        lblNroSolicitud = (TextView)findViewById(R.id.lblNroSolicitudAE);
        btnAgegar = (Button)findViewById(R.id.btnAgregarEmp) ;
        lvEmpleadosMant = (ListView)findViewById(R.id.LVEmpleadosAsignados);
        lblEstado.setText(sEstado);
        lblFecha.setText(sFechaReg);
        lblNroSolicitud.setText(sNro);
        lblPrioridad.setText(sPrioridad);
        DataEmpleadosMant();

        btnAgegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopuPEmpleados(EmpleadosData);
            }
        });

    }


    public void DataEmpleadosMant () {

        GetEmpleadosMantTask getEmpleadosMantTask = new GetEmpleadosMantTask() ;
        AsyncTask<String,String,ArrayList<EmpleadoMant>> asyncTask ;
        ArrayList<EmpleadoMant> listEmplados = null;
        Log.i("Compania Solicitud" , sCompaniaSolcit);
        try {
            asyncTask = getEmpleadosMantTask.execute(sCompaniaSolcit) ;
            listEmplados =  ( ArrayList<EmpleadoMant>) asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listEmplados != null  && listEmplados.size()>0){
            EmpleadosData = new CharSequence[listEmplados.size()];

            for (int i  = 0 ;  i < listEmplados.size(); i++){
                EmpleadoMant e = listEmplados.get(i);
                EmpleadosData[i] = String.valueOf(e.getN_empleado()) + " | " + e.getC_nombreempleado();
            }

        }

    }

    public void PopuPEmpleados(final CharSequence[]  data) {



        AlertDialog.Builder builder = new AlertDialog.Builder(AsignarEmpleado.this);
        builder.setTitle("Seleccione un Empleado...");
        builder.setIcon(R.drawable.icn_add32);
        builder.setItems(data, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                // will toast your selection
                //showToast("Name: " + items[item]);
                ActulizarLVEmpleados(String.valueOf(data[item]));
                dialog.dismiss();

            }
        }).show();
    }

    public void   ActulizarLVEmpleados (String item ) {
        listaEmpledosAsignados.add(item);
        adapterLV=new ArrayAdapter<String>(AsignarEmpleado.this, android.R.layout.simple_list_item_1,listaEmpledosAsignados);
        lvEmpleadosMant.setAdapter(adapterLV);


    }



}
