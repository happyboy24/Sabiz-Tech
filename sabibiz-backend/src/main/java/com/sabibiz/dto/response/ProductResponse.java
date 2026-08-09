package com.sabibiz.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private String sku;
    private String barcode;
    private String description;
    private String imageUrl;
    private BigDecimal costPrice;
    private BigDecimal sellingPrice;
    private BigDecimal wholesalePrice;
    private Integer minStockLevel;
    private Integer maxStockLevel;
    private Integer currentStock;
    private Integer reservedStock;
    private Integer availableStock;
    private Boolean trackStock;
    private Boolean isActive;
    private Boolean isService;
    private BigDecimal taxRate;
    private String unitOfMeasure;
    private Long categoryId;
    private String categoryName;
    private Long supplierId;
    private String supplierName;
    private Boolean isLowStock;
    private Long businessId;
    private String businessName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}