package ar.com.fitlandia.fitlandia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ar.com.fitlandia.fitlandia.models.UsuarioModel;
import butterknife.ButterKnife;

import butterknife.BindView;

public class Perfil extends AppCompatActivity {
    @BindView(R.id.edad) EditText edad;
    @BindView(R.id.EditTextName) EditText nombre;
    @BindView(R.id.altura) EditText altura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
    }
    public void actualizar(View v){
        ButterKnife.bind(this);
        Toast toast1 =  Toast.makeText(getApplicationContext(), "Cargando tu perfil..."+nombre.getText(), Toast.LENGTH_SHORT);
        toast1.show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }
}
