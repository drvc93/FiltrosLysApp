package com.filtroslys.filtroslysapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Model.Menu;
import Model.SubMenu;

public class MenuPrincipal extends AppCompatActivity {

    Context context = this;
    ActionBar actionBarGlobal;
   String codUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        final ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBarGlobal = actionBar;
        HideToolBar();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MenuPrincipal.this);
        codUser = preferences.getString("UserCod",null);
        CreateCustomToast("BIENVENIDO " +  codUser +" !  ");



    }

    public  void  LoadMenu (){
       /* ProdMantDataBase db = new ProdMantDataBase(MenuPrincipal.this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MenuPrincipal.this);
        codUser = preferences.getString("user",null);
        ArrayList<Menu> listMenu = new ArrayList<Menu>();
        ArrayList<SubMenu> listSubMenu = new ArrayList<SubMenu>();

        listMenu = db.GetMenuPadre(codUser);

        listSubMenu =  db.GetMenuHijos(codUser);

        AgregarItemsAMenu(listMenu,listSubMenu);*/

    }

    public void HideToolBar() {

        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                //      Toast.makeText(context,String.valueOf(l),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                actionBarGlobal.hide();
              //  getWindow().setStatusBarColor(Color.parseColor("#fc0101"));
            }
        }.start();

    }

    public void   CreateCustomToast (String msj){

        LayoutInflater infator = getLayoutInflater();
        View layout =infator.inflate(R.layout.toast_alarm_success, (ViewGroup) findViewById(R.id.toastlayout));
          TextView toastText = (TextView)layout.findViewById(R.id.txtDisplayToast);
        toastText.setText(msj);
        Toast toast = new Toast(MenuPrincipal.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }
}
