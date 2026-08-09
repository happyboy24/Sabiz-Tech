package com.sabibiz.service.impl;

import com.sabibiz.dto.request.UserRequest;
import com.sabibiz.dto.response.UserResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.entity.Business;
import com.sabibiz.entity.User;
import com.sabibiz.entity.Role;
import com.sabibiz.exception.ResourceNotFoundException;
import com.sabibiz.mapper.UserMapper;
import com.sabibiz.repository.BusinessRepository;
import com.sabibiz.repository.UserRepository;
import com.sabibiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole()));
        user.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        user = userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getByUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse update(Long id, UserRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (!user.getUsername().equals(request.getUsername()) 
            && userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (!user.getEmail().equals(request.getEmail()) 
            && userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        userMapper.updateEntity(user, request);
        user.setRole(Role.valueOf(request.getRole()));
        user = userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setIsActive(false);
        userRepository.save(user);
    }

    @Override
    public void changePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<UserResponse> getAll(Long businessId, Pageable pageable) {
        Page<User> page = userRepository.findByBusinessId(businessId, pageable);
        return PageResponse.of(page.map(userMapper::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<UserResponse> getByBusinessId(Long businessId, Pageable pageable) {
        Page<User> page = userRepository.findByBusinessIdAndIsActiveTrue(businessId, pageable);
        return PageResponse.of(page.map(userMapper::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserEntity(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @Override
    @Transactional(readOnly = true)
    public Business getBusinessEntity(Long businessId) {
        return businessRepository.findById(businessId)
            .orElseThrow(() -> new ResourceNotFoundException("Business not found with id: " + businessId));
    }

    @Override
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCurrentBusinessId() {
        User currentUser = getCurrentUser();
        if (currentUser != null && currentUser.getBusiness() != null) {
            return currentUser.getBusiness().getId();
        }
        return null;
    }
}