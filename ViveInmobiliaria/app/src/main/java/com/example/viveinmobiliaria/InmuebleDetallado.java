package com.example.viveinmobiliaria;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.viveinmobiliaria.Adapters.ImageAdapter;
import com.example.viveinmobiliaria.Generator.ServiceGenerator;
import com.example.viveinmobiliaria.Model.Propiedad;
import com.example.viveinmobiliaria.Model.ResponseContainer;
import com.example.viveinmobiliaria.Services.PropertiesService;
import com.google.android.gms.maps.MapView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleDetallado extends AppCompatActivity {
    ViewPager viewPager;
    TextView textView_direccion_detalle, textView_city_detalle, textView_price_detalle, textView_descripcion;
    MapView mapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inmueble_detallado);

        Intent intent = getIntent();

        String id = intent.getStringExtra("id");

        findsId();
        
        events();

        PropertiesService propertiesService = ServiceGenerator.createService(PropertiesService.class);

        Call<ResponseContainer<Propiedad>> call = propertiesService.getOneProperty(id);

        call.enqueue(new Callback<ResponseContainer<Propiedad>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Propiedad>> call, Response<ResponseContainer<Propiedad>> response) {

            }

            @Override
            public void onFailure(Call<ResponseContainer<Propiedad>> call, Throwable t) {

            }
        });
    }

    private void events() {

        /*ImageAdapter imageAdapter = new ImageAdapter(this, RutasDeImagenes);*/
    }

    private void findsId() {
        
        viewPager = findViewById(R.id.viewPager);
        textView_direccion_detalle = findViewById(R.id.textView_direccion_detalle);
        textView_city_detalle = findViewById(R.id.textView_city_detalle);
        textView_price_detalle = findViewById(R.id.textView_price_detalle);
        textView_descripcion = findViewById(R.id.textView_descripcion);

    }
}
