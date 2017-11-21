package Model;

/**
 * Created by dvillanueva on 20/11/2017.
 */

public class EmpleadoMant {
    private String c_compania ;
    private int n_empleado ;
    private  String  c_nombreempleado;
    private String  c_documento ;

    public EmpleadoMant(String c_compania, int n_empleado, String c_nombreempleado, String c_documento) {
        this.c_compania = c_compania;
        this.n_empleado = n_empleado;
        this.c_nombreempleado = c_nombreempleado;
        this.c_documento = c_documento;
    }

    public EmpleadoMant() {
    }

    public String getC_compania() {
        return c_compania;
    }

    public void setC_compania(String c_compania) {
        this.c_compania = c_compania;
    }

    public int getN_empleado() {
        return n_empleado;
    }

    public void setN_empleado(int n_empleado) {
        this.n_empleado = n_empleado;
    }

    public String getC_nombreempleado() {
        return c_nombreempleado;
    }

    public void setC_nombreempleado(String c_nombreempleado) {
        this.c_nombreempleado = c_nombreempleado;
    }

    public String getC_documento() {
        return c_documento;
    }

    public void setC_documento(String c_documento) {
        this.c_documento = c_documento;
    }
}
