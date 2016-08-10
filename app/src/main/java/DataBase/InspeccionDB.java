package DataBase;

/**
 * Created by dvillanueva on 10/08/2016.
 */
public class InspeccionDB {
    private String c_inspeccion;
    private String c_descripcion;
    private String c_tipoinspeccion;
    private String n_porcentajeminimo;
    private String n_porcentajemaximo;
    private String c_familiainspeccion;
    private String c_periodoinspeccion;
    private String c_estado;
    private String c_ultimousuario;
    private String d_ultimafechamodificacion;

    public InspeccionDB() {
    }

    public InspeccionDB(String c_inspeccion, String c_descripcion, String c_tipoinspeccion, String n_porcentajeminimo, String n_porcentajemaximo, String c_familiainspeccion, String c_periodoinspeccion, String c_estado, String c_ultimousuario, String d_ultimafechamodificacion) {
        this.c_inspeccion = c_inspeccion;
        this.c_descripcion = c_descripcion;
        this.c_tipoinspeccion = c_tipoinspeccion;
        this.n_porcentajeminimo = n_porcentajeminimo;
        this.n_porcentajemaximo = n_porcentajemaximo;
        this.c_familiainspeccion = c_familiainspeccion;
        this.c_periodoinspeccion = c_periodoinspeccion;
        this.c_estado = c_estado;
        this.c_ultimousuario = c_ultimousuario;
        this.d_ultimafechamodificacion = d_ultimafechamodificacion;
    }

    public String getC_inspeccion() {
        return c_inspeccion;
    }

    public void setC_inspeccion(String c_inspeccion) {
        this.c_inspeccion = c_inspeccion;
    }

    public String getC_descripcion() {
        return c_descripcion;
    }

    public void setC_descripcion(String c_descripcion) {
        this.c_descripcion = c_descripcion;
    }

    public String getC_tipoinspeccion() {
        return c_tipoinspeccion;
    }

    public void setC_tipoinspeccion(String c_tipoinspeccion) {
        this.c_tipoinspeccion = c_tipoinspeccion;
    }

    public String getN_porcentajeminimo() {
        return n_porcentajeminimo;
    }

    public void setN_porcentajeminimo(String n_porcentajeminimo) {
        this.n_porcentajeminimo = n_porcentajeminimo;
    }

    public String getN_porcentajemaximo() {
        return n_porcentajemaximo;
    }

    public void setN_porcentajemaximo(String n_porcentajemaximo) {
        this.n_porcentajemaximo = n_porcentajemaximo;
    }

    public String getC_familiainspeccion() {
        return c_familiainspeccion;
    }

    public void setC_familiainspeccion(String c_familiainspeccion) {
        this.c_familiainspeccion = c_familiainspeccion;
    }

    public String getC_periodoinspeccion() {
        return c_periodoinspeccion;
    }

    public void setC_periodoinspeccion(String c_periodoinspeccion) {
        this.c_periodoinspeccion = c_periodoinspeccion;
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
