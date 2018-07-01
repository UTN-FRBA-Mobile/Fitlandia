package ar.com.fitlandia.fitlandia;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import ar.com.fitlandia.fitlandia.runningok.StorageOk;
import ar.com.fitlandia.fitlandia.utils.ApiUtils;
import ar.com.fitlandia.fitlandia.utils.ApplicationGlobal;
import ar.com.fitlandia.fitlandia.utils.Utils;
import ar.com.fitlandia.fitlandia.models.UsuarioModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
import ar.com.fitlandia.fitlandia.utils.APIService;


public class Register extends AppCompatActivity {

    private Button btnRegister;
    private EditText inputFullName;
    private EditText inputPassword;
    private TextView registerErrorMsg;
    private APIService api;
    LinearLayout layoutRegister;

    String usuario;
    String password;
    ApplicationGlobal applicationGlobal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        api = ApiUtils.getAPIService();
        inputFullName = (EditText) findViewById(R.id.txtUserName);
        inputPassword = (EditText) findViewById(R.id.txtPass);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);
        layoutRegister = (LinearLayout) findViewById(R.id.layoutRegister);
        applicationGlobal = ApplicationGlobal.getInstance();


        btnRegister.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                usuario = inputFullName.getText().toString();
                password = inputPassword.getText().toString();
                registerUser(usuario, password);
            }
        });
    }

    private void registerUser(final String name, final String pass){

        UsuarioModel user = new UsuarioModel();

        user.setUsername(name);
        user.setPassword(pass);

        Call<UsuarioModel> call = api.crearUsuario(user);

        call.enqueue(new Callback<UsuarioModel>() {
            @Override
            public void onResponse(Call<UsuarioModel> call,
                                   Response<UsuarioModel> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    applicationGlobal.setUsuario(response.body());
                    StorageOk.setLogin(response.body());
                    Utils.newToast(getApplicationContext(), "Usuario creado correctamente");
                    //Log.d("JJ", result.getUsername());
                    Intent intent = new Intent(Register.this, Perfil.class);
                    startActivity(intent);
                    finish();

                    //Utils.mostrarSnackBar(layoutRegister, "Usuario creado");

                }else{
                    Utils.mostrarSnackBar(layoutRegister, "Hubo un error!");
                }
            }

            @Override
            public void onFailure(Call<UsuarioModel> call, Throwable t) {
                //Log.e("Upload error:", t.getMessage());
                Utils.mostrarSnackBar(layoutRegister, "Hubo un error!");
            }
        });

    }

}
