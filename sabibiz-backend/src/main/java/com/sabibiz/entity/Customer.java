package com.sabibiz.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer extends BaseEntity {

    @NotBlank
    @Size(max = 100)
    @Column(name = "customer_number", nullable = false, unique = true)
    private String customerNumber;

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false)
    private String name;

    @Size(max = 100)
    @Email
    private String email;

    @Size(max = 20)
    @Column(name = "phone_number")
    private String phoneNumber;

    @Size(max = 20)
    @Column(name = "alt_phone_number")
    private String altPhoneNumber;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Size(max = 100)
    private String city;

    @Size(max = 100)
    private String country;

    @Size(max = 20)
    @Column(name = "postal_code")
    private String postalCode;

    @Size(max = 50)
    @Column(name = "tax_number")
    private String taxNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @Column(name = "credit_limit", precision = 19, scale = 4)
    private BigDecimal creditLimit = BigDecimal.ZERO;

    @Column(name = "current_balance", precision = 19, scale = 4)
    private BigDecimal currentBalance = BigDecimal.ZERO;

    @Column(name = "payment_terms_days")
    private Integer paymentTermsDays = 30;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "is_blacklisted")
    private Boolean isBlacklisted = false;

    @Column(name = "blacklist_reason", columnDefinition = "TEXT")
    private String blacklistReason;

    @Column(name = "last_purchase_date")
    private LocalDate lastPurchaseDate;

    @Column(name = "last_payment_date")
    private LocalDate lastPaymentDate;

    @Column(name = "total_purchases", precision = 19, scale = 4)
    private BigDecimal totalPurchases = BigDecimal.ZERO;

    @Column(name = "total_payments", precision = 19, scale = 4)
    private BigDecimal totalPayments = BigDecimal.ZERO;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints = 0;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sale> sales = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Debt> debts = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    public void updateBalance(BigDecimal amount) {
        BigDecimal current = currentBalance != null ? currentBalance : BigDecimal.ZERO;
        this.currentBalance = current.add(amount);
    }

    public BigDecimal getAvailableCredit() {
        if (creditLimit == null || creditLimit.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal available = creditLimit.subtract(currentBalance != null ? currentBalance : BigDecimal.ZERO);
        return available.compareTo(BigDecimal.ZERO) > 0 ? available : BigDecimal.ZERO;
    }
}