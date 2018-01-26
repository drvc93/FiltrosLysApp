package Model;

/**
 * Creado por dvillanueva el  23/01/2018 (FiltrosLysApp).
 */

public class TMAMarca {

    private  String c_marca ;
    private  String c_descripcion ;
    private  String c_estado ;

    public TMAMarca() {
    }

    public TMAMarca(String c_marca, String c_descripcion, String c_estado) {
        this.c_marca = c_marca;
        this.c_descripcion = c_descripcion;
        this.c_estado = c_estado;
    }

    public String getC_marca() {
        return c_marca;
    }

    public void setC_marca(String c_marca) {
        this.c_marca = c_marca;
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
