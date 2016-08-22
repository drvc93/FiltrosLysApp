package Model;

/**
 * Created by dvillanueva on 22/08/2016.
 */
public class InspeccionGenCabecera {


    private String compania;
    private String correlativo;
    private String tipoInspeccion;
    private String cod_maquina;
    private String centroCosto;
    private String comentario;
    private String usuarioInsp;
    private String fechaInsp;
    private String estado;
    private String usuarioEnvio;
    private String fechaEnvia;
    private String ultUsuario;
    private String ultFechaMod;

    public InspeccionGenCabecera() {
    }

    public InspeccionGenCabecera(String centroCosto, String cod_maquina, String comentario, String compania, String correlativo, String estado, String fechaEnvia, String fechaInsp, String tipoInspeccion, String ultFechaMod, String ultUsuario, String usuarioEnvio, String usuarioInsp) {
        this.centroCosto = centroCosto;
        this.cod_maquina = cod_maquina;
        this.comentario = comentario;
        this.compania = compania;
        this.correlativo = correlativo;
        this.estado = estado;
        this.fechaEnvia = fechaEnvia;
        this.fechaInsp = fechaInsp;
        this.tipoInspeccion = tipoInspeccion;
        this.ultFechaMod = ultFechaMod;
        this.ultUsuario = ultUsuario;
        this.usuarioEnvio = usuarioEnvio;
        this.usuarioInsp = usuarioInsp;
    }

    public String getCentroCosto() {
        return centroCosto;
    }

    public void setCentroCosto(String centroCosto) {
        this.centroCosto = centroCosto;
    }

    public String getCod_maquina() {
        return cod_maquina;
    }

    public void setCod_maquina(String cod_maquina) {
        this.cod_maquina = cod_maquina;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public String getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaEnvia() {
        return fechaEnvia;
    }

    public void setFechaEnvia(String fechaEnvia) {
        this.fechaEnvia = fechaEnvia;
    }

    public String getFechaInsp() {
        return fechaInsp;
    }

    public void setFechaInsp(String fechaInsp) {
        this.fechaInsp = fechaInsp;
    }

    public String getTipoInspeccion() {
        return tipoInspeccion;
    }

    public void setTipoInspeccion(String tipoInspeccion) {
        this.tipoInspeccion = tipoInspeccion;
    }

    public String getUltFechaMod() {
        return ultFechaMod;
    }

    public void setUltFechaMod(String ultFechaMod) {
        this.ultFechaMod = ultFechaMod;
    }

    public String getUltUsuario() {
        return ultUsuario;
    }

    public void setUltUsuario(String ultUsuario) {
        this.ultUsuario = ultUsuario;
    }

    public String getUsuarioEnvio() {
        return usuarioEnvio;
    }

    public void setUsuarioEnvio(String usuarioEnvio) {
        this.usuarioEnvio = usuarioEnvio;
    }

    public String getUsuarioInsp() {
        return usuarioInsp;
    }

    public void setUsuarioInsp(String usuarioInsp) {
        this.usuarioInsp = usuarioInsp;
    }
}
