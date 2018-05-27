package ar.com.fitlandia.fitlandia.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

public class Utils {

    public static void mostrarSnackBar(View view, String mensaje){
        Snackbar snackbar = Snackbar
                .make(view, mensaje, Snackbar.LENGTH_LONG);

        snackbar.show();

    }
}
