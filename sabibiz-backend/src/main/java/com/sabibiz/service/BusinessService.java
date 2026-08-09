package com.sabibiz.service;

import com.sabibiz.dto.request.BusinessRequest;
import com.sabibiz.dto.response.BusinessResponse;
import com.sabibiz.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface BusinessService {
    BusinessResponse create(BusinessRequest request);
    BusinessResponse getById(Long id);
    BusinessResponse getByBusinessNumber(String businessNumber);
    BusinessResponse update(Long id, BusinessRequest request);
    void delete(Long id);
    PageResponse<BusinessResponse> getAll(Pageable pageable);
    PageResponse<BusinessResponse> getActive(Pageable pageable);
}