package ar.com.fitlandia.fitlandia.models;

import java.util.List;

public class RutinaModel {

    private int id;

    private List<EjercicioModel> ejercicios;


    public List<EjercicioModel> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<EjercicioModel> ejercicios) {
        this.ejercicios = ejercicios;
    }





    private String titulo;
    private String descripcion;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
