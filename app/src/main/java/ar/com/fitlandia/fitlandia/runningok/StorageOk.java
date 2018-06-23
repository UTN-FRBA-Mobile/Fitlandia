package ar.com.fitlandia.fitlandia.runningok;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.fitlandia.fitlandia.models.VueltaEnLaPlazaModel;
import io.paperdb.Book;
import io.paperdb.Paper;

public class StorageOk {

    public static void removeHoraInicio(){
        getBookRunning().delete("hora_inicio");

    }
    public static void setHoraInicio(){
        getBookRunning().write("hora_inicio", new Date());
    }

    private static Book getBookRunning(){
        return Paper.book("running");
    }

    public static Date getHoraInicio(){
        return getBookRunning().read("hora_inicio", null);
    }



    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    public static void removePosiciones(){
        getBookRunningTrack().destroy();
    }

    public static Book getBookRunningTrack(){
        return Paper.book("running-track");
    }

    /*public  static void setNuevaPosicion(String n, String posicion){
        getBookRunningTrack().write(n, posicion);
    }*/
    public  static void setNuevaPosicionTrack(String n, VueltaEnLaPlazaModel.Tracking posicion){
        getBookRunningTrack().write(n, posicion);
    }

    public  static List<VueltaEnLaPlazaModel.Tracking> getPosicionesTrack(){
        List<VueltaEnLaPlazaModel.Tracking> posiciones = new ArrayList<>();
        VueltaEnLaPlazaModel.Tracking p ;
        for (String key: getBookRunningTrack().getAllKeys()) {
            p = getBookRunningTrack().read(key);
            posiciones.add(p);
        }
        return posiciones;
    }
    /*public  static List<String> getPosiciones(){
        List<String> posiciones = new ArrayList<>();
        String p ;
        for (String key: getBookRunningTrack().getAllKeys()) {
            p = getBookRunningTrack().read(key);
            posiciones.add(p);
        }
        return posiciones;
    }*/
}
