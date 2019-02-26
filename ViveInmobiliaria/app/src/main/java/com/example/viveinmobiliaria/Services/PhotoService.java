package com.example.viveinmobiliaria.Services;

import com.example.viveinmobiliaria.Model.Photo;
import com.example.viveinmobiliaria.Model.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhotoService {
    @GET("/photos")
    Call<ResponseContainer<Photo>> getPhotoOfProperty(@Query("propertyId") String propertyId);
}
