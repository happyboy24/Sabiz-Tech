package com.sabibiz.service.impl;

import com.sabibiz.dto.request.BusinessRequest;
import com.sabibiz.dto.response.BusinessResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.entity.Business;
import com.sabibiz.exception.ResourceNotFoundException;
import com.sabibiz.mapper.BusinessMapper;
import com.sabibiz.repository.BusinessRepository;
import com.sabibiz.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepository businessRepository;
    private final BusinessMapper businessMapper;

    @Override
    public BusinessResponse create(BusinessRequest request) {
        if (businessRepository.existsByBusinessNumber(request.getBusinessNumber())) {
            throw new IllegalArgumentException("Business number already exists");
        }
        if (businessRepository.existsByTaxNumber(request.getTaxNumber())) {
            throw new IllegalArgumentException("Tax number already exists");
        }
        if (businessRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
            throw new IllegalArgumentException("Registration number already exists");
        }
        if (businessRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Business business = businessMapper.toEntity(request);
        business = businessRepository.save(business);
        return businessMapper.toResponse(business);
    }

    @Override
    @Transactional(readOnly = true)
    public BusinessResponse getById(Long id) {
        Business business = businessRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Business not found with id: " + id));
        return businessMapper.toResponse(business);
    }

    @Override
    @Transactional(readOnly = true)
    public BusinessResponse getByBusinessNumber(String businessNumber) {
        Business business = businessRepository.findByBusinessNumber(businessNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Business not found with number: " + businessNumber));
        return businessMapper.toResponse(business);
    }

    @Override
    public BusinessResponse update(Long id, BusinessRequest request) {
        Business business = businessRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Business not found with id: " + id));

        if (!business.getBusinessNumber().equals(request.getBusinessNumber()) 
            && businessRepository.existsByBusinessNumber(request.getBusinessNumber())) {
            throw new IllegalArgumentException("Business number already exists");
        }
        if (!business.getTaxNumber().equals(request.getTaxNumber()) 
            && businessRepository.existsByTaxNumber(request.getTaxNumber())) {
            throw new IllegalArgumentException("Tax number already exists");
        }
        if (!business.getRegistrationNumber().equals(request.getRegistrationNumber()) 
            && businessRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
            throw new IllegalArgumentException("Registration number already exists");
        }
        if (!business.getEmail().equals(request.getEmail()) 
            && businessRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        businessMapper.updateEntity(business, request);
        business = businessRepository.save(business);
        return businessMapper.toResponse(business);
    }

    @Override
    public void delete(Long id) {
        Business business = businessRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Business not found with id: " + id));
        business.setIsActive(false);
        businessRepository.save(business);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<BusinessResponse> getAll(Pageable pageable) {
        Page<Business> page = businessRepository.findAll(pageable);
        return PageResponse.of(page.map(businessMapper::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<BusinessResponse> getActive(Pageable pageable) {
        Page<Business> page = businessRepository.findByIsActiveTrue(pageable);
        return PageResponse.of(page.map(businessMapper::toResponse));
    }
}