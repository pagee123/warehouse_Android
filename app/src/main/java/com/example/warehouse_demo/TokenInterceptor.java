package com.example.warehouse_demo;

import okhttp3.Interceptor;
import java.io.IOException;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    private String token;

    public TokenInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder()
                .header("Authorization", "Token " + token); // 附加 Token 到 Authorization 標頭
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }
}
