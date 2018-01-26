package Model;

/**
 * Creado por dvillanueva el  25/01/2018 (FiltrosLysApp).
 */

public class ReclamoGarantia {
    private String c_compania;
    private long n_correlativo;
    private String d_fecha;
    private int n_cliente;
    private String c_codproducto;
    private String c_lote;
    private String c_procedencia;
    private double n_qtyingreso;
    private String c_vendedor;
    private String c_estado;
    private String c_usuario;
    private String c_lote1;
    private String c_lote2;
    private String c_lote3;
    private double n_cantlote1;
    private double n_cantlote2;
    private double n_cantlote3;
    private String c_codmarca;
    private String c_codmodelo;
    private int n_pyear;
    private String c_tiempouso;
    private String c_facturaRef;
    private String c_prediagnostico;
    private int n_formato;
    private String d_fechaformato;
    private String c_obscliente;
    private String c_flagvisita;
    private String c_tiporeclamo;
    private String c_reembcliente;
    private String c_placavehic;
    private double n_montoreembcli;
    private String c_monedareembcli;
    private String c_pruebalab1;
    private String c_pruebalab2;
    private String c_pruebalab3;
    private String c_ensayo01;
    private String c_ensayo02;
    private String c_ensayo03;
    private String c_ensayo04;
    private String c_ensayo05;

    public ReclamoGarantia() {
    }

    public ReclamoGarantia(String c_compania, long n_correlativo, String d_fecha, int n_cliente, String c_codproducto, String c_lote, String c_procedencia, double n_qtyingreso, String c_vendedor, String c_estado, String c_usuario, String c_lote1, String c_lote2, String c_lote3, double n_cantlote1, double n_cantlote2, double n_cantlote3, String c_codmarca, String c_codmodelo, int n_pyear, String c_tiempouso, String c_facturaRef, String c_prediagnostico, int n_formato, String d_fechaformato, String c_obscliente, String c_flagvisita, String c_tiporeclamo, String c_reembcliente, String c_placavehic, double n_montoreembcli, String c_monedareembcli, String c_pruebalab1, String c_pruebalab2, String c_pruebalab3, String c_ensayo01, String c_ensayo02, String c_ensayo03, String c_ensayo04, String c_ensayo05) {
        this.c_compania = c_compania;
        this.n_correlativo = n_correlativo;
        this.d_fecha = d_fecha;
        this.n_cliente = n_cliente;
        this.c_codproducto = c_codproducto;
        this.c_lote = c_lote;
        this.c_procedencia = c_procedencia;
        this.n_qtyingreso = n_qtyingreso;
        this.c_vendedor = c_vendedor;
        this.c_estado = c_estado;
        this.c_usuario = c_usuario;
        this.c_lote1 = c_lote1;
        this.c_lote2 = c_lote2;
        this.c_lote3 = c_lote3;
        this.n_cantlote1 = n_cantlote1;
        this.n_cantlote2 = n_cantlote2;
        this.n_cantlote3 = n_cantlote3;
        this.c_codmarca = c_codmarca;
        this.c_codmodelo = c_codmodelo;
        this.n_pyear = n_pyear;
        this.c_tiempouso = c_tiempouso;
        this.c_facturaRef = c_facturaRef;
        this.c_prediagnostico = c_prediagnostico;
        this.n_formato = n_formato;
        this.d_fechaformato = d_fechaformato;
        this.c_obscliente = c_obscliente;
        this.c_flagvisita = c_flagvisita;
        this.c_tiporeclamo = c_tiporeclamo;
        this.c_reembcliente = c_reembcliente;
        this.c_placavehic = c_placavehic;
        this.n_montoreembcli = n_montoreembcli;
        this.c_monedareembcli = c_monedareembcli;
        this.c_pruebalab1 = c_pruebalab1;
        this.c_pruebalab2 = c_pruebalab2;
        this.c_pruebalab3 = c_pruebalab3;
        this.c_ensayo01 = c_ensayo01;
        this.c_ensayo02 = c_ensayo02;
        this.c_ensayo03 = c_ensayo03;
        this.c_ensayo04 = c_ensayo04;
        this.c_ensayo05 = c_ensayo05;
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

    public String getD_fecha() {
        return d_fecha;
    }

    public void setD_fecha(String d_fecha) {
        this.d_fecha = d_fecha;
    }

    public int getN_cliente() {
        return n_cliente;
    }

    public void setN_cliente(int n_cliente) {
        this.n_cliente = n_cliente;
    }

    public String getC_codproducto() {
        return c_codproducto;
    }

    public void setC_codproducto(String c_codproducto) {
        this.c_codproducto = c_codproducto;
    }

    public String getC_lote() {
        return c_lote;
    }

    public void setC_lote(String c_lote) {
        this.c_lote = c_lote;
    }

    public String getC_procedencia() {
        return c_procedencia;
    }

    public void setC_procedencia(String c_procedencia) {
        this.c_procedencia = c_procedencia;
    }

    public double getN_qtyingreso() {
        return n_qtyingreso;
    }

    public void setN_qtyingreso(double n_qtyingreso) {
        this.n_qtyingreso = n_qtyingreso;
    }

    public String getC_vendedor() {
        return c_vendedor;
    }

    public void setC_vendedor(String c_vendedor) {
        this.c_vendedor = c_vendedor;
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

    public String getC_lote1() {
        return c_lote1;
    }

    public void setC_lote1(String c_lote1) {
        this.c_lote1 = c_lote1;
    }

    public String getC_lote2() {
        return c_lote2;
    }

    public void setC_lote2(String c_lote2) {
        this.c_lote2 = c_lote2;
    }

    public String getC_lote3() {
        return c_lote3;
    }

    public void setC_lote3(String c_lote3) {
        this.c_lote3 = c_lote3;
    }

    public double getN_cantlote1() {
        return n_cantlote1;
    }

    public void setN_cantlote1(double n_cantlote1) {
        this.n_cantlote1 = n_cantlote1;
    }

    public double getN_cantlote2() {
        return n_cantlote2;
    }

    public void setN_cantlote2(double n_cantlote2) {
        this.n_cantlote2 = n_cantlote2;
    }

    public double getN_cantlote3() {
        return n_cantlote3;
    }

    public void setN_cantlote3(double n_cantlote3) {
        this.n_cantlote3 = n_cantlote3;
    }

    public String getC_codmarca() {
        return c_codmarca;
    }

    public void setC_codmarca(String c_codmarca) {
        this.c_codmarca = c_codmarca;
    }

    public String getC_codmodelo() {
        return c_codmodelo;
    }

    public void setC_codmodelo(String c_codmodelo) {
        this.c_codmodelo = c_codmodelo;
    }

    public int getN_pyear() {
        return n_pyear;
    }

    public void setN_pyear(int n_pyear) {
        this.n_pyear = n_pyear;
    }

    public String getC_tiempouso() {
        return c_tiempouso;
    }

    public void setC_tiempouso(String c_tiempouso) {
        this.c_tiempouso = c_tiempouso;
    }

    public String getC_facturaRef() {
        return c_facturaRef;
    }

    public void setC_facturaRef(String c_facturaRef) {
        this.c_facturaRef = c_facturaRef;
    }

    public String getC_prediagnostico() {
        return c_prediagnostico;
    }

    public void setC_prediagnostico(String c_prediagnostico) {
        this.c_prediagnostico = c_prediagnostico;
    }

    public int getN_formato() {
        return n_formato;
    }

    public void setN_formato(int n_formato) {
        this.n_formato = n_formato;
    }

    public String getD_fechaformato() {
        return d_fechaformato;
    }

    public void setD_fechaformato(String d_fechaformato) {
        this.d_fechaformato = d_fechaformato;
    }

    public String getC_obscliente() {
        return c_obscliente;
    }

    public void setC_obscliente(String c_obscliente) {
        this.c_obscliente = c_obscliente;
    }

    public String getC_flagvisita() {
        return c_flagvisita;
    }

    public void setC_flagvisita(String c_flagvisita) {
        this.c_flagvisita = c_flagvisita;
    }

    public String getC_tiporeclamo() {
        return c_tiporeclamo;
    }

    public void setC_tiporeclamo(String c_tiporeclamo) {
        this.c_tiporeclamo = c_tiporeclamo;
    }

    public String getC_reembcliente() {
        return c_reembcliente;
    }

    public void setC_reembcliente(String c_reembcliente) {
        this.c_reembcliente = c_reembcliente;
    }

    public String getC_placavehic() {
        return c_placavehic;
    }

    public void setC_placavehic(String c_placavehic) {
        this.c_placavehic = c_placavehic;
    }

    public double getN_montoreembcli() {
        return n_montoreembcli;
    }

    public void setN_montoreembcli(double n_montoreembcli) {
        this.n_montoreembcli = n_montoreembcli;
    }

    public String getC_monedareembcli() {
        return c_monedareembcli;
    }

    public void setC_monedareembcli(String c_monedareembcli) {
        this.c_monedareembcli = c_monedareembcli;
    }

    public String getC_pruebalab1() {
        return c_pruebalab1;
    }

    public void setC_pruebalab1(String c_pruebalab1) {
        this.c_pruebalab1 = c_pruebalab1;
    }

    public String getC_pruebalab2() {
        return c_pruebalab2;
    }

    public void setC_pruebalab2(String c_pruebalab2) {
        this.c_pruebalab2 = c_pruebalab2;
    }

    public String getC_pruebalab3() {
        return c_pruebalab3;
    }

    public void setC_pruebalab3(String c_pruebalab3) {
        this.c_pruebalab3 = c_pruebalab3;
    }

    public String getC_ensayo01() {
        return c_ensayo01;
    }

    public void setC_ensayo01(String c_ensayo01) {
        this.c_ensayo01 = c_ensayo01;
    }

    public String getC_ensayo02() {
        return c_ensayo02;
    }

    public void setC_ensayo02(String c_ensayo02) {
        this.c_ensayo02 = c_ensayo02;
    }

    public String getC_ensayo03() {
        return c_ensayo03;
    }

    public void setC_ensayo03(String c_ensayo03) {
        this.c_ensayo03 = c_ensayo03;
    }

    public String getC_ensayo04() {
        return c_ensayo04;
    }

    public void setC_ensayo04(String c_ensayo04) {
        this.c_ensayo04 = c_ensayo04;
    }

    public String getC_ensayo05() {
        return c_ensayo05;
    }

    public void setC_ensayo05(String c_ensayo05) {
        this.c_ensayo05 = c_ensayo05;
    }
}
