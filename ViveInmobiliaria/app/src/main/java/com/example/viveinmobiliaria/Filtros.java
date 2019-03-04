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


public class Filtros extends AppCompatActivity implements FiltrosF.OnFragmentInteractionListener {
    private Button button_filter_cancel, button_filter_ok;

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
                finish();
            }
        });

        button_filter_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findids() {
        button_filter_cancel = findViewById(R.id.button_filter_cancel);
        button_filter_ok = findViewById(R.id.button_filter_ok);

    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
