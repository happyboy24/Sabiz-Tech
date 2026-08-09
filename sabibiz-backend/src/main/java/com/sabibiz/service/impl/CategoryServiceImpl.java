package com.sabibiz.service.impl;

import com.sabibiz.dto.request.CategoryRequest;
import com.sabibiz.dto.response.CategoryResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.entity.Category;
import com.sabibiz.exception.ResourceNotFoundException;
import com.sabibiz.mapper.CategoryMapper;
import com.sabibiz.repository.CategoryRepository;
import com.sabibiz.service.CategoryService;
import com.sabibiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final UserService userService;

    @Override
    public CategoryResponse create(Long businessId, CategoryRequest request) {
        if (categoryRepository.existsByBusinessIdAndName(businessId, request.getName())) {
            throw new IllegalArgumentException("Category with this name already exists");
        }

        Category category = categoryMapper.toEntity(request);
        category.setBusiness(userService.getBusinessEntity(businessId));
        
        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
            category.setParent(parent);
        }
        
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse getById(Long businessId, Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        if (!category.getBusiness().getId().equals(businessId)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse update(Long businessId, Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        if (!category.getBusiness().getId().equals(businessId)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }

        if (!category.getName().equals(request.getName()) 
            && categoryRepository.existsByBusinessIdAndName(businessId, request.getName())) {
            throw new IllegalArgumentException("Category with this name already exists");
        }

        categoryMapper.updateEntity(category, request);
        
        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
            category.setParent(parent);
        } else {
            category.setParent(null);
        }
        
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public void delete(Long businessId, Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        if (!category.getBusiness().getId().equals(businessId)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.delete(category);
    }

    @Override
    public PageResponse<CategoryResponse> getAll(Long businessId, Pageable pageable) {
        Page<Category> page = categoryRepository.findByBusinessId(businessId, pageable);
        return PageResponse.of(page.map(categoryMapper::toResponse));
    }

    @Override
    public PageResponse<CategoryResponse> getRootCategories(Long businessId, Pageable pageable) {
        Page<Category> page = categoryRepository.findByBusinessIdAndParentIsNull(businessId, pageable);
        return PageResponse.of(page.map(categoryMapper::toResponse));
    }

    @Override
    public CategoryResponse getByName(Long businessId, String name) {
        Category category = categoryRepository.findByBusinessIdAndName(businessId, name)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with name: " + name));
        return categoryMapper.toResponse(category);
    }
}