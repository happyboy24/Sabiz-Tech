package com.sabibiz.mapper;

import com.sabibiz.dto.request.CategoryRequest;
import com.sabibiz.dto.response.CategoryResponse;
import com.sabibiz.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());
        category.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        category.setSortOrder(request.getSortOrder() != null ? request.getSortOrder() : 0);
        return category;
    }

    public CategoryResponse toResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setImageUrl(category.getImageUrl());
        response.setIsActive(category.getIsActive());
        response.setSortOrder(category.getSortOrder());
        if (category.getParent() != null) {
            response.setParentId(category.getParent().getId());
            response.setParentName(category.getParent().getName());
        }
        if (category.getChildren() != null && !category.getChildren().isEmpty()) {
            response.setChildren(category.getChildren().stream()
                .map(this::toResponse)
                .collect(Collectors.toList()));
        }
        if (category.getBusiness() != null) {
            response.setBusinessId(category.getBusiness().getId());
            response.setBusinessName(category.getBusiness().getName());
        }
        response.setProductCount(category.getProducts() != null ? category.getProducts().size() : 0);
        response.setCreatedAt(category.getCreatedAt());
        response.setUpdatedAt(category.getUpdatedAt());
        return response;
    }

    public void updateEntity(Category category, CategoryRequest request) {
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());
        if (request.getIsActive() != null) {
            category.setIsActive(request.getIsActive());
        }
        if (request.getSortOrder() != null) {
            category.setSortOrder(request.getSortOrder());
        }
    }
}