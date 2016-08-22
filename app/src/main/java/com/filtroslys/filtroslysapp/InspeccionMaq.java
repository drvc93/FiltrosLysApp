package com.filtroslys.filtroslysapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import DataBase.InspeccionDB;
import DataBase.MaquinaDB;
import DataBase.PeriodoInspeccionDB;
import DataBase.ProdMantDataBase;
import Model.InspeccionMaqCabecera;
import Model.InspeccionMaqDetalle;
import Tasks.EnviarInspMaqCabTask;
import Tasks.EnviarInspMaqDetTask;
import Tasks.GetCorrelativoTask;
import Tasks.GuardarImagenTask;
import Tasks.TransferirInspeccionTask;
import Util.Constans;

public class InspeccionMaq extends AppCompatActivity {

    TextView lblInspector, lblFechaInicio, lblFechaFin, lblEstadoCaB;
    String correlativo_update = "";
    int SOLO_GUARDAR = 0;
    int GUARDAR_Y_ENVIAR_ = 1;
    String indexPeriodoInspeccion="";

    SharedPreferences preferences;
    private static final int CAMERA_REQUEST = 1888;
    DetalleMaqAdapter detalleMaqAdap;
    ArrayList<InspeccionMaqDetalle> dataGlobal;
    int posCamara;
    String comentDialog = "";
    String tipoMant = "";
    EditText txtComentario;
    String codUser, codMaquina, NomMaquina, FamMaquina;
    Spinner spPeriodo, spCondMaq;
    ListView LVdetalleM;
    ArrayList<PeriodoInspeccionDB> listPeriodos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeccion_maq);
        ActionBar actionBar = getSupportActionBar();

        if (ChangeOrientationScreen() < 6) {

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            //  getWindow().setStatusBarColor(Color.BLACK);
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(InspeccionMaq.this);
        codUser = preferences.getString("UserCod", null);
        codMaquina = preferences.getString("CodMaquina", null);
        NomMaquina = preferences.getString("NomMaquina", null);
        FamMaquina = preferences.getString("FamMaquina", null);

        tipoMant = getIntent().getExtras().getString("tipoMant");

        // lblMaquina = (TextView)findViewById(R.id.lblMaquina);
        lblInspector = (TextView) findViewById(R.id.lblnspector);
        spCondMaq = (Spinner) findViewById(R.id.spCondMaquina);
        LVdetalleM = (ListView) findViewById(R.id.LVDetInspM);
        spPeriodo = (Spinner) findViewById(R.id.spPeriodo);
        txtComentario = (EditText) findViewById(R.id.txtCometario);
        lblFechaInicio = (TextView) findViewById(R.id.lblFechaInicio);
        lblFechaFin = (TextView) findViewById(R.id.lblFechaFin);
        lblEstadoCaB = (TextView) findViewById(R.id.txtEstadoCab);


        if (tipoMant.equals("NEW")) {
            CargarCabecera();
        } else {
            String correlativo = getIntent().getExtras().getString("Xcorrelativo");
            String codMaquina = getIntent().getExtras().getString("XcodMaq");
            correlativo_update = correlativo;
            CargarCabecera(correlativo, codMaquina);

        }


        txtComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowCometarioCabDialog();
            }
        });

        spPeriodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {

                    NumberFormat format = new DecimalFormat("00");
                    String strNumerFormat = format.format(i);
                    indexPeriodoInspeccion = strNumerFormat;
                    Log.d("Number perdiodo => ", strNumerFormat);
                    // Toast.makeText(InspeccionMaq.this, strNumerFormat, Toast.LENGTH_SHORT).show();
                    if (tipoMant.equals("NEW")) {
                        InsertRowsListView(strNumerFormat);
                    }
                }
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
    public void onBackPressed() {
        AlerExit();
    }

    public void AlerExit() {
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
        lblFechaInicio.setText(FechaActual());
    }

    public void CargarCabecera(String correlativo, String codMaquina) {

        HashMap<Integer, Integer> xindexSpiner = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> xindexIconComent = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> xindexIconFoto = new HashMap<Integer, Integer>();
        HashMap<Integer, Boolean> xindexEnableSpiner = new HashMap<Integer, Boolean>();
        HashMap<Integer, String> xindexvaluesTextBoxs = new HashMap<Integer, String>();

        ProdMantDataBase db = new ProdMantDataBase(InspeccionMaq.this);
        MaquinaDB m = db.GetMaquinaPorCodigoMaquina(codMaquina);
        setTitle(m.getC_maquina() + " - " + m.getC_descripcion());
        LoadSpinerCondicionMaquina();
        LoadSpinnerPeriodo();

        // obteniendo  cabacera
        InspeccionMaqCabecera inpCab = db.GetInspMaqCabeceraPorCorrelativo(correlativo);
        if (inpCab.getCondicionMaq().equals("A")) {
            spCondMaq.setSelection(1);
        } else {
            spCondMaq.setSelection(2);
        }
        if (inpCab.getEstado().equals("E")) {
            lblEstadoCaB.setText("Enviada");
        } else {
            lblEstadoCaB.setText("Ingresada");
        }


        txtComentario.setText(inpCab.getComentario());
        spPeriodo.setSelection(Integer.valueOf(inpCab.getPeriodoInsp()));
        lblFechaInicio.setText(inpCab.getFechaInicioInsp());
        lblFechaFin.setText(inpCab.getFechFinInsp());
        lblInspector.setText(inpCab.getUsuarioInsp());
        // fin cabecera

        // obteniendo detalle
        ArrayList<InspeccionMaqDetalle> InspDet = db.GetInspeccionMaqDetallePorCorrelativo(correlativo);
        for (int i = 0; i < InspDet.size(); i++) {
            String descripcion = db.GetDescripcionInspPorCodigo(InspDet.get(i).getCod_inspeccion());
            InspDet.get(i).setDescripcionInspecion(descripcion);

        }

        // llenar hashmaps

        for (int i = 0; i < InspDet.size(); i++) {

            InspeccionMaqDetalle d = InspDet.get(i);
            Double porcent = Double.parseDouble(d.getPorcentMax());
            if (d.getEstado().equals("O")) {
                xindexSpiner.put(i, 1);
            } else if (d.getEstado().equals("F")) {
                xindexSpiner.put(i, 2);
            }
            if (porcent.intValue() > 0) {
                xindexEnableSpiner.put(i, false);
            } else {
                xindexEnableSpiner.put(i, true);
            }
            if (d.getComentario().equals("")) {
                xindexIconComent.put(i, R.drawable.icn_pencil_32);
            } else {
                xindexIconComent.put(i, R.drawable.comentario_ok_32);
            }
            if (d.getRutaFoto().equals("")) {
                xindexIconFoto.put(i, R.drawable.icn_camera_32);
            } else {
                xindexIconFoto.put(i, R.drawable.icn_camera_ok);
            }
            if (InspDet.get(i).getPorcentInspec().equals("")) {
                xindexvaluesTextBoxs.put(i, "");
            } else {
                xindexvaluesTextBoxs.put(i, InspDet.get(i).getPorcentInspec());
            }

        }

        detalleMaqAdap = new DetalleMaqAdapter(InspeccionMaq.this, R.layout.inspeccion_maquina_det, InspDet);
        detalleMaqAdap.indexSpiner = xindexSpiner;
        detalleMaqAdap.indexEnableSpiner = xindexEnableSpiner;
        detalleMaqAdap.indexIconComent = xindexIconComent;
        detalleMaqAdap.indexIconFoto = xindexIconFoto;
        detalleMaqAdap.indexvalueTexBoxs = xindexvaluesTextBoxs;
        LVdetalleM.setAdapter(detalleMaqAdap);

    }


    public  String  FechaActual(){

        Calendar  cal = Calendar.getInstance();
        SimpleDateFormat df =  new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String res = df.format(cal.getTime());
        return  res;
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
                inpDet.setCod_inspeccion(inp.getC_inspeccion());
                inpDet.setPorcentMin(inp.getN_porcentajeminimo());
                inpDet.setPorcentMax(inp.getN_porcentajemaximo());

                listInspMaqDet.add(inpDet);

            }

             detalleMaqAdap = new DetalleMaqAdapter(InspeccionMaq.this,R.layout.inspeccion_maquina_det,listInspMaqDet);
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

    private   static  class  ViewHolder {
        private TextView lblDescripInsp;
        private TextView lblTipoInsp;
        private TextView lblPorcenMin;
        private TextView lblPorcenMax;
        private EditText txtPorcentInsp;
        private Spinner   spEstado;
        private ImageView  imgFoto;
        private ImageView imgComent;
        private int index;


    }

    public class DetalleMaqAdapter extends ArrayAdapter<InspeccionMaqDetalle> {
        public HashMap<Integer, Integer> indexSpiner = new HashMap<Integer, Integer>();
        public HashMap<Integer, Integer> indexIconComent = new HashMap<Integer, Integer>();
        public HashMap<Integer, Integer> indexIconFoto = new HashMap<Integer, Integer>();
        public HashMap<Integer, Boolean> indexEnableSpiner = new HashMap<Integer, Boolean>();
        public HashMap<Integer, String> indexvalueTexBoxs = new HashMap<Integer, String>();

        SharedPreferences preferences;
        Context context;
        int resource ;
        ArrayList<InspeccionMaqDetalle> data;
        public DetalleMaqAdapter(Context context, int resource, ArrayList<InspeccionMaqDetalle> data) {
            super(context, resource, data);
            this.context= context;
            this.resource = resource;
            this.data = data;
            LoadHaspIconComentFoto();
            preferences= PreferenceManager.getDefaultSharedPreferences(context);
            FillHashMapEnableSpiner();
            FillHashMapTextValues();
        }



        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            final InspeccionMaqDetalle inp = data.get(position);
            ArrayList<String> listEstados = new ArrayList<String>();
            listEstados.add("--Selecccione--");
            listEstados.add("OK");
            listEstados.add("FALLA");


            if (convertView==null){

                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                convertView = inflater.inflate(resource,parent,false);
                viewHolder = new ViewHolder();

                viewHolder.lblDescripInsp = (TextView)convertView.findViewById(R.id.lblInpecDescDet);
                viewHolder.lblTipoInsp = (TextView)convertView.findViewById(R.id.lblDetTipInsp);
                viewHolder.lblPorcenMin = (TextView)convertView.findViewById(R.id.lblDetPorcMin);
                viewHolder.lblPorcenMax = (TextView)convertView.findViewById(R.id.lblDetPorcMax);
                viewHolder.txtPorcentInsp = (EditText)convertView.findViewById(R.id.txtPorcInsp);
                viewHolder.txtPorcentInsp.setInputType(InputType.TYPE_CLASS_NUMBER);
                viewHolder.index = position;

                viewHolder.spEstado  = (Spinner)convertView.findViewById(R.id.spDetEsado);
                viewHolder.imgFoto = (ImageView)convertView.findViewById(R.id.imgDetFoto);
                viewHolder.imgComent = (ImageView)convertView.findViewById(R.id.imgDetComent);
                viewHolder.spEstado.setEnabled(indexEnableSpiner.get(position));




                convertView.setTag(viewHolder);

            }
            else {

                viewHolder= (ViewHolder)convertView.getTag();
            }

            ArrayAdapter<String> arrayAdapterEstado = new ArrayAdapter<String>(context,R.layout.spiner_layout_estados,listEstados);
            arrayAdapterEstado.setDropDownViewResource(R.layout.spiner_layout_estados);
            viewHolder.spEstado.setAdapter(arrayAdapterEstado);


            viewHolder.lblDescripInsp.setText(inp.getDescripcionInspecion());
            viewHolder.lblPorcenMin.setText(inp.getPorcentMin());
            viewHolder.lblPorcenMax.setText(inp.getPorcentMax());
            viewHolder.lblTipoInsp.setText(inp.getTipo_inspecicon());
            final Double porcent = Double.parseDouble(inp.getPorcentMax());
            viewHolder.imgComent.setImageResource(indexIconComent.get(position));
            viewHolder.imgFoto.setImageResource(indexIconFoto.get(position));
            final ImageView imgv = viewHolder.imgComent;

            if (porcent.intValue()>0){
                Log.i("Porcent and position >",String.valueOf(porcent.intValue()) + " - " + String.valueOf(position) );
                viewHolder.txtPorcentInsp.setEnabled(true);
                viewHolder.txtPorcentInsp.setHint("LLenar");


                indexEnableSpiner.put(position,false);
            }
            else {
                viewHolder.txtPorcentInsp.setEnabled(false);
                viewHolder.txtPorcentInsp.setHint("");

            }

            if (data.get(viewHolder.index).getPorcentInspec().length() > 0) {
                Double var_porcent = Double.parseDouble(indexvalueTexBoxs.get(viewHolder.index));
                viewHolder.txtPorcentInsp.setText(String.valueOf(var_porcent.intValue()));
            }

            viewHolder.spEstado.setEnabled(indexEnableSpiner.get(position));



            if (indexSpiner.get(position) != null) {
                viewHolder.spEstado.setSelection(indexSpiner.get(position));
            }

            if ( data.get(position).getComentario().equals("")){

            }
            else {

                indexIconComent.put(position,R.drawable.comentario_ok_32);

            }

            if (data.get(position).getRutaFoto().equals("")){

            }
            else {
                indexIconFoto.put(position,R.drawable.icn_camera_ok);
            }


            final Spinner sp_aux = viewHolder.spEstado;
            final int var_index = viewHolder.index;
            viewHolder.txtPorcentInsp.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.equals("") || charSequence.length()==0){
                        Log.i("no thing .", "");

                    }
                    else {
                        String varChar = String.valueOf(charSequence);
                        data.get(var_index).setPorcentInspec(varChar);
                        if (Integer.valueOf(varChar) > porcent.intValue()) {
                            indexSpiner.put(position, 2);
                            sp_aux.setSelection(2);
                            indexvalueTexBoxs.put(var_index, varChar);
                            // Log.i("var_porcentIns .",charSequence);
                        }

                        else {
                            indexSpiner.put(position,1);
                            sp_aux.setSelection(1);
                        }
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            viewHolder.spEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    indexSpiner.put(position,i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            viewHolder.imgComent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowCometarioCabDialog(position,imgv );
                }
            });


            viewHolder.spEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    indexSpiner.put(position, i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            viewHolder.imgComent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowCometarioCabDialog(position, imgv);
                }
            });


            viewHolder.imgFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    posCamara = position;
                    dataGlobal = data;
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent,CAMERA_REQUEST);
                }
            });

            if (indexvalueTexBoxs.get(position).length() > 0) {
                Double x_porcent = Double.parseDouble(indexvalueTexBoxs.get(position));


            }
            return convertView;
        }


        public void SetTextEditText(EditText txtEdit, int position) {

            if (indexvalueTexBoxs.get(position) == null || indexvalueTexBoxs.get(position).equals("")) {
                txtEdit.setText("");
            } else {
                Double x_porcent = Double.parseDouble(indexvalueTexBoxs.get(position));

                txtEdit.setText(String.valueOf(x_porcent.intValue()));

            }

        }
        public  void  ShowCometarioCabDialog (final int pos, final ImageView imgV){

            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_coment_layout);
            dialog.setTitle("Comentario");


            final EditText txtDComentario = (EditText)dialog.findViewById(R.id.txtDialogCom);
            txtDComentario.setText(data.get(pos).getComentario());




            Button btnSsalir = (Button) dialog.findViewById(R.id.btnDiaglogSalir);
            Button btnAceptar = (Button) dialog.findViewById(R.id.btnDialogOK);
            dialog.show();



            btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String comment =  txtDComentario.getText().toString();
                    data.get(pos).setComentario(comment);
                    imgV.setImageResource(R.drawable.comentario_ok_32);
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

        public  HashMap<Integer,Integer> GetIndexsSpiner (){

            return  indexSpiner;
        }
        public  ArrayList<InspeccionMaqDetalle> AllItemsDetalle (){
            return  data;
        }
        public  void  LoadHaspIconComentFoto (){

            for (int i = 0; i < data.size() ; i++) {
                indexIconComent.put(i,R.drawable.icn_pencil_32);
                indexIconFoto.put(i,R.drawable.icn_camera_32);
            }
        }

        public void FillHashMapTextValues() {

            for (int i = 0; i < data.size(); i++) {
                indexvalueTexBoxs.put(i, "");

            }
        }

        public  void  FillHashMapEnableSpiner (){
            for (int i = 0; i <data.size() ; i++) {

                indexEnableSpiner.put(i,true);
            }
        }


    }

    public void  ResizeAndSaveImage ( Bitmap bitmap){

        Bitmap foto =  bitmap;
        foto= Bitmap.createScaledBitmap(bitmap,600,600,true);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.JPEG,100,bytes);
        String  filename = GenerarCodigoFoto();

        File direct = new File(Environment.getExternalStorageDirectory() + Constans.Carpeta_foto);


        File f = new File(Environment.getExternalStorageDirectory()+File.separator+Constans.Carpeta_foto+filename+".jpg");
        if (!direct.exists()) {
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory()+File.separator+Constans.Carpeta_foto);
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

        dataGlobal.get(posCamara).setRutaFoto(filename+".jpg");

        ((BaseAdapter) LVdetalleM.getAdapter()).notifyDataSetChanged();

    }

    public  String GenerarCodigoFoto (){

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
        String code = df.format(c.getTime());
        code = codMaquina+""+code;
        return   code;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inspeccion_maq, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Guardar) {

            if (tipoMant.equals("Visor")) {
                CreateCustomToast("No se puede realizar cambios porque el reporte ya fue enviado", Constans.icon_warning, Constans.layot_warning);
            } else {
                SeleccionarOpcionDeGuardar();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void  SeleccionarOpcionDeGuardar() {


        final CharSequence[] items = { " Solo guardar", "Guardar y enviar" };

        AlertDialog.Builder builder = new AlertDialog.Builder(InspeccionMaq.this);
        builder.setTitle("Seleccione la opción que desea realizar");
        builder.setIcon(R.drawable.icn_save32);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

             //   Toast.makeText(InspeccionMaq.this, String.valueOf(item), Toast.LENGTH_SHORT).show();
                if (tipoMant.equals("NEW")) {
                Guardar(item);
                } else if (tipoMant.equals("Editar")) {
                    Actualizar(item);

                }
                dialog.dismiss();

            }
        }).show();
    }

    public void Actualizar(int tipoGuardado) {

        int cont = 0;
        ProdMantDataBase db = new ProdMantDataBase(InspeccionMaq.this);
        InspeccionMaqCabecera cabecerasEnvio = new InspeccionMaqCabecera();
        ArrayList<InspeccionMaqDetalle> detallesEnvio = new ArrayList<InspeccionMaqDetalle>();
        if (ValidarCabecera() == true && ValidarDetalle() == true) {
            InspeccionMaqCabecera cab = GetCabecera(tipoGuardado);
            cab.setCorrlativo(correlativo_update);
            cabecerasEnvio = cab;
            boolean result = db.deleteInspMaq(correlativo_update);
            long rowid = db.InsertInspeccionMaqCab(cab);
            if (rowid > 0) {
                Log.i("delete =>", String.valueOf(result));
                for (int i = 0; i < detalleMaqAdap.getCount(); i++) {
                    InspeccionMaqDetalle det = GetDetalle(Long.valueOf(correlativo_update), i);
                    detallesEnvio.add(det);
                    long detalleID = db.InsertInspecciomMaqDet(det);
                    Log.i("ID DETALLE INSP MAQ > ", String.valueOf(detalleID));
                    cont = cont + 1;
                }
            }

            if (tipoGuardado == SOLO_GUARDAR) {
                if (cont > 0) {
                    CreateCustomToast("Se actualizo correctamente", Constans.icon_succes, Constans.layout_success);
                    super.onBackPressed();
                }

            } else if (tipoGuardado == GUARDAR_Y_ENVIAR_) {
                EnviarInspeccion(cabecerasEnvio, detallesEnvio);
                super.onBackPressed();

            }


        }

    }

    public  void  Guardar (int tipoGuardado){

        int cont  = 0;
        ProdMantDataBase db = new ProdMantDataBase(InspeccionMaq.this);
        InspeccionMaqCabecera cabecerasEnvio = new InspeccionMaqCabecera() ;
        ArrayList<InspeccionMaqDetalle> detallesEnvio = new ArrayList<InspeccionMaqDetalle>();

            if (ValidarCabecera()==true && ValidarDetalle()==true){

               InspeccionMaqCabecera cab = GetCabecera(tipoGuardado);
                cabecerasEnvio = cab;
                long rowid = db.InsertInspeccionMaqCab(cab);
                Log.i("id cabecera >" , String.valueOf(rowid));
                Log.i("Validado :" ," SI");
                if (rowid>0){

                    for (int i = 0; i < detalleMaqAdap.getCount() ; i++) {

                        InspeccionMaqDetalle det = GetDetalle(rowid,i);
                        detallesEnvio.add(det);
                        long detid = db.InsertInspecciomMaqDet(det);
                        Log.i("id detalle >" , String.valueOf(detid));
                         cont  = cont+1;

                    }

                }

                if (tipoGuardado==SOLO_GUARDAR){
                    if (cont>0){
                        CreateCustomToast("Se guardo correctamente", Constans.icon_succes, Constans.layout_success);
                        super.onBackPressed();
                    }

                }
                else if (tipoGuardado==GUARDAR_Y_ENVIAR_){
                    EnviarInspeccion(cabecerasEnvio,detallesEnvio);
                    super.onBackPressed();

                }

            }



    }


    public void EnviarInspeccion(InspeccionMaqCabecera cabecera, ArrayList<InspeccionMaqDetalle> listdetalles) {

        String correlativo = "";
        String resulInsertCab = "";

        // obetener correlativo
        GetCorrelativoTask getCorrelativoTask = new GetCorrelativoTask();
        AsyncTask<String,String,String> asyncTaskCorrelativo ;

        try {
            asyncTaskCorrelativo = getCorrelativoTask.execute();
            correlativo = (String) asyncTaskCorrelativo.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // insertar cabecera
        AsyncTask<String,String,String> asyncTaskCabeceraInsp ;
        EnviarInspMaqCabTask enviarInspMaqCabTask = new EnviarInspMaqCabTask();


        try {
            asyncTaskCabeceraInsp = enviarInspMaqCabTask.execute(correlativo,cabecera.getCompania(),cabecera.getCodMaquina(),cabecera.getCondicionMaq(),
                    cabecera.getComentario(),cabecera.getEstado(),cabecera.getFechaInicioInsp(),cabecera.getFechFinInsp(),
                    cabecera.getPeriodoInsp(),cabecera.getUsuarioInsp(),cabecera.getUsuarioEnv(),cabecera.getUltUsuairo());
            resulInsertCab = (String)  asyncTaskCabeceraInsp.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        int cont = 0;
        // insertar detalle
        for (int i = 0; i < listdetalles.size(); i++) {
            String res = "";
            InspeccionMaqDetalle d = listdetalles.get(i);
            if (d.getPorcentInspec().equals("")|| d.getPorcentInspec()==null){
                d.setPorcentInspec("0");
            }
            EnviarInspMaqDetTask enviarInspMaqDetTask = new EnviarInspMaqDetTask();
            AsyncTask <String,String,String> asyncDetalle ;
            asyncDetalle = enviarInspMaqDetTask.execute(d.getCompania(),correlativo,d.getLinea(),d.getCod_inspeccion(),d.getTipo_inspecicon(),
                             d.getPorcentMin(),d.getPorcentMax(),d.getPorcentInspec(),d.getEstado(),d.getComentario(),d.getRutaFoto(),d.getUltimoUser());
            try {
                res = (String) asyncDetalle.get();

                if (res.equals("OK")){
                    cont = cont +1;

                    Log.i("Detalle enviado nº >", String.valueOf(cont) );
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }

        AsyncTask<String, String, String> asyncTaskTranferirs;
        TransferirInspeccionTask transferirInspeccionTask = new TransferirInspeccionTask();

        try {
            asyncTaskTranferirs = transferirInspeccionTask.execute("MAQ", correlativo);
            String trasf = (String) asyncTaskTranferirs.get();
            Log.i("Transferido > ", trasf);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (cont>0){
            GuardarImagenServer(listdetalles);
            CreateCustomToast("Se envio el reporte de inspección correctamente",Constans.icon_succes,Constans.layout_success );
        }

    }


    public  InspeccionMaqDetalle GetDetalle (long correlativo , int position){
        NumberFormat format = new DecimalFormat("00000");
       // String cod_insp = format.format(position+1);
        InspeccionMaqDetalle aux = detalleMaqAdap.AllItemsDetalle().get(position);


        InspeccionMaqDetalle detresult = new InspeccionMaqDetalle();
        detresult.setCompania(Constans.NroConpania);
        detresult.setCorrelativo(String.valueOf(correlativo));
        detresult.setLinea(String.valueOf(position+1));
        detresult.setCod_inspeccion(aux.getCod_inspeccion());
        detresult.setTipo_inspecicon(aux.getTipo_inspecicon());
        detresult.setPorcentMin(aux.getPorcentMin());
        detresult.setPorcentMax(aux.getPorcentMax());
        detresult.setPorcentInspec(DecimalFormtPorcentInspeccion(aux));
        detresult.setEstado(GetEstadoItemDetalle(position));
        detresult.setComentario(aux.getComentario());
        detresult.setRutaFoto(aux.getRutaFoto());
        detresult.setUltimoUser(codUser);
        detresult.setUltimaFechaMod(FechaActual());

        Log.i("Format porcent > ",DecimalFormtPorcentInspeccion(aux));

        return  detresult;


    }

    public String GetEstadoItemDetalle (int pos){
        HashMap<Integer,Integer>  hash = detalleMaqAdap.GetIndexsSpiner();
        int selectedIndex = hash.get(pos);
        String res = "";
        if (selectedIndex==1){
            res = "O";
        }
        else if (selectedIndex==2){

            res ="F";
        }

        return  res;


    }
    public  String DecimalFormtPorcentInspeccion (InspeccionMaqDetalle det ){
        DecimalFormat precision = new DecimalFormat("0.000000");
        String porcenInsp = det.getPorcentInspec();
        if (porcenInsp.length() > 0) {


            Double porcent = Double.parseDouble(porcenInsp);
        if (porcenInsp.equals("")){

        }
        else {
            porcenInsp = precision.format(porcent.intValue());
            porcenInsp = porcenInsp.replace(",",".");
         }
        } else {
            porcenInsp = "";
        }
        return  porcenInsp;
    }

    public  InspeccionMaqCabecera GetCabecera (int tipGuardado){
        ProdMantDataBase db = new ProdMantDataBase(InspeccionMaq.this);
        String condicionMaq = spCondMaq.getSelectedItem().toString().substring(0,1);
        InspeccionMaqCabecera cab = new InspeccionMaqCabecera();
        cab.setCompania(Constans.NroConpania);
        cab.setCorrlativo(String.valueOf(db.Correlativo()));
        cab.setCodMaquina(codMaquina);
        cab.setCondicionMaq(condicionMaq);
        cab.setComentario(txtComentario.getText().toString());
        if (tipGuardado==SOLO_GUARDAR) {
            cab.setEstado("I");
        }
        else {
            cab.setEstado("E");
        }
        if (tipoMant.equals("NEW")) {
            cab.setFechaInicioInsp(FechaFormatEng(lblFechaInicio.getText().toString()));
        } else {
            cab.setFechaInicioInsp(lblFechaInicio.getText().toString());
        }
        cab.setFechFinInsp(FechaFormatEng(FechaActual()));
        cab.setPeriodoInsp(indexPeriodoInspeccion);
        cab.setUsuarioInsp(codUser);
        cab.setUsuarioEnv(codUser);
        cab.setFechaEnv(FechaFormatEng(FechaActual()));
        cab.setUltUsuairo(codUser);
        cab.setUltFechaMod(FechaFormatEng( FechaActual()));

        return  cab;
    }

    public  String FechaFormatEng (String stringdate){

        String inputPattern = "dd/MM/yyyy HH:mm:ss";
        String outputPattern = "MM/dd/yyyy HH:mm:ss";
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
        Log.i("fecha format > " ,str);
        return str;


    }

    public boolean ValidarCabecera (){
        boolean result = true;
        if (spPeriodo.getSelectedItemPosition()==0){
            CreateCustomToast("Seleccione un periodo",Constans.icon_error,Constans.layout_error);
            result = false;

        }
        else if (spCondMaq.getSelectedItemPosition()==0){
            result=false;
            CreateCustomToast("Seleccione una condición de máquina",Constans.icon_error,Constans.layout_error);
        }

        return  result;


    }

    public  boolean  ValidarDetalle (){
        boolean result = true;
        if (LVdetalleM.getAdapter() == null || LVdetalleM.getAdapter().getCount()==0){

            CreateCustomToast("Falta ingresar detalles",Constans.icon_error,Constans.layout_error);
            result = false;
        }
       else if (detalleMaqAdap.AllItemsDetalle().size()>0){

            ArrayList<InspeccionMaqDetalle> listDet = detalleMaqAdap.AllItemsDetalle();
            HashMap<Integer,Integer> indxSpiner= detalleMaqAdap.GetIndexsSpiner();

            for (int i =0 ; i < listDet.size() ; i++) {
                InspeccionMaqDetalle det = listDet.get(i);
                Double porcent = Double.parseDouble(det.getPorcentMax());
                if (porcent.intValue()>0 && det.getPorcentInspec().equals("")){

                    CreateCustomToast("Falta llenar el porcentaje en la fila Nro " + String.valueOf(i+1),Constans.icon_error,Constans.layout_error);
                    result = false;
                }

                else if (indxSpiner.get(i)==0){
                    CreateCustomToast("Falta seleccionar un estado  en la fila Nro " + String.valueOf(i+1),Constans.icon_error,Constans.layout_error);
                    result = false;
                }
            }

        }

        return  result;

    }

    public  void GuardarImagenServer (ArrayList<InspeccionMaqDetalle> listDet ){
        Log.i("Metodo Guardar  ", "pass");
        String fileCarp = "/storage/sdcard0/LysConfig/Fotos/";
        for (int i = 0 ; i<listDet.size();i++){
            if (listDet.get(i).getRutaFoto()==null || listDet.get(i).getRutaFoto().equals("")){

            }

            else {
                Log.i("Metodo GuardarImagen == >" , listDet.get(i).getRutaFoto());
                String fileName = listDet.get(i).getRutaFoto();
                String filePath = fileCarp+fileName;
                File file = new File(filePath);

                byte [] bytes = new byte[(int)file.length()];
                try {
                    bytes =  FileUtils.readFileToByteArray(file);
                    AsyncTask asyncTask = null;
                    GuardarImagenTask guardarImagenTask = new GuardarImagenTask(bytes,listDet.get(i).getRutaFoto());
                    Log.i("Parmaetro exex GuardarTask ===>" , listDet.get(i).getRutaFoto());
                    asyncTask = guardarImagenTask.execute();
                    String resp = (String) asyncTask.get();
                   // Toast.makeText(MantInspeccionT.this, resp, Toast.LENGTH_SHORT).show();
                    Log.i("foto guardada ===>" , resp);

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

}
