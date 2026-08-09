package com.sabibiz.service.impl;

import com.sabibiz.dto.request.SupplierRequest;
import com.sabibiz.dto.response.SupplierResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.entity.Supplier;
import com.sabibiz.exception.ResourceNotFoundException;
import com.sabibiz.mapper.SupplierMapper;
import com.sabibiz.repository.SupplierRepository;
import com.sabibiz.service.SupplierService;
import com.sabibiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final UserService userService;

    @Override
    public SupplierResponse create(Long businessId, SupplierRequest request) {
        if (supplierRepository.existsByBusinessIdAndName(businessId, request.getName())) {
            throw new IllegalArgumentException("Supplier with this name already exists");
        }
        if (request.getEmail() != null && supplierRepository.existsByBusinessIdAndEmail(businessId, request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (request.getPhoneNumber() != null && supplierRepository.existsByBusinessIdAndPhoneNumber(businessId, request.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        Supplier supplier = supplierMapper.toEntity(request);
        supplier.setBusiness(userService.getBusinessEntity(businessId));
        return supplierMapper.toResponse(supplierRepository.save(supplier));
    }

    @Override
    @Transactional(readOnly = true)
    public SupplierResponse getById(Long businessId, Long id) {
        Supplier supplier = supplierRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        if (!supplier.getBusiness().getId().equals(businessId)) {
            throw new ResourceNotFoundException("Supplier not found with id: " + id);
        }
        return supplierMapper.toResponse(supplier);
    }

    @Override
    public SupplierResponse update(Long businessId, Long id, SupplierRequest request) {
        Supplier supplier = supplierRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        if (!supplier.getBusiness().getId().equals(businessId)) {
            throw new ResourceNotFoundException("Supplier not found with id: " + id);
        }

        if (!supplier.getName().equals(request.getName()) 
            && supplierRepository.existsByBusinessIdAndName(businessId, request.getName())) {
            throw new IllegalArgumentException("Supplier with this name already exists");
        }
        if (request.getEmail() != null && !request.getEmail().equals(supplier.getEmail()) 
            && supplierRepository.existsByBusinessIdAndEmail(businessId, request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().equals(supplier.getPhoneNumber()) 
            && supplierRepository.existsByBusinessIdAndPhoneNumber(businessId, request.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        supplierMapper.updateEntity(supplier, request);
        return supplierMapper.toResponse(supplierRepository.save(supplier));
    }

    @Override
    public void delete(Long businessId, Long id) {
        Supplier supplier = supplierRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        if (!supplier.getBusiness().getId().equals(businessId)) {
            throw new ResourceNotFoundException("Supplier not found with id: " + id);
        }
        supplierRepository.delete(supplier);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<SupplierResponse> getAll(Long businessId, Pageable pageable) {
        Page<Supplier> page = supplierRepository.findByBusinessId(businessId, pageable);
        return PageResponse.of(page.map(supplierMapper::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<SupplierResponse> search(Long businessId, String query, Pageable pageable) {
        Page<Supplier> page = supplierRepository.searchSuppliers(businessId, query, pageable);
        return PageResponse.of(page.map(supplierMapper::toResponse));
    }
}