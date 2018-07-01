package ar.com.fitlandia.fitlandia.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogRutinaModel {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("texto")
    @Expose
    private String texto;
    @SerializedName("usuario")
    @Expose
    private String usuario;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
