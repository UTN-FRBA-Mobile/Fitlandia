package ar.com.fitlandia.fitlandia.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EntrenamientoModel {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("distanciaEnMetros")
    @Expose
    private double distanciaEnMetros;
    @SerializedName("tiempoEnSegundos")
    @Expose
    private double tiempoEnSegundos;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getDistanciaEnMetros() {
        return distanciaEnMetros;
    }

    public void setDistanciaEnMetros(double distanciaEnMetros) {
        this.distanciaEnMetros = distanciaEnMetros;
    }

    public double getTiempoEnSegundos() {
        return tiempoEnSegundos;
    }

    public void setTiempoEnSegundos(double tiempoEnSegundos) {
        this.tiempoEnSegundos = tiempoEnSegundos;
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