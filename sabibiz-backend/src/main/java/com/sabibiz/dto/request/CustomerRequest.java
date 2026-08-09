package com.sabibiz.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {

    @NotBlank
    @Size(max = 100)
    private String customerNumber;

    @NotBlank
    @Size(max = 200)
    private String name;

    @Size(max = 100)
    @Email
    private String email;

    @Size(max = 20)
    private String phoneNumber;

    @Size(max = 20)
    private String altPhoneNumber;

    private String address;

    @Size(max = 100)
    private String city;

    @Size(max = 100)
    private String country;

    @Size(max = 20)
    private String postalCode;

    @Size(max = 50)
    private String taxNumber;

    private BigDecimal creditLimit = BigDecimal.ZERO;

    private Integer paymentTermsDays = 30;

    private Boolean isActive = true;

    private String notes;
}