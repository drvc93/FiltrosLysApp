package Model;

/**
 * Creado por dvillanueva el  23/01/2018 (FiltrosLysApp).
 */

public class TMATipoReclamo {
    private  String c_tiporeclamo  ;
    private  String c_descripcion;
    private  String c_estado;
    private  String c_tipo;

    public TMATipoReclamo() {
    }

    public TMATipoReclamo(String c_tiporeclamo, String c_descripcion, String c_estado, String c_tipo) {
        this.c_tiporeclamo = c_tiporeclamo;
        this.c_descripcion = c_descripcion;
        this.c_estado = c_estado;
        this.c_tipo = c_tipo;
    }

    public String getC_tiporeclamo() {
        return c_tiporeclamo;
    }

    public void setC_tiporeclamo(String c_tiporeclamo) {
        this.c_tiporeclamo = c_tiporeclamo;
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

    public String getC_tipo() {
        return c_tipo;
    }

    public void setC_tipo(String c_tipo) {
        this.c_tipo = c_tipo;
    }
}
