package com.filtroslys.filtroslysapp;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.ParcelUuid;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MenuPrincipal extends AppCompatActivity {

    Context context= this;
    ActionBar actionBarGlobal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        final ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBarGlobal = actionBar;
        HideToolBar();


    }

    public  void  HideToolBar () {

        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
          //      Toast.makeText(context,String.valueOf(l),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                actionBarGlobal.hide();
                getWindow().setStatusBarColor(Color.parseColor("#fc0101"));
            }
        }.start();

    }
}
