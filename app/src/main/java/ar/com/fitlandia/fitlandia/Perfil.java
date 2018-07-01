package ar.com.fitlandia.fitlandia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import ar.com.fitlandia.fitlandia.utils.ApplicationGlobal;
import retrofit2.Call;
import retrofit2.Callback;

import java.io.IOException;

import ar.com.fitlandia.fitlandia.models.UsuarioModel;
import ar.com.fitlandia.fitlandia.utils.APIService;
import ar.com.fitlandia.fitlandia.utils.ApiUtils;
import butterknife.ButterKnife;

import butterknife.BindView;
import retrofit2.Response;

public class Perfil extends AppCompatActivity {
    //   @BindView(R.id.edad) EditText edad;

    @BindView(R.id.EditTextName) EditText nombre;
    @BindView(R.id.altura) EditText altura;
    @BindView(R.id.cintura) EditText cintura;
    @BindView(R.id.cuello) EditText cuello;
    @BindView(R.id.cadera) EditText cadera;
    @BindView(R.id.peso) EditText peso;
    static APIService api;
    ApplicationGlobal applicationGlobal;
    static String defaultUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        applicationGlobal = ApplicationGlobal.getInstance();
        defaultUsername=applicationGlobal.getUsername();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        ButterKnife.bind(this);
        api = ApiUtils.getAPIService();
        //TODO traer los datos del usuario logueado antes de modificarlos
        api.getUser(defaultUsername).enqueue(new Callback<UsuarioModel>() {
            @Override
            public void onResponse(Call<UsuarioModel> call, Response<UsuarioModel> response) {
                if (response.isSuccessful()) {
                    EditText inputName = (EditText) findViewById(R.id.EditTextName);
                    if( response.body().getUsername()!=null)
                        inputName.setText(response.body().getUsername().toString());
                    EditText inputpeso = (EditText) findViewById(R.id.peso);
                    if( response.body().getPeso()!=null)
                        inputpeso.setText(response.body().getPeso().toString());

                    EditText inputaltura = (EditText) findViewById(R.id.altura);
                    if( response.body().getAltura()!=null)
                        inputaltura.setText(response.body().getAltura().toString());

                    EditText inputcintura = (EditText) findViewById(R.id.cintura);
                    if( response.body().getCintura()!=null)
                        inputcintura.setText(response.body().getCintura().toString());
                    EditText inputcuello = (EditText) findViewById(R.id.cuello);
                    if( response.body().getCuello()!=null)
                        inputcuello.setText(response.body().getCuello().toString());
                    EditText inputcadera = (EditText) findViewById(R.id.cadera);
                    if( response.body().getCadera()!=null)
                        inputcadera.setText(response.body().getCadera().toString());
                }

            }

            @Override
            public void onFailure(Call<UsuarioModel> call, Throwable t) {

            }

        });
    }

    public void actualizar (View v) throws IOException {
        api = ApiUtils.getAPIService();
        UsuarioModel usuarioModel = new UsuarioModel();

        //TODO cambiar fit por el usuario logueado

        usuarioModel.setUsername(defaultUsername);
        // usuarioModel.setPassword("fit");
        //usuarioModel.setUsername(nombre.getText().toString());


        usuarioModel.setAltura(Float.parseFloat(altura.getText().toString()));
        usuarioModel.setCintura(Float.parseFloat(cintura.getText().toString()));
        usuarioModel.setCuello(Float.parseFloat(cuello.getText().toString()));
        usuarioModel.setCadera(Float.parseFloat(cadera.getText().toString()));
        usuarioModel.setPeso(Float.parseFloat(peso.getText().toString()));

        Call<UsuarioModel> call = api.editarUsuario(defaultUsername, usuarioModel);

        call.enqueue(new Callback<UsuarioModel>() {
            @Override
            public void onResponse(Call<UsuarioModel> call,
                                   Response<UsuarioModel> response) {

                if (response.isSuccessful()) {
                    UsuarioModel result = response.body();
                    Log.d("perfil", result.getUsername());

                } else {
                }
            }


            @Override
            public void onFailure(Call<UsuarioModel> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                //   Utils.mostrarSnackBar(linearLayout, "Hubo un error!");
            }
        });
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}