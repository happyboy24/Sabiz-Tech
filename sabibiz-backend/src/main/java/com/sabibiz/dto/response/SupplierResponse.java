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
public class SupplierResponse {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String alternatePhone;
    private String address;
    private String city;
    private String country;
    private String postalCode;
    private String taxNumber;
    private String contactPerson;
    private Integer paymentTermsDays;
    private BigDecimal creditLimit;
    private BigDecimal currentBalance;
    private BigDecimal availableCredit;
    private Boolean isActive;
    private LocalDate supplierSince;
    private String notes;
    private Long businessId;
    private String businessName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}