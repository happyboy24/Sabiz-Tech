package com.sabibiz.controller;

import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.dto.response.StockMovementResponse;
import com.sabibiz.enums.MovementType;
import com.sabibiz.service.StockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/businesses/{businessId}/stock-movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @GetMapping("/product/{productId}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<StockMovementResponse>> getByProduct(
            @PathVariable Long businessId,
            @PathVariable Long productId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(stockMovementService.getByProductId(businessId, productId, pageable));
    }

    @GetMapping("/type/{type}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<StockMovementResponse>> getByType(
            @PathVariable Long businessId,
            @PathVariable MovementType type,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(stockMovementService.getByType(businessId, type, pageable));
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<StockMovementResponse>> getByDateRange(
            @PathVariable Long businessId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(stockMovementService.getByDateRange(businessId, start, end, pageable));
    }
}
