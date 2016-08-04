package Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by dvillanueva on 04/08/2016.
 */
public class Menu implements KvmSerializable {
    private String codMenu;
    private String descripcionMenu;
    private ArrayList<SubMenu> subMenus;


    private String codAplicacion;


    public String getCodMenu() {
        return codMenu;
    }

    public void setCodMenu(String codMenu) {
        this.codMenu = codMenu;
    }

    public String getDescripcionMenu() {
        return descripcionMenu;
    }

    public void setDescripcionMenu(String descripcionMenu) {
        this.descripcionMenu = descripcionMenu;
    }

    public String getCodAplicacion() {
        return codAplicacion;
    }

    public void setCodAplicacion(String codAplicacion) {
        this.codAplicacion = codAplicacion;
    }


    public Menu(String codMenu, String descripcionMenu, String codApp) {
        this.codMenu = codMenu;
        this.descripcionMenu = descripcionMenu;
        this.codAplicacion = codApp;
    }

    public ArrayList<SubMenu> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(ArrayList<SubMenu> subMenus) {
        this.subMenus = subMenus;
    }

    public Menu() {
    }

    ;

    @Override
    public Object getProperty(int i) {
        switch (i) {

            case 0:
                return codMenu;
            case 1:
                return descripcionMenu;
            case 2:
                return codAplicacion;

        }

        return null;

    }

    @Override
    public int getPropertyCount() {
        return 3;
    }

    @Override
    public void setProperty(int i, Object o) {


        switch (i) {

            case 0:
                codMenu = o.toString();
                break;
            case 1:
                descripcionMenu = o.toString();
                break;
            case 2:
                codAplicacion = o.toString();
            default:
                break;


        }


    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

        switch (i) {

            case 0:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "codMenu";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "descripcionMenu";
                break;

            case 2:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "codAplicacion";

            default:
                break;


        }


    }
}
