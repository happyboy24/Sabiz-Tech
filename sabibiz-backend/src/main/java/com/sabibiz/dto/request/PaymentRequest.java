package com.sabibiz.dto.request;

import com.sabibiz.enums.PaymentMethod;
import com.sabibiz.enums.PaymentStatus;
import com.sabibiz.enums.PaymentType;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    @NotBlank
    @Size(max = 50)
    private String paymentNumber;

    @NotNull
    private PaymentType paymentType;

    @NotNull
    private PaymentMethod paymentMethod;

    private Long customerId;

    private Long supplierId;

    private Long saleId;

    private Long debtId;

    private Long purchaseOrderId;

    private Long expenseId;

    @NotNull
    private BigDecimal amount = BigDecimal.ZERO;

    private BigDecimal taxAmount = BigDecimal.ZERO;

    @NotNull
    private LocalDate paymentDate = LocalDate.now();

    private LocalDate dueDate;

    @Size(max = 100)
    private String referenceNumber;

    @Size(max = 200)
    private String receivedFrom;

    private String notes;

    private String receiptUrl;

    private PaymentStatus status = PaymentStatus.PENDING;
}