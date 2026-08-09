package com.sabibiz.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessRequest {

    @NotBlank
    @Size(max = 200)
    private String name;

    @Size(max = 100)
    private String tradeName;

    @Size(max = 50)
    private String businessNumber;

    @Size(max = 50)
    private String registrationNumber;

    @Size(max = 50)
    private String taxNumber;

    private String address;

    @Size(max = 100)
    private String city;

    @Size(max = 100)
    private String state;

    @Size(max = 100)
    private String country;

    @Size(max = 20)
    private String postalCode;

    @Size(max = 20)
    private String phoneNumber;

    @Size(max = 20)
    private String altPhoneNumber;

    @Size(max = 100)
    @Email
    private String email;

    @Size(max = 200)
    private String website;

    @Size(max = 3)
    private String currencyCode = "USD";

    @Size(max = 5)
    private String currencySymbol = "$";

    @Size(max = 50)
    private String timezone = "UTC";

    @Size(max = 20)
    private String dateFormat = "yyyy-MM-dd";

    @Size(max = 10)
    private String timeFormat = "HH:mm";

    @Size(max = 20)
    private String numberFormat = "#,##0.00";

    private Integer maxUsers = 10;

    private Integer maxProducts = 1000;

    private Boolean allowMultiBranch = false;

    private Boolean allowMultiCurrency = false;

    private String settings;
}