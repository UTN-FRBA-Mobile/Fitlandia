package ar.com.fitlandia.fitlandia.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utils {

    public static void mostrarSnackBar(View view, String mensaje){
        Snackbar snackbar = Snackbar
                .make(view, mensaje, Snackbar.LENGTH_LONG);

        snackbar.show();

    }

    public static String fromDateToString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        //Date date = new Date();
        String dateTime = dateFormat.format(date);
        //System.out.println("Current Date Time : " + dateTime);
        return dateTime;
    }


    public  static Date fromStringToDate(String dtStart){
        //String dtStart = "2010-10-15T09:27:37Z";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date date = format.parse(dtStart);
            //System.out.println(date);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

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
