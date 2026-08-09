package com.sabibiz.dto.request;

import com.sabibiz.enums.ExpenseCategory;
import com.sabibiz.enums.PaymentMethod;
import com.sabibiz.enums.PaymentStatus;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseRequest {

    @NotBlank
    @Size(max = 50)
    private String expenseNumber;

    @NotNull
    private ExpenseCategory category;

    private Long supplierId;

    @NotNull
    private BigDecimal amount = BigDecimal.ZERO;

    private BigDecimal taxAmount = BigDecimal.ZERO;

    @NotNull
    private LocalDate expenseDate = LocalDate.now();

    private LocalDate dueDate;

    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    private PaymentMethod paymentMethod;

    @Size(max = 100)
    private String referenceNumber;

    private String description;

    private String receiptUrl;

    private Boolean isRecurring = false;

    @Size(max = 20)
    private String recurrencePattern;

    private LocalDate nextDueDate;
}