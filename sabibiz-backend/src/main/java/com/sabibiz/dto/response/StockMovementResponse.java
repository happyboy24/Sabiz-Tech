package com.sabibiz.dto.response;

import com.sabibiz.enums.MovementType;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovementResponse {

    private Long id;
    private String movementNumber;
    private MovementType movementType;
    private String movementTypeDisplayName;
    private Long productId;
    private String productName;
    private String productSku;
    private Integer quantity;
    private BigDecimal unitCost;
    private BigDecimal totalCost;
    private Integer stockBefore;
    private Integer stockAfter;
    private String referenceType;
    private Long referenceId;
    private String referenceNumber;
    private String unitOfMeasure;
    private String notes;
    private Long createdById;
    private String createdByName;
    private LocalDateTime movementDate;
    private Long businessId;
    private String businessName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}