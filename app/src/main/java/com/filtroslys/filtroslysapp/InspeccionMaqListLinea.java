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

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import DataBase.CentroCostoDB;
import DataBase.HistorialInspMaqDB;
import DataBase.MaquinaDB;
import DataBase.ProdMantDataBase;
import Model.InspeccionMaqDetalle;
import Tasks.GetHistorialInspMaqTask;
import Util.Constans;
import Util.HistorialInspMaqAdapater;


public class InspeccionMaqListLinea extends AppCompatActivity {

    Spinner spMaquina,spCentroCosto;
    EditText txtFechaIni,txtFechaFin;
    String FinicioGlobal, FFinGlobal;
    ArrayList<MaquinaDB> lisMaquina ;
    ArrayList<CentroCostoDB> listCentroCosto;
    CoordinatorLayout coord ;
    ListView LVhistiral;
    HistorialInspMaqAdapater adapater;
    String tipoSincro = "";
    boolean isTouch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeccion_maq_list_linea);
        tipoSincro = getIntent().getExtras().getString("TipoSync");
        Log.i("Tipo Mant >", tipoSincro);
        spMaquina =(Spinner) findViewById(R.id.spMaquina);
        spCentroCosto = (Spinner) findViewById(R.id.spCentroC) ;
        coord = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);
        txtFechaFin = (EditText) findViewById(R.id.txtFFin) ;
        txtFechaIni = (EditText) findViewById(R.id.txtFinicio);
        txtFechaIni.setHint("F.Inicio");
        txtFechaFin.setHint("F.Fin");
        LVhistiral  = (ListView)findViewById(R.id.LVHistorialInsp) ;
        ActionBar  actionBar = getSupportActionBar();

        txtFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelecFecha(txtFechaFin, "Fin");
            }
        });

        txtFechaIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelecFecha(txtFechaIni, "Inicio");
            }
        });


        LVhistiral.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HistorialInspMaqDB h = adapater.getItem(i);
                if (h.getEstado().equals("I")) {

                    Intent intent = new Intent(InspeccionMaqListLinea.this, InspeccionMaq.class);
                    intent.putExtra("tipoMant", "Editar");
                    intent.putExtra("Xcorrelativo", h.getNumero());
                    intent.putExtra("XcodMaq", h.getCod_maquina());
                    intent.putExtra("tipoSincro", tipoSincro);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                } else if (h.getEstado().equals("E") || h.getEstado().equals("PE")) {
                    Intent intent = new Intent(InspeccionMaqListLinea.this, InspeccionMaq.class);
                    intent.putExtra("tipoMant", "Visor");
                    intent.putExtra("Xcorrelativo", h.getNumero());
                    intent.putExtra("XcodMaq", h.getCod_maquina());
                    intent.putExtra("tipoSincro", tipoSincro);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }
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
        } else {
            actionBar.show();
        }
        LoadSpiners();
        CreateSnackabar();
        if (tipoSincro.equals("Online")) {
            setTitle("Busqueda inspecciones en liena");
        } else {
            setTitle("Busqueda inspecciones");
            LoadListViewOffline("1", "", "", "", "");
        }
        // LoadListView("1","","","","");
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

            dataMaq.add(listMaq.get(i).getC_maquina() + "   -    " + listMaq.get(i).getC_descripcion());

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
                        // Toast.makeText(InspeccionMaqListLinea.this, "click", Toast.LENGTH_SHORT).show();
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

    public void SelectedFiltersOffline() {
        String maq = spMaquina.getSelectedItem().toString();
        maq = maq.substring(0, 7);
        maq = maq.trim();
        String costo = spCentroCosto.getSelectedItem().toString();
        costo = costo.substring(0, 6);
        costo = costo.trim();
        String fechaIni = txtFechaIni.getText().toString();
        String fechaFin = txtFechaFin.getText().toString();

        if (spMaquina.getSelectedItemPosition() == 0 && spCentroCosto.getSelectedItemPosition() == 0 && txtFechaFin.getText().toString().equals("") && txtFechaIni.getText().toString().equals("")) {
            CreateCustomToast("Debe seleccinar un filtro primero", Constans.icon_warning, Constans.layot_warning);

        }

        if (spMaquina.getSelectedItemPosition() != 0 && spCentroCosto.getSelectedItemPosition() == 0 && txtFechaFin.getText().toString().equals("") && txtFechaIni.getText().toString().equals("")) {

            Log.i("cod maquina ", maq);
            LoadListViewOffline("2", maq, "", "", "");
        }
        if (spMaquina.getSelectedItemPosition() != 0 && spCentroCosto.getSelectedItemPosition() != 0 && txtFechaFin.getText().toString().equals("") && txtFechaIni.getText().toString().equals("")) {


            Log.i("cod maquina ", maq);
            Log.i("cod costo ", costo);
            LoadListViewOffline("3", maq, costo, "", "");

        }

        if (spMaquina.getSelectedItemPosition() == 0 && spCentroCosto.getSelectedItemPosition() == 0 && txtFechaFin.getText().toString().length() > 0 && txtFechaIni.getText().toString().length() > 0) {


            Log.i("cod maquina ", maq);
            Log.i("cod costo ", costo);
            LoadListViewOffline("4", maq, costo, FinicioGlobal, FFinGlobal);

        }

        CreateSnackabar();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int X = (int) event.getX();
        int Y = (int) event.getY();
        int eventaction = event.getAction();

        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:

                isTouch = true;
                break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:
                CreateSnackabar();
                break;
        }
        return true;
    }

    public void SelectedFiltersOnline() {
        String maq = spMaquina.getSelectedItem().toString();
        maq = maq.substring(0, 7);
        maq = maq.trim();
        String costo = spCentroCosto.getSelectedItem().toString();
        costo = costo.substring(0, 6);
        costo = costo.trim();
        String fechaIni = txtFechaIni.getText().toString();
        String fechaFin = txtFechaFin.getText().toString();

        if (spMaquina.getSelectedItemPosition() == 0 && spCentroCosto.getSelectedItemPosition() == 0 && txtFechaFin.getText().toString().equals("") && txtFechaIni.getText().toString().equals("")) {
            CreateCustomToast("Debe seleccinar un filtro primero", Constans.icon_warning, Constans.layot_warning);

        }
        if (spMaquina.getSelectedItemPosition() != 0 && spCentroCosto.getSelectedItemPosition() == 0 && txtFechaFin.getText().toString().equals("") && txtFechaIni.getText().toString().equals("")) {

            Log.i("cod maquina ", maq);
            LoadListView("2", maq, "", "", "");

        }
        if (spMaquina.getSelectedItemPosition() != 0 && spCentroCosto.getSelectedItemPosition() != 0 && txtFechaFin.getText().toString().equals("") && txtFechaIni.getText().toString().equals("")) {


            Log.i("cod maquina ", maq);
            Log.i("cod costo ", costo);
            LoadListView("3", maq, costo, "", "");

        }

        if (spMaquina.getSelectedItemPosition() == 0 && spCentroCosto.getSelectedItemPosition() == 0 && txtFechaFin.getText().toString().length() > 0 && txtFechaIni.getText().toString().length() > 0) {


            Log.i("cod maquina ", maq);
            Log.i("cod costo ", costo);
            Log.i("fecha ini ", fechaIni);
            Log.i("fecha fin ", fechaFin);
            LoadListView("4", maq, costo, fechaIni, fechaFin);

        }

        if (spMaquina.getSelectedItemPosition() != 0 && spCentroCosto.getSelectedItemPosition() != 0 && txtFechaFin.getText().toString().length() > 0 && txtFechaIni.getText().toString().length() > 0) {


            Log.i("cod maquina ", maq);
            Log.i("cod costo ", costo);
            Log.i("fecha ini ", fechaIni);
            Log.i("fecha fin ", fechaFin);
            LoadListView("5", maq, costo, fechaIni, fechaFin);

        }


        CreateSnackabar();


    }


    public void LoadListViewOffline(String accion, String maq, String cenctroC, String FIni, String FFin) {
        ProdMantDataBase db = new ProdMantDataBase(InspeccionMaqListLinea.this);

        ArrayList<HistorialInspMaqDB> listHisto = db.GetHistorialInspList(accion, maq, cenctroC, FIni, FFin);
        if (listHisto != null) {
            adapater = new HistorialInspMaqAdapater(InspeccionMaqListLinea.this, R.layout.item_list_busq_insp_m, listHisto);
            adapater.setDropDownViewResource(R.layout.item_list_busq_insp_m);
            LVhistiral.setAdapter(adapater);
        } else {
            CreateCustomToast("No se encontro resultados ", Constans.icon_error, Constans.layout_error);
        }


    }

    public  void  LoadListView (String accion , String maq , String cenctroC , String FIni , String FFin){
        ArrayList<HistorialInspMaqDB> listHisto ;
        AsyncTask<String,String,ArrayList<HistorialInspMaqDB>> asyncHistorial ;
        GetHistorialInspMaqTask getHistorialTask = new GetHistorialInspMaqTask();

        try {
            asyncHistorial = getHistorialTask.execute(accion,maq,cenctroC,FIni,FFin);
            listHisto=(ArrayList<HistorialInspMaqDB>)asyncHistorial.get();
            if (listHisto != null) {
                adapater = new HistorialInspMaqAdapater(InspeccionMaqListLinea.this, R.layout.item_list_busq_insp_m, listHisto);
            adapater.setDropDownViewResource(R.layout.item_list_busq_insp_m);
                LVhistiral.setAdapter(adapater);
            } else {
                CreateCustomToast("No se encontro resultados ", Constans.icon_error, Constans.layout_error);
            }
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

    public void SelecFecha(final EditText txtFecha, final String etiqueta) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.datapicker_layout, null, false);
        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);
        new AlertDialog.Builder(InspeccionMaqListLinea.this).setView(view)
                .setTitle("Seleccionar Fecha")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   // @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {

                        int month = myDatePicker.getMonth() + 1;
                        int day = myDatePicker.getDayOfMonth();
                        int year = myDatePicker.getYear();
                        String mes = String.format("%02d", month);

                        String dia = String.format("%02d",day);
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
        Toast toast = new Toast(InspeccionMaqListLinea.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }
}
