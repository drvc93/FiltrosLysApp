package Model;

public class TMATipoCalificacionQueja {
    private String c_tipocalificacion;
    private String c_calificacion;
    private String c_descripcion;
    private String c_estado;

    public TMATipoCalificacionQueja() {
    }

    public TMATipoCalificacionQueja(String c_tipocalificacion, String c_calificacion, String c_descripcion, String c_estado) {
        this.c_tipocalificacion = c_tipocalificacion;
        this.c_calificacion = c_calificacion;
        this.c_descripcion = c_descripcion;
        this.c_estado = c_estado;
    }

    public String getC_tipocalificacion() {
        return c_tipocalificacion;
    }

    public void setC_tipocalificacion(String c_tipocalificacion) {
        this.c_tipocalificacion = c_tipocalificacion;
    }

    public String getC_calificacion() {
        return c_calificacion;
    }

    public void setC_calificacion(String c_calificacion) {
        this.c_calificacion = c_calificacion;
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
