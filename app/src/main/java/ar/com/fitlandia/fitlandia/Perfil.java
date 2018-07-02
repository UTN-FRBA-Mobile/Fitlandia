package ar.com.fitlandia.fitlandia;

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
import android.widget.EditText;
import android.widget.ImageView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import ar.com.fitlandia.fitlandia.models.FotoModel;
import ar.com.fitlandia.fitlandia.runningok.StorageOk;
import ar.com.fitlandia.fitlandia.utils.ApplicationGlobal;
import ar.com.fitlandia.fitlandia.utils.Utils;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import java.io.File;
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

    @BindView(R.id.fotoPerfil) ImageView fotoPerfil;
    @OnClick(R.id.tomarFoto)
    public void tomarFoto(View view ){
        abrirCamara();
    }


    static APIService api;
    ApplicationGlobal applicationGlobal;
    static String defaultUsername;
    FotoModel fotoModel;
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

        if(applicationGlobal.tieneFotoPerfil()){
            fotoPerfil.setImageBitmap(applicationGlobal.getFotoPerfilBitmap());
            fotoModel = applicationGlobal.getUsuario().getFotoPerfil();
        }

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



                fotoModel =  response.body();
                //urlfoto.setText(fotoModel.getUrl());

                String base64String = fotoModel.getBase64();
                String base64Image = base64String.split(";")[1];

                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                fotoPerfil.setImageBitmap(decodedByte);

                progressBar.dismiss();
                //Utils.mostrarSnackBar(linearLayout, "Guardado en MongoDB!");
            }
            @Override
            public void onFailure(Call<FotoModel> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());


                progressBar.dismiss();
            }
        });
    }

    public void actualizar (View v) throws IOException {
        api = ApiUtils.getAPIService();
        UsuarioModel usuarioModel = new UsuarioModel();
        //UsuarioModel usuarioModel  = applicationGlobal.getUsuario();

        //TODO cambiar fit por el usuario logueado

        usuarioModel.setUsername(defaultUsername);
        usuarioModel.setPassword(applicationGlobal.getUsuario().getPassword());
        // usuarioModel.setPassword("fit");
        //usuarioModel.setUsername(nombre.getText().toString());

        final ProgressDialog progressDialog = Utils.getProgressBar(this, "Guardando ...");
        progressDialog.show();
        usuarioModel.setAltura(Float.parseFloat(altura.getText().toString()));
        usuarioModel.setCintura(Float.parseFloat(cintura.getText().toString()));
        usuarioModel.setCuello(Float.parseFloat(cuello.getText().toString()));
        usuarioModel.setCadera(Float.parseFloat(cadera.getText().toString()));
        usuarioModel.setPeso(Float.parseFloat(peso.getText().toString()));
        if(fotoModel!=null)
            usuarioModel.setFoto(fotoModel.getId());

        Call<UsuarioModel> call = api.editarUsuario(defaultUsername, usuarioModel);

        call.enqueue(new Callback<UsuarioModel>() {
            @Override
            public void onResponse(Call<UsuarioModel> call,
                                   Response<UsuarioModel> response) {

                if (response.isSuccessful()) {
                    UsuarioModel usuario = response.body();
                    Log.d("perfil", usuario.getUsername());

                    usuario.setFotoPerfil(fotoModel);


                    applicationGlobal.vaciarFotoPerfilGuardada();
                    StorageOk.setLogin(usuario);

                    applicationGlobal.setUsuario(usuario);
                    progressDialog.dismiss();
                    Utils.newToast(getApplicationContext(), "Guardado ok");
                    Intent intent = new Intent(Perfil.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    progressDialog.dismiss();
                }
            }


            @Override
            public void onFailure(Call<UsuarioModel> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                progressDialog.dismiss();
                Utils.newToast(getApplicationContext(), "Hubo un error");
            }
        });
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
    }


}