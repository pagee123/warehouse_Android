package com.example.warehouse_demo;

import android.service.autofill.UserData;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Header;

public interface ApiService {
    @GET("api/products/")  // Django API 的端點
    Call<List<Product>> getProducts();
}

