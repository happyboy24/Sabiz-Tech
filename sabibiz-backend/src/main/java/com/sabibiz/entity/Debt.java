package com.sabibiz.entity;

import com.sabibiz.enums.DebtStatus;
import com.sabibiz.enums.DebtType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "debts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Debt extends BaseEntity {

    @NotBlank
    @Size(max = 50)
    @Column(name = "debt_number", nullable = false, unique = true)
    private String debtNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "debt_type", nullable = false)
    private DebtType debtType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DebtStatus status = DebtStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @NotNull
    @Column(name = "original_amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal originalAmount = BigDecimal.ZERO;

    @Column(name = "paid_amount", precision = 19, scale = 4)
    private BigDecimal paidAmount = BigDecimal.ZERO;

    @Column(name = "balance_amount", precision = 19, scale = 4)
    private BigDecimal balanceAmount = BigDecimal.ZERO;

    @Column(name = "interest_rate", precision = 5, scale = 2)
    private BigDecimal interestRate = BigDecimal.ZERO;

    @Column(name = "interest_amount", precision = 19, scale = 4)
    private BigDecimal interestAmount = BigDecimal.ZERO;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "paid_date")
    private LocalDate paidDate;

    @Column(name = "last_payment_date")
    private LocalDate lastPaymentDate;

    @Column(name = "next_due_date")
    private LocalDate nextDueDate;

    @Column(name = "is_overdue")
    private Boolean isOverdue = false;

    @Column(name = "days_overdue")
    private Integer daysOverdue = 0;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "payment_terms_days")
    private Integer paymentTermsDays = 30;

    public void calculateBalance() {
        BigDecimal paid = paidAmount != null ? paidAmount : BigDecimal.ZERO;
        BigDecimal interest = interestAmount != null ? interestAmount : BigDecimal.ZERO;
        this.balanceAmount = originalAmount.add(interest).subtract(paid);
        if (this.balanceAmount.compareTo(BigDecimal.ZERO) <= 0) {
            this.balanceAmount = BigDecimal.ZERO;
            this.status = DebtStatus.PAID;
            this.paidDate = LocalDate.now();
        } else {
            this.status = DebtStatus.PARTIAL;
        }
    }

    public void updateOverdueStatus() {
        if (dueDate != null && status != DebtStatus.PAID && status != DebtStatus.CANCELLED) {
            this.isOverdue = LocalDate.now().isAfter(dueDate);
            if (isOverdue) {
                this.daysOverdue = (int) java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now());
                this.status = DebtStatus.OVERDUE;
            }
        } else {
            this.isOverdue = false;
            this.daysOverdue = 0;
        }
    }
}