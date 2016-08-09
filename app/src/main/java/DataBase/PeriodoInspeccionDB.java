package DataBase;

/**
 * Created by dvillanueva on 09/08/2016.
 */
public class PeriodoInspeccionDB {


    private String c_periodoinspeccion;
    private String c_descripcion;
    private String c_estado;
    private String c_ultimousuario;
    private String d_ultimafechamodificacion;

    public PeriodoInspeccionDB() {
    }

    public PeriodoInspeccionDB(String c_periodoinspeccion, String c_descripcion, String c_estado, String c_ultimousuario, String d_ultimafechamodificacion) {
        this.c_periodoinspeccion = c_periodoinspeccion;
        this.c_descripcion = c_descripcion;
        this.c_estado = c_estado;
        this.c_ultimousuario = c_ultimousuario;
        this.d_ultimafechamodificacion = d_ultimafechamodificacion;
    }

    public String getC_periodoinspeccion() {
        return c_periodoinspeccion;
    }

    public void setC_periodoinspeccion(String c_periodoinspeccion) {
        this.c_periodoinspeccion = c_periodoinspeccion;
    }

    public String getC_descripcion() {
        return c_descripcion;
    }

    public void setC_descripcion(String c_descripcion) {
        this.c_descripcion = c_descripcion;
    }

    public String getC_estado() {
        return c_estado;
    }

    public void setC_estado(String c_estado) {
        this.c_estado = c_estado;
    }

    public String getC_ultimousuario() {
        return c_ultimousuario;
    }

    public void setC_ultimousuario(String c_ultimousuario) {
        this.c_ultimousuario = c_ultimousuario;
    }

    public String getD_ultimafechamodificacion() {
        return d_ultimafechamodificacion;
    }

    public void setD_ultimafechamodificacion(String d_ultimafechamodificacion) {
        this.d_ultimafechamodificacion = d_ultimafechamodificacion;
    }



}
