package com.filtroslys.filtroslysapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import DataBase.InspeccionDB;
import DataBase.PeriodoInspeccionDB;
import DataBase.ProdMantDataBase;
import Model.InspeccionMaqDetalle;
import Util.Constans;

public class InspeccionMaq extends AppCompatActivity {

    TextView lblInspector ,lblFechaInicio;
    SharedPreferences preferences;
    private static final int CAMERA_REQUEST = 1888;
    DetalleMaqAdapter detalleMaqAdap;
    ArrayList<InspeccionMaqDetalle> dataGlobal ;
    int posCamara ;
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

    }
    public class DetalleMaqAdapter extends ArrayAdapter<InspeccionMaqDetalle> {
        HashMap<Integer,Integer> indexSpiner = new HashMap<Integer, Integer>();
        HashMap<Integer,Integer> indexIconComent = new HashMap<Integer, Integer>();
        HashMap<Integer,Integer> indexIconFoto = new HashMap<Integer, Integer>();

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
        }



        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            InspeccionMaqDetalle inp = data.get(position);
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
                viewHolder.spEstado  = (Spinner)convertView.findViewById(R.id.spDetEsado);
                viewHolder.imgFoto = (ImageView)convertView.findViewById(R.id.imgDetFoto);
                viewHolder.imgComent = (ImageView)convertView.findViewById(R.id.imgDetComent);



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
            Double porcent = Double.parseDouble(inp.getPorcentMax());
            viewHolder.imgComent.setImageResource(indexIconComent.get(position));
            viewHolder.imgFoto.setImageResource(indexIconFoto.get(position));
            final ImageView imgv = viewHolder.imgComent;

            if (porcent.intValue()>0){
                viewHolder.txtPorcentInsp.setEnabled(true);
                viewHolder.txtPorcentInsp.setHint("LLenar");
                viewHolder.txtPorcentInsp.setText(data.get(position).getPorcentInspec());
            }
            else {
                viewHolder.txtPorcentInsp.setEnabled(false);
                viewHolder.txtPorcentInsp.setHint("");
            }

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


            viewHolder.imgFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    posCamara = position;
                    dataGlobal = data;
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent,CAMERA_REQUEST);
                }
            });

            return convertView;
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

        public  ArrayList<InspeccionMaqDetalle> AllItemsDetalle (){
            return  data;
        }
        public  void  LoadHaspIconComentFoto (){

            for (int i = 0; i < data.size() ; i++) {
                indexIconComent.put(i,R.drawable.icn_pencil_32);
                indexIconFoto.put(i,R.drawable.icn_camera_32);
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

            SeleccionarOpcionDeGuardar();

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

                Toast.makeText(InspeccionMaq.this, String.valueOf(item), Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        }).show();
    }
}
