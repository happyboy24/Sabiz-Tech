package com.sabibiz.service;

import com.sabibiz.dto.request.SupplierRequest;
import com.sabibiz.dto.response.SupplierResponse;
import com.sabibiz.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface SupplierService {
    SupplierResponse create(Long businessId, SupplierRequest request);
    SupplierResponse getById(Long businessId, Long id);
    SupplierResponse update(Long businessId, Long id, SupplierRequest request);
    void delete(Long businessId, Long id);
    PageResponse<SupplierResponse> getAll(Long businessId, Pageable pageable);
    PageResponse<SupplierResponse> search(Long businessId, String query, Pageable pageable);
}