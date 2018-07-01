package ar.com.fitlandia.fitlandia.historicoRutinas;

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
import android.widget.Toast;

import ar.com.fitlandia.fitlandia.Main_historial;
import ar.com.fitlandia.fitlandia.models.LogRutinaModel;

import java.util.List;

import ar.com.fitlandia.fitlandia.R;

import ar.com.fitlandia.fitlandia.utils.APIService;
import ar.com.fitlandia.fitlandia.utils.ApiUtils;
import ar.com.fitlandia.fitlandia.utils.ApplicationGlobal;
import ar.com.fitlandia.fitlandia.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class HistoricoRutinas extends AppCompatActivity {

    @BindView(R.id.activity_rutinas_historial)
    LinearLayout activity_rutinas_historial;
    private APIService api;
    RecyclerViewAdapterRutinasHistorial adapter;
    ApplicationGlobal applicationGlobal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_rutinas);
        ButterKnife.bind(this);

        api = ApiUtils.getAPIService();
        applicationGlobal = ApplicationGlobal.getInstance();

        final ProgressDialog progressDialog = Utils.getProgressBar(this, "Cargando rutinas realizadas");
        progressDialog.show();
        api.getLogRutinas(applicationGlobal.getUsername()).enqueue(new Callback<List<LogRutinaModel>>() {
            @Override
            public void onResponse(Call<List<LogRutinaModel>> call, Response<List<LogRutinaModel>> response) {
                List<LogRutinaModel> rutinas;
                rutinas = response.body();
                 cargarLista(activity_rutinas_historial, rutinas);

                    progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<LogRutinaModel>> call, Throwable t) {
                Utils.mostrarSnackBar(activity_rutinas_historial, "Error al guardar en server");
            }
        });

    }

    private void cargarLista(View view, List<LogRutinaModel> rutinas){
        RecyclerView recyclerView = view.findViewById(R.id.rv_rutinas_historial);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapterRutinasHistorial(this, rutinas);
        final Activity _this = this;

        //TODO que hacer si le dan click, supongo que nada
      /*  adapter.setClickListener(new RecyclerViewAdapterRutinasHistorial.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LogRutinaModel e = adapter.getItem(position);
               Intent myIntent = new Intent(_this, Main_historial.class);
                myIntent.putExtra("id", e.getId()); //Optional parameters
                _this.startActivity(myIntent);
    //Toast.makeText(_this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
            }
        });*/
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
