package com.filtroslys.filtroslysapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import Model.QuejaCliente;
import Model.ReclamoGarantia;
import Model.TMACliente;
import Model.UsuarioCompania;
import Tasks.GetCompUserComercialTask;
import Tasks.GetListQueja;
import Tasks.GetListReclamoGar;
import Util.QuejaClienteAdapter;
import Util.ReclamogarantiaAdapter;
import spencerstudios.com.fab_toast.FabToast;

public class ListaQuejaCliente extends AppCompatActivity {

    EditText txtFechaIni,txtFechaFin,txtCliente;
    Spinner spCompania,spEstado;
    SharedPreferences preferences;
    String sFechaI,sFechaF;
    String codUser;
    CheckBox chkOnline;
    ListView lvQueja;
    Button btnBuscarQJ;
    QuejaClienteAdapter adapter;
    String sFlagOffOn  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_queja);
        setTitle("Lista de Quejas Cliente");

        txtFechaFin = findViewById(R.id.txtFechaFinQJ);
        preferences = PreferenceManager.getDefaultSharedPreferences(ListaQuejaCliente.this);
        codUser = preferences.getString("UserCod", null);
        txtFechaIni = findViewById(R.id.txtFechaIniQJ);
        txtCliente = findViewById(R.id.txtClienteQJ);
        spCompania = findViewById(R.id.spCompaniaQJ);
        spEstado = findViewById(R.id.spEstadoQJ) ;
        chkOnline = findViewById(R.id.chkOnlineQJ);
        lvQueja = findViewById(R.id.LvQuejaCliente);
        btnBuscarQJ = findViewById(R.id.btnBuscarQJ);
        sFlagOffOn  = "OFF";

        InicializarFechas();
        LoadSpinerCompania();
        LoadSpinerEstado();
        if (!chkOnline.isChecked()){
            LoadOffLinelvQueja();
        }

        txtFechaIni.setOnClickListener(new View.OnClickListener() {
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
        btnBuscarQJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkOnline.isChecked()){
                    LoadOnlineLvReclamo();
                    sFlagOffOn = "ON";
                }
                else {
                    LoadOffLinelvQueja();
                    sFlagOffOn = "OFF";
                }
            }
        });

        lvQueja.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertSeleccionarOpcion(position);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_agregar_qj, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Agregar) {
            Intent intent = new Intent(getApplicationContext(),DatosGenQueja.class);
            intent.putExtra("AccionQJ","NEW");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void LoadOnlineLvReclamo(){
        String sFechaIni,sFechaFin,sCliente,sEstado,sCompania;
        sFechaIni = sFechaI;
        sFechaFin = sFechaF;
        if (txtCliente.getText().toString().trim().isEmpty()){
            sCliente = "0";
        }
        else {
            sCliente = txtCliente.getText().toString().trim();
            sCliente = sCliente.substring(0,sCliente.indexOf("|")-1).trim();
        }
        sEstado = CodEstado();
        if (spCompania.getCount()<=0){
            FabToast.makeText(ListaQuejaCliente.this, "No tiene asigando ninguna compania asigna en el modulo comercial.", FabToast.LENGTH_LONG, FabToast.ERROR,  FabToast.POSITION_DEFAULT).show();
            return;
        }
        else {
            sCompania = spCompania.getSelectedItem().toString();
            sCompania = sCompania.substring(0,sCompania.indexOf("|")-1).trim();
        }

        ArrayList<QuejaCliente> listQJ = new ArrayList<>();
        AsyncTask<String,String,ArrayList<QuejaCliente> > asyncTaskListQJ;
        GetListQueja getlistQueja = new GetListQueja();

        try {
            asyncTaskListQJ = getlistQueja.execute(sCompania,sCliente,sEstado,sFechaIni,sFechaFin);
            listQJ = asyncTaskListQJ.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if ( listQJ != null && listQJ.size()>=0){
            SetAdaptadorListView(listQJ);
        }
        else {
            FabToast.makeText(ListaQuejaCliente.this, "No se encontro información.", FabToast.LENGTH_LONG, FabToast.WARNING,  FabToast.POSITION_DEFAULT).show();
            lvQueja.setAdapter(null);
            return;
        }
    }

    public void LoadOffLinelvQueja(){
        ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
        String sFechaIni,sFechaFin,sCliente,sEstado,sCompania;
        sFechaIni = sFechaI;
        sFechaFin = sFechaF;
        if (txtCliente.getText().toString().trim().isEmpty()){
            sCliente = "%";
        }
        else {
            sCliente = txtCliente.getText().toString().trim();
            sCliente = sCliente.substring(0,sCliente.indexOf("|")-1).trim();
        }
        sEstado = CodEstado();
        if (spCompania.getCount()<=0){
            FabToast.makeText(ListaQuejaCliente.this, "No tiene asigando ninguna compania asigna en el modulo comercial.", FabToast.LENGTH_LONG, FabToast.ERROR,  FabToast.POSITION_DEFAULT).show();
            return;
        }
        else {
            sCompania = spCompania.getSelectedItem().toString();
            sCompania = sCompania.substring(0,sCompania.indexOf("|")-1).trim();
        }

        ArrayList<QuejaCliente> listQueja = new ArrayList<>();
        listQueja = db.GetListQuejaCliente(sCompania,sCliente,sEstado,sFechaIni,sFechaFin);
        SetAdaptadorListView(listQueja);
    }

    public void SetAdaptadorListView(ArrayList<QuejaCliente> listrg){
        adapter = new QuejaClienteAdapter(ListaQuejaCliente.this, R.layout.item_queja, listrg);
        lvQueja.setAdapter(adapter);
    }

    public String CodEstado(){
        String sCod,sResult ="";
        sCod = spEstado.getSelectedItem().toString();
        switch (sCod){
            case "Todos":
                sResult = "%";
                break;
            case "Activas":
                sResult ="A";
                break;
            case "Anuladas":
                sResult ="AN";
                break;
        }
        return sResult;
    }

    public void LoadSpinerCompania() {
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

    public void LoadSpinerEstado(){
        ArrayList<String> dataSpinerComp = new ArrayList<String>() ;
        dataSpinerComp.add("Todos");
        dataSpinerComp.add("Activas");
        dataSpinerComp.add("Anuladas");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, dataSpinerComp);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstado.setAdapter(adapter);
    }

    public void AlertDialogBuscarCliente () {
        final Dialog dialog  = new Dialog(ListaQuejaCliente.this);
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
        ProdMantDataBase db = new ProdMantDataBase(ListaQuejaCliente.this);
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
        new AlertDialog.Builder(ListaQuejaCliente.this).setView(view)
                .setTitle("Seleccionar Fecha")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
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

    public void InicializarFechas(){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String mes = String.format("%02d", month);
        String dia = String.format("%02d", day);
        sFechaF= String.valueOf(year) + "-" + mes + "-" + dia;
        sFechaI=  String.valueOf(year) +  "-" + mes + "-" + "01";

        txtFechaIni.setText("01/"+mes+"/"+String.valueOf(year));
        txtFechaFin.setText( dia+ "/"+mes+"/"+String.valueOf(year));
    }
    public void AlertSeleccionarOpcion(final int positem) {
        String sItemOpcion = adapter.getObject(positem).getC_enviado().equals("S") ? "VER":"MODIFICAR";
        final CharSequence[] items = { sItemOpcion,"ELIMINAR" };


        AlertDialog.Builder builder = new AlertDialog.Builder(ListaQuejaCliente.this);
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
                        String sAccion = "";
                        QuejaCliente qj = new QuejaCliente();
                        if (sFlagOffOn.equals("ON")){
                            qj = adapter.getObject(positem);
                        }else if (sFlagOffOn.equals("OFF")){
                            ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
                            qj = db.GetQuejaClienteItem(String.valueOf( adapter.getObject(positem).getN_correlativo()));
                        }
                        sAccion = qj.getC_enviado().equals("S") ? "VER" :"MOD" ;
                        Gson gson = new Gson();
                        String ObjectRG = gson.toJson(qj);
                        Intent intent = new Intent(ListaQuejaCliente.this, DatosGenQueja.class);
                        intent.putExtra("ObjectQJ", ObjectRG);
                        intent.putExtra("AccionQJ", sAccion);
                        startActivity(intent);
                        break;
                    case 1:
                        if (adapter.getObject(positem).getC_enviado().equals("S")){
                            FabToast.makeText(ListaQuejaCliente.this, "No se puede eliminar el registro.", FabToast.LENGTH_LONG, FabToast.WARNING,  FabToast.POSITION_DEFAULT).show();
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
