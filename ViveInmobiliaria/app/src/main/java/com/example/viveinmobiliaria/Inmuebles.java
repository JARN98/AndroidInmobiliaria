package com.example.viveinmobiliaria;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.viveinmobiliaria.Fragments.LoginFragment;
import com.example.viveinmobiliaria.Fragments.listaInmueblesFavoritos;
import com.example.viveinmobiliaria.Fragments.listaInmueblesFragment;
import com.example.viveinmobiliaria.Listener.InmueblesListener;

public class Inmuebles extends AppCompatActivity implements InmueblesListener {

    private FrameLayout contenedor;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contenedor, new listaInmueblesFragment())
                            .commit();
                    return true;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contenedor, new listaInmueblesFavoritos())
                            .commit();
                    return true;
                case R.id.navigation_notifications:

                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inmuebles);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedor, new listaInmueblesFragment())
                .commit();

        contenedor = findViewById(R.id.contenedor);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void verCasa(String casa) {

    }
}
