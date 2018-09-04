package Model;

/**
 * Creado por dvillanueva el  06/03/2018 (FiltrosLysApp).
 */

public class EventoAuditoriaAPP {
    private  int n_correlativo ;
    private  String d_fechaserv;
    private  String c_origen;
    private  String c_imei;
    private  String c_movil;
    private  String c_seriechip;
    private  String d_hora;
    private  String c_accion;
    private  String c_enviado ;
    private  String c_usuario;
    private  String c_codIntApp;

    public EventoAuditoriaAPP() {
    }

    public EventoAuditoriaAPP(int n_correlativo, String d_fechaserv, String c_origen, String c_imei, String c_movil, String c_seriechip, String d_hora, String c_accion, String c_enviado, String c_usuario, String c_codIntApp) {
        this.n_correlativo = n_correlativo;
        this.d_fechaserv = d_fechaserv;
        this.c_origen = c_origen;
        this.c_imei = c_imei;
        this.c_movil = c_movil;
        this.c_seriechip = c_seriechip;
        this.d_hora = d_hora;
        this.c_accion = c_accion;
        this.c_enviado = c_enviado;
        this.c_usuario = c_usuario;
        this.c_codIntApp = c_codIntApp;
    }

    public String getC_usuario() {
        return c_usuario;
    }

    public void setC_usuario(String c_usuario) {
        this.c_usuario = c_usuario;
    }

    public String getC_codIntApp() {
        return c_codIntApp;
    }

    public void setC_codIntApp(String c_codIntApp) {
        this.c_codIntApp = c_codIntApp;
    }

    public String getC_enviado() {
        return c_enviado;
    }

    public void setC_enviado(String c_enviado) {
        this.c_enviado = c_enviado;
    }

    public int getN_correlativo() {
        return n_correlativo;
    }

    public void setN_correlativo(int n_correlativo) {
        this.n_correlativo = n_correlativo;
    }

    public String getD_fechaserv() {
        return d_fechaserv;
    }

    public void setD_fechaserv(String d_fechaserv) {
        this.d_fechaserv = d_fechaserv;
    }

    public String getC_origen() {
        return c_origen;
    }

    public void setC_origen(String c_origen) {
        this.c_origen = c_origen;
    }

    public String getC_imei() {
        return c_imei;
    }

    public void setC_imei(String c_imei) {
        this.c_imei = c_imei;
    }

    public String getC_movil() {
        return c_movil;
    }

    public void setC_movil(String c_movil) {
        this.c_movil = c_movil;
    }

    public String getC_seriechip() {
        return c_seriechip;
    }

    public void setC_seriechip(String c_seriechip) {
        this.c_seriechip = c_seriechip;
    }

    public String getD_hora() {
        return d_hora;
    }

    public void setD_hora(String d_hora) {
        this.d_hora = d_hora;
    }

    public String getC_accion() {
        return c_accion;
    }

    public void setC_accion(String c_accion) {
        this.c_accion = c_accion;
    }
}
