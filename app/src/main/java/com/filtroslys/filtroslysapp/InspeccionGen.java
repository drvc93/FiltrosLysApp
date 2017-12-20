package com.filtroslys.filtroslysapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import DataBase.CentroCostoDB;
import DataBase.MaquinaDB;
import DataBase.ProdMantDataBase;
import DataBase.TipoRevisionGBD;
import Model.InspeccionGenCabecera;
import Model.InspeccionGenDetalle;
import Tasks.EnviarInspGenCabTask;
import Tasks.EnviarInspGenDetTask;
import Tasks.GetCorrelativoTask;
import Tasks.GetFotoTask;
import Tasks.GetInspGenCabTask;
import Tasks.GetInspGenDetTask;
import Tasks.GuardarImagenTask;
import Tasks.GuardarImgLocalTask;
import Tasks.TransferirInspeccionTask;
import Util.Constans;

public class InspeccionGen extends AppCompatActivity {

    Spinner spMaqCC, spTipoInsp;
    boolean CambiarProblemaDetectado =  true;
    String tipoSincro, xcorrelativo;
    String value_spiner_seletec = "";
    TextView lblSPCCMaq, lblProblemaDetec, lblInspector;
    private static final int CAMERA_REQUEST = 1888;
    EditText txtFechaInsp, txtArea, txtProblemadetect;
    ActionBar actionBar;
    int INSP_MAQUINA = 2;
    int INSP_OTROS = 1;
    int postItemFoto;
    int SOLO_GUARDAR = 0;
    int GUARDAR_Y_ENVIAR_ = 1;
    int IndexSpMaq=0;
    String codUser, tipoMant = "";
    int var_tipoIsnpeccion = 0;
    public CharSequence[] listTipoRevision;
    ListView LVInspGen;
    DetalleGenAdapater detalleAdapter;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inpeccion_gen);
        //setTitle("Inspección General");
        spTipoInsp = (Spinner) findViewById(R.id.spTipoIns);
        spMaqCC = (Spinner) findViewById(R.id.spMaqCC);
        lblSPCCMaq = (TextView) findViewById(R.id.lblSPMAQCC);
        txtProblemadetect = (EditText) findViewById(R.id.txtProbDet);
        txtFechaInsp = (EditText) findViewById(R.id.txtFechaInsG);
        txtArea = (EditText) findViewById(R.id.txtAreaG);
        lblInspector = (TextView) findViewById(R.id.lblInspectorG);
        LVInspGen = (ListView) findViewById(R.id.LVInspeccionGen);
        tipoMant = getIntent().getExtras().getString("tipoMant");
        spMaqCC.setEnabled(false);
        txtProblemadetect.setEnabled(false);
        preferences = PreferenceManager.getDefaultSharedPreferences(InspeccionGen.this);
        codUser = preferences.getString("UserCod", null);


        txtFechaInsp.setText(FechaActual());

        actionBar = getSupportActionBar();
        if (GetDisplaySize() < 4) {

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
        listTipoRevision = GettListTipoRevision();
        LoadSpinerTipoInsp();

        if (tipoMant.equals("NEW")) {
            LoadListView();
            LoadSpinerMaqCC(0);
            setTitle("Nueva Inspección");
        } else {
            tipoSincro = getIntent().getExtras().getString("tipoSincro");
            xcorrelativo = getIntent().getExtras().getString("xcorrelativo");
            Log.i("Correlativo Insp Gen", xcorrelativo );
            LoadListView(xcorrelativo, tipoSincro);
            Log.i("tipo mant", tipoMant );
            setTitle("Inspeccion Nº : " + xcorrelativo);
            //AsignIndexSpMaq();
            //txtProblemadetect.setText("----");

            if (tipoMant.equals("Visor")){
                spTipoInsp.setEnabled(false);
                spMaqCC.setEnabled(false);
                txtArea.setEnabled(false);
                txtFechaInsp.setEnabled(false);
                txtProblemadetect.setEnabled(false);
                LVInspGen.setClickable(false);
                LVInspGen.setEnabled(false);


            }
        }


        txtArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowCometarioCabDialog(txtArea, "Area afectada");
            }
        });
        txtProblemadetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowCometarioCabDialog(txtProblemadetect, "Problema detectado");
            }
        });

        spTipoInsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (CambiarProblemaDetectado==false){
                    CambiarProblemaDetectado=true;
                }
                else {
                    LoadSpinerMaqCC(i);
                }
               // Log.i("index cambiado " , String.valueOf(i) + "/" + String.valueOf(l));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
               // Log.i("index nothimh " , "vacio");
            }

        });
        spMaqCC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    //if (tipoMant.equals("NEW")) {
                        String codMaq = spMaqCC.getSelectedItem().toString();
                        codMaq = codMaq.substring(0, 7);
                        codMaq = codMaq.trim();
                        Log.i("cod maquina" , codMaq);
                        AsignarCodCcostoTexBox(codMaq);
                }
                else {
                  //  txtProblemadetect.setText("");
                }
                Log.i("index  spiner maq cc" , String.valueOf(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ResizeAndSaveImage(photo);
        }
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
            SeleccionarOpcionDeGuardar();
        }
        if (id == R.id.Agregar) {
            AlertAddDetail();
        }
        return true;
    }

    public void ActualizarReporte(int TipoGuardado) {
        ProdMantDataBase db = new ProdMantDataBase(InspeccionGen.this);
        InspeccionGenCabecera cabEnvio;
        ArrayList<InspeccionGenDetalle> detalleEnvio = new ArrayList<>();
        if (spMaqCC.getSelectedItemPosition()==0){
            CreateCustomToast("Debe seleccionar una Maquina o Centro de Costo en el combo.", Constans.icon_warning, Constans.layot_warning);
            return;
        }
        if (ValidarCabecera() == true && ValidarDetalle() == true) {
            InspeccionGenCabecera cab = GetCabecera(TipoGuardado);
            cab.setFechaInsp(txtFechaInsp.getText().toString());
            cab.setCorrelativo(xcorrelativo);
            cabEnvio = cab;
            Log.i("fecha envio",cabEnvio.getFechaInsp());
            db.deleteInspGen(xcorrelativo);
            long cabId = db.InsertInspGenCab(cab);
            long detID ;
            int cont = 0;
            Log.i("ID INSPE GEN CAB ID >", String.valueOf(cabId));
            if (cabId > 0) {
                ArrayList<InspeccionGenDetalle> list = detalleAdapter.Alldata();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setUltUsuario(codUser);
                    list.get(i).setCompania(cab.getCompania());
                    list.get(i).setCorrelativo(cab.getCorrelativo());
                    list.get(i).setUltFechaMod((FechaFormatEng(FechaActual())));
                    list.get(i).setFlagadictipo("N");
                    list.get(i).setLinea(String.valueOf(i+1));
                    detalleEnvio.add(list.get(i));
                    detID = db.InsertInspGenDet(list.get(i));
                    Log.i("ID INSPE GEN DET ID >", String.valueOf(detID));
                    if (detID > 0) {
                        cont = cont + 1;
                    }
                }
            }
            if (TipoGuardado == SOLO_GUARDAR && cont > 0) {

                CreateCustomToast("Se actualizo correctamente los datos", Constans.icon_succes, Constans.layout_success);

            } else if (TipoGuardado == GUARDAR_Y_ENVIAR_ && cont > 0) {

                EnviarReporteInspGeneral(cabEnvio, detalleEnvio);
            }
        }
    }

    public void GuardarReporte(int TipoGuardado) {
        ProdMantDataBase db = new ProdMantDataBase(InspeccionGen.this);
        InspeccionGenCabecera cabEnvio;
        ArrayList<InspeccionGenDetalle> detalleEnvio = new ArrayList<InspeccionGenDetalle>();
        int cont = 0;
        if (ValidarCabecera() == true || ValidarDetalle() == true) {

            InspeccionGenCabecera cab = GetCabecera(TipoGuardado);
            cabEnvio = cab;
            long idCab = db.InsertInspGenCab(cab);
            Log.i("Id insp gen cab >", String.valueOf(idCab));
            if (idCab > 0) {
                String correlativo = cab.getCorrelativo();
                ArrayList<InspeccionGenDetalle> listdetalles = GetDetalle(correlativo);
                for (int i = 0; i < listdetalles.size(); i++) {
                    listdetalles.get(i).setLinea(String.valueOf(i+1));
                    detalleEnvio.add(listdetalles.get(i));
                    long detID = db.InsertInspGenDet(listdetalles.get(i));
                    Log.i("Id insp gen det >", String.valueOf(detID));
                    if (detID > 0) {
                        cont = cont + 1;
                    }
                }
                if (TipoGuardado == SOLO_GUARDAR && cont > 0) {
                    CreateCustomToast("Se guardo correctamente el reporte ", Constans.icon_succes, Constans.layout_success);
                    super.onBackPressed();
                   // setResult(2);
                } else if (TipoGuardado == GUARDAR_Y_ENVIAR_ && cont > 0) {
                    EnviarReporteInspGeneral(cabEnvio, detalleEnvio);
                }
            }
        }
    }
    public void EnviarReporteInspGeneral(InspeccionGenCabecera cab, ArrayList<InspeccionGenDetalle> detalles) {

        GetCorrelativoTask getcorrelativoTask = new GetCorrelativoTask();
        AsyncTask<String, String, String> asyncCorrelativo;
        String correlativo = "";
        String stTransfer= "";
        try {
            asyncCorrelativo = getcorrelativoTask.execute();
            correlativo = asyncCorrelativo.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        EnviarInspGenCabTask enviarCabeceratTask = new EnviarInspGenCabTask();
        AsyncTask<String, String, String> asyncEnviarCab;
        String resulCab = "";
        try {
            asyncEnviarCab = enviarCabeceratTask.execute(cab.getCompania(), correlativo, cab.getTipoInspeccion(),
                    cab.getCod_maquina(), cab.getCentroCosto(), cab.getComentario(), cab.getUsuarioInsp(), cab.getFechaInsp(),
                    cab.getEstado(), cab.getUsuarioEnvio(), cab.getUltUsuario());
            resulCab = asyncEnviarCab.get();
            Log.i("result envio insp gen cab ==> ", resulCab);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (Integer.valueOf(resulCab) > 0) {
            String resultDet = "";
            int contDet = 0;
            for (int i = 0; i < detalles.size(); i++) {
                InspeccionGenDetalle d = detalles.get(i);
                AsyncTask<String, String, String> asyncDetalle;
                EnviarInspGenDetTask enviarDetalleTask = new EnviarInspGenDetTask();
                try {
                    asyncDetalle = enviarDetalleTask.execute(d.getCompania(), correlativo, d.getLinea(),
                            d.getComentario(), d.getRutaFoto(), d.getUltUsuario(), d.getTipoRevision(), d.getFlagadictipo());

                    resultDet =  asyncDetalle.get();
                    Log.i("parametros detalle insp gen =>  ", correlativo +"|"+ d.getLinea());
                    Log.i("Resul insert inp gen det  =>  ", resultDet);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                if (resultDet.equals("OK")) {
                    contDet = contDet + 1;
                }
            }

            if (contDet > 0) {

                TransferirInspeccionTask trasnferirInspTask = new TransferirInspeccionTask();
                AsyncTask<String, String, String> asynckTrans;

                try {
                    asynckTrans = trasnferirInspTask.execute("GEN", correlativo);
                    String resTrans =  asynckTrans.get();
                    stTransfer = resTrans;
                    Log.d("Result transf Insp Det => ", resTrans);
                    GuardarImagenServer(detalles);
                } catch (InterruptedException e) {
                    //  e.printStackTrace();
                } catch (ExecutionException e) {
                    // e.printStackTrace();
                }
                if (stTransfer.equals("OK")) {
                    CreateCustomToast("Se realizó el envio del reporte correctamente", Constans.icon_succes, Constans.layout_success);
                    if  ( tipoMant.equals("Editar") ||  tipoMant.equals("NEW")  ){
                         super.onBackPressed();
                    }
                }
                else {
                    CreateCustomToast(stTransfer,Constans.icon_error,Constans.layout_error);
                }
            }
        }
    }

    public ArrayList<InspeccionGenDetalle> GetDetalle(String correlativo) {
        ArrayList<InspeccionGenDetalle> detalles = detalleAdapter.Alldata();
        for (int i = 0; i < detalles.size(); i++) {

            detalles.get(i).setCorrelativo(correlativo);
            detalles.get(i).setUltUsuario(codUser);
            detalles.get(i).setUltFechaMod(FechaFormatEng(FechaActual()));
            detalles.get(i).setFlagadictipo("N");
        }
        return detalles;
    }

    public InspeccionGenCabecera GetCabecera(int TipoGuardado) {
        ProdMantDataBase db = new ProdMantDataBase(InspeccionGen.this);
        int getcorrelativo = db.CorrelativoInspGen();

        InspeccionGenCabecera cab = new InspeccionGenCabecera();
        cab.setCompania(Constans.NroConpania);
        cab.setCorrelativo(String.valueOf(getcorrelativo));
        cab.setFechaInsp(FechaFormatEng(txtFechaInsp.getText().toString()));
        if (spTipoInsp.getSelectedItemPosition() == INSP_MAQUINA) {
            cab.setCod_maquina(GetCodSpinerMaqCC("MAQ"));
            cab.setCentroCosto(txtProblemadetect.getText().toString());
        } else if (spTipoInsp.getSelectedItemPosition() == INSP_OTROS) {
            cab.setCentroCosto(GetCodSpinerMaqCC("CC"));
            cab.setCod_maquina(txtProblemadetect.getText().toString());
        }
        if (spTipoInsp.getSelectedItemPosition() == INSP_OTROS) {
            cab.setTipoInspeccion("OT");
        } else {
            cab.setTipoInspeccion("MQ");
        }

        cab.setComentario(txtArea.getText().toString());
        cab.setUsuarioInsp(codUser);
        cab.setUsuarioEnvio(codUser);
        if (TipoGuardado == SOLO_GUARDAR) {
            cab.setEstado("I");
            cab.setFechaEnvia("-");
        } else {
            cab.setEstado("E");
            cab.setFechaEnvia(FechaFormatEng(FechaActual()));
        }
        cab.setUltUsuario(codUser);
        cab.setUltFechaMod(FechaFormatEng(FechaActual()));
        return cab;
    }

    public String GetCodSpinerMaqCC(String tip) {
        String result = "";
        String str = "";
        if (tip.equals("MAQ")) {
            str = spMaqCC.getSelectedItem().toString();
            result = str.substring(0, 7);
            result = result.trim();
        } else if (tip.equals("CC")) {

            str = spMaqCC.getSelectedItem().toString();
            result = str.substring(0, 5);
            result = result.trim();
        }
        Log.i("Get cod spiner => ", result);
        return result;
    }

    public void GuardarImagenServer(ArrayList<InspeccionGenDetalle> listDet) {
        Log.i("Metodo Guardar  ", "pass");
        String fileCarp = Environment.getExternalStorageDirectory() +"/LysConfig/Fotos/";
        for (int i = 0; i < listDet.size(); i++) {
            if (listDet.get(i).getRutaFoto() == null || listDet.get(i).getRutaFoto().equals("")) {

            } else {
                Log.i("Metodo GuardarImagen == >", listDet.get(i).getRutaFoto());
                String fileName = listDet.get(i).getRutaFoto();
                String filePath = fileCarp + fileName;
                File file = new File(filePath);

                byte[] bytes = new byte[(int) file.length()];
                try {
                    bytes = FileUtils.readFileToByteArray(file);
                    AsyncTask asyncTask = null;
                    GuardarImagenTask guardarImagenTask = new GuardarImagenTask(bytes, listDet.get(i).getRutaFoto());
                    Log.i("Parmaetro exex GuardarTask ===>", listDet.get(i).getRutaFoto());
                    asyncTask = guardarImagenTask.execute();
                    String resp = (String) asyncTask.get();
                    Log.i("foto guardada ===>", resp);

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
    public void SeleccionarOpcionDeGuardar() {

        final CharSequence[] items = {" Solo guardar", "Guardar y enviar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(InspeccionGen.this);
        builder.setTitle("Seleccione la opción que desea realizar");
        builder.setIcon(R.drawable.icn_save32);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (tipoMant.equals("NEW")) {
                    GuardarReporte(item);
                } else if (tipoMant.equals("Editar")) {
                    ActualizarReporte(item);
                } else if (tipoMant.equals("Visor")) {
                    CreateCustomToast("No se puede  modificar el reporte porque ya fue enviado ", Constans.layot_warning, Constans.layot_warning);
                }
                dialog.dismiss();

            }
        }).show();
    }

    public void ResizeAndSaveImage(Bitmap bitmap) {

        Bitmap foto = bitmap;
        foto = Bitmap.createScaledBitmap(bitmap, 600, 600, true);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String filename = GenerarCodigoFoto();

        File direct = new File(Environment.getExternalStorageDirectory() + Constans.Carpeta_foto);


        File f = new File(Environment.getExternalStorageDirectory() + File.separator + Constans.Carpeta_foto + filename + ".jpg");
        if (!direct.exists()) {
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + Constans.Carpeta_foto);
            wallpaperDirectory.mkdirs();
        }
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.flush();
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        detalleAdapter.SetRutaFotoIndex(filename + ".jpg", postItemFoto);

    }


    public String GenerarCodigoFoto() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
        String code = df.format(c.getTime());
        //code = codMaquina+""+code;
        if (spTipoInsp.getSelectedItemPosition() == INSP_OTROS) {
            code = "OT-" + code;
        } else {
            code = "MQ-" + code;
        }
        return code;
    }

    public void LoadListView(String correlativo, String tipoSincro) {
        ProdMantDataBase db = new ProdMantDataBase(InspeccionGen.this);
        InspeccionGenCabecera cab = new InspeccionGenCabecera();
        ArrayList<InspeccionGenDetalle> detalles = new ArrayList<InspeccionGenDetalle>();
        int indexSPMaqCC = 0;
        if (tipoSincro.equals("Offline"))
        {
            cab = db.GetInspGenCabeceraPorCorrelativo(correlativo);

            if (cab != null) {
                Log.i("Fecha Insp",cab.getFechaInsp());
                //SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                //String date = format.format(Date.parse(cab.getFechaInsp()));
                // cab.setFechaInsp(date);
                detalles = db.GetInspeccionGenDetallePorCorrelativo(correlativo);
                for (int i = 0 ; i< detalles.size();i++) {
                    String sTipoRevision = detalles.get(i).getTipoRevision() ;
                    String sTipoRevDescripcion =  "";
                    if (sTipoRevision.equals("01")){
                        sTipoRevDescripcion = "SERVICIO GENERAL";
                    }
                    else if (sTipoRevision.equals("02")){
                        sTipoRevDescripcion = "MECÁNICO";
                    }
                    else if (sTipoRevision.equals("03")){
                        sTipoRevDescripcion = "ELÉCTRICO";
                    }
                    detalles.get(i).setDescripcionInspGen(sTipoRevDescripcion);
                    Log.i("detalle inspec tipo revision > ", detalles.get(i).getTipoRevision());
                }
            }


        } else {
            ArrayList<InspeccionGenCabecera> listCab = null;
            AsyncTask<String, String, ArrayList<InspeccionGenCabecera>> asyncCabecera;
            GetInspGenCabTask getInspeGenCabTask = new GetInspGenCabTask();

            try {
                asyncCabecera = getInspeGenCabTask.execute(xcorrelativo);
                listCab = asyncCabecera.get();
                //  asyncCabecera = getInspeGenCabTask.execute(xcorrelativo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (listCab != null) {
                cab = listCab.get(0);
                ArrayList<InspeccionGenDetalle> listdet = null;
                AsyncTask<String, String, ArrayList<InspeccionGenDetalle>> asynckDetalle;
                GetInspGenDetTask getInpGentDetTask = new GetInspGenDetTask();
                String strCurrentDate = cab.getFechaInsp();
                Log.i("fecha insp online " , cab.getFechaInsp());
                String  sNewFecha =strCurrentDate.substring(8,10) + "/"+strCurrentDate.substring(5,7)+"/"+strCurrentDate.substring(0,4);
                cab.setFechaInsp(sNewFecha);
                Log.i("fecha insp online " ,sNewFecha);

                try {
                    asynckDetalle = getInpGentDetTask.execute(xcorrelativo);
                    listdet = (ArrayList<InspeccionGenDetalle>) asynckDetalle.get();
                    if (listdet != null) {
                        detalles = listdet;
                        for (int i = 0 ; i< detalles.size();i++) {
                            String sTipoRevision = detalles.get(i).getTipoRevision() ;
                            String sTipoRevDescripcion =  "";
                            if (sTipoRevision.equals("01")){
                                sTipoRevDescripcion = "SERVICIO GENERAL";
                            }
                            else if (sTipoRevision.equals("02")){
                                sTipoRevDescripcion = "MECÁNICO";
                            }
                            else if (sTipoRevision.equals("03")){
                                sTipoRevDescripcion = "ELÉCTRICO";
                            }
                            detalles.get(i).setDescripcionInspGen(sTipoRevDescripcion);
                            Log.i("detalle inspec tipo revision > ", detalles.get(i).getTipoRevision());
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }


        }

        if (cab != null && detalles != null) {

            int indexSPTipo = 0;
            String getValueForSp ;
            String problemaDetec ;

            if (cab.getTipoInspeccion().equals("OT")) {
                indexSPTipo = 1;
                getValueForSp = cab.getCentroCosto();
                problemaDetec = cab.getCod_maquina();
            } else {
                indexSPTipo = 2;
                getValueForSp = cab.getCod_maquina();
                problemaDetec = cab.getCentroCosto();
            }
            spTipoInsp.setSelection(indexSPTipo);
            CambiarProblemaDetectado = false ;
            LoadSpinerMaqCC(indexSPTipo);
            indexSPMaqCC = SetSelectionItemSpMaqCC(getValueForSp);
            Log.i("Problema detectado ===> ", problemaDetec);

            txtArea.setText(cab.getComentario());
            lblInspector.setText(cab.getUsuarioInsp());
            txtFechaInsp.setText(cab.getFechaInsp());
            spMaqCC.setSelection(indexSPMaqCC);
            IndexSpMaq = indexSPMaqCC;
           txtProblemadetect.setText(problemaDetec);
            Log.i("text problema detectado" ,  txtProblemadetect.getText().toString());
            Log.i("Selection index maquina", String.valueOf(indexSPMaqCC));
            detalleAdapter = new DetalleGenAdapater(InspeccionGen.this, R.layout.inspeccion_general_det, detalles);
            LVInspGen.setAdapter(detalleAdapter);
        }
    }


    public void LoadListView() {
        ProdMantDataBase db = new ProdMantDataBase(InspeccionGen.this);
        ArrayList<TipoRevisionGBD> list = db.GetAllTipoReivision();
        ArrayList<InspeccionGenDetalle> data = new ArrayList<InspeccionGenDetalle>();
        for (int i = 0; i < list.size(); i++) {
            TipoRevisionGBD revision = list.get(i);
            InspeccionGenDetalle detalle = new InspeccionGenDetalle();
            detalle.setDescripcionInspGen(revision.getDescripcion());
            detalle.setTipoRevision(revision.getCod_tiporevision());
            Log.i("descripcion rev detalle" ,revision.getDescripcion());
            Log.i("descripcion rev cod detalle" ,revision.getCod_tiporevision());
            data.add(detalle);
        }
        detalleAdapter = new DetalleGenAdapater(InspeccionGen.this, R.layout.inspeccion_general_det, data);
        LVInspGen.setAdapter(detalleAdapter);
    }


    public int SetSelectionItemSpMaqCC(String valueSP) {
        int result = 0;
        for (int i = 0; i < spMaqCC.getCount(); i++) {

            String item = "";
            if (spTipoInsp.getSelectedItemPosition() == 1) {
                item = spMaqCC.getItemAtPosition(i).toString();
                item = item.substring(0, 5);
                item = item.trim();
                if (valueSP.equals(item)) {

                    result = i;
                    value_spiner_seletec = spMaqCC.getItemAtPosition(i).toString();
                }
            } else if (spTipoInsp.getSelectedItemPosition() == 2) {
                item = spMaqCC.getItemAtPosition(i).toString();
                item = item.substring(0, 7);
                item = item.trim();
                if (valueSP.equals(item)) {
                    //spMaqCC.setSelection(i);
                    result = i;
                    value_spiner_seletec = spMaqCC.getItemAtPosition(i).toString();
                }
            }
        }
        Log.i("Item maq seletec" , String.valueOf(result));
        return result;
    }

    public void AddTiporevision(String tipo_revision) {
        InspeccionGenDetalle res = null;
        ProdMantDataBase db = new ProdMantDataBase(InspeccionGen.this);
        ArrayList<TipoRevisionGBD> list = db.GetAllTipoReivision();
        for (int i = 0; i < list.size(); i++) {
            TipoRevisionGBD t = list.get(i);
            if (t.getCod_tiporevision().equals(tipo_revision)) {

                res = new InspeccionGenDetalle();
                res.setDescripcionInspGen(t.getDescripcion());
                res.setTipoRevision(t.getCod_tiporevision());
            }
        }
        if (res != null) {
            Log.i("tipo revision " , res.getDescripcionInspGen());
            boolean exist = detalleAdapter.AddObject(res);
            if (exist == false) {

                CreateCustomToast("Ya se agregó este tipo de revisión al detalle ", Constans.icon_warning, Constans.layot_warning);
            }
        }
    }

    public CharSequence[] GettListTipoRevision() {
        ProdMantDataBase db = new ProdMantDataBase(InspeccionGen.this);
        ArrayList<TipoRevisionGBD> listTipoRev = db.GetAllTipoReivision();
        CharSequence[] result = new CharSequence[listTipoRev.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = listTipoRev.get(i).getCod_tiporevision() + "  -  " + listTipoRev.get(i).getDescripcion();
        }
        return result;
    }

    public void LoadSpinerTipoInsp() {
        ArrayList<String> listTipIns = new ArrayList<>();
        listTipIns.add("-SELECCIONE-");
        listTipIns.add("OTRO");
        listTipIns.add("MAQUINA");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(InspeccionGen.this, android.R.layout.simple_spinner_dropdown_item, listTipIns);
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
            var_tipoIsnpeccion = INSP_OTROS;
            liscCcosto = db.GetCemtroCostos();
            msjPrompt = "- SELECCIONE CENTRO DE COSTO -";
            data.add(msjPrompt);
            lblSPCCMaq.setText("C.C.:");
            txtProblemadetect.setEnabled(true);
            txtProblemadetect.setText("");
            for (int i = 0; i < liscCcosto.size(); i++) {
                data.add(liscCcosto.get(i).getC_centrocosto() + "  -   " + liscCcosto.get(i).getC_descripcion());
            }

        } else if (selecTipoInsp == INSP_MAQUINA) {
            var_tipoIsnpeccion = INSP_MAQUINA;
            lblSPCCMaq.setText("MAQ:");
            msjPrompt = "- SELECCIONE MAQUINA -";
            txtProblemadetect.setEnabled(false);
            txtProblemadetect.setText("");
            data = new ArrayList<String>();
            data.add(msjPrompt);
            listMaq = db.GetMaquinasALL();
            for (int i = 0; i < listMaq.size(); i++) {
                data.add(listMaq.get(i).getC_maquina() + "  -  " + listMaq.get(i).getC_descripcion());
            }
        } else {
            data = new ArrayList<String>();
            data.add("-SELECCIONE-");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(InspeccionGen.this, android.R.layout.simple_spinner_dropdown_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMaqCC.setAdapter(adapter);
        spMaqCC.setEnabled(true);
        if (IndexSpMaq>0){
            spMaqCC.setSelection(IndexSpMaq);
        }
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
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String res = df.format(cal.getTime());
        Log.i("fecha actual >>>" , res);
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
        txtDComentario.setText(txtvar.getText().toString());
        dialog.show();

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

    public void AsignarCodCcostoTexBox(String codMaq) {
        Log.i("Cod Maquina", codMaq);
        if (var_tipoIsnpeccion == INSP_MAQUINA) {
            ProdMantDataBase db = new ProdMantDataBase(InspeccionGen.this);
            MaquinaDB mq = db.GetMaquinaPorCodigoMaquina(codMaq);
            txtProblemadetect.setText(mq.getC_centrocosto());
        }
    }

    public void AlertAddDetail() {
        if (tipoMant.equals("Visor")){
            CreateCustomToast("No se puede realizar esta acción" , Constans.icon_warning,Constans.layot_warning);
            return;
        }
        final CharSequence[] items = listTipoRevision;

        AlertDialog.Builder builder = new AlertDialog.Builder(InspeccionGen.this);
        builder.setTitle("Seleccione el tipo de rivisión");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                String cod_tipo_rev = String.format("%02d", (item + 1));
                AddTiporevision(cod_tipo_rev);

                dialog.dismiss();
            }
        }).show();
    }

    public static class ViewHolder {
        private TextView lblDescripcion;
        private EditText txtComentario;
        private ImageView imgfoto;
        private ImageView imgEliminar;
        private int index;

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
        Toast toast = new Toast(InspeccionGen.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public boolean ValidarCabecera() {
        boolean result = true;
        if (spTipoInsp.getSelectedItemPosition() == 0) {
            result = false;
            CreateCustomToast("Seleccione tipo de inspección ", Constans.icon_error, Constans.layout_error);
        }
        return result;
    }

    public boolean ValidarDetalle() {
        boolean result = true;
        if (detalleAdapter == null || detalleAdapter.data.size() == 0) {
            result = false;
            CreateCustomToast("Debe agregar al menos un detalle  a la inspección", Constans.icon_error, Constans.layout_error);
        }
        return result;

    }

    public String FechaFormatEng(String stringdate) {
        Log.i("fecha format input > ", stringdate);
        String inputPattern = "dd/MM/yyyy";
        String outputPattern = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(stringdate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("fecha format > ", str);
        return str;


    }

    private void SaveAndScan(File directory, byte[] data)
    {
        String fileName = new SimpleDateFormat("'JPEG_'yyyyMMdd_HHmmss'.jpg'")
                .format(System.currentTimeMillis());
        File file = new File(directory, fileName);
        try{
            directory.mkdirs();
            OutputStream os = new FileOutputStream(file);
            os.write(data);
            os.close();

            MediaScannerConnection.scanFile(this, new String[ ] { file.toString() },
                    null, new MediaScannerConnection.OnScanCompletedListener()
                    {
                        @Override
                        public void onScanCompleted(String path, Uri uri)
                        {
                            Log.d("save picture to ", "savePictureTo: scanned path=" + path + ", uri=" + uri);
                        }
                    });
        }
        catch(IOException e){
            Log.w("save picture to", "savePictureTo: error writing " + file, e);
        }
    }


    public void showImage(String filename) {
        File filefoto = null;
        Bitmap bitmap = null;

        if (tipoSincro.equals("Offline")) {
        String filePath = Environment.getExternalStorageDirectory() +"/LysConfig/Fotos/" + File.separator + filename;


             Log.i("Ruta Foto" , filePath);
            bitmap = BitmapFactory.decodeFile(filePath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
        }
        else if (tipoSincro.equals("Online")) {
            AsyncTask<String, String, byte[]> asynckGetFoto;
            GetFotoTask getFotoTask = new GetFotoTask();
            byte[] bytes = null;
            try {
                asynckGetFoto = getFotoTask.execute(filename);
                bytes = (byte[]) asynckGetFoto.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            AsyncTask<byte[], String, String> asyncSaveImglocal;
            GuardarImgLocalTask guardarImgLocalTask = new GuardarImgLocalTask(filename, bytes);

            try {
                asyncSaveImglocal = guardarImgLocalTask.execute(bytes);
                String resSaveImg = (String) asyncSaveImglocal.get();
                Log.i("result save imag local => ", resSaveImg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            String filePath2 = Environment.getExternalStorageDirectory() +"/LysConfig/Fotos/" + File.separator+ filename;
            bitmap = BitmapFactory.decodeFile(filePath2);

        }
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
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    public class DetalleGenAdapater extends ArrayAdapter<InspeccionGenDetalle> {

        Context context;
        int recosurceid;
        ArrayList<InspeccionGenDetalle> data;

        public DetalleGenAdapater(Context context, int resource, ArrayList<InspeccionGenDetalle> data) {
            super(context, resource, data);
            this.context = context;
            this.recosurceid = resource;
            this.data = data;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            InspeccionGenDetalle inp = data.get(position);

            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                convertView = inflater.inflate(recosurceid, parent, false);
                viewHolder = new ViewHolder();

                viewHolder.lblDescripcion = (TextView) convertView.findViewById(R.id.lblInspGDescripcion);
                viewHolder.txtComentario = (EditText) convertView.findViewById(R.id.txtInspGComent);
                if (tipoMant.equals("Visor")){
                    viewHolder.txtComentario.setEnabled(false);
                    viewHolder.txtComentario.setTextColor(Color.BLACK);
                }
                viewHolder.imgfoto = (ImageView) convertView.findViewById(R.id.imgInspGenCam);
                viewHolder.imgEliminar = (ImageView) convertView.findViewById(R.id.imgInspGenEliminar);
                viewHolder.index = position;
                data.get(position).setLinea(String.valueOf(position + 1));
                data.get(position).setCompania(Constans.NroConpania);
                viewHolder.txtComentario.setText(data.get(position).getComentario());
                convertView.setTag(viewHolder);
            } else {

                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.lblDescripcion.setText(data.get(position).getDescripcionInspGen());
            if (inp.getRutaFoto().equals("") || inp.getRutaFoto().equals("anyType{}")) {
                viewHolder.imgfoto.setImageResource(R.drawable.icn_camera_32);
            } else {
                viewHolder.imgfoto.setImageResource(R.drawable.icn_camera_ok);
            }

            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.imgEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tipoMant.equals("Visor")){
                        return;
                    }
                    data.remove(position);
                    detalleAdapter = new DetalleGenAdapater(InspeccionGen.this, R.layout.inspeccion_general_det, data);
                    LVInspGen.setAdapter(detalleAdapter);
                    /*if (position>0) {
                        finalViewHolder.index = position-1;
                    }
                    detalleAdapter.notifyDataSetChanged();*/
                }
            });

            final EditText var_editex = viewHolder.txtComentario;
            viewHolder.txtComentario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ShowCometarioCabDialog(position, var_editex);
                }
            });

            viewHolder.imgfoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tipoMant.equals("NEW") || tipoMant.equals("Editar")) {
                    postItemFoto = position;
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    } else if (data.get(position).getRutaFoto().length() > 0 && tipoMant.equals("Visor")) {
                        Log.i("show foto ruta foto" , data.get(position).getRutaFoto());
                        showImage(data.get(position).getRutaFoto());
                    }
                }
            });
            return convertView;
        }

        public ArrayList<InspeccionGenDetalle> Alldata() {

            return data;
        }
        public void ShowCometarioCabDialog(final int pos, final EditText txtComent) {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_coment_layout);
            dialog.setTitle("Comentario");
            final EditText txtDComentario = (EditText) dialog.findViewById(R.id.txtDialogCom);
            txtDComentario.setText(data.get(pos).getComentario());
            Button btnSsalir = (Button) dialog.findViewById(R.id.btnDiaglogSalir);
            Button btnAceptar = (Button) dialog.findViewById(R.id.btnDialogOK);
            dialog.show();
            btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String comment = txtDComentario.getText().toString();
                    data.get(pos).setComentario(comment);
                    txtComent.setText(comment);
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
        public void SetRutaFotoIndex(String rutaFoto, int index) {
            data.get(index).setRutaFoto(rutaFoto);
            this.notifyDataSetChanged();
        }
        public boolean AddObject(InspeccionGenDetalle det) {
            boolean result = true;
            for (int i = 0; i < data.size(); i++) {
                InspeccionGenDetalle d = data.get(i);
                if (d.getTipoRevision().equals(det.getTipoRevision())) {
                    result = false;
                    break;
                } else {
                    result = true;
                }
            }
            if (result == true) {
                data.add(det);
                Log.i("tipo revision det", det.getDescripcionInspGen());
                this.notifyDataSetChanged();
            }
            return result;
        }
    }
}