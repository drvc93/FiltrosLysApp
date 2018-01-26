package Model;

/**
 * Creado por dvillanueva el  22/01/2018 (FiltrosLysApp).
 */

public class TMACliente {

    public  String c_compania ;
    public  int n_cliente;
    public  String c_razonsocial;

    public TMACliente(String c_compania, int n_cliente, String c_razonsocial) {
        this.c_compania = c_compania;
        this.n_cliente = n_cliente;
        this.c_razonsocial = c_razonsocial;
    }

    public TMACliente() {
    }

    public String getC_compania() {
        return c_compania;
    }

    public void setC_compania(String c_compania) {
        this.c_compania = c_compania;
    }

    public int getN_cliente() {
        return n_cliente;
    }

    public void setN_cliente(int n_cliente) {
        this.n_cliente = n_cliente;
    }

    public String getC_razonsocial() {
        return c_razonsocial;
    }

    public void setC_razonsocial(String c_razonsocial) {
        this.c_razonsocial = c_razonsocial;
    }
}
