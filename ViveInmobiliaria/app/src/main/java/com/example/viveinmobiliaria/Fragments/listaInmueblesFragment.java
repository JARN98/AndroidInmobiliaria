package com.example.viveinmobiliaria.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.viveinmobiliaria.Adapters.MylistaInmueblesRecyclerViewAdapter;
import com.example.viveinmobiliaria.Generator.ServiceGenerator;
import com.example.viveinmobiliaria.Listener.InmueblesListener;
import com.example.viveinmobiliaria.Model.Propiedad;
import com.example.viveinmobiliaria.Model.ResponseContainer;
import com.example.viveinmobiliaria.R;
import com.example.viveinmobiliaria.Services.PropertiesService;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class listaInmueblesFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private InmueblesListener mListener;
    private List<Propiedad> listaPropiedades;
    private MylistaInmueblesRecyclerViewAdapter adapter;
    private Context cxt;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public listaInmueblesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static listaInmueblesFragment newInstance(int columnCount) {
        listaInmueblesFragment fragment = new listaInmueblesFragment();
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
        View view = inflater.inflate(R.layout.fragment_listainmuebles_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            cxt = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(cxt));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(cxt, mColumnCount));
            }
                listaPropiedades = new ArrayList<>();

                PropertiesService service = ServiceGenerator.createService(PropertiesService.class);
                Call<ResponseContainer<Propiedad>> call = service.getProperties();

                call.enqueue(new Callback<ResponseContainer<Propiedad>>() {
                    @Override
                    public void onResponse(Call<ResponseContainer<Propiedad>> call, Response<ResponseContainer<Propiedad>> response) {
                        if (response.code() != 200) {
                            Toast.makeText(getActivity(), "Error en petición", Toast.LENGTH_SHORT).show();
                        } else {
                            listaPropiedades = response.body().getRows();

                            adapter = new MylistaInmueblesRecyclerViewAdapter(
                                    cxt,
                                    listaPropiedades,
                                    mListener
                            );
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseContainer<Propiedad>> call, Throwable t) {
                        Log.e("NetworkFailure", t.getMessage());
                        Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });

        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InmueblesListener) {
            mListener = (InmueblesListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
