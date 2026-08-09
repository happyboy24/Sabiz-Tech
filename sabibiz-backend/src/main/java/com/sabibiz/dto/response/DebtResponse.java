package com.sabibiz.dto.response;

import com.sabibiz.enums.DebtStatus;
import com.sabibiz.enums.DebtType;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DebtResponse {

    private Long id;
    private String debtNumber;
    private DebtType debtType;
    private DebtStatus status;
    private Long customerId;
    private String customerName;
    private Long supplierId;
    private String supplierName;
    private Long businessId;
    private String businessName;
    private Long saleId;
    private String saleNumber;
    private Long purchaseOrderId;
    private String purchaseOrderNumber;
    private BigDecimal originalAmount;
    private BigDecimal paidAmount;
    private BigDecimal balanceAmount;
    private BigDecimal interestRate;
    private BigDecimal interestAmount;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private LocalDate lastPaymentDate;
    private LocalDate nextDueDate;
    private Boolean isOverdue;
    private Integer daysOverdue;
    private String notes;
    private Long createdBy;
    private String createdByName;
    private Integer paymentTermsDays;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}