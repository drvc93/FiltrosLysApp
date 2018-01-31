package com.filtroslys.filtroslysapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import DataBase.ProdMantDataBase;
import Model.QuejaCliente;
import Model.TMACliente;
import Model.TMAMedioRecepcion;
import Model.TMACalificacionQueja;
import Model.TMATipoCalificacionQueja;
import Model.TMAAccionesTomar;
import Model.TMANotificacionQueja;
import Tasks.GetCorrelativoQuejaTask;
import Tasks.GetCorrelativoRecGarTask;
import Tasks.GetLoteSerieRGTask;
import Tasks.InsertQuejaClienteTask;
import Tasks.InsertReclamoGarantiaTask;
import Tasks.TransferirQuejaClienteTask;
import Tasks.TransferirReclamoGarantiaTask;
import Tasks.VerificarItemExisteTask;
import Util.Constans;
import br.com.sapereaude.maskedEditText.MaskedEditText;
import libs.mjn.prettydialog.PrettyDialog;
import spencerstudios.com.fab_toast.FabToast;

public class DatosGenQueja extends AppCompatActivity {
    EditText txtNroQueja,txtFechaRegQJ,txtBSClienteQJ,txtItemQJ;
    EditText txtSerieQJ,txtDocRefQJ,txtFormatoQJ,txtDetalleQJ,txtObservacionesQJ;
    EditText txtDerivadoAQJ,txtFDerivacionQJ;
    EditText txtDescripcionInvestigacionQJ, txtFechaRespuestaQJ;
    EditText txtInvestigadoPorQJ,txtFechaInvestigacionQJ;
    EditText txtDisposicionQJ,txtObservCierreQJ,txtCerradoPorQJ,txtFechaCierreQJ;
    Spinner spMedioRecepcionQJ , spClaseQJ,spSubClaseQJ;
    Spinner spProcedeQJ;
    Spinner spSubClaseCierreQJ,spAccionTomar1QJ,spAccionTomar2QJ,spAccionTomar3QJ,spAccionTomar4QJ;
    CheckBox chkInvestigando,chkCerrarQueja;
    int PantallaReal;
    ImageView btnBuscarClienteQJ;
    MaskedEditText txtCantidadQJ;
    QuejaCliente objQueja;
    Button btnSiguiente,btnCancelar;
    LinearLayout layGeneralQJ,layInvestigacionQJ,layCierreQJ;
    int nIndicadorLayout ;
    String codUser,sFechaSelec,AccionQJ="VER";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_gen_queja);
        setTitle("Datos Generales");
        preferences = PreferenceManager.getDefaultSharedPreferences(DatosGenQueja.this);
        codUser = preferences.getString("UserCod", null);
        AccionQJ = getIntent().getExtras().getString("AccionQJ");

        btnCancelar = findViewById(R.id.btnCancelRegQJ);
        btnSiguiente = findViewById(R.id.btnSigDatosQJ);
        layGeneralQJ = findViewById(R.id.layGeneralQJ);
        layInvestigacionQJ = findViewById(R.id.layInvestigacionQJ);
        layCierreQJ = findViewById(R.id.layCierreQJ);

        nIndicadorLayout = 1 ;
        ConfigurarPantalla(nIndicadorLayout);

        txtNroQueja = findViewById(R.id.txtNroQueja);
        txtFechaRegQJ = findViewById(R.id.txtFechaRegQJ);
        txtBSClienteQJ =  findViewById(R.id.txtBSClienteQJ);
        btnBuscarClienteQJ = findViewById(R.id.btnBuscarClienteQJ);
        spMedioRecepcionQJ = findViewById(R.id.spMedioRecepcionQJ);
        spClaseQJ = findViewById(R.id.spClaseQJ);
        spSubClaseQJ = findViewById(R.id.spSubClaseQJ);
        txtItemQJ = findViewById(R.id.txtItemQJ);
        txtSerieQJ = findViewById(R.id.txtSerieQJ);
        txtDocRefQJ = findViewById(R.id.txtDocRefQJ);
        txtFormatoQJ = findViewById(R.id.txtFormatoQJ);
        txtCantidadQJ = findViewById(R.id.txtCantidadQJ);
        txtDetalleQJ = findViewById(R.id.txtDetalleQJ);
        txtObservacionesQJ = findViewById(R.id.txtObservacionesQJ);
        txtDerivadoAQJ = findViewById(R.id.txtDerivadoAQJ);
        txtFDerivacionQJ = findViewById(R.id.txtFDerivacionQJ);

        chkInvestigando = findViewById(R.id.chkNecesitaVIisita);
        txtDescripcionInvestigacionQJ =  findViewById(R.id.txtDescripcionInvestigacionQJ);
        spProcedeQJ = findViewById(R.id.spProcedeQJ);
        txtFechaRespuestaQJ = findViewById(R.id.txtFechaRespuestaQJ);
        txtInvestigadoPorQJ = findViewById(R.id.txtInvestigadoPorQJ);
        txtFechaInvestigacionQJ = findViewById(R.id.txtFechaInvestigacionQJ);
        spSubClaseCierreQJ = findViewById(R.id.spSubClaseCierreQJ);
        txtDisposicionQJ = findViewById(R.id.txtDisposicionQJ);
        spAccionTomar1QJ = findViewById(R.id.spAccionTomar1QJ);
        spAccionTomar2QJ = findViewById(R.id.spAccionTomar2QJ);
        spAccionTomar3QJ = findViewById(R.id.spAccionTomar3QJ);
        spAccionTomar4QJ = findViewById(R.id.spAccionTomar4QJ);
        chkCerrarQueja = findViewById(R.id.chkCerrarQueja);
        txtObservCierreQJ = findViewById(R.id.txtObservCierreQJ);
        txtCerradoPorQJ = findViewById(R.id.txtCerradoPorQJ);
        txtFechaCierreQJ = findViewById(R.id.txtFechaCierreQJ);
        
        txtBSClienteQJ.setEnabled(false);
        txtFechaRegQJ.setEnabled(false);
        txtNroQueja.setInputType(InputType.TYPE_CLASS_NUMBER);
        txtCantidadQJ.setText("0");
        PantallaReal=1;

        DateFormat DateFrm = new SimpleDateFormat("yyyy-MM-dd");
        String sFechaReg = DateFrm.format(Calendar.getInstance().getTime());
        txtFechaRegQJ.setText(sFechaReg);

        objQueja  = new QuejaCliente();
        LoadSpinersMedioRecepcion();
        LoadSpinersClase();
        LoadSpinersProcede();
        LoadSpinersAcciones();

        spClaseQJ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    String clase = spClaseQJ.getSelectedItem().toString().trim();
                    if(clase.equals("01")){
                        txtItemQJ.setEnabled(true);
                        txtSerieQJ.setEnabled(true);
                        txtCantidadQJ.setEnabled(true);
                        txtCantidadQJ.setText("0");
                    }else{
                        txtItemQJ.setEnabled(false);
                        txtSerieQJ.setEnabled(false);
                        txtCantidadQJ.setEnabled(false);
                        txtCantidadQJ.setText("0");
                    }
                    clase = clase.substring(0,2);
                    Log.i("valorspiner" , clase);
                    LoadSpinersSubClase(clase);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nIndicadorLayout>1){
                    nIndicadorLayout= nIndicadorLayout -1;
                    ConfigurarPantalla(nIndicadorLayout);
                }
                else {
                    DatosGenQueja.super.onBackPressed();
                }
            }
        });
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccionQJ.equals("VER")){
                    if (nIndicadorLayout<3) {
                        nIndicadorLayout = nIndicadorLayout +1 ;
                        ConfigurarPantalla(nIndicadorLayout);
                    }
                    else if (nIndicadorLayout==3){
                        CerrarVisor();
                    }
                }else{
                    VaLidacionFinal();
                }
            }
        });

        btnBuscarClienteQJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogBuscarCliente();
            }
        });



        txtItemQJ.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    VerificarItemExiste(txtItemQJ.getText().toString());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (nIndicadorLayout>1) {
            nIndicadorLayout = nIndicadorLayout-1;
            ConfigurarPantalla(nIndicadorLayout);
        }
        else {
            return;
        }
    }

    public void VaLidacionFinal(){
        String sCliente,sMedioRec="",sClase="",sSubClase="",sDetalleQJ,sItem="",sCantidad="0";
        String sLote,sDocRef,sFormato,sObserv,sFechaReg;

        sCliente = txtBSClienteQJ.getText().toString().trim();
        sCliente = sCliente.indexOf("|")>0? sCliente.substring(0,sCliente.indexOf("|")-1).trim():"0";
        sDetalleQJ = txtDetalleQJ.getText().toString().trim();
        sItem = txtItemQJ.getText().toString().trim();
        sLote = txtSerieQJ.getText().toString().trim();
        sCantidad = txtCantidadQJ.getText().toString();
        sDocRef = txtDocRefQJ.getText().toString().trim();
        sFormato = txtFormatoQJ.getText().toString().trim();
        sObserv = txtObservacionesQJ.getText().toString().trim();

        if (sCliente.isEmpty() || sCliente.equals("0")){
            ShowMessageWindow("Debe seleccionar un cliente.");
            return;
        }

        if (spMedioRecepcionQJ.getSelectedItemPosition()>0){
            sMedioRec = spMedioRecepcionQJ.getSelectedItem().toString();
            sMedioRec = sMedioRec.substring(0,sMedioRec.indexOf("|")-1).trim();
        }

        if (spClaseQJ.getSelectedItemPosition()>0){
            sClase = spClaseQJ.getSelectedItem().toString();
            sClase = sClase.substring(0,sClase.indexOf("|")-1).trim();
        }
        if (spSubClaseQJ.getSelectedItemPosition()>0) {
            sSubClase = spSubClaseQJ.getSelectedItem().toString();
            sSubClase = sSubClase.substring(0, sSubClase.indexOf("|") - 1).trim();
        }

        if (sMedioRec.isEmpty()){
            ShowMessageWindow("Debe seleccionar Medio de Recepcion.");
            return;
        }

        if (sClase.isEmpty()){
            ShowMessageWindow("Debe seleccionar Clase.");
            return;
        }

        if (sSubClase.isEmpty()){
            ShowMessageWindow("Debe seleccionar Sub Clase.");
            return;
        }

        if (sDetalleQJ.isEmpty()){
            ShowMessageWindow("Debe ingresar Detalle de Queja.");
            return;
        }

        if (sClase.equals("01")){
            if (sItem.isEmpty()){
                ShowMessageWindow("Debe Ingresar Item.");
                return;
            }

            if (sCantidad.isEmpty() || sCantidad.equals("0")){
                ShowMessageWindow("Cantidad Invalida.");
                return;
            }
        }
        DateFormat DateFrm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sFechaReg = DateFrm.format(Calendar.getInstance().getTime());

        objQueja.setC_compania(Constans.NroConpania);
        objQueja.setN_cliente(Integer.valueOf(sCliente));
        objQueja.setC_nroformato(sFormato);
        objQueja.setC_documentoref(sDocRef);
        objQueja.setC_mediorecepcion(sMedioRec);
        objQueja.setC_calificacion(sClase);
        objQueja.setC_tipocalificacion(sSubClase);
        objQueja.setC_item(sItem);
        objQueja.setC_lote(sLote);
        objQueja.setN_cantidad(Integer.valueOf(sCantidad));
        objQueja.setC_desqueja(sDetalleQJ);
        objQueja.setC_observaciones(sObserv);
        objQueja.setC_usuario(codUser);
        objQueja.setD_fechareg(sFechaReg);
        objQueja.setC_estado("A");
        SeleccionarFormaGuardado();
    }

    public void CerrarVisor(){

    }

    public void SeleccionarFormaGuardado(){
        final CharSequence[] items = {"Solo guardar en movil","Enviar a Servidor","Cancelar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(DatosGenQueja.this);
        builder.setTitle("Seleccionar opcion : ");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

               switch (item) {
                   case  0 :
                       GuardarQueja("Local");
                       break;
                   case 1:
                       GuardarQueja("Servidor");
                       break;
                   case 2:
                       dialog.dismiss();
               }
            }
        }).show();
    }

    public void ConfigurarPantalla (int nIndicadorPantlla){
        layGeneralQJ.setVisibility(View.GONE);
        layInvestigacionQJ.setVisibility(View.GONE);
        layCierreQJ.setVisibility(View.GONE);

        PantallaReal=nIndicadorPantlla;
        switch (nIndicadorPantlla){
            case 1:
                btnCancelar.setText("Cancelar");
                btnSiguiente.setText("Siguiente");
                layGeneralQJ.setVisibility(View.VISIBLE);
                break;
            case 2:
                btnCancelar.setText("Volver");
                btnSiguiente.setText("Siguiente");
                layInvestigacionQJ.setVisibility(View.VISIBLE);
                break;
            case 3:
                btnCancelar.setText("Volver");
                btnSiguiente.setText("Siguiente");
                layCierreQJ.setVisibility(View.VISIBLE);
                break;
        }
    }

    public  void  GuardarQueja(String sTipoGuardado){
        ProdMantDataBase  db =  new ProdMantDataBase(DatosGenQueja.this);
        long rowid = db.InsertQuejaCliente(objQueja);
        if (rowid>0){
            if (sTipoGuardado.equals("Local"))
                FabToast.makeText(DatosGenQueja.this, "Se guardo correctamente la informacion", FabToast.LENGTH_LONG, FabToast.SUCCESS,  FabToast.POSITION_DEFAULT).show();
            Log.i("row id reclamog " , String.valueOf(rowid));
        }
        if (sTipoGuardado.equals("Servidor") && rowid>0) {
            String sCorrelativoQJ = "";
            AsyncTask<String,String,String> asyncTaskCorrQJ;
            GetCorrelativoQuejaTask getCorrQJTask = new GetCorrelativoQuejaTask();
            try {
                asyncTaskCorrQJ = getCorrQJTask.execute();
                sCorrelativoQJ = asyncTaskCorrQJ.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Log.i("correlativo queja",sCorrelativoQJ);

            sCorrelativoQJ = String.valueOf(Integer.valueOf(sCorrelativoQJ)+1);
            AsyncTask<String,String,String> asyncTaskInsertQJ;
            InsertQuejaClienteTask inserQJTask = new InsertQuejaClienteTask();

            String InserQJ="";
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String fecha = df.format(Calendar.getInstance().getTime());
            objQueja.setD_fechareg(fecha);

            try {
                asyncTaskInsertQJ = inserQJTask.execute(
                        Constans.NroConpania,sCorrelativoQJ,"accion",codUser,
                        objQueja.getC_nroformato(),
                        String.valueOf(objQueja.getN_cliente()),
                        objQueja.getD_fechareg(),
                        objQueja.getC_documentoref(),
                        objQueja.getC_mediorecepcion(),
                        "XX",
                        objQueja.getC_calificacion(),
                        "USD",
                        objQueja.getC_tipocalificacion(),
                        objQueja.getC_item(),
                        objQueja.getC_lote(),
                        String.valueOf(objQueja.getN_cantidad()),
                        objQueja.getC_desqueja(),
                        objQueja.getC_observaciones(),
                        objQueja.getC_estado());
                InserQJ = asyncTaskInsertQJ.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (InserQJ.equals("OK")){
                String sResultTrasnf="";
                AsyncTask<String,String,String> asyncTaskTransQJ ;
                TransferirQuejaClienteTask transfQuejaClienteTask = new TransferirQuejaClienteTask();
                try {
                    asyncTaskTransQJ = transfQuejaClienteTask.execute(Constans.NroConpania,sCorrelativoQJ,"tranferir");
                    sResultTrasnf = asyncTaskTransQJ.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if (sResultTrasnf.equals("OK")){
                    FabToast.makeText(DatosGenQueja.this,"Se guardo y sincronizo correctamente la informacion en el servidor.", FabToast.LENGTH_LONG, FabToast.SUCCESS,  FabToast.POSITION_DEFAULT).show();
                    super.onBackPressed();
                }
                else {
                    FabToast.makeText(DatosGenQueja.this,"Error : " + sResultTrasnf, FabToast.LENGTH_LONG, FabToast.ERROR,  FabToast.POSITION_DEFAULT).show();
                }
            }
            else {
                FabToast.makeText(DatosGenQueja.this, "Error : " + InserQJ, FabToast.LENGTH_LONG, FabToast.ERROR,  FabToast.POSITION_DEFAULT).show();
            }
        }
    }

    public void VerificarItemExiste(String sItem){
        String sVerif = "0";
        AsyncTask<String,String,String> asyncTaskVerif;
        VerificarItemExisteTask verificarItemExisteTask = new VerificarItemExisteTask();
        try {
            asyncTaskVerif = verificarItemExisteTask.execute(sItem.toUpperCase());
            sVerif =  asyncTaskVerif.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (Integer.valueOf(sVerif)<=0){
            ShowMessageWindow("Codigo de Filtro no existe.");
            //btnBuscarFiltro.setImageResource(R.drawable.icn_error_24red);
        }
        else {
            //btnBuscarFiltro.setImageResource(R.drawable.icn_succes_32);
        }
    }

    public void LoadSpinersClase(){
        ProdMantDataBase db = new ProdMantDataBase(DatosGenQueja.this);
        ArrayList<TMACalificacionQueja> oLista = db.GetClase();
        ArrayList<String> spData = new ArrayList<>();
        spData.add("Seleccione Clase...");
        if (oLista.size()>0){
            for (int i=0;i<oLista.size();i++) {
                spData.add(oLista.get(i).getC_calificacion()+ " | " + oLista.get(i).getC_descripcion());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spData);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spClaseQJ.setAdapter(dataAdapter);
        }
    }

    public void LoadSpinersSubClase(String Clase){
        ProdMantDataBase db = new ProdMantDataBase(DatosGenQueja.this);
        ArrayList<TMATipoCalificacionQueja> oLista = db.GetSubClase(Clase);
        ArrayList<String> spData = new ArrayList<>();
        spData.add("Seleccione SubClase...");
        if (oLista.size()>0){
            for (int i=0;i<oLista.size();i++) {
                spData.add(oLista.get(i).getC_tipocalificacion()+ " | " +oLista.get(i).getC_descripcion());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, spData);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spSubClaseQJ.setAdapter(dataAdapter);
            spSubClaseCierreQJ.setAdapter(dataAdapter);
        }
    }

    public void LoadSpinersMedioRecepcion(){
        ProdMantDataBase db = new ProdMantDataBase(DatosGenQueja.this);
        ArrayList<TMAMedioRecepcion> oLista = db.GetMedioRecepcion();
        ArrayList<String> spData = new ArrayList<>();
        spData.add("Seleccione Medio Recepcion...");
        if (oLista.size()>0){
            for (int i=0;i<oLista.size();i++) {
                spData.add(oLista.get(i).getC_mediorecepcion()+ " | " + oLista.get(i).getC_descripcion());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spData);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMedioRecepcionQJ.setAdapter(dataAdapter);
        }
    }

    public void LoadSpinersAcciones(){
        ProdMantDataBase db = new ProdMantDataBase(DatosGenQueja.this);
        ArrayList<TMAAccionesTomar> oLista = db.GetAccionesTomar();
        ArrayList<String> spData = new ArrayList<>();
        spData.add("Seleccione Accion...");
        if (oLista.size()>0){
            for (int i=0;i<oLista.size();i++) {
                spData.add(oLista.get(i).getC_codaccion()+ " | " + oLista.get(i).getC_descripcion());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spData);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spAccionTomar1QJ.setAdapter(dataAdapter);
            spAccionTomar2QJ.setAdapter(dataAdapter);
            spAccionTomar3QJ.setAdapter(dataAdapter);
            spAccionTomar4QJ.setAdapter(dataAdapter);
        }
    }

    public void LoadSpinersProcede(){
        ArrayList<String> spdata = new ArrayList<>();
        spdata.add("NO");
        spdata.add("SI");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, spdata);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProcedeQJ.setAdapter(dataAdapter);
    }

    public void AlertDialogBuscarCliente(){
        final Dialog dialog  = new Dialog(DatosGenQueja.this);
        dialog.setContentView(R.layout.dialog_buscar_cliente);
        dialog.setTitle("Buscar Cliente");
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button btnSearch = dialog.findViewById(R.id.btnSearchCliente);
        Button btnCancel = dialog.findViewById(R.id.btnCancelSearchCli);
        final EditText txtSearchCliente = dialog.findViewById(R.id.txtSearchCliente);
        final ListView lvBuscarClientes  = dialog.findViewById(R.id.lvSearchCliente);
        lvBuscarClientes.setAdapter(getAdapterBuscarCliente("%"));

        lvBuscarClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtBSClienteQJ.setText(lvBuscarClientes.getItemAtPosition(position).toString());
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvBuscarClientes.setAdapter(getAdapterBuscarCliente(txtSearchCliente.getText().toString().toUpperCase()));
            }
        });
        dialog.show();
    }

    public ArrayAdapter<String> getAdapterBuscarCliente(String sFilterText){
        ProdMantDataBase db = new ProdMantDataBase(DatosGenQueja.this);
        ArrayList<TMACliente> clientes = db.GetSelectClientes(sFilterText);
        ArrayList<String> data = new ArrayList<>();
            if (clientes.size()>0){
                for (int i = 0; i < clientes.size() ; i++) {
                    TMACliente c = clientes.get(i);
                    data.add(String.valueOf(c.n_cliente)+ " | " + c.getC_razonsocial());
                }
            }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);
        Log.i("size adapter  " , String.valueOf(data.size()));
        return arrayAdapter;
    }

    public void ShowMessageWindow(String sMessage){
        new PrettyDialog(this)
                .setTitle("Aviso")
                .setMessage(sMessage)
                .setIconTint( R.color.WarningColor)
                .show();
    }

}
