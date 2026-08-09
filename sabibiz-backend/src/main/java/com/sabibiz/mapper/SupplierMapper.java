package com.sabibiz.mapper;

import com.sabibiz.dto.request.SupplierRequest;
import com.sabibiz.dto.response.SupplierResponse;
import com.sabibiz.entity.Supplier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SupplierMapper {

    public Supplier toEntity(SupplierRequest request) {
        Supplier supplier = new Supplier();
        supplier.setName(request.getName());
        supplier.setEmail(request.getEmail());
        supplier.setPhoneNumber(request.getPhoneNumber());
        supplier.setAlternatePhone(request.getAlternatePhone());
        supplier.setAddress(request.getAddress());
        supplier.setCity(request.getCity());
        supplier.setCountry(request.getCountry());
        supplier.setPostalCode(request.getPostalCode());
        supplier.setTaxNumber(request.getTaxNumber());
        supplier.setContactPerson(request.getContactPerson());
        supplier.setPaymentTermsDays(request.getPaymentTermsDays() != null ? request.getPaymentTermsDays() : 30);
        supplier.setCreditLimit(request.getCreditLimit() != null ? request.getCreditLimit() : BigDecimal.ZERO);
        supplier.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        supplier.setSupplierSince(request.getSupplierSince());
        supplier.setNotes(request.getNotes());
        return supplier;
    }

    public SupplierResponse toResponse(Supplier supplier) {
        SupplierResponse response = new SupplierResponse();
        response.setId(supplier.getId());
        response.setName(supplier.getName());
        response.setEmail(supplier.getEmail());
        response.setPhoneNumber(supplier.getPhoneNumber());
        response.setAlternatePhone(supplier.getAlternatePhone());
        response.setAddress(supplier.getAddress());
        response.setCity(supplier.getCity());
        response.setCountry(supplier.getCountry());
        response.setPostalCode(supplier.getPostalCode());
        response.setTaxNumber(supplier.getTaxNumber());
        response.setContactPerson(supplier.getContactPerson());
        response.setPaymentTermsDays(supplier.getPaymentTermsDays());
        response.setCreditLimit(supplier.getCreditLimit());
        response.setCurrentBalance(supplier.getCurrentBalance());
        response.setAvailableCredit(supplier.getAvailableCredit());
        response.setIsActive(supplier.getIsActive());
        response.setSupplierSince(supplier.getSupplierSince());
        response.setNotes(supplier.getNotes());
        if (supplier.getBusiness() != null) {
            response.setBusinessId(supplier.getBusiness().getId());
            response.setBusinessName(supplier.getBusiness().getName());
        }
        response.setCreatedAt(supplier.getCreatedAt());
        response.setUpdatedAt(supplier.getUpdatedAt());
        return response;
    }

    public void updateEntity(Supplier supplier, SupplierRequest request) {
        supplier.setName(request.getName());
        supplier.setEmail(request.getEmail());
        supplier.setPhoneNumber(request.getPhoneNumber());
        supplier.setAlternatePhone(request.getAlternatePhone());
        supplier.setAddress(request.getAddress());
        supplier.setCity(request.getCity());
        supplier.setCountry(request.getCountry());
        supplier.setPostalCode(request.getPostalCode());
        supplier.setTaxNumber(request.getTaxNumber());
        supplier.setContactPerson(request.getContactPerson());
        if (request.getPaymentTermsDays() != null) supplier.setPaymentTermsDays(request.getPaymentTermsDays());
        if (request.getCreditLimit() != null) supplier.setCreditLimit(request.getCreditLimit());
        if (request.getIsActive() != null) supplier.setIsActive(request.getIsActive());
        supplier.setSupplierSince(request.getSupplierSince());
        supplier.setNotes(request.getNotes());
    }
}