package com.example.viveinmobiliaria;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viveinmobiliaria.Fragments.LoginFragment;
import com.example.viveinmobiliaria.Fragments.PerfilFragment;
import com.example.viveinmobiliaria.Fragments.listaInmueblesDelUsuarioFragment;
import com.example.viveinmobiliaria.Fragments.listaInmueblesFavoritos;
import com.example.viveinmobiliaria.Fragments.listaInmueblesFragment;
import com.example.viveinmobiliaria.Generator.UtilUser;
import com.example.viveinmobiliaria.Listener.InmueblesListener;

public class Inmuebles extends AppCompatActivity implements InmueblesListener {

    private FrameLayout contenedor;
    private ImageView imageView_fav;
    private Menu menu;
    private FloatingActionButton fab;
    private boolean filtro;

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
                    fab.show();
                    return true;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contenedor, new listaInmueblesFavoritos())
                            .commit();
                    fab.show();
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contenedor, new listaInmueblesDelUsuarioFragment())
                            .commit();
                    fab.show();
                    return true;
                case R.id.navigation_cuenta:
                    if (UtilUser.getEmail(Inmuebles.this) == null) {
                        Intent i = new Intent(Inmuebles.this, SessionActivity.class);
                        startActivity(i);
                    } else {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.contenedor, new PerfilFragment())
                                .commit();
                        fab.hide();
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
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        menu = navigation.getMenu();

        contenedor = findViewById(R.id.contenedor);
        fab = findViewById(R.id.fab);

/*        Intent intent = getIntent();

        filtro = intent.getStringExtra("id");*/



        events();

    }


    private void events() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Inmuebles.this, CrearInmueble.class);
                startActivity(i);
            }
        });
    }

    public boolean isFiltro() {
        return filtro;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        switch (id) {
            case R.id.action_map:
                Intent i = new Intent(Inmuebles.this, Mapa.class);
                startActivity(i);
                return true;
            case R.id.filter_list:
                Intent in = new Intent(Inmuebles.this, Filtros.class);
                startActivity(in);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void verCasa(String casa) {

    }

    @Override
    public void addFavProperties(String id) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor, new listaInmueblesFragment())
                .commit();

        ocultarBotonesParaAnonimos(menu);

        Bundle b = new Bundle();
        b = getIntent().getExtras();

        if (b != null) {
            if (!b.getString("filtro").isEmpty()) {
                filtro = true;
            }
        }

        MenuItem inicio = menu.findItem(R.id.navigation_home);
        inicio.setChecked(true);
    }

    public void ocultarBotonesParaAnonimos(Menu menu) {
        if (UtilUser.getEmail(this) == null) {
            MenuItem item = menu.findItem(R.id.navigation_dashboard);
            item.setVisible(false);
            MenuItem item2 = menu.findItem(R.id.navigation_notifications);
            item2.setVisible(false);

            fab.hide();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar2, menu);
        return true;
    }
}
