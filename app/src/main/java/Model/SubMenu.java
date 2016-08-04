package Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by dvillanueva on 04/08/2016.
 */
public class SubMenu implements KvmSerializable {

    private String codSubMenu;
    private String descripcionSubmenu;
    private String codPadre;
    private String estado;

    public SubMenu(String codSubMenu, String descripcionSubmenu, String codPadre, String estado) {
        this.codSubMenu = codSubMenu;
        this.descripcionSubmenu = descripcionSubmenu;
        this.codPadre = codPadre;
        this.estado = estado;
    }

    public String getCodPadre() {
        return codPadre;
    }

    public void setCodPadre(String codPadre) {
        this.codPadre = codPadre;
    }


    public SubMenu() {
    }

    ;

    public String getCodSubMenu() {
        return codSubMenu;
    }

    public void setCodSubMenu(String codSubMenu) {
        this.codSubMenu = codSubMenu;
    }

    public String getDescripcionSubmenu() {
        return descripcionSubmenu;
    }

    public void setDescripcionSubmenu(String descripcionSubmenu) {
        this.descripcionSubmenu = descripcionSubmenu;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    @Override
    public Object getProperty(int i) {
        switch (i) {

            case 0:
                return codSubMenu;
            case 1:
                return descripcionSubmenu;
            case 2:
                return codPadre;
            case 3:
                return estado;


        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        return 4;
    }

    @Override
    public void setProperty(int i, Object o) {

        switch (i) {


            case 0:
                codSubMenu = o.toString();
                break;

            case 1:
                descripcionSubmenu = o.toString();
                break;
            case 2:
                codPadre = o.toString();
                break;
            case 3:
                estado = o.toString();
            default:
                break;


        }

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

        switch (i) {

            case 0:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "codSubMenu";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "descripcionSubmenu";
                break;
            case 2:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "codPadre";
                break;
            case 3:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "estado";
            default:
                break;


        }


    }
}
