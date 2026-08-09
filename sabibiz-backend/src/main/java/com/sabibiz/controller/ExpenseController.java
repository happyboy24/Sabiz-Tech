package com.sabibiz.controller;

import com.sabibiz.dto.request.ExpenseRequest;
import com.sabibiz.dto.response.ExpenseResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.enums.ExpenseCategory;
import com.sabibiz.enums.PaymentStatus;
import com.sabibiz.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/businesses/{businessId}/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<ExpenseResponse> create(
            @PathVariable Long businessId,
            @Valid @RequestBody ExpenseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.create(businessId, request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<ExpenseResponse> getById(
            @PathVariable Long businessId,
            @PathVariable Long id) {
        return ResponseEntity.ok(expenseService.getById(businessId, id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<ExpenseResponse> update(
            @PathVariable Long businessId,
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequest request) {
        return ResponseEntity.ok(expenseService.update(businessId, id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER')")
    public ResponseEntity<Void> delete(
            @PathVariable Long businessId,
            @PathVariable Long id) {
        expenseService.delete(businessId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<ExpenseResponse>> getAll(
            @PathVariable Long businessId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(expenseService.getAll(businessId, pageable));
    }

    @GetMapping("/category/{category}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<ExpenseResponse>> getByCategory(
            @PathVariable Long businessId,
            @PathVariable ExpenseCategory category,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(expenseService.getByCategory(businessId, category, pageable));
    }

    @GetMapping("/payment-status/{status}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<ExpenseResponse>> getByPaymentStatus(
            @PathVariable Long businessId,
            @PathVariable PaymentStatus status,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(expenseService.getByPaymentStatus(businessId, status, pageable));
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<ExpenseResponse>> getByDateRange(
            @PathVariable Long businessId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(expenseService.getByDateRange(businessId, start, end, pageable));
    }

    @PostMapping("/{id}/mark-paid")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<ExpenseResponse> markAsPaid(
            @PathVariable Long businessId,
            @PathVariable Long id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate paidDate) {
        return ResponseEntity.ok(expenseService.markAsPaid(businessId, id, paidDate));
    }
}
