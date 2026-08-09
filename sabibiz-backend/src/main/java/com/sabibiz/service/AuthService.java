package com.sabibiz.service;

import com.sabibiz.dto.request.LoginRequest;
import com.sabibiz.dto.response.AuthResponse;
import com.sabibiz.entity.User;
import com.sabibiz.exception.BusinessException;
import com.sabibiz.repository.UserRepository;
import com.sabibiz.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        User user = (User) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        return buildAuthResponse(user, jwt);
    }

    @Transactional
    public AuthResponse register(LoginRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already registered", HttpStatus.CONFLICT);
        }
        
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName("");
        user.setLastName("");
        user.setRole(com.sabibiz.entity.Role.ROLE_CASHIER);
        user.setIsActive(true);
        
        user = userRepository.save(user);
        String jwt = jwtUtils.generateTokenFromUsername(user.getUsername());
        
        return buildAuthResponse(user, jwt);
    }

    public AuthResponse refreshToken(String authHeader) {
        String jwt = authHeader.substring(7);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new BusinessException("User not found", HttpStatus.NOT_FOUND));
        
        String newJwt = jwtUtils.generateTokenFromUsername(username);
        return buildAuthResponse(user, newJwt);
    }

    private AuthResponse buildAuthResponse(User user, String jwt) {
        return AuthResponse.builder()
            .accessToken(jwt)
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .role(user.getRole())
            .businessId(user.getBusiness() != null ? user.getBusiness().getId() : null)
            .businessName(user.getBusiness() != null ? user.getBusiness().getName() : null)
            .build();
    }
}