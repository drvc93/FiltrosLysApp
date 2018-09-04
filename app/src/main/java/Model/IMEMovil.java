package Model;

/**
 * Creado por dvillanueva el  01/03/2018 (FiltrosLysApp).
 */

public class IMEMovil  {

   private  String  c_imei ;
   private  String c_tipo;
   private  String c_numero;
   private  String c_usuarioreg;
   private  String d_fechareg;
   private  String c_estado;
   private  String c_ultimousuario;
   private  String d_ultimafechamodificacion;
   private  String c_seriechip;

    public IMEMovil() {

    }

    public IMEMovil(String c_imei, String c_tipo, String c_numero, String c_usuarioreg, String d_fechareg, String c_estado, String c_ultimousuario, String d_ultimafechamodificacion, String c_seriechip) {
        this.c_imei = c_imei;
        this.c_tipo = c_tipo;
        this.c_numero = c_numero;
        this.c_usuarioreg = c_usuarioreg;
        this.d_fechareg = d_fechareg;
        this.c_estado = c_estado;
        this.c_ultimousuario = c_ultimousuario;
        this.d_ultimafechamodificacion = d_ultimafechamodificacion;
        this.c_seriechip = c_seriechip;
    }

    public String getC_imei() {
        return c_imei;
    }

    public void setC_imei(String c_imei) {
        this.c_imei = c_imei;
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

    public String getC_usuarioreg() {
        return c_usuarioreg;
    }

    public void setC_usuarioreg(String c_usuarioreg) {
        this.c_usuarioreg = c_usuarioreg;
    }

    public String getD_fechareg() {
        return d_fechareg;
    }

    public void setD_fechareg(String d_fechareg) {
        this.d_fechareg = d_fechareg;
    }

    public String getC_estado() {
        return c_estado;
    }

    public void setC_estado(String c_estado) {
        this.c_estado = c_estado;
    }

    public String getC_ultimousuario() {
        return c_ultimousuario;
    }

    public void setC_ultimousuario(String c_ultimousuario) {
        this.c_ultimousuario = c_ultimousuario;
    }

    public String getD_ultimafechamodificacion() {
        return d_ultimafechamodificacion;
    }

    public void setD_ultimafechamodificacion(String d_ultimafechamodificacion) {
        this.d_ultimafechamodificacion = d_ultimafechamodificacion;
    }

    public String getC_seriechip() {
        return c_seriechip;
    }

    public void setC_seriechip(String c_seriechip) {
        this.c_seriechip = c_seriechip;
    }
}
