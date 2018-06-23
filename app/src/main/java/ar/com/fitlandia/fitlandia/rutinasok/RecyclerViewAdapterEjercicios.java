package ar.com.fitlandia.fitlandia.rutinasok;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ar.com.fitlandia.fitlandia.R;
import ar.com.fitlandia.fitlandia.models.EjercicioModel;


public class RecyclerViewAdapterEjercicios extends RecyclerView.Adapter<RecyclerViewAdapterEjercicios.ViewHolder>
{

    private List<EjercicioModel> _ejercicios;
    private LayoutInflater mInflater;
    private RecyclerViewAdapterEjercicios.ItemClickListener mClickListener;


    // data is passed into the constructor
    private Context context;
    RecyclerViewAdapterEjercicios(Context context, List<EjercicioModel> ejercicios) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this._ejercicios = ejercicios;
    }

    // inflates the row layout from xml when needed
    @Override
    public RecyclerViewAdapterEjercicios.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_row_runninghistorial, parent, false);
        return new RecyclerViewAdapterEjercicios.ViewHolder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(RecyclerViewAdapterEjercicios.ViewHolder holder, int position) {
        EjercicioModel ejercicioModel = _ejercicios.get(position);
        holder.tv_titulo.setText(ejercicioModel.getTitulo());
        holder.tv_descripcion.setText(ejercicioModel.getDescripcion());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return _ejercicios.size();
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
            Picasso.get().load(R.drawable.ic_peso_muerto_filled_50).resize(100, 100).into(iv_run);



            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // convenience method for getting data at click position
    EjercicioModel getItem(int id) {
        return _ejercicios.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(RecyclerViewAdapterEjercicios.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}
