package DataBase;

/**
 * Created by dvillanueva on 23/08/2016.
 */
public class HistorialInspGenDB {

    private String numero;
    private String tipoInsp;
    private String codMaq;
    private String codCosto;
    private String usarioInp;
    private String fecha;
    private String comentario;

    public HistorialInspGenDB() {
    }

    public HistorialInspGenDB(String numero, String tipoInsp, String codMaq, String codCosto, String usarioInp, String fecha, String comentario) {
        this.numero = numero;
        this.tipoInsp = tipoInsp;
        this.codMaq = codMaq;
        this.codCosto = codCosto;
        this.usarioInp = usarioInp;
        this.fecha = fecha;
        this.comentario = comentario;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipoInsp() {
        return tipoInsp;
    }

    public void setTipoInsp(String tipoInsp) {
        this.tipoInsp = tipoInsp;
    }

    public String getCodMaq() {
        return codMaq;
    }

    public void setCodMaq(String codMaq) {
        this.codMaq = codMaq;
    }

    public String getCodCosto() {
        return codCosto;
    }

    public void setCodCosto(String codCosto) {
        this.codCosto = codCosto;
    }

    public String getUsarioInp() {
        return usarioInp;
    }

    public void setUsarioInp(String usarioInp) {
        this.usarioInp = usarioInp;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


}
