package Model;

/**
 * Creado por dvillanueva el  23/01/2018 (FiltrosLysApp).
 */

public class TMAPruebaLab {

    private  String c_codigo ;
    private  String c_descripcion;
    private  String c_tipo;
    private  String c_unidadcodigo;

    public TMAPruebaLab() {

    }

    public TMAPruebaLab(String c_codigo, String c_descripcion, String c_tipo, String c_unidadcodigo) {
        this.c_codigo = c_codigo;
        this.c_descripcion = c_descripcion;
        this.c_tipo = c_tipo;
        this.c_unidadcodigo = c_unidadcodigo;
    }

    public String getC_codigo() {
        return c_codigo;
    }

    public void setC_codigo(String c_codigo) {
        this.c_codigo = c_codigo;
    }

    public String getC_descripcion() {
        return c_descripcion;
    }

    public void setC_descripcion(String c_descripcion) {
        this.c_descripcion = c_descripcion;
    }

    public String getC_tipo() {
        return c_tipo;
    }

    public void setC_tipo(String c_tipo) {
        this.c_tipo = c_tipo;
    }

    public String getC_unidadcodigo() {
        return c_unidadcodigo;
    }

    public void setC_unidadcodigo(String c_unidadcodigo) {
        this.c_unidadcodigo = c_unidadcodigo;
    }
}
