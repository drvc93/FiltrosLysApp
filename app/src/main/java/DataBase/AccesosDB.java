package DataBase;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by dvillanueva on 04/08/2016.
 */
public class AccesosDB implements KvmSerializable {

    private  int id;
    private  String usuario;
    private  String appCodigo;
    private  String nivelAcc;
    private  String acceso;
    private  String nuevo;
    private  String modificar;
    private  String eliminar;
    private  String otros;

    public String getAcceso() {
        return acceso;
    }

    public void setAcceso(String acceso) {
        this.acceso = acceso;
    }

    public String getAppCodigo() {
        return appCodigo;
    }

    public void setAppCodigo(String appCodigo) {
        this.appCodigo = appCodigo;
    }

    public String getEliminar() {
        return eliminar;
    }

    public void setEliminar(String eliminar) {
        this.eliminar = eliminar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModificar() {
        return modificar;
    }

    public void setModificar(String modificar) {
        this.modificar = modificar;
    }

    public String getNivelAcc() {
        return nivelAcc;
    }

    public void setNivelAcc(String nivelAcc) {
        this.nivelAcc = nivelAcc;
    }

    public String getNuevo() {
        return nuevo;
    }

    public void setNuevo(String nuevo) {
        this.nuevo = nuevo;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public AccesosDB(String acceso, String appCodigo, String eliminar, int id, String modificar, String nivelAcc, String nuevo, String otros, String usuario) {
        this.acceso = acceso;
        this.appCodigo = appCodigo;
        this.eliminar = eliminar;
        this.id = id;
        this.modificar = modificar;
        this.nivelAcc = nivelAcc;
        this.nuevo = nuevo;
        this.otros = otros;
        this.usuario = usuario;
    }

    public AccesosDB() {
    }


    @Override
    public Object getProperty(int i) {
        switch (i){

            case  0 :
                return  usuario;
            case  1 :
                return  appCodigo;
            case  2:
                return nivelAcc;
            case 3:
                return  acceso;
            case 4:
                return  nuevo;
            case 5:
                return  modificar;
            case 6:
                return  eliminar;
            case  7:
                return  otros;



        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        return 8;
    }

    @Override
    public void setProperty(int i, Object o) {

        switch (i){

            case  0:
                usuario = o.toString();
                break;
            case 1:
                appCodigo = o.toString();
                break;
            case 2:
                nivelAcc = o.toString();
                break;
            case 3:
                acceso = o.toString();
                break;
            case 4:
                nuevo = o.toString();
                break;
            case 5:
                modificar = o.toString();
                break;
            case  6:
                eliminar = o.toString();
                break;
            case 7:
                otros = o.toString();
                break;
            default: break;


        }


    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

        switch (i){

            case 0:
                propertyInfo.type= PropertyInfo.STRING_CLASS;
                propertyInfo.name="usuario";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "appCodigo";
                break;
            case 2:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name= "nivelAcc";
                break;
            case 3:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "acceso";
                break;
            case 4:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "nuevo";
                break;
            case  5:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "modificar";
                break;
            case 6:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name= "eliminar";
                break;
            case 7:
                propertyInfo.type= PropertyInfo.STRING_CLASS;
                propertyInfo.name = "otros";
                break;

            default:break;

        }

    }

}
