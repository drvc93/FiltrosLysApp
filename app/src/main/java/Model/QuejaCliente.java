package Model;

public class QuejaCliente {
    private String c_compania;
    private long n_correlativo;
    private String c_queja;
    private String c_nroformato;
    private int n_cliente;
    private String d_fechareg;
    private String c_documentoref;
    private String c_mediorecepcion;
    private String c_centrocosto;
    private String c_calificacion;
    private String c_usuarioderiv;
    private String c_tipocalificacion;
    private String c_item;
    private String c_lote;
    private double n_cantidad;
    private String c_desqueja;
    private String c_observaciones;
    private String c_usuarioinvestigacion;
    private String d_fechaderivacion;
    private String c_unidadneg;
    private String c_razonsocial;
    private String c_estado;
    private String c_usuario;
    private String c_flaginvestigando;
    private String c_descripcioninvestigacion;
    private String c_procede;
    private String d_fecharespuesta;
    private String c_usuarioinvestigadopor;
    private String d_fechainvestigadopor;
    private String c_tipocalificacioncierre;
    private String c_descripcioncierre;
    private String c_codaccion1;
    private String c_codaccion2;
    private String c_codaccion3;
    private String c_codaccion4;
    private String c_notificacion;
    private String c_cerrado;
    private String c_accioncorrectiva;
    private String c_observacionescierre;
    private String c_usuariocerrado;
    private String d_fechacerrado;

    public QuejaCliente() {
    }

    public QuejaCliente(String c_compania, long n_correlativo, String c_queja, String c_nroformato, int n_cliente, String d_fechareg,
                        String c_documentoref, String c_mediorecepcion, String c_centrocosto, String c_calificacion, String c_usuarioderiv,
                        String c_tipocalificacion, String c_item, String c_lote, double n_cantidad, String c_desqueja, String c_observaciones,
                        String c_usuarioinvestigacion, String d_fechaderivacion,
                        String c_unidadneg, String c_razonsocial, String c_estado, String c_usuario, String c_flaginvestigando,
                        String c_descripcioninvestigacion, String c_procede, String d_fecharespuesta, String c_usuarioinvestigadopor,
                        String d_fechainvestigadopor, String c_tipocalificacioncierre, String c_descripcioncierre, String c_codaccion1,
                        String c_codaccion2, String c_codaccion3, String c_codaccion4, String c_notificacion, String c_cerrado,
                        String c_accioncorrectiva, String c_observacionescierre,String c_usuariocerrado, String d_fechacerrado) {
        this.c_compania = c_compania;
        this.n_correlativo = n_correlativo;
        this.c_queja = c_queja;
        this.c_nroformato = c_nroformato;
        this.n_cliente = n_cliente;
        this.d_fechareg = d_fechareg;
        this.c_documentoref = c_documentoref;
        this.c_mediorecepcion = c_mediorecepcion;
        this.c_centrocosto = c_centrocosto;
        this.c_calificacion = c_calificacion;
        this.c_usuarioderiv = c_usuarioderiv;
        this.c_tipocalificacion = c_tipocalificacion;
        this.c_item = c_item;
        this.c_lote = c_lote;
        this.n_cantidad = n_cantidad;
        this.c_desqueja = c_desqueja;
        this.c_observaciones = c_observaciones;
        this.c_usuarioinvestigacion = c_usuarioinvestigacion;
        this.d_fechaderivacion = d_fechaderivacion;
        this.c_unidadneg = c_unidadneg;
        this.c_razonsocial = c_razonsocial;
        this.c_estado = c_estado;
        this.c_usuario = c_usuario;
        this.c_flaginvestigando = c_flaginvestigando;
        this.c_descripcioninvestigacion = c_descripcioninvestigacion;
        this.c_procede = c_procede;
        this.d_fecharespuesta = d_fecharespuesta;
        this.c_usuarioinvestigadopor = c_usuarioinvestigadopor;
        this.d_fechainvestigadopor = d_fechainvestigadopor;
        this.c_tipocalificacioncierre = c_tipocalificacioncierre;
        this.c_descripcioncierre = c_descripcioncierre;
        this.c_codaccion1 = c_codaccion1;
        this.c_codaccion2 = c_codaccion2;
        this.c_codaccion3 = c_codaccion3;
        this.c_codaccion4 = c_codaccion4;
        this.c_notificacion = c_notificacion;
        this.c_cerrado = c_cerrado;
        this.c_accioncorrectiva = c_accioncorrectiva;
        this.c_observacionescierre = c_observacionescierre;
        this.c_usuariocerrado = c_usuariocerrado;
        this.d_fechacerrado = d_fechacerrado;
    }

    public String getC_compania() {
        return c_compania;
    }

    public void setC_compania(String c_compania) {
        this.c_compania = c_compania;
    }

    public long getN_correlativo() {
        return n_correlativo;
    }

    public void setN_correlativo(long n_correlativo) {
        this.n_correlativo = n_correlativo;
    }

    public String getC_queja() {
        return c_queja;
    }

    public void setC_queja(String c_queja) {
        this.c_queja = c_queja;
    }

    public String getC_nroformato() {
        return c_nroformato;
    }

    public void setC_nroformato(String c_nroformato) {
        this.c_nroformato = c_nroformato;
    }

    public int getN_cliente() {
        return n_cliente;
    }

    public void setN_cliente(int n_cliente) {
        this.n_cliente = n_cliente;
    }

    public String getD_fechareg() {
        return d_fechareg;
    }

    public void setD_fechareg(String d_fechareg) {
        this.d_fechareg = d_fechareg;
    }

    public String getC_documentoref() {
        return c_documentoref;
    }

    public void setC_documentoref(String c_documentoref) {
        this.c_documentoref = c_documentoref;
    }

    public String getC_mediorecepcion() {
        return c_mediorecepcion;
    }

    public void setC_mediorecepcion(String c_mediorecepcion) {
        this.c_mediorecepcion = c_mediorecepcion;
    }

    public String getC_centrocosto() {
        return c_centrocosto;
    }

    public void setC_centrocosto(String c_centrocosto) {
        this.c_centrocosto = c_centrocosto;
    }

    public String getC_calificacion() {
        return c_calificacion;
    }

    public void setC_calificacion(String c_calificacion) {
        this.c_calificacion = c_calificacion;
    }

    public String getC_usuarioderiv() {
        return c_usuarioderiv;
    }

    public void setC_usuarioderiv(String c_usuarioderiv) {
        this.c_usuarioderiv = c_usuarioderiv;
    }

    public String getC_tipocalificacion() {
        return c_tipocalificacion;
    }

    public void setC_tipocalificacion(String c_tipocalificacion) {
        this.c_tipocalificacion = c_tipocalificacion;
    }

    public String getC_item() {
        return c_item;
    }

    public void setC_item(String c_item) {
        this.c_item = c_item;
    }

    public String getC_lote() {
        return c_lote;
    }

    public void setC_lote(String c_lote) {
        this.c_lote = c_lote;
    }

    public double getN_cantidad() {
        return n_cantidad;
    }

    public void setN_cantidad(double n_cantidad) {
        this.n_cantidad = n_cantidad;
    }

    public String getC_desqueja() {
        return c_desqueja;
    }

    public void setC_desqueja(String c_desqueja) {
        this.c_desqueja = c_desqueja;
    }

    public String getC_observaciones() {
        return c_observaciones;
    }

    public void setC_observaciones(String c_observaciones) {
        this.c_observaciones = c_observaciones;
    }

    public String getC_usuarioinvestigacion() {
        return c_usuarioinvestigacion;
    }

    public void setC_usuarioinvestigacion(String c_usuarioinvestigacion) {
        this.c_usuarioinvestigacion = c_usuarioinvestigacion;
    }

    public String getD_fechaderivacion() {
        return d_fechaderivacion;
    }

    public void setD_fechaderivacion(String d_fechaderivacion) {
        this.d_fechaderivacion = d_fechaderivacion;
    }

    public String getC_unidadneg() {
        return c_unidadneg;
    }

    public void setC_unidadneg(String c_unidadneg) {
        this.c_unidadneg = c_unidadneg;
    }

    public String getC_razonsocial() {
        return c_razonsocial;
    }

    public void setC_razonsocial(String c_razonsocial) {
        this.c_razonsocial = c_razonsocial;
    }

    public String getC_estado() {
        return c_estado;
    }

    public void setC_estado(String c_estado) {
        this.c_estado = c_estado;
    }

    public String getC_usuario() {
        return c_usuario;
    }

    public void setC_usuario(String c_usuario) {
        this.c_usuario = c_usuario;
    }

    public String getC_flaginvestigando() {
        return c_flaginvestigando;
    }

    public void setC_flaginvestigando(String c_flaginvestigando) {
        this.c_flaginvestigando = c_flaginvestigando;
    }

    public String getC_descripcioninvestigacion() {
        return c_descripcioninvestigacion;
    }

    public void setC_descripcioninvestigacion(String c_descripcioninvestigacion) {
        this.c_descripcioninvestigacion = c_descripcioninvestigacion;
    }

    public String getC_procede() {
        return c_procede;
    }

    public void setC_procede(String c_procede) {
        this.c_procede = c_procede;
    }

    public String getD_fecharespuesta() {
        return d_fecharespuesta;
    }

    public void setD_fecharespuesta(String d_fecharespuesta) {
        this.d_fecharespuesta = d_fecharespuesta;
    }

    public String getC_usuarioinvestigadopor() {
        return c_usuarioinvestigadopor;
    }

    public void setC_usuarioinvestigadopor(String c_usuarioinvetigadopor) {
        this.c_usuarioinvestigadopor = c_usuarioinvetigadopor;
    }

    public String getD_fechainvestigadopor() {
        return d_fechainvestigadopor;
    }

    public void setD_fechainvestigadopor(String d_fechainvestigadopor) {
        this.d_fechainvestigadopor = d_fechainvestigadopor;
    }

    public String getC_tipocalificacioncierre() {
        return c_tipocalificacioncierre;
    }

    public void setC_tipocalificacioncierre(String c_tipocalificacioncierre) {
        this.c_tipocalificacioncierre = c_tipocalificacioncierre;
    }

    public String getC_descripcioncierre() {
        return c_descripcioncierre;
    }

    public void setC_descripcioncierre(String c_descripcioncierre) {
        this.c_descripcioncierre = c_descripcioncierre;
    }

    public String getC_codaccion1() {
        return c_codaccion1;
    }

    public void setC_codaccion1(String c_codaccion1) {
        this.c_codaccion1 = c_codaccion1;
    }

    public String getC_codaccion2() {
        return c_codaccion2;
    }

    public void setC_codaccion2(String c_codaccion2) {
        this.c_codaccion2 = c_codaccion2;
    }

    public String getC_codaccion3() {
        return c_codaccion3;
    }

    public void setC_codaccion3(String c_codaccion3) {
        this.c_codaccion3 = c_codaccion3;
    }

    public String getC_codaccion4() {
        return c_codaccion4;
    }

    public void setC_codaccion4(String c_codaccion4) {
        this.c_codaccion4 = c_codaccion4;
    }

    public String getC_notificacion() {
        return c_notificacion;
    }

    public void setC_notificacion(String c_notificacion) {
        this.c_notificacion = c_notificacion;
    }

    public String getC_cerrado() {
        return c_cerrado;
    }

    public void setC_cerrado(String c_cerrado) {
        this.c_cerrado = c_cerrado;
    }

    public String getC_accioncorrectiva() {
        return c_accioncorrectiva;
    }

    public void setC_accioncorrectiva(String c_accioncorrectiva) {
        this.c_accioncorrectiva = c_accioncorrectiva;
    }

    public String getC_observacionescierre() {
        return c_observacionescierre;
    }

    public void setC_observacionescierre(String c_observacionescierre) {
        this.c_observacionescierre = c_observacionescierre;
    }

    public String getC_usuariocerrado() {
        return c_usuariocerrado;
    }

    public void setC_usuariocerrado(String c_usuariocerrado) {
        this.c_usuariocerrado = c_usuariocerrado;
    }

    public String getD_fechacerrado() {
        return d_fechacerrado;
    }

    public void setD_fechacerrado(String d_fechacerrado) {
        this.d_fechacerrado = d_fechacerrado;
    }

}
