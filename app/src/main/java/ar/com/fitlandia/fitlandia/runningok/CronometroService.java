package ar.com.fitlandia.fitlandia.runningok;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Date;

import ar.com.fitlandia.fitlandia.R;
import ar.com.fitlandia.fitlandia.Running;

public class CronometroService  extends Service {
    private static final String TAG = "BroadcastService";
    public static final String BROADCAST_ACTION = "com.websmithing.broadcasttest.displayevent";
    private Handler handler;
    Intent intent;
    int counter = 0;

    Cronometro cronometro;
    Thread thCronometro;
    NotificationManagerCompat notificationManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //handler.removeCallbacks(sendUpdatesToUI);
        //handler.postDelayed(sendUpdatesToUI, 1000); // 1 second

        notificationManager = NotificationManagerCompat.from(this);

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                // This is where you do your work in the UI thread.
                // Your worker tells you in the message what to do.
                enviarProgreso(message.getData().getString("salida").toString());
            }
        };

        cronometro = new Cronometro("Nombre del cronómetro", handler, null );
        thCronometro = new Thread(cronometro);
        thCronometro.start();
        //cronometro.pause();

        mostrarNotificacion();


        return START_STICKY;

    }

    private void mostrarNotificacion(){

        /*Intent intent = new Intent(this, MyBroadcastReceiver.class);
        snoozeIntent.setAction(ACTION_SNOOZE);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);
*/

        Intent intent = new Intent(this, Running.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Fitlandia")
                .setContentText("Corriendo ...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .addAction(R.drawable.ic_menu_gallery, "Detener", pendingIntent)
                .setAutoCancel(false);


        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(123, mBuilder.build());



    }
/*
    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            DisplayLoggingInfo();
            handler.postDelayed(this, 10000); // 10 seconds
        }
    };*/

    private void enviarProgreso(String s){
        Intent intent = new Intent(BROADCAST_ACTION);

        intent.putExtra("time", s);
        intent.putExtra("counter", String.valueOf(++counter));
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        //handler.removeCallbacks(sendUpdatesToUI);

        thCronometro.interrupt();
        cronometro = null;
        thCronometro = null;
        super.onDestroy();
    }
}
