package com.sabibiz.entity;

import com.sabibiz.enums.PaymentMethodType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "payment_methods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMethod extends BaseEntity {

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, unique = true)
    private String code;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String name;

    @Size(max = 200)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "method_type", nullable = false)
    private PaymentMethodType methodType = PaymentMethodType.OTHER;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Column(name = "icon")
    private String icon;

    @Column(name = "config", columnDefinition = "JSON")
    private String config;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;
}