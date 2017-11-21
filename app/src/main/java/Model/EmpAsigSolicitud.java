package Model;

/**
 * Created by dvillanueva on 21/11/2017.
 */

public class EmpAsigSolicitud {
    private String c_compania ;
    private int  n_solicitud;
    private int  n_secuencia ;
    private String c_compEmpleado ;
    private int  n_empleado ;
    private String c__nombreempleado;
    private String c_ultimousuario ;


    public EmpAsigSolicitud(String c_compania, int n_solicitud, int n_secuencia, String c_compEmpleado, int n_empleado, String c__nombreempleado, String c_ultimousuario) {
        this.c_compania = c_compania;
        this.n_solicitud = n_solicitud;
        this.n_secuencia = n_secuencia;
        this.c_compEmpleado = c_compEmpleado;
        this.n_empleado = n_empleado;
        this.c__nombreempleado = c__nombreempleado;
        this.c_ultimousuario = c_ultimousuario;
    }

    public EmpAsigSolicitud() {
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

    public int getN_secuencia() {
        return n_secuencia;
    }

    public void setN_secuencia(int n_secuencia) {
        this.n_secuencia = n_secuencia;
    }

    public String getC_compEmpleado() {
        return c_compEmpleado;
    }

    public void setC_compEmpleado(String c_compEmpleado) {
        this.c_compEmpleado = c_compEmpleado;
    }

    public int getN_empleado() {
        return n_empleado;
    }

    public void setN_empleado(int n_empleado) {
        this.n_empleado = n_empleado;
    }

    public String getC__nombreempleado() {
        return c__nombreempleado;
    }

    public void setC__nombreempleado(String c__nombreempleado) {
        this.c__nombreempleado = c__nombreempleado;
    }

    public String getC_ultimousuario() {
        return c_ultimousuario;
    }

    public void setC_ultimousuario(String c_ultimousuario) {
        this.c_ultimousuario = c_ultimousuario;
    }
}
