package com.filtroslys.filtroslysapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import DataBase.ProdMantDataBase;
import Model.Menu;
import Model.SubMenu;
import Util.Constans;
import Util.ExpandibleListMenuAdapater;

public class MenuPrincipal extends AppCompatActivity {

    ActionBar actionBarGlobal;
    ExpandibleListMenuAdapater menuAdapater;
   String codUser;
    ArrayList<Menu> MenuFinalList;
    ExpandableListView menuExpListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        String var = Constans.UrlServer;

        Log.i("test aplicaction context", var);
        final ActionBar actionBar;
        int currentapiVersion;
        currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            actionBar = getSupportActionBar();
            actionBarGlobal = actionBar;
            HideToolBar();

        }
        else  {
            ActionBar ac = getSupportActionBar();
            if (ac != null) {
                ac.hide();
            }
        }
      //  HideToolBar();

        menuExpListView = findViewById(R.id.ELVMenu);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MenuPrincipal.this);
        codUser = preferences.getString("UserCod",null);
        CreateCustomToast("BIENVENIDO " +  codUser +" !  ");

        LoadMenu();
        ShowMenu();

        menuExpListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Menu menu = MenuFinalList.get(i);
                SubMenu subMenu = menu.getSubMenus().get(i1);
                Intent  intent = new Intent(MenuPrincipal.this,MenuOpciones.class);
                intent.putExtra("codPadre",menu.getCodMenu());
                intent.putExtra("codHijo",subMenu.getCodSubMenu());

                startActivity(intent);
                return  false;

            }
        });



    }

    public  void  LoadMenu (){
       ProdMantDataBase db = new ProdMantDataBase(MenuPrincipal.this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MenuPrincipal.this);
        codUser = preferences.getString("UserCod",null);
        ArrayList<Menu> listMenu;
        ArrayList<SubMenu> listSubMenu;

        listMenu = db.GetMenuPadre(codUser);

        listSubMenu =  db.GetMenuHijos(codUser);

        AgregarItemsAMenu(listMenu,listSubMenu);

    }
    public  void AgregarItemsAMenu (ArrayList<Menu> menus , ArrayList<SubMenu> subMenus){

        MenuFinalList = new ArrayList<>();

        for (int i  = 0 ; i<menus.size(); i++){
            Log.i("menu count >" , String.valueOf(i));
            String codMenu = menus.get(i).getCodMenu();
            ArrayList<SubMenu> listSubMenu = new ArrayList<>();
            for (int j =  0 ; j<subMenus.size();j++){
                Log.i("SubMenu count >" , String.valueOf(j));
                String codPadre = subMenus.get(j).getCodPadre();

                if (codPadre.equals(codMenu)){

                    listSubMenu.add(subMenus.get(j));

                }

            }

            menus.get(i).setSubMenus(listSubMenu);

        }

        MenuFinalList=menus;
    }

    public  void  ShowMenu (){

        menuAdapater =  new ExpandibleListMenuAdapater(MenuPrincipal.this,MenuFinalList);
        menuExpListView.setAdapter(menuAdapater);




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
        TextView toastText = layout.findViewById(R.id.txtDisplayToast);
        toastText.setText(msj);
        Toast toast = new Toast(MenuPrincipal.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }

}
