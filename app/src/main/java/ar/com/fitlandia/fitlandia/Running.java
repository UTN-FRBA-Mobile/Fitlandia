package ar.com.fitlandia.fitlandia;

import android.Manifest;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Date;
import java.util.List;

import ar.com.fitlandia.fitlandia.logrosok.HistoricoLogrosActivity;
import ar.com.fitlandia.fitlandia.models.VueltaEnLaPlazaModel;
import ar.com.fitlandia.fitlandia.runningok.Cronometro;
import ar.com.fitlandia.fitlandia.runningok.LocationMonitoringService;
import ar.com.fitlandia.fitlandia.runningok.RunningHistorialActivity;
import ar.com.fitlandia.fitlandia.runningok.RunningMapActivity;
import ar.com.fitlandia.fitlandia.runningok.StorageOk;
import ar.com.fitlandia.fitlandia.utils.APIService;
import ar.com.fitlandia.fitlandia.utils.ApiUtils;
import ar.com.fitlandia.fitlandia.utils.ApplicationGlobal;
import ar.com.fitlandia.fitlandia.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Running extends AppCompatActivity {

    private static final String TAG = Running.class.getSimpleName();

    /**
     * Code used in requesting runtime permissions.
     */
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;


    private boolean mAlreadyStartedService = false;
    @BindView(R.id.running_iniciar) ImageView iniciar;
    @BindView(R.id.running_detener) ImageView detener;
    @BindView(R.id.running_cronometro) TextView tvCronometro;
    @BindView(R.id.running_tracking_log) TextView tvLogTrack;

    @BindView(R.id.running_status) TextView tvEstado;
    @BindView(R.id.running_layout) LinearLayout running_layout;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.btnrunning_subir) Button btnrunning_subir;
    @BindView(R.id.btnhistorial) Button btnhistorial;

    @OnClick(R.id.btnhistorial)
    public void verHistorial() {
        Utils.mostrarSnackBar(running_layout, "FFFF");

        Intent myIntent = new Intent(Running.this, RunningHistorialActivity.class);
        Running.this.startActivity(myIntent);
    }


    @BindView(R.id.btnVerRutaEnMapa)
    ImageView btnVerRutaEnMapa;

    private APIService api;
    Cronometro cronometro;
    boolean finalizado;
    Thread thCronometro;
    Handler mHandler;
    NotificationManagerCompat notificationManager;
    private VueltaEnLaPlazaModel _ultimoVueltaEnLaPlazaModel;
    ApplicationGlobal applicationGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        ButterKnife.bind(this);

        applicationGlobal = ApplicationGlobal.getInstance();
        notificationManager = NotificationManagerCompat.from(this);

        api = ApiUtils.getAPIService();

        tvCronometro.setText("00:00:00");
        mostrarIniciar();



        inicializarTracking();

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                // This is where you do your work in the UI thread.
                // Your worker tells you in the message what to do.
                String msg =message.getData().getString("salida").toString();
                if(!msg.equals("CHAU"))
                    tvCronometro.setText(msg);
                else{
                    detener(null);

                }

            }
        };

        //inicializo el cronometro con la hora guardada en memoria
        Date inicioGuardado = StorageOk.getHoraInicio();

        finalizado = inicioGuardado == null;

        if(inicioGuardado!=null) {
            iniciar(null);

        }
        else{
            mostrarIniciar();
        }

        btnrunning_subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subirAlServer();
            }
        });

        btnVerRutaEnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Running.this, RunningMapActivity.class);
                if(_ultimoVueltaEnLaPlazaModel !=null){
                    myIntent.putExtra("id", _ultimoVueltaEnLaPlazaModel.getId()); //Optional parameters
                    Running.this.startActivity(myIntent);
                }


            }
        });


        //si viene de la notificacoin y le dio stop
        Intent intent = getIntent();
        boolean detenerCronometro = intent.getBooleanExtra("EXTRA_NOTIFICATION_ID_DETENER", false);
        if(detenerCronometro){
            detener(null);
        }
    }


    public void subirAlServer(){
        Intent myIntent = new Intent(Running.this, RunningMapActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        Running.this.startActivity(myIntent);

    }


    public void goToHistorialLogros(View v){
        Intent intent = new Intent(this, HistoricoLogrosActivity.class);
        startActivity(intent);
    }

    public void iniciar(View view){

        if(!Utils.tieneActivadoElGePeEse(this)){
            Utils.mostrarSnackBar(running_layout, "Debe activar el GPS");
            return;
        }
        //comenzarTracking();
        startStep1();

        //inicializo el cronometro con la hora guardada en memoria
        Date inicioGuardado = StorageOk.getHoraInicio();
        cronometro = new Cronometro("Nombre del cronómetro", mHandler, inicioGuardado );
        thCronometro = new Thread(cronometro);
        thCronometro.start();


        if(finalizado){
            StorageOk.setHoraInicio();
        }
        mostrarDetener();
    }

    public void detener(View view){
        mostrarIniciar();

        finalizar();
    }

    public void finalizar(){


        finalizarTracking();
        finalizarCronometro();
    }

    private void finalizarCronometro(){
        cronometro.pause();
        cronometro = null;
        thCronometro.interrupt();
        StorageOk.removeHoraInicio();

    }

    private void finalizarTracking(){

        stopService(new Intent(this.getApplicationContext(), LocationMonitoringService.class));
        mAlreadyStartedService = false;

        notificationManager.cancel(123);
        progressBar.setVisibility(View.VISIBLE);

        List<VueltaEnLaPlazaModel.Tracking>  posiciones = StorageOk.getPosicionesTrack();
        VueltaEnLaPlazaModel vueltaEnLaPlazaModel = new VueltaEnLaPlazaModel();

        vueltaEnLaPlazaModel.setTracking(posiciones);
        //vueltaEnLaPlazaModel.setInicio();

        Date dtInicio = StorageOk.getHoraInicio();
        //long fin = System.currentTimeMillis();
        long fin = (new Date()).getTime();
        long inicio = dtInicio.getTime();

        vueltaEnLaPlazaModel.setInicio(inicio);
        vueltaEnLaPlazaModel.setFin(fin);
        vueltaEnLaPlazaModel.setTiempoEnSegundos( (fin-inicio)/1000 );

        vueltaEnLaPlazaModel.setDistanciaEnMetros(vueltaEnLaPlazaModel.calcularDistanciaEnKm());
        vueltaEnLaPlazaModel.setVelocidadEnMetrosSobreSegundos(vueltaEnLaPlazaModel.calcularVelocidad());


        tvEstado.setText("Sincronizando ...");

        api.nuevaVueltaEnLaPlaza(applicationGlobal.getUsername(), vueltaEnLaPlazaModel).enqueue(new Callback<VueltaEnLaPlazaModel>() {
            @Override
            public void onResponse(Call<VueltaEnLaPlazaModel> call, Response<VueltaEnLaPlazaModel> response) {
                StorageOk.removePosiciones();
                Utils.mostrarSnackBar(running_layout, "Guardado OK");
                _ultimoVueltaEnLaPlazaModel = response.body();

                btnVerRutaEnMapa.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                tvEstado.setText("Detenido");
            }

            @Override
            public void onFailure(Call<VueltaEnLaPlazaModel> call, Throwable t) {
                Utils.mostrarSnackBar(running_layout, "Error al guardar en server");
                progressBar.setVisibility(View.INVISIBLE);
                tvEstado.setText("Detenido");
            }
        });

    }


    private void mostrarIniciar(){

        iniciar.setVisibility(View.VISIBLE);
        detener.setVisibility(View.INVISIBLE);

        tvCronometro.setTextColor(Color.GREEN);
        finalizado = true;
        tvEstado.setText("Detenido ");
    }
    private void mostrarDetener(){
        iniciar.setVisibility(View.INVISIBLE);
        detener.setVisibility(View.VISIBLE);

        tvEstado.setText("Corriendo ...");


        tvCronometro.setTextColor(Color.BLACK);
        finalizado = true;
    }

    private void comenzarTracking(){
        boolean estaEjecutandoElServicio = false;
        estaEjecutandoElServicio  = isMyServiceRunning(LocationMonitoringService.class);

        if (!mAlreadyStartedService && tvLogTrack != null   & !estaEjecutandoElServicio) {

            tvLogTrack.setText(R.string.running_msg_location_service_started);

            //Start location sharing service to app server.........
            Intent intent = new Intent(this, LocationMonitoringService.class);
            startService(intent);

            mAlreadyStartedService = true;
            //Ends................................................
        }
        mostrarNotificacion();
    }

    private void mostrarNotificacion(){
/*
        Intent intent = new Intent(this, Running.class);
        intent.setAction("ACTION_SNOOZE");
        intent.putExtra("EXTRA_NOTIFICATION_ID", 0);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
*/
/*
        Intent intent = new Intent(this, Running.class);
        intent.putExtra("EXTRA_NOTIFICATION_ID", "OK");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
*/
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_conejo_corredor_16)
                .setContentTitle("Fitlandia")
                .setContentText("Corriendo ...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(getPendingIntentDetener(false))
                .setOngoing(true)
                .addAction(R.drawable.ic_detener_24, "Detener", getPendingIntentDetener(true))
                .setAutoCancel(false);


        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(123, mBuilder.build());
    }

    private PendingIntent getPendingIntentDetener(boolean detener){


        if(detener){
            Intent intent = new Intent(this, Running.class);
            intent.putExtra("EXTRA_NOTIFICATION_ID_DETENER", detener);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }else{
            Intent intent = new Intent(this, MainActivity.class);
            //intent.putExtra("EXTRA_NOTIFICATION_ID_DETENER", detener);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            return PendingIntent.getActivity(this, 0, intent, 0);
        }


    }


    private void inicializarTracking(){
        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String latitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LATITUDE);
                        String longitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LONGITUDE);
                        String count = intent.getStringExtra(LocationMonitoringService.EXTRA_COUNT);

                        if (latitude != null && longitude != null) {
                            tvLogTrack.setText("GET N°" + count + "\n Latitude : " + latitude + "\n Longitude: " + longitude);
                        }
                    }
                }, new IntentFilter(LocationMonitoringService.ACTION_LOCATION_BROADCAST)
        );
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    /*
    * *************************************************************************************
    * */


    @Override
    public void onResume() {
        super.onResume();

        //startStep1();
    }


    /**
     * Step 1: Check Google Play services
     */
    private void startStep1() {

        //Check whether this user has installed Google play service which is being used by Location updates.
        if (isGooglePlayServicesAvailable()) {

            //Passing null to indicate that it is executing for the first time.
            startStep2(null);

        } else {
            Toast.makeText(getApplicationContext(), R.string.running_no_google_playservice_available, Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Step 2: Check & Prompt Internet connection
     */
    private Boolean startStep2(DialogInterface dialog) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            promptInternetConnect();
            return false;
        }


        if (dialog != null) {
            dialog.dismiss();
        }

        //Yes there is active internet connection. Next check Location is granted by user or not.

        if (checkPermissions()) { //Yes permissions are granted by the user. Go to the next step.
            startStep3();
        } else {  //No user has not granted the permissions yet. Request now.
            requestPermissions();
        }
        return true;
    }

    /**
     * Show A Dialog with button to refresh the internet state.
     */
    private void promptInternetConnect() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Running.this);
        builder.setTitle(R.string.running_title_alert_no_intenet);
        builder.setMessage(R.string.running_msg_alert_no_internet);

        String positiveText = getString(R.string.running_btn_label_refresh);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        //Block the Application Execution until user grants the permissions
                        if (startStep2(dialog)) {

                            //Now make sure about location permission.
                            if (checkPermissions()) {

                                //Step 2: Start the Location Monitor Service
                                //Everything is there to start the service.
                                startStep3();
                            } else if (!checkPermissions()) {
                                requestPermissions();
                            }

                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Step 3: Start the Location Monitor Service
     */
    private void startStep3() {

        //And it will be keep running until you close the entire application from task manager.
        //This method will executed only once.

        comenzarTracking();
    }

    /**
     * Return the availability of GooglePlayServices
     */
    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(this, status, 2404).show();
            }
            return false;
        }
        return true;
    }


    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState1 = ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);

        int permissionState2 = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        return permissionState1 == PackageManager.PERMISSION_GRANTED && permissionState2 == PackageManager.PERMISSION_GRANTED;

    }

    /**
     * Start permissions requests.
     */
    private void requestPermissions() {

        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

        boolean shouldProvideRationale2 =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);


        // Provide an additional rationale to the img_user. This would happen if the img_user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale || shouldProvideRationale2) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.running_permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(Running.this,
                                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the img_user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(Running.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If img_user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.i(TAG, "Permission granted, updates requested, starting location updates");
                startStep3();

            } else {
                // Permission denied.

                // Notify the img_user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the img_user for permission (device policy or "Never ask
                // again" prompts). Therefore, a img_user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.running_permission_denied_explanation,
                        R.string.running_settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }


    /*@Override
    public void onDestroy() {
        stopService(new Intent(this, LocationMonitoringService.class));
        mAlreadyStartedService = false;
        super.onDestroy();
    }*/
}
