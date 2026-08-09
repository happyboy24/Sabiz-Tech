package com.sabibiz.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "businesses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Business extends BaseEntity {

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false)
    private String name;

    @Size(max = 100)
    @Column(name = "trade_name")
    private String tradeName;

    @Size(max = 50)
    @Column(name = "business_number", unique = true)
    private String businessNumber;

    @Size(max = 50)
    @Column(name = "registration_number", unique = true)
    private String registrationNumber;

    @Size(max = 50)
    @Column(name = "tax_number", unique = true)
    private String taxNumber;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Size(max = 100)
    private String city;

    @Size(max = 100)
    private String state;

    @Size(max = 100)
    private String country;

    @Size(max = 20)
    @Column(name = "postal_code")
    private String postalCode;

    @Size(max = 20)
    @Column(name = "phone_number")
    private String phoneNumber;

    @Size(max = 20)
    @Column(name = "alt_phone_number")
    private String altPhoneNumber;

    @Size(max = 100)
    @Email
    private String email;

    @Size(max = 200)
    private String website;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "currency_code", length = 3)
    private String currencyCode = "USD";

    @Column(name = "currency_symbol", length = 5)
    private String currencySymbol = "$";

    @Column(name = "timezone", length = 50)
    private String timezone = "UTC";

    @Column(name = "date_format", length = 20)
    private String dateFormat = "yyyy-MM-dd";

    @Column(name = "time_format", length = 10)
    private String timeFormat = "HH:mm";

    @Column(name = "number_format", length = 20)
    private String numberFormat = "#,##0.00";

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "subscription_status")
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus = SubscriptionStatus.TRIAL;

    @Column(name = "subscription_expires_at")
    private LocalDateTime subscriptionExpiresAt;

    @Column(name = "trial_ends_at")
    private LocalDateTime trialEndsAt;

    @Column(name = "max_users")
    private Integer maxUsers = 10;

    @Column(name = "max_products")
    private Integer maxProducts = 1000;

    @Column(name = "allow_multi_branch")
    private Boolean allowMultiBranch = false;

    @Column(name = "allow_multi_currency")
    private Boolean allowMultiCurrency = false;

    @Column(name = "settings", columnDefinition = "JSON")
    private String settings;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Customer> customers = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Supplier> suppliers = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Sale> sales = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Expense> expenses = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<StockMovement> stockMovements = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Debt> debts = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();

    public enum SubscriptionStatus {
        TRIAL,
        ACTIVE,
        EXPIRED,
        CANCELLED,
        SUSPENDED
    }

    public String getDisplayName() {
        return tradeName != null && !tradeName.isEmpty() ? tradeName : name;
    }
}