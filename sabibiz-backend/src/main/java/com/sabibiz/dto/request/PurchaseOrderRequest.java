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
public class PurchaseOrderRequest {

    @NotNull
    private Long supplierId;

    private LocalDate orderDate = LocalDate.now();

    private LocalDate expectedDeliveryDate;

    private BigDecimal discountAmount = BigDecimal.ZERO;

    private String paymentMethod;

    private String referenceNumber;

    private String notes;

    private List<PurchaseOrderItemRequest> items;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PurchaseOrderItemRequest {
        @NotNull
        private Long productId;

        @NotNull
        private Integer quantityOrdered;

        @NotNull
        private BigDecimal unitPrice;

        private BigDecimal discountPercent = BigDecimal.ZERO;

        private BigDecimal discountAmount = BigDecimal.ZERO;

        private BigDecimal taxRate = BigDecimal.ZERO;

        private String unitOfMeasure;
    }
}