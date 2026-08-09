package com.sabibiz.controller;

import com.sabibiz.dto.request.CustomerRequest;
import com.sabibiz.dto.response.CustomerResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/businesses/{businessId}/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<CustomerResponse> create(@PathVariable Long businessId, @RequestBody CustomerRequest request) {
        return ResponseEntity.ok(customerService.create(businessId, request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<CustomerResponse> getById(@PathVariable Long businessId, @PathVariable Long id) {
        return ResponseEntity.ok(customerService.getById(businessId, id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<CustomerResponse> update(@PathVariable Long businessId, @PathVariable Long id, @RequestBody CustomerRequest request) {
        return ResponseEntity.ok(customerService.update(businessId, id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER')")
    public ResponseEntity<Void> delete(@PathVariable Long businessId, @PathVariable Long id) {
        customerService.delete(businessId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<CustomerResponse>> getAll(@PathVariable Long businessId, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(customerService.getAll(businessId, pageable));
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER')")
    public ResponseEntity<PageResponse<CustomerResponse>> search(@PathVariable Long businessId, @RequestParam String q, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(customerService.search(businessId, q, pageable));
    }
}