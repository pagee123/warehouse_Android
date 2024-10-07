package com.example.warehouse_demo;

public class LoginData {
    public static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    public class LoginResponse {
        private String token;
        private String message;
        private String username;

        public String getUsername() {
            return username;
        }

        public String getToken() {
            return token;
        }

        public String getMessage() {
            return message;
        }

        public String toString() {
            return "LoginResponse{" +
                    "token='" + token + '\'' +
                    ", message='" + message + '\'' +
                    ", username='" + username + '\'' +
                    '}';
        }
    }
}
