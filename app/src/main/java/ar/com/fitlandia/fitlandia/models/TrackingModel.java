
package ar.com.fitlandia.fitlandia.models;

        import java.util.ArrayList;
        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class TrackingModel {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("tracking")
    @Expose
    private List<Tracking> tracking = new ArrayList<Tracking>();
    @SerializedName("entrenamiento")
    @Expose
    private String entrenamiento;
    @SerializedName("__v")
    @Expose
    private Integer v;

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





    public class Tracking {

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

    }

}