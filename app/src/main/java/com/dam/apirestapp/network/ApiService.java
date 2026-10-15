package com.dam.apirestapp.network;

import com.dam.apirestapp.models.LoginRequest;
import com.dam.apirestapp.models.LoginResponse;
import com.dam.apirestapp.models.Product;
import com.dam.apirestapp.models.RegisterRequest;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("auth/register")
    Call<Map<String, String>> register(@Body RegisterRequest request);

    @POST("auth/logout")
    Call<Map<String, String>> logout(@Header("Authorization") String token);

    @GET("products")
    Call<List<Product>> getProducts(@Header("Authorization") String token);

    @GET("products/{id}")
    Call<Product> getProductById(@Header("Authorization") String token, @Path("id") int id);

    @POST("products")
    Call<Product> createProduct(@Header("Authorization") String token, @Body Product product);

    @PUT("products/{id}")
    Call<Product> updateProduct(@Header("Authorization") String token, @Path("id") int id, @Body Product product);

    @DELETE("products/{id}")
    Call<Map<String, String>> deleteProduct(@Header("Authorization") String token, @Path("id") int id);
}
