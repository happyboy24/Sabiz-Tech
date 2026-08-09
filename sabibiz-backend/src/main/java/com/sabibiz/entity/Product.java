package com.sabibiz.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false)
    private String name;

    @Size(max = 100)
    @Column(name = "sku", unique = true)
    private String sku;

    @Size(max = 50)
    private String barcode;

    @Size(max = 1000)
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @Column(name = "cost_price", precision = 19, scale = 4, nullable = false)
    private BigDecimal costPrice = BigDecimal.ZERO;

    @NotNull
    @Column(name = "selling_price", precision = 19, scale = 4, nullable = false)
    private BigDecimal sellingPrice = BigDecimal.ZERO;

    @Column(name = "wholesale_price", precision = 19, scale = 4)
    private BigDecimal wholesalePrice;

    @Column(name = "min_stock_level")
    private Integer minStockLevel = 10;

    @Column(name = "max_stock_level")
    private Integer maxStockLevel;

    @Column(name = "current_stock")
    private Integer currentStock = 0;

    @Column(name = "reserved_stock")
    private Integer reservedStock = 0;

    @Column(name = "track_stock")
    private Boolean trackStock = true;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "is_service")
    private Boolean isService = false;

    @Column(name = "tax_rate", precision = 5, scale = 2)
    private BigDecimal taxRate = BigDecimal.ZERO;

    @Size(max = 20)
    @Column(name = "unit_of_measure")
    private String unitOfMeasure = "PCS";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleItem> saleItems = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseOrderItem> purchaseOrderItems = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockMovement> stockMovements = new ArrayList<>();

    public Integer getAvailableStock() {
        return currentStock - (reservedStock != null ? reservedStock : 0);
    }

    public Boolean isLowStock() {
        return trackStock && currentStock != null && minStockLevel != null && currentStock <= minStockLevel;
    }
}