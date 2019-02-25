package com.example.viveinmobiliaria.Services;

import com.example.viveinmobiliaria.Model.Propiedad;
import com.example.viveinmobiliaria.Model.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PropertiesService {
    @GET("/properties")
    Call<ResponseContainer<Propiedad>> getProperties();

    @GET("/properties/favs")
    Call<ResponseContainer<Propiedad>> getFavProperties();

    @GET("/properties/mine")
    Call<ResponseContainer<Propiedad>> getMineProperties();



}
