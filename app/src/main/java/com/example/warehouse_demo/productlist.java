package com.example.warehouse_demo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class productlist extends AppCompatActivity {

    private TextView textViewPdl;
    private TextView textViewUsername;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_productlist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        textViewPdl= (TextView)findViewById(R.id.textView_pdList);
        textViewPdl.setText("");
        textViewUsername = (TextView)findViewById(R.id.textView_username);
        buttonLogout = (Button)findViewById(R.id.button_logout);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        if (token != null) {
            // 使用保存的 Token 創建 Retrofit 客戶端
            ApiService apiService = ApiClient.getClient(token).create(ApiService.class);

            ApiService_user apiServiceUser  = ApiClient.getClient(token).create(ApiService_user.class);
            Call<Login_User> callUser = apiServiceUser.getUserProfile("Token " + token);
            callUser.enqueue(new Callback<Login_User>() {
                @Override
                public void onResponse(Call<Login_User> call, Response<Login_User> response) {
                    if (response.isSuccessful()) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                        String username = sharedPreferences.getString("username", "Guest");
                        // 在界面上顯示用戶資料
                        textViewUsername.setText("Login Success!\nUsername: " + username + "\n");
                    } else {
                        textViewUsername.setText("Failed to retrieve user data.");
                    }
                }

                @Override
                public void onFailure(Call<Login_User> call, Throwable t) {
                    textViewUsername.setText("Error retrieving user data: " + t.getMessage());
                    Log.d("main",t.getMessage());
                }
            });

            // 發送 API 請求
            Call<List<Product>> call = apiService.getProducts();
            call.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if (response.isSuccessful()) {
                        // 成功獲取產品資料
                        List<Product> products = response.body();
                        // 處理產品數據
                        for (Product product : products) {
                            // 在日誌中輸出產品信息
                            Log.d("Product", "sn: " + product.getserial_number() + ", Name: " + product.getproduct_name());
                            textViewPdl.append("SN:"+product.getserial_number() + ", Name: " + product.getproduct_name()+"\n");
                        }
                    } else {
                        Toast.makeText(productlist.this, "Failed to load products", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    // 處理錯誤
                    Toast.makeText(productlist.this, "Network Error", Toast.LENGTH_SHORT).show();
                    Log.d("Product", "Network Error");
                }
            });
        } else {
            // Token 不存在，可能需要重新登入
            Log.d("TokenError", "No Token Found");
        }
    }
}