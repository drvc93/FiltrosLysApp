package com.filtroslys.filtroslysapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import DataBase.ProdMantDataBase;
import Model.Permisos;
import Model.SubMenuBotones;
import Util.Constans;

public class MenuOpciones extends AppCompatActivity {

    String  codPadre , codHijo;
    String codUser,resultBarCode;
    public static final int REQUEST_CODE = 0x0000c0de;
    ListView LVOpciones;
    ArrayList<SubMenuBotones> listOpciones = new ArrayList<SubMenuBotones>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_opciones);
        LVOpciones = (ListView) findViewById(R.id.LVopcion);

        codHijo = getIntent().getExtras().getString("codHijo");
        codPadre = getIntent().getExtras().getString("codPadre");
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        if (codPadre != null && codHijo != null ){

            Log.i("cod Padre > ", codPadre);
            Log.i("cod  hijo >", codHijo);
            listOpciones = GetOpciones(codPadre,codHijo);
            CreateButtons();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                resultBarCode = data.getStringExtra("SCAN_RESULT");
                Toast.makeText(MenuOpciones.this, resultBarCode, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public  ArrayList<SubMenuBotones> GetOpciones(String codPadre , String smenu){
        ProdMantDataBase db = new ProdMantDataBase(MenuOpciones.this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MenuOpciones.this);
        codUser = preferences.getString("UserCod",null);

        ArrayList<SubMenuBotones> result = db.getSubBotones(codPadre,smenu,codUser);


        return  result;

    }


    public void CreateButtons (){

        OpcionesAdapter  opcionesAdapter = new OpcionesAdapter(MenuOpciones.this,R.layout.layout_lista_opciones,listOpciones);
        LVOpciones.setAdapter(opcionesAdapter);
    }



    // Adapter  listview

    public class OpcionesAdapter  extends ArrayAdapter<SubMenuBotones> {


        public OpcionesAdapter(Context context, int resource, ArrayList<SubMenuBotones> data) {
            super(context, resource, data);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View  view = convertView;

            if (view == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                view= vi.inflate(R.layout.layout_lista_opciones, null);
            }

            SubMenuBotones subMenuBotones = getItem(position);
            Button btnOpcList =(Button) view.findViewById(R.id.btnListOpciones);
            if (btnOpcList!=null){

                btnOpcList.setText(subMenuBotones.getDescripcion());
            }

            btnOpcList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  Toast.makeText(ListaOpciones.this,String.valueOf(position),Toast.LENGTH_SHORT).show();

                    Log.i("Boton Seleciconado ====>cod Boton : " , listOpciones.get(position).getCodMenuBoton());
                    Log.i("Boton Seleciconado ====>codPadre : " , listOpciones.get(position).getCodPadre());
                    Log.i("Boton Seleciconado ====>submenu : ",listOpciones.get(position).getCodSubmenu());
                    // Log.i("Boton Seleciconado ====>codPadre: " , listOpciones.get(position).getaClass().getPackageName() );
                    SubMenuBotones sbmenu = listOpciones.get(position);
                     IrActivity(sbmenu);
                }
            });

            return  view;
        }
    }

    public  void  IrActivity (SubMenuBotones sb){
        ProdMantDataBase db = new ProdMantDataBase(MenuOpciones.this);
        ArrayList<Permisos> permiso = new ArrayList<Permisos>() ;
        String var_concatenado =  sb.getCodPadre()+sb.getCodSubmenu()+sb.getCodMenuBoton();
        Log.i("Contaenado nivel ==> ",var_concatenado);

        if (var_concatenado.equals("010101")){

            Intent intentScan = new Intent(Constans.BS_PACKAGE + ".SCAN");
            intentScan.putExtra("PROMPT_MESSAGE", "Enfoque entre 9 y 11 cm. apuntado el codigo de barras de la maquina");
            startActivityForResult(intentScan, REQUEST_CODE);

        }

    }
}
