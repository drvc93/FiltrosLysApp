package DataBase;

/**
 * Created by dvillanueva on 19/08/2016.
 */
public class TipoRevisionGBD {
    private String cod_tiporevision;
    private String descripcion;
    private String estado;
    private String ultimoUsuario;
    private String ultFechaMod;

    public TipoRevisionGBD(String cod_tiporevision, String descripcion, String estado, String ultimoUsuario, String ultFechaMod) {
        this.cod_tiporevision = cod_tiporevision;
        this.descripcion = descripcion;
        this.estado = estado;
        this.ultimoUsuario = ultimoUsuario;
        this.ultFechaMod = ultFechaMod;
    }

    public TipoRevisionGBD() {
    }

    public String getCod_tiporevision() {
        return cod_tiporevision;
    }

    public void setCod_tiporevision(String cod_tiporevision) {
        this.cod_tiporevision = cod_tiporevision;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUltimoUsuario() {
        return ultimoUsuario;
    }

    public void setUltimoUsuario(String ultimoUsuario) {
        this.ultimoUsuario = ultimoUsuario;
    }

    public String getUltFechaMod() {
        return ultFechaMod;
    }

    public void setUltFechaMod(String ultFechaMod) {
        this.ultFechaMod = ultFechaMod;
    }


}
