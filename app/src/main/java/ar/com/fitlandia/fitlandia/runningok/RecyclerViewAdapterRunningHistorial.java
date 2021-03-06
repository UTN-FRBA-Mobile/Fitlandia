package ar.com.fitlandia.fitlandia.runningok;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.github.marlonlom.utilities.timeago.TimeAgoMessages;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ar.com.fitlandia.fitlandia.R;
import ar.com.fitlandia.fitlandia.models.VueltaEnLaPlazaModel;
import ar.com.fitlandia.fitlandia.utils.Utils;

public class RecyclerViewAdapterRunningHistorial extends RecyclerView.Adapter<RecyclerViewAdapterRunningHistorial.ViewHolder>
{

    private List<VueltaEnLaPlazaModel> _caratulas;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Locale LocaleBylanguageTag;
    TimeAgoMessages messages;
    // data is passed into the constructor
    private Context context;
    RecyclerViewAdapterRunningHistorial(Context context, List<VueltaEnLaPlazaModel>  caratulas) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this._caratulas = caratulas;
        LocaleBylanguageTag = Locale.forLanguageTag("es");

        messages = new TimeAgoMessages.Builder().withLocale(LocaleBylanguageTag).build();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_row_runninghistorial, parent, false);
        return new ViewHolder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        VueltaEnLaPlazaModel entrenamientoModel = _caratulas.get(position);

        if(entrenamientoModel.getDistanciaEnMetros()!=0){
            /*double distanceInKm = entrenamientoModel.getDistanciaEnMetros()/1000D;
            int tiempoEnMinutos = (int)entrenamientoModel.getTiempoEnSegundos()/60;
            String desc;
            desc = String.format("%.2f", distanceInKm)  + "Km en " + String.valueOf(tiempoEnMinutos) + " minutos. ";*/
            holder.tv_descripcion.setText( entrenamientoModel.distanciaEnPalabras());
        }else{
            holder.tv_descripcion.setText( "123Km en 11 minutos");
        }

        //holder.tv_descripcion.setText(this.latLngToAddresName());

        Date fecha = Utils.fromStringToDate(entrenamientoModel.getFecha());

        //TODO: PARQUE PARA BORRAR EL GMT-3
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(fecha); // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, -3); // adds one hour
        fecha = cal.getTime(); // returns new date object, one hour in the future

        String tiempo = TimeAgo.using(fecha.getTime(), messages);
        holder.tv_titulo.setText(tiempo);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return _caratulas.size();
    }

    private String latLngToAddresName(double latitude, double longitude){
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this.context, Locale.forLanguageTag("es"));

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
        return  city;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_titulo, tv_descripcion;
        ImageView iv_run;

        ViewHolder(View itemView) {
            super(itemView);
            tv_titulo = itemView.findViewById(R.id.tv_titulo);
            tv_descripcion = itemView.findViewById(R.id.tv_descripcion);
            iv_run = itemView.findViewById(R.id.iv_run);
            //Picasso.get().load("http://i.imgur.com/DvpvklR.png").resize(100, 100).into(iv_sitio);
            Picasso.get().load(R.drawable.ic_mapa_waypoint_96).resize(100, 100).into(iv_run);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // convenience method for getting data at click position
    VueltaEnLaPlazaModel getItem(int id) {
        return _caratulas.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}
