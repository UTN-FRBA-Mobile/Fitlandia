
package ar.com.fitlandia.fitlandia.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsuarioModel {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("peso")
    @Expose
    private Float peso;
    @SerializedName("altura")
    @Expose
    private Float altura;
    @SerializedName("edad")
    @Expose
    private Integer edad;
    @SerializedName("cintura")
    @Expose
    private Float cintura;
    @SerializedName("cuello")
    @Expose
    private Float cuello;
    @SerializedName("cadera")
    @Expose
    private Float cadera;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public  String getUsername() {
        return username;
    }

    public void  setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getAltura() {
        return altura;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Float getCintura() {
        return cintura;
    }

    public void setCintura(Float cintura) {
        this.cintura = cintura;
    }

    public Float getCuello() {
        return cuello;
    }

    public void setCuello(Float cuello) {
        this.cuello = cuello;
    }

    public Float getCadera() {
        return cadera;
    }

    public void setCadera(Float cadera) {
        this.cadera = cadera;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}