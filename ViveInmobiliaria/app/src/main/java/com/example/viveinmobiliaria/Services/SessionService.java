package com.example.viveinmobiliaria.Services;

import com.example.viveinmobiliaria.Model.User;
import com.example.viveinmobiliaria.Model.UserDto;
import com.example.viveinmobiliaria.Responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SessionService {

    @POST("/auth")
    Call<LoginResponse> doLogin(@Header("Authorization") String authorization);

    @POST("/users")
    Call<LoginResponse> doRegister(@Body UserDto user);
}
