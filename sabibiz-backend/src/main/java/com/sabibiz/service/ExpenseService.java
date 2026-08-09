package com.sabibiz.service;

import com.sabibiz.dto.request.ExpenseRequest;
import com.sabibiz.dto.response.ExpenseResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.enums.ExpenseCategory;
import com.sabibiz.enums.PaymentStatus;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ExpenseService {
    ExpenseResponse create(Long businessId, ExpenseRequest request);
    ExpenseResponse getById(Long businessId, Long id);
    ExpenseResponse update(Long businessId, Long id, ExpenseRequest request);
    void delete(Long businessId, Long id);
    PageResponse<ExpenseResponse> getAll(Long businessId, Pageable pageable);
    PageResponse<ExpenseResponse> getByCategory(Long businessId, ExpenseCategory category, Pageable pageable);
    PageResponse<ExpenseResponse> getByPaymentStatus(Long businessId, PaymentStatus status, Pageable pageable);
    PageResponse<ExpenseResponse> getByDateRange(Long businessId, LocalDate start, LocalDate end, Pageable pageable);
    ExpenseResponse markAsPaid(Long businessId, Long id, LocalDate paidDate);
}
