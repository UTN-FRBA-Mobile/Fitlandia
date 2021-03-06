package ar.com.fitlandia.fitlandia;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import ar.com.fitlandia.fitlandia.models.FotoModel;
import ar.com.fitlandia.fitlandia.models.LogroModel;
import ar.com.fitlandia.fitlandia.utils.APIService;
import ar.com.fitlandia.fitlandia.utils.ApiUtils;
import ar.com.fitlandia.fitlandia.utils.ApplicationGlobal;
import ar.com.fitlandia.fitlandia.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Selfie extends AppCompatActivity {
//public class Selfie extends Activity{

    private APIService api;
    @BindView(R.id.btnAbrirCamara)
    Button btnAbrirCamara;
    @BindView(R.id.act_selfie)
    LinearLayout linearLayout;

    @BindView(R.id.btnAgregarComentario)
    Button btnAgregarComentario;

    @BindView(R.id.selfie_estado)
    TextView estado;

    @BindView(R.id.urlfoto)
    TextView urlfoto;

    @BindView(R.id.imagenok)
    ImageView imagenok;

    @BindView(R.id.comentario)
    TextView comentario;

    FotoModel fotoModel;
    ApplicationGlobal applicationGlobal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfie);

        ButterKnife.bind(this);
        applicationGlobal = ApplicationGlobal.getInstance();
        api = ApiUtils.getAPIService();
        btnAbrirCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamara();
            }
        });
        btnAgregarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarComentario();
            }
        });


    }


    public void abrirCamara(){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.OFF)
                .setCropMenuCropButtonTitle("Ok")
                .setAllowRotation(false)
                .setAllowFlipping(false)
                .setInitialCropWindowPaddingRatio(0)
                .start(this);

    }

    public void agregarComentario(){
        LogroModel lmodel = new LogroModel();
        lmodel.setComentario(comentario.getText().toString());
        lmodel.setFoto(fotoModel.getId());


        api.nuevoLogro(applicationGlobal.getUsername(), lmodel).enqueue(new Callback<LogroModel>() {
            @Override
            public void onResponse(Call<LogroModel> call, Response<LogroModel> response) {
                if(response.isSuccessful() && response.body()!=null){

                    Utils.newToast(getApplicationContext(), "Logro guardado");
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LogroModel> call, Throwable t) {
                Utils.newToast(getApplicationContext(), "Hubo un error ");

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                uploadFile(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void uploadFile(Uri fileUri) {

        final ProgressDialog progressBar = Utils.getProgressBar(this, "Subiendo imagen");
        progressBar.show();

        estado.setText("Subiendo ...");
        // create upload service client
        //FileUploadService service = ServiceGenerator.createService(FileUploadService.class);
        APIService service = ApiUtils.getAPIService();
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        //File file = FileUtils.getFile(this, fileUri);
        File file = new File(fileUri.getPath());

        // create RequestBody instance from file
        RequestBody requestFile ;
        String jj ;
        ContentResolver contentResolver;
        contentResolver =getContentResolver();
        //jj = contentResolver.getType(fileUri);

        Uri selectedUri = fileUri;
        String fileExtension
                = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
        String mimeType
                = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
        jj= mimeType;
        requestFile=  RequestBody.create( MediaType.parse(jj), file );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("foto", file.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = "hello, this is description speaking";
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);

        // finally, execute the request
        Call<FotoModel> call = api.subirFoto(description, body);
        call.enqueue(new Callback<FotoModel>() {
            @Override
            public void onResponse(Call<FotoModel> call,
                                   Response<FotoModel> response) {


                estado.setText("Subido OK...");
                fotoModel =  response.body();
                urlfoto.setText(fotoModel.getUrl());

                String base64String = fotoModel.getBase64();
                String base64Image = base64String.split(";")[1];

                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                imagenok.setImageBitmap(decodedByte);

                btnAgregarComentario.setVisibility(View.VISIBLE);
                progressBar.dismiss();
                //Utils.mostrarSnackBar(linearLayout, "Guardado en MongoDB!");
            }
            @Override
            public void onFailure(Call<FotoModel> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                Utils.mostrarSnackBar(linearLayout, "Hubo un error!");

                progressBar.dismiss();
            }
        });
    }

}
