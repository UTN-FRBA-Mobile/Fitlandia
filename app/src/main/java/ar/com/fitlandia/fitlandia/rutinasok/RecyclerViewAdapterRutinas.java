package ar.com.fitlandia.fitlandia.rutinasok;

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
import ar.com.fitlandia.fitlandia.models.RutinaModel;
import ar.com.fitlandia.fitlandia.models.RutinasModel;
import ar.com.fitlandia.fitlandia.models.VueltaEnLaPlazaModel;
import ar.com.fitlandia.fitlandia.utils.Utils;

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

public class RecyclerViewAdapterRutinas extends RecyclerView.Adapter<RecyclerViewAdapterRutinas.ViewHolder>
{

    private List<RutinaModel> _rutinas;
    private LayoutInflater mInflater;
    private RecyclerViewAdapterRutinas.ItemClickListener mClickListener;


    // data is passed into the constructor
    private Context context;
    RecyclerViewAdapterRutinas(Context context, List<RutinaModel> rutinas) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this._rutinas = rutinas;
    }

    // inflates the row layout from xml when needed
    @Override
    public RecyclerViewAdapterRutinas.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_row_runninghistorial, parent, false);
        return new RecyclerViewAdapterRutinas.ViewHolder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(RecyclerViewAdapterRutinas.ViewHolder holder, int position) {
        RutinaModel rutinaModel = _rutinas.get(position);
        holder.tv_titulo.setText(rutinaModel.getTitulo());
        holder.tv_descripcion.setText(rutinaModel.getDescripcion());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return _rutinas.size();
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
            Picasso.get().load(R.drawable.ic_lista_de_quehaceres_64).resize(100, 100).into(iv_run);



            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // convenience method for getting data at click position
    RutinaModel getItem(int id) {
        return _rutinas.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(RecyclerViewAdapterRutinas.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}
