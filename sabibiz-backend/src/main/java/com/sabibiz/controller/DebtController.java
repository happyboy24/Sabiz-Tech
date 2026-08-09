package com.sabibiz.controller;

import com.sabibiz.dto.request.DebtRequest;
import com.sabibiz.dto.response.DebtResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.enums.DebtStatus;
import com.sabibiz.enums.DebtType;
import com.sabibiz.service.DebtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/businesses/{businessId}/debts")
@RequiredArgsConstructor
public class DebtController {

    private final DebtService debtService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<DebtResponse> create(
            @PathVariable Long businessId,
            @Valid @RequestBody DebtRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(debtService.create(businessId, request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<DebtResponse> getById(
            @PathVariable Long businessId,
            @PathVariable Long id) {
        return ResponseEntity.ok(debtService.getById(businessId, id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<DebtResponse> update(
            @PathVariable Long businessId,
            @PathVariable Long id,
            @Valid @RequestBody DebtRequest request) {
        return ResponseEntity.ok(debtService.update(businessId, id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER')")
    public ResponseEntity<Void> delete(
            @PathVariable Long businessId,
            @PathVariable Long id) {
        debtService.delete(businessId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<DebtResponse>> getAll(
            @PathVariable Long businessId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(debtService.getAll(businessId, pageable));
    }

    @GetMapping("/type/{type}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<DebtResponse>> getByType(
            @PathVariable Long businessId,
            @PathVariable DebtType type,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(debtService.getByType(businessId, type, pageable));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<DebtResponse>> getByStatus(
            @PathVariable Long businessId,
            @PathVariable DebtStatus status,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(debtService.getByStatus(businessId, status, pageable));
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<DebtResponse>> getByCustomer(
            @PathVariable Long businessId,
            @PathVariable Long customerId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(debtService.getByCustomer(businessId, customerId, pageable));
    }

    @GetMapping("/supplier/{supplierId}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<DebtResponse>> getBySupplier(
            @PathVariable Long businessId,
            @PathVariable Long supplierId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(debtService.getBySupplier(businessId, supplierId, pageable));
    }

    @GetMapping("/overdue")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<DebtResponse>> getOverdue(
            @PathVariable Long businessId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(debtService.getOverdue(businessId, pageable));
    }

    @PostMapping("/{id}/payment")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<DebtResponse> recordPayment(
            @PathVariable Long businessId,
            @PathVariable Long id,
            @RequestBody Map<String, BigDecimal> body) {
        BigDecimal amount = body.get("amount");
        return ResponseEntity.ok(debtService.recordPayment(businessId, id, amount));
    }
}
