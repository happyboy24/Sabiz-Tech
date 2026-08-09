package com.sabibiz.dto.response;

import com.sabibiz.enums.PaymentMethod;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderItemResponse {

    private Long id;
    private Long purchaseOrderId;
    private Long productId;
    private String productName;
    private String productSku;
    private Integer quantityOrdered;
    private Integer quantityReceived;
    private Integer quantityPending;
    private BigDecimal unitPrice;
    private BigDecimal discountPercent;
    private BigDecimal discountAmount;
    private BigDecimal taxRate;
    private BigDecimal taxAmount;
    private BigDecimal lineTotal;
    private String unitOfMeasure;
    private Boolean isFullyReceived;
}