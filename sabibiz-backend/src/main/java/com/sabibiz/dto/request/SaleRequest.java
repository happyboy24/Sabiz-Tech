package com.sabibiz.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleRequest {

    @NotNull
    private Long customerId;

    private List<SaleItemRequest> items;

    private BigDecimal discountAmount = BigDecimal.ZERO;

    private BigDecimal taxAmount = BigDecimal.ZERO;

    private String paymentMethod;

    private String referenceNumber;

    private String notes;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SaleItemRequest {
        @NotNull
        private Long productId;

        @NotNull
        private Integer quantity;

        @NotNull
        private BigDecimal unitPrice;

        private BigDecimal discountPercent = BigDecimal.ZERO;

        private BigDecimal discountAmount = BigDecimal.ZERO;

        private BigDecimal taxRate = BigDecimal.ZERO;

        private String unitOfMeasure;
    }
}