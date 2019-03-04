package com.example.viveinmobiliaria.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.viveinmobiliaria.Adapters.MyeditarPhotoRecyclerViewAdapter;
import com.example.viveinmobiliaria.EditarPhoto;
import com.example.viveinmobiliaria.Generator.ServiceGenerator;
import com.example.viveinmobiliaria.InmuebleDetallado;
import com.example.viveinmobiliaria.Listener.EditarPhotosListener;
import com.example.viveinmobiliaria.Model.Photo;
import com.example.viveinmobiliaria.Model.Propiedad;
import com.example.viveinmobiliaria.Model.ResponseContainer;
import com.example.viveinmobiliaria.Model.ResponseContainerNoList;
import com.example.viveinmobiliaria.R;
import com.example.viveinmobiliaria.Services.PhotoService;
import com.example.viveinmobiliaria.Services.PropertiesService;
import com.example.viveinmobiliaria.ViewModels.SubirFotoViewModel;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class editarPhotoFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2    ;
    private EditarPhotosListener mListener;
    private Propiedad fotos;
    private MyeditarPhotoRecyclerViewAdapter adapter;
    private Context cxt;


    public editarPhotoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static editarPhotoFragment newInstance(int columnCount) {
        editarPhotoFragment fragment = new editarPhotoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editarphoto_list, container, false);

        EditarPhoto activity = (EditarPhoto) getActivity();
        String id = activity.getIdProperty();

        // Set the adapter
        if (view instanceof RecyclerView) {
            cxt = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(cxt));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(cxt, mColumnCount));
            }

            final PropertiesService service = ServiceGenerator.createService(PropertiesService.class);
            Call<ResponseContainerNoList<Propiedad>> call = service.getOneProperty(id);

            call.enqueue(new Callback<ResponseContainerNoList<Propiedad>>() {
                @Override
                public void onResponse(Call<ResponseContainerNoList<Propiedad>> call, Response<ResponseContainerNoList<Propiedad>> response) {
                    if (response.code() != 200) {
                        Toast.makeText(getContext(), "Fallo al traer propiedades", Toast.LENGTH_SHORT).show();
                    } else {
                        fotos = response.body().getRows();

                        adapter = new MyeditarPhotoRecyclerViewAdapter(
                                cxt,
                                fotos,
                                mListener
                        );

                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<ResponseContainerNoList<Propiedad>> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });


            SubirFotoViewModel subirFotoViewModel = ViewModelProviders.of(getActivity()).get(SubirFotoViewModel.class);

            subirFotoViewModel.getAll().observe(getActivity(), new Observer<Propiedad>() {
                @Override
                public void onChanged(@Nullable Propiedad propiedad) {
                    EditarPhoto activity = (EditarPhoto) getActivity();
                    String id = activity.getIdProperty();
                    final PropertiesService service = ServiceGenerator.createService(PropertiesService.class);
                    Call<ResponseContainerNoList<Propiedad>> call = service.getOneProperty(id);

                    call.enqueue(new Callback<ResponseContainerNoList<Propiedad>>() {
                        @Override
                        public void onResponse(Call<ResponseContainerNoList<Propiedad>> call, Response<ResponseContainerNoList<Propiedad>> response) {
                            if (response.code() != 200) {
                                Toast.makeText(getContext(), "Fallo al traer propiedades", Toast.LENGTH_SHORT).show();
                            } else {
                                fotos = response.body().getRows();

                                adapter = new MyeditarPhotoRecyclerViewAdapter(
                                        cxt,
                                        fotos,
                                        mListener
                                );

                                recyclerView.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseContainerNoList<Propiedad>> call, Throwable t) {
                            Log.e("NetworkFailure", t.getMessage());
                            Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });


        }


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EditarPhotosListener) {
            mListener = (EditarPhotosListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement EditarPhotosListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
