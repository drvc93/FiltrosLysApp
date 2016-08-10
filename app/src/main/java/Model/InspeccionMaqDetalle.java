package Model;

/**
 * Created by dvillanueva on 10/08/2016.
 */
public class InspeccionMaqDetalle {

    private  String descripcionInspecion ;
    private  String compania ;
    private  String correlativo;
    private  String linea;
    private  String cod_inspeccion;
    private  String tipo_inspecicon;
    private  String porcentMin;
    private  String porcentMax;
    private  String porcentInspec;
    private  String estado ;
    private  String comentario ;
    private  String rutaFoto;
    private  String ultimoUser;
    private  String ultimaFechaMod;

    public InspeccionMaqDetalle() {

    }

    public InspeccionMaqDetalle(String cod_inspeccion, String comentario, String compania, String correlativo, String descripcionInspecion, String estado, String linea, String porcentInspec, String porcentMax, String porcentMin, String rutaFoto, String tipo_inspecicon, String ultimaFechaMod, String ultimoUser) {
        this.cod_inspeccion = cod_inspeccion;
        this.comentario = comentario;
        this.compania = compania;
        this.correlativo = correlativo;
        this.descripcionInspecion = descripcionInspecion;
        this.estado = estado;
        this.linea = linea;
        this.porcentInspec = porcentInspec;
        this.porcentMax = porcentMax;
        this.porcentMin = porcentMin;
        this.rutaFoto = rutaFoto;
        this.tipo_inspecicon = tipo_inspecicon;
        this.ultimaFechaMod = ultimaFechaMod;
        this.ultimoUser = ultimoUser;
    }

    public String getCod_inspeccion() {
        return cod_inspeccion;
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public void setCod_inspeccion(String cod_inspeccion) {
        this.cod_inspeccion = cod_inspeccion;
    }

    public String getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


    public String getUltimoUser() {
        return ultimoUser;
    }

    public void setUltimoUser(String ultimoUser) {
        this.ultimoUser = ultimoUser;
    }

    public String getUltimaFechaMod() {
        return ultimaFechaMod;
    }

    public void setUltimaFechaMod(String ultimaFechaMod) {
        this.ultimaFechaMod = ultimaFechaMod;
    }

    public String getTipo_inspecicon() {
        return tipo_inspecicon;
    }

    public void setTipo_inspecicon(String tipo_inspecicon) {
        this.tipo_inspecicon = tipo_inspecicon;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public String getPorcentMin() {
        return porcentMin;
    }

    public void setPorcentMin(String porcentMin) {
        this.porcentMin = porcentMin;
    }

    public String getPorcentMax() {
        return porcentMax;
    }

    public void setPorcentMax(String porcentMax) {
        this.porcentMax = porcentMax;
    }

    public String getPorcentInspec() {
        return porcentInspec;
    }

    public void setPorcentInspec(String porcentInspec) {
        this.porcentInspec = porcentInspec;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcionInspecion() {
        return descripcionInspecion;
    }

    public void setDescripcionInspecion(String descripcionInspecion) {
        this.descripcionInspecion = descripcionInspecion;
    }
}
