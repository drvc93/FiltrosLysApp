package Model;

/**
 * Creado por dvillanueva el  09/02/2018 (FiltrosLysApp).
 */

public class DocsCapacitacion {
    private String  c_compania ;
    private int n_solicitud;
    private int n_linea ;
    private String c_descripcion ;
    private String c_nombre_archivo;
    private  String c_ruta_archivo;
    private  String c_ultimousuario;
    private  String d_ultimafechamodificacion ;

    public DocsCapacitacion() {
    }

    public DocsCapacitacion(String c_compania, int n_soliciutud, int n_linea, String c_descripcion, String c_nombre_archivo, String c_ruta_archivo, String c_ultimousuario, String d_ultimafechamodificacion) {
        this.c_compania = c_compania;
        this.n_solicitud = n_soliciutud;
        this.n_linea = n_linea;
        this.c_descripcion = c_descripcion;
        this.c_nombre_archivo = c_nombre_archivo;
        this.c_ruta_archivo = c_ruta_archivo;
        this.c_ultimousuario = c_ultimousuario;
        this.d_ultimafechamodificacion = d_ultimafechamodificacion;
    }

    public String getC_compania() {
        return c_compania;
    }

    public void setC_compania(String c_compania) {
        this.c_compania = c_compania;
    }

    public int getN_solicitud() {
        return n_solicitud;
    }

    public void setN_solicitud(int n_solicitud) {
        this.n_solicitud = n_solicitud;
    }

    public int getN_linea() {
        return n_linea;
    }

    public void setN_linea(int n_linea) {
        this.n_linea = n_linea;
    }

    public String getC_descripcion() {
        return c_descripcion;
    }

    public void setC_descripcion(String c_descripcion) {
        this.c_descripcion = c_descripcion;
    }

    public String getC_nombre_archivo() {
        return c_nombre_archivo;
    }

    public void setC_nombre_archivo(String c_nombre_archivo) {
        this.c_nombre_archivo = c_nombre_archivo;
    }

    public String getC_ruta_archivo() {
        return c_ruta_archivo;
    }

    public void setC_ruta_archivo(String c_ruta_archivo) {
        this.c_ruta_archivo = c_ruta_archivo;
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
