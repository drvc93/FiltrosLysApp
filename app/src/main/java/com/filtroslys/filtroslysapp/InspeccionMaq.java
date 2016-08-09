package com.filtroslys.filtroslysapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import DataBase.PeriodoInspeccionDB;
import DataBase.ProdMantDataBase;

public class InspeccionMaq extends AppCompatActivity {

    TextView lblInspector ,lblFechaInicio;
    SharedPreferences preferences;
    EditText txtComentario ;
    String codUser,codMaquina,NomMaquina;
    Spinner spPeriodo, spCondMaq ;
    ListView LVdetalleM ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeccion_maq);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
          //  getWindow().setStatusBarColor(Color.BLACK);
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(InspeccionMaq.this);
        codUser = preferences.getString("UserCod",null);
        codMaquina = preferences.getString("CodMaquina",null);
        NomMaquina = preferences.getString("NomMaquina",null);


       // lblMaquina = (TextView)findViewById(R.id.lblMaquina);
        lblInspector  =  (TextView) findViewById(R.id.lblnspector);
        spCondMaq = (Spinner) findViewById(R.id.spCondMaquina);
        LVdetalleM = (ListView) findViewById(R.id.LVDetInspM);
        spPeriodo = (Spinner)findViewById(R.id.spPeriodo);
         txtComentario = (EditText)findViewById(R.id.txtCometario);
        lblFechaInicio = (TextView)findViewById(R.id.lblFechaInicio);

        CargarCabecera();


        txtComentario.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                ShowCometarioCabDialog();
            }
        });


    }

    @Override
    public void onBackPressed() {
        AlerExit();
    }

    public  void   AlerExit (){
        new AlertDialog.Builder(InspeccionMaq.this)
                .setTitle("Advertencia")
                .setMessage("¿Esta seguro que desea salir de esta ventana?")
                .setIcon(R.drawable.icn_alert)
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                finish();

                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();

    }

    public  void CargarCabecera (){
        setTitle(codMaquina + " - " + NomMaquina);
        lblInspector.setText(codUser);
        LoadSpinerCondicionMaquina();
        LoadSpinnerPeriodo();
        LoadListViewDetall();

        Calendar  cal = Calendar.getInstance();
        SimpleDateFormat df =  new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        lblFechaInicio.setText(df.format(cal.getTime()));
    }

    public void LoadSpinerCondicionMaquina(){
        ArrayList<String> lisCondMaq = new ArrayList<String>();
        lisCondMaq.add("-- Seleccione --");
        lisCondMaq.add("ABIERTA");
        lisCondMaq.add("CERRADA");
        ArrayAdapter<String> adapterCondM = new ArrayAdapter<String>(InspeccionMaq.this,android.R.layout.simple_spinner_dropdown_item,lisCondMaq);
        adapterCondM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCondMaq.setAdapter(adapterCondM);

    }

    public  void  LoadSpinnerPeriodo (){

        ProdMantDataBase db = new ProdMantDataBase(InspeccionMaq.this);
        ArrayList<PeriodoInspeccionDB> listPeriodos = db.PeriodosInspeccionList();
        ArrayList<String> listString = new ArrayList<String>();
        listString.add("-- SELECCIONE --");
        for (int i = 0 ; i<listPeriodos.size();i++){
            listString.add(listPeriodos.get(i).getC_descripcion());
        }

        ArrayAdapter<String> adapterPeriodos = new ArrayAdapter<String>(InspeccionMaq.this,android.R.layout.simple_spinner_dropdown_item,listString);
        adapterPeriodos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPeriodo.setAdapter(adapterPeriodos);

    }
    public  void  LoadListViewDetall (){

        ArrayList<String> listTest = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {

            listTest.add("Item Nro : " + String.valueOf(i));


        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(InspeccionMaq.this,android.R.layout.simple_expandable_list_item_1,listTest);
        LVdetalleM.setAdapter(adapter);

    }


    public  void  ShowCometarioCabDialog (){

        final Dialog dialog = new Dialog(InspeccionMaq.this);
        dialog.setContentView(R.layout.dialog_coment_layout);
        setTitle("Comentarios");
        EditText txtComentario = (EditText)dialog.findViewById(R.id.txtDialogCom);
        Button btnSsalir = (Button) dialog.findViewById(R.id.btnDiaglogSalir);
        Button btnAceptar = (Button) dialog.findViewById(R.id.btnDialogOK);
        dialog.show();


        btnSsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }
}
