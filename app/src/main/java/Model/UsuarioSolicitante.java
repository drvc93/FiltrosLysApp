package Model;

/**
 * Created by dvillanueva on 20/11/2017.
 */

public class UsuarioSolicitante {
    private  int n_persona ;
    private String c_nombreCompleto ;
    private String c_numerodocumento ;

    public UsuarioSolicitante(){}
    public UsuarioSolicitante(int n_persona, String c_nombreCompleto, String c_numerodocumento) {
        this.n_persona = n_persona;
        this.c_nombreCompleto = c_nombreCompleto;
        this.c_numerodocumento = c_numerodocumento;
    }

    public int getN_persona() {
        return n_persona;
    }

    public void setN_persona(int n_persona) {
        this.n_persona = n_persona;
    }

    public String getC_nombreCompleto() {
        return c_nombreCompleto;
    }

    public void setC_nombreCompleto(String c_nombreCompleto) {
        this.c_nombreCompleto = c_nombreCompleto;
    }

    public String getC_numerodocumento() {
        return c_numerodocumento;
    }

    public void setC_numerodocumento(String c_numerodocumento) {
        this.c_numerodocumento = c_numerodocumento;
    }
}
