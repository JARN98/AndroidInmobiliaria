package com.example.viveinmobiliaria.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.viveinmobiliaria.Generator.ServiceGenerator;
import com.example.viveinmobiliaria.Generator.UtilToken;
import com.example.viveinmobiliaria.Generator.UtilUser;
import com.example.viveinmobiliaria.Inmuebles;
import com.example.viveinmobiliaria.R;
import com.example.viveinmobiliaria.Responses.LoginResponse;
import com.example.viveinmobiliaria.Services.SessionService;
import com.example.viveinmobiliaria.SessionActivity;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private EditText editText_email_login, editText_passwordrep_login;
    private Button button_login, button_registroAlogin;
    private LoginInterface loginInterface;
    ProgressDialog progressDialog;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        findViews(view);

        events();
        return view;
    }

    private void events() {
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                doLogin();
            }
        });


        button_registroAlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginInterface.navegarRegistro();
            }
        });
    }


    private void findViews(View view) {
        editText_email_login = view.findViewById(R.id.editText_email_login);
        editText_passwordrep_login = view.findViewById(R.id.editText_passwordrep_login);
        button_login = view.findViewById(R.id.button_login);
        button_registroAlogin = view.findViewById(R.id.button_registroAlogin);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginInterface) {
            loginInterface = (LoginInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loginInterface = null;
    }


    public void doLogin() {

        String email = editText_email_login.getText().toString();
        String password = editText_passwordrep_login.getText().toString();

        String credentials = Credentials.basic(email, password);


        if (validarString(email) && validarString(password)) {

            SessionService service = ServiceGenerator.createService(SessionService.class);
            Call<LoginResponse> call = service.doLogin(credentials);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.code() != 201) {
                        // error
                        Log.e("RequestError", response.message());
                        Toast.makeText(getContext(), "Email o contraseña incorrecto", Toast.LENGTH_SHORT).show();

                    } else {

                        UtilToken.setToken(getActivity(), response.body().getToken());
                        UtilUser.setUserInfo(getActivity(), response.body().getUser());

                        startActivity(new Intent(getActivity(), Inmuebles.class));
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        } else {

            Toast.makeText(getContext(), "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
    }

    Boolean validarString(String texto) {
        return texto != null && texto.trim().length() > 0;
    }


    public interface LoginInterface {
        void navegarRegistro();
    }

}
