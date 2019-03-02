package com.example.viveinmobiliaria;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viveinmobiliaria.Adapters.ImageAdapter;
import com.example.viveinmobiliaria.Generator.ServiceGenerator;
import com.example.viveinmobiliaria.Generator.TipoAutenticacion;
import com.example.viveinmobiliaria.Generator.UtilToken;
import com.example.viveinmobiliaria.Generator.UtilUser;
import com.example.viveinmobiliaria.Model.Propiedad;
import com.example.viveinmobiliaria.Model.ResponseContainer;
import com.example.viveinmobiliaria.Model.ResponseContainerNoList;
import com.example.viveinmobiliaria.Services.PropertiesService;
import com.google.android.gms.maps.MapView;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleDetallado extends AppCompatActivity {
    ViewPager viewPager;
    TextView textView_direccion_detalle, textView_city_detalle, textView_price_detalle, textView_descripcion;
    MapView mapView;
    Propiedad propiedad;
    private List<String> imagenes;
    private MenuItem editPhotos;
    private String id;
    private Menu menu;
    private MenuItem action_editarPhoto, action_editarPropiedad, action_eliminarPropiedad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inmueble_detallado);

        findsId();

    }


    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();

        id = intent.getStringExtra("id");

        PropertiesService propertiesService = ServiceGenerator.createService(PropertiesService.class);

        Call<ResponseContainerNoList<Propiedad>> call = propertiesService.getOneProperty(id);

        call.enqueue(new Callback<ResponseContainerNoList<Propiedad>>() {
            @Override
            public void onResponse(Call<ResponseContainerNoList<Propiedad>> call, Response<ResponseContainerNoList<Propiedad>> response) {
                if (response.code() != 200) {
                    Toast.makeText(InmuebleDetallado.this, "Error al ver Inmueble", Toast.LENGTH_SHORT).show();
                } else {
                    propiedad = response.body().getRows();
                    if(UtilUser.getNombre(InmuebleDetallado.this) != null){
                        if ( UtilUser.getNombre(InmuebleDetallado.this).equals(propiedad.getOwnerIdname())){
                            /*
                            Esconder iconos de navbar
                             */

                            Toast.makeText(InmuebleDetallado.this, "Entra", Toast.LENGTH_SHORT).show();
                        }
                    }

                    sets();
                }
            }

            @Override
            public void onFailure(Call<ResponseContainerNoList<Propiedad>> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(InmuebleDetallado.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(UtilUser.getNombre(InmuebleDetallado.this) != null){
            item.setVisible(false);
        }

        switch (id) {
            case R.id.action_editarPhoto:
                Intent i = new Intent(InmuebleDetallado.this, EditarPhoto.class);
                i.putExtra("id", propiedad.getId());
                startActivity(i);
                return true;
            case R.id.action_editarPropiedad:
                startActivity(new Intent(InmuebleDetallado.this, CrearInmueble.class).putExtra("idpropiedad", propiedad.getId()));
                return true;
            case R.id.action_eliminarPropiedad:
                eliminarPropiedad();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void eliminarPropiedad() {
        PropertiesService service = ServiceGenerator.createService(PropertiesService.class, UtilToken.getToken(this), TipoAutenticacion.JWT);
        Call<ResponseContainerNoList<Propiedad>> call = service.deleteProperty(id);

        call.enqueue(new Callback<ResponseContainerNoList<Propiedad>>() {
            @Override
            public void onResponse(Call<ResponseContainerNoList<Propiedad>> call, Response<ResponseContainerNoList<Propiedad>> response) {
                if (response.code() != 204) {
                    Toast.makeText(InmuebleDetallado.this, "Fallo al borrar", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseContainerNoList<Propiedad>> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(InmuebleDetallado.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sets() {
        textView_descripcion.setText(propiedad.getDescription());
        textView_city_detalle.setText(propiedad.getCity() + " - " + propiedad.getProvince());
        textView_direccion_detalle.setText(propiedad.getAddress());
        textView_price_detalle.setText(propiedad.getPrice() + " €/mes");

        imagenes = Arrays.asList(propiedad.getPhotos());

        ImageAdapter imageAdapter = new ImageAdapter(InmuebleDetallado.this, imagenes);

/*        if (UtilUser.getId(this) != null) {
            if (!UtilUser.getId(this).equals(propiedad.getOwnerId())) {
                floatingActionButtonEditPhoto.hide();
            }
        }*/

        viewPager.setAdapter(imageAdapter);
    }

    private void findsId() {

        viewPager = findViewById(R.id.viewPager);
        textView_direccion_detalle = findViewById(R.id.textView_direccion_detalle);
        textView_city_detalle = findViewById(R.id.textView_city_detalle);
        textView_price_detalle = findViewById(R.id.textView_price_detalle);
        textView_descripcion = findViewById(R.id.textView_descripcion);
        editPhotos = findViewById(R.id.action_editarPhoto);
        action_eliminarPropiedad = findViewById(R.id.action_eliminarPropiedad);
        action_editarPhoto = findViewById(R.id.action_editarPhoto);
        action_editarPropiedad = findViewById(R.id.action_editarPropiedad);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
}
