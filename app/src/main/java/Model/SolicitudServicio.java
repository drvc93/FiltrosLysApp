package Model;

/**
 * Created by dvillanueva on 17/11/2017.
 */

public class SolicitudServicio {
    private String c_compania ;
    private int n_solicitud ;
    private String c_maquina ;
    private String  c_maquinanomb;
    private String c_ccosto ;
    private String c_ccostonomb ;
    private String c_personasolicitud ;
    private String c_usuariosolicit ;
    private String c_prioridad  ;
    private String c_descriproblema ;
    private String c_descfalla ;
    private String c_estado ;
    private String c_fechareg ;
    private String c_tiposolcitud;

    public SolicitudServicio(String c_compania, int n_solicitud, String c_maquina, String c_maquinanomb, String c_ccosto, String c_ccostonomb, String c_personasolicitud, String c_usuariosolicit, String c_prioridad, String c_descriproblema, String c_descfalla, String c_estado, String c_fechareg, String c_tiposolcitud) {
        this.c_compania = c_compania;
        this.n_solicitud = n_solicitud;
        this.c_maquina = c_maquina;
        this.c_maquinanomb = c_maquinanomb;
        this.c_ccosto = c_ccosto;
        this.c_ccostonomb = c_ccostonomb;
        this.c_personasolicitud = c_personasolicitud;
        this.c_usuariosolicit = c_usuariosolicit;
        this.c_prioridad = c_prioridad;
        this.c_descriproblema = c_descriproblema;
        this.c_descfalla = c_descfalla;
        this.c_estado = c_estado;
        this.c_fechareg = c_fechareg;
        this.c_tiposolcitud = c_tiposolcitud;
    }

    public  SolicitudServicio () {}

    public String getC_compania() {
        return c_compania;
    }

    public void setC_compania(String c_compania) {
        this.c_compania = c_compania;
    }

    public int getN_solicitud() {
        return n_solicitud;
    }

    public void setN_solicitud(int n_solicitud) {
        this.n_solicitud = n_solicitud;
    }

    public String getC_maquina() {
        return c_maquina;
    }

    public void setC_maquina(String c_maquina) {
        this.c_maquina = c_maquina;
    }

    public String getC_maquinanomb() {
        return c_maquinanomb;
    }

    public void setC_maquinanomb(String c_maquinanomb) {
        this.c_maquinanomb = c_maquinanomb;
    }

    public String getC_ccosto() {
        return c_ccosto;
    }

    public void setC_ccosto(String c_ccosto) {
        this.c_ccosto = c_ccosto;
    }

    public String getC_ccostonomb() {
        return c_ccostonomb;
    }

    public void setC_ccostonomb(String c_ccostonomb) {
        this.c_ccostonomb = c_ccostonomb;
    }

    public String getC_personasolicitud() {
        return c_personasolicitud;
    }

    public void setC_personasolicitud(String c_personasolicitud) {
        this.c_personasolicitud = c_personasolicitud;
    }

    public String getC_usuariosolicit() {
        return c_usuariosolicit;
    }

    public void setC_usuariosolicit(String c_usuariosolicit) {
        this.c_usuariosolicit = c_usuariosolicit;
    }

    public String getC_prioridad() {
        return c_prioridad;
    }

    public void setC_prioridad(String c_prioridad) {
        this.c_prioridad = c_prioridad;
    }

    public String getC_descriproblema() {
        return c_descriproblema;
    }

    public void setC_descriproblema(String c_descriproblema) {
        this.c_descriproblema = c_descriproblema;
    }

    public String getC_descfalla() {
        return c_descfalla;
    }

    public void setC_descfalla(String c_descfalla) {
        this.c_descfalla = c_descfalla;
    }

    public String getC_estado() {
        return c_estado;
    }

    public void setC_estado(String c_estado) {
        this.c_estado = c_estado;
    }

    public String getC_fechareg() {
        return c_fechareg;
    }

    public void setC_fechareg(String c_fechareg) {
        this.c_fechareg = c_fechareg;
    }
    public String getC_tiposolcitud() {
        return c_tiposolcitud;
    }

    public void setC_tiposolcitud(String c_tiposolcitud) {
        this.c_tiposolcitud = c_tiposolcitud;
    }


}
