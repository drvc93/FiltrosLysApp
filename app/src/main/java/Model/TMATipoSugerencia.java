package Model;

public class TMATipoSugerencia {
    private String c_tiposugerencia;
    private String c_descripcion;
    private String c_estado;

    public TMATipoSugerencia() {
    }

    public TMATipoSugerencia(String c_tiposugerencia, String c_descripcion, String c_estado) {
        this.c_tiposugerencia = c_tiposugerencia;
        this.c_descripcion = c_descripcion;
        this.c_estado = c_estado;
    }

    public String getC_tiposugerencia() {
        return c_tiposugerencia;
    }

    public void setC_tiposugerencia(String c_tiposugerencia) {
        this.c_tiposugerencia = c_tiposugerencia;
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
