package com.sabibiz.controller;

import com.sabibiz.dto.request.SaleRequest;
import com.sabibiz.dto.response.SaleResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.enums.SaleStatus;
import com.sabibiz.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/businesses/{businessId}/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER')")
    public ResponseEntity<SaleResponse> create(
            @PathVariable Long businessId,
            @Valid @RequestBody SaleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.create(businessId, request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<SaleResponse> getById(
            @PathVariable Long businessId,
            @PathVariable Long id) {
        return ResponseEntity.ok(saleService.getById(businessId, id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER')")
    public ResponseEntity<SaleResponse> update(
            @PathVariable Long businessId,
            @PathVariable Long id,
            @Valid @RequestBody SaleRequest request) {
        return ResponseEntity.ok(saleService.update(businessId, id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<Void> delete(
            @PathVariable Long businessId,
            @PathVariable Long id) {
        saleService.delete(businessId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<SaleResponse>> getAll(
            @PathVariable Long businessId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(saleService.getAll(businessId, pageable));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<SaleResponse>> getByStatus(
            @PathVariable Long businessId,
            @PathVariable SaleStatus status,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(saleService.getByStatus(businessId, status, pageable));
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<SaleResponse>> getByDateRange(
            @PathVariable Long businessId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(saleService.getByDateRange(businessId, start, end, pageable));
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<SaleResponse>> getByCustomer(
            @PathVariable Long businessId,
            @PathVariable Long customerId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(saleService.getByCustomerId(businessId, customerId, pageable));
    }

    @PostMapping("/{id}/complete")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER')")
    public ResponseEntity<SaleResponse> completeSale(
            @PathVariable Long businessId,
            @PathVariable Long id) {
        return ResponseEntity.ok(saleService.completeSale(businessId, id));
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<SaleResponse> cancelSale(
            @PathVariable Long businessId,
            @PathVariable Long id) {
        return ResponseEntity.ok(saleService.cancelSale(businessId, id));
    }

    @PostMapping("/{id}/refund")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<SaleResponse> refundSale(
            @PathVariable Long businessId,
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "") String reason) {
        return ResponseEntity.ok(saleService.refundSale(businessId, id, reason));
    }
}
