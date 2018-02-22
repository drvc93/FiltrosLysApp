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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import DataBase.ProdMantDataBase;
import Model.DocsReclamoGarantia;
import Model.EFacSerLot;
import Model.ReclamoGarantia;
import Model.TMACliente;
import Model.TMAFalla;
import Model.TMAMarca;
import Model.TMAModelo;
import Model.TMAPruebaLab;
import Model.TMATipoReclamo;
import Model.TMAVendedor;
import Tasks.GetCorrelativoRecGarTask;
import Tasks.GetFotoRG;
import Tasks.GetListaDatosFotosRG;
import Tasks.GetLoteSerieRGTask;
import Tasks.GuardaLocalFotoRGTask;
import Tasks.GuardarFotoRGTask;
import Tasks.InsertDocsReclamoGarTask;
import Tasks.InsertReclamoGarantiaTask;
import Tasks.TransferirReclamoGarantiaTask;
import Tasks.VerificarItemExisteTask;
import Util.Constans;
import br.com.sapereaude.maskedEditText.MaskedEditText;
import libs.mjn.prettydialog.PrettyDialog;
import spencerstudios.com.fab_toast.FabToast;


public class DatosGenRG extends AppCompatActivity {
    EditText txtNroFormato, txtNroRG, txtFechaRegistro, txtClienteCodNom, txtFiltroBuscar;
    EditText txtFacturaRG, txtLoteF, txtSerieF, txtEstado;
    EditText txtPlaca, txtAnio, txtTiempoUso, txtObsCliente;
    Spinner spTipoRG, spMarca, spModelo;
    Spinner spPrueba1, spPrueba2, spPrueba3;
    Spinner spFalla1, spFalla2, spFalla3, spFalla4, spFalla5, spVendedorDiag;
    Spinner spMonedaRemb, spReembolso;
    EditText txtMontoRemb, txtObsVendedor;
    CheckBox chkNecVisita;
    int PantallaReal;
    TextView lblav1, lblav2, lblav3, lblav4;
    ImageView btnBuscarCliente, btnBuscarFiltro;
    MaskedEditText txtCantGen, txtLote1, txtLote2, txtLote3, txtCantLote1, txtCantLote2, txtCantLote3;
    ReclamoGarantia objReclamo;
    Button btnSiguiente, btnCancelar;
    LinearLayout ly_Datos_Generales, ly_Datos_Vehiculo, ly_Datos_Pruebas, ly_Datos_Diagnostico;
    int nIndicadorLayout;
    String codUser, sFechaSelec, sOperacion;
    SharedPreferences preferences;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    DocsReclamoGarantiaAdapter docsAdapdater = null ;
    ArrayList<DocsReclamoGarantia> listaDocsFotos = new ArrayList<>();
    ListView lvFotosRG;
    FrameLayout frameViewFotos;
    ImageView imgviewFoto;
    String pictureImagePath = "" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_gen_rg);
        setTitle("Datos Generales");
        preferences = PreferenceManager.getDefaultSharedPreferences(DatosGenRG.this);
        codUser = preferences.getString("UserCod", null);
        btnCancelar = findViewById(R.id.btnCancelRegRG);
        btnSiguiente = findViewById(R.id.btnSigDatosGRG);
        ly_Datos_Generales = findViewById(R.id.layoutDatosGen);
        ly_Datos_Vehiculo = findViewById(R.id.layoutDatosVehiculos);
        ly_Datos_Pruebas = findViewById(R.id.layoutDatosPruebas);
        ly_Datos_Diagnostico = findViewById(R.id.layoutDatosDiagnost);
        lblav1 = findViewById(R.id.lblAvance1);
        lblav2 = findViewById(R.id.lblAvance2);
        lblav3 = findViewById(R.id.lblAvance3);
        lblav4 = findViewById(R.id.lblAvance4);
        nIndicadorLayout = 1;
        ConfigurarPantalla(nIndicadorLayout, false);

        sOperacion = getIntent().getStringExtra("Operacion");
        txtFechaRegistro = findViewById(R.id.txtFechaRegRG);
        txtNroFormato = findViewById(R.id.txtNroFormtRG);
        txtNroRG = findViewById(R.id.txtNroRecGar);
        spTipoRG = findViewById(R.id.spTipoRG);
        txtClienteCodNom = findViewById(R.id.txtBSClienteRG);
        txtLoteF = findViewById(R.id.txtLoteRG);
        txtSerieF = findViewById(R.id.txtSerieFiltroRG);
        txtClienteCodNom.setEnabled(false);
        btnBuscarCliente = findViewById(R.id.btnBuscarClienteRG);
        txtFiltroBuscar = findViewById(R.id.txtBFiltroRG);
        btnBuscarFiltro = findViewById(R.id.btnBuscarFiltroRG);
        txtNroFormato.setInputType(InputType.TYPE_CLASS_NUMBER);
        txtFacturaRG = findViewById(R.id.txtFactRG);
        txtEstado = findViewById(R.id.txtEstadoRG);
        txtCantGen = findViewById(R.id.txtCantidadGenRG);
        txtCantLote1 = findViewById(R.id.txtCantLote1);
        txtCantLote2 = findViewById(R.id.txtCantLote2);
        txtCantLote3 = findViewById(R.id.txtCantLote3);
        txtCantLote1.setText("0");
        txtCantLote2.setText("0");
        txtCantLote3.setText("0");
        txtLote1 = findViewById(R.id.txtLoteProd1);
        txtLote2 = findViewById(R.id.txtLoteProd2);
        txtLote3 = findViewById(R.id.txtLoteProd3);
        PantallaReal = 1;
        spMarca = findViewById(R.id.spMarcaRG);
        spModelo = findViewById(R.id.spModeloVRG);
        txtAnio = findViewById(R.id.txtAnioVRG);
        txtPlaca = findViewById(R.id.txtPlacaVRG);
        txtObsCliente = findViewById(R.id.txtObsClienteRG);
        txtTiempoUso = findViewById(R.id.txtTiempoUso);
        spPrueba1 = findViewById(R.id.spPrueba01);
        spPrueba2 = findViewById(R.id.spPrueba02);
        spPrueba3 = findViewById(R.id.spPrueba03);
        spFalla1 = findViewById(R.id.spFalla01);
        spFalla2 = findViewById(R.id.spFalla02);
        spFalla3 = findViewById(R.id.spFalla03);
        spFalla4 = findViewById(R.id.spFalla04);
        spFalla5 = findViewById(R.id.spFalla05);
        spVendedorDiag = findViewById(R.id.spVendedorDiag);
        spMonedaRemb = findViewById(R.id.spMonedaDiag);
        spReembolso = findViewById(R.id.spRembolsoDiag);
        txtMontoRemb = findViewById(R.id.txtMontoDIAG);
        txtObsVendedor = findViewById(R.id.txtObsVendedor);
        chkNecVisita = findViewById(R.id.chkNecesitaVIisita);
        spMonedaRemb.setEnabled(false);
        txtMontoRemb.setEnabled(true);
        txtFiltroBuscar.setFilters(new InputFilter[]{new InputFilter.AllCaps()});


        LoadSpinerTipoReclamo();
        LoadSpinerMarca();
        LoadSpinersPruebas();
        LoadSpinersFalla();
        LoadSpinerVendedor();
        LoadSpinerMoneda();
        LoadSpinerRembolso();

        if (sOperacion.equals("NEW")) {
            txtEstado.setText("Ingresado");
            objReclamo = new ReclamoGarantia();
            if (getIntent().getStringExtra("Cliente") != null) {
                txtClienteCodNom.setText(getIntent().getStringExtra("Cliente"));
            }
        } else if (sOperacion.equals("MOD") || sOperacion.equals("VER")) {
            Gson gson = new Gson();
            String rgAsString = getIntent().getStringExtra("ObjectRG");
            objReclamo = gson.fromJson(rgAsString, ReclamoGarantia.class);
            SetControlsValues(objReclamo);
            docsAdapdater = FillAdaptadorDocs(sOperacion,objReclamo.getC_compania(), String.valueOf( objReclamo.getN_correlativo()));
        }

        spMarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String marca = spMarca.getSelectedItem().toString().trim();
                    marca = marca.substring(0, marca.indexOf("|") - 1).trim();
                    LoadSpinerModeo(marca);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spReembolso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spReembolso.getSelectedItem().toString().equals("SI")) {
                    txtMontoRemb.setEnabled(true);
                    spMonedaRemb.setEnabled(true);
                } else {
                    txtMontoRemb.setEnabled(false);
                    spMonedaRemb.setEnabled(false);
                    spMonedaRemb.setSelection(0);
                    txtMontoRemb.setText("");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nIndicadorLayout > 1) {
                    nIndicadorLayout = nIndicadorLayout - 1;
                    ConfigurarPantalla(nIndicadorLayout, false);
                } else {
                    DatosGenRG.super.onBackPressed();
                }
            }
        });
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("index layout ", String.valueOf(nIndicadorLayout));
                if (nIndicadorLayout < 4) {
                    nIndicadorLayout = nIndicadorLayout + 1;
                    ConfigurarPantalla(nIndicadorLayout, true);
                } else if (nIndicadorLayout == 4) {
                    if (!sOperacion.equals("VER")) {
                        VaLidacionFinal();
                    }

                }
            }
        });

        btnBuscarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogBuscarCliente();
            }
        });

        txtFechaRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelecFecha(txtFechaRegistro);
            }
        });

        txtFacturaRG.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String sFac = "";
                    sFac = txtFacturaRG.getText().toString().toUpperCase();
                    if (sFac.isEmpty() || txtClienteCodNom.getText().toString().isEmpty()) {
                        FabToast.makeText(DatosGenRG.this, "Se recomienda ingresar cliente primero.", FabToast.LENGTH_LONG, FabToast.WARNING, FabToast.POSITION_DEFAULT).show();
                        return;
                    }
                    ObtenerLoteSerieFiltro("F", txtFiltroBuscar.getText().toString().toUpperCase(), sFac);
                }
            }
        });

        txtFiltroBuscar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    VerificarItemExiste(txtFiltroBuscar.getText().toString());
                }
            }
        });

        txtCantLote1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    SumaCantidadesLotes();
                }
            }
        });
        txtCantLote2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    SumaCantidadesLotes();
                }
            }
        });
        txtCantLote3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    SumaCantidadesLotes();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        if (nIndicadorLayout > 1) {
            nIndicadorLayout = nIndicadorLayout - 1;
            ConfigurarPantalla(nIndicadorLayout, false);
        } else {
            return;
        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
             Bitmap imageBitmap = (Bitmap) extras.get("data");
             String codigo = CodigoFoto() ;
             GuardarFoto(imageBitmap,codigo,"Local");
             SetAdapterDocsFoto(codigo);
             Log.i("paso " , ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        /* if (requestCode == 1) {
            File imgFile = new  File(pictureImagePath);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                String codigo = CodigoFoto() ;
                GuardarFoto(myBitmap,codigo,"Local");
                SetAdapterDocsFoto(codigo);

            }
        }*/
    }

    public  void SetAdapterDocsFoto(String sCodFoto){
        DocsReclamoGarantia d = new DocsReclamoGarantia();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sUltFecha =    sdf.format(new Date());
        d.setD_ultimafechamodificacion(sUltFecha);
        d.setC_ultimousuario(codUser);
        d.setC_nombre_archivo(sCodFoto);
        d.setC_ruta_archivo(sCodFoto+".jpg");
        d.setC_compania(Constans.NroConpania);
        if (docsAdapdater==null) {

            docsAdapdater = new DocsReclamoGarantiaAdapter(DatosGenRG.this, R.layout.item_fotos_reclamogar, listaDocsFotos);
            lvFotosRG.setAdapter(docsAdapdater);
        }


        docsAdapdater.add(d);
        docsAdapdater.notifyDataSetChanged();
        //.setAdapter(adapter);
    }
    public  void GuardarFoto(Bitmap bmp , String sCodFoto, String sTipoSincron){

        String filename =   Constans.Carpeta_foto_RG+sCodFoto+".jpg";
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

    public void RecuperandoImagen(String filename) {
        //File filefoto = null;
        Bitmap bitmap = null;

        File fileexist = new File(Environment.getExternalStorageDirectory() +File.separator+Constans.Carpeta_foto_RG + filename);
        if (fileexist.exists()){
            return;
        }

            AsyncTask<String, String, byte[]> asynckGetFoto;
            GetFotoRG getFotoTask = new GetFotoRG();
            byte[] bytes = null;
            try {
                asynckGetFoto = getFotoTask.execute(filename,"RG");
                bytes = (byte[]) asynckGetFoto.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            AsyncTask<byte[], String, String> asyncSaveImglocal;
            GuardaLocalFotoRGTask guardarImgLocalTask = new GuardaLocalFotoRGTask(filename, bytes);

            try {
                asyncSaveImglocal = guardarImgLocalTask.execute(bytes);
                String resSaveImg = (String) asyncSaveImglocal.get();
                Log.i("result save imag local => ", resSaveImg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            String filePath2 = Environment.getExternalStorageDirectory() +File.separator+Constans.Carpeta_foto_RG + filename;
            bitmap = BitmapFactory.decodeFile(filePath2);


        }



    public  void  GuardarFotoEnServer(ArrayList<DocsReclamoGarantia> listDocsF)
    {
        String fileCarp = Environment.getExternalStorageDirectory() +File.separator +Constans.Carpeta_foto_RG;
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
                    GuardarFotoRGTask guardarImagenTask = new GuardarFotoRGTask(bytes, listDocsF.get(i).getC_ruta_archivo(),"RG");
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

    public  void ActualizarTablaLocalFotos(ArrayList<DocsReclamoGarantia> listFotos){

        ProdMantDataBase  db = new ProdMantDataBase(getApplicationContext());
        for (int i = 0; i < listFotos.size() ; i++) {
            DocsReclamoGarantia dc = listFotos.get(i);
            dc.setN_linea(i+1);
            long rowdoc =  db.InsertDocFotosReclamoGarantia(dc);
            Log.i("id row doc" ,String.valueOf(rowdoc));
        }

    }

    public  void  ActualizarTablaServerFotos(ArrayList<DocsReclamoGarantia> listDocFotos){
        String ressultDocInsert  = "";
        for (int i = 0; i < listDocFotos.size() ; i++) {
            DocsReclamoGarantia d =  listDocFotos.get(i);
            AsyncTask<String,String,String> asyncTaskDocFoto;
            InsertDocsReclamoGarTask insertDocsReclamoGarTask = new InsertDocsReclamoGarTask();
           try {
               asyncTaskDocFoto  = insertDocsReclamoGarTask.execute(d.getC_compania(),String.valueOf(d.getN_numero()),String.valueOf(i+1),d.getC_descripcion(),d.getC_nombre_archivo(),d.getC_ruta_archivo(),codUser);
               ressultDocInsert = asyncTaskDocFoto.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
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

    public  void  VaLidacionFinal (){
         String sVendedor ,sObsVendedor,sRembolso,sMoneda,sMonto,sFlagNecsVisita;
        sVendedor = "";

        sObsVendedor = txtObsVendedor.getText().toString().trim();
        sMonto = txtMontoRemb.getText().toString().trim();
        sMoneda = spMonedaRemb.getSelectedItem().toString();
        sRembolso = spReembolso.getSelectedItem().toString();

        if (sMonto.isEmpty()){
            sMonto = "0";
        }

        if (spVendedorDiag.getSelectedItemPosition()>0) {
            sVendedor =spVendedorDiag.getSelectedItem().toString();
            sVendedor = sVendedor.substring(0,sVendedor.indexOf("|")-1).trim();
        }
        else  if (spVendedorDiag.getSelectedItemPosition()<=0){
            ShowMessageWindow("Debe seleccionar vendedor.");
            return;
        }
        if (sObsVendedor.isEmpty()){
            ShowMessageWindow("Debe ingresar una observacion de diagnostico.");
            return;
        }

        if (chkNecVisita.isChecked()) {
            sFlagNecsVisita = "S";
        }
        else {
            sFlagNecsVisita = "N";
        }
        if (sRembolso.equals("SI")){
            if (Double.valueOf(sMonto)<=0){
                ShowMessageWindow("Debe ingresar un monto de reembolso valido.");
                return;
            }
            if (spMonedaRemb.getSelectedItemPosition()==0){
                ShowMessageWindow("Debe seleccionar el tipo de moneda.");
                return;
            }
        }

        if (spMonedaRemb.getSelectedItem().toString().equals("Soles")){
            sMoneda = "L";
        }
        else  {
            sMoneda = "E";
        }
        objReclamo.setC_vendedor(sVendedor);
        objReclamo.setC_reembcliente(sRembolso.substring(0,1));
        objReclamo.setC_monedareembcli(sMoneda);
        objReclamo.setN_montoreembcli(Double.valueOf(sMonto));
        objReclamo.setC_flagvisita(sFlagNecsVisita);
        objReclamo.setC_prediagnostico(sObsVendedor);
        objReclamo.setC_usuario(codUser);
        objReclamo.setC_enviado("N");
        SeleccionarFormaGuardado();
    }


    public void   SeleccionarFormaGuardado(){

        final CharSequence[] items = { "Solo guardar en movil", "Enviar a Servidor", "Cancelar" };

        AlertDialog.Builder builder = new AlertDialog.Builder(DatosGenRG.this);
        builder.setTitle("Seleccionar opcion : ");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

               switch (item) {
                   case  0 :
                       GuardarReclamoG("Local");
                       break;
                   case 1:
                       GuardarReclamoG("Servidor");
                       break;
                   case 2:
                       dialog.dismiss();
               }

            }
        }).show();
    }

    public  void  ConfigurarPantalla (int nIndicadorPantlla, boolean validar){
        Log.i("pantalla real", String.valueOf(PantallaReal));
        if (validar && !sOperacion.equals("VER")){
            //Layout 1
            String nroFormato , sFechaIngreso, sTipoReclamo,sCliente,sFiltro,sLoteFiltro,sSerieFiltro,sFactura;
            String sCantidadGnrl  , sEstado,sLoteProd1,sLoteProd2,sLoteProd3,sCant1,sCant2,sCant3;
            //Layout 2
            String sMarca ,sModelo , sAnio , sPlaca, sTiempoUso,sObservacionCliente;
            // Layout 3
           String sPrueba1 , sPrueba2,sPrueba3,sFalla1,sFalla2,sFalla3,sFalla4,sFalla5;

            if(PantallaReal==1)
            {
                objReclamo.setC_compania(Constans.NroConpania);
                nroFormato= txtNroFormato.getText().toString().trim();
                sFechaIngreso = sFechaSelec;
                sTipoReclamo = spTipoRG.getSelectedItem().toString();
                sTipoReclamo = sTipoReclamo.substring(0,sTipoReclamo.indexOf("|")-1).trim();
                sCliente=txtClienteCodNom.getText().toString().trim();
                sCliente = sCliente.indexOf("|")>0? sCliente.substring(0,sCliente.indexOf("|")-1).trim():"0";
                sFiltro = txtFiltroBuscar.getText().toString().trim();
                sLoteFiltro = txtLoteF.getText().toString().trim();
                sSerieFiltro = txtSerieF.getText().toString().trim();
                sFactura = txtFacturaRG.getText().toString().trim();
                sCantidadGnrl = txtCantGen.getText().toString();
                sLoteProd1 = txtLote1.getText().toString();
                sLoteProd2 = txtLote2.getText().toString();
                sLoteProd3 = txtLote3.getText().toString();
                sCant1 = txtCantLote1.getText().toString();
                sCant2  = txtCantLote2.getText().toString();
                sCant3 = txtCantLote3.getText().toString();
                sEstado = txtEstado.getText().toString();

                Log.i("tipo reclamo" , sTipoReclamo);
                Log.i("cliente" , sCliente);
                Log.i("perido lote1"  ,sLoteProd1);
                    if (TextUtils.isEmpty(nroFormato)) {
                        ShowMessageWindow("Debe ingresar número  de formato.");
                        nIndicadorLayout = nIndicadorLayout -1;
                        return;
                    }
                    else if (TextUtils.isEmpty(sFechaIngreso)){
                        ShowMessageWindow("Debe registrar la fecha de ingreso.");
                        nIndicadorLayout = nIndicadorLayout -1;
                        return;
                    }
                    else if (sTipoReclamo.isEmpty()){
                        ShowMessageWindow("Debe seleccionar un tipo de reclamo.");
                        nIndicadorLayout = nIndicadorLayout -1;
                        return;
                    }
                    else if (sCliente.isEmpty() || sCliente.equals("0")){
                        ShowMessageWindow("Debe seleccionar un cliente.");
                        nIndicadorLayout = nIndicadorLayout -1;
                        return;
                    }
                    else if (sFiltro.isEmpty()){
                        ShowMessageWindow("Debe ingresar un codigo de filtro.");
                        nIndicadorLayout = nIndicadorLayout -1;
                        return;
                    }
                    else if (sLoteFiltro.isEmpty()){
                        ShowMessageWindow("Lote de filtro invalido.");
                        nIndicadorLayout = nIndicadorLayout -1;
                        return;
                    }
                    else if (sSerieFiltro.isEmpty()){
                        ShowMessageWindow("Serie de filtro invalido.");
                        nIndicadorLayout = nIndicadorLayout -1;
                        return;
                    }
                    else if (sFactura.isEmpty()){
                        ShowMessageWindow("Debe ingresar Factura.");
                        nIndicadorLayout = nIndicadorLayout -1;
                        return;
                    }
                    else if (sCantidadGnrl.isEmpty() || sCantidadGnrl.equals("0")){
                        ShowMessageWindow("Cantidad Invalida.");
                        nIndicadorLayout = nIndicadorLayout -1;
                        return;
                    }

                    objReclamo.setN_formato(Integer.valueOf(nroFormato));
                    objReclamo.setD_fechaformato(sFechaIngreso);
                    objReclamo.setC_tiporeclamo(sTipoReclamo);
                    objReclamo.setN_cliente(Integer.valueOf(sCliente));
                    objReclamo.setC_codproducto(sFiltro);
                    objReclamo.setC_lote(sLoteFiltro);
                    objReclamo.setC_procedencia(sSerieFiltro);
                    objReclamo.setC_facturaRef(sFactura);
                    objReclamo.setN_qtyingreso(Double.valueOf(sCantidadGnrl));
                    objReclamo.setC_estado(CodEstado(sEstado));
                    objReclamo.setC_lote1(sLoteProd1);
                    objReclamo.setC_lote2(sLoteProd2);
                    objReclamo.setC_lote3(sLoteProd3);
                    objReclamo.setN_cantlote1(Double.valueOf(sCant1));
                    objReclamo.setN_cantlote2(Double.valueOf(sCant2));
                    objReclamo.setN_cantlote3(Double.valueOf(sCant3));

            }
            if(PantallaReal==2) {
                sMarca="";
                sModelo="";
                sPlaca = txtPlaca.getText().toString().trim().toUpperCase();
                sAnio = txtAnio.getText().toString().trim();
                sObservacionCliente = txtObsCliente.getText().toString().trim().toUpperCase();
                sTiempoUso = txtTiempoUso.getText().toString().trim();
                if (spMarca.getSelectedItemPosition()>0){
                    sMarca = spMarca.getSelectedItem().toString();
                    sMarca = sMarca.substring(0,sMarca.indexOf("|")-1).trim();
                }
                if (spModelo.getSelectedItemPosition()>0) {
                    sModelo = spModelo.getSelectedItem().toString();
                    sModelo = sModelo.substring(0, sModelo.indexOf("|") - 1).trim();
                }


                if (sMarca.isEmpty()){
                    ShowMessageWindow("Debe seleccionar una marca.");
                    nIndicadorLayout = nIndicadorLayout -1;
                    return;
                }
                else if (sModelo.isEmpty()){
                    ShowMessageWindow("Debe seleccionar un modelo.");
                    nIndicadorLayout = nIndicadorLayout -1;
                    return;
                }
                else if (sAnio.isEmpty()){
                    ShowMessageWindow("Debe  ingresar el año del vehiculo.");
                    nIndicadorLayout = nIndicadorLayout -1;
                    return;
                }
                else if (sTiempoUso.isEmpty()){
                    ShowMessageWindow("Debe ingresar el tiempo del uso.");
                    nIndicadorLayout = nIndicadorLayout -1;
                    return;
                }
                else if (sObservacionCliente.isEmpty()){
                    ShowMessageWindow("Debe ingresar un observacion del cliente.");
                    nIndicadorLayout = nIndicadorLayout -1;
                    return;
                }

                objReclamo.setC_codmarca(sMarca);
                objReclamo.setC_codmodelo(sModelo);
                objReclamo.setN_pyear(Integer.valueOf(sAnio));
                objReclamo.setC_placavehic(sPlaca);
                objReclamo.setC_tiempouso(sTiempoUso);
                objReclamo.setC_obscliente(sObservacionCliente);

            }
            if(PantallaReal ==3) {

                sFalla1="";sFalla2="";sFalla3="";
                sFalla4="";sFalla5="";sPrueba1="";sPrueba2="";sPrueba3="";

               /* if (spFalla1.getSelectedItemPosition()==0 && spFalla2.getSelectedItemPosition()==0 && spFalla3.getSelectedItemPosition()==0  && spFalla4.getSelectedItemPosition()==0  && spFalla5.getSelectedItemPosition()==0    ){
                 ShowMessageWindow("Debe seleccioanr al menos un tipo de falla.");
                 nIndicadorLayout = nIndicadorLayout -1;
                 return;
                }*/
                if (spFalla1.getSelectedItemPosition()>0) {
                    sFalla1 = spFalla1.getSelectedItem().toString();
                    sFalla1 = sFalla1.substring(0, sFalla1.indexOf("|") - 1).trim();
                }
                if (spFalla2.getSelectedItemPosition()>0) {
                    sFalla2 = spFalla2.getSelectedItem().toString();
                    sFalla2 = sFalla2.substring(0,sFalla2.indexOf("|")-1).trim();
                }
                if (spFalla3.getSelectedItemPosition()>0) {
                    sFalla3 = spFalla3.getSelectedItem().toString();
                    sFalla3 = sFalla3.substring(0,sFalla3.indexOf("|")-1).trim();
                }
                if (spFalla4.getSelectedItemPosition()>0) {
                    sFalla4 = spFalla4.getSelectedItem().toString();
                    sFalla4 = sFalla4.substring(0,sFalla4.indexOf("|")-1).trim();
                }
                if (spFalla5.getSelectedItemPosition()>0) {
                    sFalla5 = spFalla5.getSelectedItem().toString();
                    sFalla5 = sFalla5.substring(0,sFalla5.indexOf("|")-1).trim();
                }
                if (spPrueba1.getSelectedItemPosition()>0) {
                    sPrueba1 = spPrueba1.getSelectedItem().toString();
                    sPrueba1 = sPrueba1.substring(0,sPrueba1.indexOf("|")-1).trim();
                }
                if (spPrueba2.getSelectedItemPosition()>0) {
                    sPrueba2 = spPrueba2.getSelectedItem().toString();
                    sPrueba2 = sPrueba2.substring(0,sPrueba2.indexOf("|")-1).trim();
                }
                if (spPrueba3.getSelectedItemPosition()>0) {
                    sPrueba3 = spPrueba3.getSelectedItem().toString();
                    sPrueba3 = sPrueba3.substring(0,sPrueba3.indexOf("|")-1).trim();
                }

                objReclamo.setC_pruebalab1(sPrueba1);
                objReclamo.setC_pruebalab2(sPrueba2);
                objReclamo.setC_pruebalab3(sPrueba3);
                objReclamo.setC_ensayo01(sFalla1);
                objReclamo.setC_ensayo02(sFalla2);
                objReclamo.setC_ensayo03(sFalla3);
                objReclamo.setC_ensayo04(sFalla4);
                objReclamo.setC_ensayo05(sFalla5);

            }




        }
        ly_Datos_Vehiculo.setVisibility(View.GONE);
        ly_Datos_Generales.setVisibility(View.GONE);
        ly_Datos_Pruebas.setVisibility(View.GONE);
        ly_Datos_Diagnostico.setVisibility(View.GONE);
        setAvance(nIndicadorPantlla);
        PantallaReal=nIndicadorPantlla;
        btnSiguiente.setEnabled(true);
        switch (nIndicadorPantlla){
            case 1:
                btnCancelar.setText("Cancelar");
                btnSiguiente.setText("Siguiente");
                ly_Datos_Generales.setVisibility(View.VISIBLE);
                break;
            case 2:
                btnCancelar.setText("Volver");
                btnSiguiente.setText("Siguiente");
                ly_Datos_Vehiculo.setVisibility(View.VISIBLE);
                break;
            case 3:
                btnCancelar.setText("Volver");
                btnSiguiente.setText("Siguiente");
                ly_Datos_Pruebas.setVisibility(View.VISIBLE);
                break;
            case 4:
                if (sOperacion.equals("VER")){
                    btnSiguiente.setEnabled(false);
                }
                btnCancelar.setText("Volver");
                btnSiguiente.setText("Finalizar");
                ly_Datos_Diagnostico.setVisibility(View.VISIBLE);
                break;
        }
    }





    public  void  GuardarReclamoG(String sTipoGuardado){
        Log.i("sOpera guardar " , sOperacion );
        long rowid = 0;
        ProdMantDataBase  db =  new ProdMantDataBase(DatosGenRG.this);
        objReclamo.setC_enviado(sTipoGuardado.equals("Local")?"N":"S");
        if (sOperacion.equals("NEW")){
            rowid = db.InsertReclamoGarantia(objReclamo);
        }else if (sOperacion.equals("MOD")){
            rowid = db.UpdateReclamoGarantia(objReclamo);
            rowid  = objReclamo.getN_correlativo();
        }

        if (rowid>0){
            if (docsAdapdater!=null) {
                docsAdapdater.SetNumero((int) rowid);
                db.DeleteDocsReclamoGarantia(String.valueOf(objReclamo.getN_correlativo()));
                ActualizarTablaLocalFotos(docsAdapdater.AllItems());
            }
            Log.i("row id reclamog ", String.valueOf(rowid));
            if (sTipoGuardado.equals("Local")) {
                if (docsAdapdater!=null && docsAdapdater.getCount()>0){
                    //GuardarFotoEnServer(docsAdapdater.AllItems());

                }
                FabToast.makeText(DatosGenRG.this, "Se guardo correctamente la informacion", FabToast.LENGTH_LONG, FabToast.SUCCESS, FabToast.POSITION_DEFAULT).show();
                super.onBackPressed();
            }

        }
        if (sTipoGuardado.equals("Servidor") && rowid>0) {
            String sCorrelativoRG = "";
            AsyncTask<String, String, String> asyncTaskCorrelativoRG;
            GetCorrelativoRecGarTask getCorrelativoRecGarTask = new GetCorrelativoRecGarTask();
            try {
                asyncTaskCorrelativoRG = getCorrelativoRecGarTask.execute();
                sCorrelativoRG = asyncTaskCorrelativoRG.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            InsertReclamoGarantiaTask insertReclamoGarantiaTask =  new InsertReclamoGarantiaTask();
            AsyncTask<String,String,String> asyncTaskInsertRG;
            sCorrelativoRG = String.valueOf(Integer.valueOf(sCorrelativoRG)+1);
            String InserRG="";
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String fecha = df.format(Calendar.getInstance().getTime());
            objReclamo.setD_fecha(fecha);
            try {
            asyncTaskInsertRG = insertReclamoGarantiaTask.execute(Constans.NroConpania,sCorrelativoRG,"accion",codUser,objReclamo.getC_tiporeclamo(),objReclamo.getD_fecha(),String.valueOf(objReclamo.getN_formato()),
                                objReclamo.getD_fechaformato(),String.valueOf(objReclamo.getN_cliente()),objReclamo.getC_codproducto(),objReclamo.getC_lote(),objReclamo.getC_procedencia(),objReclamo.getC_facturaRef(),
                                objReclamo.getC_lote1(),objReclamo.getC_lote2(),objReclamo.getC_lote3(),String.valueOf(objReclamo.getN_cantlote1()),String.valueOf(objReclamo.getN_cantlote2()),String.valueOf(objReclamo.getN_cantlote3()),
                                 objReclamo.getC_tiempouso(),objReclamo.getC_codmarca(),objReclamo.getC_codmodelo(),String.valueOf(objReclamo.getN_pyear()),objReclamo.getC_placavehic(),objReclamo.getC_obscliente(),
                                objReclamo.getC_pruebalab1(),objReclamo.getC_pruebalab2(),objReclamo.getC_pruebalab3(),objReclamo.getC_ensayo01(),objReclamo.getC_ensayo02(),objReclamo.getC_ensayo03(),
                                objReclamo.getC_ensayo04(),objReclamo.getC_ensayo05(),objReclamo.getC_vendedor(),objReclamo.getC_prediagnostico(),objReclamo.getC_reembcliente(), String.valueOf(objReclamo.getN_montoreembcli()),
                                objReclamo.getC_monedareembcli(),objReclamo.getC_flagvisita());
                InserRG = asyncTaskInsertRG.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (InserRG.equals("OK")){
                if (docsAdapdater!=null) {
                    docsAdapdater.SetNumero(Integer.valueOf(sCorrelativoRG));
                    ActualizarTablaServerFotos(docsAdapdater.AllItems());
                }
                String sResultTrasnf="";
                TransferirReclamoGarantiaTask transferirReclamoGarantiaTask = new TransferirReclamoGarantiaTask();
                AsyncTask<String,String,String> asyncTaskTransRG ;
                try {
                asyncTaskTransRG = transferirReclamoGarantiaTask.execute(Constans.NroConpania,sCorrelativoRG,"tranferir");
                    sResultTrasnf = asyncTaskTransRG.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                if (sResultTrasnf.equals("OK")){
                    if (docsAdapdater!=null && docsAdapdater.getCount()>0){
                        GuardarFotoEnServer(docsAdapdater.AllItems());
                    }
                    FabToast.makeText(DatosGenRG.this, "Se guardo y sincronizo correctamente la informacion en el servidor.", FabToast.LENGTH_LONG, FabToast.SUCCESS,  FabToast.POSITION_DEFAULT).show();
                    super.onBackPressed();
                }
                else {
                    FabToast.makeText(DatosGenRG.this, "Error : " + sResultTrasnf, FabToast.LENGTH_LONG, FabToast.ERROR,  FabToast.POSITION_DEFAULT).show();

                }


            }
            else {
                FabToast.makeText(DatosGenRG.this, "Error : " + InserRG, FabToast.LENGTH_LONG, FabToast.ERROR,  FabToast.POSITION_DEFAULT).show();
            }
        }
    }



   public  String CodEstado (String  descrpEstado){
        String codEstado = "";
        switch (descrpEstado){
            case "Ingresado":
                codEstado="C";
                break;
            case "Pendiente":
                codEstado="P";
                break;
            case "En Proceso":
                codEstado="R";
                break;
            case "Terminado":
                codEstado="T";
                break;
            case "Anulado":
                codEstado="A";
                break;
        }

        return codEstado;
   }

    public void   setAvance (int nIndic){
        switch (nIndic){
            case 1:
                lblav1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                lblav2.setBackgroundColor(getResources().getColor(R.color.avGray));
                lblav3.setBackgroundColor(getResources().getColor(R.color.avGray));
                lblav4.setBackgroundColor(getResources().getColor(R.color.avGray));
                break;
            case 2:
                lblav1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                lblav2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                lblav3.setBackgroundColor(getResources().getColor(R.color.avGray));
                lblav4.setBackgroundColor(getResources().getColor(R.color.avGray));
                break;
            case 3:
                lblav1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                lblav2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                lblav3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                lblav4.setBackgroundColor(getResources().getColor(R.color.avGray));
                break;
            case 4:
                lblav1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                lblav2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                lblav3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                lblav4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;

        }
    }


    public void SumaCantidadesLotes (){
        String sCantidad1  =  txtCantLote1.getText().toString();
        String sCantidad2  =  txtCantLote2.getText().toString();
        String sCantidad3  =  txtCantLote3.getText().toString();
        Double cant1 =0.0 , cant2 =0.0,cant3 =0.0 , cantGen;

        if (!sCantidad1.isEmpty()){
            cant1 = Double.valueOf(sCantidad1);
        }
        if (!sCantidad2.isEmpty()){
            cant2 = Double.valueOf(sCantidad2);
        }
        if (!sCantidad3.isEmpty()){
            cant3 = Double.valueOf(sCantidad3);
        }
        cantGen = cant1+cant2+cant3;

        txtCantGen.setText(String.valueOf(cantGen));

    }

    public  void  ObtenerLoteSerieFiltro (String sTipo,String sItem, String sFact){
        AsyncTask<String,String,ArrayList<EFacSerLot>> asyncTaskFacLoteSer ;
        GetLoteSerieRGTask getReqLogDetalleTask =  new GetLoteSerieRGTask();
        ArrayList<EFacSerLot> listEfac = new ArrayList<>();
        String sCliente = txtClienteCodNom.getText().toString().substring(0,txtClienteCodNom.getText().toString().indexOf("|")-1);
        sCliente = sCliente.trim();
        try {
            asyncTaskFacLoteSer = getReqLogDetalleTask.execute(sTipo ," " ,sCliente,sFact,sItem);
            listEfac = asyncTaskFacLoteSer.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (listEfac.size()>0){
            EFacSerLot f = listEfac.get(0);
            if (!f.getC_factRef().isEmpty()){
                txtFacturaRG.setText(f.getC_factRef());
            }

            if (!f.getC_lote().isEmpty()){
                txtLoteF.setText(f.getC_lote());
            }
            else {
                txtLoteF.setText("");
            }
            if (!f.getC_procedencia().isEmpty()){
                txtSerieF.setText(f.getC_procedencia());
            }
            else {
                txtSerieF.setText("");
            }

        }

    }

    public void  VerificarItemExiste(String sItem){
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
           btnBuscarFiltro.setImageResource(R.drawable.icn_error_24red);
        }
        else {
            btnBuscarFiltro.setImageResource(R.drawable.icn_succes_32);
            if (txtClienteCodNom.getText().toString().isEmpty()){
                return;
            }
            ObtenerLoteSerieFiltro("I",sItem.toUpperCase()," ");
        }


    }

    public  void  LoadSpinersPruebas (){

        ProdMantDataBase db = new ProdMantDataBase(DatosGenRG.this);
        ArrayList<TMAPruebaLab> listPruebas  = db.GetSelectPruebasLab();
        ArrayList<String> spdata = new ArrayList<>();
        spdata.add("Seleccione Prueba....|");
        if (listPruebas.size()>0){
            for (int i = 0; i <  listPruebas.size(); i++) {
                spdata.add(listPruebas.get(i).getC_codigo() + " | " + listPruebas.get(i).getC_descripcion());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, spdata);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spPrueba1.setAdapter(dataAdapter);
            spPrueba2.setAdapter(dataAdapter);
            spPrueba3.setAdapter(dataAdapter);

        }
    }

    public void LoadSpinerMoneda (){

        ArrayList<String> spdata = new ArrayList<>();
        spdata.add("Seleccione Moneda");
        spdata.add("Soles");
        spdata.add("Dolares");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, spdata);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMonedaRemb.setAdapter(dataAdapter);
    }

    public void LoadSpinerRembolso (){

        ArrayList<String> spdata = new ArrayList<>();
        spdata.add("NO");
        spdata.add("SI");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spdata);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spReembolso.setAdapter(dataAdapter);
    }

    public  void  LoadSpinerVendedor (){
        ProdMantDataBase db = new ProdMantDataBase(DatosGenRG.this);
        ArrayList<TMAVendedor> listVend  = db.GetSelectVendedor();
        ArrayList<String> spdata = new ArrayList<>();
        spdata.add("Seleccione Vendedor.....|");
        if (listVend.size()>0){
            for (int i = 0; i <  listVend.size(); i++) {
                spdata.add(listVend.get(i).getC_vendedor() + " | " + listVend.get(i).getC_nombres());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, spdata);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
             spVendedorDiag.setAdapter(dataAdapter);


        }
    }

    public  void LoadSpinersFalla (){
        ProdMantDataBase db = new ProdMantDataBase(DatosGenRG.this);
        ArrayList<TMAFalla> listFallas  = db.GetSelectFallas();
        ArrayList<String> spdata = new ArrayList<>();
        spdata.add("Seleccione Falla.......|");
        if (listFallas.size()>0){
            for (int i = 0; i <  listFallas.size(); i++) {
                spdata.add(listFallas.get(i).getC_codigo() + " | " + listFallas.get(i).getC_descripcion());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, spdata);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spFalla1.setAdapter(dataAdapter);
            spFalla2.setAdapter(dataAdapter);
            spFalla3.setAdapter(dataAdapter);
            spFalla4.setAdapter(dataAdapter);
            spFalla5.setAdapter(dataAdapter);

        }
    }
    public void  LoadSpinerTipoReclamo (){
        ProdMantDataBase db = new ProdMantDataBase(DatosGenRG.this);
        ArrayList<TMATipoReclamo> listReclamo  = db.GetSelectTipoReclamo();
        ArrayList<String> spdata = new ArrayList<>();
        if (listReclamo.size()>0){
            for (int i = 0; i <  listReclamo.size(); i++) {
                spdata.add(listReclamo.get(i).getC_tiporeclamo() + " | " + listReclamo.get(i).getC_descripcion());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, spdata);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spTipoRG.setAdapter(dataAdapter);

        }


    }

    public  void  LoadSpinerMarca (){
        ProdMantDataBase db = new ProdMantDataBase(DatosGenRG.this);
        ArrayList<TMAMarca> listMarca =  db.GetSelectMarca();
        ArrayList<String> spdata = new ArrayList<>();
        spdata.add("Seleccione Marca....|");
        if (listMarca.size()>0){
            for (int i = 0; i <  listMarca.size(); i++) {
                spdata.add(listMarca.get(i).getC_marca()+ " | " +listMarca.get(i).getC_descripcion());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, spdata);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMarca.setAdapter(dataAdapter);
        }
    }

    public void  LoadSpinerModeo(String sMarca){
        ProdMantDataBase db = new ProdMantDataBase(DatosGenRG.this);
        ArrayList<TMAModelo> listModelo =  db.GetSelectModelo(sMarca);
        ArrayList<String> spdata = new ArrayList<>();
        spdata.add("Seleccione Modelo....|");
        if (listModelo.size()>0){
            for (int i = 0; i <  listModelo.size(); i++) {
                spdata.add(listModelo.get(i).getC_modelo()+ " | " +listModelo.get(i).getC_descripcion());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, spdata);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spModelo.setAdapter(dataAdapter);
        }
    }

    public  void  AlertDialogBuscarCliente () {
        final Dialog dialog  = new Dialog(DatosGenRG.this);
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
                txtClienteCodNom.setText(lvBuscarClientes.getItemAtPosition(position).toString());
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

    public void AlertAgregarFotos(){
        final Dialog dialog  = new Dialog(DatosGenRG.this);
        dialog.setContentView(R.layout.dialog_list_fotos_rg);
        dialog.setTitle("Buscar Cliente");
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button btnAddFoto = dialog.findViewById(R.id.btnAddFotoRG);
        Button btnEliminar = dialog.findViewById(R.id.btnEliminarFotoRG);
        Button btnAceptarFotos = dialog.findViewById(R.id.btnAceptarFotoRG);
        frameViewFotos = dialog.findViewById(R.id.lyFramViewFoto);
        Button btnCerrarFoto = dialog.findViewById(R.id.btnCloseViewFoto);
         imgviewFoto = dialog.findViewById(R.id.ImageViewFoto);
        lvFotosRG  = dialog.findViewById(R.id.lvFotosGuardadasRG);
        lvFotosRG.setItemsCanFocus(true);
        if (docsAdapdater  != null && docsAdapdater.getCount()>0){
            lvFotosRG.setAdapter(docsAdapdater);
        }

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

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (docsAdapdater!=null && docsAdapdater.getCount()>0){
                    docsAdapdater.data.remove(docsAdapdater.data.size()-1);
                    docsAdapdater.notifyDataSetChanged();
                }
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

    private void openBackCamera() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
        File file = new File(pictureImagePath);
        Uri outputFileUri = Uri.fromFile(file);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(cameraIntent, 1);
    }

    public ArrayAdapter<String> getAdapterBuscarCliente(String sFilterText){
        ProdMantDataBase db = new ProdMantDataBase(DatosGenRG.this);
        ArrayList<TMACliente> clientes = db.GetSelectClientes(sFilterText);
        ArrayList<String> data = new ArrayList<>();
            if (clientes.size()>0){
                for (int i = 0; i < clientes.size() ; i++) {
                    TMACliente c = clientes.get(i);
                    data.add(String.valueOf(c.n_cliente)+ " | " + c.getC_razonsocial());

                }
            }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                data );
        Log.i("size adapter  " , String.valueOf(data.size()));
            return  arrayAdapter;
    }

    public void SelecFecha(final EditText txtFecha) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.datapicker_layout, null, false);
        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);
        new AlertDialog.Builder(DatosGenRG.this).setView(view)
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
                        sFechaSelec = String.valueOf(year)+"-"+mes+"-"+dia;

                        dialog.cancel();

                    }

                }).show();
    }

    public  void  SetControlsValues(ReclamoGarantia objR){
        ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
        txtNroFormato.setText(String.valueOf(objR.getN_formato()));
        txtFechaRegistro.setText(FormatearFecha(objR.getD_fechaformato().substring(0,10),"yyyy-MM-dd","dd/MM/yyyy"));
        sFechaSelec = objR.getD_fechaformato().substring(0,10);
        txtClienteCodNom.setText(String.valueOf(objR.getN_cliente())+ " | " + db.GetNombreClienteXCod(String.valueOf(objR.getN_cliente())));
        spTipoRG.setSelection(getIndexSpiner(spTipoRG,objR.getC_tiporeclamo()));
        txtFiltroBuscar.setText(objR.getC_codproducto());
        txtLoteF.setText(objR.getC_lote());
        txtSerieF.setText(objR.getC_procedencia());
        txtFacturaRG.setText(objR.getC_facturaRef());
        txtCantGen.setText(String.valueOf(objR.getN_qtyingreso()));
        txtLote1.setText(objR.getC_lote1());
        txtLote2.setText(objR.getC_lote2());
        txtLote3.setText(objR.getC_lote3());
        txtCantLote1.setText(String.valueOf(objR.getN_cantlote1()));
        txtCantLote2.setText(String.valueOf(objR.getN_cantlote2()));
        txtCantLote3.setText(String.valueOf(objR.getN_cantlote3()));
        spMarca.setSelection(getIndexSpiner(spMarca,objR.getC_codmarca()),false);
        LoadSpinerModeo(objR.getC_codmarca());
        spModelo.setSelection(getIndexSpiner(spModelo,objR.getC_codmodelo()));
        txtAnio.setText(String.valueOf(objR.getN_pyear()));
        txtPlaca.setText(objR.getC_placavehic());
        txtTiempoUso.setText(objR.getC_tiempouso());
        txtObsCliente.setText(objR.getC_obscliente());
        spPrueba1.setSelection(getIndexSpiner(spPrueba1,objR.getC_pruebalab1()));
        spPrueba2.setSelection(getIndexSpiner(spPrueba2,objR.getC_pruebalab2()));
        spPrueba3.setSelection(getIndexSpiner(spPrueba3,objR.getC_pruebalab3()));
        spFalla1.setSelection(getIndexSpiner(spFalla1,objR.getC_ensayo01().trim()));
        spFalla2.setSelection(getIndexSpiner(spFalla2,objR.getC_ensayo02().trim()));
        spFalla3.setSelection(getIndexSpiner(spFalla3,objR.getC_ensayo03().trim()));
        spFalla4.setSelection(getIndexSpiner(spFalla4,objR.getC_ensayo04().trim()));
        spFalla5.setSelection(getIndexSpiner(spFalla5,objR.getC_ensayo05().trim()));
        spVendedorDiag.setSelection(getIndexSpiner(spVendedorDiag,objR.getC_vendedor()));
        txtObsVendedor.setText(objR.getC_prediagnostico());
        spReembolso.setSelection(objR.getC_reembcliente().equals("S")?1:0);
        if (objR.getC_reembcliente().equals("S")){
            spMonedaRemb.setSelection(objR.getC_monedareembcli().equals("L")?1:2);
            txtMontoRemb.setText(String.valueOf(objR.getN_montoreembcli()));
        }
        chkNecVisita.setChecked(objR.getC_reembcliente().equals("S")?true:false);
        switch (objR.getC_estado()){

            case "C":
                txtEstado.setText("Ingresado");
                break;
            case  "P":
                txtEstado.setText("Pendiente");
                break;
            case  "R":
                txtEstado.setText("En Proceso");
                break;
            case  "T":
                txtEstado.setText("Terminado");
                break;
            case  "A":
                txtEstado.setText("Anulado");
                break;
        }
    }

    public void   ShowMessageWindow (String sMessage){
        new PrettyDialog(this)
                .setTitle("Aviso")
                .setMessage(sMessage)
                .setIconTint( R.color.WarningColor)
                .show();
    }

    public  String FormatearFecha(String sFecha , String FormatoEntrada , String FomarmatoSalida){
        String result = "" ;
        SimpleDateFormat parseador = new SimpleDateFormat(FormatoEntrada);
        SimpleDateFormat formateador = new SimpleDateFormat(FomarmatoSalida);
        Date date = null;
        try {
            date = parseador.parse(sFecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
          result =(formateador.format(date));
        return result;
    }

    private int getIndexSpiner(Spinner spinner, String Cod)
    {
        Log.i("Cod Titpo RG" , Cod);
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            String val = spinner.getItemAtPosition(i).toString() ;
            val = val.substring(0,val.indexOf("|")-1);
            Log.i("val sp " , val );
            if (val.equals(Cod)){
                index = i;
                break;
            }
        }
        return index;
    }

    public  DocsReclamoGarantiaAdapter  FillAdaptadorDocs(String sTipoOperacion,String sComp,String sCorrelativo){
        Log.i("operaciones  >>" , sTipoOperacion +" | " + sComp + " | "+ sCorrelativo );
        ArrayList<DocsReclamoGarantia> listaFotos =  new ArrayList<>();
        if (sTipoOperacion.equals("VER")){
            AsyncTask<String,String,ArrayList<DocsReclamoGarantia>> asyncTaskListaFotos;
            GetListaDatosFotosRG  getListaDatosFotosRG = new GetListaDatosFotosRG();
            try {
                asyncTaskListaFotos = getListaDatosFotosRG.execute(sComp,sCorrelativo);
                listaFotos  = asyncTaskListaFotos.get();
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

        }else  if (sTipoOperacion.equals("MOD")){

            ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
            listaFotos = db.GetListDocsRG(sCorrelativo);

        }

        if (listaFotos == null || listaFotos.size()==0){
            listaFotos =  new ArrayList<>();
        }


        docsAdapdater = new DocsReclamoGarantiaAdapter(DatosGenRG.this, R.layout.item_fotos_reclamogar, listaFotos);
//
        return  docsAdapdater;

    }

    public class DocsReclamoGarantiaAdapter extends ArrayAdapter<DocsReclamoGarantia> {
        private Context context ;
        private ArrayList<DocsReclamoGarantia> data ;
        private int layoutResourceId;
        HashMap<Integer,String> hashDescrip;


        public DocsReclamoGarantiaAdapter(@NonNull Context context, int resource, ArrayList<DocsReclamoGarantia> data) {
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
            DocsReclamoGarantia doc = data.get(position);
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

        public ArrayList<DocsReclamoGarantia> AllItems () {
            return data ;
        }

        public  void SetNumero(int n){
            for (int i = 0; i < data.size() ; i++) {
                data.get(i).setN_numero(n);
            }
        }
    }

    public static class ViewHolder{
        TextView lblNombreFoto;
        EditText txtDescripcion;
        Button btnVer ;
    }

    public  void  VisorImagen (String path){

        String filePath2 = Environment.getExternalStorageDirectory() + File.separator + Constans.Carpeta_foto_RG+ path;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath2);
        // frameViewFotos.setVisibility(View.VISIBLE);
        //imgviewFoto.setImageBitmap(bitmap);
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
        //LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(400,400);
        // imageView.setLayoutParams(parms);
        imageView.setImageBitmap(bitmap);
        builder.addContentView(imageView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    public void AlertEdittextListView(final int positionObj, final EditText txtDescp){

        // creating the EditText widget programatically
        final EditText editText = new EditText(DatosGenRG.this);

        // create the AlertDialog as final
        final AlertDialog dialog = new AlertDialog.Builder(DatosGenRG.this)
                .setMessage("Descripcion de foto")
                .setTitle("Escribir texto")
                .setView(editText)

                // Set the action buttons
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
                        // removes the AlertDialog in the screen
                    }
                })
                .create();

        // set the focus change listener of the EditText
        // this part will make the soft keyboard automaticall visible
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
