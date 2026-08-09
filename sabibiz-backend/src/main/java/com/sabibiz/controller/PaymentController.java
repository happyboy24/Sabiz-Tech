package com.sabibiz.controller;

import com.sabibiz.dto.request.PaymentRequest;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.dto.response.PaymentResponse;
import com.sabibiz.enums.PaymentStatus;
import com.sabibiz.enums.PaymentType;
import com.sabibiz.service.PaymentService;
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
@RequestMapping("/api/v1/businesses/{businessId}/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER')")
    public ResponseEntity<PaymentResponse> create(
            @PathVariable Long businessId,
            @Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.create(businessId, request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PaymentResponse> getById(
            @PathVariable Long businessId,
            @PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getById(businessId, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER')")
    public ResponseEntity<Void> delete(
            @PathVariable Long businessId,
            @PathVariable Long id) {
        paymentService.delete(businessId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<PaymentResponse>> getAll(
            @PathVariable Long businessId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(paymentService.getAll(businessId, pageable));
    }

    @GetMapping("/type/{type}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<PaymentResponse>> getByType(
            @PathVariable Long businessId,
            @PathVariable PaymentType type,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(paymentService.getByType(businessId, type, pageable));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<PaymentResponse>> getByStatus(
            @PathVariable Long businessId,
            @PathVariable PaymentStatus status,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(paymentService.getByStatus(businessId, status, pageable));
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<PaymentResponse>> getByDateRange(
            @PathVariable Long businessId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(paymentService.getByDateRange(businessId, start, end, pageable));
    }

    @GetMapping("/sale/{saleId}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<PaymentResponse>> getBySale(
            @PathVariable Long businessId,
            @PathVariable Long saleId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(paymentService.getBySale(businessId, saleId, pageable));
    }

    @PostMapping("/{id}/confirm")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<PaymentResponse> confirm(
            @PathVariable Long businessId,
            @PathVariable Long id) {
        return ResponseEntity.ok(paymentService.confirm(businessId, id));
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<PaymentResponse> cancel(
            @PathVariable Long businessId,
            @PathVariable Long id) {
        return ResponseEntity.ok(paymentService.cancel(businessId, id));
    }
}
