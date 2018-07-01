package ar.com.fitlandia.fitlandia;

import android.util.Log;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import ar.com.fitlandia.fitlandia.models.LoginModel;
import ar.com.fitlandia.fitlandia.models.UsuarioModel;
import ar.com.fitlandia.fitlandia.utils.APIService;
import ar.com.fitlandia.fitlandia.utils.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    static APIService api;
    static LoginModel loginModel;
    static String defaultUsername;
    @BeforeClass
    public static void  setUpBeforeClass() throws Exception {
        api = ApiUtils.getAPIService();
        defaultUsername = "fit";
        loginModel = new LoginModel();
        loginModel.setUsername(defaultUsername);
        loginModel.setPassword(defaultUsername);


    }

    @Test
    public void login_con_usuario_fit_es_valido() {

        try {
            Response<UsuarioModel> result =  api.Login(loginModel).execute();
            assertTrue(result.body() !=null);
            assertTrue(result.body().getUsername().equalsIgnoreCase(defaultUsername));
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue("Excepcion:"+e.getMessage(), false);
        }

        /*api.Login(loginModel).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if(response.isSuccessful()) {
                    LoginModel result = response.body();
                    Log.d("JJ", result.getUsername());
                    assertTrue(result !=null);

                }else{
                    assertTrue(false);
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                assertTrue("Error al logear:"  +t.getMessage(), false);
            }
        });*/
    }

    @Test
    public void get_de_user_fit_retora_user_fit(){

        try {
            Response<UsuarioModel> response = api.getUser(defaultUsername).execute();
            assertTrue(response.body().getUsername().equalsIgnoreCase(defaultUsername));
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void crear_nuevo_usuario(){
        try {

            UsuarioModel usuarioModel = new UsuarioModel();
            usuarioModel.setUsername("test123");
            usuarioModel.setPassword("test123");

            UsuarioModel usuarioNuevoModel;
            Response<UsuarioModel> response=  api.crearUsuario(usuarioModel ).execute();
            usuarioNuevoModel= response.body();
            assertTrue(usuarioNuevoModel.getUsername().equalsIgnoreCase(usuarioModel.getUsername()) );



        } catch (IOException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void editar_usuario_fit(){

        try {

            Response<UsuarioModel> response = api.getUser(defaultUsername).execute();
            UsuarioModel usuarioModel = response.body();
            usuarioModel.setEdad(123);
            UsuarioModel usuarioModificadoModel;
            Response<UsuarioModel> response2=  api.editarUsuario(usuarioModel.getUsername(), usuarioModel ).execute();
            usuarioModificadoModel = response2.body();

            assertTrue(usuarioModificadoModel.getEdad() == 123);

        } catch (IOException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }




}