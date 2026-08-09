package com.sabibiz.dto.request;

import com.sabibiz.enums.DebtStatus;
import com.sabibiz.enums.DebtType;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DebtRequest {

    @NotBlank
    @Size(max = 50)
    private String debtNumber;

    @NotNull
    private DebtType debtType;

    private DebtStatus status = DebtStatus.PENDING;

    private Long customerId;

    private Long supplierId;

    private Long saleId;

    private Long purchaseOrderId;

    @NotNull
    private BigDecimal originalAmount = BigDecimal.ZERO;

    private BigDecimal paidAmount = BigDecimal.ZERO;

    private BigDecimal interestRate = BigDecimal.ZERO;

    private LocalDate dueDate;

    private Integer paymentTermsDays = 30;

    private String notes;
}