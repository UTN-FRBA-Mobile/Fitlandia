package ar.com.fitlandia.fitlandia.rutinasok;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import ar.com.fitlandia.fitlandia.R;
import ar.com.fitlandia.fitlandia.Rutinas;
import ar.com.fitlandia.fitlandia.models.EjercicioModel;
import ar.com.fitlandia.fitlandia.models.RutinaModel;
import ar.com.fitlandia.fitlandia.models.RutinasModel;
import ar.com.fitlandia.fitlandia.utils.APIService;
import ar.com.fitlandia.fitlandia.utils.ApiUtils;
import ar.com.fitlandia.fitlandia.utils.ApplicationGlobal;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EjerciciosDeRutinaActivity extends AppCompatActivity {

    @BindView(R.id.activity_ejercicios)
    LinearLayout activity_ejercicios;
    @BindView(R.id.rv_ejercicios)
    RecyclerView recyclerView;

    private List<EjercicioModel> _ejerciciosModel;

    private APIService api;
    RecyclerViewAdapterEjercicios adapter;
    ApplicationGlobal applicationGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios_de_rutina);

        ButterKnife.bind(this);
        api = ApiUtils.getAPIService();

        //_ejercicioModel = RutinasModel.newRutinasDisponibles();


        applicationGlobal = ApplicationGlobal.getInstance();
        _ejerciciosModel = applicationGlobal.getRutinaSelected().getEjercicios();

        cargarRecyclerView();
    }


    private void cargarRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapterEjercicios(this, _ejerciciosModel);
        final Activity _this = this;
        adapter.setClickListener(new RecyclerViewAdapterEjercicios.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                EjercicioModel ejercicioModel = adapter.getItem(position);
                Intent myIntent = new Intent(_this, Rutinas.class);
                //myIntent.putExtra("rutina_id", rutinaModel.getId()); //Optional parameters
                applicationGlobal.setEjercicioSelected(ejercicioModel);
                _this.startActivity(myIntent);



                //Toast.makeText(_this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

}
