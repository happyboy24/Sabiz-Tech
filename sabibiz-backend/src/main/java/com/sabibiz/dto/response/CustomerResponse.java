package com.sabibiz.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    private Long id;
    private String customerNumber;
    private String name;
    private String email;
    private String phoneNumber;
    private String altPhoneNumber;
    private String address;
    private String city;
    private String country;
    private String postalCode;
    private String taxNumber;
    private BigDecimal creditLimit;
    private BigDecimal currentBalance;
    private BigDecimal availableCredit;
    private Integer paymentTermsDays;
    private Boolean isActive;
    private Boolean isBlacklisted;
    private String blacklistReason;
    private LocalDate lastPurchaseDate;
    private LocalDate lastPaymentDate;
    private BigDecimal totalPurchases;
    private BigDecimal totalPayments;
    private Integer loyaltyPoints;
    private String notes;
    private Long businessId;
    private String businessName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}