package com.example.viveinmobiliaria.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.viveinmobiliaria.Generator.ServiceGenerator;
import com.example.viveinmobiliaria.Generator.UtilUser;
import com.example.viveinmobiliaria.Model.PassDto;
import com.example.viveinmobiliaria.Model.User;
import com.example.viveinmobiliaria.R;
import com.example.viveinmobiliaria.Services.SessionService;
import com.example.viveinmobiliaria.SessionActivity;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {
    /*    private PerfilViewModel mViewModel;*/
    private TextView tvNombre, tvEmail;
    private EditText etPass, etNewPass, etRepeatPass;
    private ImageView ivImagen;
    private Button btnLogout, btnCambiarPass, btnCambiar;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        tvNombre = view.findViewById(R.id.textViewNombrePerfil);
        tvEmail = view.findViewById(R.id.textViewEmailPerfil);
        ivImagen = view.findViewById(R.id.imageViewPerfil);
        btnLogout = view.findViewById(R.id.buttonLogout);
        btnCambiarPass = view.findViewById(R.id.buttonCambiarPass);
        btnCambiar = view.findViewById(R.id.buttonUpdatePass);
        etPass = view.findViewById(R.id.editTextPassPerfil);
        etNewPass = view.findViewById(R.id.editTextNuevaPassPerfil);
        etRepeatPass = view.findViewById(R.id.editTextRepetirPass);

        btnCambiar.setVisibility(View.GONE);
        etPass.setVisibility(View.GONE);
        etNewPass.setVisibility(View.GONE);
        etRepeatPass.setVisibility(View.GONE);

        tvNombre.setText(UtilUser.getNombre(getActivity()));
        tvEmail.setText(UtilUser.getEmail(getActivity()));

        Glide
                .with(getActivity())
                .load(UtilUser.getImagen(getActivity()))
                .into(ivImagen);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        btnCambiarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCambiar.setVisibility(View.VISIBLE);
                etPass.setVisibility(View.VISIBLE);
                etNewPass.setVisibility(View.VISIBLE);
                etRepeatPass.setVisibility(View.VISIBLE);
            }
        });

        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etNewPass.getText().toString().equals(etRepeatPass.getText().toString()))
                    Toast.makeText(getActivity(), "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();

                else
                    updatePass();
            }
        });


        return view;
    }

/*    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PerfilViewModel.class);
        // TODO: Use the ViewModel
    }*/

    public void logout() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                UtilUser.clearSharedPreferences(getActivity());
                navegarSessionActivity();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public void navegarSessionActivity() {
        startActivity(new Intent(getActivity(), SessionActivity.class));
    }

    public void updatePass() {


        String credentials = Credentials.basic(UtilUser.getEmail(getActivity()), etPass.getText().toString());
        String idUser = UtilUser.getId(getActivity());
        PassDto newPass = new PassDto(etNewPass.getText().toString());
        SessionService service = ServiceGenerator.createService(SessionService.class);
        Call<User> call = service.updatePass(credentials, idUser, newPass);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 401) {
                    Toast.makeText(getActivity(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
                if (response.code() != 200) {
                    // error
                    Log.e("RequestError", response.message());

                } else {
                    logout();
                    navegarSessionActivity();
                    Toast.makeText(getActivity(), "Contraseña cambiada", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
