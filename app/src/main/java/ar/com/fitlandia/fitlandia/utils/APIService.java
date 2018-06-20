package ar.com.fitlandia.fitlandia.utils;

import java.util.List;

import ar.com.fitlandia.fitlandia.models.FotoModel;
import ar.com.fitlandia.fitlandia.models.LoginModel;
import ar.com.fitlandia.fitlandia.models.VueltaEnLaPlazaModel;
import ar.com.fitlandia.fitlandia.models.UsuarioModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIService {


    @GET("user/{username}/entrenamientos/vueltaenlaplaza")
    Call<List<VueltaEnLaPlazaModel>> getAllVueltaEnLaPlaza(@Path("username")String username);

    @POST("login")
    Call<LoginModel> Login(@Body LoginModel loginModel);


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



}
