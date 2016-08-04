package com.filtroslys.filtroslysapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import DataBase.AccesosDB;
import DataBase.MenuDB;
import DataBase.ProdMantDataBase;
import Tasks.GetAccesosDataTask;
import Tasks.GetMenuDataTask;

public class Login extends AppCompatActivity {

    //VARIABLES

    Button btnIngresar;
    EditText txtUser, txtPassword;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        final ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        // instanciando controles
        btnIngresar = (Button) findViewById(R.id.btnIngresarLogin);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtUser = (EditText) findViewById(R.id.txtUser);
        //***********************


        txtUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                actionBar.hide();
                getWindow().setStatusBarColor(Color.parseColor("#fc0101"));

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogAlert();
                //Intent i = new Intent(Login.this, MenuPrincipal.class);
                //startActivity(i);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.Volver) {

            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public  void  ShowDialogAlert (){



        new AlertDialog.Builder(Login.this)
                .setTitle("Advertencia")
                .setMessage("Para ingresar a la aplicación  móvil , primero se debe realizar la sincronizacion")
                .setIcon(R.drawable.icn_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                       SincMenuAcceso();
                    }
                }).show();


    }

    public  void SincMenuAcceso()  {



        GetMenuDataTask getMenuDataTask = new GetMenuDataTask();
        AsyncTask<String,String,ArrayList<MenuDB>> asyncTask;
        ArrayList<MenuDB> menuDBs= new ArrayList<MenuDB>();
        ProdMantDataBase db =  new ProdMantDataBase(Login.this);
        db.deleteTables();


        try {
            asyncTask = getMenuDataTask.execute();
            menuDBs= (ArrayList<MenuDB>)asyncTask.get();
            for (int i = 0 ; i <menuDBs.size();i++){

                MenuDB mn = menuDBs.get(i);
                db.InsetrtMenus(mn);
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ArrayList<AccesosDB> accesosDBs = new ArrayList<AccesosDB>();
        GetAccesosDataTask getAccesosDataTask = new GetAccesosDataTask();
        AsyncTask<String,String,ArrayList<AccesosDB>> asyncTaskAccesos;

        try {
            asyncTaskAccesos = getAccesosDataTask.execute();
            accesosDBs = (ArrayList<AccesosDB>)asyncTaskAccesos.get();
            for (int i = 0 ; i <accesosDBs.size() ; i++){
                AccesosDB acdb =  accesosDBs.get(i);
                db.InsertAccesos(acdb);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //ShowProgressDialog(tipoSincro);

    }
}
