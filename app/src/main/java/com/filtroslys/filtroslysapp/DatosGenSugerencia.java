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
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import DataBase.ProdMantDataBase;
import Model.DocsQuejaCliente;
import Model.DocsSugerencia;
import Model.EFacSerLot;
import Model.SugerenciaCliente;
import Model.TMACliente;
import Model.TMATipoSugerencia;
import Tasks.GetCorrelativoSugerenciaTask;
import Tasks.GetFotoRG;
import Tasks.GetListaDatosFotosSGTask;
import Tasks.GuardaLocalFotoSGTask;
import Tasks.GuardarFotoRGTask;
import Tasks.InsertDocsSugerenciaTask;
import Tasks.InsertSugerenciaClienteTask;
import Tasks.TransferirSugerenciaClienteTask;
import Util.Constans;
import br.com.sapereaude.maskedEditText.MaskedEditText;
import libs.mjn.prettydialog.PrettyDialog;
import spencerstudios.com.fab_toast.FabToast;

public class DatosGenSugerencia extends AppCompatActivity {
    EditText txtNroSugerencia,txtFechaRegSG,txtClienteCodNom,txtObservacionSG;
    EditText txtDetalleSG,txtRevisadoPorSG,txtFechaRevisadoSG,txtObservacionRevSG;
    EditText txtAccionRevisionSG,txtCerradoPorSG,txtFechaCierreSG,txtObservacionCierreSG;
    Spinner spTipoSugerenciaSG;
    CheckBox chkFinRevisionSG;
    TextView lblAvance1,lblAvance2;
    int PantallaReal;
    ImageView btnBuscarCliente;
    SugerenciaCliente objSugerencia;
    Button btnSiguiente,btnCancelar;
    LinearLayout ly_Datos_Generales,ly_Datos_Otros;
    FrameLayout layTipoSug;
    int nIndicadorLayout ;
    String codUser,sFechaSelec,sOperacion="VER",sTipoInfo="";
    SharedPreferences preferences;
    ListView lvFotosSG;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    DocsSugerenciaAdapter docsAdapdater = null ;
    ArrayList<DocsSugerencia> listaDocsFotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_gen_sugerencia);
        setTitle("Datos Generales");
        preferences = PreferenceManager.getDefaultSharedPreferences(DatosGenSugerencia.this);
        btnCancelar = findViewById(R.id.btnCancelRegSG);
        btnSiguiente = findViewById(R.id.btnSigDatosSG);
        ly_Datos_Generales = findViewById(R.id.layGeneralSG);
        ly_Datos_Otros = findViewById(R.id.layOtrosSG);
        layTipoSug = findViewById(R.id.layTipoSug);

        txtNroSugerencia = findViewById(R.id.txtNroSugerencia);
        txtFechaRegSG = findViewById(R.id.txtFechaRegSG);
        spTipoSugerenciaSG = findViewById(R.id.spTipoSugerenciaSG);
        txtClienteCodNom =  findViewById(R.id.txtBSClienteSG);
        btnBuscarCliente = findViewById(R.id.btnBuscarClienteSG);
        txtDetalleSG = findViewById(R.id.txtDetalleSG);
        txtObservacionSG = findViewById(R.id.txtObservacionSG);
        chkFinRevisionSG = findViewById(R.id.chkFinRevisionSG);
        txtRevisadoPorSG = findViewById(R.id.txtRevisadoPorSG);
        txtFechaRevisadoSG = findViewById(R.id.txtFechaRevisadoSG);
        txtObservacionRevSG = findViewById(R.id.txtObservacionRevSG);
        txtAccionRevisionSG = findViewById(R.id.txtAccionRevisionSG);
        txtCerradoPorSG = findViewById(R.id.txtCerradoPorSG);
        txtFechaCierreSG =  findViewById(R.id.txtFechaCierreSG);
        txtObservacionCierreSG  = findViewById(R.id.txtObservacionCierreSG);
        lblAvance1 = findViewById(R.id.lblAvance1);
        lblAvance2 = findViewById(R.id.lblAvance2);;
        txtClienteCodNom.setEnabled(false);

        PantallaReal=1;
        nIndicadorLayout = 1;
        ConfigurarPantalla(nIndicadorLayout);
        codUser = preferences.getString("UserCod", null);
        sOperacion = getIntent().getStringExtra("Operacion");
        sTipoInfo = getIntent().getStringExtra("TipoInfo");

        LoadSpinerTipoSug();
        Log.i("operacion y tipo info" , sTipoInfo + " // " + sOperacion);
        if(!sTipoInfo.equalsIgnoreCase("SU")){
            layTipoSug.setVisibility(View.GONE);
        }

        if (sOperacion.equals("NEW")){
            objSugerencia  = new SugerenciaCliente();
            if (getIntent().getStringExtra("Cliente")!=null){
                txtClienteCodNom.setText(getIntent().getStringExtra("Cliente"));
            }
        }else if(sOperacion.equals("MOD") || sOperacion.equals("VER")){
            Gson gson = new Gson();
            String sgAsString = getIntent().getStringExtra("ObjectSG");
            objSugerencia  = gson.fromJson(sgAsString, SugerenciaCliente.class);
            docsAdapdater = FillAdaptadorDocs(sOperacion,objSugerencia.getC_compania(), String.valueOf( objSugerencia.getN_correlativo()));
            SetControlsValues(objSugerencia);
        }

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nIndicadorLayout>1){
                    nIndicadorLayout= nIndicadorLayout -1;
                    ConfigurarPantalla(nIndicadorLayout);
                }
                else {
                    DatosGenSugerencia.super.onBackPressed();
                }
            }
        });
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sOperacion.equals("VER")){
                    if (nIndicadorLayout<2) {
                        nIndicadorLayout = nIndicadorLayout +1 ;
                        ConfigurarPantalla(nIndicadorLayout);
                    }
                    else if (nIndicadorLayout==2){
                        CerrarVisor();
                    }
                }else{
                    VaLidacionFinal();
                }
            }
        });

        txtFechaRegSG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelecFecha(txtFechaRegSG);
            }
        });
        btnBuscarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogBuscarCliente();
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

    public  void GuardarFoto(Bitmap bmp , String sCodFoto, String sTipoSincron){

        File DirExist =  new File(Environment.getExternalStorageDirectory(),  Constans.Carpeta_foto_SG);
        if (!DirExist.exists()){
            DirExist.mkdir();
        }


        String filename =   Constans.Carpeta_foto_SG+sCodFoto+".jpg";
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

    public  void SetAdapterDocsFoto(String sCodFoto) {
        DocsSugerencia d = new DocsSugerencia();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sUltFecha = sdf.format(new Date());
        d.setD_ultimafechamodificacion(sUltFecha);
        d.setC_ultimousuario(codUser);
        d.setC_nombre_archivo(sCodFoto);
        d.setC_ruta_archivo(sCodFoto + ".jpg");
        d.setC_compania(Constans.NroConpania);
        listaDocsFotos.add(d);
        if (docsAdapdater == null) {

            docsAdapdater = new DocsSugerenciaAdapter(DatosGenSugerencia.this, R.layout.item_fotos_reclamogar, listaDocsFotos);
            lvFotosSG.setAdapter(docsAdapdater);
        } else {

            lvFotosSG.setAdapter(docsAdapdater);
        }
    }

    public void AlertAgregarFotos(){
        final Dialog dialog  = new Dialog(DatosGenSugerencia.this);
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
        lvFotosSG  = dialog.findViewById(R.id.lvFotosGuardadasRG);
        lvFotosSG.setItemsCanFocus(true);
        if (docsAdapdater != null){
            if (docsAdapdater.getCount()>0) {
                lvFotosSG.setAdapter(docsAdapdater);
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

    public void VaLidacionFinal(){
        String sCliente,sTipoSug="",sDetalleSG="",sObsSG="",sFechaReg="";

        sCliente = txtClienteCodNom.getText().toString().trim();
        sCliente = sCliente.indexOf("|")>0? sCliente.substring(0,sCliente.indexOf("|")-1).trim():"0";
        sDetalleSG = txtDetalleSG.getText().toString().trim();
        sObsSG = txtObservacionSG.getText().toString().trim();

        if (spTipoSugerenciaSG.getSelectedItemPosition()>0){
            sTipoSug = spTipoSugerenciaSG.getSelectedItem().toString();
            sTipoSug = sTipoSug.substring(0,sTipoSug.indexOf("|")-1).trim();
        }

        if (sCliente.isEmpty() || sCliente.equals("0")){
            ShowMessageWindow("Debe seleccionar un cliente.");
            return;
        }

        if(sTipoInfo.equalsIgnoreCase("SU")){
            if (sTipoSug.isEmpty()){
                ShowMessageWindow("Debe seleccionar Tipo de Sugerencia.");
                return;
            }
        }

        if (sDetalleSG.isEmpty()){
            ShowMessageWindow("Debe ingresar Detalle.");
            return;
        }

        DateFormat DateFrm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sFechaReg = DateFrm.format(Calendar.getInstance().getTime());

        objSugerencia.setC_compania(Constans.NroConpania);
        objSugerencia.setC_tipoinfo(sTipoInfo);
        objSugerencia.setN_cliente(Integer.valueOf(sCliente));
        objSugerencia.setD_fecha(sFechaReg);
        objSugerencia.setC_tiposug(sTipoSug);
        objSugerencia.setC_descripcion(sDetalleSG);
        objSugerencia.setC_observacion(sObsSG);
        objSugerencia.setC_usuarioreg(codUser);
        objSugerencia.setD_fechareg(sFechaReg);
        objSugerencia.setC_estado("PE");
        SeleccionarFormaGuardado();
    }

    public void CerrarVisor(){

    }

    public  DocsSugerenciaAdapter  FillAdaptadorDocs(String sTipoOperacion,String sComp,String sCorrelativo){
        Log.i("operaciones  >>" , sTipoOperacion +" | " + sComp + " | "+ sCorrelativo );
        ArrayList<DocsSugerencia> listaFotos =  new ArrayList<>();
        if (sTipoOperacion.equals("VER")){
            AsyncTask<String,String,ArrayList<DocsSugerencia>> asyncTaskListaFotosQJ;
            GetListaDatosFotosSGTask getListaDatosFotosQJTask = new GetListaDatosFotosSGTask();
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
            listaFotos = db.GetListDocsSugerencia(sCorrelativo);

        }

        if (listaFotos == null){
            listaFotos =  new ArrayList<>();
        }


        docsAdapdater = new DocsSugerenciaAdapter(DatosGenSugerencia.this, R.layout.item_fotos_reclamogar, listaFotos);
//
        return  docsAdapdater;

    }

    public void RecuperandoImagen(String filename) {
        //File filefoto = null;
        Bitmap bitmap = null;

        File fileexist = new File(Environment.getExternalStorageDirectory() +File.separator+Constans.Carpeta_foto_SG + filename);
        if (fileexist.exists()){
            return;
        }

        AsyncTask<String, String, byte[]> asynckGetFoto;
        GetFotoRG getFotoTask = new GetFotoRG();
        byte[] bytes = null;
        try {
            asynckGetFoto = getFotoTask.execute(filename, sTipoInfo);
            bytes = (byte[]) asynckGetFoto.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        AsyncTask<byte[], String, String> asyncSaveImglocal;
        GuardaLocalFotoSGTask guardarImgLocalTask = new GuardaLocalFotoSGTask(filename, bytes);

        try {
            asyncSaveImglocal = guardarImgLocalTask.execute(bytes);
            String resSaveImg = (String) asyncSaveImglocal.get();
            Log.i("result save imag local SG => ", resSaveImg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String filePath2 = Environment.getExternalStorageDirectory() +File.separator+Constans.Carpeta_foto_SG + filename;
        bitmap = BitmapFactory.decodeFile(filePath2);


    }

    public void SeleccionarFormaGuardado(){
        final CharSequence[] items = {"Solo guardar en movil","Enviar a Servidor","Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(DatosGenSugerencia.this);
        builder.setTitle("Seleccionar opcion : ");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
               switch (item) {
                   case  0 :
                       GuardarSugerencia("Local");
                       break;
                   case 1:
                       GuardarSugerencia("Servidor");
                       break;
                   case 2:
                       dialog.dismiss();
               }
            }
        }).show();
    }

    public void ConfigurarPantalla (int nIndicadorPantlla){
        ly_Datos_Generales.setVisibility(View.GONE);
        ly_Datos_Otros.setVisibility(View.GONE);

        setAvance(nIndicadorPantlla);
        btnSiguiente.setEnabled(true);
        PantallaReal=nIndicadorPantlla;
        switch (nIndicadorPantlla){
            case 1:
                btnCancelar.setText("Cancelar");
                btnSiguiente.setText("Siguiente");
                ly_Datos_Generales.setVisibility(View.VISIBLE);
                break;
            case 2:
                btnCancelar.setText("Volver");
                btnSiguiente.setText("Finalizar");
                btnSiguiente.setEnabled(objSugerencia.getC_enviado().equals("S")? false:true);
                ly_Datos_Otros.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void GuardarSugerencia(String sTipoGuardado){
        ProdMantDataBase db = new ProdMantDataBase(DatosGenSugerencia.this);
        long rowid = 0;
        objSugerencia.setC_enviado(sTipoGuardado.equals("Local")?"N":"S");
        if (sOperacion.equals("NEW")) {
             rowid  =   db.InsertSugerenciaCliente(objSugerencia);
        }else if (sOperacion.equals("MOD")){
             rowid   = db.UpdateSugerenciaCliente(objSugerencia);
        }


        if (rowid>0){
            Log.i("row id sugerencia ", String.valueOf(rowid));
            if (docsAdapdater!=null) {
                docsAdapdater.SetNumero((int) rowid);
                ActualizarTablaLocalFotos(docsAdapdater.AllItems());
            }
            if (sTipoGuardado.equals("Local")) {
                FabToast.makeText(DatosGenSugerencia.this, "Se guardo correctamente la informacion", FabToast.LENGTH_LONG, FabToast.SUCCESS, FabToast.POSITION_DEFAULT).show();
                super.onBackPressed();
            }
        }
        if (sTipoGuardado.equals("Servidor") && rowid>0) {
            String sCorrelativoSG = "";
            AsyncTask<String, String, String> asyncTaskCorrelativoSG;
            GetCorrelativoSugerenciaTask getCorrelativoSugTask = new GetCorrelativoSugerenciaTask();
            try {
                asyncTaskCorrelativoSG = getCorrelativoSugTask.execute();
                sCorrelativoSG = asyncTaskCorrelativoSG.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            InsertSugerenciaClienteTask insertSugerenciaClienteTask = new InsertSugerenciaClienteTask();
            AsyncTask<String,String,String> asyncTaskInsertSG;
            sCorrelativoSG = String.valueOf(Integer.valueOf(sCorrelativoSG)+1);
            String InserSG="";
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String fecha = df.format(Calendar.getInstance().getTime());
            objSugerencia.setD_fecha(fecha);
            objSugerencia.setD_fechareg(fecha);
            try {
                asyncTaskInsertSG = insertSugerenciaClienteTask.execute(Constans.NroConpania,sCorrelativoSG,"accion",codUser,
                        objSugerencia.getD_fecha(),String.valueOf(objSugerencia.getN_cliente()),objSugerencia.getC_tiposug(),
                        objSugerencia.getC_descripcion(),objSugerencia.getC_estado(),objSugerencia.getC_tipoinfo(),objSugerencia.getC_observacion());
                InserSG = asyncTaskInsertSG.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (InserSG.equals("OK")){
                if (docsAdapdater!=null) {
                    docsAdapdater.SetNumero(Integer.valueOf(sCorrelativoSG));
                    ActualizarTablaServerFotos(docsAdapdater.AllItems());
                }
                String sResultTrasnf="";
                TransferirSugerenciaClienteTask transferirSugerenciaClienteTask = new TransferirSugerenciaClienteTask();
                AsyncTask<String,String,String> asyncTaskTransSG ;
                try {
                    asyncTaskTransSG = transferirSugerenciaClienteTask.execute(Constans.NroConpania,sCorrelativoSG,"tranferir");
                    sResultTrasnf = asyncTaskTransSG.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if (sResultTrasnf.equals("OK")){
                    if (docsAdapdater!=null && docsAdapdater.getCount()>0){
                        GuardarFotoEnServer(docsAdapdater.AllItems());
                    }
                    FabToast.makeText(DatosGenSugerencia.this, "Se guardo y sincronizo correctamente la informacion en el servidor.", FabToast.LENGTH_LONG, FabToast.SUCCESS,  FabToast.POSITION_DEFAULT).show();
                    super.onBackPressed();
                }
                else {
                    FabToast.makeText(DatosGenSugerencia.this, "Error : " + sResultTrasnf, FabToast.LENGTH_LONG, FabToast.ERROR,  FabToast.POSITION_DEFAULT).show();
                }
            }
            else {
                FabToast.makeText(DatosGenSugerencia.this, "Error : " + InserSG, FabToast.LENGTH_LONG, FabToast.ERROR,  FabToast.POSITION_DEFAULT).show();
            }
        }
    }

    public  void  GuardarFotoEnServer(ArrayList<DocsSugerencia> listDocsF)
    {
        String fileCarp = Environment.getExternalStorageDirectory() +File.separator +Constans.Carpeta_foto_SG;
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
                    GuardarFotoRGTask guardarImagenTask = new GuardarFotoRGTask(bytes, listDocsF.get(i).getC_ruta_archivo(),sTipoInfo);
                    //Log.i("Parmaetro exex GuardarTask ===>", listDet.get(i).getRutaFoto());
                    asyncTask = guardarImagenTask.execute();
                    String resp = (String) asyncTask.get();
                    Log.i("foto server guardada sugerencia "+sTipoInfo+" ===>", resp);

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

    public  void  ActualizarTablaServerFotos(ArrayList<DocsSugerencia> listDocFotos) {
        String ressultDocInsert = "";
        for (int i = 0; i < listDocFotos.size(); i++) {
            DocsSugerencia d = listDocFotos.get(i);
            AsyncTask<String, String, String> asyncTaskDocFotoSG;
            InsertDocsSugerenciaTask insertDocsQuejaClienteTask = new InsertDocsSugerenciaTask();
            try {
                asyncTaskDocFotoSG = insertDocsQuejaClienteTask.execute(d.getC_compania(), String.valueOf(d.getN_sugerencia()), String.valueOf(i + 1), d.getC_descripcion(), d.getC_nombre_archivo(), d.getC_ruta_archivo(), codUser);
                ressultDocInsert = asyncTaskDocFotoSG.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public  void ActualizarTablaLocalFotos(ArrayList<DocsSugerencia> listFotos){

        ProdMantDataBase  db = new ProdMantDataBase(getApplicationContext());
        for (int i = 0; i < listFotos.size() ; i++) {
            DocsSugerencia dc = listFotos.get(i);
            dc.setN_linea(i+1);
            long rowdoc =  db.InsertDocsSuegerencia(dc);
            Log.i("id row doc sg" ,String.valueOf(rowdoc));
        }

    }

    public String CodEstado(String descrpEstado){
        String codEstado = "";
        switch (descrpEstado){
            case "Pendiente":
                codEstado="PE";
                break;
            case "Revisado":
                codEstado="RE";
                break;
            case "Cerrado":
                codEstado="CE";
                break;
            case "Anulado":
                codEstado="AN";
                break;
        }
        return codEstado;
   }

    public void setAvance(int nIndic){
        switch (nIndic){
            case 1:
                lblAvance1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                lblAvance2.setBackgroundColor(getResources().getColor(R.color.avGray));
                break;
            case 2:
                lblAvance1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                lblAvance2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
        }
    }

    public void LoadSpinerTipoSug(){
        ProdMantDataBase db = new ProdMantDataBase(DatosGenSugerencia.this);
        ArrayList<TMATipoSugerencia> listTipoSG  = db.GetTipoSugerencia();
        ArrayList<String> spdata = new ArrayList<>();
        spdata.add("Selecione tipo sugerencia .....|");
        if (listTipoSG.size()>0){
            for (int i=0;i<listTipoSG.size();i++) {
                spdata.add(listTipoSG.get(i).getC_tiposugerencia() + " | " + listTipoSG.get(i).getC_descripcion());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,spdata);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spTipoSugerenciaSG.setAdapter(dataAdapter);
        }
    }

    public void AlertDialogBuscarCliente () {
        final Dialog dialog  = new Dialog(DatosGenSugerencia.this);
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

    public ArrayAdapter<String> getAdapterBuscarCliente(String sFilterText){
        ProdMantDataBase db = new ProdMantDataBase(DatosGenSugerencia.this);
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
        return arrayAdapter;
    }

    public void SetControlsValues(SugerenciaCliente oEnt){
        ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
        txtNroSugerencia.setText(String.valueOf(oEnt.getN_correlativo()));
        txtNroSugerencia.setText(String.valueOf(oEnt.getN_identinfo()));

        Log.i("operacion set value " , sOperacion);
        //Log.i("fecha set value " , oEnt.getD_fecha());
        //txtFechaRegSG.setText("dsdsd");
        txtFechaRegSG.setText(FormatearFecha(oEnt.getD_fechareg().substring(0,10),"yyyy-MM-dd","dd/MM/yyyy"));
        txtClienteCodNom.setText(String.valueOf(oEnt.getN_cliente())+ " | " + db.GetNombreClienteXCod(String.valueOf(oEnt.getN_cliente())));
        spTipoSugerenciaSG.setSelection(getIndexSpiner(spTipoSugerenciaSG,oEnt.getC_tiposug()));
        txtDetalleSG.setText(oEnt.getC_descripcion());
        txtObservacionSG.setText(oEnt.getC_observacion());
        if(sOperacion.equals("VER")){
            txtRevisadoPorSG.setText(oEnt.getC_usuariorev());
            txtFechaRevisadoSG.setText(oEnt.getD_fecharev());
            txtObservacionRevSG.setText(oEnt.getC_observacionrev());
            txtAccionRevisionSG.setText(oEnt.getC_accionrev());
            chkFinRevisionSG.setChecked(false);
            if(!TextUtils.isEmpty(oEnt.getC_flagfinrev())){
                chkFinRevisionSG.setChecked(oEnt.getC_flagfinrev().equals("S")?true:false);
            }
            txtCerradoPorSG.setText(oEnt.getC_usuarioCE());
            txtFechaCierreSG.setText(oEnt.getD_fechaCE());
            txtObservacionCierreSG.setText(oEnt.getC_observacionCE());
        }
    }

    public void ShowMessageWindow (String sMessage){
        new PrettyDialog(this)
                .setTitle("Aviso")
                .setMessage(sMessage)
                .setIconTint( R.color.WarningColor)
                .show();
    }

    public String FormatearFecha(String sFecha, String FormatoEntrada, String FomarmatoSalida){
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

    private int getIndexSpiner(Spinner spinner, String Cod){
        Log.i("Cod Spin " , Cod);
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
    public void SelecFecha(final EditText txtFecha) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.datapicker_layout, null, false);
        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);
        new AlertDialog.Builder(DatosGenSugerencia.this).setView(view)
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

    public class DocsSugerenciaAdapter extends ArrayAdapter<DocsSugerencia> {
        private Context context ;
        private ArrayList<DocsSugerencia> data ;
        private int layoutResourceId;
        HashMap<Integer,String> hashDescrip;


        public DocsSugerenciaAdapter(@NonNull Context context, int resource, ArrayList<DocsSugerencia> data) {
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
            DocsSugerencia doc = data.get(position);
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

        public ArrayList<DocsSugerencia> AllItems () {
            return data ;
        }

        public  void SetNumero(int n){
            for (int i = 0; i < data.size() ; i++) {
                data.get(i).setN_sugerencia(n);
            }
        }
    }

    public static class ViewHolder{
        TextView lblNombreFoto;
        EditText txtDescripcion;
        Button btnVer ;
    }

    public  void  VisorImagen (String path ){

        String filePath2 = Environment.getExternalStorageDirectory() + File.separator + Constans.Carpeta_foto_SG+ path;
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
        final EditText editText = new EditText(DatosGenSugerencia.this);
        final AlertDialog dialog = new AlertDialog.Builder(DatosGenSugerencia.this)
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
