package com.sabibiz.dto.response;

import com.sabibiz.enums.PaymentMethod;
import com.sabibiz.enums.PaymentStatus;
import com.sabibiz.enums.PaymentType;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long id;
    private String paymentNumber;
    private PaymentType paymentType;
    private PaymentMethod paymentMethod;
    private Long customerId;
    private String customerName;
    private Long supplierId;
    private String supplierName;
    private Long businessId;
    private String businessName;
    private Long saleId;
    private String saleNumber;
    private Long debtId;
    private String debtNumber;
    private Long purchaseOrderId;
    private String purchaseOrderNumber;
    private Long expenseId;
    private String expenseNumber;
    private BigDecimal amount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private LocalDate paymentDate;
    private LocalDate dueDate;
    private String referenceNumber;
    private String receivedFrom;
    private String notes;
    private String receiptUrl;
    private PaymentStatus status;
    private Long createdBy;
    private String createdByName;
    private LocalDateTime confirmedAt;
    private Long confirmedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}