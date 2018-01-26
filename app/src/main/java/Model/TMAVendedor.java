package Model;

/**
 * Creado por dvillanueva el  23/01/2018 (FiltrosLysApp).
 */

public class TMAVendedor {
    private  String c_compania ;
    private  String c_vendedor;
    private  String c_ciasecundaria;
    private  String c_nombres;
    private  String c_estado ;

    public TMAVendedor() {
    }

    public TMAVendedor(String c_compania, String c_vendedor, String c_ciasecundaria, String c_nombres, String c_estado) {
        this.c_compania = c_compania;
        this.c_vendedor = c_vendedor;
        this.c_ciasecundaria = c_ciasecundaria;
        this.c_nombres = c_nombres;
        this.c_estado = c_estado;
    }

    public String getC_compania() {
        return c_compania;
    }

    public void setC_compania(String c_compania) {
        this.c_compania = c_compania;
    }

    public String getC_vendedor() {
        return c_vendedor;
    }

    public void setC_vendedor(String c_vendedor) {
        this.c_vendedor = c_vendedor;
    }

    public String getC_ciasecundaria() {
        return c_ciasecundaria;
    }

    public void setC_ciasecundaria(String c_ciasecundaria) {
        this.c_ciasecundaria = c_ciasecundaria;
    }

    public String getC_nombres() {
        return c_nombres;
    }

    public void setC_nombres(String c_nombres) {
        this.c_nombres = c_nombres;
    }

    public String getC_estado() {
        return c_estado;
    }

    public void setC_estado(String c_estado) {
        this.c_estado = c_estado;
    }
}
