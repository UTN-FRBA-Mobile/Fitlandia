package ar.com.fitlandia.fitlandia.runningok;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ar.com.fitlandia.fitlandia.R;
import ar.com.fitlandia.fitlandia.models.EntrenamientoModel;

public class RecyclerViewAdapterRunningHistorial extends RecyclerView.Adapter<RecyclerViewAdapterRunningHistorial.ViewHolder>
{

    private List<EntrenamientoModel> _caratulas;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    RecyclerViewAdapterRunningHistorial(Context context, List<EntrenamientoModel>  caratulas) {
        this.mInflater = LayoutInflater.from(context);
        this._caratulas = caratulas;

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
        EntrenamientoModel caratulaViewModel = _caratulas.get(position);
        holder.tv_nombre.setText(caratulaViewModel.getTipo());
        holder.tv_sitio.setText(caratulaViewModel.getFecha());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return _caratulas.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_nombre, tv_sitio;
        ImageView iv_sitio;

        ViewHolder(View itemView) {
            super(itemView);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            tv_sitio = itemView.findViewById(R.id.tv_sitio);
            iv_sitio = itemView.findViewById(R.id.iv_sitio);
            Picasso.get().load("http://i.imgur.com/DvpvklR.png").resize(100, 100).into(iv_sitio);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    EntrenamientoModel getItem(int id) {
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
