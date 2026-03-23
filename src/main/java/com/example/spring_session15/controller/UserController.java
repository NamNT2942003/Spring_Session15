package com.example.spring_session15.controller;

import com.example.spring_session15.dto.request.UpdateRoleRequest;
import com.example.spring_session15.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Bất kỳ user nào đã đăng nhập cũng gọi được
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUserProfile());
    }

    // Chỉ ADMIN mới được phép gọi
    @PutMapping("/{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id, @RequestBody UpdateRoleRequest request) {
        userService.updateUserRole(id, request.getRole());
        return ResponseEntity.ok().body("Cập nhật quyền thành công!");
    }
}