package Model;

public class TMACalificacionQueja {
    private String c_calificacion;
    private String c_descripcion;
    private String c_usuarioderivacion;
    private String c_correo;
    private String c_estado;

    public TMACalificacionQueja() {
    }

    public TMACalificacionQueja(String c_calificacion, String c_descripcion, String c_usuarioderivacion, String c_correo, String c_estado) {
        this.c_calificacion = c_calificacion;
        this.c_descripcion = c_descripcion;
        this.c_usuarioderivacion = c_usuarioderivacion;
        this.c_correo = c_correo;
        this.c_estado = c_estado;
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

    public String getC_usuarioderivacion() {
        return c_usuarioderivacion;
    }

    public void setC_usuarioderivacion(String c_usuarioderivacion) {
        this.c_usuarioderivacion = c_usuarioderivacion;
    }

    public String getC_correo() {
        return c_correo;
    }

    public void setC_correo(String c_correo) {
        this.c_correo = c_correo;
    }

    public String getC_estado() {
        return c_estado;
    }

    public void setC_estado(String c_estado) {
        this.c_estado = c_estado;
    }
}
