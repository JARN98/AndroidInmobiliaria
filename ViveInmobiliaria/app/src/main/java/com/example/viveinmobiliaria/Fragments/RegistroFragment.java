package com.example.viveinmobiliaria.Fragments;


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
import com.example.viveinmobiliaria.Model.User;
import com.example.viveinmobiliaria.Model.UserDto;
import com.example.viveinmobiliaria.R;
import com.example.viveinmobiliaria.Responses.LoginResponse;
import com.example.viveinmobiliaria.Services.SessionService;

import okhttp3.Credentials;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroFragment extends Fragment {
    private EditText editText_email_registro, editText_password_registro, editText_passwordrep_login;
    private Button button_registro, button_registroAlogin;
    private RegistroInterface registroInterface;


    public RegistroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_registro, container, false);

        findViews(view);
        events();

        return view;
    }

    private void events() {

        button_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });

        button_registroAlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registroInterface.navegarLogin();
            }
        });
    }

    private void findViews(View view) {
        editText_email_registro = view.findViewById(R.id.editText_email_registro);
        editText_password_registro = view.findViewById(R.id.editText_password_registro);
        editText_passwordrep_login = view.findViewById(R.id.editText_passwordrep_login);
        button_registro = view.findViewById(R.id.button_registro);
        button_registroAlogin = view.findViewById(R.id.button_registroAlogin);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RegistroInterface) {
            registroInterface = (RegistroInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        registroInterface = null;
    }


    public void doRegister() {

/*        if (!editText_password_registro.equals(editText_passwordrep_login) || editText_password_registro != editText_passwordrep_login) {
            Toast.makeText(getContext(), "Las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
            return;
        }*/

        String email = editText_email_registro.getText().toString();
        String password = editText_passwordrep_login.getText().toString();

        UserDto usuarioARegistrar = new UserDto(email, password, email);

        if (validarString(email) && validarString(password)) {

            SessionService service = ServiceGenerator.createService(SessionService.class);
            Call<LoginResponse> call = service.doRegister(usuarioARegistrar);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.code() != 201) {
                        // error
                        Log.e("RequestError", response.message());
                        Toast.makeText(getContext(), "Registro fallido", Toast.LENGTH_SHORT).show();

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
    }

    Boolean validarString(String texto) {
        return texto != null && texto.trim().length() > 0;
    }

    public interface RegistroInterface {
        void navegarLogin();
    }

}
