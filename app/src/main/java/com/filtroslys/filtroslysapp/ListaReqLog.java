package com.filtroslys.filtroslysapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Region;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.List;
import java.util.concurrent.ExecutionException;

import DataBase.CentroCostoDB;
import DataBase.MaquinaDB;
import DataBase.ProdMantDataBase;
import Model.RequisicionLogCab;
import Model.SolicitudServicio;
import Model.UsuarioCompania;
import Tasks.GetListReqLogTask;
import Tasks.ValidarEmpleadoMantTask;
import Tasks.getCompaniasXUsuarioTask;
import Util.Constans;
import Util.ReqLogisticaAdapter;
import Util.SolicitudServAdapter;

public class ListaReqLog extends AppCompatActivity {

    Spinner spEstado , spCompania , spCentroCosto ;
    EditText txtFechaIni , txtFechaFin ;
    SharedPreferences preferences;
    String codUser ;
    TextView  lblCantReg ;
    ListView lvReqLog ;
    ReqLogisticaAdapter adapter;
    String sCompaniaSel , sFechaIni , sFechaFin,sEstado,sCentroCosto ;
    Button btnBuscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_req_log);
        ActionBar actionBar = getSupportActionBar();


        spCentroCosto = (Spinner)findViewById(R.id.spCentroCostoRL);
        spCompania = (Spinner)findViewById(R.id.spCompaniaRL);
        spEstado = (Spinner)findViewById(R.id.spEstadoRL);
        txtFechaFin = (EditText)findViewById(R.id.txtFechaFinRL);
        txtFechaIni = (EditText)findViewById(R.id.txtFechaIniRL);
        lblCantReg = (TextView)findViewById(R.id.lblCantidadRegRL)  ;
        lvReqLog = (ListView) findViewById(R.id.LVReqLog);
        btnBuscar = (Button) findViewById(R.id.btnBuscarRL);
        preferences = PreferenceManager.getDefaultSharedPreferences(ListaReqLog.this);
        codUser = preferences.getString("UserCod", null);
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH)+1;
        String mes = String.format("%02d", month);
        int year = c.get(Calendar.YEAR);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.RED);
            actionBar.hide();

        } else {
            actionBar.hide();
        }

        setTitle("Requisiciones Logistica");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtFechaIni.setText(  "01/"+mes+"/"+String.valueOf(year));
        txtFechaFin.setText( sdf.format(new Date()));
        LoadSpinnerEstado();
        LoadSpinerCCosto();
        LoadSpinerCompania();

        LoadDataListView();

        txtFechaIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelecFecha(txtFechaIni);
            }
        });

        txtFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelecFecha(txtFechaFin);
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadDataListView();
            }
        });

        lvReqLog.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                MenuLista(i);
                return false;
            }
        });
    }

    public  void  LoadDataListView () {

        if (spCompania!= null && spCompania.getCount()>0){
            sCompaniaSel= spCompania.getSelectedItem().toString();
            sCompaniaSel = sCompaniaSel.substring(sCompaniaSel.indexOf("|")+1).trim();
        }
        else {
            CreateCustomToast("Su usuario no tiene asignado ninguna compania.",Constans.icon_error,Constans.layout_error);
            return;
        }
        /**  CENTRO  DE COSTO **/
        sCentroCosto = spCentroCosto.getSelectedItem().toString();
        if (sCentroCosto.equals("TODOS")){
            sCentroCosto = "%";
        }
        else {
            sCentroCosto = sCentroCosto.substring(0, sCentroCosto.indexOf("-") - 1).trim();
        }
        /**ESTADO**/
        sEstado  = spEstado.getSelectedItem().toString().substring(0,2);
        if (sEstado.equals("TO")){
            sEstado = "%";
        }
        else if (sEstado.equals("PA")){
            sEstado = "AP" ;
        }

        sFechaIni = txtFechaIni.getText().toString();
        sFechaFin = txtFechaFin.getText().toString();



        ArrayList<RequisicionLogCab> listareq  = null ;
        AsyncTask<String,String,ArrayList<RequisicionLogCab>> asyncTask ;
        GetListReqLogTask  getListReqLogTask = new GetListReqLogTask();


        try {
            asyncTask =  getListReqLogTask.execute(sCompaniaSel,sCentroCosto,sEstado,sFechaIni,sFechaFin);
            listareq  = ( ArrayList<RequisicionLogCab> )  asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listareq!=null && listareq.size()>0){



        adapter = new ReqLogisticaAdapter(ListaReqLog.this, R.layout.item_req_logisitica, listareq);
        lvReqLog.setAdapter(adapter);
        lblCantReg.setText("LISTA REQUISICIONES      |    " + String.valueOf(lvReqLog.getCount())+ " REGISTROS.");
        }
        else {
            CreateCustomToast("No se encontro  resultados.", Constans.icon_warning,Constans.layot_warning);
        }

    }

    public  void LoadSpinnerEstado () {
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("TODOS");
        spinnerArray.add("PREPARACION");
        spinnerArray.add("APROBADA");
        spinnerArray.add("ANULADA");
        spinnerArray.add("COMPLETADA");
        spinnerArray.add("RECHAZADA");
        spinnerArray.add("CERRADA");
        spinnerArray.add("PARC.ATENDIDA");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spEstado.setAdapter(adapter);
        spEstado.setSelection(1);

    }

    public  void  LoadSpinerCCosto ()  {
        ProdMantDataBase db = new ProdMantDataBase(ListaReqLog.this);
        ArrayList<CentroCostoDB> listCosto =  db.GetCemtroCostos();
        ArrayList<String> dataCCosto = new ArrayList<String>();
        dataCCosto.add("TODOS");

        for (int i = 0; i <listCosto.size() ; i++) {

            dataCCosto.add(listCosto.get(i).getC_centrocosto() + "   -    "+  listCosto.get(i).getC_descripcion());

        }
        ArrayAdapter<String> adapterCosto =  new ArrayAdapter<String>(ListaReqLog.this,android.R.layout.simple_spinner_dropdown_item,dataCCosto);
        adapterCosto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCentroCosto.setAdapter(adapterCosto);


    }

    public  void LoadSpinerCompania () {

        getCompaniasXUsuarioTask getCompaniasXUsuarioTask = new getCompaniasXUsuarioTask();
        AsyncTask<String,String,ArrayList<UsuarioCompania>> asyncTask;
        ArrayList<UsuarioCompania> listaCompanias = null ;
        ArrayList<String> dataSpinerComp = new ArrayList<String>() ;

        try {
            asyncTask = getCompaniasXUsuarioTask.execute(codUser);
            listaCompanias = ( ArrayList<UsuarioCompania>) asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listaCompanias!= null && listaCompanias.size()>0){

            for (int i =  0  ; i < listaCompanias.size();i++){
                UsuarioCompania u = listaCompanias.get(i);
                dataSpinerComp.add(u.getC_nombres() + " | " +  u.getC_compania());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, dataSpinerComp);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCompania.setAdapter(adapter);

        }
        else {
            CreateCustomToast("El usuario : "+codUser+" no tiene compania asignada. ", Constans.icon_warning, Constans.layot_warning);
        }

    }


    public void SelecFecha(final EditText txtFecha) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.datapicker_layout, null, false);
        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);
        new AlertDialog.Builder(ListaReqLog.this).setView(view)
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
        Toast toast = new Toast(ListaReqLog.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void MenuLista(final int Opcicion) {

        final int SelectedItem = Opcicion;
        final CharSequence[] items = { "01.- Aprobar Requisicion","02.- Rechazar Requsicion" , "03.- Ver Detalle"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ListaReqLog.this);
        builder.setTitle("Seleccione una opción  | NºReq.: "+adapter.GetItem(SelectedItem).getC_numeroreq());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                if (item == 2) {
                }
                RequisicionLogCab s = adapter.GetItem(SelectedItem);
                Intent intent  = new Intent(ListaReqLog.this  , DetalleReqLog.class);
                intent.putExtra("NroReq", String.valueOf(s.getC_numeroreq()));
                intent.putExtra("CCosto", s.getC_ccostonomb());
                intent.putExtra("Estado", s.getC_estadonomb());
                intent.putExtra("Compania", s.getC_compania());
                intent.putExtra("FCreacion", s.getC_fechacreacion());
                intent.putExtra("UCreacion", s.getC_usuariocreacion());
                startActivity(intent);


                dialog.dismiss();

            }
        }).show();
    }
}
