package Model;

/**
 * Creado por dvillanueva el  05/03/2018 (FiltrosLysApp).
 */

public class Parametros
{
    private  String c_compania;
    private  String c_aplicacion;
    private  String c_parametrocodigo;
    private  String c_descripcion;
    private  String c_texto;
    private  String c_ultusuario;
    private  String c_ultfechamodificacion;
    private  String d_fecha;
    private  double n_numero;

    public Parametros() {
    }

    public Parametros(String c_compania, String c_aplicacion, String c_parametrocodigo, String c_descripcion, String c_texto, String c_ultusuario, String c_ultfechamodificacion, String d_fecha, double n_numero) {
        this.c_compania = c_compania;
        this.c_aplicacion = c_aplicacion;
        this.c_parametrocodigo = c_parametrocodigo;
        this.c_descripcion = c_descripcion;
        this.c_texto = c_texto;
        this.c_ultusuario = c_ultusuario;
        this.c_ultfechamodificacion = c_ultfechamodificacion;
        this.d_fecha = d_fecha;
        this.n_numero = n_numero;
    }

    public double getN_numero() {
        return n_numero;
    }

    public void setN_numero(double n_numero) {
        this.n_numero = n_numero;
    }

    public String getD_fecha() {
        return d_fecha;
    }

    public void setD_fecha(String d_fecha) {
        this.d_fecha = d_fecha;
    }

    public String getC_compania() {
        return c_compania;
    }

    public void setC_compania(String c_compania) {
        this.c_compania = c_compania;
    }

    public String getC_aplicacion() {
        return c_aplicacion;
    }

    public void setC_aplicacion(String c_aplicacion) {
        this.c_aplicacion = c_aplicacion;
    }

    public String getC_parametrocodigo() {
        return c_parametrocodigo;
    }

    public void setC_parametrocodigo(String c_parametrocodigo) {
        this.c_parametrocodigo = c_parametrocodigo;
    }

    public String getC_descripcion() {
        return c_descripcion;
    }

    public void setC_descripcion(String c_descripcion) {
        this.c_descripcion = c_descripcion;
    }

    public String getC_texto() {
        return c_texto;
    }

    public void setC_texto(String c_texto) {
        this.c_texto = c_texto;
    }

    public String getC_ultusuario() {
        return c_ultusuario;
    }

    public void setC_ultusuario(String c_ultusuario) {
        this.c_ultusuario = c_ultusuario;
    }

    public String getC_ultfechamodificacion() {
        return c_ultfechamodificacion;
    }

    public void setC_ultfechamodificacion(String c_ultfechamodificacion) {
        this.c_ultfechamodificacion = c_ultfechamodificacion;
    }
}
