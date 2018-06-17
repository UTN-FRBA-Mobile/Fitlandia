package ar.com.fitlandia.fitlandia.runningok;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import ar.com.fitlandia.fitlandia.R;
import ar.com.fitlandia.fitlandia.models.TrackingModel;
import ar.com.fitlandia.fitlandia.utils.APIService;
import ar.com.fitlandia.fitlandia.utils.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RunningMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private APIService api;
    private GoogleMap mMap;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_map);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");


        api = ApiUtils.getAPIService();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(-34.584977, -58.393639);
        mMap.addMarker(new MarkerOptions().position(sydney).title("12Km, 150Kcal, 11min"));//.snippet("Distancia: 1.2Km\nCalorias perdidas: 123kcal\nTiempo: 12min"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18));

        if(id != null){
            api.getVueltaEnLaPlaza(id).enqueue(new Callback<TrackingModel>() {
                @Override
                public void onResponse(Call<TrackingModel> call, Response<TrackingModel> response) {
                    TrackingModel trackingModel  =response.body();

                    PolylineOptions rectOptions = new PolylineOptions();

                    TrackingModel.Tracking trackInicial = trackingModel.getTracking().get(0);

                    LatLng inicio = new LatLng(trackInicial.getLat(), trackInicial.getLng());
                    mMap.addMarker(new MarkerOptions().position(inicio).title("12Km, 150Kcal, 11min"));//.snippet("Distancia: 1.2Km\nCalorias perdidas: 123kcal\nTiempo: 12min"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(inicio, 16));


                    for (TrackingModel.Tracking trac: trackingModel.getTracking() ) {
                        rectOptions.add( new LatLng(trac.getLat(), trac.getLng()));

                    }


                    Polyline polyline = mMap.addPolyline(rectOptions);
                }

                @Override
                public void onFailure(Call<TrackingModel> call, Throwable t) {

                }
            });
        }else{
            cargarDefault();
        }//fin el se

    }
    private void cargarDefault(){

        LatLng sydney = new LatLng(-34.584977, -58.393639);
        mMap.addMarker(new MarkerOptions().position(sydney).title("12Km, 150Kcal, 11min"));//.snippet("Distancia: 1.2Km\nCalorias perdidas: 123kcal\nTiempo: 12min"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));

        PolylineOptions rectOptions = new PolylineOptions()
                .add(new LatLng(-34.584977, -58.393639))
                .add(new LatLng(-34.584988, -58.393715))
                .add(new LatLng(-34.584984, -58.393815))
                .add(new LatLng(-34.584967, -58.393901))
                .add(new LatLng(-34.584934, -58.393885))
                .add(new LatLng(-34.584900, -58.393870))
                .add(new LatLng(-34.584817, -58.393827))
                .add(new LatLng(-34.584676, -58.393758))
                .add(new LatLng(-34.584618, -58.393710))
                .add(new LatLng(-34.584563, -58.393644))
                .add(new LatLng(-34.584528, -58.393577))
                .add(new LatLng(-34.584501, -58.393500))
                .add(new LatLng(-34.584494, -58.393411))
                .add(new LatLng(-34.584501, -58.393299))
                .add(new LatLng(-34.584545, -58.393221))
                .add(new LatLng(-34.584633, -58.393137))
                .add(new LatLng(-34.584722, -58.393084))
                .add(new LatLng(-34.584794, -58.393057))
                .add(new LatLng(-34.584870, -58.393045))
                .add(new LatLng(-34.584940, -58.393061))
                .add(new LatLng(-34.584971, -58.393066))
                .add(new LatLng(-34.585031, -58.393101))
                .add(new LatLng(-34.585147, -58.393226))
                .add(new LatLng(-34.585041, -58.393372))
                .add(new LatLng(-34.585066, -58.393408))
                .add(new LatLng(-34.585083, -58.393462))
                .add(new LatLng(-34.585083, -58.393490))
                .add(new LatLng(-34.585077, -58.393524))
                .add(new LatLng(-34.585067, -58.393548))
                .add(new LatLng(-34.585352, -58.393685));

// Get back the mutable Polyline
        Polyline polyline = mMap.addPolyline(rectOptions);
    }
}
