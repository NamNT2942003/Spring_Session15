package com.example.spring_session15.service.impl;

import com.example.spring_session15.dto.request.LoginRequest;
import com.example.spring_session15.dto.request.RegisterRequest;
import com.example.spring_session15.dto.response.AuthResponse;
import com.example.spring_session15.dto.response.MessageResponse;
import com.example.spring_session15.entity.User;
import com.example.spring_session15.repository.UserRepository;
import com.example.spring_session15.security.JwtTokenProvider;
import com.example.spring_session15.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Override
    public MessageResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã được sử dụng!");
            // Thực tế nên ném custom exception và xử lý ở GlobalExceptionHandler
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Mã hóa password
        user.setPhone(request.getPhone());

        // Gán Role mặc định nếu client không gửi lên
        String role = (request.getRole() != null && !request.getRole().isEmpty()) ? request.getRole() : "ROLE_USER";
        user.setRole(role);

        userRepository.save(user);

        return new MessageResponse("Đăng ký tài khoản thành công!");
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // Xác thực qua AuthenticationManager của Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Set authentication vào context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Tạo token
        String accessToken = tokenProvider.generateAccessToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);

        return new AuthResponse(accessToken, refreshToken);
    }
}