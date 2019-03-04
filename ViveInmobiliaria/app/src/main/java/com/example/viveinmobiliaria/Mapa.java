package com.example.viveinmobiliaria;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.viveinmobiliaria.Generator.ServiceGenerator;
import com.example.viveinmobiliaria.Model.Propiedad;
import com.example.viveinmobiliaria.Model.ResponseContainer;
import com.example.viveinmobiliaria.Services.PropertiesService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {
    private static final String TODO = "";
    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap mMap;
    private PropertiesService service;
    private LocationManager locManager;
    private Location loc;
    private List<Propiedad> propiedades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ActivityCompat.requestPermissions(Mapa.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
/*            Toast.makeText(this, "Lo siento, no puede ver el mapa", Toast.LENGTH_SHORT).show();
            finish();*/
            return;
        }
        locManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            loc = location;
                            LatLng casa = new LatLng(loc.getLatitude(), loc.getLongitude());
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(casa, 13));

                            inmueblesCercanos();
                        }
                    }
                });

    }

    public void inmueblesCercanos() {
        PropertiesService service = ServiceGenerator.createService(PropertiesService.class);

        Call<ResponseContainer<Propiedad>> call1 = service.getProperties();

        call1.enqueue(new Callback<ResponseContainer<Propiedad>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Propiedad>> call, Response<ResponseContainer<Propiedad>> response) {
                if (response.isSuccessful()) {
                    propiedades = response.body().getRows();

                    aniadirMarkers(propiedades);
                } else {
                    Toast.makeText(Mapa.this, "Fallo al cargar los inmuebles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<Propiedad>> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(Mapa.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void aniadirMarkers(List<Propiedad> propiedades) {
        for (int i = 0; i < propiedades.size(); i++) {
            String[] parts = propiedades.get(i).getLoc().split(",");
            double lat = Double.valueOf(parts[0]);
            double lon = Double.valueOf(parts[1]);

            mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(propiedades.get(i).getTitle()));

        }
    }
}
