package com.sabibiz.service;

import com.sabibiz.dto.request.SaleRequest;
import com.sabibiz.dto.response.SaleResponse;
import com.sabibiz.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

public interface SaleService {
    SaleResponse create(Long businessId, SaleRequest request);
    SaleResponse getById(Long businessId, Long id);
    SaleResponse update(Long businessId, Long id, SaleRequest request);
    void delete(Long businessId, Long id);
    PageResponse<SaleResponse> getAll(Long businessId, Pageable pageable);
    PageResponse<SaleResponse> getByStatus(Long businessId, com.sabibiz.enums.SaleStatus status, Pageable pageable);
    PageResponse<SaleResponse> getByDateRange(Long businessId, LocalDateTime start, LocalDateTime end, Pageable pageable);
    PageResponse<SaleResponse> getByCustomerId(Long businessId, Long customerId, Pageable pageable);
    SaleResponse completeSale(Long businessId, Long id);
    SaleResponse cancelSale(Long businessId, Long id);
    SaleResponse refundSale(Long businessId, Long id, String reason);
}