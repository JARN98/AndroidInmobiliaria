package com.example.viveinmobiliaria.Services;

import com.example.viveinmobiliaria.Model.Photo;
import com.example.viveinmobiliaria.Model.ResponseContainer;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface PhotoService {
    @GET("/photos")
    Call<ResponseContainer<Photo>> getPhotoOfProperty(@Query("propertyId") String propertyId);

    @Multipart
    @POST("/photos")
    Call<Photo> addPhoto(@Part MultipartBody.Part photo,
                                   @Part("propertyId") RequestBody propertyId);
}
