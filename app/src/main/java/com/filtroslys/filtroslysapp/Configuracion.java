package com.filtroslys.filtroslysapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import javax.xml.parsers.SAXParser;

import Util.Constans;

public class Configuracion extends AppCompatActivity {

    SharedPreferences preferences;
    Button btnSalir, btnGuardar;
    EditText txtRuta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        setTitle("Configuración");
        preferences = PreferenceManager.getDefaultSharedPreferences(Configuracion.this);
        btnGuardar = (Button) findViewById(R.id.btnGuardarConfig);
        btnSalir = (Button) findViewById(R.id.btnSalirConfig);
        txtRuta = (EditText) findViewById(R.id.txtRutaFoto);
        if (preferences.getString("RutaFoto", null) == null) {
            CrearRuta();
        } else {
            String ruta = preferences.getString("RutaFoto", null);
            txtRuta.setText(ruta);
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtRuta.getText().toString().equals("")) {

                    CreateCustomToast("Debe escribir la ruta de la carpeta para guardar", Constans.icon_error, Constans.layout_error);
                } else if (txtRuta.getText().toString().length() > 0) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("RutaFoto", txtRuta.getText().toString());
                    editor.commit();
                    finish();

                }
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Salir();
            }
        });

    }

    public void Salir() {

        super.onBackPressed();
    }

    public void CrearRuta() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("RutaFoto", "//IBSERVER_1/Servidor de Archivos/Fotos_Tablet/");
        editor.commit();
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
        Toast toast = new Toast(Configuracion.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }


}
