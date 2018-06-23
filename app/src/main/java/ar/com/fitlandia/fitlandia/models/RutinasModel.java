package ar.com.fitlandia.fitlandia.models;

import java.util.ArrayList;
import java.util.List;

public class RutinasModel {


    private List<RutinaModel> rutinas;

    public List<RutinaModel> getRutinas() {
        return rutinas;
    }

    public void setRutinas(List<RutinaModel> rutinas) {
        this.rutinas = rutinas;
    }



    public static RutinasModel newRutinasDisponibles(){
        RutinasModel rutinasModel = new RutinasModel();

        List<RutinaModel> rutinas = new ArrayList<>();
        RutinaModel rutinaModel;

        for (int i = 0; i < 5; i++) {
            rutinaModel = new RutinaModel();
            rutinaModel.setTitulo("TituloRutina " + String.valueOf(i));
            rutinaModel.setDescripcion("TItuloDescripcion " + String.valueOf(i));

            List<EjercicioModel> ejercicios = new ArrayList<>();

            for (int j = 0; j < 5; j++) {
                EjercicioModel ejercicioModel = new EjercicioModel();
                ejercicioModel.setCantidad(j*10);
                ejercicioModel.setDuracionEnSegundos(j*10);
                ejercicioModel.setVideoId("R7JlVgWMsk8");
                ejercicioModel.setTitulo( "TituloEjercicio " + j*10);
                ejercicioModel.setDescripcion( "DescripcionEjercicio " + j*10);
                ejercicioModel.setTipo("repe");

                ejercicios.add(ejercicioModel);
            }

            rutinaModel.setEjercicios(ejercicios);

            rutinas.add(rutinaModel);
        }

        rutinasModel.setRutinas(rutinas);

        return rutinasModel;
    }
}
