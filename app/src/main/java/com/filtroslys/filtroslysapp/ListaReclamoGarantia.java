package com.filtroslys.filtroslysapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import DataBase.ProdMantDataBase;
import Model.ReclamoGarantia;
import Model.TMACliente;
import Model.UsuarioCompania;
import Tasks.GetCompUserComercialTask;
import Tasks.GetListReclamoGar;
import Util.ReclamogarantiaAdapter;
import Util.ReqLogisticaAdapter;
import spencerstudios.com.fab_toast.FabToast;

public class ListaReclamoGarantia extends AppCompatActivity {

    EditText txtFehcaIni , txtFechaFin , txtCliente;
    Spinner spCompania , spEstado;
    SharedPreferences preferences;
    String sFechaI , sFechaF;
    String codUser;
    CheckBox chkOnline;
    ListView lvReclamoG;
    Button btnBuscarRg;
    String sFlagOffOnline;
    ReclamogarantiaAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reclamo_garantia);
        setTitle("Lista de Reclamos de Garantia");

        txtFechaFin = findViewById(R.id.txtFechaFinRG);
        preferences = PreferenceManager.getDefaultSharedPreferences(ListaReclamoGarantia.this);
        codUser = preferences.getString("UserCod", null);
        txtFehcaIni = findViewById(R.id.txtFechaIniRG);
        txtCliente = findViewById(R.id.txtClienteListRG);
        spCompania = findViewById(R.id.spCompaniaRG);
        spEstado = findViewById(R.id.spEstadoListRG) ;
        chkOnline = findViewById(R.id.chkOnlineRG);
        lvReclamoG = findViewById(R.id.LvReclamoGarantia);
        btnBuscarRg = findViewById(R.id.btnBuscarRG);
        sFlagOffOnline = "OFF";


        InicializarFechas();
        LoadSpinerCompania();
        LoadSpinerEstado();

        if (!chkOnline.isChecked()){
            LoadOffLineLVReclamoG();
        }


        txtFehcaIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelecFecha((EditText)v, "FechaI");
            }
        });
        txtFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelecFecha((EditText)v,"FechaF");
            }
        });
        txtCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogBuscarCliente();
            }
        });
        btnBuscarRg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkOnline.isChecked()){
                    LoadOnlineLvReclamo();
                    sFlagOffOnline = "ON";
                }
                else {
                    LoadOffLineLVReclamoG();
                    sFlagOffOnline = "OFF";
                }
            }
        });

        lvReclamoG.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          AlertSeleccionarOpcion(position);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_agregar_rg, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.Agregar) {
            Intent intent = new Intent(getApplicationContext(),DatosGenRG.class);
            intent.putExtra("Operacion" ,"NEW");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public  void  LoadOnlineLvReclamo(){
        Log.i("Load online lv" , "online ");
        String sFechaIni , sFechaFin , sCliente , sEstado,sCompania;
        sFechaIni =sFechaI;
        sFechaFin  = sFechaF ;
        if (txtCliente.getText().toString().trim().isEmpty()){
            sCliente = "0";
        }
        else {
            sCliente = txtCliente.getText().toString().trim();
            sCliente = sCliente.substring(0,sCliente.indexOf("|")-1).trim();
        }
        sEstado = CodEstado();
        if (spCompania.getCount()<=0){
            FabToast.makeText(ListaReclamoGarantia.this, "No tiene asigando ninguna compania asigna en el modulo comercial.", FabToast.LENGTH_LONG, FabToast.ERROR,  FabToast.POSITION_DEFAULT).show();
            return;
        }
        else {
            sCompania = spCompania.getSelectedItem().toString();
            sCompania = sCompania.substring(0,sCompania.indexOf("|")-1).trim();
        }

        ArrayList<ReclamoGarantia> listRG = new ArrayList<>();
        AsyncTask<String,String,ArrayList<ReclamoGarantia> > asyncTaskListReclamog;
        GetListReclamoGar getListReclamoGar = new GetListReclamoGar();

        try {
            asyncTaskListReclamog = getListReclamoGar.execute(sCompania,sCliente,sEstado,sFechaIni,sFechaFin);
            listRG = asyncTaskListReclamog.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listRG!=null && listRG.size()>0){
            SetAdaptadorListView(listRG);
        }
        else {
            FabToast.makeText(ListaReclamoGarantia.this, "No se encontro información.", FabToast.LENGTH_LONG, FabToast.WARNING,  FabToast.POSITION_DEFAULT).show();
            lvReclamoG.setAdapter(null);
            return;
        }


    }

    public void  LoadOffLineLVReclamoG (){
        ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
        String sFechaIni , sFechaFin , sCliente , sEstado,sCompania;
        sFechaIni =sFechaI;
        sFechaFin  = sFechaF ;
        if (txtCliente.getText().toString().trim().isEmpty()){
            sCliente = "%";
        }
        else {
            sCliente = txtCliente.getText().toString().trim();
            sCliente = sCliente.substring(0,sCliente.indexOf("|")-1).trim();
        }
        sEstado = CodEstado();
        if (spCompania.getCount()<=0){
            FabToast.makeText(ListaReclamoGarantia.this, "No tiene asigando ninguna compania asigna en el modulo comercial.", FabToast.LENGTH_LONG, FabToast.ERROR,  FabToast.POSITION_DEFAULT).show();
            return;
        }
        else {
            sCompania = spCompania.getSelectedItem().toString();
            sCompania = sCompania.substring(0,sCompania.indexOf("|")-1).trim();
        }

        ArrayList<ReclamoGarantia> listReclamo = new ArrayList<>();
        listReclamo = db.GetListReclamosGar(sCompania,sCliente,sEstado,sFechaIni,sFechaFin);
        SetAdaptadorListView(listReclamo);

    }

    public  void SetAdaptadorListView(ArrayList<ReclamoGarantia> listrg){
        adapter = new ReclamogarantiaAdapter(ListaReclamoGarantia.this, R.layout.item_reclamo_gar, listrg);
        lvReclamoG.setAdapter(adapter);
    }

    public String CodEstado(){
        String sCod,sResult ="";
        sCod = spEstado.getSelectedItem().toString();
        switch (sCod){
            case "Todos":
                sResult = "%";
                break;
            case "Ingresado":
                sResult ="C";
                break;
            case  "Pendiente":
                sResult ="P";
                break;
            case  "En Proceso":
                sResult ="R";
                break;
            case  "Terminado":
                sResult ="T";
                break;
            case  "Anulado":
                sResult ="A";
                break;


        }

        return sResult;
    }

    public  void  LoadSpinerCompania() {
        ArrayList<UsuarioCompania> listComp = new ArrayList<>();
        AsyncTask<String,String,ArrayList<UsuarioCompania>> asyncTaskUserComp;
        GetCompUserComercialTask getCompUserComercialTask = new GetCompUserComercialTask();
        ArrayList<String> dataSpinerComp = new ArrayList<String>() ;
        try {
            asyncTaskUserComp = getCompUserComercialTask.execute(codUser);
            listComp =  asyncTaskUserComp.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listComp.size()>0){

            for (int i = 0; i <  listComp.size(); i++) {
                UsuarioCompania  u = listComp.get(i);
                dataSpinerComp.add(u.getC_compania() + " | "+u.getC_nombres());

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, dataSpinerComp);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCompania.setAdapter(adapter);
        }

    }

    public  void  LoadSpinerEstado(){
        ArrayList<String> dataSpinerComp = new ArrayList<String>() ;
        dataSpinerComp.add("Todos");
        dataSpinerComp.add("Ingresado");
        dataSpinerComp.add("Pendiente");
        dataSpinerComp.add("En Proceso");
        dataSpinerComp.add("Terminado");
        dataSpinerComp.add("Anulado");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, dataSpinerComp);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstado.setAdapter(adapter);
    }

    public  void  AlertDialogBuscarCliente () {
        final Dialog dialog  = new Dialog(ListaReclamoGarantia.this);
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
                txtCliente.setText(lvBuscarClientes.getItemAtPosition(position).toString());
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
        ProdMantDataBase db = new ProdMantDataBase(ListaReclamoGarantia.this);
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

    public void SelecFecha(final EditText txtFecha , final String sTipoFecha) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.datapicker_layout, null, false);
        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);
        new AlertDialog.Builder(ListaReclamoGarantia.this).setView(view)
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
                        if (sTipoFecha.equals("FechaI")) {
                            sFechaI = String.valueOf(year) + "-" + mes + "-" + dia;
                        }
                        else if (sTipoFecha.equals("FechaF")){
                            sFechaF = String.valueOf(year) + "-" + mes + "-" + dia;
                        }
                        dialog.cancel();

                    }

                }).show();
    }
    public  void InicializarFechas (){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String mes = String.format("%02d", month);
        String dia = String.format("%02d", day);
        sFechaF= String.valueOf(year) + "-" + mes + "-" + dia;
        sFechaI=  String.valueOf(year) +  "-" + mes + "-" + "01";

        txtFehcaIni.setText("01/"+mes+"/"+String.valueOf(year));
        txtFechaFin.setText( dia+ "/"+mes+"/"+String.valueOf(year));

    }

    public void AlertSeleccionarOpcion(final int positem) {

        String sItemOpcion = adapter.getObject(positem).getC_enviado().equals("S") ? "VER":"MODIFICAR";
        final CharSequence[] items = { sItemOpcion,"ELIMINAR" };


        AlertDialog.Builder builder = new AlertDialog.Builder(ListaReclamoGarantia.this);
        builder.setTitle("Selecciona opcion");
        builder.setPositiveButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
            }
        });
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
            switch (item){
                case 0:
                    String sOpe = "";
                    ReclamoGarantia r =  new ReclamoGarantia();
                    if (sFlagOffOnline.equals("ON")) {
                        r = adapter.getObject(positem);
                    }else  if (sFlagOffOnline.equals("OFF")){
                        ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
                        r = db.GetReclamoGarantiaItem(String.valueOf( adapter.getObject(positem).getN_correlativo()));
                    }
                    sOpe = r.getC_enviado().equals("S") ? "VER":"MOD";
                    Gson gson = new Gson();
                    String ObjectRG = gson.toJson(r);
                    Intent intent = new Intent(ListaReclamoGarantia.this, DatosGenRG.class);
                    intent.putExtra("ObjectRG", ObjectRG);
                    intent.putExtra("Operacion", sOpe);
                    startActivity(intent);
                    break;
                case 1:
                    if (adapter.getObject(positem).getC_enviado().equals("S")){
                        FabToast.makeText(ListaReclamoGarantia.this, "No se puede eliminar el registro.", FabToast.LENGTH_LONG, FabToast.WARNING,  FabToast.POSITION_DEFAULT).show();
                        return;
                    }
                    else {
                        adapter.RemoveItem(positem);
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
              //  dialog.dismiss();
            }
        }).show();
    }
}
