package com.example.warehouse_demo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService_login {
    // POST 請求，發送用戶登入的數據
    @POST("api/login/")
    Call<LoginData.LoginResponse> loginUser(@Body LoginData.LoginRequest loginRequest);
}
