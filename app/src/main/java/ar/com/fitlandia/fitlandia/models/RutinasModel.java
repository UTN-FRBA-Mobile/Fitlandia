package ar.com.fitlandia.fitlandia.models;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        List<String>rutinasNombres=new ArrayList<>();
        rutinasNombres.add("GAP_Gluteos abdominales y piernas");
        rutinasNombres.add("Brazos_Ejercicios de fuerza de brazos");
        rutinasNombres.add("Piernas_Ejercicios con fuerza de piernas");

        List<String>videos=new ArrayList<>();
        videos.add("LlkUKOXCYbA_Jumping Jacks_Jumping Jacks es un ejercicio aerobico");
        videos.add("R7JlVgWMsk8_Sentadillas con peso_Sentadillas con peso corporal");
        videos.add("-ce8eAkKcSg_Flexiones_Flexiones de brazos");
        videos.add("fFXFlq3xa8c_Hip thrust_Empuje de cadera");
        videos.add("VcxRJ3wVs1o_Espinales_Espinales con manos en la nuca");
        videos.add("AWWIsmZy4Hw_Flexiones con apoyo_Flexiones de brazos con apoyo en las rodillas");
        videos.add("qJadDKagAmg_Remo Renegado_Remo renegado");
        videos.add("02ScMntXbzU_Plancha_Plancha");

        for (int i = 0; i<rutinasNombres.size()  ; i++) {
            rutinaModel = new RutinaModel();
            String[]  rutina = rutinasNombres.get(i).split("_");
            rutinaModel.setTitulo(rutina[0] );
            rutinaModel.setDescripcion( rutina[1] );

            List<EjercicioModel> ejercicios = new ArrayList<>();


            for(int j=0;j<videos.size();j++) {
                EjercicioModel ejercicioModel = new EjercicioModel();

                String[]  video = videos.get(j).split("_");
                ejercicioModel.setCantidad(10);
                ejercicioModel.setDuracionEnSegundos(10);
                ejercicioModel.setVideoId(video[0]);
                ejercicioModel.setTitulo(video[1]);
                ejercicioModel.setDescripcion(video[2]);
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
