package com.example.viveinmobiliaria.Services;

import com.example.viveinmobiliaria.Model.Propiedad;
import com.example.viveinmobiliaria.Model.ResponseContainer;
import com.example.viveinmobiliaria.Model.ResponseContainerNoList;
import com.example.viveinmobiliaria.Model.addFavouriteDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PropertiesService {
    @GET("/properties")
    Call<ResponseContainer<Propiedad>> getProperties();

    @GET("/properties/fav")
    Call<ResponseContainer<Propiedad>> getFavProperties();

    @GET("/properties/mine")
    Call<ResponseContainer<Propiedad>> getMineProperties();

    @POST("/properties/fav/{id}")
    Call<addFavouriteDto> addFavPropertie(@Path("id") String id);

    @GET("/properties/{id}")
    Call<ResponseContainerNoList<Propiedad>> getOneProperty(@Path("id") String id);



}
