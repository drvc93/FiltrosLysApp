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
import Model.CapacitacionCliente;
import Model.SugerenciaCliente;
import Model.TMACliente;
import Model.UsuarioCompania;
import Tasks.GetCompUserComercialTask;
import Tasks.GetListCapacitacion;
import Tasks.GetListSugerencia;
import Util.CapacitacionClienteAdapter;
import spencerstudios.com.fab_toast.FabToast;

public class ListaCapacitacionCliente extends AppCompatActivity {
    EditText txtFehcaIni , txtFechaFin , txtCliente;
    Spinner spCompania , spEstado;
    SharedPreferences preferences;
    String sFechaI , sFechaF;
    String codUser;
    CheckBox chkOnline;
    ListView lvCapacitacion;
    Button btnBuscarCP;
    CapacitacionClienteAdapter adapter;
    String sFlagOnOff = "OFF";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_capacitacion);
        setTitle("Lista de Capacitacion");

        txtFechaFin = findViewById(R.id.txtFechaFinCP);
        preferences = PreferenceManager.getDefaultSharedPreferences(ListaCapacitacionCliente.this);
        codUser = preferences.getString("UserCod", null);
        txtFehcaIni = findViewById(R.id.txtFechaIniCP);
        txtCliente = findViewById(R.id.txtClienteListCP);
        spCompania = findViewById(R.id.spCompaniaCP);
        spEstado = findViewById(R.id.spEstadoListCP) ;
        chkOnline = findViewById(R.id.chkOnlineCP);
        lvCapacitacion = findViewById(R.id.LvCapacitacion);
        btnBuscarCP = findViewById(R.id.btnBuscarCP);

        InicializarFechas();
        LoadSpinerCompania();
        LoadSpinerEstado();

        if (!chkOnline.isChecked()){
            LoadOffLineLvCapacitacion();
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
        btnBuscarCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkOnline.isChecked()){
                    sFlagOnOff="ON";
                    LoadOnLineLvXCapacitacion();
                }
                else {
                    sFlagOnOff="OFF";
                    LoadOffLineLvCapacitacion();
                }
            }
        });

        lvCapacitacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sOpe = "";
                CapacitacionCliente cap =  new CapacitacionCliente();
                if (sFlagOnOff.equals("ON")) {
                    cap = adapter.getObject(position);
                }else  if (sFlagOnOff.equals("OFF")){
                    ProdMantDataBase db = new ProdMantDataBase(getApplicationContext());
                    cap = db.GetCapacitacionClienteItem(String.valueOf( adapter.getObject(position).getN_correlativo()));
                }

                sOpe = cap.getC_enviado().equals("S") ? "VER":"MOD";
                Gson gson = new Gson();
                String ObjectCP = gson.toJson(cap);
                Log.i("suegerencia gson > ", ObjectCP);
                Intent intent = new Intent(ListaCapacitacionCliente.this, DatosGenCapacitacion.class);
                intent.putExtra("ObjectCP", ObjectCP);
                intent.putExtra("Operacion", sOpe);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_agregar_cp, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.Agregar) {
            Intent intent = new Intent(getApplicationContext(),DatosGenCapacitacion.class);
            intent.putExtra("Operacion","NEW");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void LoadOnLineLvXCapacitacion(){
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
            FabToast.makeText(ListaCapacitacionCliente.this, "No tiene asigando ninguna compania asigna en el modulo comercial.", FabToast.LENGTH_LONG, FabToast.ERROR,  FabToast.POSITION_DEFAULT).show();
            return;
        }
        else {
            sCompania = spCompania.getSelectedItem().toString();
            sCompania = sCompania.substring(0,sCompania.indexOf("|")-1).trim();
        }

        ArrayList<CapacitacionCliente> listCP = new ArrayList<>();
        AsyncTask<String,String,ArrayList<CapacitacionCliente> > asyncTaskListCapac;
        GetListCapacitacion getListCapacitacion = new GetListCapacitacion();
        try {
            asyncTaskListCapac = getListCapacitacion.execute(sCompania,sCliente,sEstado,sFechaIni,sFechaFin);
            listCP = asyncTaskListCapac.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if ( listCP != null && listCP.size()>=0){
            SetAdaptadorListView(listCP);
        }
        else {
            FabToast.makeText(ListaCapacitacionCliente.this, "No se encontro información.", FabToast.LENGTH_LONG, FabToast.WARNING,  FabToast.POSITION_DEFAULT).show();
            lvCapacitacion.setAdapter(null);
            return;
        }
    }

    public void LoadOffLineLvCapacitacion(){
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
            FabToast.makeText(ListaCapacitacionCliente.this, "No tiene asigando ninguna compania asigna en el modulo comercial.", FabToast.LENGTH_LONG, FabToast.ERROR,  FabToast.POSITION_DEFAULT).show();
            return;
        }
        else {
            sCompania = spCompania.getSelectedItem().toString();
            sCompania = sCompania.substring(0,sCompania.indexOf("|")-1).trim();
        }

        ArrayList<CapacitacionCliente> listCapac = new ArrayList<>();
        listCapac = db.GetListCapacitacionCliente(sCompania,sCliente,sEstado,sFechaIni,sFechaFin);
        SetAdaptadorListView(listCapac);
    }

    public  void SetAdaptadorListView(ArrayList<CapacitacionCliente> listrg){
        adapter = new CapacitacionClienteAdapter(ListaCapacitacionCliente.this, R.layout.item_capacitacion, listrg);
        lvCapacitacion.setAdapter(adapter);
    }

    public String CodEstado(){
        String sCod,sResult ="";
        sCod = spEstado.getSelectedItem().toString();
        switch (sCod){
            case "Todos":
                sResult = "%";
                break;
            case "Pendiente":
                sResult ="PE";
                break;
            case "Revisado":
                sResult ="RE";
                break;
            case "Cerrado":
                sResult ="CE";
                break;
            case "Anulado":
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
            for (int i=0;i<listComp.size();i++) {
                UsuarioCompania  u = listComp.get(i);
                dataSpinerComp.add(u.getC_compania() + " | "+u.getC_nombres());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataSpinerComp);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCompania.setAdapter(adapter);
        }
    }

    public void LoadSpinerEstado(){
        ArrayList<String> dataSpinerComp = new ArrayList<String>() ;
        dataSpinerComp.add("Todos");
        dataSpinerComp.add("Pendiente");
        dataSpinerComp.add("Revisado");
        dataSpinerComp.add("Cerrado");
        dataSpinerComp.add("Anulado");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, dataSpinerComp);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstado.setAdapter(adapter);
    }

    public void AlertDialogBuscarCliente () {
        final Dialog dialog  = new Dialog(ListaCapacitacionCliente.this);
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
        ProdMantDataBase db = new ProdMantDataBase(ListaCapacitacionCliente.this);
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
        new AlertDialog.Builder(ListaCapacitacionCliente.this).setView(view)
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
    public void InicializarFechas (){
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
}
