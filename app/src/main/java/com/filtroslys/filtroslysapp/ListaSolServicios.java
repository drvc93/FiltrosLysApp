package com.filtroslys.filtroslysapp;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import DataBase.MaquinaDB;
import DataBase.ProdMantDataBase;
import Model.EmpAsigSolicitud;
import Model.EmpleadoMant;
import Model.SolicitudServicio;
import Model.UsuarioCompania;
import Model.UsuarioSolicitante;
import Tasks.AprobarSolicitudServTask;
import Tasks.GetEmpleadosMantTask;
import Tasks.GetEmpleadosSolicitudTask;
import Tasks.GetListUsuarioSolicitTask;
import Tasks.GetListaSolcitudServTask;
import Tasks.GetMaquinaXCompaniaTask;
import Tasks.GetNumeroEmpSolicitudTask;
import Tasks.ValidarEmpleadoMantTask;
import Tasks.getCompaniasXUsuarioTask;
import Util.Constans;
import Util.ExpandibleListMenuAdapater;
import Util.HistorialInspGenAdapater;
import Util.SolicitudServAdapter;

public class ListaSolServicios extends AppCompatActivity {
   // Button  btnFiltros ;
   SharedPreferences preferences;
   String sFechaIni= "" , sFechaFin ="" , sFlagFecha ="" , sPersonaSolcitante ="" , sMaquina ="", sPrioridad = "" ,sEstado ="",sCompania ="";
    ListView LVSolcitudesServ;
    EditText txtFechaIni , txtFechaFin ,txtNroSolicitud ;
    SolicitudServAdapter adapter ;
    Spinner spPrioridad , spEstado , spMaquina , spUsuarioSolict,spCompania;
    int nPersonaSolict ;
    String codUser ;
    Button btnBuscar ;
    TextView lblCantReg ;
    int SelectedItem  = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_sol_servicios);
        setTitle("Lista de Solicitudes de Servicio.");

        Calendar c = Calendar.getInstance();

        int month = c.get(Calendar.MONTH)+1;
        String mes = String.format("%02d", month);
        int year = c.get(Calendar.YEAR);


        //btnFiltros = (Button)findViewById(R.id.btnFiltroSolicitudes);
        LVSolcitudesServ = (ListView)findViewById(R.id.lvSoliciudesServ);
        spEstado = (Spinner)findViewById(R.id.spEstadoSS);
        spMaquina  =(Spinner)findViewById(R.id.spMaquinaSS);
        spPrioridad = (Spinner)findViewById(R.id.spPrioridadSS);
        spCompania = (Spinner)findViewById(R.id.spCompSS);
        spUsuarioSolict = (Spinner)findViewById(R.id.spUsuarioSolicitanteSS);
        txtFechaIni = (EditText)findViewById(R.id.txtFechaIniSS);
        txtFechaFin = (EditText)findViewById(R.id.txtFechaFinSS);
        btnBuscar = (Button)findViewById(R.id.btnBuscarSS);
        lblCantReg = (TextView)findViewById(R.id.lblCantRegistrosSS);
        txtNroSolicitud = (EditText)findViewById(R.id.txtNroSolicit);
        preferences = PreferenceManager.getDefaultSharedPreferences(ListaSolServicios.this);
        codUser = preferences.getString("UserCod", null);


        ActionBar actionBar = getSupportActionBar();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.RED);
            actionBar.hide();

        } else {
            actionBar.hide();
        }


        LoadSpinerCompania();
        LoadSpinnerEstado();
        LoadSpinerPiroidad();
        LoadMaquinas();
        LoadSpinerUserSolic();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtFechaIni.setText(  "01/"+mes+"/"+String.valueOf(year));
        txtFechaFin.setText( sdf.format(new Date()));

        // data list view
        LoadDataLista();
        requestSmsPermission();

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

        LVSolcitudesServ.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {



                SelectedItem= i ;
              MenuLista();
                return false;
            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadDataLista();
            }
        });

        spCompania.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LoadMaquinas();
                LoadSpinerUserSolic();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void requestSmsPermission() {
        String permission = Manifest.permission.SEND_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,
                                            int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ListaSolServicios.this,"Permiso otorgado", Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(ListaSolServicios.this,"permiso deneado", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public  void LoadSpinnerEstado () {
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("TODOS");
        spinnerArray.add("PENDIENTE");
        spinnerArray.add("APROBADO");
        spinnerArray.add("ANULADO");
        spinnerArray.add("EN PROCESO");
        spinnerArray.add("TERMINADO");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spEstado.setAdapter(adapter);
        spEstado.setSelection(1);

    }

    public  void     LoadSpinerPiroidad () {

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("TODOS");
        spinnerArray.add("URGENTE");
        spinnerArray.add("PRIORITARIO");
        spinnerArray.add("NORMAL");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spPrioridad.setAdapter(adapter);

    }

    public  void  LoadMaquinas () {
        AsyncTask<String,String,ArrayList<MaquinaDB>>  asyncTask ;
        GetMaquinaXCompaniaTask  getMaquinaXCompaniaTask = new GetMaquinaXCompaniaTask();
        ArrayList<MaquinaDB> listMaq = null;
        ArrayList<String> dataMaq = new ArrayList<String>();
        dataMaq.add("TODOS");

        if (spCompania.getAdapter() != null && spCompania.getCount() > 0) {
            String sTempComp = spCompania.getSelectedItem().toString();
            sTempComp = sTempComp.substring(sTempComp.indexOf("|")+1 , sTempComp.length());
            sTempComp = sTempComp.trim();
            try {
                asyncTask = getMaquinaXCompaniaTask.execute(sTempComp);
                listMaq = (ArrayList<MaquinaDB>) asyncTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (listMaq!= null && listMaq.size()>0) {

                for (int i = 0; i < listMaq.size(); i++) {

                    dataMaq.add(listMaq.get(i).getC_maquina() + "   -    " + listMaq.get(i).getC_descripcion());

                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, dataMaq);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spMaquina.setAdapter(adapter);

        }
    }

    public  void  LoadSpinerUserSolic () {

        GetListUsuarioSolicitTask getListUsuarioSolicitTask = new GetListUsuarioSolicitTask();
        AsyncTask <String,String,ArrayList<UsuarioSolicitante>> asyncTask;
        ArrayList<UsuarioSolicitante>  listaUSuario  = null   ;
        ArrayList<String> dataSpiner = new ArrayList<String>()  ;
        if  ( spCompania !=null && spCompania.getCount()>0) {
            String comp = spCompania.getSelectedItem().toString();
            comp = comp.substring(comp.indexOf("|")+1).trim();
            Log.i("Compania Useer solic:" ,">" +comp +"<") ;

        try {
            asyncTask = getListUsuarioSolicitTask.execute(comp);
            listaUSuario = ( ArrayList<UsuarioSolicitante> ) asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        }
        dataSpiner.add("TODOS");
        if (listaUSuario != null && listaUSuario.size()>0){

            for (int i = 0; i < listaUSuario.size() ; i++) {
                UsuarioSolicitante u = listaUSuario.get(i);
                dataSpiner.add(String.valueOf(u.getN_persona())+ " | " + u.getC_nombreCompleto());

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, dataSpiner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spUsuarioSolict.setAdapter(adapter);


        }
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
            if (getCompaniasXUsuarioTask.msj.equals("OK")){

            }
            else {
                CreateCustomToast( getCompaniasXUsuarioTask.msj, Constans.icon_warning, Constans.layot_warning);
                return;
            }

            CreateCustomToast("El usuario : "+codUser+" no tiene compania asignada. ", Constans.icon_warning, Constans.layot_warning);
        }

    }


    public  void  LoadDataLista () {
          String nSolicitud;


        if (spCompania.getAdapter()!=null && spCompania.getCount()>0) {
            sCompania = spCompania.getSelectedItem().toString();
            sCompania = sCompania.substring(sCompania.indexOf("|") + 1).trim();
            sFlagFecha = "S";
            sFechaIni = txtFechaIni.getText().toString();
            sFechaFin = txtFechaFin.getText().toString();
        }
        else
        {
            return;
        }


        if (TextUtils.isEmpty(txtNroSolicitud.getText().toString().trim())){
            nSolicitud = "0" ;
        }
        else {
            nSolicitud = txtNroSolicitud.getText().toString().trim();
        }

        /* maquina */
        sMaquina = spMaquina.getSelectedItem().toString() ;
        if (sMaquina.equals("TODOS")) {
          sMaquina = "%" ;
        }
        else {
            sMaquina = sMaquina.substring(0,11);
            sMaquina  = sMaquina.substring(0,sMaquina.lastIndexOf("-")-1).trim();
        }

        /* prioridad*/
        sPrioridad = spPrioridad.getSelectedItem().toString();
        if  (sPrioridad.equals("TODOS")) {
         sPrioridad =  "%" ;

        }
        else  if (sPrioridad.equals("URGENTE")){
            sPrioridad = "01";
        }
        else  if (sPrioridad.equals("PRIORITARIO")){
            sPrioridad = "02";
        }
        else  if (sPrioridad.equals("NORMAL")){
            sPrioridad = "03";
        }

        /************/

        /* PERSONA SOLICITANTE*/
        if (spUsuarioSolict.getSelectedItem().toString().equals("TODOS")){
            nPersonaSolict = 0 ;
        }
        else  {
            String sCodPersona =  spUsuarioSolict.getSelectedItem().toString() ;
            sCodPersona = sCodPersona.substring(0,sCodPersona.indexOf("|")-1);
            nPersonaSolict = Integer.valueOf(sCodPersona);
        }

        sEstado = spEstado.getSelectedItem().toString();
        if (sEstado.equals("TODOS")){
            sEstado = "%";
        }
        else if (sEstado.equals("EN PROCESO")){
            sEstado="EP" ;
        }
        else {
            sEstado= sEstado.substring(0,2);
        }

        AsyncTask<String,String,ArrayList<SolicitudServicio>> asyncTaskSolcit;
        GetListaSolcitudServTask getListaSolcitudServTask = new GetListaSolcitudServTask();
        ArrayList<SolicitudServicio> lisdata  = new ArrayList<SolicitudServicio>();

        Log.i("SS Compania" , sCompania);
        Log.i("SS maquina" , sMaquina);
        Log.i("SS prioridad" , sPrioridad);
        Log.i("SS fechaini" , sFechaIni);
        Log.i("SS fechafin" , sFechaFin);
        Log.i("SS nPersonaSolict" , String.valueOf(nPersonaSolict));
        Log.i("SS estado" , sEstado);

        try {
            asyncTaskSolcit = getListaSolcitudServTask.execute(sCompania,sFlagFecha,sFechaIni,sFechaFin,sMaquina,sPrioridad,sEstado,String.valueOf(nPersonaSolict),nSolicitud);
            lisdata = (ArrayList<SolicitudServicio>)asyncTaskSolcit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (lisdata != null) {
            adapter = new SolicitudServAdapter(ListaSolServicios.this, R.layout.item_list_solocitservicios, lisdata);
            LVSolcitudesServ.setAdapter(adapter);
            lblCantReg.setText("LISTA DE SOLCITIUDES      |    " + String.valueOf(LVSolcitudesServ.getCount())+ " REGISTROS.");
        } else {
            CreateCustomToast("No se encontro resultados ", Constans.icon_warning, Constans.layot_warning);
            LVSolcitudesServ.setAdapter(null);
            lblCantReg.setText("LISTA DE SOLCITIUDES      |   0 REGISTROS.");

        }

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
        Toast toast = new Toast(ListaSolServicios.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }

    public void SelecFecha(final EditText txtFecha) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.datapicker_layout, null, false);
        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);
        new AlertDialog.Builder(ListaSolServicios.this).setView(view)
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

    public  void  AprobarSolicitud () {
        SolicitudServicio solicitud = adapter.GetItem(SelectedItem);
        String resultCantEmp  = "";
        int cantidadEmp = 0;
         if (solicitud.getC_estado().equals("Pendiente")) {
             AsyncTask<String,String,String> asyncTaskCantEmp   ;
             GetNumeroEmpSolicitudTask getNumeroEmpSolicitudTask = new GetNumeroEmpSolicitudTask();

                 try {
                     asyncTaskCantEmp = getNumeroEmpSolicitudTask.execute(solicitud.getC_compania(),String.valueOf(solicitud.getN_solicitud()));
                     resultCantEmp = (String) asyncTaskCantEmp.get();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 } catch (ExecutionException e) {
                     e.printStackTrace();
                 }
                 cantidadEmp = Integer.valueOf(resultCantEmp);

                 if (cantidadEmp>0){
                  AvisoAprobarSolcitus( String.valueOf( solicitud.getN_solicitud()), solicitud.getC_compania());
                 }
                 else {
                     CreateCustomToast("No se ha asginado empleados a esta solicitud ("+String.valueOf(solicitud.getN_solicitud())+")",Constans.icon_error,Constans.layout_error);
                 }


         }
        else {
            CreateCustomToast("La solicitud no  se encuntra pendiente.",Constans.icon_error,Constans.layout_error);
        }

    }

    public void MenuLista() {

        final CharSequence[] items = { "01.- Asignar Empleado","02.- Aprobar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ListaSolServicios.this);
        builder.setTitle("Seleccione una opción");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                if (item == 0){
                    String validacion = "" , sItemEstado ="";
                    AsyncTask<String,String,String> asyncTask;
                    ValidarEmpleadoMantTask validarEmpleadoMantTask = new ValidarEmpleadoMantTask();

                    try {
                        asyncTask = validarEmpleadoMantTask.execute(codUser);
                        validacion = (String)validarEmpleadoMantTask.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    if (validacion.equals("S")) {
                        sItemEstado = adapter.GetItem(SelectedItem).getC_estado();
                        if (sItemEstado.equals("Pendiente")) {

                            Intent intent = new Intent(ListaSolServicios.this, AsignarEmpleado.class);
                            SolicitudServicio s = adapter.GetItem(SelectedItem);
                            intent.putExtra("NroSolicitud", String.valueOf(s.getN_solicitud()));
                            intent.putExtra("FechaReg", s.getC_fechareg());
                            intent.putExtra("Estado", s.getC_estado());
                            intent.putExtra("Prioridad", s.getC_prioridad());
                            intent.putExtra("Compania", s.getC_compania());
                            startActivity(intent);
                        }
                        else {
                            CreateCustomToast("Solicitud no se encuntra pendiente.", Constans.icon_warning, Constans.layot_warning);
                            return;
                        }
                    }
                    else {
                        CreateCustomToast("Usted no puede Asignar Personal.", Constans.icon_warning, Constans.layot_warning);
                         return;
                    }
                }
                else if(item==1){
                    AprobarSolicitud();
                }

                dialog.dismiss();

            }
        }).show();
    }

    public  void  EjecutarAprobacion (String compania, String nSolicitud  ) {
        String msj_result = "";
        AprobarSolicitudServTask aprobarSolicitudServTask = new AprobarSolicitudServTask();
        AsyncTask<String,String,String> asyncTask  ;

        try {
            asyncTask = aprobarSolicitudServTask.execute(compania,nSolicitud,codUser);
            msj_result = (String) asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (msj_result.equals("OK")){
            EnviandoNotificacionSMS(compania,nSolicitud);
            CreateCustomToast("Se aprobó la solicitud de servicio Nº " + nSolicitud + ".",Constans.icon_succes,Constans.layout_success);

        }
        else {
            CreateCustomToast(msj_result,Constans.icon_error,Constans.layout_error);
        }

    }

    public void  EnviandoNotificacionSMS (String sCompaniaSolicitud, String nNroSolicitud) {
        ArrayList<EmpleadoMant> listEmpleadosGeneral = new ArrayList<EmpleadoMant>();
        ArrayList<EmpAsigSolicitud> listEmpleadosAsignados = new ArrayList<EmpAsigSolicitud>();
        AsyncTask <String,String, ArrayList<EmpleadoMant>> asyncTaskGeneral  ;
        AsyncTask<String,String, ArrayList<EmpAsigSolicitud>>asyncTaskAsignados ;
        GetEmpleadosSolicitudTask  getEmpleadosSolicitudTask = new GetEmpleadosSolicitudTask();
        GetEmpleadosMantTask getEmpleadosMantTask = new GetEmpleadosMantTask();


        try {
            Log.i("Compania/nro Solcitud Envio Sms" , sCompaniaSolicitud + "/" + nNroSolicitud);
            asyncTaskAsignados = getEmpleadosSolicitudTask.execute(sCompaniaSolicitud,nNroSolicitud);
            listEmpleadosAsignados =  (ArrayList<EmpAsigSolicitud>) asyncTaskAsignados.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listEmpleadosAsignados != null && listEmpleadosAsignados.size()>0){

            try {
                asyncTaskGeneral = getEmpleadosMantTask.execute(sCompaniaSolicitud);
                listEmpleadosGeneral = (ArrayList<EmpleadoMant>)asyncTaskGeneral.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            for (int i = 0 ; i < listEmpleadosAsignados.size();i++){
                String movil  = BuscarNumeroEmp(listEmpleadosAsignados.get(i).getN_empleado(),listEmpleadosGeneral);
                String sProblema = adapter.GetItem(SelectedItem).getC_descriproblema();
                String ccsto   = adapter.GetItem(SelectedItem).getC_ccostonomb();
                String sTipo = adapter.GetItem(SelectedItem).getC_tiposolcitud();
                String sMq = adapter.GetItem(SelectedItem).getC_maquinanomb();
                String sSolcitud = String.valueOf(adapter.GetItem(SelectedItem).getN_solicitud());
                String mensaje = "Estimado, se le ha asignado la solicitud de mantenimiento Nro "+sSolcitud+ ", hacer la revision correspondiente del caso;"+ "\n"+
                                "Centro Costo : " + ccsto +  "\n"+
                                "Tipo :" + sTipo+  "\n"+
                                "Maquina:" + sMq +  "\n"+
                                "Descripcion gral. del problema : " +sProblema;
                Log.i("Sms soliicitud " , mensaje);
                sendSMS(movil,mensaje);
            }
        }

    }

    public String BuscarNumeroEmp (int nEmpleado,  ArrayList<EmpleadoMant> listaGeneralEmp){
        String numeromovil = "";
        for (int i = 0 ; i <listaGeneralEmp.size() ; i++){
            if (listaGeneralEmp.get(i).getN_empleado()==nEmpleado){
                numeromovil = listaGeneralEmp.get(i).getC_numeromovil();
                break;
            }
        }
        return numeromovil ;
    }

    public void AvisoAprobarSolcitus(final String nSolcitud, final String sCompania) {
        new AlertDialog.Builder(ListaSolServicios.this)
                .setTitle("Advertencia")
                .setMessage("¿Esta seguro que desea aprobar la solicitud Nº "+nSolcitud+" ?")
                .setIcon(R.drawable.icn_alert)
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                            //   @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                EjecutarAprobacion(sCompania,nSolcitud);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    //@TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        //   showToast("Mike is not awesome for you. :(");
                        CreateCustomToast("Se cancelo la operación." , Constans.icon_warning,Constans.layot_warning);
                        dialog.cancel();
                    }
                }).show();
    }



    private void sendSMS(String phoneNumber, String message) {
         message = message.replace('.',';');

        if (phoneNumber.equals("-")){
        }
        else {

            SmsManager sms = SmsManager.getDefault();
            if (message.length()>140){
                //Toast.makeText(ListaSolServicios.this , phoneNumber+" | " + message +" | "+String.valueOf(message.length() + "-Multipart"),Toast.LENGTH_LONG).show();
                ArrayList<String>  parts = sms.divideMessage(message);
                sms.sendMultipartTextMessage(phoneNumber,null,parts,null,null);


            }
            else {


                SmsManager sms_2 = SmsManager.getDefault();

                sms_2.sendTextMessage(phoneNumber, null, message, null, null);

                //Toast.makeText(this, "Sent.", Toast.LENGTH_SHORT).show();

            }
        }
    }

}
