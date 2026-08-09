package com.sabibiz.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "purchase_order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    @Column(name = "quantity_ordered", nullable = false)
    private Integer quantityOrdered = 1;

    @Column(name = "quantity_received")
    private Integer quantityReceived = 0;

    @NotNull
    @Column(name = "unit_price", precision = 19, scale = 4, nullable = false)
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @Column(name = "discount_percent", precision = 5, scale = 2)
    private BigDecimal discountPercent = BigDecimal.ZERO;

    @Column(name = "discount_amount", precision = 19, scale = 4)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "tax_rate", precision = 5, scale = 2)
    private BigDecimal taxRate = BigDecimal.ZERO;

    @Column(name = "tax_amount", precision = 19, scale = 4)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "line_total", precision = 19, scale = 4, nullable = false)
    private BigDecimal lineTotal = BigDecimal.ZERO;

    @Size(max = 20)
    @Column(name = "unit_of_measure")
    private String unitOfMeasure;

    public void calculateLineTotal() {
        BigDecimal qty = BigDecimal.valueOf(quantityOrdered != null ? quantityOrdered : 1);
        BigDecimal price = unitPrice != null ? unitPrice : BigDecimal.ZERO;
        BigDecimal discount = discountAmount != null ? discountAmount : BigDecimal.ZERO;
        BigDecimal tax = taxAmount != null ? taxAmount : BigDecimal.ZERO;

        this.lineTotal = price.multiply(qty).subtract(discount).add(tax);
    }

    public Integer getQuantityPending() {
        int ordered = quantityOrdered != null ? quantityOrdered : 0;
        int received = quantityReceived != null ? quantityReceived : 0;
        return Math.max(0, ordered - received);
    }

    public Boolean isFullyReceived() {
        return getQuantityPending() == 0;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }
}