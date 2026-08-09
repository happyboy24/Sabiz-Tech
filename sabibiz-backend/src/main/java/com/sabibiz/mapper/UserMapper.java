package com.sabibiz.mapper;

import com.sabibiz.dto.request.UserRequest;
import com.sabibiz.dto.response.UserResponse;
import com.sabibiz.entity.User;
import com.sabibiz.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(Role.valueOf(request.getRole()));
        user.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        return user;
    }

    public UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole());
        response.setIsActive(user.getIsActive());
        response.setLastLoginAt(user.getLastLoginAt());
        if (user.getBusiness() != null) {
            response.setBusinessId(user.getBusiness().getId());
            response.setBusinessName(user.getBusiness().getName());
        }
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }

    public void updateEntity(User user, UserRequest request) {
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setIsActive(request.getIsActive());
    }
}