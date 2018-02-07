package Model;

public class TMATemaCapacitacion {
    private String c_temacapacitacion;
    private String c_descripcion;
    private String c_estado;

    public TMATemaCapacitacion() {
    }

    public TMATemaCapacitacion(String c_temacapacitacion, String c_descripcion, String c_estado) {
        this.c_temacapacitacion = c_temacapacitacion;
        this.c_descripcion = c_descripcion;
        this.c_estado = c_estado;
    }

    public String getC_temacapacitacion() {
        return c_temacapacitacion;
    }

    public void setC_temacapacitacion(String c_temacapacitacion) {
        this.c_temacapacitacion = c_temacapacitacion;
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
