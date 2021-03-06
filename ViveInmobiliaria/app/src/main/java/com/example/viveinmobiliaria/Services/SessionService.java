package com.example.viveinmobiliaria.Services;

import com.example.viveinmobiliaria.Model.PassDto;
import com.example.viveinmobiliaria.Model.User;
import com.example.viveinmobiliaria.Model.UserDto;
import com.example.viveinmobiliaria.Responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SessionService {

    @POST("/auth")
    Call<LoginResponse> doLogin(@Header("Authorization") String authorization);

    @POST("/users")
    Call<LoginResponse> doRegister(@Body UserDto user);

    @PUT("/users/{id}/password")
    Call<User> updatePass(@Header("Authorization") String authorization, @Path("id") String id, @Body PassDto pass);
}
