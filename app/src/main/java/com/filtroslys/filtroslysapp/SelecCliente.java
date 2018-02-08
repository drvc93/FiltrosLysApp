package com.filtroslys.filtroslysapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import DataBase.ProdMantDataBase;
import Model.TMACliente;

public class SelecCliente extends AppCompatActivity {

    ListView lvCliente ;
    EditText txtCliente;
    ImageView btnBuscarCliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selec_cliente);
        setTitle("Lista de Cliente");

        lvCliente = findViewById(R.id.lvBuscarClienteSelec);
        txtCliente =  findViewById(R.id.txtClienteSelec);
        btnBuscarCliente  = findViewById(R.id.btnBuscarClienteSelect);

        lvCliente.setAdapter(getAdapterBuscarCliente("%"));
        lvCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelecioanrOpcion(lvCliente.getItemAtPosition(position).toString());
            }
        });

        btnBuscarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvCliente.setAdapter(getAdapterBuscarCliente(txtCliente.getText().toString().trim()));
            }
        });

    }

    public ArrayAdapter<String> getAdapterBuscarCliente(String sFilterText){
        ProdMantDataBase db = new ProdMantDataBase(SelecCliente.this);
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

    public void SelecioanrOpcion(final String sCliente) {
        final CharSequence[] items = { "Generar Reclamo Garanria", "Generar Queja", "Generar Sugerencia", "Generar Sol. Capacitacion",
                                        "Generar Sol. Compra Especial","Generar Sol. Mat.Public","Generar Consulta Tecnica"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SelecCliente.this);
        builder.setTitle("Seleccione una opción");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Intent intent =null;
                switch (item){
                    case 0:
                        intent = new Intent(getApplicationContext(),DatosGenRG.class);
                        intent.putExtra("Operacion","NEW");
                        intent.putExtra("Cliente",sCliente);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getApplicationContext(),DatosGenQueja.class);
                        intent.putExtra("AccionQJ","NEW");
                        intent.putExtra("Cliente",sCliente);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(),DatosGenSugerencia.class);
                        intent.putExtra("Operacion","NEW");
                        intent.putExtra("Cliente",sCliente);
                        intent.putExtra("TipoInfo","SU");
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(getApplicationContext(),DatosGenCapacitacion.class);
                        intent.putExtra("Operacion","NEW");
                        intent.putExtra("Cliente",sCliente);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(getApplicationContext(),DatosGenSugerencia.class);
                        intent.putExtra("Operacion","NEW");
                        intent.putExtra("Cliente",sCliente);
                        intent.putExtra("TipoInfo","SE");
                        startActivity(intent);
                    case 5:
                        intent = new Intent(getApplicationContext(),DatosGenSugerencia.class);
                        intent.putExtra("Operacion","NEW");
                        intent.putExtra("Cliente",sCliente);
                        intent.putExtra("TipoInfo","MP");
                        startActivity(intent);
                    case 6:
                        intent = new Intent(getApplicationContext(),DatosGenSugerencia.class);
                        intent.putExtra("Operacion","NEW");
                        intent.putExtra("Cliente",sCliente);
                        intent.putExtra("TipoInfo","CT");
                        startActivity(intent);

                }
                dialog.dismiss();
            }
        }).show();
    }
}
