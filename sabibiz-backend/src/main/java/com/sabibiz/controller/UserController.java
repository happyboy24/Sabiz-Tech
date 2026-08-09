package com.sabibiz.controller;

import com.sabibiz.dto.request.UserRequest;
import com.sabibiz.dto.response.UserResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/users/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        com.sabibiz.entity.User user = userService.getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userService.getById(user.getId()));
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/businesses/{businessId}/users")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<PageResponse<UserResponse>> getByBusiness(
            @PathVariable Long businessId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(userService.getByBusinessId(businessId, pageable));
    }

    @PostMapping("/users/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String oldPassword = body.get("oldPassword");
        String newPassword = body.get("newPassword");
        userService.changePassword(id, oldPassword, newPassword);
        return ResponseEntity.noContent().build();
    }
}
