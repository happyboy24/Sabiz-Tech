package com.sabibiz.service;

import com.sabibiz.dto.request.PaymentRequest;
import com.sabibiz.dto.response.PaymentResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.enums.PaymentStatus;
import com.sabibiz.enums.PaymentType;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface PaymentService {
    PaymentResponse create(Long businessId, PaymentRequest request);
    PaymentResponse getById(Long businessId, Long id);
    void delete(Long businessId, Long id);
    PageResponse<PaymentResponse> getAll(Long businessId, Pageable pageable);
    PageResponse<PaymentResponse> getByType(Long businessId, PaymentType type, Pageable pageable);
    PageResponse<PaymentResponse> getByStatus(Long businessId, PaymentStatus status, Pageable pageable);
    PageResponse<PaymentResponse> getByDateRange(Long businessId, LocalDate start, LocalDate end, Pageable pageable);
    PageResponse<PaymentResponse> getBySale(Long businessId, Long saleId, Pageable pageable);
    PaymentResponse confirm(Long businessId, Long id);
    PaymentResponse cancel(Long businessId, Long id);
}
