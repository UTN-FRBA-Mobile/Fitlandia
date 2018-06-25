package ar.com.fitlandia.fitlandia.logrosok;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ar.com.fitlandia.fitlandia.R;
import ar.com.fitlandia.fitlandia.models.FotoModel;
import ar.com.fitlandia.fitlandia.models.LogroModel;
import ar.com.fitlandia.fitlandia.models.VueltaEnLaPlazaModel;
import ar.com.fitlandia.fitlandia.utils.APIService;
import ar.com.fitlandia.fitlandia.utils.ApiUtils;
import ar.com.fitlandia.fitlandia.utils.ApplicationGlobal;
import ar.com.fitlandia.fitlandia.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class verLogroActivity extends AppCompatActivity {

    @BindView(R.id.fecha)
    TextView fecha;

    @BindView(R.id.comentario)
    TextView comentario;

    @BindView(R.id.foto)
    ImageView foto;

    @BindView(R.id.layout_ver_logro)
    LinearLayout layout_ver_logro;

    private APIService api;
    ApplicationGlobal applicationGlobal;
    LogroModel logroModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_logro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ButterKnife.bind(this);
        api = ApiUtils.getAPIService();

        applicationGlobal = ApplicationGlobal.getInstance();

        logroModel = applicationGlobal.getLogroSelected();


        final ProgressDialog progressDialog = Utils.getProgressBar(this, "Cargando ...");
        progressDialog.show();
        api.getLogro(logroModel.getId()).enqueue(new Callback<LogroModel>() {
            @Override
            public void onResponse(Call<LogroModel> call, Response<LogroModel> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()){
                    LogroModel logroModel;
                    logroModel = response.body();

                    SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                    Date dtfecha = Utils.fromStringToDate(logroModel.getFecha());
                    String  sfecha= "" ;
                    sfecha = dateFormat.format(dtfecha);
                    fecha.setText(sfecha);


                    comentario.setText(logroModel.getComentario());

                    cargarFoto(logroModel.getFoto());

                }else{
                    //Utils.mostrarSnackBar(view, "FFFFFFFFFFFFFF");
                    Utils.mostrarSnackBar(layout_ver_logro, "Debe activar el GPS");


                }
            }

            @Override
            public void onFailure(Call<LogroModel> call, Throwable t) {
                Utils.mostrarSnackBar(layout_ver_logro, "Error al guardar en server");
                progressDialog.dismiss();

            }
        });

    }

    private void cargarFoto(String fotoId){
        //final ProgressDialog progressDialog = Utils.getProgressBar(this, "Cargando ...");
        //progressDialog.show();
        api.getFoto(fotoId).enqueue(new Callback<FotoModel>() {
            @Override
            public void onResponse(Call<FotoModel> call, Response<FotoModel> response) {
                //progressDialog.dismiss();
                if(response.isSuccessful()){
                    FotoModel fotoModel;
                    fotoModel = response.body();

                    String base64String = fotoModel.getBase64();
                    String base64Image = base64String.split(";")[1];

                    byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                    foto.setImageBitmap(decodedByte);



                }else{
                    //Utils.mostrarSnackBar(view, "FFFFFFFFFFFFFF");
                    Utils.mostrarSnackBar(layout_ver_logro, "Debe activar el GPS");


                }
            }

            @Override
            public void onFailure(Call<FotoModel> call, Throwable t) {
                Utils.mostrarSnackBar(layout_ver_logro, "Error al guardar en server");
                //progressDialog.dismiss();

            }
        });
    }

}
