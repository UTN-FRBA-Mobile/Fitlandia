package ar.com.fitlandia.fitlandia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    Button Rutinas;
    Button Running;
    Button Selfie;
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

         Paper.init(getApplicationContext());

     }

    public void goToRutinas(View v){
        Intent intent = new Intent(this, Rutinas.class);
        startActivity(intent);
    }
    public void goToRunning(View v){
        Intent intent = new Intent(this, Running.class);
        startActivity(intent);
    }
    public void goToSelfie(View v){
        Intent intent = new Intent(this, Selfie.class);
        startActivity(intent);
    }
}
