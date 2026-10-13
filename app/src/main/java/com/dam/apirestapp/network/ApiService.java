package com.dam.apirestapp.network;

import com.dam.apirestapp.models.LoginRequest;
import com.dam.apirestapp.models.LoginResponse;
import com.dam.apirestapp.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("products")
    Call<List<Product>> getProducts(@Header("Authorization") String token);
}
