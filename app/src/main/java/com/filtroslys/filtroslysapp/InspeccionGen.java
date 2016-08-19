package com.filtroslys.filtroslysapp;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import DataBase.CentroCostoDB;
import DataBase.MaquinaDB;
import DataBase.ProdMantDataBase;

public class InspeccionGen extends AppCompatActivity {

    Spinner spMaqCC, spTipoInsp;
    TextView lblSPCCMaq, lblProblemaDetec, lblInspector;
    EditText txtFechaInsp, txtArea, txtProblemadetect;
    ActionBar actionBar;
    int INSP_MAQUINA = 2;
    int INSP_OTROS = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inpeccion_gen);
        setTitle("Inspección General");
        spTipoInsp = (Spinner) findViewById(R.id.spTipoIns);
        spMaqCC = (Spinner) findViewById(R.id.spMaqCC);
        lblSPCCMaq = (TextView) findViewById(R.id.lblSPMAQCC);
        txtProblemadetect = (EditText) findViewById(R.id.txtProbDet);
        txtFechaInsp = (EditText) findViewById(R.id.txtFechaInsG);
        txtArea = (EditText) findViewById(R.id.txtAreaG);
        lblInspector = (TextView) findViewById(R.id.lblInspectorG);
        spMaqCC.setEnabled(false);
        txtProblemadetect.setEnabled(false);

        txtFechaInsp.setText(FechaActual());

        actionBar = getSupportActionBar();
        if (GetDisplaySize() < 6) {

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            actionBar.hide();
        } else {
            actionBar.show();
        }
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.RED);
            actionBar.hide();

        }

        LoadSpinerTipoInsp();
        LoadSpinerMaqCC(0);

        txtArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowCometarioCabDialog(txtArea, "Area afectada");
            }
        });

        spTipoInsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LoadSpinerMaqCC(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inspeccion_gen, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Guardar) {

        }
        return true;
    }

    public void LoadSpinerTipoInsp() {

        ArrayList<String> listTipIns = new ArrayList<String>();
        listTipIns.add("-SELECCIONE-");
        listTipIns.add("OTRO");
        listTipIns.add("MAQUINA");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(InspeccionGen.this, android.R.layout.simple_spinner_dropdown_item, listTipIns);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoInsp.setPrompt("Tipo de inspección");
        spTipoInsp.setAdapter(adapter);
    }

    public void LoadSpinerMaqCC(int selecTipoInsp) {

        ProdMantDataBase db = new ProdMantDataBase(InspeccionGen.this);
        ArrayList<CentroCostoDB> liscCcosto;
        ArrayList<MaquinaDB> listMaq;
        ArrayList<String> data;
        String msjPrompt = "";

        if (selecTipoInsp == INSP_OTROS) {
            data = new ArrayList<String>();
            liscCcosto = db.GetCemtroCostos();
            msjPrompt = "SELECCIONE CENTRO DE COSTO";
            lblSPCCMaq.setText("C.C.:");
            for (int i = 0; i < liscCcosto.size(); i++) {
                data.add(liscCcosto.get(i).getC_centrocosto() + "  -   " + liscCcosto.get(i).getC_descripcion());


            }

        } else if (selecTipoInsp == INSP_MAQUINA) {
            lblSPCCMaq.setText("MAQ:");
            msjPrompt = "SELECCIONE MAQUINA";
            data = new ArrayList<String>();
            listMaq = db.GetMaquinasALL();
            for (int i = 0; i < listMaq.size(); i++) {

                data.add(listMaq.get(i).getC_maquina() + "  -  " + listMaq.get(i).getC_descripcion());

            }


        } else {
            data = new ArrayList<String>();
            data.add("-SELECCIONE-");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(InspeccionGen.this, android.R.layout.simple_spinner_dropdown_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMaqCC.setAdapter(adapter);
        spMaqCC.setPrompt(msjPrompt);
        spMaqCC.setEnabled(true);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int X = (int) event.getX();
        int Y = (int) event.getY();
        int eventaction = event.getAction();
        ActionBar actionBar = getSupportActionBar();

        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:


                actionBar.show();
                HideToolBar();
                break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }


    public void HideToolBar() {

        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                actionBar.hide();

            }
        }.start();

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

    public String FechaActual() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String res = df.format(cal.getTime());
        return res;
    }

    public void ShowCometarioCabDialog(final EditText txtvar, String msj) {

        final Dialog dialog = new Dialog(InspeccionGen.this);
        dialog.setContentView(R.layout.dialog_coment_layout);
        dialog.setTitle(msj);


        final EditText txtDComentario = (EditText) dialog.findViewById(R.id.txtDialogCom);
        final String coment = txtDComentario.getText().toString();


        Log.i("Comentario =>> ", coment);
        Button btnSsalir = (Button) dialog.findViewById(R.id.btnDiaglogSalir);
        Button btnAceptar = (Button) dialog.findViewById(R.id.btnDialogOK);
        dialog.show();
        //dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.icn_alert);


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtvar.setText(txtDComentario.getText().toString());
                dialog.dismiss();
            }
        });
        btnSsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }


}
