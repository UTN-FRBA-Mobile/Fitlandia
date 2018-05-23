package ar.com.fitlandia.fitlandia.runningok;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Cronometro implements Runnable
{
    // Atributos privados de la clase
    //private TextView etiq; // Etiqueta para mostrar la información
    private String nombrecronometro; // Nombre del cronómetro
    private int segundos, minutos, horas; // Segundos, minutos y horas que lleva activo el cronómetro
    private Handler escribirenUI; // Necesario para modificar la UI
    private Boolean pausado; // Para pausar el cronómetro
    private String salida; // Salida formateada de los datos del cronómetro

    public static Date getDate(long milliSeconds)
    {
        // Create a DateFormatter object for displaying date in specified format.
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return calendar.getTime();
    }


    public Cronometro(String nombre, Handler handler, Date comienzo)
    {
        //etiq = etiqueta;
        salida = "";

        if(comienzo == null){
            segundos = 0;
            minutos = 0;
            horas = 0;
        }else{
            Date ahora = new Date();
            long diffInMillisec = ahora.getTime() - comienzo.getTime();
            Date com  = getDate(diffInMillisec);

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long different = diffInMillisec;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;


            ////long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
            //long diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillisec);
            //long diffInMin = TimeUnit.MILLISECONDS.toMinutes(diffInMillisec);
            //long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMillisec);
            /////////////////////////////////
            segundos = (int)elapsedSeconds;
            minutos = (int)elapsedMinutes;
            horas = (int)elapsedHours;


        }


        nombrecronometro = nombre;
        escribirenUI = new Handler();
        pausado = Boolean.FALSE;
        escribirenUI = handler;
    }
    public void reiniciar()
    {
        segundos = 0;
        minutos = 0;
        horas = 0;
        pausado = Boolean.FALSE;
    }

    public void pause()
    {
        pausado = !pausado;
    }

    @Override
    /**
     * Acción del cronómetro, contar tiempo en segundo plano
     */
    public void run()
    {
        try
        {
            while(Boolean.TRUE)
            {
                Thread.sleep(1000);
                salida = "";
                if( !pausado )
                {
                    segundos++;
                    if(segundos == 60)
                    {
                        segundos = 0;
                        minutos++;
                    }
                    if(minutos == 60)
                    {
                        minutos = 0;
                        horas++;
                    }
                    // Formateo la salida
                    salida += "0";
                    salida += horas;
                    salida += ":";
                    if( minutos <= 9 )
                    {
                        salida += "0";
                    }
                    salida += minutos;
                    salida += ":";
                    if( segundos <= 9 )
                    {
                        salida += "0";
                    }
                    salida += segundos;
                    // Modifico la UI
                    try
                    {

                        //parque para que pare a los 10min

                        Message m = new Message();
                        Bundle b = new Bundle( );
                        if(minutos ==5){
                            b.putString("salida", "CHAU");
                            m.setData(b);
                            escribirenUI.sendMessage(m);
                            break;
                        }else{
                            b.putString("salida", salida);
                            m.setData(b);
                            escribirenUI.sendMessage(m);
                        }
                        /*escribirenUI.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                etiq.setText(salida);
                            }
                        });*/

                    }
                    catch (Exception e)
                    {
                        Log.i("Cronometro", "Error en el cronometro " + nombrecronometro + " al escribir en la UI: " + e.toString());
                    }
                }
            }
        }
        catch (InterruptedException e)
        {
            Log.i("Cronometro", "Error en el cronometro " + nombrecronometro + ": " + e.toString());
        }
    }



}