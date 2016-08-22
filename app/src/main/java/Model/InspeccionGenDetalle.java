package Model;

/**
 * Created by dvillanueva on 22/08/2016.
 */
public class InspeccionGenDetalle {

    private String descripcionInspGen;
    private String compania;
    private String correlativo;
    private String linea;
    private String comentario = "";
    private String rutaFoto = "";
    private String ultUsuario;
    private String ultFechaMod;
    private String tipoRevision;
    private String flagadictipo;

    public InspeccionGenDetalle() {

    }

    public InspeccionGenDetalle(String comentario, String compania, String correlativo, String descripcionInspGen, String flagadictipo, String linea, String rutaFoto, String tipoRevision, String ultFechaMod, String ultUsuario) {
        this.comentario = comentario;
        this.compania = compania;
        this.correlativo = correlativo;
        this.descripcionInspGen = descripcionInspGen;
        this.flagadictipo = flagadictipo;
        this.linea = linea;
        this.rutaFoto = rutaFoto;
        this.tipoRevision = tipoRevision;
        this.ultFechaMod = ultFechaMod;
        this.ultUsuario = ultUsuario;
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

    public String getDescripcionInspGen() {
        return descripcionInspGen;
    }

    public void setDescripcionInspGen(String descripcionInspGen) {
        this.descripcionInspGen = descripcionInspGen;
    }

    public String getFlagadictipo() {
        return flagadictipo;
    }

    public void setFlagadictipo(String flagadictipo) {
        this.flagadictipo = flagadictipo;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public String getTipoRevision() {
        return tipoRevision;
    }

    public void setTipoRevision(String tipoRevision) {
        this.tipoRevision = tipoRevision;
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
}
