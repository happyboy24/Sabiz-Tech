package com.sabibiz.service;

import com.sabibiz.dto.request.CustomerRequest;
import com.sabibiz.dto.response.CustomerResponse;
import com.sabibiz.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    CustomerResponse create(Long businessId, CustomerRequest request);
    CustomerResponse getById(Long businessId, Long id);
    CustomerResponse update(Long businessId, Long id, CustomerRequest request);
    void delete(Long businessId, Long id);
    PageResponse<CustomerResponse> getAll(Long businessId, Pageable pageable);
    PageResponse<CustomerResponse> search(Long businessId, String query, Pageable pageable);
    CustomerResponse getByCustomerNumber(Long businessId, String customerNumber);
}