package com.example.viveinmobiliaria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.viveinmobiliaria.Fragments.LoginFragment;
import com.example.viveinmobiliaria.Fragments.RegistroFragment;

public class SessionActivity extends AppCompatActivity implements LoginFragment.LoginInterface, RegistroFragment.RegistroInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.sessionContainer, new LoginFragment())
                .commit();
    }

    @Override
    public void navegarRegistro() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.sessionContainer, new RegistroFragment())
                .commit();
    }

    @Override
    public void navegarLogin() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.sessionContainer, new LoginFragment())
                .commit();
    }
}
