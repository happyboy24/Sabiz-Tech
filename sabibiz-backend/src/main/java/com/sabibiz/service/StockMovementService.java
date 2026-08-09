package com.sabibiz.service;

import com.sabibiz.dto.response.StockMovementResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.entity.Product;
import org.springframework.data.domain.Pageable;

public interface StockMovementService {
    StockMovementResponse createStockMovement(Long productId, Integer quantity, com.sabibiz.enums.MovementType type, 
                                               Integer stockBefore, Integer stockAfter, Long userId);
    StockMovementResponse createStockMovement(Product product, Integer quantity, com.sabibiz.enums.MovementType type, 
                                               Integer stockBefore, Integer stockAfter, Long userId);
    StockMovementResponse createOpeningStock(Product product, Integer quantity);
    PageResponse<StockMovementResponse> getByProductId(Long businessId, Long productId, Pageable pageable);
    PageResponse<StockMovementResponse> getByDateRange(Long businessId, java.time.LocalDateTime start, java.time.LocalDateTime end, Pageable pageable);
    PageResponse<StockMovementResponse> getByType(Long businessId, com.sabibiz.enums.MovementType type, Pageable pageable);
}