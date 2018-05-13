package ar.com.fitlandia.fitlandia.runningok;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Date;

public class CronometroService  extends Service {
    private static final String TAG = "BroadcastService";
    public static final String BROADCAST_ACTION = "com.websmithing.broadcasttest.displayevent";
    private Handler handler;
    Intent intent;
    int counter = 0;

    Cronometro cronometro;
    Thread thCronometro;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //handler.removeCallbacks(sendUpdatesToUI);
        //handler.postDelayed(sendUpdatesToUI, 1000); // 1 second

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


        return START_STICKY;

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
