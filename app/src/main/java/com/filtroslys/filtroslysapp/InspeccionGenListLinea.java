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
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import DataBase.HistorialInspGenDB;
import Tasks.GetHistorialInspGenTask;
import Util.HistorialInspGenAdapater;

public class InspeccionGenListLinea extends AppCompatActivity {

    String tipoSincro = "";
    Spinner spTipoInsp;
    String FinicioGlobal, FFinGlobal;
    EditText txtFInicio, txtFFin;
    ListView LVHinspeGen;
    HistorialInspGenAdapater adapater;
    CoordinatorLayout coord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeccion_gen_list_linea);
        coord = (CoordinatorLayout) findViewById(R.id.coordLayoutGen);
        spTipoInsp = (Spinner) findViewById(R.id.spHGTipoInsp);
        txtFInicio = (EditText) findViewById(R.id.txtHGFInici);
        txtFFin = (EditText) findViewById(R.id.txtHGFFin);
        LVHinspeGen = (ListView) findViewById(R.id.LVHInspGen);
        tipoSincro = getIntent().getExtras().getString("tipoSincro");

        ActionBar actionBar = getSupportActionBar();

        if (tipoSincro.equals("Online")) {
            setTitle("Busqueda inspección general en linea");
        } else {
            setTitle("Busqueda inspección general ");
        }

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.RED);
            actionBar.hide();

        } else {
            actionBar.hide();
        }

        if (GetDisplaySize() < 6) {

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            actionBar.show();

        }

        txtFInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelecFecha(txtFInicio, "Inicio");
            }
        });
        txtFFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelecFecha(txtFFin, "Fin");
            }
        });

        CreateSnackabar();
        LoadSpiner();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int X = (int) event.getX();
        int Y = (int) event.getY();
        int eventaction = event.getAction();

        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:

                CreateSnackabar();
                break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:
                CreateSnackabar();
                break;
        }
        return true;
    }

    public void LoadSpiner() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("AMBOS");
        list.add("OTROS");
        list.add("MAQUINA");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(InspeccionGenListLinea.this, android.R.layout.simple_spinner_dropdown_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoInsp.setPrompt("Seleccion tipo Inspección");
        spTipoInsp.setAdapter(arrayAdapter);

    }

    public void CreateSnackabar() {

        Snackbar snakbar = Snackbar
                .make(coord, "", Snackbar.LENGTH_INDEFINITE)

                .setAction("Buscar", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Toast.makeText(InspeccionMaqListLinea.this, "click", Toast.LENGTH_SHORT).show();
                        if (tipoSincro.equals("Online")) {

                            SelectedFiltersOnline();
                        } else {
                            // SelectedFiltersOffline();
                        }
                    }
                });
        snakbar.setActionTextColor(Color.WHITE);
        snakbar.show();

    }

    public void SelectedFiltersOnline() {

        AsyncTask<String, String, ArrayList<HistorialInspGenDB>> asyncTaskOnline;
        GetHistorialInspGenTask getHistorialInspGenTask = new GetHistorialInspGenTask();
        ArrayList<HistorialInspGenDB> lisdata = new ArrayList<HistorialInspGenDB>();
        int spinerTipIndex = spTipoInsp.getSelectedItemPosition();
        String fechaInicio = txtFInicio.getText().toString();
        String fechaFin = txtFFin.getText().toString();
        String accion = "";

        if (spinerTipIndex > 0 && fechaFin.equals("") && fechaInicio.equals("")) {
            accion = "1";
        }

        if (spinerTipIndex > 0 && fechaFin.length() > 0 && fechaInicio.length() > 0) {

            accion = "2";
        }

        if (spinerTipIndex == 0 && fechaFin.length() > 0 && fechaInicio.length() > 0) {

            accion = "3";
        }

        if (spinerTipIndex == 0 && fechaFin.equals("") && fechaInicio.equals("")) {
            accion = "4";
        }

        try {
            asyncTaskOnline = getHistorialInspGenTask.execute(accion, GetValueSpiner(), fechaInicio, fechaFin);
            lisdata = (ArrayList<HistorialInspGenDB>) asyncTaskOnline.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        adapater = new HistorialInspGenAdapater(InspeccionGenListLinea.this, R.layout.item_list_busq_insp_gen, lisdata);
        LVHinspeGen.setAdapter(adapater);

    }

    public String GetValueSpiner() {
        String result = "";
        if (spTipoInsp.getSelectedItemPosition() == 1) {
            result = "OT";
        } else if (spTipoInsp.getSelectedItemPosition() == 2) {
            result = "MQ";
        } else {
            result = "TODOS";
        }
        return result;
    }





    public double GetDisplaySize() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int dens = dm.densityDpi;
        double wi = (double) width / (double) dens;
        double hi = (double) height / (double) dens;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);
        double screenInches = Math.sqrt(x + y);
        Log.i("Pulgadas => ", String.valueOf(screenInches));
        return screenInches;
    }

    public void SelecFecha(final EditText txtFecha, final String etiqueta) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.datapicker_layout, null, false);
        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);
        new AlertDialog.Builder(InspeccionGenListLinea.this).setView(view)
                .setTitle("Seleccionar Fecha")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    // @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {

                        int month = myDatePicker.getMonth() + 1;
                        int day = myDatePicker.getDayOfMonth();
                        int year = myDatePicker.getYear();
                        String mes = String.format("%02d", month);

                        String dia = String.format("%02d", day);
                        txtFecha.setText(mes + "/" + dia + "/" + String.valueOf(year));
                        if (etiqueta.equals("Inicio")) {
                            FinicioGlobal = String.valueOf(year) + "-" + mes + "-" + dia;
                        } else {

                            FFinGlobal = String.valueOf(year) + "-" + mes + "-" + dia;
                        }
                        dialog.cancel();

                    }

                }).show();
    }

}

