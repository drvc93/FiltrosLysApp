package com.filtroslys.filtroslysapp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import DataBase.ProdMantDataBase;
import Model.DocsQuejaCliente;
import Model.QuejaCliente;
import Model.ReclamoGarantia;
import Model.TMACliente;
import Model.TMAMedioRecepcion;
import Model.TMACalificacionQueja;
import Model.TMATipoCalificacionQueja;
import Model.TMAAccionesTomar;
import Model.TMANotificacionQueja;
import Tasks.GetCorrelativoQuejaTask;
import Tasks.GetCorrelativoRecGarTask;
import Tasks.GetFotoRG;
import Tasks.GetListaDatosFotosQJTask;
import Tasks.GetLoteSerieRGTask;
import Tasks.GuardaLocalFotoQJTask;
import Tasks.GuardarFotoRGTask;
import Tasks.InsertDocsQuejaClienteTask;
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
    ListView lvFotosQJ;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    DocsQuejaClienteAdapater docsAdapdater = null ;
    ArrayList<DocsQuejaCliente> listaDocsFotos = new ArrayList<>();

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

        chkInvestigando = findViewById(R.id.chkInvestigando);
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


        LoadSpinersMedioRecepcion();
        LoadSpinersClase();
        LoadSpinersProcede();
        LoadSpinersAcciones();

        if (AccionQJ.equals("NEW")) {
            objQueja = new QuejaCliente();
            if (getIntent().getExtras().getString("Cliente") != null){
                txtBSClienteQJ.setText(getIntent().getExtras().getString("Cliente"));
            }
        }else {
            Gson gson = new Gson();
            String qjAsString = getIntent().getStringExtra("ObjectQJ");
            objQueja  = gson.fromJson(qjAsString, QuejaCliente.class);
            SetControlValues(objQueja);
            docsAdapdater = FillAdaptadorDocs(AccionQJ,objQueja.getC_compania(), String.valueOf( objQueja.getN_correlativo()));
        }

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
                Log.i("Accion QJ" , AccionQJ);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_fotos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.AddFotos) {
            AlertAgregarFotos();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            String codigo = CodigoFoto();
            GuardarFoto(imageBitmap, codigo, "Local");
            SetAdapterDocsFoto(codigo);
            Log.i("paso ", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
    }
    public  void SetAdapterDocsFoto(String sCodFoto){
        DocsQuejaCliente d = new DocsQuejaCliente();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sUltFecha =    sdf.format(new Date());
        d.setD_ultimafechamodificacion(sUltFecha);
        d.setC_ultimousuario(codUser);
        d.setC_nombre_archivo(sCodFoto);
        d.setC_ruta_archivo(sCodFoto+".jpg");
        d.setC_compania(Constans.NroConpania);

        if (docsAdapdater==null) {

            docsAdapdater = new DocsQuejaClienteAdapater(DatosGenQueja.this, R.layout.item_fotos_reclamogar, listaDocsFotos);
            lvFotosQJ.setAdapter(docsAdapdater);
        }


        docsAdapdater.add(d);
        docsAdapdater.notifyDataSetChanged();
        //.setAdapter(adapter);
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
    public  void GuardarFoto(Bitmap bmp , String sCodFoto, String sTipoSincron){

        File DirExist =  new File(Environment.getExternalStorageDirectory(),  Constans.Carpeta_foto_QJ);
        if (!DirExist.exists()){
            DirExist.mkdir();
        }


        String filename =   Constans.Carpeta_foto_QJ+sCodFoto+".jpg";
        File sd = Environment.getExternalStorageDirectory();

        File dest = new File(sd, filename);
        Log.i("Destino",dest.getPath());
        Bitmap bitmap = bmp ;
        try {
            FileOutputStream out = new FileOutputStream(dest);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String CodigoFoto() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        String idImei = telephonyManager.getDeviceId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String sCodigoFoto =   idImei +"_"+  sdf.format(new Date());
        return sCodigoFoto ;
    }
    public void AlertAgregarFotos(){
        final Dialog dialog  = new Dialog(DatosGenQueja.this);
        dialog.setContentView(R.layout.dialog_list_fotos_rg);
        dialog.setTitle("Buscar Cliente");
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button btnAddFoto = dialog.findViewById(R.id.btnAddFotoRG);
        Button btnEliminar = dialog.findViewById(R.id.btnEliminarFotoRG);
        Button btnAceptarFotos = dialog.findViewById(R.id.btnAceptarFotoRG);
        FrameLayout frameLayout = dialog.findViewById(R.id.lyFramViewFoto);
        Button btnCerrarFoto = dialog.findViewById(R.id.btnCloseViewFoto);
        ImageView imageView = dialog.findViewById(R.id.ImageViewFoto);
        lvFotosQJ  = dialog.findViewById(R.id.lvFotosGuardadasRG);
        lvFotosQJ.setItemsCanFocus(true);
        if (docsAdapdater != null){
            if (docsAdapdater.getCount()>0) {
                lvFotosQJ.setAdapter(docsAdapdater);
            }
        }

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (docsAdapdater!=null && docsAdapdater.getCount()>0){
                    docsAdapdater.data.remove(docsAdapdater.data.size()-1);
                    docsAdapdater.notifyDataSetChanged();
                }
            }
        });

        //lvBuscarClientes.setAdapter(getAdapterBuscarCliente("%"));
        btnAddFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                //openBackCamera();
            }
        });

        btnAceptarFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public  void  SetControlValues ( QuejaCliente obQueja ){
        Log.i("Sub clase valor >>" ,  obQueja.getC_tipocalificacion());
        ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
        txtNroQueja.setText(obQueja.getC_queja());
        txtFechaRegQJ.setText(obQueja.getD_fechareg());
        txtBSClienteQJ.setText(String.valueOf(obQueja.getN_cliente()+ " | "+ db.GetNombreClienteXCod(String.valueOf(obQueja.getN_cliente()))));
        spClaseQJ.setSelection( getIndexSpiner(spClaseQJ,obQueja.getC_calificacion()),false);
        LoadSpinersSubClase(obQueja.getC_calificacion());
        spSubClaseQJ.setSelection( getIndexSpiner(spSubClaseQJ, obQueja.getC_tipocalificacion()));
        txtItemQJ.setText(obQueja.getC_item());
        txtSerieQJ.setText(obQueja.getC_procede());
        txtCantidadQJ.setText(String.valueOf(obQueja.getN_cantidad()));
        txtDocRefQJ.setText(obQueja.getC_documentoref());
        txtFormatoQJ.setText(obQueja.getC_nroformato());
        txtDetalleQJ.setText(obQueja.getC_desqueja());
        txtObservacionesQJ.setText(obQueja.getC_observaciones());
        spMedioRecepcionQJ.setSelection(getIndexSpiner(spMedioRecepcionQJ,obQueja.getC_mediorecepcion()));
        if (!TextUtils.isEmpty(obQueja.getC_flaginvestigando())){
            chkInvestigando.setChecked(   obQueja.getC_flaginvestigando().equals("S") ? true:false);
        }
        txtDescripcionInvestigacionQJ.setText(obQueja.getC_descripcioninvestigacion());
        if (!TextUtils.isEmpty(obQueja.getC_procede())) {
            spProcedeQJ.setSelection(obQueja.getC_procede().equals("S") ? 1 : 0);
        }
        txtFechaRespuestaQJ.setText( !TextUtils.isEmpty(obQueja.getD_fecharespuesta()) && obQueja.getD_fecharespuesta().substring(0,4).equals("1900")  ? "":obQueja.getD_fecharespuesta());
        txtFechaInvestigacionQJ.setText( !TextUtils.isEmpty(obQueja.getD_fechainvestigadopor()) && obQueja.getD_fechainvestigadopor().substring(0,4).equals("1900") ? "":obQueja.getD_fechainvestigadopor());
        txtInvestigadoPorQJ.setText(obQueja.getC_usuarioinvestigadopor());
        //Log.i("clase cierre" , obQueja.getC_tipocalificacioncierre());
        spSubClaseCierreQJ.setSelection(getIndexSpiner(spSubClaseCierreQJ, !TextUtils.isEmpty(obQueja.getC_tipocalificacioncierre()) ? obQueja.getC_tipocalificacioncierre().trim() : ""));
        txtDisposicionQJ.setText(obQueja.getC_descripcioncierre());
        spAccionTomar1QJ.setSelection(getIndexSpiner(spAccionTomar1QJ,!TextUtils.isEmpty(obQueja.getC_codaccion1()) ?  obQueja.getC_codaccion1().trim():""));
        spAccionTomar2QJ.setSelection(getIndexSpiner(spAccionTomar2QJ,!TextUtils.isEmpty(obQueja.getC_codaccion2()) ?  obQueja.getC_codaccion2().trim():""));
        spAccionTomar3QJ.setSelection(getIndexSpiner(spAccionTomar3QJ,!TextUtils.isEmpty(obQueja.getC_codaccion3()) ?  obQueja.getC_codaccion3().trim():""));
        spAccionTomar4QJ.setSelection(getIndexSpiner(spAccionTomar4QJ,!TextUtils.isEmpty(obQueja.getC_codaccion4()) ?  obQueja.getC_codaccion4().trim():""));

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

    public  DocsQuejaClienteAdapater  FillAdaptadorDocs(String sTipoOperacion,String sComp,String sCorrelativo){
        Log.i("operaciones  >>" , sTipoOperacion +" | " + sComp + " | "+ sCorrelativo );
        ArrayList<DocsQuejaCliente> listaFotos =  new ArrayList<>();
        if (sTipoOperacion.equals("VER")){
            AsyncTask<String,String,ArrayList<DocsQuejaCliente>> asyncTaskListaFotosQJ;
            GetListaDatosFotosQJTask getListaDatosFotosQJTask = new GetListaDatosFotosQJTask();
            try {
                asyncTaskListaFotosQJ = getListaDatosFotosQJTask.execute(sComp,sCorrelativo);
                listaFotos  = asyncTaskListaFotosQJ.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (listaFotos!=null && listaFotos.size()>0){
                for (int i = 0; i <  listaFotos.size(); i++) {
                    RecuperandoImagen(listaFotos.get(i).getC_ruta_archivo());
                }
            }
         //Log.i("valor adaptador" , String.valueOf( listaFotos.size()));
        }else  if (sTipoOperacion.equals("MOD")){

            ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
            listaFotos = db.GetListDocsQuejaCliente(sCorrelativo);

        }

        if (listaFotos == null){
            listaFotos =  new ArrayList<>();
        }


        docsAdapdater = new DocsQuejaClienteAdapater(DatosGenQueja.this, R.layout.item_fotos_reclamogar, listaFotos);
//
        return  docsAdapdater;

    }

    public void RecuperandoImagen(String filename) {
        //File filefoto = null;
        Bitmap bitmap = null;

        File fileexist = new File(Environment.getExternalStorageDirectory() +File.separator+Constans.Carpeta_foto_QJ + filename);
        if (fileexist.exists()){
            return;
        }

        AsyncTask<String, String, byte[]> asynckGetFoto;
        GetFotoRG getFotoTask = new GetFotoRG();
        byte[] bytes = null;
        try {
            asynckGetFoto = getFotoTask.execute(filename,"QJ");
            bytes = (byte[]) asynckGetFoto.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        AsyncTask<byte[], String, String> asyncSaveImglocal;
        GuardaLocalFotoQJTask guardarImgLocalTask = new GuardaLocalFotoQJTask(filename, bytes);

        try {
            asyncSaveImglocal = guardarImgLocalTask.execute(bytes);
            String resSaveImg = (String) asyncSaveImglocal.get();
            Log.i("result save imag local qj => ", resSaveImg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String filePath2 = Environment.getExternalStorageDirectory() +File.separator+Constans.Carpeta_foto_QJ + filename;
        bitmap = BitmapFactory.decodeFile(filePath2);


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
        btnSiguiente.setEnabled(true);
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
                btnSiguiente.setText("Finalizar");
                layCierreQJ.setVisibility(View.VISIBLE);
                btnSiguiente.setEnabled(AccionQJ.equals("VER") ? false:true);
                break;
        }
    }

    public  void ActualizarTablaLocalFotos(ArrayList<DocsQuejaCliente> listFotos){

        ProdMantDataBase  db = new ProdMantDataBase(getApplicationContext());
        for (int i = 0; i < listFotos.size() ; i++) {
            DocsQuejaCliente dc = listFotos.get(i);
            dc.setN_linea(i+1);
            long rowdoc =  db.InsertDocFotosQuejaCliente(dc);
            Log.i("id row doc qj" ,String.valueOf(rowdoc));
        }

    }

    public  void  GuardarQueja(String sTipoGuardado){
        long rowid = 0 ;
        ProdMantDataBase  db =  new ProdMantDataBase(DatosGenQueja.this);
        objQueja.setC_enviado(sTipoGuardado.equals("Local")? "N":"S");
        if (AccionQJ.equals("NEW")) {
            rowid = db.InsertQuejaCliente(objQueja);
        }
        else if (AccionQJ.equals("MOD")){
            rowid = db.UpdareQuejaCliente(objQueja);
            rowid  = objQueja.getN_correlativo();
        }

        if (rowid>0){
            if (docsAdapdater!=null) {
                docsAdapdater.SetNumero((int) rowid);
                db.DeleteDocsQuejaCliente(String.valueOf(objQueja.getN_correlativo()));
                ActualizarTablaLocalFotos(docsAdapdater.AllItems());
            }
            if (sTipoGuardado.equals("Local"))
                FabToast.makeText(DatosGenQueja.this, "Se guardo correctamente la informacion", FabToast.LENGTH_LONG, FabToast.SUCCESS,  FabToast.POSITION_DEFAULT).show();
            Log.i("row id queja cli " , String.valueOf(rowid));
            super.onBackPressed();
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
                if (docsAdapdater!=null) {
                    docsAdapdater.SetNumero(Integer.valueOf(sCorrelativoQJ));
                    ActualizarTablaServerFotos(docsAdapdater.AllItems());
                }
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
                    if (docsAdapdater!=null && docsAdapdater.getCount()>0){
                        GuardarFotoEnServer(docsAdapdater.AllItems());
                    }
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

    public  void  GuardarFotoEnServer(ArrayList<DocsQuejaCliente> listDocsF)
    {
        String fileCarp = Environment.getExternalStorageDirectory() +File.separator +Constans.Carpeta_foto_QJ;
        if (listDocsF!=null && listDocsF.size()>0) {
            for (int i = 0; i < listDocsF.size(); i++) {
                Log.i("ruta foto rg  save server " , listDocsF.get(i).getC_ruta_archivo());
                String fileName = listDocsF.get(i).getC_ruta_archivo();
                String filePath = fileCarp + fileName;
                File file = new File(filePath);

                byte[] bytes = new byte[(int) file.length()];
                try {
                    bytes = FileUtils.readFileToByteArray(file);
                    AsyncTask asyncTask = null;
                    GuardarFotoRGTask guardarImagenTask = new GuardarFotoRGTask(bytes, listDocsF.get(i).getC_ruta_archivo(),"QJ");
                    //Log.i("Parmaetro exex GuardarTask ===>", listDet.get(i).getRutaFoto());
                    asyncTask = guardarImagenTask.execute();
                    String resp = (String) asyncTask.get();
                    Log.i("foto server guardada rg ===>", resp);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
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

        }
        else {

        }
    }

    public  void  ActualizarTablaServerFotos(ArrayList<DocsQuejaCliente> listDocFotos){
        String ressultDocInsert  = "";
        for (int i = 0; i < listDocFotos.size() ; i++) {
            DocsQuejaCliente d =  listDocFotos.get(i);
            AsyncTask<String,String,String> asyncTaskDocFotoQJ;
            InsertDocsQuejaClienteTask insertDocsQuejaClienteTask = new InsertDocsQuejaClienteTask();
            try {
                asyncTaskDocFotoQJ  = insertDocsQuejaClienteTask.execute(d.getC_compania(),String.valueOf(d.getN_queja()),String.valueOf(i+1),d.getC_descripcion(),d.getC_nombre_archivo(),d.getC_ruta_archivo(),codUser);
                ressultDocInsert = asyncTaskDocFotoQJ.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }


    }

    public void LoadSpinersClase(){
        ProdMantDataBase db = new ProdMantDataBase(DatosGenQueja.this);
        ArrayList<TMACalificacionQueja> oLista = db.GetClase();
        ArrayList<String> spData = new ArrayList<>();
        spData.add("Seleccione Clase......|");
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
        Clase=Clase.trim();
        ProdMantDataBase db = new ProdMantDataBase(DatosGenQueja.this);
        ArrayList<TMATipoCalificacionQueja> oLista = db.GetSubClase(Clase);
        ArrayList<String> spData = new ArrayList<>();
        spData.add("Seleccione SubClase......|");
        if (oLista.size()>0){
            for (int i=0;i<oLista.size();i++) {
                spData.add(oLista.get(i).getC_tipocalificacion().trim()+ " | " +oLista.get(i).getC_descripcion().trim());
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
        spData.add("Seleccione Medio Recepcion.....|");
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
        spData.add("Seleccione Accion......|");
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

    private int getIndexSpiner(Spinner spinner, String Cod)
    {
        Log.i("Cod Titpo QJ" , Cod);
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            String val = spinner.getItemAtPosition(i).toString().trim();
            val = val.substring(0,val.indexOf("|")-1);
            Log.i("val sp " , val );
            if (val.equals(Cod)){
                index = i;
                break;
            }
        }
        return index;
    }


    public class DocsQuejaClienteAdapater extends ArrayAdapter<DocsQuejaCliente> {
        private Context context ;
        private ArrayList<DocsQuejaCliente> data ;
        private int layoutResourceId;
        HashMap<Integer,String> hashDescrip;


        public DocsQuejaClienteAdapater(@NonNull Context context, int resource, ArrayList<DocsQuejaCliente> data) {
            super(context,resource,data);
            this.context = context;
            this.data = data;
            this.layoutResourceId = resource;
            hashDescrip = new HashMap<>();
        }


        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            ViewHolder viewHolder ;
            DocsQuejaCliente doc = data.get(position);
            if (convertView == null){
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                convertView = inflater.inflate(layoutResourceId,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.txtDescripcion = convertView.findViewById(R.id.txtDescripFotoRG);
                viewHolder.lblNombreFoto = convertView.findViewById(R.id.lblNombrFotoRG);
                viewHolder.btnVer = convertView.findViewById(R.id.btnVerFotoItemRG);
                convertView.setTag(viewHolder);
            }
            else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

//            Log.i("get descripcion foto", data.get(position).getC_nombre_archivo());
            viewHolder.lblNombreFoto.setText(doc.getC_nombre_archivo());
            viewHolder.txtDescripcion.setText(data.get(position).getC_descripcion());



            viewHolder.txtDescripcion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertEdittextListView(position,(EditText)v);
                }
            });



            viewHolder.btnVer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VisorImagen(data.get(position).getC_ruta_archivo());
                    // lvFotosRG.setVisibility(View.GONE);
                }
            });

            return  convertView;

        }

        public ArrayList<DocsQuejaCliente> AllItems () {
            return data ;
        }

        public  void SetNumero(int n){
            for (int i = 0; i < data.size() ; i++) {
                data.get(i).setN_queja(n);
            }
        }
    }

    public static class ViewHolder{
        TextView lblNombreFoto;
        EditText txtDescripcion;
        Button btnVer ;
    }


    public  void  VisorImagen (String path ){

        String filePath2 = Environment.getExternalStorageDirectory() + File.separator + Constans.Carpeta_foto_QJ+ path;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath2);
        Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }
        });
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bitmap);
        builder.addContentView(imageView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    public void AlertEdittextListView(final int positionObj, final EditText txtDescp){
        final EditText editText = new EditText(DatosGenQueja.this);
        final AlertDialog dialog = new AlertDialog.Builder(DatosGenQueja.this)
                .setMessage("Descripcion de foto")
                .setTitle("Escribir texto")
                .setView(editText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        docsAdapdater.data.get(positionObj).setC_descripcion(editText.getText().toString());
                        txtDescp.setText(editText.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .create();

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        dialog.show();

    }
}
