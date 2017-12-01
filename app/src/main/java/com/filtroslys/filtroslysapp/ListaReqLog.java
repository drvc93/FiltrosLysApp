package com.filtroslys.filtroslysapp;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorTreeAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;

import DataBase.CentroCostoDB;
import DataBase.MaquinaDB;
import DataBase.ProdMantDataBase;
import Model.Rechazos;
import Model.RequisicionLogCab;
import Model.SolicitudServicio;
import Model.UsuarioCompania;
import Tasks.CentroCostoXCompaniaTask;
import Tasks.EjecutarAprobReqLogTask;
import Tasks.EjecutarRechazoReqLogTask;
import Tasks.EnvioCorreoReqTask;
import Tasks.GetCompaniaXUsuarioLogiTask;
import Tasks.GetListRechazosTask;
import Tasks.GetListReqLogTask;
import Tasks.ValidarAprobacionReqLogTask;
import Tasks.ValidarEmpleadoMantTask;
import Tasks.ValidarRechazoReqLogTask;
import Tasks.getCompaniasXUsuarioTask;
import Util.Constans;
import Util.ReqLogisticaAdapter;
import Util.SolicitudServAdapter;

public class ListaReqLog extends AppCompatActivity {

    Spinner spEstado , spCompania , spCentroCosto ;
    EditText txtFechaIni , txtFechaFin  , txtNroReq;
    SharedPreferences preferences;
    String codUser ;
    TextView  lblCantReg ;
    ListView lvReqLog ;
    ReqLogisticaAdapter adapter;
    String sCompaniaSel , sFechaIni , sFechaFin,sEstado,sCentroCosto ;
    Button btnBuscar;
    String sDescripcionRechazo  = "";
    int selectedItem =  0;
    ProgressBar progressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_req_log);
        ActionBar actionBar = getSupportActionBar();

        progressBar = new ProgressBar(ListaReqLog.this, null, android.R.attr.progressBarStyleSmall);
        spCentroCosto = (Spinner)findViewById(R.id.spCentroCostoRL);
        spCompania = (Spinner)findViewById(R.id.spCompaniaRL);
        spEstado = (Spinner)findViewById(R.id.spEstadoRL);
        txtFechaFin = (EditText)findViewById(R.id.txtFechaFinRL);
        txtFechaIni = (EditText)findViewById(R.id.txtFechaIniRL);
        txtNroReq   =  (EditText)findViewById(R.id.txtNroRequsicionRL);
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
        LoadSpinerCompania();
        LoadSpinnerEstado();
        LoadSpinerCCosto();


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
                FormatearNroReq();
                LoadDataListView();
            }
        });

        lvReqLog.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                MenuLista(i);
                selectedItem =  i ;
                return false;
            }
        });

        spCompania.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LoadSpinerCCosto();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public  void  FormatearNroReq () {
        if (TextUtils.isEmpty(txtNroReq.getText().toString().trim())){
            return;
        }
        else if (txtNroReq.getText().toString().length()>0){
            int lengString =  txtNroReq.getText().toString().length() ;
            int digitosFaltantes =   10- lengString ;
            if (lengString>10){
                CreateCustomToast("Número de requisición invalido", Constans.icon_warning,Constans.layot_warning);
                return;
            }
            else if (lengString<10){
                String textNro =   txtNroReq.getText().toString();
                for (int i = 0 ; i < digitosFaltantes ; i++){
                  textNro = "0"+textNro;

                }
                txtNroReq.setText(textNro);
            }
        }

    }
    public  void  LoadDataListView () {
        String sNroRequisicion   = "";
        sNroRequisicion = txtNroReq.getText().toString().trim();
        if (TextUtils.isEmpty(sNroRequisicion)|| sNroRequisicion.length()<= 0){
            sNroRequisicion = "%";
        }


        if (spCompania!= null && spCompania.getCount()>0){
            sCompaniaSel= spCompania.getSelectedItem().toString();
            sCompaniaSel = sCompaniaSel.substring(sCompaniaSel.indexOf("|")+1).trim();
        }
        else {
            CreateCustomToast("Su usuario no tiene asignado ninguna compania.",Constans.icon_error,Constans.layout_error);
            return;
        }
        /**  CENTRO  DE COSTO **/
        Log.i("Compania sel" , sCompaniaSel) ;
        if (spCentroCosto== null && spCentroCosto.getCount()<=0){
            CreateCustomToast("No se pudo encontrar Centro de Costos",Constans.icon_error,Constans.layout_error);
            return;
        }

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
            asyncTask =  getListReqLogTask.execute(sCompaniaSel,sCentroCosto,sEstado,sFechaIni,sFechaFin, sNroRequisicion);
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
            lblCantReg.setText("LISTA REQUISICIONES      |    0 REGISTROS.");
            lvReqLog.setAdapter(null);

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

        CentroCostoXCompaniaTask centroCostoXCompaniaTask = new CentroCostoXCompaniaTask();
        AsyncTask<String,String,ArrayList<CentroCostoDB>> asyncTask ;
        ArrayList<CentroCostoDB> listCosto =   null ;
        ArrayList<String> dataCCosto = new ArrayList<String>();
        dataCCosto.add("TODOS");
        if (spCompania!= null && spCompania.getCount()>0) {
            String sTempComp = spCompania.getSelectedItem().toString();
            sTempComp = sTempComp.substring(sTempComp.indexOf("|")+1 , sTempComp.length());
            sTempComp = sTempComp.trim();
            try {
                asyncTask = centroCostoXCompaniaTask.execute(sTempComp);
                listCosto = asyncTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (listCosto != null  && listCosto.size()>0) {

                for (int i = 0; i < listCosto.size(); i++) {

                    dataCCosto.add(listCosto.get(i).getC_centrocosto() + "   -    " + listCosto.get(i).getC_descripcion());

                }
                ArrayAdapter<String> adapterCosto = new ArrayAdapter<String>(ListaReqLog.this, android.R.layout.simple_spinner_dropdown_item, dataCCosto);
                adapterCosto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCentroCosto.setAdapter(adapterCosto);
            }
        }
    }

    public  void LoadSpinerCompania () {

        GetCompaniaXUsuarioLogiTask getCompaniasXUsuarioTask = new GetCompaniaXUsuarioLogiTask();
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
                if (item == 0){
                ValidandoAprobacion(adapter.GetItem(Opcicion));
                }
                if (item ==1) {
                 ValidarRechazoLog(adapter.GetItem(Opcicion));
                }
                if (item == 2) {

                RequisicionLogCab s = adapter.GetItem(SelectedItem);
                Intent intent  = new Intent(ListaReqLog.this  , DetalleReqLog.class);
                intent.putExtra("NroReq", String.valueOf(s.getC_numeroreq()));
                intent.putExtra("CCosto", s.getC_ccostonomb());
                intent.putExtra("Estado", s.getC_estadonomb());
                intent.putExtra("Compania", s.getC_compania());
                intent.putExtra("FCreacion", s.getC_fechacreacion());
                intent.putExtra("UCreacion", s.getC_usuariocreacion());
                intent.putExtra("Clasificacion", s.getC_clasificacion());
                startActivity(intent);

                }
                dialog.dismiss();

            }
        }).show();
    }



    public  void  ValidandoAprobacion  (RequisicionLogCab r){

        ValidarAprobacionReqLogTask validarAprobacionReqLogTask = new ValidarAprobacionReqLogTask();
        String sVerificar  = "" ;
        AsyncTask<String,String,String> asyncTask   ;

        Log.i("VALIDAR Aprobar Req "  , r.getC_compania() + " | " + r.getC_numeroreq() +" | "+ codUser);

        try {
            asyncTask = validarAprobacionReqLogTask.execute(r.getC_compania(),r.getC_numeroreq(),codUser);
            sVerificar = (String)asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (sVerificar.equals("OK")){
            AlertAproRechazar("aprobar", r.getC_numeroreq(), r.getC_compania());
        }
        else {
            CreateCustomToast(sVerificar,Constans.icon_error,Constans.layout_error);
            return;
        }

    }

    public void AlertAproRechazar(final String TipoOp, final String numeroReq, final String sCompaniaReq) {
        new AlertDialog.Builder(ListaReqLog.this)
                .setTitle("Advertencia")
                .setMessage("¿Esta seguro que desea "+TipoOp+" la requisición Nº "+numeroReq+" ?")
                .setIcon(R.drawable.icn_alert)
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                if (TipoOp.equals("aprobar")){
                                   // CreateCustomToast("Espere un momento por favor.... " ,Constans.icon_warning,Constans.layot_warning);
                                    dialog.cancel();
                                EjecutarAprobacion(numeroReq,sCompaniaReq);
                                }


                                dialog.cancel();
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();
    }

    public  void  AlerDialogRechazarReqLog (final RequisicionLogCab req , String sCompaniaNomb){
        final Dialog dialog = new Dialog(ListaReqLog.this);
        dialog.setContentView(R.layout.dialog_rechazar_reqlog);
        dialog.setTitle("Rechazar Requisición Nº " + req.getC_numeroreq());
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

         final EditText txtRechazo = (EditText) dialog.findViewById(R.id.txtRechazoReqLog);
        // set the custom dialog components - text, image and button
        EditText txtCompania = (EditText) dialog.findViewById(R.id.txtCompiaRech);
        txtCompania.setText(sCompaniaNomb);
        EditText txtNumeroReq = (EditText) dialog.findViewById(R.id.txtNumeroReqRech);
        txtNumeroReq.setText(req.getC_numeroreq());
        final Spinner spRechazos = (Spinner)dialog.findViewById(R.id.spRazonRechazo);
        spRechazos.setAdapter(adapterRazonRechazo());



        Button btnAceptar = (Button) dialog.findViewById(R.id.btnAceptarRechazo);
        Button btnCancelar = (Button)dialog.findViewById(R.id.btnCancelarRechazo);
        // if button is clicked, close the custom dialog

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String SrazonRech,sRazonDescrip;
                sRazonDescrip = txtRechazo.getText().toString();

                if (spRechazos.getSelectedItemPosition()>0){
                    String sRazRech  = spRechazos.getSelectedItem().toString();
                    sRazRech = sRazRech.substring(0,sRazRech.indexOf("|")-1);
                    EjecutarRechazo(req,sRazRech,sRazonDescrip);
                    LoadDataListView();
                    dialog.dismiss();
                }
                else {
                    CreateCustomToast("Seleccione una  razón de rechazo." , Constans.icon_warning, Constans.layot_warning);
                    return;
                }
            }
        });

        dialog.show();
    }

    public  void   EjecutarRechazo (RequisicionLogCab rq , String sRazonRechazo , String sDescripRechazo)  {
        String sEjecResult  = "";
        AsyncTask<String,String,String> asyncTask;
        EjecutarRechazoReqLogTask  ejecutarRechazoReqLogTask = new EjecutarRechazoReqLogTask();
        try {
            asyncTask =  ejecutarRechazoReqLogTask.execute(rq.getC_compania(),rq.getC_numeroreq(),sRazonRechazo,sDescripRechazo);
            sEjecResult = (String)asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (sEjecResult.equals("OK")){
            //CreateCustomToast("Se rechazó la requición Nº "+rq.getC_numeroreq()+ " .",Constans.icon_succes,Constans.layout_success);
            AlertRechazoOK(rq.getC_numeroreq());
            EnvioCorreo(rq,"RE");

        }
        else  {
            CreateCustomToast(sEjecResult , Constans.icon_error,Constans.layout_error);
            return;
        }


    }
    public  void  ValidarRechazoLog (RequisicionLogCab r){
        String sValdResult = "" ;
        AsyncTask<String,String,String> asyncTask ;
        ValidarRechazoReqLogTask validarRechazoReqLogTask = new ValidarRechazoReqLogTask();

        try {
            asyncTask = validarRechazoReqLogTask.execute(r.getC_compania(),r.getC_numeroreq(),codUser);
            sValdResult = (String)asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (sValdResult.equals("OK")){
            String TmpCompaniaNomb = spCompania.getSelectedItem().toString();
            TmpCompaniaNomb = TmpCompaniaNomb.substring(0,TmpCompaniaNomb.indexOf("|")-1);

            AlerDialogRechazarReqLog(r, TmpCompaniaNomb);
        }
        else {
            CreateCustomToast(sValdResult,Constans.icon_error, Constans.layout_error);
            return;
        }

    }

    public  ArrayAdapter<String> adapterRazonRechazo (){
        ArrayList<String> dataSpiner = new ArrayList<String>();
        ArrayList<Rechazos> listrechazo =  new ArrayList<Rechazos>();
        AsyncTask<String,String,ArrayList<Rechazos>> asyncTask ;
        GetListRechazosTask getListRechazosTask = new GetListRechazosTask();

        try {
            asyncTask =  getListRechazosTask.execute();
            listrechazo =  (ArrayList<Rechazos>) asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        dataSpiner.add("");
        if (listrechazo != null && listrechazo.size()>0){
            for (int i = 0 ; i < listrechazo.size() ; i++){
                dataSpiner.add(listrechazo.get(i).getC_razonrechazo() + " | " + listrechazo.get(i).getC_descripcion() );
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, dataSpiner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return  adapter;

    }


    public  void EjecutarAprobacion (String sNumeroReq  , String  sCompaniaReq) {
        AsyncTask <String,String,String> asyncTask ;
        EjecutarAprobReqLogTask  ejecutarAprobReqLogTask  = new EjecutarAprobReqLogTask();
        String sResulEjec =  "" ;
        try {
            asyncTask = ejecutarAprobReqLogTask.execute(sCompaniaReq,sNumeroReq,codUser);
            sResulEjec = (String) asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (sResulEjec.equals("OK")) {
            AlertAprobacionOK(sNumeroReq);
            EnvioCorreo(adapter.GetItem(selectedItem),"AP");

        }

        else {
            CreateCustomToast(sResulEjec,Constans.icon_error,Constans.layout_error);
            return;

        }


    }

    public void AlertAprobacionOK(String NumeroReq) {

        new AlertDialog.Builder(ListaReqLog.this)
                .setTitle("Información guardada")
                .setMessage("Se aprobó la requisición Nº" + NumeroReq)
                .setIcon(R.drawable.icn_succes_32)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //showToast("Thank you!");
                        LoadDataListView();

                        dialog.cancel();
                    }
                }).show();
    }

    public void AlertRechazoOK(String NumeroReq) {

        new AlertDialog.Builder(ListaReqLog.this)
                .setTitle("Información guardada")
                .setMessage("Se rechazó la requisición Nº" + NumeroReq)
                .setIcon(R.drawable.icn_succes_32)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //showToast("Thank you!");
                        LoadDataListView();

                        dialog.cancel();
                    }
                }).show();
    }

    public  void     EnvioCorreo ( RequisicionLogCab re   , String sEstadoNew) {
        //ProgressDialog progressDialog = null ;
        String sResultEnvio = "" ;
        AsyncTask<String,String,String >asyncTask ;
        EnvioCorreoReqTask  envioCorreoReqTask = new EnvioCorreoReqTask() ;

       // progressDialog= new ProgressDialog(ListaReqLog.this);
      //  progressDialog.setTitle("Sincronizando");
      //  progressDialog.setMessage("Sincronizando accesos .. espere por favor..");
        //progressDialog.setIcon(R.drawable.icn_sync_48);

        //envioCorreoReqTask.progressBar = progressDialog;
        try {
            asyncTask  = envioCorreoReqTask.execute(re.getC_compania(), re.getC_numeroreq(),codUser,sEstadoNew, re.getC_ccosto());
            sResultEnvio = asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (sResultEnvio.equals("OK")){

        }
        else {
          //  CreateCustomToast("No se envio   pudo enviar  notificaciones por correo electronico" ,Constans.icon_error,Constans.layout_error);
           // return;
        }

    }
}
