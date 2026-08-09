package com.sabibiz.dto.response;

import com.sabibiz.entity.Role;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Role role;
    private Boolean isActive;
    private LocalDateTime lastLoginAt;
    private Long businessId;
    private String businessName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getFullName() {
        if (firstName != null && lastName != null) {
            return firstName + " " + lastName;
        }
        return firstName != null ? firstName : username;
    }
}