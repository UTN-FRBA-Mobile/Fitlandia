package ar.com.fitlandia.fitlandia.logrosok;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ar.com.fitlandia.fitlandia.R;
import ar.com.fitlandia.fitlandia.models.EjercicioModel;
import ar.com.fitlandia.fitlandia.models.LogroModel;
import ar.com.fitlandia.fitlandia.models.RutinaModel;
import ar.com.fitlandia.fitlandia.models.VueltaEnLaPlazaModel;
import ar.com.fitlandia.fitlandia.runningok.RecyclerViewAdapterRunningHistorial;
import ar.com.fitlandia.fitlandia.runningok.RunningMapActivity;
import ar.com.fitlandia.fitlandia.rutinasok.EjerciciosDeRutinaActivity;
import ar.com.fitlandia.fitlandia.rutinasok.RecyclerViewAdapterEjercicios;
import ar.com.fitlandia.fitlandia.utils.APIService;
import ar.com.fitlandia.fitlandia.utils.ApiUtils;
import ar.com.fitlandia.fitlandia.utils.ApplicationGlobal;
import ar.com.fitlandia.fitlandia.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoricoLogrosActivity extends AppCompatActivity {



    @BindView(R.id.layout_logros)
    LinearLayout layout_logros;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<LogroModel> _logroModel;

    private APIService api;
    RecyclerViewAdapterLogros adapter;
    ApplicationGlobal applicationGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_logros);

        ButterKnife.bind(this);
        api = ApiUtils.getAPIService();

        applicationGlobal = ApplicationGlobal.getInstance();



        final ProgressDialog progressDialog = Utils.getProgressBar(this, "Cargando EntrenamientoModel");
        progressDialog.show();
        api.getLogros(applicationGlobal.getUsername()).enqueue(new Callback<List<LogroModel>>() {
            @Override
            public void onResponse(Call<List<LogroModel>> call, Response<List<LogroModel>> response) {
                if(response.isSuccessful()){
                    List<LogroModel> caratulas ;
                    caratulas = response.body();
                    cargarLista(layout_logros, caratulas);

                }else{
                    //Utils.mostrarSnackBar(view, "FFFFFFFFFFFFFF");
                    Utils.mostrarSnackBar(layout_logros, "err layout_logros");

                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<LogroModel>> call, Throwable t) {
                Utils.mostrarSnackBar(layout_logros, "Error layout_logros");
                progressDialog.dismiss();

            }
        });
    }

    private void cargarLista(View view, List<LogroModel> caratulas){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapterLogros(this, caratulas);
        final Activity _this = this;
        adapter.setClickListener(new RecyclerViewAdapterLogros.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LogroModel logroModel = adapter.getItem(position);

                Intent myIntent = new Intent(_this, verLogroActivity.class);
                //myIntent.putExtra("rutina_id", rutinaModel.getId()); //Optional parameters
                applicationGlobal.setLogroSelected(logroModel);
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
