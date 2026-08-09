package com.sabibiz.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierRequest {

    @NotBlank
    @Size(max = 200)
    private String name;

    @Size(max = 100)
    @Email
    private String email;

    @Size(max = 20)
    private String phoneNumber;

    @Size(max = 20)
    private String alternatePhone;

    @Size(max = 500)
    private String address;

    @Size(max = 100)
    private String city;

    @Size(max = 100)
    private String country;

    @Size(max = 20)
    private String postalCode;

    @Size(max = 50)
    private String taxNumber;

    @Size(max = 100)
    private String contactPerson;

    private Integer paymentTermsDays = 30;

    private BigDecimal creditLimit = BigDecimal.ZERO;

    private Boolean isActive = true;

    private LocalDate supplierSince;

    private String notes;
}