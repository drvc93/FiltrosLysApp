package DataBase;

/**
 * Created by dvillanueva on 08/08/2016.
 */
public class MaquinaDB {

    private String  c_ompania;
    private String  c_maquina;
    private String  c_descripcion;
    private String  c_codigobarras;
    private String  c_familiainspeccion;
    private String  c_centrocosto;
    private String  c_estado;
    private String  c_ultimousuario;
    private String  d_ultimafechamodificacion;

    public MaquinaDB() {
    }

    public MaquinaDB(String c_ompania, String c_maquina, String c_descripcion, String c_codigobarras, String c_familiainspeccion, String c_centrocosto, String c_estado, String c_ultimousuario, String d_ultimafechamodificacion) {
        this.c_ompania = c_ompania;
        this.c_maquina = c_maquina;
        this.c_descripcion = c_descripcion;
        this.c_codigobarras = c_codigobarras;
        this.c_familiainspeccion = c_familiainspeccion;
        this.c_centrocosto = c_centrocosto;
        this.c_estado = c_estado;
        this.c_ultimousuario = c_ultimousuario;
        this.d_ultimafechamodificacion = d_ultimafechamodificacion;
    }

    public String getC_ompania() {
        return c_ompania;
    }

    public void setC_ompania(String c_ompania) {
        this.c_ompania = c_ompania;
    }

    public String getC_maquina() {
        return c_maquina;
    }

    public void setC_maquina(String c_maquina) {
        this.c_maquina = c_maquina;
    }

    public String getC_descripcion() {
        return c_descripcion;
    }

    public void setC_descripcion(String c_descripcion) {
        this.c_descripcion = c_descripcion;
    }

    public String getC_codigobarras() {
        return c_codigobarras;
    }

    public void setC_codigobarras(String c_codigobarras) {
        this.c_codigobarras = c_codigobarras;
    }

    public String getC_familiainspeccion() {
        return c_familiainspeccion;
    }

    public void setC_familiainspeccion(String c_familiainspeccion) {
        this.c_familiainspeccion = c_familiainspeccion;
    }

    public String getC_centrocosto() {
        return c_centrocosto;
    }

    public void setC_centrocosto(String c_centrocosto) {
        this.c_centrocosto = c_centrocosto;
    }

    public String getC_estado() {
        return c_estado;
    }

    public void setC_estado(String c_estado) {
        this.c_estado = c_estado;
    }

    public String getC_ultimousuario() {
        return c_ultimousuario;
    }

    public void setC_ultimousuario(String c_ultimousuario) {
        this.c_ultimousuario = c_ultimousuario;
    }

    public String getD_ultimafechamodificacion() {
        return d_ultimafechamodificacion;
    }

    public void setD_ultimafechamodificacion(String d_ultimafechamodificacion) {
        this.d_ultimafechamodificacion = d_ultimafechamodificacion;
    }



}
