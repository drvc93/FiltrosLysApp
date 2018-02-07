package Model;

public class TMADireccionCli {
    private String c_compania;
    private int n_cliente;
    private int n_secuencia;
    private String c_descripcion;
    private String c_estado;

    public TMADireccionCli() {
    }

    public TMADireccionCli(String c_compania,int n_cliente, int n_secuencia, String c_descripcion, String c_estado) {
        this.c_compania = c_compania;
        this.n_cliente = n_cliente;
        this.n_secuencia = n_secuencia;
        this.c_descripcion = c_descripcion;
        this.c_estado = c_estado;
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

    public int getN_secuencia() {
        return n_secuencia;
    }

    public void setN_secuencia(int n_secuencia) {
        this.n_secuencia = n_secuencia;
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
