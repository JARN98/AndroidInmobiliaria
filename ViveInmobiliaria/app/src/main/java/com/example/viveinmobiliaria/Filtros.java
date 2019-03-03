package com.example.viveinmobiliaria;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.viveinmobiliaria.Fragments.FiltrosF;
import com.example.viveinmobiliaria.Fragments.listaInmueblesFragment;
import com.example.viveinmobiliaria.Generator.ServiceGenerator;
import com.example.viveinmobiliaria.Model.Propiedad;
import com.example.viveinmobiliaria.Model.ResponseContainer;
import com.example.viveinmobiliaria.Services.PropertiesService;
import com.example.viveinmobiliaria.ViewModels.AplicarFiltroViewModel;
import com.example.viveinmobiliaria.ViewModels.FiltroViewModel;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Filtros extends AppCompatActivity implements FiltrosF.OnFragmentInteractionListener {
    private EditText editText_filtrar_city, editText_filtrar_province, editText_filtrar_address, editText_filter_minpric, editText_filter_maxprice, editText_filter_minRooms,
            editText_filter_maxRooms, editText_filter_minSize, editText_filter_maxSize, editText_filter_maxdistance;
    private Button button_filter_myubi, button_filter_cancel, button_filter_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.filterContainer, new FiltrosF())
                .commit();

        findids();

        events();
    }

    private void events() {
        final Map<String, String> data = new HashMap<>();


        button_filter_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*filtrar(data);*/
                AplicarFiltroViewModel aplicarFiltroViewModel = ViewModelProviders.of(Filtros.this).get(AplicarFiltroViewModel.class);

                aplicarFiltroViewModel.getAll().setValue(true);
            }
        });
    }

   /* private void filtrar(Map<String, String> data) {
        if (editText_filtrar_city != null) {
            data.put("city", editText_filtrar_city.getText().toString());
        }

        if (editText_filtrar_province != null) {
            data.put("province", editText_filtrar_province.getText().toString());
        }

        if (editText_filtrar_address != null) {
            data.put("address", editText_filtrar_address.getText().toString());
        }

        if (editText_filter_minpric != null && Integer.valueOf(editText_filter_minpric.getText().toString()) > 0) {
            data.put("min_price ", editText_filter_minpric.getText().toString());
        }

        if (editText_filter_maxprice != null && Integer.valueOf(editText_filter_maxprice.getText().toString()) > 0) {
            data.put("max_price ", editText_filter_maxprice.getText().toString());
        }


        if (editText_filter_minRooms != null && Integer.valueOf(editText_filter_minRooms.getText().toString()) > 0 &&
                editText_filter_maxRooms != null && Integer.valueOf(editText_filter_maxRooms.getText().toString()) > 0
                && Integer.valueOf(editText_filter_minRooms.getText().toString()) <= Integer.valueOf(editText_filter_maxRooms.getText().toString())) {

            int numeroMinimo = Integer.valueOf(editText_filter_minRooms.getText().toString());
            int numeroMaximo = Integer.valueOf(editText_filter_maxRooms.getText().toString());


            int[] numerosEnMedio = null;

            for (int i = 0; i < (numeroMaximo - numeroMinimo); i++) {
                numerosEnMedio[i] = (numeroMinimo - i);
            }
            String rooms = "";

            for (int i = 0; i < numerosEnMedio.length; i++) {
                rooms = rooms + "," + numerosEnMedio[i];
            }

            data.put("rooms", rooms);
        }

        if (editText_filter_minSize != null && Integer.valueOf(editText_filter_minSize.getText().toString()) > 0) {
            data.put("min_size", editText_filter_minSize.getText().toString());
        }

        if (editText_filter_maxSize != null && Integer.valueOf(editText_filter_maxSize.getText().toString()) > 0) {
            data.put("min_size", editText_filter_maxSize.getText().toString());
        }

        PropertiesService service = ServiceGenerator.createService(PropertiesService.class);

        Call<ResponseContainer<Propiedad>> call = service.getProperties(data);

        call.enqueue(new Callback<ResponseContainer<Propiedad>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Propiedad>> call, Response<ResponseContainer<Propiedad>> response) {
                FiltroViewModel filtroViewModel = ViewModelProviders.of(Filtros.this)
                        .get(FiltroViewModel.class);

                filtroViewModel.selectPropiedadList(response.body().getRows());

                startActivity(new Intent(Filtros.this, Inmuebles.class).putExtra("filtro", "si"));
            }

            @Override
            public void onFailure(Call<ResponseContainer<Propiedad>> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(Filtros.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });


    }*/

    private void findids() {
        /*editText_filtrar_city = findViewById(R.id.editText_filtrar_city);
        editText_filtrar_province = findViewById(R.id.editText_filtrar_province);
        editText_filtrar_address = findViewById(R.id.editText_filtrar_address);
        editText_filter_minpric = findViewById(R.id.editText_filter_minprice);
        editText_filter_maxprice = findViewById(R.id.editText_filter_maxprice);
        editText_filter_minRooms = findViewById(R.id.editText_filter_minRooms);
        editText_filter_maxRooms = findViewById(R.id.editText_filter_maxRooms);
        editText_filter_minSize = findViewById(R.id.editText_filter_minSize);
        editText_filter_maxSize = findViewById(R.id.editText_filter_maxSize);
        editText_filter_maxdistance = findViewById(R.id.editText_filter_maxdistance);

        button_filter_myubi = findViewById(R.id.button_filter_myubi);*/
        button_filter_cancel = findViewById(R.id.button_filter_cancel);
        button_filter_ok = findViewById(R.id.button_filter_ok);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
