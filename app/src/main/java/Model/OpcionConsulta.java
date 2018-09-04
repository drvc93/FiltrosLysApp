package Model;

/**
 * Creado por dvillanueva el  12/07/2018 (FiltrosLysApp).
 */

public class OpcionConsulta {
    private String c_compania ;
    private String c_tipo ;
    private  String c_numero ;
    private  String c_descripcion;
    private String c_exportable;

    public OpcionConsulta() {
    }

    public OpcionConsulta(String c_compania, String c_tipo, String c_numero, String c_descripcion ,String c_exportable) {
        this.c_compania = c_compania;
        this.c_tipo = c_tipo;
        this.c_numero = c_numero;
        this.c_descripcion = c_descripcion;
        this.c_exportable =c_exportable;
    }
    public String getC_exportable() {
        return c_exportable;
    }

    public void setC_exportable(String c_exportable) {
        this.c_exportable = c_exportable;
    }

    public String getC_compania() {
        return c_compania;
    }

    public void setC_compania(String c_compania) {
        this.c_compania = c_compania;
    }

    public String getC_tipo() {
        return c_tipo;
    }

    public void setC_tipo(String c_tipo) {
        this.c_tipo = c_tipo;
    }

    public String getC_numero() {
        return c_numero;
    }

    public void setC_numero(String c_numero) {
        this.c_numero = c_numero;
    }

    public String getC_descripcion() {
        return c_descripcion;
    }

    public void setC_descripcion(String c_descripcion) {
        this.c_descripcion = c_descripcion;
    }
}
