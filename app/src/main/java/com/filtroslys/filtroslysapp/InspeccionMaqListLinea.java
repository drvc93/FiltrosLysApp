package com.filtroslys.filtroslysapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import DataBase.CentroCostoDB;
import DataBase.HistorialInspMaqDB;
import DataBase.MaquinaDB;
import DataBase.ProdMantDataBase;
import Tasks.GetHistorialInspMaqTask;
import Util.HistorialInspMaqAdapater;


public class InspeccionMaqListLinea extends AppCompatActivity {

    Spinner spMaquina,spCentroCosto;
    EditText txtFechaIni,txtFechaFin;
    ArrayList<MaquinaDB> lisMaquina ;
    ArrayList<CentroCostoDB> listCentroCosto;
    CoordinatorLayout coord ;
    ListView LVhistiral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeccion_maq_list_linea);
        setTitle("Busqueda inspecciones");
        spMaquina =(Spinner) findViewById(R.id.spMaquina);
        spCentroCosto = (Spinner) findViewById(R.id.spCentroC) ;
        coord = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);
        txtFechaFin = (EditText) findViewById(R.id.txtFFin) ;
        txtFechaIni = (EditText) findViewById(R.id.txtFinicio);
        LVhistiral  = (ListView)findViewById(R.id.LVHistorialInsp) ;
        ActionBar  actionBar = getSupportActionBar();

        txtFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelecFecha(txtFechaFin);
            }
        });

        txtFechaIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelecFecha(txtFechaIni);
            }
        });

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
             getWindow().setStatusBarColor(Color.RED);
            actionBar.hide();

        }
        else {
            actionBar.hide();
        }

        if (GetDisplaySize() < 6) {

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        LoadSpiners();
        CreateSnackabar();
        LoadListView("1","","","","");
    }


    public  void LoadSpiners (){

        ProdMantDataBase  db = new ProdMantDataBase(InspeccionMaqListLinea.this);
        ArrayList<CentroCostoDB> listCosto =  db.GetCemtroCostos();
        ArrayList<MaquinaDB> listMaq = db.GetMaquinasALL();
        ArrayList<String> dataMaq = new ArrayList<String>();
        ArrayList<String> dataCCosto = new ArrayList<String>();

        dataCCosto.add("--SELECCIONE--");
        dataMaq.add("--SELECCIONE--");


        for (int i = 0; i <listCosto.size() ; i++) {

            dataCCosto.add(listCosto.get(i).getC_centrocosto() + "   -    "+  listCosto.get(i).getC_descripcion());

        }

        for (int i = 0; i <listMaq.size() ; i++) {

            dataMaq.add(listMaq.get(i).getC_centrocosto()+"   -    "+ listMaq.get(i).getC_descripcion());

        }

        ArrayAdapter<String> adapterCosto =  new ArrayAdapter<String>(InspeccionMaqListLinea.this,android.R.layout.simple_spinner_dropdown_item,dataCCosto);
        adapterCosto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCentroCosto.setAdapter(adapterCosto);
        ArrayAdapter<String> adapaterMaq = new ArrayAdapter<String>(InspeccionMaqListLinea.this,android.R.layout.simple_spinner_dropdown_item,dataMaq);
        adapaterMaq.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMaquina.setAdapter(adapaterMaq);


    }

    public  void  CreateSnackabar (){

        Snackbar snakbar= Snackbar
                .make(coord,"",Snackbar.LENGTH_INDEFINITE)

                .setAction("Buscar", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(InspeccionMaqListLinea.this, "click", Toast.LENGTH_SHORT).show();
                        CreateSnackabar();
                    }
                });
        snakbar.setActionTextColor(Color.WHITE);
        snakbar.show();

    }

    public  void  LoadListView (String accion , String maq , String cenctroC , String FIni , String FFin){
        ArrayList<HistorialInspMaqDB> listHisto ;
        AsyncTask<String,String,ArrayList<HistorialInspMaqDB>> asyncHistorial ;
        GetHistorialInspMaqTask getHistorialTask = new GetHistorialInspMaqTask();

        try {
            asyncHistorial = getHistorialTask.execute(accion,maq,cenctroC,FIni,FFin);
            listHisto=(ArrayList<HistorialInspMaqDB>)asyncHistorial.get();
            HistorialInspMaqAdapater adapater  = new HistorialInspMaqAdapater(InspeccionMaqListLinea.this,R.layout.item_list_busq_insp_m,listHisto);
            adapater.setDropDownViewResource(R.layout.item_list_busq_insp_m);
            LVhistiral.setAdapter(adapater);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    public  double   GetDisplaySize (){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        int dens=dm.densityDpi;
        double wi=(double)width/(double)dens;
        double hi=(double)height/(double)dens;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        double screenInches = Math.sqrt(x+y);
        Log.i("Pulgadas => ", String.valueOf(screenInches));
        return  screenInches;
    }
    public void SelecFecha(final EditText txtFecha) {

    /*
     * Inflate the XML view. activity_main is in res/layout/date_picker.xml
     */
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.datapicker_layout, null, false);

        // the time picker on the alert dialog, this is how to get the value
        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);

        // so that the calendar view won't appear
      // myDatePicker.

        // the alert dialog
        new AlertDialog.Builder(InspeccionMaqListLinea.this).setView(view)
                .setTitle("Seleccionar Fecha")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   // @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {

                    /*
                     * In the docs of the calendar class, January = 0, so we
                     * have to add 1 for getting correct month.
                     * http://goo.gl/9ywsj
                     */
                        int month = myDatePicker.getMonth() + 1;
                        int day = myDatePicker.getDayOfMonth();
                        int year = myDatePicker.getYear();
                        String mes = String.format("%02d", month);

                        String dia = String.format("%02d",day);
                        txtFecha.setText(mes+"-"+dia+"-"+String.valueOf(year) );



                      //  showToast(month + "/" + day + "/" + year);

                        dialog.cancel();

                    }

                }).show();
    }
}
