package ar.com.fitlandia.fitlandia.models;

import java.util.List;

public class RutinaModel extends ListItemModel {

    private int id;

    private List<EjercicioModel> ejercicios;


    public List<EjercicioModel> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<EjercicioModel> ejercicios) {
        this.ejercicios = ejercicios;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
