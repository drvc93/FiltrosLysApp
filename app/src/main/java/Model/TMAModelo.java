package Model;

/**
 * Creado por dvillanueva el  23/01/2018 (FiltrosLysApp).
 */

public class TMAModelo {
    private  String  c_marca ;
    private  String  c_modelo ;
    private  String c_descripcion;
    private  String  c_estado ;

    public TMAModelo() {
    }

    public TMAModelo(String c_marca, String c_modelo, String c_descripcion, String c_estado) {
        this.c_marca = c_marca;
        this.c_modelo = c_modelo;
        this.c_descripcion = c_descripcion;
        this.c_estado = c_estado;
    }

    public String getC_marca() {
        return c_marca;
    }

    public void setC_marca(String c_marca) {
        this.c_marca = c_marca;
    }

    public String getC_modelo() {
        return c_modelo;
    }

    public void setC_modelo(String c_modelo) {
        this.c_modelo = c_modelo;
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
