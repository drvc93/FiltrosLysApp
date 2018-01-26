package com.filtroslys.filtroslysapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import DataBase.ProdMantDataBase;
import Model.EFacSerLot;
import Model.ReclamoGarantia;
import Model.TMACliente;
import Model.TMATipoReclamo;
import Tasks.GetLoteSerieRGTask;
import Tasks.GetReqLogDetalleTask;
import Tasks.VerificarItemExisteTask;
import br.com.sapereaude.maskedEditText.MaskedEditText;
import libs.mjn.prettydialog.PrettyDialog;
import spencerstudios.com.fab_toast.FabToast;

public class DatosGenRG extends AppCompatActivity {
    EditText txtNroFormato, txtNroRG, txtFechaRegistro, txtClienteCodNom , txtFiltroBuscar ;
    EditText txtFacturaRG, txtLoteF , txtSerieF, txtEstado;
    Spinner spTipoRG ;
    ImageView btnBuscarCliente , btnBuscarFiltro;
    MaskedEditText  txtCantGen , txtCantLote1, txtCantLote2,txtCantLote3;
    ReclamoGarantia objReclamo ;
    Button btnSiguiente,btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_gen_rg);
        setTitle("Datos Generales");
        txtFechaRegistro = findViewById(R.id.txtFechaRegRG);
        txtNroFormato = findViewById(R.id.txtNroFormtRG);
        txtNroRG = findViewById(R.id.txtNroRecGar);
        spTipoRG = findViewById(R.id.spTipoRG);
        txtClienteCodNom =  findViewById(R.id.txtBSClienteRG);
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
        btnSiguiente = findViewById(R.id.btnSigDatosGRG);
        txtFiltroBuscar.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        txtEstado.setText("Ingresado");
        objReclamo  = new ReclamoGarantia();
        LoadSpinerTipoReclamo();

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                if (!hasFocus){
                    String sFac="";
                    sFac = txtFacturaRG.getText().toString().toUpperCase();
                    if (sFac.isEmpty() || txtClienteCodNom.getText().toString().isEmpty()){
                        FabToast.makeText(DatosGenRG.this, "Se recomienda ingresar cliente primero.", FabToast.LENGTH_LONG, FabToast.WARNING,  FabToast.POSITION_DEFAULT).show();
                        return;
                    }
                    ObtenerLoteSerieFiltro("F",txtFiltroBuscar.getText().toString().toUpperCase(),sFac);
                }
            }
        });

        txtFiltroBuscar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    VerificarItemExiste(txtFiltroBuscar.getText().toString());
                }
            }
        });

        txtCantLote1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    SumaCantidadesLotes();
                }
            }
        });
        txtCantLote2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    SumaCantidadesLotes();
                }
            }
        });
        txtCantLote3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    SumaCantidadesLotes();
                }
            }
        });
    }

    public  void  SetandoClaseReclamoG(){

    }

    public void SumaCantidadesLotes (){
        String sCantidad1  =  txtCantLote1.getText().toString();
        String sCantidad2  =  txtCantLote2.getText().toString();
        String sCantidad3  =  txtCantLote3.getText().toString();
        int cant1 =0 , cant2 =0,cant3 =0 , cantGen;

        if (!sCantidad1.isEmpty()){
            cant1 = Integer.valueOf(sCantidad1);
        }
        if (!sCantidad2.isEmpty()){
            cant2 = Integer.valueOf(sCantidad2);
        }
        if (!sCantidad3.isEmpty()){
            cant3 = Integer.valueOf(sCantidad3);
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

                        dialog.cancel();

                    }

                }).show();
    }

    public void   ShowMessageWindow (String sMessage){
        new PrettyDialog(this)
                .setTitle("Aviso")
                .setMessage(sMessage)
                .setIconTint( R.color.WarningColor)
                .show();
    }
}
