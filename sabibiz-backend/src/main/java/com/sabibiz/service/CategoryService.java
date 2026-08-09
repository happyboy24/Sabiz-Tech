package com.sabibiz.service;

import com.sabibiz.dto.request.CategoryRequest;
import com.sabibiz.dto.response.CategoryResponse;
import com.sabibiz.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryResponse create(Long businessId, CategoryRequest request);
    CategoryResponse getById(Long businessId, Long id);
    CategoryResponse update(Long businessId, Long id, CategoryRequest request);
    void delete(Long businessId, Long id);
    PageResponse<CategoryResponse> getAll(Long businessId, Pageable pageable);
    PageResponse<CategoryResponse> getRootCategories(Long businessId, Pageable pageable);
    CategoryResponse getByName(Long businessId, String name);
}