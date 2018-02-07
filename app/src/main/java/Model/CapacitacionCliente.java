package Model;

public class CapacitacionCliente {
    private String c_compania;
    private long n_correlativo;
    private int n_cliente;
    private String d_fecha;
    private long n_personas;
    private String d_fechaprob;
    private String d_horaprob;
    private String c_lugar;
    private int n_direccioncli;
    private String c_direccionreg;
    private String c_temacapacitacion;
    private String c_descripciontema;
    private String c_usuarioreg;
    private String d_fechareg;
    private String c_estado;
    private String c_usuariorev;
    private String d_fecharev;
    private String c_observacionrev;
    private String c_accionrev;
    private String c_flagfinrev;
    private String c_usuarioAN;
    private String d_fechaAN;
    private String c_observacionAN;
    private String c_usuarioCE;
    private String d_fechaCE;
    private String c_observacionCE;
    private String c_enviado;
    private String c_observacion;

    public CapacitacionCliente() {
    }

    public CapacitacionCliente(String c_compania, long n_correlativo, int n_cliente, String d_fecha, long n_personas, String d_fechaprob, String d_horaprob, String c_lugar, int n_direccioncli, String c_direccionreg, String c_temacapacitacion, String c_descripciontema, String c_usuarioreg, String d_fechareg, String c_estado, String c_usuariorev, String d_fecharev, String c_observacionrev, String c_accionrev, String c_flagfinrev, String c_usuarioAN, String d_fechaAN, String c_observacionAN, String c_usuarioCE, String d_fechaCE, String c_observacionCE, String c_enviado, String c_observacion) {
        this.c_compania = c_compania;
        this.n_correlativo = n_correlativo;
        this.n_cliente = n_cliente;
        this.d_fecha = d_fecha;
        this.n_personas = n_personas;
        this.d_fechaprob = d_fechaprob;
        this.d_horaprob = d_horaprob;
        this.c_lugar = c_lugar;
        this.n_direccioncli = n_direccioncli;
        this.c_direccionreg = c_direccionreg;
        this.c_temacapacitacion = c_temacapacitacion;
        this.c_descripciontema = c_descripciontema;
        this.c_usuarioreg = c_usuarioreg;
        this.d_fechareg = d_fechareg;
        this.c_estado = c_estado;
        this.c_usuariorev = c_usuariorev;
        this.d_fecharev = d_fecharev;
        this.c_observacionrev = c_observacionrev;
        this.c_accionrev = c_accionrev;
        this.c_flagfinrev = c_flagfinrev;
        this.c_usuarioAN = c_usuarioAN;
        this.d_fechaAN = d_fechaAN;
        this.c_observacionAN = c_observacionAN;
        this.c_usuarioCE = c_usuarioCE;
        this.d_fechaCE = d_fechaCE;
        this.c_observacionCE = c_observacionCE;
        this.c_enviado = c_enviado;
        this.c_observacion = c_observacion;
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

    public int getN_cliente() {
        return n_cliente;
    }

    public void setN_cliente(int n_cliente) {
        this.n_cliente = n_cliente;
    }

    public String getD_fecha() {
        return d_fecha;
    }

    public void setD_fecha(String d_fecha) {
        this.d_fecha = d_fecha;
    }

    public long getN_personas() {
        return n_personas;
    }

    public void setN_personas(long n_personas) {
        this.n_personas = n_personas;
    }

    public String getD_fechaprob() {
        return d_fechaprob;
    }

    public void setD_fechaprob(String d_fechaprob) {
        this.d_fechaprob = d_fechaprob;
    }

    public String getD_horaprob() {
        return d_horaprob;
    }

    public void setD_horaprob(String d_horaprob) {
        this.d_horaprob = d_horaprob;
    }

    public String getC_lugar() {
        return c_lugar;
    }

    public void setC_lugar(String c_lugar) {
        this.c_lugar = c_lugar;
    }

    public int getN_direccioncli() {
        return n_direccioncli;
    }

    public void setN_direccioncli(int n_direccioncli) {
        this.n_direccioncli = n_direccioncli;
    }

    public String getC_direccionreg() {
        return c_direccionreg;
    }

    public void setC_direccionreg(String c_direccionreg) {
        this.c_direccionreg = c_direccionreg;
    }

    public String getC_temacapacitacion() {
        return c_temacapacitacion;
    }

    public void setC_temacapacitacion(String c_temacapacitacion) {
        this.c_temacapacitacion = c_temacapacitacion;
    }

    public String getC_descripciontema() {
        return c_descripciontema;
    }

    public void setC_descripciontema(String c_descripciontema) {
        this.c_descripciontema = c_descripciontema;
    }

    public String getC_usuarioreg() {
        return c_usuarioreg;
    }

    public void setC_usuarioreg(String c_usuarioreg) {
        this.c_usuarioreg = c_usuarioreg;
    }

    public String getD_fechareg() {
        return d_fechareg;
    }

    public void setD_fechareg(String d_fechareg) {
        this.d_fechareg = d_fechareg;
    }

    public String getC_estado() {
        return c_estado;
    }

    public void setC_estado(String c_estado) {
        this.c_estado = c_estado;
    }

    public String getC_usuariorev() {
        return c_usuariorev;
    }

    public void setC_usuariorev(String c_usuariorev) {
        this.c_usuariorev = c_usuariorev;
    }

    public String getD_fecharev() {
        return d_fecharev;
    }

    public void setD_fecharev(String d_fecharev) {
        this.d_fecharev = d_fecharev;
    }

    public String getC_observacionrev() {
        return c_observacionrev;
    }

    public void setC_observacionrev(String c_observacionrev) {
        this.c_observacionrev = c_observacionrev;
    }

    public String getC_accionrev() {
        return c_accionrev;
    }

    public void setC_accionrev(String c_accionrev) {
        this.c_accionrev = c_accionrev;
    }

    public String getC_flagfinrev() {
        return c_flagfinrev;
    }

    public void setC_flagfinrev(String c_flagfinrev) {
        this.c_flagfinrev = c_flagfinrev;
    }

    public String getC_usuarioAN() {
        return c_usuarioAN;
    }

    public void setC_usuarioAN(String c_usuarioAN) {
        this.c_usuarioAN = c_usuarioAN;
    }

    public String getD_fechaAN() {
        return d_fechaAN;
    }

    public void setD_fechaAN(String d_fechaAN) {
        this.d_fechaAN = d_fechaAN;
    }

    public String getC_observacionAN() {
        return c_observacionAN;
    }

    public void setC_observacionAN(String c_observacionAN) {
        this.c_observacionAN = c_observacionAN;
    }

    public String getC_usuarioCE() {
        return c_usuarioCE;
    }

    public void setC_usuarioCE(String c_usuarioCE) {
        this.c_usuarioCE = c_usuarioCE;
    }

    public String getD_fechaCE() {
        return d_fechaCE;
    }

    public void setD_fechaCE(String d_fechaCE) {
        this.d_fechaCE = d_fechaCE;
    }

    public String getC_observacionCE() {
        return c_observacionCE;
    }

    public void setC_observacionCE(String c_observacionCE) {
        this.c_observacionCE = c_observacionCE;
    }

    public String getC_enviado() {
        return c_enviado;
    }

    public void setC_enviado(String c_enviado) {
        this.c_enviado = c_enviado;
    }

    public String getC_observacion() {
        return c_observacion;
    }

    public void setC_observacion(String c_observacion) {
        this.c_observacion = c_observacion;
    }
}
