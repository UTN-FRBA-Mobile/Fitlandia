package ar.com.fitlandia.fitlandia.utils;

import ar.com.fitlandia.fitlandia.models.FotoModel;
import ar.com.fitlandia.fitlandia.models.LoginModel;
import ar.com.fitlandia.fitlandia.models.TrackingModel;
import ar.com.fitlandia.fitlandia.models.UsuarioModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIService {


    @POST("login")
    Call<LoginModel> Login(@Body LoginModel loginModel);


    @POST("user/{username}/entrenamientos/vueltaenlaplaza")
    Call<TrackingModel> nuevaVueltaEnLaPlaza(@Path("username") String username, @Body TrackingModel trackingModel);



    @GET("user/{username}")
    Call<UsuarioModel> getUser(@Path("username") String username);


    @Multipart
    @POST("fotos")
    Call<FotoModel> subirFoto(
            @Part("nombre") RequestBody nombre,
            @Part MultipartBody.Part foto
    );



}
