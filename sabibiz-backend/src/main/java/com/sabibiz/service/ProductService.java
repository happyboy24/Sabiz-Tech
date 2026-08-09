package com.sabibiz.service;

import com.sabibiz.dto.request.ProductRequest;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.dto.response.ProductResponse;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponse create(Long businessId, ProductRequest request);
    ProductResponse getById(Long businessId, Long id);
    ProductResponse update(Long businessId, Long id, ProductRequest request);
    void delete(Long businessId, Long id);
    PageResponse<ProductResponse> getAll(Long businessId, Pageable pageable);
    PageResponse<ProductResponse> getActive(Long businessId, Pageable pageable);
    PageResponse<ProductResponse> getLowStock(Long businessId, Pageable pageable);
    PageResponse<ProductResponse> getByCategory(Long businessId, Long categoryId, Pageable pageable);
    PageResponse<ProductResponse> search(Long businessId, String query, Long categoryId, Boolean isActive, Pageable pageable);
    ProductResponse getBySku(Long businessId, String sku);
    ProductResponse getByBarcode(Long businessId, String barcode);
    ProductResponse updateStock(Long businessId, Long id, Integer quantity);
    ProductResponse adjustStock(Long businessId, Long id, Integer adjustment, String reason);
}