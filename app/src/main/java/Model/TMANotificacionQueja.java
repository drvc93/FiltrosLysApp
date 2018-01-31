package Model;

public class TMANotificacionQueja {
    private String c_notificacion;
    private String c_descripcion;
    private String c_envianot;
    private String c_usuarionot;
    private String c_estado;

    public TMANotificacionQueja() {
    }

    public TMANotificacionQueja(String c_notificacion, String c_descripcion, String c_envianot, String c_usuarionot, String c_estado) {
        this.c_notificacion = c_notificacion;
        this.c_descripcion = c_descripcion;
        this.c_envianot = c_envianot;
        this.c_usuarionot = c_usuarionot;
        this.c_estado = c_estado;
    }

    public String getC_notificacion() {
        return c_notificacion;
    }

    public void setC_notificacion(String c_notificacion) {
        this.c_notificacion = c_notificacion;
    }

    public String getC_descripcion() {
        return c_descripcion;
    }

    public void setC_descripcion(String c_descripcion) {
        this.c_descripcion = c_descripcion;
    }

    public String getC_envianot() {
        return c_envianot;
    }

    public void setC_envianot(String c_envianot) {
        this.c_envianot = c_envianot;
    }

    public String getC_usuarionot() {
        return c_usuarionot;
    }

    public void setC_usuarionot(String c_usuarionot) {
        this.c_usuarionot = c_usuarionot;
    }

    public String getC_estado() {
        return c_estado;
    }

    public void setC_estado(String c_estado) {
        this.c_estado = c_estado;
    }
}
