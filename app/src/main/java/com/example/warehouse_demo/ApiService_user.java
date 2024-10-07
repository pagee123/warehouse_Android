package com.example.warehouse_demo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiService_user {
    // 其他 API 請求...

    @GET("api/user/profile/")  // 用戶資料 API 端點
    Call<Login_User> getUserProfile(@Header("Authorization") String token);

}
