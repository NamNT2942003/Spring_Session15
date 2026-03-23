package com.example.spring_session15.service;

import com.example.spring_session15.dto.request.LoginRequest;
import com.example.spring_session15.dto.request.RegisterRequest;
import com.example.spring_session15.dto.response.AuthResponse;
import com.example.spring_session15.dto.response.MessageResponse;

public interface AuthService {
    MessageResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}