
package ar.com.fitlandia.fitlandia.models;

        import android.text.format.DateFormat;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;
        import java.util.Locale;
        import java.util.TimeZone;

        import com.google.android.gms.maps.model.LatLng;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

        import ar.com.fitlandia.fitlandia.utils.DistanceCalculator;

public class VueltaEnLaPlazaModel {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("tracking")
    @Expose
    private List<Tracking> tracking = new ArrayList<Tracking>();
    @SerializedName("distanciaEnMetros")
    @Expose
    private double distanciaEnMetros;
    @SerializedName("tiempoEnSegundos")
    @Expose
    private double tiempoEnSegundos;
    @SerializedName("inicio")
    @Expose
    private long inicio;
    @SerializedName("fin")
    @Expose
    private long fin;
    @SerializedName("velocidadEnMetrosSobreSegundos")
    @Expose
    private double velocidadEnMetrosSobreSegundos;
    @SerializedName("entrenamiento")
    @Expose
    private String entrenamiento;
    @SerializedName("__v")
    @Expose
    private Integer v;


    @SerializedName("usuario")
    @Expose
    private String usuario;
    @SerializedName("fecha")
    @Expose
    private String fecha;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Tracking> getTracking() {
        return tracking;
    }

    public void setTracking(List<Tracking> tracking) {
        this.tracking = tracking;
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

    public long getInicio() {
        return inicio;
    }

    public void setInicio(long inicio) {
        this.inicio = inicio;
    }

    public long getFin() {
        return fin;
    }

    public void setFin(long fin) {
        this.fin = fin;
    }

    public double getVelocidadEnMetrosSobreSegundos() {
        return velocidadEnMetrosSobreSegundos;
    }

    public void setVelocidadEnMetrosSobreSegundos(double velocidadEnMetrosSobreSegundos) {
        this.velocidadEnMetrosSobreSegundos = velocidadEnMetrosSobreSegundos;
    }

    public String getEntrenamiento() {
        return entrenamiento;
    }

    public void setEntrenamiento(String entrenamiento) {
        this.entrenamiento = entrenamiento;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
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


    public double calcularDistanciaEnKm() {
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        double distanciaEnMetros = 0;
        Tracking t1, t2 ;
        double partial;
        for (int i = 0; i < this.getTracking().size()-1; i++) {
            t1 = tracking.get(i);
            t2 = tracking.get(i+1);

            partial = distanceCalculator.greatCircleInMeters(t1.toLatLng(), t2.toLatLng());
            if(partial>0.001)
                distanciaEnMetros =distanciaEnMetros + partial;
        }


        return  distanciaEnMetros;
    }

    public String distanciaEnPalabras(){
        double distanceInKm = this.getDistanciaEnMetros()/1000D;
        int tiempoEnMinutos = (int)this.getTiempoEnSegundos()/60;
        String desc;
        return String.format("%.2f", distanceInKm)  + "Km en " + String.valueOf(tiempoEnMinutos) + " minutos. ";
    }


    ///TODO: ES NECESARIO QUE YA ESTA CALCULADA LA DISTANCIA
    public double calcularVelocidad() {
        //long duracion = this.fin - this.inicio;
        //Date dtDuracion = getDate(duracion);
        //long diffInMillisec = duracion;
        /*
        long diffInMillisec = (long)this.getTiempoEnSegundos();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;

        long different = diffInMillisec;

        long elapsedSeconds = different / secondsInMilli;*/

        double vel ;
        vel = this.distanciaEnMetros / (long)this.tiempoEnSegundos;
        return  vel;

    }




    public static class Tracking {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("lat")
        @Expose
        private Float lat;
        @SerializedName("lng")
        @Expose
        private Float lng;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Float getLat() {
            return lat;
        }

        public void setLat(Float lat) {
            this.lat = lat;
        }

        public Float getLng() {
            return lng;
        }

        public void setLng(Float lng) {
            this.lng = lng;
        }

        public LatLng toLatLng(){
            return  new LatLng(this.lat, this.lng);
        }

    }

}