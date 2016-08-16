package DataBase;

/**
 * Created by dvillanueva on 16/08/2016.
 */
public class CentroCostoDB {
    private String c_compania;
    private String c_centrocosto;
    private String c_descripcion;
    private String c_estado;

    public CentroCostoDB() {
    }

    public CentroCostoDB(String c_compania, String c_centrocosto, String c_descripcion, String c_estado) {
        this.c_compania = c_compania;
        this.c_centrocosto = c_centrocosto;
        this.c_descripcion = c_descripcion;
        this.c_estado = c_estado;
    }

    public String getC_compania() {
        return c_compania;
    }

    public void setC_compania(String c_compania) {
        this.c_compania = c_compania;
    }

    public String getC_centrocosto() {
        return c_centrocosto;
    }

    public void setC_centrocosto(String c_centrocosto) {
        this.c_centrocosto = c_centrocosto;
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
