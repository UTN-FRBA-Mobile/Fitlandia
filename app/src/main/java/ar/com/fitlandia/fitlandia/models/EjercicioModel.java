package ar.com.fitlandia.fitlandia.models;

public class EjercicioModel extends ListItemModel {

    private int id;
    private String videoId;
    private String nombre;
    private int duracionEnSegundos;
    private int cantidad;
    private String tipo;


    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDuracionEnSegundos() {
        return duracionEnSegundos;
    }

    public void setDuracionEnSegundos(int duracion) {
        this.duracionEnSegundos = duracion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
