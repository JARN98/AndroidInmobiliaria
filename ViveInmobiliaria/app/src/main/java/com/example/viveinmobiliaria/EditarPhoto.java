package com.example.viveinmobiliaria;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.viveinmobiliaria.Fragments.editarPhotoFragment;
import com.example.viveinmobiliaria.Generator.ServiceGenerator;
import com.example.viveinmobiliaria.Generator.TipoAutenticacion;
import com.example.viveinmobiliaria.Generator.UtilToken;
import com.example.viveinmobiliaria.Listener.EditarPhotosListener;
import com.example.viveinmobiliaria.Model.Photo;
import com.example.viveinmobiliaria.Model.Propiedad;
import com.example.viveinmobiliaria.Services.PhotoService;
import com.example.viveinmobiliaria.ViewModels.SubirFotoViewModel;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarPhoto extends AppCompatActivity implements EditarPhotosListener {
    private static final int READ_REQUEST_CODE = 10; //lo que merece este proyecto :D
    private String id;
    private FrameLayout FotoContainer;
    private FloatingActionButton floatingActionButton_upload_photos;
    Uri uriSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_photo);


        floatingActionButton_upload_photos = findViewById(R.id.floatingActionButton_upload_photos);




        Bundle b = new Bundle();
        b = getIntent().getExtras();
        id = b.getString("id");


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.FotoContainer, new editarPhotoFragment())
                .commit();


        floatingActionButton_upload_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();

            }
        });

    }

    public String getIdProperty(){
        return id;
    }

    public void performFileSearch() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("Filechooser URI", "Uri: " + uri.toString());
                uriSelected = uri;
                subirFotoApi();
            }
        }
    }

    private void subirFotoApi() {
        try {
            InputStream inputStream = EditarPhoto.this.getContentResolver().openInputStream(uriSelected);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            int cantBytes;
            byte[] buffer = new byte[1024 * 4];

            while ((cantBytes = bufferedInputStream.read(buffer, 0, 1024 * 4)) != -1) {
                baos.write(buffer, 0, cantBytes);
            }


            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse(EditarPhoto.this.getContentResolver().getType(uriSelected)), baos.toByteArray());


            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("photo", "photo", requestFile);

            RequestBody propertyId = RequestBody.create(MultipartBody.FORM, id);

            PhotoService service = ServiceGenerator.createService(PhotoService.class, UtilToken.getToken(EditarPhoto.this), TipoAutenticacion.JWT);
            Call<Photo> call = service.addPhoto(body, propertyId);

            call.enqueue(new Callback<Photo>() {
                @Override
                public void onResponse(Call<Photo> call, Response<Photo> response) {
                    if (response.code() != 201) {
                        Toast.makeText(EditarPhoto.this, "Fallo al subir foto", Toast.LENGTH_SHORT).show();
                    } else {
                        SubirFotoViewModel subirFotoViewModel = ViewModelProviders.of(EditarPhoto.this).get(SubirFotoViewModel.class);
                        Propiedad propiedad = new Propiedad();
                        subirFotoViewModel.selectedAplicar(propiedad);
                        Toast.makeText(EditarPhoto.this, "Foto súbida", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<Photo> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(EditarPhoto.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
