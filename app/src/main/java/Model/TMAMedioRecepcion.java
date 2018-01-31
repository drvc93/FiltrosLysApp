package Model;

public class TMAMedioRecepcion {
    private String c_mediorecepcion  ;
    private String c_descripcion;
    private String c_estado;

    public TMAMedioRecepcion() {
    }

    public TMAMedioRecepcion(String c_mediorecepcion, String c_descripcion, String c_estado) {
        this.c_mediorecepcion = c_mediorecepcion;
        this.c_descripcion = c_descripcion;
        this.c_estado = c_estado;
    }

    public String getC_mediorecepcion() {
        return c_mediorecepcion;
    }

    public void setC_mediorecepcion(String c_mediorecepcion) {
        this.c_mediorecepcion = c_mediorecepcion;
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
}
