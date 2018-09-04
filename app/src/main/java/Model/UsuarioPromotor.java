package Model;

/**
 * Creado por dvillanueva el  19/04/2018 (FiltrosLysApp).
 */

public class UsuarioPromotor {
    private String c_usuario ;
    private String c_nombusuario;

    public UsuarioPromotor() {
    }

    public UsuarioPromotor(String c_usuario, String c_nombusuario) {
        this.c_usuario = c_usuario;
        this.c_nombusuario = c_nombusuario;
    }

    public String getC_usuario() {
        return c_usuario;
    }

    public void setC_usuario(String c_usuario) {
        this.c_usuario = c_usuario;
    }

    public String getC_nombusuario() {
        return c_nombusuario;
    }

    public void setC_nombusuario(String c_nombusuario) {
        this.c_nombusuario = c_nombusuario;
    }
}
