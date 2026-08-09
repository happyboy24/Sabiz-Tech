package com.sabibiz.dto.response;

import com.sabibiz.enums.PurchaseOrderStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderResponse {

    private Long id;
    private String orderNumber;
    private PurchaseOrderStatus status;
    private Long supplierId;
    private String supplierName;
    private Long businessId;
    private String businessName;
    private Long createdBy;
    private String createdByName;
    private LocalDate orderDate;
    private LocalDate expectedDeliveryDate;
    private LocalDate actualDeliveryDate;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private BigDecimal balanceAmount;
    private String paymentMethod;
    private String referenceNumber;
    private String notes;
    private List<PurchaseOrderItemResponse> items;
    private List<PaymentResponse> payments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}