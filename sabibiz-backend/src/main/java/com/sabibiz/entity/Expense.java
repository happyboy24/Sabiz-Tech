package com.sabibiz.entity;

import com.sabibiz.enums.ExpenseCategory;
import com.sabibiz.enums.PaymentMethod;
import com.sabibiz.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense extends BaseEntity {

    @NotBlank
    @Size(max = 50)
    @Column(name = "expense_number", nullable = false, unique = true)
    private String expenseNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "expense_category", nullable = false)
    private ExpenseCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @NotNull
    @Column(name = "amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(name = "tax_amount", precision = 19, scale = 4)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "total_amount", precision = 19, scale = 4)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate = LocalDate.now();

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "paid_date")
    private LocalDate paidDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Size(max = 100)
    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "receipt_url")
    private String receiptUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "is_recurring")
    private Boolean isRecurring = false;

    @Size(max = 20)
    @Column(name = "recurrence_pattern")
    private String recurrencePattern;

    @Column(name = "next_due_date")
    private LocalDate nextDueDate;

    public void calculateTotal() {
        BigDecimal tax = taxAmount != null ? taxAmount : BigDecimal.ZERO;
        this.totalAmount = amount.add(tax);
    }
}