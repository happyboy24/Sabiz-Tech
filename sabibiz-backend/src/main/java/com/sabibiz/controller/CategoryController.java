package com.sabibiz.controller;

import com.sabibiz.dto.request.CategoryRequest;
import com.sabibiz.dto.response.CategoryResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/businesses/{businessId}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<CategoryResponse> create(@PathVariable Long businessId, @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.create(businessId, request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long businessId, @PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(businessId, id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long businessId, @PathVariable Long id, @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.update(businessId, id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER')")
    public ResponseEntity<Void> delete(@PathVariable Long businessId, @PathVariable Long id) {
        categoryService.delete(businessId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<CategoryResponse>> getAll(@PathVariable Long businessId, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(categoryService.getAll(businessId, pageable));
    }

    @GetMapping("/roots")
    @PreAuthorize("hasRole('ROLE_BUSINESS_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<PageResponse<CategoryResponse>> getRootCategories(@PathVariable Long businessId, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(categoryService.getRootCategories(businessId, pageable));
    }
}