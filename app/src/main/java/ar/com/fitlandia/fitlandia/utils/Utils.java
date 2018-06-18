package ar.com.fitlandia.fitlandia.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.view.View;

public class Utils {

    public static void mostrarSnackBar(View view, String mensaje){
        Snackbar snackbar = Snackbar
                .make(view, mensaje, Snackbar.LENGTH_LONG);

        snackbar.show();

    }



    public static ProgressDialog getProgressBar(Activity activity, String message){
        ProgressDialog progressBar = new ProgressDialog(activity);
        progressBar.setMessage(message);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        return progressBar;
    }

    public static boolean tieneActivadoElGePeEse(Activity activity){
        final LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        boolean gpsProviderEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        return gpsProviderEnabled ;
        /*String s;
        if(gpsProviderEnabled) {
            s = "GPS : enable";
        } else {
            s = "GPS : disable";
        }*/

    }
}
