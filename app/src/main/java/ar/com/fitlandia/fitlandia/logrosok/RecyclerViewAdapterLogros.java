package ar.com.fitlandia.fitlandia.logrosok;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.github.marlonlom.utilities.timeago.TimeAgoMessages;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ar.com.fitlandia.fitlandia.R;
import ar.com.fitlandia.fitlandia.models.EjercicioModel;
import ar.com.fitlandia.fitlandia.models.LogroModel;
import ar.com.fitlandia.fitlandia.utils.Utils;

public class RecyclerViewAdapterLogros extends RecyclerView.Adapter<RecyclerViewAdapterLogros.ViewHolder>
{
    Locale LocaleBylanguageTag;
    TimeAgoMessages messages;
    private List<LogroModel> logroModels;
    private LayoutInflater mInflater;
    private RecyclerViewAdapterLogros.ItemClickListener mClickListener;


    // data is passed into the constructor
    private Context context;
    RecyclerViewAdapterLogros(Context context, List<LogroModel> ejercicios) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.logroModels = ejercicios;
        LocaleBylanguageTag = Locale.forLanguageTag("es");

        messages = new TimeAgoMessages.Builder().withLocale(LocaleBylanguageTag).build();
    }

    // inflates the row layout from xml when needed
    @Override
    public RecyclerViewAdapterLogros.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_row_runninghistorial, parent, false);
        return new RecyclerViewAdapterLogros.ViewHolder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(RecyclerViewAdapterLogros.ViewHolder holder, int position) {
        LogroModel logroModel = logroModels.get(position);
        //holder.tv_titulo.setText(logroModel.getTitulo());
        holder.tv_descripcion.setText(logroModel.getComentario());





        Date fecha = Utils.fromStringToDate(logroModel.getFecha());

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
        return logroModels.size();
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
            Picasso.get().load(R.drawable.ic_selfie_48).resize(100, 100).into(iv_run);



            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // convenience method for getting data at click position
    LogroModel getItem(int id) {
        return logroModels.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(RecyclerViewAdapterLogros.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}
