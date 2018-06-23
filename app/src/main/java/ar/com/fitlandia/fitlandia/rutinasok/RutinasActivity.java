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

import ar.com.fitlandia.fitlandia.R;
import ar.com.fitlandia.fitlandia.models.RutinaModel;
import ar.com.fitlandia.fitlandia.models.RutinasModel;
import ar.com.fitlandia.fitlandia.models.VueltaEnLaPlazaModel;
import ar.com.fitlandia.fitlandia.runningok.RecyclerViewAdapterRunningHistorial;
import ar.com.fitlandia.fitlandia.runningok.RunningMapActivity;
import ar.com.fitlandia.fitlandia.utils.APIService;
import ar.com.fitlandia.fitlandia.utils.ApiUtils;
import ar.com.fitlandia.fitlandia.utils.ApplicationGlobal;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RutinasActivity extends AppCompatActivity {

    @BindView(R.id.activity_rutinas)
    LinearLayout activity_rutinas;
    @BindView(R.id.rv_rutinas)
    RecyclerView recyclerView;

    private RutinasModel _rutinasModel;

    private APIService api;
    RecyclerViewAdapterRutinas adapter;
    ApplicationGlobal applicationGlobal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas2);

        ButterKnife.bind(this);

        final Activity activity = this;
        activity.setTitle("Rutinas");

        api = ApiUtils.getAPIService();

        _rutinasModel = RutinasModel.newRutinasDisponibles();

        applicationGlobal = ApplicationGlobal.getInstance();


        cargarRecyclerView();


    }

    private void cargarRecyclerView(){


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapterRutinas(this, _rutinasModel.getRutinas());
        final Activity _this = this;
        adapter.setClickListener(new RecyclerViewAdapterRutinas.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                RutinaModel rutinaModel = adapter.getItem(position);
                Intent myIntent = new Intent(_this, EjerciciosDeRutinaActivity.class);
                //myIntent.putExtra("rutina_id", rutinaModel.getId()); //Optional parameters
                applicationGlobal.setRutinaSelected(rutinaModel);
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
