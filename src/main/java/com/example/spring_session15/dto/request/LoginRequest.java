package com.example.spring_session15.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}