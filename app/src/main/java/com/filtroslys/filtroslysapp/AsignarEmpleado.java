package com.filtroslys.filtroslysapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Model.EmpAsigSolicitud;
import Model.EmpleadoMant;
import Model.SolicitudServicio;
import Tasks.GetEmpleadosMantTask;
import Tasks.GetEmpleadosSolicitudTask;
import Tasks.InsertEmpSolicitudTask;
import Tasks.LimpiandoDetalleEmpSolTask;
import Tasks.ValidarEmpleadoMantTask;
import Util.Constans;

public class AsignarEmpleado extends AppCompatActivity {
    String sEstado  , sPrioridad , sNro , sFechaReg , sCompaniaSolcit,sFlagTieneEmpleados ;
    TextView  lblEstado , lblPrioridad , lblNroSolicitud , lblFecha ;
    CharSequence[]  EmpleadosData ;
    Button btnAgegar , btnGuardar;
    ArrayList<String> listaEmpledosAsignados;
    ListView lvEmpleadosMant ;
    ArrayAdapter<String> adapterLV;
    ArrayList<EmpleadoMant> listEmpladosTodos = null;
    SharedPreferences preferences;
    String codUser ;
    int nItemSeletec  = 0;

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

        preferences = PreferenceManager.getDefaultSharedPreferences(AsignarEmpleado.this);
        codUser = preferences.getString("UserCod", null);
        lblEstado = (TextView) findViewById(R.id.lblEstadoAE) ;
        lblPrioridad = (TextView)findViewById(R.id.lblPrioridadAE);
        lblFecha =  (TextView)findViewById(R.id.lblFechaAE);
        lblNroSolicitud = (TextView)findViewById(R.id.lblNroSolicitudAE);
        btnAgegar = (Button)findViewById(R.id.btnAgregarEmp) ;
        btnGuardar = (Button) findViewById(R.id.btnGuardarAsignEmp);
        lvEmpleadosMant = (ListView)findViewById(R.id.LVEmpleadosAsignados);
        lblEstado.setText(sEstado);
        lblFecha.setText(sFechaReg);
        lblNroSolicitud.setText(sNro);
        lblPrioridad.setText(sPrioridad);
        ListaEmpAsignados();
        DataEmpleadosMant();

        btnAgegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopuPEmpleados(EmpleadosData);
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AvisoGuardarInfo();
            }
        });

        lvEmpleadosMant.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                nItemSeletec = i;
                MenuOpcionesItem();
                return false;
            }
        });
    }


    public  void GuardarInformacion (){
        String sCompaniaEmpleado , nCodEmpleado , sUsuarioReg, nSecuencia, InsertResutl= "", LimpiaDetalle = "-";
        sUsuarioReg = codUser;
        int CountOK = 0 ;

        if (sFlagTieneEmpleados.equals("S")){
                AsyncTask<String,String,String> asyncTaskLimpiar;
            LimpiandoDetalleEmpSolTask limpiandoDetalleEmpSolTask = new LimpiandoDetalleEmpSolTask();

            try {
                asyncTaskLimpiar = limpiandoDetalleEmpSolTask.execute(sCompaniaSolcit, sNro) ;
                LimpiaDetalle = (String) asyncTaskLimpiar.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            if (LimpiaDetalle.equals("OK")) {

            }

        }

        if ( lvEmpleadosMant!= null && lvEmpleadosMant.getCount()>0) {

            for (int i = 0 ; i < lvEmpleadosMant.getCount() ; i++){
                nCodEmpleado =  lvEmpleadosMant.getItemAtPosition(i).toString();
                nCodEmpleado = nCodEmpleado.substring(0,nCodEmpleado.indexOf("|")-1);
                nSecuencia = String.valueOf(i+1);
                sCompaniaEmpleado = CompaniaEmpleado(nCodEmpleado) ;
                AsyncTask<String,String,String> asyncTask ;
                InsertEmpSolicitudTask insertEmpSolicitudTask = new InsertEmpSolicitudTask();

                try {
                    asyncTask = insertEmpSolicitudTask.execute(sCompaniaSolcit,sNro,nSecuencia,sCompaniaEmpleado,nCodEmpleado,sUsuarioReg);
                    InsertResutl = (String)asyncTask.get();

                    if (InsertResutl.equals("OK")){
                        CountOK = CountOK + 1 ;
                       // sendSMS(MovilEmpleado(nCodEmpleado),"Estimado, se le ha asignado la solicitud de servicio Nro.:"+sNro+" .");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }

            if (CountOK >0 ){
                CreateCustomToast("Se registro correctamente la información.",Constans.icon_succes,Constans.layout_success);

            }
            else {
                CreateCustomToast("Error al momento de registrar , intentar nuevamente por favor.",Constans.icon_error,Constans.layout_error);
            }
        }
        else {
            CreateCustomToast("No existen empleados para registrar",Constans.icon_warning,Constans.icon_warning);
        }


    }

    public  void  ListaEmpAsignados (){
        GetEmpleadosSolicitudTask getEmpleadosSolicitudTask  = new GetEmpleadosSolicitudTask();
        AsyncTask<String, String , ArrayList<EmpAsigSolicitud>> asyncTask ;
        ArrayList<EmpAsigSolicitud> listEmpAsig  = null;

        Log.i("Comp EmpAsi",sCompaniaSolcit);
        Log.i("Comp Nro Solic",sNro);
        try {
            asyncTask = getEmpleadosSolicitudTask.execute(sCompaniaSolcit,sNro) ;
            listEmpAsig = (ArrayList<EmpAsigSolicitud>) asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listEmpAsig != null && listEmpAsig.size()>0){

            for (int i = 0 ; i < listEmpAsig.size(); i++){
                EmpAsigSolicitud e =  listEmpAsig.get(i) ;
                listaEmpledosAsignados.add(e.getN_empleado()+" | "+ e.getC__nombreempleado());
            }
            adapterLV=new ArrayAdapter<String>(AsignarEmpleado.this, android.R.layout.simple_list_item_1,listaEmpledosAsignados);
            lvEmpleadosMant.setAdapter(adapterLV);
            sFlagTieneEmpleados = "S" ;

        }
        else {
            CreateCustomToast("La solicitud no tiene empleados asignados aun. ", Constans.icon_warning, Constans.layot_warning);
            sFlagTieneEmpleados = "N" ;
        }



    }


    public void DataEmpleadosMant () {

        GetEmpleadosMantTask getEmpleadosMantTask = new GetEmpleadosMantTask() ;
        AsyncTask<String,String,ArrayList<EmpleadoMant>> asyncTask ;

        Log.i("Compania Solicitud" , sCompaniaSolcit);
        try {
            asyncTask = getEmpleadosMantTask.execute(sCompaniaSolcit) ;
            listEmpladosTodos =  ( ArrayList<EmpleadoMant>) asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listEmpladosTodos != null  && listEmpladosTodos.size()>0){
            EmpleadosData = new CharSequence[listEmpladosTodos.size()];

            for (int i  = 0 ;  i < listEmpladosTodos.size(); i++){
                EmpleadoMant e = listEmpladosTodos.get(i);
                EmpleadosData[i] = String.valueOf(e.getN_empleado()) + " | " + e.getC_nombreempleado();
            }


        }

    }

    public  String CompaniaEmpleado (String sCodEmpleado){
        String result = "";
        if ( listEmpladosTodos!= null && listEmpladosTodos.size()>0){
            for (int i = 0 ; i <listEmpladosTodos.size(); i++){
                EmpleadoMant e =  listEmpladosTodos.get(i);
                if (sCodEmpleado.equals(String.valueOf(e.getN_empleado()))){
                    result = e.getC_compania();
                    break;
                }
                else {
                    result = "" ;
                }
            }
        }

        return result  ;
    }

    public  String MovilEmpleado (String CodEmpleado){
        String result = "";
        if ( listEmpladosTodos!= null && listEmpladosTodos.size()>0){
            for (int i = 0 ; i <listEmpladosTodos.size(); i++){
                EmpleadoMant e =  listEmpladosTodos.get(i);
                if (CodEmpleado.equals(String.valueOf(e.getN_empleado()))){
                    result = e.getC_numeromovil();
                    break;
                }
                else {
                    result = "" ;
                }
            }
        }

        return result  ;
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

        String sCodEmpleado =  item.substring(0,item.indexOf("|")-1);
        String sVerifcacionEmp = "NO" ;
        if (listaEmpledosAsignados.size()>0){
            sVerifcacionEmp = VerificarEmpleado(sCodEmpleado);
        }

        if (sVerifcacionEmp.equals("NO")) {
            listaEmpledosAsignados.add(item);
            adapterLV = new ArrayAdapter<String>(AsignarEmpleado.this, android.R.layout.simple_list_item_1, listaEmpledosAsignados);
            lvEmpleadosMant.setAdapter(adapterLV);
        }
        else {
            CreateCustomToast("El empleado de codigo "  + sCodEmpleado +" ya ha sido asignado.", Constans.icon_error, Constans.layout_error);
        }


    }
    public  String VerificarEmpleado (String codEmp){
        String result = "" ;
        String codTemp = "" ;
        for (int i = 0 ; i<listaEmpledosAsignados.size();i++){
            codTemp = listaEmpledosAsignados.get(i);
            codTemp =  codTemp.substring(0,codTemp.indexOf("|")-1);

            if (codEmp.equals(codTemp)) {
                result  = "SI" ;
             break;
            }
            else {
                 result  = "NO" ;
            }
        }
        return  result;
    }

    public void CreateCustomToast(String msj, int icon, int backgroundLayout) {

        LayoutInflater infator = getLayoutInflater();
        View layout = infator.inflate(R.layout.toast_alarm_success, (ViewGroup) findViewById(R.id.toastlayout));
        TextView toastText = (TextView) layout.findViewById(R.id.txtDisplayToast);
        ImageView imgIcon = (ImageView) layout.findViewById(R.id.imgToastSucc);
        LinearLayout parentLayout = (LinearLayout) layout.findViewById(R.id.toastlayout);
        imgIcon.setImageResource(icon);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            parentLayout.setBackgroundDrawable(getResources().getDrawable(backgroundLayout));
        } else {
            parentLayout.setBackground(getResources().getDrawable(backgroundLayout));
        }


        toastText.setText(msj);
        Toast toast = new Toast(AsignarEmpleado.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }

    public void AvisoGuardarInfo() {
        new AlertDialog.Builder(AsignarEmpleado.this)
                .setTitle("Advertencia")
                .setMessage("¿Esta seguro que desea guardar la información?")
                .setIcon(R.drawable.icn_alert)
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                         //   @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                GuardarInformacion();
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    //@TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                     //   showToast("Mike is not awesome for you. :(");
                        dialog.cancel();
                    }
                }).show();
    }

    private void sendSMS(String phoneNumber, String message) {
        if (phoneNumber.equals("-")){
        }
        else {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
        Log.i("Sms enviado >",  phoneNumber);
        }
    }

    public void MenuOpcionesItem() {

        final CharSequence[] items = { "01.- Eliminar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AsignarEmpleado.this);
        builder.setTitle("Seleccione una opción");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                if (item == 0){
                    Eliminar(nItemSeletec);
                }

                dialog.dismiss();

            }
        }).show();
    }

    public  void Eliminar (int index ){
        listaEmpledosAsignados.remove(index);
        adapterLV.notifyDataSetChanged();
    }

}
