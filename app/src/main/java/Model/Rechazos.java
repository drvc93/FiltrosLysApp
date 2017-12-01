package Model;

/**
 * Created by dvillanueva on 24/11/2017.
 */

public class Rechazos  {

    private String c_razonrechazo ;
    private String c_descripcion ;


    public Rechazos(String c_razonrechazo, String c_descripcion) {
        this.c_razonrechazo = c_razonrechazo;
        this.c_descripcion = c_descripcion;
    }

    public Rechazos() {
    }

    public String getC_razonrechazo() {
        return c_razonrechazo;
    }

    public void setC_razonrechazo(String c_razonrechazo) {
        this.c_razonrechazo = c_razonrechazo;
    }

    public String getC_descripcion() {
        return c_descripcion;
    }

    public void setC_descripcion(String c_descripcion) {
        this.c_descripcion = c_descripcion;
    }
}
