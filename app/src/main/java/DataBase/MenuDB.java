package DataBase;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by dvillanueva on 04/08/2016.
 */
public class MenuDB implements KvmSerializable {

    private int id;
    private String AppCodigo;
    private String nivel1;
    private String nivel2;
    private String nivel3;
    private String nivel4;
    private String nivel5;
    private String NombreMenu;
    private String Descripcion;

    public MenuDB(String appCodigo, String descripcion, String nivel1, String nivel2, String nivel3, String nivel4, String nivel5, String nombreMenu) {
        AppCodigo = appCodigo;


        this.nivel1 = nivel1;
        this.nivel2 = nivel2;
        this.nivel3 = nivel3;
        this.nivel4 = nivel4;
        this.nivel5 = nivel5;
        NombreMenu = nombreMenu;
        Descripcion = descripcion;
    }

    public MenuDB() {
    }

    public String getAppCodigo() {
        return AppCodigo;
    }

    public void setAppCodigo(String appCodigo) {
        AppCodigo = appCodigo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNivel1() {
        return nivel1;
    }

    public void setNivel1(String nivel1) {
        this.nivel1 = nivel1;
    }

    public String getNivel2() {
        return nivel2;
    }

    public void setNivel2(String nivel2) {
        this.nivel2 = nivel2;
    }

    public String getNivel3() {
        return nivel3;
    }

    public void setNivel3(String nivel3) {
        this.nivel3 = nivel3;
    }

    public String getNivel4() {
        return nivel4;
    }

    public void setNivel4(String nivel4) {
        this.nivel4 = nivel4;
    }

    public String getNivel5() {
        return nivel5;
    }

    public void setNivel5(String nivel5) {
        this.nivel5 = nivel5;
    }

    public String getNombreMenu() {
        return NombreMenu;
    }

    public void setNombreMenu(String nombreMenu) {
        NombreMenu = nombreMenu;
    }


    @Override
    public Object getProperty(int i) {

        switch (i) {

            case 0:
                return AppCodigo;
            case 1:
                return nivel1;
            case 2:
                return nivel2;
            case 3:
                return nivel3;
            case 4:
                return nivel4;
            case 5:
                return nivel5;
            case 6:
                return NombreMenu;
            case 7:
                return Descripcion;


        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        return 8;

    }

    @Override
    public void setProperty(int i, Object o) {

        switch (i) {

            case 0:
                AppCodigo = o.toString();
                break;
            case 1:
                nivel1 = o.toString();
                break;
            case 2:
                nivel2 = o.toString();
                break;
            case 3:
                nivel3 = o.toString();
                break;
            case 4:
                nivel4 = o.toString();
                break;
            case 5:
                nivel5 = o.toString();
                break;
            case 6:
                NombreMenu = o.toString();
                break;
            case 7:
                Descripcion = o.toString();
                break;

            default:
                break;

        }

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

        switch (i) {

            case 0:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "AppCodigo";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "nivel1";
                break;
            case 2:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "nivel2";
                break;
            case 3:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "nivel3";
                break;
            case 4:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "nivel4";
                break;
            case 5:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "nivel5";
                break;
            case 6:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "NombreMenu";
                break;
            case 7:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "Descripcion";
                break;
            default:
                break;


        }
    }
}
