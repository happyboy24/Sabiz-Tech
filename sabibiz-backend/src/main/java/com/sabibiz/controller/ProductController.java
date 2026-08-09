package com.sabibiz.controller;

import com.sabibiz.dto.request.ProductRequest;
import com.sabibiz.dto.response.ProductResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/businesses/{businessId}/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<ProductResponse> create(@PathVariable Long businessId, @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.create(businessId, request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long businessId, @PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(businessId, id));
    }

    @GetMapping("/sku/{sku}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER')")
    public ResponseEntity<ProductResponse> getBySku(@PathVariable Long businessId, @PathVariable String sku) {
        return ResponseEntity.ok(productService.getBySku(businessId, sku));
    }

    @GetMapping("/barcode/{barcode}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER')")
    public ResponseEntity<ProductResponse> getByBarcode(@PathVariable Long businessId, @PathVariable String barcode) {
        return ResponseEntity.ok(productService.getByBarcode(businessId, barcode));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<ProductResponse> update(@PathVariable Long businessId, @PathVariable Long id, @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.update(businessId, id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER')")
    public ResponseEntity<Void> delete(@PathVariable Long businessId, @PathVariable Long id) {
        productService.delete(businessId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<ProductResponse>> getAll(@PathVariable Long businessId, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(productService.getAll(businessId, pageable));
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER')")
    public ResponseEntity<PageResponse<ProductResponse>> search(
            @PathVariable Long businessId,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean isActive,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(productService.search(businessId, q, categoryId, isActive, pageable));
    }

    @GetMapping("/low-stock")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<PageResponse<ProductResponse>> getLowStock(@PathVariable Long businessId, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(productService.getLowStock(businessId, pageable));
    }

    @PostMapping("/{id}/stock")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<ProductResponse> updateStock(@PathVariable Long businessId, @PathVariable Long id, @RequestParam Integer quantity) {
        return ResponseEntity.ok(productService.updateStock(businessId, id, quantity));
    }

    @PostMapping("/{id}/adjust-stock")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<ProductResponse> adjustStock(@PathVariable Long businessId, @PathVariable Long id, @RequestParam Integer adjustment, @RequestParam String reason) {
        return ResponseEntity.ok(productService.adjustStock(businessId, id, adjustment, reason));
    }
}