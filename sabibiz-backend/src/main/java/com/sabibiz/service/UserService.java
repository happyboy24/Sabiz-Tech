package com.sabibiz.service;

import com.sabibiz.dto.request.UserRequest;
import com.sabibiz.dto.response.UserResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.entity.User;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponse create(UserRequest request);
    UserResponse getById(Long id);
    UserResponse getByUsername(String username);
    UserResponse update(Long id, UserRequest request);
    void delete(Long id);
    void changePassword(Long id, String oldPassword, String newPassword);
    PageResponse<UserResponse> getAll(Long businessId, Pageable pageable);
    PageResponse<UserResponse> getByBusinessId(Long businessId, Pageable pageable);
    User getUserEntity(Long userId);
    com.sabibiz.entity.Business getBusinessEntity(Long businessId);
    User getCurrentUser();
    Long getCurrentBusinessId();
}