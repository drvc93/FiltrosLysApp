package com.filtroslys.filtroslysapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import DataBase.AccesosDB;
import DataBase.MenuDB;
import DataBase.ProdMantDataBase;
import DataBase.UsuarioDB;
import Tasks.GetAccesosDataTask;
import Tasks.GetMenuDataTask;
import Tasks.GetUsuariosTask;
import Tasks.SincronizarAccesosTask;
import Util.Constans;

public class Login extends AppCompatActivity {

    //VARIABLES

    Button btnIngresar;
    EditText txtUser, txtPassword;
    ActionBar actionBar;
    int currentapiVersion;
    SharedPreferences preferences;
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

         preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
        Constans.ifExistCreateFile();
         currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
            actionBar = getSupportActionBar();
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

        } else{

        }
      //  copyFile();
      //  ShowDialogAlert();

        // instanciando controles
        btnIngresar = (Button) findViewById(R.id.btnIngresarLogin);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtUser = (EditText) findViewById(R.id.txtUser);
        txtUser.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        //***********************



        txtUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (currentapiVersion>=20){
                actionBar.hide();
                getWindow().setStatusBarColor(Color.parseColor("#fc0101"));
                }
                else {

                }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                   // String var_text = txtUser.getText().toString();
                  // txtUser.setText(var_text.toUpperCase());
            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Sync ;
                ProdMantDataBase db =  new ProdMantDataBase(Login.this);
                String user  = txtUser.getText().toString();
                String pass = txtPassword.getText().toString();
                Sync = preferences.getString("Sync",null);
                 if (Sync== null) {
                     if(user.equals("MAESTRO") &&  pass.equals("MAESTRO"))
                         ShowDialogAlert();
                 }

                if(Sync!=null) {

                    boolean res = db.AutenticarUsuario(user, pass);
                    if (res == true) {

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("UserCod", user);
                        editor.commit();
                        Intent i = new Intent(Login.this, MenuPrincipal.class);
                        startActivity(i);

                    } else {

                        CreateCustomToast("Usuario o contraseña incorrecta", Constans.icon_error, Constans.layout_error);

                    }

                }

                else {
                    CreateCustomToast("Falta sincronizar el dispositivo",Constans.icon_warning,Constans.layot_warning);

                }
              // ShowDialogAlert();
                //Intent i = new Intent(Login.this,MenuPrincipal.class);
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
        if (id == R.id.Config) {

            SeleccionarModoTrabajo();
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
                        dialogInterface.cancel();
                       SincMenuAcceso();
                       // dialogInterface.dismiss();
                    }
                }).show();


    }

    public ProgressDialog CreateProgressDialog (String  title , String msj, int icon ){

        ProgressDialog  progress= new ProgressDialog(Login.this);
         progress.setMessage(msj);
        progress.setTitle(title);
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIcon(icon);
        return  progress;

    }

    public  void SincMenuAcceso()  {

        ProgressDialog progressDialog;
        int icn = (R.drawable.icn_sync_48);


        // INSERT MENUS IN SQLITE

        GetMenuDataTask getMenuDataTask = new GetMenuDataTask();
        AsyncTask<String,String,ArrayList<MenuDB>> asyncTask;
        ArrayList<MenuDB> menuDBs= new ArrayList<MenuDB>();
        ProdMantDataBase db =  new ProdMantDataBase(Login.this);
        db.deleteTables();

        try {
            asyncTask = getMenuDataTask.execute();
            menuDBs= (ArrayList<MenuDB>)asyncTask.get();



        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // INSERT USUARIOS IN SQLITE

        ArrayList<UsuarioDB> listaUsers = new ArrayList<UsuarioDB>();
        GetUsuariosTask getUsuariosTask = new GetUsuariosTask();
        AsyncTask<String,String,ArrayList<UsuarioDB>> asyncTaskUsers ;

        try {
            asyncTaskUsers =  getUsuariosTask.execute();
            listaUsers = (ArrayList<UsuarioDB>) asyncTaskUsers.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //INSERT ACCESOS IN SQLITE
        ArrayList<AccesosDB> accesosDBs = new ArrayList<AccesosDB>();
       //
        GetAccesosDataTask getAccesosDataTask = new GetAccesosDataTask();
        AsyncTask<String,String,ArrayList<AccesosDB>> asyncTaskAccesos;

        try {
            asyncTaskAccesos = getAccesosDataTask.execute();
            accesosDBs = (ArrayList<AccesosDB>)asyncTaskAccesos.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        progressDialog= new ProgressDialog(Login.this);
        progressDialog.setTitle("Sincronizando");
        progressDialog.setMessage("Sincronizando accesos .. espere por favor..");
        progressDialog.setIcon(R.drawable.icn_sync_48);
        SincronizarAccesosTask sincronizarAccesosTask = new SincronizarAccesosTask(Login.this,accesosDBs,menuDBs,listaUsers,progressDialog);
        AsyncTask<Void,Void,Void> asyncTaskSincroAccesos ;

        asyncTaskSincroAccesos = sincronizarAccesosTask.execute();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Sync","Si");
        editor.commit();



    }

    public void copyFile()
    {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//com.filtroslys.filtroslysapp//databases//dbProdMant.db";
                String backupDBPath = "dbProdMant.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
        }
    }

    public void   CreateCustomToast (String msj, int icon,int backgroundLayout ){

        LayoutInflater infator = getLayoutInflater();
        View layout =infator.inflate(R.layout.toast_alarm_success, (ViewGroup) findViewById(R.id.toastlayout));
        TextView toastText = (TextView)layout.findViewById(R.id.txtDisplayToast);
        ImageView imgIcon =  (ImageView)layout.findViewById(R.id.imgToastSucc);
        LinearLayout parentLayout = ( LinearLayout)layout.findViewById(R.id.toastlayout);
        imgIcon.setImageResource(icon);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            parentLayout.setBackgroundDrawable( getResources().getDrawable(backgroundLayout) );
        } else {
            parentLayout.setBackground( getResources().getDrawable(backgroundLayout));
        }


        toastText.setText(msj);
        Toast toast = new Toast(Login.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }


    public void SeleccionarModoTrabajo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        CharSequence[] data = {"Local", "Externo"};

        builder.setTitle("Modo de trabajo")


                .setSingleChoiceItems(data, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }

                })

                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String Ipserver = "";
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        if (selectedPosition == 0) {
                            Ipserver = Constans.ReaderFileLocal();
                        } else {

                            Ipserver = Constans.ReaderFileExterno();

                        }

                        Constans.SetConexion("P", Ipserver);
                        int i = android.os.Process.myPid();
                        android.os.Process.killProcess(i);
                        //  showToast("selectedPosition: " + selectedPosition);

                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // removes the dialog from the screen

                    }
                })

                .show();

    }
}
