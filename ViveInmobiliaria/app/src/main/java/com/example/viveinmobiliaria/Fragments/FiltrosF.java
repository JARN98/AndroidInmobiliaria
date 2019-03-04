package com.example.viveinmobiliaria.Fragments;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.viveinmobiliaria.Filtros;
import com.example.viveinmobiliaria.Generator.ServiceGenerator;
import com.example.viveinmobiliaria.Generator.ServiceGeneratorNear;
import com.example.viveinmobiliaria.Inmuebles;
import com.example.viveinmobiliaria.Mapa;
import com.example.viveinmobiliaria.Model.Propiedad;
import com.example.viveinmobiliaria.Model.ResponseContainer;
import com.example.viveinmobiliaria.R;
import com.example.viveinmobiliaria.Services.PropertiesService;
import com.example.viveinmobiliaria.ViewModels.AplicarFiltroViewModel;
import com.example.viveinmobiliaria.ViewModels.FiltroViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FiltrosF.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FiltrosF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FiltrosF extends Fragment {
    private EditText editText_filtrar_city, editText_filtrar_province, editText_filtrar_address, editText_filter_minpric, editText_filter_maxprice, editText_filter_minRooms,
            editText_filter_maxRooms, editText_filter_minSize, editText_filter_maxSize, editText_filter_maxdistance;
    private Button button_filter_myubi;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationManager locManager;
    Location loc;

    private OnFragmentInteractionListener mListener;

    public FiltrosF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FiltrosF.
     */
    // TODO: Rename and change types and number of parameters
    public static FiltrosF newInstance(String param1, String param2) {
        FiltrosF fragment = new FiltrosF();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filtros, container, false);

        findids(view);

        AplicarFiltroViewModel aplicarFiltroViewModel = ViewModelProviders.of((Filtros) getContext()).get(AplicarFiltroViewModel.class);

        aplicarFiltroViewModel.getAll().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Map<String, String> data = new HashMap<>();

                filtrar(data);
            }
        });

        button_filter_myubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String, String> data = new HashMap<>();
                /*Toast.makeText(getContext(), "WOO", Toast.LENGTH_SHORT).show();*/
                locManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                } else {
                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    loc = location;

                                    filtrarPorUbi(data);

                                }
                            });
                }

            }
        });


        return view;
    }

    private void filtrarPorUbi(Map<String, String> data) {

        data.put("near", loc.getLongitude() + "," + loc.getLatitude());

        if (!editText_filter_maxdistance.getText().toString().isEmpty()) {
            data.put("min_distance", "0");
            data.put("max_distance", editText_filter_maxdistance.getText().toString());
        }

        startActivity(new Intent(getContext(), Inmuebles.class).putExtra("data", (Serializable) data));

    }



    private void filtrar(Map<String, String> data) {

        if (!editText_filtrar_city.getText().toString().isEmpty()) {
            data.put("city", editText_filtrar_city.getText().toString());
        }

        if (!editText_filtrar_province.getText().toString().isEmpty()) {

            data.put("province", editText_filtrar_province.getText().toString());


        }

        if (!editText_filtrar_address.getText().toString().isEmpty()) {
            data.put("address", editText_filtrar_address.getText().toString());
        }

        if (!editText_filter_minpric.getText().toString().isEmpty()) {
            data.put("min_price", editText_filter_minpric.getText().toString());
        }

        if (!editText_filter_maxprice.getText().toString().isEmpty()) {
            data.put("max_price", editText_filter_maxprice.getText().toString());
        }


        if (!editText_filter_minRooms.getText().toString().isEmpty() &&
                !editText_filter_maxRooms.getText().toString().isEmpty()) {
            int numeroMinimo = Integer.valueOf(editText_filter_minRooms.getText().toString());
            int numeroMaximo = Integer.valueOf(editText_filter_maxRooms.getText().toString());


            List<Integer> numerosEnMedio = new ArrayList<>();

            for (int i = 0; i < (numeroMaximo - numeroMinimo); i++) {
                numerosEnMedio.add((numeroMinimo + i));
            }
            String rooms = "";

            for (int i = 0; i < numerosEnMedio.size(); i++) {
                rooms = rooms.toString() + "," + numerosEnMedio.get(i);
            }

            data.put("rooms", rooms);

        }

        if (!editText_filter_minSize.getText().toString().isEmpty()) {
            data.put("min_size", editText_filter_minSize.getText().toString());
        }

        if (!editText_filter_maxSize.getText().toString().isEmpty()) {
            data.put("min_size", editText_filter_maxSize.getText().toString());
        }

        PropertiesService service = ServiceGenerator.createService(PropertiesService.class);


        startActivity(new Intent(getContext(), Inmuebles.class).putExtra("data", (Serializable) data));




    }

    private void findids(View view) {
        editText_filtrar_city = view.findViewById(R.id.editText_filtrar_city);
        editText_filtrar_province = view.findViewById(R.id.editText_filtrar_province);
        editText_filtrar_address = view.findViewById(R.id.editText_filtrar_address);
        editText_filter_minpric = view.findViewById(R.id.editText_filter_minprice);
        editText_filter_maxprice = view.findViewById(R.id.editText_filter_maxprice);
        editText_filter_minRooms = view.findViewById(R.id.editText_filter_minRooms);
        editText_filter_maxRooms = view.findViewById(R.id.editText_filter_maxRooms);
        editText_filter_minSize = view.findViewById(R.id.editText_filter_minSize);
        editText_filter_maxSize = view.findViewById(R.id.editText_filter_maxSize);
        editText_filter_maxdistance = view.findViewById(R.id.editText_filter_maxdistance);

        button_filter_myubi = view.findViewById(R.id.button_filter_myubi);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
