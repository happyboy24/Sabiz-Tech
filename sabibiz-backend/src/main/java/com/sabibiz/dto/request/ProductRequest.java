package com.sabibiz.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank
    @Size(max = 200)
    private String name;

    @Size(max = 100)
    private String sku;

    @Size(max = 50)
    private String barcode;

    @Size(max = 1000)
    private String description;

    private String imageUrl;

    @NotNull
    private BigDecimal costPrice = BigDecimal.ZERO;

    @NotNull
    private BigDecimal sellingPrice = BigDecimal.ZERO;

    private BigDecimal wholesalePrice;

    private Integer minStockLevel = 10;

    private Integer maxStockLevel;

    private Integer currentStock = 0;

    private Boolean trackStock = true;

    private Boolean isActive = true;

    private Boolean isService = false;

    private BigDecimal taxRate = BigDecimal.ZERO;

    @Size(max = 20)
    private String unitOfMeasure = "PCS";

    private Long categoryId;

    private Long supplierId;
}