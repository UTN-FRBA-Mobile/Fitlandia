package ar.com.fitlandia.fitlandia.utils;

import java.util.List;

import ar.com.fitlandia.fitlandia.models.FotoModel;
import ar.com.fitlandia.fitlandia.models.LogPesoModel;
import ar.com.fitlandia.fitlandia.models.LogRutinaModel;
import ar.com.fitlandia.fitlandia.models.LoginModel;
import ar.com.fitlandia.fitlandia.models.LogroModel;
import ar.com.fitlandia.fitlandia.models.VueltaEnLaPlazaModel;
import ar.com.fitlandia.fitlandia.models.UsuarioModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIService {


    @GET("user/{username}/entrenamientos/vueltaenlaplaza")
    Call<List<VueltaEnLaPlazaModel>> getAllVueltaEnLaPlaza(@Path("username")String username);

    @POST("login")
    Call<UsuarioModel> Login(@Body LoginModel loginModel);


    @POST("user/{username}/entrenamientos/vueltaenlaplaza")
    Call<VueltaEnLaPlazaModel> nuevaVueltaEnLaPlaza(@Path("username") String username, @Body VueltaEnLaPlazaModel vueltaEnLaPlazaModel);



    @GET("vueltaenlaplaza/{id}")
    Call<VueltaEnLaPlazaModel> getVueltaEnLaPlaza(@Path("id")String id);

    @GET("user/{username}")
    Call<UsuarioModel> getUser(@Path("username") String username);


    @Multipart
    @POST("fotos")
    Call<FotoModel> subirFoto(
            @Part("nombre") RequestBody nombre,
            @Part MultipartBody.Part foto
    );


    @POST("user")
    Call<UsuarioModel> crearUsuario(
            @Body UsuarioModel usuarioModel
    );


    @PUT("user/{username}")
    Call<UsuarioModel> editarUsuario(@Path("username") String username, @Body UsuarioModel usuarioModel);


    @POST("user/{username}/logros")
    Call<LogroModel> nuevoLogro(@Path("username") String username,@Body LogroModel logroModel);

    @GET("user/{username}/logros")
    Call<List<LogroModel>> getLogros(@Path("username") String username);

    @GET("logros/{logroid}")
    Call<LogroModel> getLogro(@Path("logroid") String logroid);

    @GET("fotos/{fotoid}")
    Call<FotoModel> getFoto(@Path("fotoid") String fotoid);



    @GET("user/{username}/entrenamientos/rutinas")
    Call<List<LogRutinaModel>> getLogRutinas(@Path("username")String username);

    @POST("user/{username}/entrenamientos/rutinas")
    Call<LogRutinaModel> nuevoLogRutina(@Path("username") String username, @Body LogRutinaModel logRutinaModel);


    @GET("user/{username}/peso")
    Call<List<LogRutinaModel>> getLogPesos(@Path("username")String username);

    @POST("user/{username}/peso")
    Call<LogPesoModel> nuevoLogPeso(@Path("username") String username, @Body LogPesoModel logPesoModel);


}
