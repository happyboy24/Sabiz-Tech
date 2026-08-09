package com.sabibiz.dto.response;

import com.sabibiz.enums.PaymentMethod;
import com.sabibiz.enums.PaymentStatus;
import com.sabibiz.enums.SaleStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleResponse {

    private Long id;
    private String saleNumber;
    private SaleStatus status;
    private PaymentStatus paymentStatus;
    private Long customerId;
    private String customerName;
    private String customerNumber;
    private Long businessId;
    private String businessName;
    private Long cashierId;
    private String cashierName;
    private LocalDateTime saleDate;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private BigDecimal changeAmount;
    private BigDecimal dueAmount;
    private PaymentMethod paymentMethod;
    private String referenceNumber;
    private String notes;
    private LocalDateTime completedAt;
    private List<SaleItemResponse> items;
    private List<PaymentResponse> payments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}