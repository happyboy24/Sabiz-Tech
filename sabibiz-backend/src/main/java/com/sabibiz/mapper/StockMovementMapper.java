package com.sabibiz.mapper;

import com.sabibiz.dto.response.StockMovementResponse;
import com.sabibiz.entity.StockMovement;
import com.sabibiz.enums.MovementType;
import org.springframework.stereotype.Component;

@Component
public class StockMovementMapper {

    public StockMovementResponse toResponse(StockMovement movement) {
        StockMovementResponse response = new StockMovementResponse();
        response.setId(movement.getId());
        response.setMovementNumber(movement.getMovementNumber());
        response.setMovementType(movement.getMovementType());
        response.setMovementTypeDisplayName(getMovementTypeDisplayName(movement.getMovementType()));
        
        if (movement.getProduct() != null) {
            response.setProductId(movement.getProduct().getId());
            response.setProductName(movement.getProduct().getName());
            response.setProductSku(movement.getProduct().getSku());
        }
        
        response.setQuantity(movement.getQuantity());
        response.setUnitCost(movement.getUnitCost());
        response.setTotalCost(movement.getTotalCost());
        response.setStockBefore(movement.getStockBefore());
        response.setStockAfter(movement.getStockAfter());
        response.setReferenceType(movement.getReferenceType());
        response.setReferenceId(movement.getReferenceId());
        response.setReferenceNumber(movement.getReferenceNumber());
        response.setUnitOfMeasure(movement.getUnitOfMeasure());
        response.setNotes(movement.getNotes());
        
        if (movement.getCreatedBy() != null) {
            response.setCreatedById(movement.getCreatedBy().getId());
            response.setCreatedByName(movement.getCreatedBy().getFullName());
        }
        
        response.setMovementDate(movement.getMovementDate());
        
        if (movement.getBusiness() != null) {
            response.setBusinessId(movement.getBusiness().getId());
            response.setBusinessName(movement.getBusiness().getName());
        }
        
        response.setCreatedAt(movement.getCreatedAt());
        response.setUpdatedAt(movement.getUpdatedAt());
        return response;
    }

    private String getMovementTypeDisplayName(MovementType type) {
        if (type == null) return null;
        return switch (type) {
            case PURCHASE_RECEIPT -> "Purchase Receipt";
            case PURCHASE_RETURN -> "Purchase Return";
            case SALE -> "Sale";
            case SALE_RETURN -> "Sale Return";
            case STOCK_ADJUSTMENT_IN -> "Stock Adjustment In";
            case STOCK_ADJUSTMENT_OUT -> "Stock Adjustment Out";
            case STOCK_TRANSFER_IN -> "Stock Transfer In";
            case STOCK_TRANSFER_OUT -> "Stock Transfer Out";
            case OPENING_STOCK -> "Opening Stock";
            case DAMAGE_LOSS -> "Damage/Loss";
            case EXPIRY_WRITE_OFF -> "Expiry Write-off";
            case INVENTORY_COUNT_ADJUSTMENT -> "Inventory Count Adjustment";
            default -> type.name();
        };
    }
}