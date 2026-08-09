package com.sabibiz.dto.response;

import com.sabibiz.enums.ExpenseCategory;
import com.sabibiz.enums.PaymentMethod;
import com.sabibiz.enums.PaymentStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseResponse {

    private Long id;
    private String expenseNumber;
    private ExpenseCategory category;
    private String categoryDisplayName;
    private Long supplierId;
    private String supplierName;
    private Long businessId;
    private String businessName;
    private BigDecimal amount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private LocalDate expenseDate;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private String referenceNumber;
    private String description;
    private String receiptUrl;
    private Long createdBy;
    private String createdByName;
    private Boolean isRecurring;
    private String recurrencePattern;
    private LocalDate nextDueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}