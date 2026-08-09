package com.sabibiz.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity = 1;

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

    @Column(name = "product_snapshot", columnDefinition = "JSON")
    private String productSnapshot;

    public void calculateLineTotal() {
        BigDecimal qty = BigDecimal.valueOf(quantity != null ? quantity : 1);
        BigDecimal price = unitPrice != null ? unitPrice : BigDecimal.ZERO;
        BigDecimal discount = discountAmount != null ? discountAmount : BigDecimal.ZERO;
        BigDecimal tax = taxAmount != null ? taxAmount : BigDecimal.ZERO;

        this.lineTotal = price.multiply(qty).subtract(discount).add(tax);
    }

    public BigDecimal getLineSubtotal() {
        BigDecimal qty = BigDecimal.valueOf(quantity != null ? quantity : 1);
        BigDecimal price = unitPrice != null ? unitPrice : BigDecimal.ZERO;
        return price.multiply(qty);
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }
}