package Model;

public class TMAAccionesTomar {
    private String c_codaccion  ;
    private String c_descripcion;
    private String c_estado;

    public TMAAccionesTomar() {
    }

    public TMAAccionesTomar(String c_codaccion, String c_descripcion, String c_estado) {
        this.c_codaccion = c_codaccion;
        this.c_descripcion = c_descripcion;
        this.c_estado = c_estado;
    }

    public String getC_codaccion() {
        return c_codaccion;
    }

    public void setC_codaccion(String c_codaccion) {
        this.c_codaccion = c_codaccion;
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
