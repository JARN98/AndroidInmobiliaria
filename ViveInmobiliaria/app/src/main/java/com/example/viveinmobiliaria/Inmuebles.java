package com.example.viveinmobiliaria;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viveinmobiliaria.Fragments.LoginFragment;
import com.example.viveinmobiliaria.Fragments.listaInmueblesDelUsuarioFragment;
import com.example.viveinmobiliaria.Fragments.listaInmueblesFavoritos;
import com.example.viveinmobiliaria.Fragments.listaInmueblesFragment;
import com.example.viveinmobiliaria.Generator.UtilUser;
import com.example.viveinmobiliaria.Listener.InmueblesListener;

public class Inmuebles extends AppCompatActivity implements InmueblesListener {

    private FrameLayout contenedor;
    private ImageView imageView_fav;

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
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contenedor, new listaInmueblesDelUsuarioFragment())
                            .commit();
                    return true;
                case R.id.navigation_cuenta:
                    if(UtilUser.getEmail(Inmuebles.this) == null) {
                        Intent i =  new Intent(Inmuebles.this, SessionActivity.class);
                        startActivity(i);
                    }

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

    @Override
    public void addFavProperties(String id) {
    }
}
