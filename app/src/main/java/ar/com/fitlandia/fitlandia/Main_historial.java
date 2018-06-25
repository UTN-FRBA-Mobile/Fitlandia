package ar.com.fitlandia.fitlandia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ar.com.fitlandia.fitlandia.logrosok.HistoricoLogrosActivity;
import ar.com.fitlandia.fitlandia.runningok.RunningHistorialActivity;

public class Main_historial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_historial);
    }
    public void goToHistoricoRutinas(View v){
        Intent intent = new Intent(this, HistoricoRutinas.class);
        startActivity(intent);
    }
    public void goToHistoricoRunning(View v){
        Intent intent = new Intent(this, RunningHistorialActivity.class);
        startActivity(intent);
    }
    public void goToHistoricoPeso(View v){
        Intent intent = new Intent(this, HistoricoPeso.class);
        startActivity(intent);
    }
    public void goToHistoricoFotos(View v){
        Intent intent = new Intent(this, HistoricoLogrosActivity.class);
        startActivity(intent);
    }
}
