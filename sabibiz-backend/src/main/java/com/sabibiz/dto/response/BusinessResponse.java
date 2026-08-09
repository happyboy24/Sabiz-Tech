package com.sabibiz.dto.response;

import com.sabibiz.entity.Business;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessResponse {

    private Long id;
    private String name;
    private String tradeName;
    private String businessNumber;
    private String registrationNumber;
    private String taxNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phoneNumber;
    private String altPhoneNumber;
    private String email;
    private String website;
    private String logoUrl;
    private String currencyCode;
    private String currencySymbol;
    private String timezone;
    private String dateFormat;
    private String timeFormat;
    private String numberFormat;
    private Boolean isActive;
    private Business.SubscriptionStatus subscriptionStatus;
    private LocalDateTime subscriptionExpiresAt;
    private LocalDateTime trialEndsAt;
    private Integer maxUsers;
    private Integer maxProducts;
    private Boolean allowMultiBranch;
    private Boolean allowMultiCurrency;
    private String settings;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getDisplayName() {
        return tradeName != null && !tradeName.isEmpty() ? tradeName : name;
    }
}