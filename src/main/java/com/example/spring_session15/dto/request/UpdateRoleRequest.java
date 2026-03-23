package com.example.spring_session15.dto.request;

import lombok.Data;

@Data
public class UpdateRoleRequest {
    private String role; // Ví dụ: "ROLE_STAFF", "ROLE_ADMIN", "ROLE_CUSTOMER"
}