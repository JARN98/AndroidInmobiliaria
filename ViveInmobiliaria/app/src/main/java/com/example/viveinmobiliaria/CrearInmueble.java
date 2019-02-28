package com.example.viveinmobiliaria;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viveinmobiliaria.Generator.ServiceGenerator;
import com.example.viveinmobiliaria.Geography.Data.GeographySpain;
import com.example.viveinmobiliaria.Geography.Geocode;
import com.example.viveinmobiliaria.Geography.Selector.GeographyListener;
import com.example.viveinmobiliaria.Geography.Selector.GeographySelector;
import com.example.viveinmobiliaria.Model.Category;
import com.example.viveinmobiliaria.Model.ResponseContainer;
import com.example.viveinmobiliaria.Services.CategoryService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearInmueble extends AppCompatActivity implements GeographyListener {
    private TextView tvRegion, tvProvincia, tvMunicipio;
    private EditText editText_title_create, editText_descripcion_create, editText_price_create, editText_CP_create, editText_direccion_create, editText_rooms_create;
    private Button button_ubicacion, button_create_inmueble;
    private Spinner spinner_category;
    List<Category> categories;
    private String direccionCompleta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_inmueble);

        findviews();

        events();

        llamadaApiParaConseguirCategorias();



    }

    private void llamadaApiParaConseguirCategorias() {

        CategoryService service = ServiceGenerator.createService(CategoryService.class);
        Call<ResponseContainer<Category>> call = service.getCategories();

        call.enqueue(new Callback<ResponseContainer<Category>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Category>> call, Response<ResponseContainer<Category>> response) {
                if (response.code() != 200) {
                    Toast.makeText(CrearInmueble.this, "Fallo al traer categorias", Toast.LENGTH_SHORT).show();
                } else {
                    categories = response.body().getRows() ;

                    ArrayAdapter<Category> adapter =
                            new ArrayAdapter<>(CrearInmueble.this, android.R.layout.simple_spinner_dropdown_item, categories);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner_category.setAdapter(adapter);
                    spinner_category.setSelection(categories.size() - 1);


                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<Category>> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(CrearInmueble.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public String getLocation(String direccion) throws IOException {
        String loc = Geocode.getLatLong(CrearInmueble.this, direccion);
        return loc;
    }

    private void findviews() {
        button_ubicacion = findViewById(R.id.button_ubicacion);
        tvRegion = findViewById(R.id.tvRegion);
        tvProvincia = findViewById(R.id.tvProvincia);
        tvMunicipio = findViewById(R.id.tvMunicipio);
        spinner_category = findViewById(R.id.spinner_category);
        button_create_inmueble = findViewById(R.id.button_create_inmueble);
        editText_title_create = findViewById(R.id.editText_title_create);
        editText_descripcion_create = findViewById(R.id.editText_descripcion_create);
        editText_price_create = findViewById(R.id.editText_price_create);
        editText_CP_create = findViewById(R.id.editText_CP_create);
        editText_direccion_create = findViewById(R.id.editText_direccion_create);
        editText_rooms_create = findViewById(R.id.editText_rooms_create);
    }



    private void events() {
        button_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeographySelector gs = new GeographySelector(CrearInmueble.this);
                gs.setOnGeograpySelectedListener(CrearInmueble.this);
                FragmentManager fm = getSupportFragmentManager();
                gs.show(fm, "geographySelector");
            }
        });

        button_create_inmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirInmueble();
            }
        });
    }

    private void subirInmueble() {

    }


    @Override
    public void onGeographySelected(Map<String, String> hm) {
        tvRegion.setText(hm.get(GeographySpain.REGION));
        tvProvincia.setText(hm.get(GeographySpain.PROVINCIA));
        tvMunicipio.setText(hm.get(GeographySpain.MUNICIPIO));
    }
}
