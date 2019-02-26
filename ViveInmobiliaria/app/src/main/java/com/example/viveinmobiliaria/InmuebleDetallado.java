package com.example.viveinmobiliaria;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viveinmobiliaria.Adapters.ImageAdapter;
import com.example.viveinmobiliaria.Generator.ServiceGenerator;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inmueble_detallado);

        Intent intent = getIntent();

        String id = intent.getStringExtra("id");

        findsId();

        PropertiesService propertiesService = ServiceGenerator.createService(PropertiesService.class);

        Call<ResponseContainerNoList<Propiedad>> call = propertiesService.getOneProperty(id);

        call.enqueue(new Callback<ResponseContainerNoList<Propiedad>>() {
            @Override
            public void onResponse(Call<ResponseContainerNoList<Propiedad>> call, Response<ResponseContainerNoList<Propiedad>> response) {
                if (response.code() != 200) {
                    Toast.makeText(InmuebleDetallado.this, "Error al ver Inmueble", Toast.LENGTH_SHORT).show();
                } else {
                    propiedad = response.body().getRows();
                    sets();
                }
            }

            @Override
            public void onFailure(Call<ResponseContainerNoList<Propiedad>> call, Throwable t) {

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

        viewPager.setAdapter(imageAdapter);
    }

    private void findsId() {

        viewPager = findViewById(R.id.viewPager);
        textView_direccion_detalle = findViewById(R.id.textView_direccion_detalle);
        textView_city_detalle = findViewById(R.id.textView_city_detalle);
        textView_price_detalle = findViewById(R.id.textView_price_detalle);
        textView_descripcion = findViewById(R.id.textView_descripcion);

    }
}
