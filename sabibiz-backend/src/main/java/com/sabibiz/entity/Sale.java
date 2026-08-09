package com.sabibiz.entity;

import com.sabibiz.enums.PaymentMethod;
import com.sabibiz.enums.PaymentStatus;
import com.sabibiz.enums.SaleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale extends BaseEntity {

    @NotBlank
    @Size(max = 50)
    @Column(name = "sale_number", nullable = false, unique = true)
    private String saleNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SaleStatus status = SaleStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashier_id")
    private User cashier;

    @Column(name = "sale_date", nullable = false)
    private LocalDateTime saleDate = LocalDateTime.now();

    @Column(name = "subtotal", precision = 19, scale = 4, nullable = false)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Column(name = "tax_amount", precision = 19, scale = 4)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "discount_amount", precision = 19, scale = 4)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "total_amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "paid_amount", precision = 19, scale = 4)
    private BigDecimal paidAmount = BigDecimal.ZERO;

    @Column(name = "change_amount", precision = 19, scale = 4)
    private BigDecimal changeAmount = BigDecimal.ZERO;

    @Column(name = "due_amount", precision = 19, scale = 4)
    private BigDecimal dueAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Size(max = 100)
    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SaleItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();

    @OneToOne(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private Debt debt;

    public void calculateTotals() {
        this.subtotal = items.stream()
            .map(SaleItem::getLineTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.taxAmount = items.stream()
            .map(SaleItem::getTaxAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.totalAmount = this.subtotal.add(this.taxAmount).subtract(this.discountAmount != null ? this.discountAmount : BigDecimal.ZERO);
        this.dueAmount = this.totalAmount.subtract(this.paidAmount != null ? this.paidAmount : BigDecimal.ZERO);
    }

    public void updatePaymentStatus() {
        if (paidAmount == null || paidAmount.compareTo(BigDecimal.ZERO) == 0) {
            this.paymentStatus = PaymentStatus.PENDING;
        } else if (paidAmount.compareTo(totalAmount) >= 0) {
            this.paymentStatus = PaymentStatus.COMPLETED;
        } else {
            this.paymentStatus = PaymentStatus.PARTIAL;
        }
    }
}