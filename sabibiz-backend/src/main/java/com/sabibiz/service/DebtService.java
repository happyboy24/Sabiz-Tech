package com.sabibiz.service;

import com.sabibiz.dto.request.DebtRequest;
import com.sabibiz.dto.response.DebtResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.enums.DebtStatus;
import com.sabibiz.enums.DebtType;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface DebtService {
    DebtResponse create(Long businessId, DebtRequest request);
    DebtResponse getById(Long businessId, Long id);
    DebtResponse update(Long businessId, Long id, DebtRequest request);
    void delete(Long businessId, Long id);
    PageResponse<DebtResponse> getAll(Long businessId, Pageable pageable);
    PageResponse<DebtResponse> getByType(Long businessId, DebtType type, Pageable pageable);
    PageResponse<DebtResponse> getByStatus(Long businessId, DebtStatus status, Pageable pageable);
    PageResponse<DebtResponse> getByCustomer(Long businessId, Long customerId, Pageable pageable);
    PageResponse<DebtResponse> getBySupplier(Long businessId, Long supplierId, Pageable pageable);
    PageResponse<DebtResponse> getOverdue(Long businessId, Pageable pageable);
    DebtResponse recordPayment(Long businessId, Long id, BigDecimal paymentAmount);
    void updateOverdueStatuses(Long businessId);
}
