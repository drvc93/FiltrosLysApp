package com.filtroslys.filtroslysapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import DataBase.HistorialInspGenDB;
import DataBase.ProdMantDataBase;
import Model.InspeccionGenCabecera;
import Tasks.GetHistorialInspGenTask;
import Util.Constans;
import Util.HistorialInspGenAdapater;

public class InspeccionGenListLinea extends AppCompatActivity {

    String tipoSincro = "";
    Spinner spTipoInsp,spEstado;
    String FinicioGlobal = "", FFinGlobal = "";
    EditText txtFInicio, txtFFin;
    ListView LVHinspeGen;
    HistorialInspGenAdapater adapater;
    LinearLayout coord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeccion_gen_list_linea);
        coord = (LinearLayout) findViewById(R.id.coordLayoutGen);
        spTipoInsp = (Spinner) findViewById(R.id.spHGTipoInsp);
        txtFInicio = (EditText) findViewById(R.id.txtHGFInici);
        txtFFin = (EditText) findViewById(R.id.txtHGFFin);
        LVHinspeGen = (ListView) findViewById(R.id.LVHInspGen);
        tipoSincro = getIntent().getExtras().getString("tipoSincro");
        spEstado  = (Spinner) findViewById(R.id.spEstadoINSPGen);

        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH)+1;
        String mes = String.format("%02d", month);
        int year = c.get(Calendar.YEAR);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtFInicio.setText(  "01/01/"+String.valueOf(year));
        txtFFin.setText( sdf.format(new Date()));

        ActionBar actionBar = getSupportActionBar();

        if (tipoSincro.equals("Online")) {
            setTitle("Busqueda inspección general en linea");
        } else {
            setTitle("Busqueda inspección general ");
        }

        LoadSpinerEstado();
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.RED);
            actionBar.hide();

        } else {
            actionBar.hide();
        }

        if (GetDisplaySize() < 4.5) {

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


        LVHinspeGen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HistorialInspGenDB h = adapater.GetItem(i);
                Log.i("Estado Insp", h.getEstado());

                ProdMantDataBase db = new ProdMantDataBase(InspeccionGenListLinea.this);
                InspeccionGenCabecera cab = new InspeccionGenCabecera();
                if (tipoSincro.equals("Offline")) {
                    cab = db.GetInspGenCabeceraPorCorrelativo(h.getNumero());
                    Log.i("Estado Insp desde bd", cab.getEstado());
                }


                if (h.getEstado().equals("Enviado") || h.getEstado().equals("PENDIENTE")) {
                    Intent intent = new Intent(InspeccionGenListLinea.this, InspeccionGen.class);
                    intent.putExtra("tipoMant", "Visor");
                    intent.putExtra("tipoSincro", tipoSincro);
                    intent.putExtra("xcorrelativo", h.getNumero());
                   // intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);

                } else if (h.getEstado().equals("Ingresado")) {
                    Intent intent = new Intent(InspeccionGenListLinea.this, InspeccionGen.class);
                    if (tipoSincro.equals("Offline") && cab.getEstado().equals("E") ){
                        intent.putExtra("tipoMant", "Visor");
                    }
                    else {
                        intent.putExtra("tipoMant", "Editar");
                    }
                    intent.putExtra("tipoSincro", tipoSincro);
                    intent.putExtra("xcorrelativo", h.getNumero());
                   // intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);

                }
            }
        });
        CreateSnackabar();
        LoadSpiner();
        if (tipoSincro.equals("Offline")) {

            SelectedFiltersOffline();
        }
        else {
            SelectedFiltersOnline();
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

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

    public  void LoadSpinerEstado(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("TODOS");
        if (tipoSincro.equals("Online")) {
            list.add("PENDIENTE");
            list.add("APROBADO");
            list.add("ANULADO");
        }
        else {
            list.add("INGRESADO");
            list.add("ENVIADO");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(InspeccionGenListLinea.this, android.R.layout.simple_spinner_dropdown_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstado.setPrompt("Seleccione estado");
        spEstado.setAdapter(arrayAdapter);

    }

    public void CreateSnackabar() {

        Snackbar snakbar = Snackbar
                .make(coord, "", Snackbar.LENGTH_INDEFINITE)

                .setAction("Buscar", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (tipoSincro.equals("Online")) {

                            SelectedFiltersOnline();
                        } else {
                            SelectedFiltersOffline();
                        }
                    }
                });
        snakbar.setActionTextColor(Color.WHITE);
        snakbar.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
          SelectedFiltersOffline();
        }
    }

    public void SelectedFiltersOffline() {
        ProdMantDataBase db = new ProdMantDataBase(InspeccionGenListLinea.this);
        ArrayList<HistorialInspGenDB> lisdata = new ArrayList<HistorialInspGenDB>();
        String Fini , Ffin ;
       // Ffin =  FechasFormatOff( "FF",txtFFin.getText().toString());
       // Fini =  FechasFormatOff("FI",txtFInicio.getText().toString());
        int spinerTipIndex = spTipoInsp.getSelectedItemPosition();
        String fechaInicio = txtFInicio.getText().toString(); //Fini.substring(6,10) +"-"+Fini.substring(3,5)+"-"+Fini.substring(0,2); // FinicioGlobal;
        String fechaFin =  FechasFormatOff( "FF",txtFFin.getText().toString()); //Ffin.substring(6,10) +"-"+Ffin.substring(3,5)+"-"+Ffin.substring(0,2);
        Log.i("fechas params" ,fechaInicio +"|"  + fechaFin );

        String accion = "",sEstado= "";
        if (spEstado.getSelectedItem().toString().equals("TODOS")){
            sEstado = "%";
        }
        else {
            sEstado = spEstado.getSelectedItem().toString().substring(0,1);
        }

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

        lisdata = db.GetHistorialInsGenpList(accion, GetValueSpiner(), fechaInicio, fechaFin,sEstado);
        Log.i("parametros filtros" , accion + "/"+GetValueSpiner() + "/" + fechaInicio + "/"+ fechaFin + "/" + sEstado );
        if (lisdata != null && lisdata.size() > 0) {

            /*for (int i = 0  ; i <  lisdata.size();i++){
                HistorialInspGenDB  h   = lisdata.get(i);
                Log.i("Fecha data off" , lisdata.get(i).getFecha());
            }*/

            adapater = new HistorialInspGenAdapater(InspeccionGenListLinea.this, R.layout.item_list_busq_insp_gen, lisdata);
            adapater.flagFormat= "S";
            LVHinspeGen.setAdapter(adapater);
        } else {
            LVHinspeGen.setAdapter(null);
            CreateCustomToast("No se encontro resultados ", Constans.icon_warning, Constans.layot_warning);

        }

    }
    public void SelectedFiltersOnline() {

        AsyncTask<String, String, ArrayList<HistorialInspGenDB>> asyncTaskOnline;
        GetHistorialInspGenTask getHistorialInspGenTask = new GetHistorialInspGenTask();
        ArrayList<HistorialInspGenDB> lisdata = new ArrayList<HistorialInspGenDB>();
        int spinerTipIndex = spTipoInsp.getSelectedItemPosition();
        String fechaInicio = txtFInicio.getText().toString();
        String fechaFin = txtFFin.getText().toString();
        String accion = "", sEstado="";
        if (spEstado.getSelectedItem().toString().equals("TODOS")){
            sEstado = "%";
        }
        else {
            sEstado = spEstado.getSelectedItem().toString().substring(0,2);
        }

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
            asyncTaskOnline = getHistorialInspGenTask.execute(accion, GetValueSpiner(), fechaInicio, fechaFin,sEstado);
            lisdata = (ArrayList<HistorialInspGenDB>) asyncTaskOnline.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (lisdata != null) {
            adapater = new HistorialInspGenAdapater(InspeccionGenListLinea.this, R.layout.item_list_busq_insp_gen, lisdata);
            LVHinspeGen.setAdapter(adapater);
        } else {
            LVHinspeGen.setAdapter(null);
            CreateCustomToast("No se encontro resultados ", Constans.icon_warning, Constans.layot_warning);

        }

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
                        txtFecha.setText(dia + "/" + mes + "/" + String.valueOf(year));
                        if (etiqueta.equals("Inicio")) {
                            FinicioGlobal = String.valueOf(year) + "-" + mes + "-" + dia;
                            Log.i("Fecha global inicio => ", FinicioGlobal);
                        } else {

                            FFinGlobal = String.valueOf(year) + "-" + mes + "-" + dia;
                            Log.i("Fecha global FIN => ", FFinGlobal);
                        }
                        dialog.cancel();

                    }

                }).show();
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
        Toast toast = new Toast(InspeccionGenListLinea.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }

    public  String FechasFormatOff ( String sTipoFecha , String dateString ){
        String result = "";
        int  aumentarDias = 0 ;
        if (sTipoFecha.equals("FI")){
            aumentarDias = -1;
        }
        else {
            aumentarDias = 1 ;
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(convertedDate);
            cal.add(Calendar.DAY_OF_YEAR,aumentarDias);
            Date newDate = cal.getTime();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            result = formatter.format(newDate);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i("add date convert " , dateString +"/"+result );

        return  result  ;

    }
}

