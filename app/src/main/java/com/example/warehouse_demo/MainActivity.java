package com.example.warehouse_demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button loginButton, buttonPdl;
    private EditText usernameInput, passwordInput;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 綁定 EditText 和 Button
        usernameInput = findViewById(R.id.editText_account);
        passwordInput = findViewById(R.id.editText_Password);
        loginButton = findViewById(R.id.button_login);
        buttonPdl = findViewById(R.id.button_Pdl);

        // 登入按鈕點擊事件
        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            // 檢查是否有輸入
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // 使用用戶輸入進行登入請求
            LoginData.LoginRequest loginRequest = new LoginData.LoginRequest(username, password);
            ApiService_login apiServiceLogin = ApiClient.getClient(token).create(ApiService_login.class);
            Call<LoginData.LoginResponse> call = apiServiceLogin.loginUser(loginRequest);
            call.enqueue(new Callback<LoginData.LoginResponse>() {
                @Override
                public void onResponse(Call<LoginData.LoginResponse> call, Response<LoginData.LoginResponse> response) {
                    if (response.isSuccessful()) {
                        // 檢查伺服器回應的原始資料
                        Log.d("main", "Response body: " + response.body().toString());

                        LoginData.LoginResponse loginResponse = response.body();
                        if (loginResponse != null) {
                            // 顯示訊息
                            Toast.makeText(MainActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            // 保存 Token 和 Username
                            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token", loginResponse.getToken());
                            editor.putString("username", loginResponse.getUsername()); // 假設你有這個字段
                            editor.apply();

                            Intent intent = new Intent(MainActivity.this, productlist.class);
                            startActivity(intent);
                        } else {
                            Log.d("LoginResponse", "Response body is null");
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginData.LoginResponse> call, Throwable t) {
                    // 處理網絡錯誤
                    Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // 跳轉到產品列表的按鈕事件
        buttonPdl.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, productlist.class);
            startActivity(intent);
        });
    }
}
