package Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import com.filtroslys.filtroslysapp.R;

import java.util.ArrayList;

import Model.Menu;
import Model.SubMenu;

/**
 * Created by dvillanueva on 08/08/2016.
 */
public class ExpandibleListMenuAdapater extends BaseExpandableListAdapter {


    private Context context;
    private ArrayList<Menu> menus;


    public ExpandibleListMenuAdapater(Context context, ArrayList<Menu> menu) {
        this.context = context;
        this.menus = menu;
    }


    @Override
    public int getGroupCount() {
        return menus.size();
    }

    @Override
    public int getChildrenCount(int i) {
        ArrayList<SubMenu> subMenus  = menus.get(i).getSubMenus();


        return subMenus.size();
    }

    @Override
    public Object getGroup(int i) {
        return  menus.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {

        ArrayList<SubMenu> subMenus = menus.get(i).getSubMenus();

        return  subMenus.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        Menu menu = (Menu) getGroup(i);
        if (view==null){


            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.menu_padre, null);


        }

        TextView txtDescpPadre = (TextView) view.findViewById(R.id.txtDescripPadre);
        txtDescpPadre.setText(((Menu) getGroup(i)).getDescripcionMenu());

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        SubMenu subMenu = (SubMenu)getChild(i,i1);
        if (view==null){


            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.menu_hijo, null);


        }


        TextView txtDescripcionSubM = (TextView)  view.findViewById(R.id.txtSubMenuDescrip);
        txtDescripcionSubM.setText(subMenu.getDescripcionSubmenu());



        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
