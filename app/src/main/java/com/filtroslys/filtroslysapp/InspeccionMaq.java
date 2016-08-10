package com.filtroslys.filtroslysapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import DataBase.InspeccionDB;
import DataBase.PeriodoInspeccionDB;
import DataBase.ProdMantDataBase;
import Model.InspeccionMaqDetalle;
import Util.Constans;
import Util.DetalleMaqAdapter;

public class InspeccionMaq extends AppCompatActivity {

    TextView lblInspector ,lblFechaInicio;
    SharedPreferences preferences;
    String comentDialog = "";
    EditText txtComentario ;
    String codUser,codMaquina,NomMaquina, FamMaquina;
    Spinner spPeriodo, spCondMaq ;
    ListView LVdetalleM ;
    ArrayList<PeriodoInspeccionDB> listPeriodos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeccion_maq);
        ActionBar actionBar = getSupportActionBar();

        if (ChangeOrientationScreen()<6) {

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
          //  getWindow().setStatusBarColor(Color.BLACK);
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(InspeccionMaq.this);
        codUser = preferences.getString("UserCod",null);
        codMaquina = preferences.getString("CodMaquina",null);
        NomMaquina = preferences.getString("NomMaquina",null);
        FamMaquina = preferences.getString("FamMaquina",null);



       // lblMaquina = (TextView)findViewById(R.id.lblMaquina);
        lblInspector  =  (TextView) findViewById(R.id.lblnspector);
        spCondMaq = (Spinner) findViewById(R.id.spCondMaquina);
        LVdetalleM = (ListView) findViewById(R.id.LVDetInspM);
        spPeriodo = (Spinner)findViewById(R.id.spPeriodo);
         txtComentario = (EditText)findViewById(R.id.txtCometario);
        lblFechaInicio = (TextView)findViewById(R.id.lblFechaInicio);

        CargarCabecera();


         txtComentario.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ShowCometarioCabDialog();
             }
         });

        spPeriodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0){

                    NumberFormat format =  new DecimalFormat("00");
                    String strNumerFormat = format.format(i);
                    Log.d("Number perdiodo => ", strNumerFormat );
                   // Toast.makeText(InspeccionMaq.this, strNumerFormat, Toast.LENGTH_SHORT).show();
                    InsertRowsListView(strNumerFormat);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        //LoadListViewDetall();

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
        listPeriodos = db.PeriodosInspeccionList();
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
        dialog.setTitle("Comentarios");


        final EditText txtDComentario = (EditText)dialog.findViewById(R.id.txtDialogCom);
        final String coment = txtDComentario.getText().toString();

        comentDialog = coment;
        Log.i("Comentario =>> " , coment);
        Button btnSsalir = (Button) dialog.findViewById(R.id.btnDiaglogSalir);
        Button btnAceptar = (Button) dialog.findViewById(R.id.btnDialogOK);
        dialog.show();
        //dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.icn_alert);


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtComentario.setText(txtDComentario.getText().toString());
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

    public  void SetTextComent (){

        txtComentario.setText(comentDialog);
    }

    public void InsertRowsListView (String codPeriodo){

        ProdMantDataBase db = new ProdMantDataBase(InspeccionMaq.this);
        ArrayList<InspeccionDB> listInsp =  db.GetInspecciones(FamMaquina,codPeriodo);
        ArrayList<InspeccionMaqDetalle> listInspMaqDet = new ArrayList<InspeccionMaqDetalle>();
        if (listInsp.size()>0){
            for (int i = 0; i < listInsp.size(); i++) {
                InspeccionDB inp = listInsp.get(i);
                InspeccionMaqDetalle inpDet = new InspeccionMaqDetalle();
                inpDet.setDescripcionInspecion(inp.getC_descripcion());
                inpDet.setTipo_inspecicon(inp.getC_tipoinspeccion());
                inpDet.setPorcentMin(inp.getN_porcentajeminimo());
                inpDet.setPorcentMax(inp.getN_porcentajemaximo());

                listInspMaqDet.add(inpDet);

            }

            DetalleMaqAdapter detalleMaqAdap = new DetalleMaqAdapter(InspeccionMaq.this,R.layout.inspeccion_maquina_det,listInspMaqDet);
            LVdetalleM.setAdapter(detalleMaqAdap);


        }

        else {

            CreateCustomToast("No se encontro inspecciones para este periodo.", Constans.icon_warning,Constans.layot_warning);
        }

    }

    public  double   ChangeOrientationScreen (){
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

    public void   CreateCustomToast (String msj, int icon,int backgroundLayout ){

        LayoutInflater infator = getLayoutInflater();
        View layout =infator.inflate(R.layout.toast_alarm_success, (ViewGroup) findViewById(R.id.toastlayout));
        TextView toastText = (TextView)layout.findViewById(R.id.txtDisplayToast);
        ImageView imgIcon =  (ImageView)layout.findViewById(R.id.imgToastSucc);
        LinearLayout parentLayout = ( LinearLayout)layout.findViewById(R.id.toastlayout);
        imgIcon.setImageResource(icon);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            parentLayout.setBackgroundDrawable( getResources().getDrawable(backgroundLayout) );
        } else {
            parentLayout.setBackground( getResources().getDrawable(backgroundLayout));
        }


        toastText.setText(msj);
        Toast toast = new Toast(InspeccionMaq.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }
}
