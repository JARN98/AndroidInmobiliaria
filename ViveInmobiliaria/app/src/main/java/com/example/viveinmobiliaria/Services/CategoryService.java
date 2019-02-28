package com.example.viveinmobiliaria.Services;

import com.example.viveinmobiliaria.Model.Category;
import com.example.viveinmobiliaria.Model.ResponseContainer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {

    @GET("/categories")
    Call<ResponseContainer<Category>> getCategories();
}
