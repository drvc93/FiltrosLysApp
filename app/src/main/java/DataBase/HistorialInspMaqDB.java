package DataBase;

/**
 * Created by dvillanueva on 16/08/2016.
 */
public class HistorialInspMaqDB {
    private String  numero ;
    private String fecha;
    private String cod_maquina;
    private String centro_costo;
    private String  frecuencia;
    private String usuario ;
    private String comentario ;

    public HistorialInspMaqDB() {
    }

    public HistorialInspMaqDB(String numero, String fecha, String cod_maquina, String centro_costo, String frecuencia, String usuario, String comentario) {
        this.numero = numero;
        this.fecha = fecha;
        this.cod_maquina = cod_maquina;
        this.centro_costo = centro_costo;
        this.frecuencia = frecuencia;
        this.usuario = usuario;
        this.comentario = comentario;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCod_maquina() {
        return cod_maquina;
    }

    public void setCod_maquina(String cod_maquina) {
        this.cod_maquina = cod_maquina;
    }

    public String getCentro_costo() {
        return centro_costo;
    }

    public void setCentro_costo(String centro_costo) {
        this.centro_costo = centro_costo;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }



}
