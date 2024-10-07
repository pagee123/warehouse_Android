package com.example.warehouse_demo;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ApiClient {

    // 你的 Django API 基本 URL，確保這個 URL 是正確的
    private static final String BASE_URL = "http://192.168.58.156:8080/";
    //private static final String BASE_URL = "http://192.168.0.24:8080/";
    private static Retrofit retrofit = null;
    private static String token;

    public static Retrofit getClient(String token) {
        // 檢查 Retrofit 是否為 null，如果是則創建新的 Retrofit 實例
        if (retrofit == null) {
            // 創建一個新的 OkHttpClient，並添加一個攔截器來附加 Token 到每個請求
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();

                    // 如果 token 不為 null，將其附加到 Authorization 標頭中
                    if (ApiClient.token != null) {
                        Request newRequest = originalRequest.newBuilder()
                                .header("Authorization", "Token " + ApiClient.token)
                                .build();
                        return chain.proceed(newRequest);
                    }

                    return chain.proceed(originalRequest);
                }
            });
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)  // 設置基本 URL
                    .addConverterFactory(GsonConverterFactory.create())  // 使用 Gson 將 JSON 轉換為對象
                    .build();
        }
        return retrofit;  // 返回 Retrofit 實例
    }
}



