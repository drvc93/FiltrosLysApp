package com.filtroslys.filtroslysapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import DataBase.ConstasDB;
import DataBase.ProdMantDataBase;
import Model.Parametros;
import Tasks.GetListParametrosSistTask;
import Util.Internet;

public class Splash extends AppCompatActivity {
    private static final long SPLASH_SCREEN_DELAY = 3000;
    int  currentapiVersion;
    ActionBar actionBar ;
    TextView  lblVersion ;
    String version ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        lblVersion =  findViewById(R.id.lblVersionCode);
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
            lblVersion.setText("Ver." + version);
        } catch (PackageManager.NameNotFoundException e) {
            version= "";
            lblVersion.setText("");
        }


        // Set portrait orientation
        if (VerificarConexionInternet() && checkDataBase()){
            GetListParametrosSistTask getListParametrosSistTask= new GetListParametrosSistTask();
            AsyncTask<String,String,ArrayList<Parametros>> asyncTaskListaParams ;
            ArrayList<Parametros> listParams = new ArrayList<>();
            try {
                asyncTaskListaParams = getListParametrosSistTask.execute();
                listParams = asyncTaskListaParams.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (listParams!= null && listParams.size()>0){
                ProdMantDataBase db = new ProdMantDataBase(Splash.this);
                for (int i = 0; i < listParams.size() ; i++) {
                    Parametros prm = listParams.get(i);
                    if (db.ExiteParametro(prm.getC_parametrocodigo(),prm.getC_compania())>0){
                        db.UpdateParametro(prm);
                    }
                    else  {
                        db.InsertParametro(prm);
                    }
                }
            }
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
            actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }
        if (currentapiVersion>=16){
            if (actionBar != null) {
                actionBar.hide();
            }
            getWindow().setStatusBarColor(Color.parseColor("#fc0101"));
        }




        TimerTask task = new TimerTask() {
            @Override
            public void run() {


                Intent mainIntent = new Intent().setClass(
                        Splash.this, Login.class);
                startActivity(mainIntent);

                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);

    }

    public  boolean  VerificarConexionInternet (){

        return  Internet.ConexionInternet(Splash.this);
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        String dirdb = getApplicationInfo().dataDir+"/databases/"+ ConstasDB.DB_NAME;
        File f = new File(dirdb);
        if (f.exists()){
            return true ;
        }
        else {
            return  false;
        }
    }

}
