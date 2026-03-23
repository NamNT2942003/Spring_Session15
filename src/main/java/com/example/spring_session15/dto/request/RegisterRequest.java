package com.example.spring_session15.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String phone;
    private String role;
}