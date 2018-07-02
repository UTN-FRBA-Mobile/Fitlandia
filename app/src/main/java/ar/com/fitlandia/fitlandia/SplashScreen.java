package ar.com.fitlandia.fitlandia;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        goToActivity(MainActivity.class);

    }

    private void goToActivity(final Class aClass) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                Intent intent = new Intent(SplashScreen.this, aClass);
                startActivity(intent);
                finish();

            }
        }, 4000);
    }

}
