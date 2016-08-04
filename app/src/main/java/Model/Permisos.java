package Model;

/**
 * Created by dvillanueva on 04/08/2016.
 */
public class Permisos {
    private String codigoNivel;
    private String Descripcion;
    private String Valor;

    public Permisos(String codigoNivel, String descripcion, String valor) {
        this.codigoNivel = codigoNivel;
        Descripcion = descripcion;
        Valor = valor;
    }

    public String getCodigoNivel() {
        return codigoNivel;
    }

    public void setCodigoNivel(String codigoNivel) {
        this.codigoNivel = codigoNivel;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String valor) {
        Valor = valor;
    }

    public Permisos() {
    }
}
