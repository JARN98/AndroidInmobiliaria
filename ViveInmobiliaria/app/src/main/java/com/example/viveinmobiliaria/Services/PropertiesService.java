package com.example.viveinmobiliaria.Services;

import com.example.viveinmobiliaria.Model.CrearPropiedadDto;
import com.example.viveinmobiliaria.Model.PropiedaFavoritasDto;
import com.example.viveinmobiliaria.Model.Propiedad;
import com.example.viveinmobiliaria.Model.ResponseContainer;
import com.example.viveinmobiliaria.Model.ResponseContainerNoList;
import com.example.viveinmobiliaria.Model.addFavouriteDto;
import com.example.viveinmobiliaria.Responses.CrearPropiedadResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface PropertiesService {

    @GET("/properties")
    Call<ResponseContainer<Propiedad>> getPropertiesNear(@Query("near") String near);

    @GET("/properties")
    Call<ResponseContainer<Propiedad>> getProperties();

    @GET("/properties/fav")
    Call<ResponseContainer<Propiedad>> getFavProperties();

    @GET("/properties/auth")
    Call<ResponseContainer<Propiedad>> getAuthProperties();

    @GET("/properties/mine")
    Call<ResponseContainer<PropiedaFavoritasDto>> getMineProperties();

    @POST("/properties/fav/{id}")
    Call<addFavouriteDto> addFavPropertie(@Path("id") String id);

    @DELETE("/properties/fav/{id}")
    Call<addFavouriteDto> removeFavPropertie(@Path("id") String id);

    @GET("/properties/{id}")
    Call<ResponseContainerNoList<Propiedad>> getOneProperty(@Path("id") String id);

    @POST("/properties")
    Call<CrearPropiedadResponse> addProperty(@Body CrearPropiedadDto crearPropiedadDto);

    @PUT("/properties/{id}")
    Call<CrearPropiedadResponse> editProperty(@Path("id") String id, @Body CrearPropiedadDto crearPropiedadDto);

    @DELETE("/properties/{id}")
    Call<ResponseContainerNoList<Propiedad>> deleteProperty(@Path("id") String id);

    @GET("/properties")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Call<ResponseContainer<Propiedad>> getProperties(
            @QueryMap Map<String, String> options
    );



}
