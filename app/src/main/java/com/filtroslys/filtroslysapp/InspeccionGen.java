package com.filtroslys.filtroslysapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import DataBase.CentroCostoDB;
import DataBase.MaquinaDB;
import DataBase.ProdMantDataBase;
import DataBase.TipoRevisionGBD;
import Model.InspeccionGenDetalle;
import Util.Constans;
import Util.HistorialInspMaqAdapater;

public class InspeccionGen extends AppCompatActivity {

    Spinner spMaqCC, spTipoInsp;
    TextView lblSPCCMaq, lblProblemaDetec, lblInspector;
    private static final int CAMERA_REQUEST = 1888;
    EditText txtFechaInsp, txtArea, txtProblemadetect;
    ActionBar actionBar;
    int INSP_MAQUINA = 2;
    int INSP_OTROS = 1;
    int postItemFoto;
    String tipoMant = "";
    int var_tipoIsnpeccion = 0;
    public CharSequence[] listTipoRevision;
    ListView LVInspGen;
    DetalleGenAdapater detalleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inpeccion_gen);
        setTitle("Inspección General");
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

        txtFechaInsp.setText(FechaActual());

        actionBar = getSupportActionBar();
        if (GetDisplaySize() < 6) {

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
        LoadSpinerMaqCC(0);
        LoadListView();


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
                LoadSpinerMaqCC(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spMaqCC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String codMaq = spMaqCC.getSelectedItem().toString();
                codMaq = codMaq.substring(0, 7);
                codMaq = codMaq.trim();
                AsignarCodCcostoTexBox(codMaq);


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

        }
        if (id == R.id.Agregar) {
            AlertAddDetail();
        }
        return true;
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
        return code;

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
            data.add(detalle);
        }

        detalleAdapter = new DetalleGenAdapater(InspeccionGen.this, R.layout.inspeccion_general_det, data);
        LVInspGen.setAdapter(detalleAdapter);


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

        ArrayList<String> listTipIns = new ArrayList<String>();
        listTipIns.add("-SELECCIONE-");
        listTipIns.add("OTRO");
        listTipIns.add("MAQUINA");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(InspeccionGen.this, android.R.layout.simple_spinner_dropdown_item, listTipIns);
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
            msjPrompt = "SELECCIONE CENTRO DE COSTO";
            lblSPCCMaq.setText("C.C.:");
            txtProblemadetect.setEnabled(true);
            txtProblemadetect.setText("");
            for (int i = 0; i < liscCcosto.size(); i++) {
                data.add(liscCcosto.get(i).getC_centrocosto() + "  -   " + liscCcosto.get(i).getC_descripcion());


            }

        } else if (selecTipoInsp == INSP_MAQUINA) {
            var_tipoIsnpeccion = INSP_MAQUINA;
            lblSPCCMaq.setText("MAQ:");
            msjPrompt = "SELECCIONE MAQUINA";
            txtProblemadetect.setEnabled(false);
            txtProblemadetect.setText("");
            data = new ArrayList<String>();
            listMaq = db.GetMaquinasALL();
            for (int i = 0; i < listMaq.size(); i++) {

                data.add(listMaq.get(i).getC_maquina() + "  -  " + listMaq.get(i).getC_descripcion());

            }


        } else {
            data = new ArrayList<String>();
            data.add("-SELECCIONE-");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(InspeccionGen.this, android.R.layout.simple_spinner_dropdown_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMaqCC.setAdapter(adapter);
        spMaqCC.setPrompt(msjPrompt);
        spMaqCC.setEnabled(true);


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
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String res = df.format(cal.getTime());
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
        dialog.show();
        //dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.icn_alert);


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
                viewHolder.imgfoto = (ImageView) convertView.findViewById(R.id.imgInspGenCam);
                viewHolder.imgEliminar = (ImageView) convertView.findViewById(R.id.imgInspGenEliminar);
                viewHolder.index = position;

                convertView.setTag(viewHolder);
            } else {

                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.lblDescripcion.setText(data.get(position).getDescripcionInspGen());
            if (inp.getRutaFoto().equals("")) {
                viewHolder.imgfoto.setImageResource(R.drawable.icn_camera_32);
            } else {
                viewHolder.imgfoto.setImageResource(R.drawable.icn_camera_ok);
            }


            viewHolder.imgEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.remove(position);
                    detalleAdapter.notifyDataSetChanged();
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
                    postItemFoto = position;
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            });

            return convertView;
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
                this.notifyDataSetChanged();
            }

            return result;

        }


    }




}
