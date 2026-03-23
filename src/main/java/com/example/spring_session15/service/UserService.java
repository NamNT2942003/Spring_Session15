package com.example.spring_session15.service;

import com.example.spring_session15.dto.response.UserProfileResponse;

public interface UserService {
    UserProfileResponse getCurrentUserProfile();
    void updateUserRole(Long userId, String newRole);
}