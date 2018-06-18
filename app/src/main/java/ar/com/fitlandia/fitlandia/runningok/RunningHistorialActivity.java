package ar.com.fitlandia.fitlandia.runningok;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import ar.com.fitlandia.fitlandia.R;
import ar.com.fitlandia.fitlandia.models.EntrenamientoModel;
import ar.com.fitlandia.fitlandia.utils.APIService;
import ar.com.fitlandia.fitlandia.utils.ApiUtils;
import ar.com.fitlandia.fitlandia.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RunningHistorialActivity extends AppCompatActivity {

    @BindView(R.id.running_historial)
    LinearLayout activity_running_historial;
    private APIService api;
    RecyclerViewAdapterRunningHistorial adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_historial);
        ButterKnife.bind(this);

        api = ApiUtils.getAPIService();


        final ProgressDialog progressDialog = Utils.getProgressBar(this, "Cargando EntrenamientoModel");
        progressDialog.show();
        api.getAllVueltaEnLaPlaza("fit").enqueue(new Callback<List<EntrenamientoModel>>() {
            @Override
            public void onResponse(Call<List<EntrenamientoModel>> call, Response<List<EntrenamientoModel>> response) {
                if(response.isSuccessful()){
                    List<EntrenamientoModel> caratulas ;
                    caratulas = response.body();
                    cargarLista(activity_running_historial, caratulas);
                    progressDialog.dismiss();

                }else{
                    //Utils.mostrarSnackBar(view, "FFFFFFFFFFFFFF");
                    Utils.mostrarSnackBar(activity_running_historial, "Debe activar el GPS");
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<List<EntrenamientoModel>> call, Throwable t) {
                Utils.mostrarSnackBar(activity_running_historial, "Error al guardar en server");
            }
        });



    }

    private void cargarLista(View view, List<EntrenamientoModel> caratulas){
        RecyclerView recyclerView = view.findViewById(R.id.rv_running_historial);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapterRunningHistorial(this, caratulas);
        final Activity _this = this;
        adapter.setClickListener(new RecyclerViewAdapterRunningHistorial.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(_this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

            }
        });
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
