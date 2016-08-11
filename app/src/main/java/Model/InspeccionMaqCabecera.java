package Model;

/**
 * Created by dvillanueva on 11/08/2016.
 */
public class InspeccionMaqCabecera {

    private  String compania  ;
    private  String corrlativo;
    private  String codMaquina;
    private  String condicionMaq;
    private  String comentario;
    private  String estado;
    private  String fechaInicioInsp;
    private  String fechaFinInsp;
    private  String periodoInsp;
    private  String usuarioInsp;
    private  String usuarioEnv;
    private  String fechaEnv;
    private  String ultUsuairo;
    private  String ultFechaMod;

    public InspeccionMaqCabecera() {
    }

    public InspeccionMaqCabecera(String codMaquina, String comentario, String compania, String condicionMaq, String corrlativo, String estado, String fechaEnv, String fechaInicioInsp, String fechaFinInsp, String periodoInsp, String ultFechaMod, String ultUsuairo, String usuarioEnv, String usuarioInsp) {
        this.codMaquina = codMaquina;
        this.comentario = comentario;
        this.compania = compania;
        this.condicionMaq = condicionMaq;
        this.corrlativo = corrlativo;
        this.estado = estado;
        this.fechaEnv = fechaEnv;
        this.fechaInicioInsp = fechaInicioInsp;
        this.fechaFinInsp = fechaFinInsp;
        this.periodoInsp = periodoInsp;
        this.ultFechaMod = ultFechaMod;
        this.ultUsuairo = ultUsuairo;
        this.usuarioEnv = usuarioEnv;
        this.usuarioInsp = usuarioInsp;
    }

    public String getCodMaquina() {
        return codMaquina;
    }

    public void setCodMaquina(String codMaquina) {
        this.codMaquina = codMaquina;
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

    public String getCondicionMaq() {
        return condicionMaq;
    }

    public void setCondicionMaq(String condicionMaq) {
        this.condicionMaq = condicionMaq;
    }

    public String getCorrlativo() {
        return corrlativo;
    }

    public void setCorrlativo(String corrlativo) {
        this.corrlativo = corrlativo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaEnv() {
        return fechaEnv;
    }

    public void setFechaEnv(String fechaEnv) {
        this.fechaEnv = fechaEnv;
    }

    public String getFechaInicioInsp() {
        return fechaInicioInsp;
    }

    public void setFechaInicioInsp(String fechaInicioInsp) {
        this.fechaInicioInsp = fechaInicioInsp;
    }

    public String getFechFinInsp() {
        return fechaFinInsp;
    }

    public void setFechFinInsp(String fechFinInsp) {
        this.fechaFinInsp = fechFinInsp;
    }

    public String getPeriodoInsp() {
        return periodoInsp;
    }

    public void setPeriodoInsp(String periodoInsp) {
        this.periodoInsp = periodoInsp;
    }

    public String getUltFechaMod() {
        return ultFechaMod;
    }

    public void setUltFechaMod(String ultFechaMod) {
        this.ultFechaMod = ultFechaMod;
    }

    public String getUltUsuairo() {
        return ultUsuairo;
    }

    public void setUltUsuairo(String ultUsuairo) {
        this.ultUsuairo = ultUsuairo;
    }

    public String getUsuarioEnv() {
        return usuarioEnv;
    }

    public void setUsuarioEnv(String usuarioEnv) {
        this.usuarioEnv = usuarioEnv;
    }

    public String getUsuarioInsp() {
        return usuarioInsp;
    }

    public void setUsuarioInsp(String usuarioInsp) {
        this.usuarioInsp = usuarioInsp;
    }
}
