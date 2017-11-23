package Model;

/**
 * Created by dvillanueva on 22/11/2017.
 */

public class RequisicionLogCab {
    private String c_compania ;
    private String c_numeroreq ;
    private String c_fechacreacion ;
    private String c_usuariocreacion ;
    private String c_prioridad;
    private String c_prioridadnomb;
    private String c_usuarioaprobacion  ;
    private String c_fechaaprobacion ;
    private String c_ccosto;
    private String c_ccostonomb ;
    private String c_cometario ;
    private String c_estado ;
    private String c_estadonomb ;

    public RequisicionLogCab(String c_compania, String c_numeroreq, String c_fechacreacion, String c_usuariocreacion, String c_prioridad, String c_prioridadnomb, String c_usuarioaprobacion, String c_fechaaprobacion, String c_ccosto, String c_ccostonomb, String c_cometario, String c_estado, String c_estadonomb) {
        this.c_compania = c_compania;
        this.c_numeroreq = c_numeroreq;
        this.c_fechacreacion = c_fechacreacion;
        this.c_usuariocreacion = c_usuariocreacion;
        this.c_prioridad = c_prioridad;
        this.c_prioridadnomb = c_prioridadnomb;
        this.c_usuarioaprobacion = c_usuarioaprobacion;
        this.c_fechaaprobacion = c_fechaaprobacion;
        this.c_ccosto = c_ccosto;
        this.c_ccostonomb = c_ccostonomb;
        this.c_cometario = c_cometario;
        this.c_estado = c_estado;
        this.c_estadonomb = c_estadonomb;
    }

    public RequisicionLogCab() {
    }

    public String getC_compania() {
        return c_compania;
    }

    public void setC_compania(String c_compania) {
        this.c_compania = c_compania;
    }

    public String getC_numeroreq() {
        return c_numeroreq;
    }

    public void setC_numeroreq(String c_numeroreq) {
        this.c_numeroreq = c_numeroreq;
    }

    public String getC_fechacreacion() {
        return c_fechacreacion;
    }

    public void setC_fechacreacion(String c_fechacreacion) {
        this.c_fechacreacion = c_fechacreacion;
    }

    public String getC_usuariocreacion() {
        return c_usuariocreacion;
    }

    public void setC_usuariocreacion(String c_usuariocreacion) {
        this.c_usuariocreacion = c_usuariocreacion;
    }

    public String getC_prioridad() {
        return c_prioridad;
    }

    public void setC_prioridad(String c_prioridad) {
        this.c_prioridad = c_prioridad;
    }

    public String getC_prioridadnomb() {
        return c_prioridadnomb;
    }

    public void setC_prioridadnomb(String c_prioridadnomb) {
        this.c_prioridadnomb = c_prioridadnomb;
    }

    public String getC_usuarioaprobacion() {
        return c_usuarioaprobacion;
    }

    public void setC_usuarioaprobacion(String c_usuarioaprobacion) {
        this.c_usuarioaprobacion = c_usuarioaprobacion;
    }

    public String getC_fechaaprobacion() {
        return c_fechaaprobacion;
    }

    public void setC_fechaaprobacion(String c_fechaaprobacion) {
        this.c_fechaaprobacion = c_fechaaprobacion;
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

    public String getC_cometario() {
        return c_cometario;
    }

    public void setC_cometario(String c_cometario) {
        this.c_cometario = c_cometario;
    }

    public String getC_estado() {
        return c_estado;
    }

    public void setC_estado(String c_estado) {
        this.c_estado = c_estado;
    }

    public String getC_estadonomb() {
        return c_estadonomb;
    }

    public void setC_estadonomb(String c_estadonomb) {
        this.c_estadonomb = c_estadonomb;
    }
}
