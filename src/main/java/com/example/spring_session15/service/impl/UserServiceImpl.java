package com.example.spring_session15.service.impl;

import com.example.spring_session15.dto.response.UserProfileResponse;
import com.example.spring_session15.entity.User;
import com.example.spring_session15.repository.UserRepository;
import com.example.spring_session15.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng hiện tại trong hệ thống"));
    }

    @Override
    public UserProfileResponse getCurrentUserProfile() {
        User user = getCurrentUser();

        // Sử dụng Builder pattern (nhờ @Builder của Lombok) để tạo object an toàn
        return UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }

    @Override
    @Transactional
    public void updateUserRole(Long userId, String newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản với ID: " + userId));

        // Validate role hợp lệ nếu cần thiết
        if (!newRole.startsWith("ROLE_")) {
            newRole = "ROLE_" + newRole.toUpperCase();
        }

        user.setRole(newRole);
        userRepository.save(user);
    }
}