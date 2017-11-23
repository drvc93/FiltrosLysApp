package Model;

/**
 * Created by dvillanueva on 23/11/2017.
 */

public class RequisicionLogDet {

    private String  c_compania ;
    private String c_item ;
    private String c_descripcion ;
    private String c_cantidadpedida;
    private String c_stockdisponible ;
    private String c_stockminimo ;

    public RequisicionLogDet(String c_compania, String c_item, String c_descripcion, String c_cantidadpedida, String c_stockdisponible, String c_stockminimo) {
        this.c_compania = c_compania;
        this.c_item = c_item;
        this.c_descripcion = c_descripcion;
        this.c_cantidadpedida = c_cantidadpedida;
        this.c_stockdisponible = c_stockdisponible;
        this.c_stockminimo = c_stockminimo;
    }

    public RequisicionLogDet() {
    }

    public String getC_compania() {
        return c_compania;
    }

    public void setC_compania(String c_compania) {
        this.c_compania = c_compania;
    }

    public String getC_item() {
        return c_item;
    }

    public void setC_item(String c_item) {
        this.c_item = c_item;
    }

    public String getC_descripcion() {
        return c_descripcion;
    }

    public void setC_descripcion(String c_descripcion) {
        this.c_descripcion = c_descripcion;
    }

    public String getC_cantidadpedida() {
        return c_cantidadpedida;
    }

    public void setC_cantidadpedida(String c_cantidadpedida) {
        this.c_cantidadpedida = c_cantidadpedida;
    }

    public String getC_stockdisponible() {
        return c_stockdisponible;
    }

    public void setC_stockdisponible(String c_stockdisponible) {
        this.c_stockdisponible = c_stockdisponible;
    }

    public String getC_stockminimo() {
        return c_stockminimo;
    }

    public void setC_stockminimo(String c_stockminimo) {
        this.c_stockminimo = c_stockminimo;
    }
}
