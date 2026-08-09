package com.sabibiz.controller;

import com.sabibiz.dto.request.SupplierRequest;
import com.sabibiz.dto.response.SupplierResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/businesses/{businessId}/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<SupplierResponse> create(@PathVariable Long businessId, @RequestBody SupplierRequest request) {
        return ResponseEntity.ok(supplierService.create(businessId, request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<SupplierResponse> getById(@PathVariable Long businessId, @PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getById(businessId, id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<SupplierResponse> update(@PathVariable Long businessId, @PathVariable Long id, @RequestBody SupplierRequest request) {
        return ResponseEntity.ok(supplierService.update(businessId, id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER')")
    public ResponseEntity<Void> delete(@PathVariable Long businessId, @PathVariable Long id) {
        supplierService.delete(businessId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<SupplierResponse>> getAll(@PathVariable Long businessId, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(supplierService.getAll(businessId, pageable));
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER')")
    public ResponseEntity<PageResponse<SupplierResponse>> search(@PathVariable Long businessId, @RequestParam String q, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(supplierService.search(businessId, q, pageable));
    }
}