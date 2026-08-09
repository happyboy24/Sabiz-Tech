package com.sabibiz.mapper;

import com.sabibiz.dto.request.SaleRequest;
import com.sabibiz.dto.response.SaleItemResponse;
import com.sabibiz.dto.response.SaleResponse;
import com.sabibiz.entity.Sale;
import com.sabibiz.entity.SaleItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SaleMapper {

    public SaleResponse toResponse(Sale sale) {
        SaleResponse response = new SaleResponse();
        response.setId(sale.getId());
        response.setSaleNumber(sale.getSaleNumber());
        response.setStatus(sale.getStatus());
        response.setPaymentStatus(sale.getPaymentStatus());
        response.setSaleDate(sale.getSaleDate());
        response.setSubtotal(sale.getSubtotal());
        response.setTaxAmount(sale.getTaxAmount());
        response.setDiscountAmount(sale.getDiscountAmount());
        response.setTotalAmount(sale.getTotalAmount());
        response.setPaidAmount(sale.getPaidAmount());
        response.setChangeAmount(sale.getChangeAmount());
        response.setDueAmount(sale.getDueAmount());
        response.setPaymentMethod(sale.getPaymentMethod());
        response.setReferenceNumber(sale.getReferenceNumber());
        response.setNotes(sale.getNotes());
        response.setCompletedAt(sale.getCompletedAt());
        
        if (sale.getCustomer() != null) {
            response.setCustomerId(sale.getCustomer().getId());
            response.setCustomerName(sale.getCustomer().getName());
        }
        
        if (sale.getCashier() != null) {
            response.setCashierId(sale.getCashier().getId());
            response.setCashierName(sale.getCashier().getFullName());
        }
        
        if (sale.getBusiness() != null) {
            response.setBusinessId(sale.getBusiness().getId());
            response.setBusinessName(sale.getBusiness().getName());
        }
        
        if (sale.getItems() != null) {
            response.setItems(sale.getItems().stream()
                .map(this::toItemResponse)
                .collect(Collectors.toList()));
        }
        
        response.setCreatedAt(sale.getCreatedAt());
        response.setUpdatedAt(sale.getUpdatedAt());
        return response;
    }

    public SaleItemResponse toItemResponse(SaleItem item) {
        SaleItemResponse response = new SaleItemResponse();
        response.setId(item.getId());
        response.setSaleId(item.getSale().getId());
        response.setProductId(item.getProduct().getId());
        response.setProductName(item.getProduct().getName());
        response.setProductSku(item.getProduct().getSku());
        response.setQuantity(item.getQuantity());
        response.setUnitPrice(item.getUnitPrice());
        response.setDiscountPercent(item.getDiscountPercent());
        response.setDiscountAmount(item.getDiscountAmount());
        response.setTaxRate(item.getTaxRate());
        response.setTaxAmount(item.getTaxAmount());
        response.setLineTotal(item.getLineTotal());
        response.setUnitOfMeasure(item.getUnitOfMeasure());
        response.setCreatedAt(item.getCreatedAt());
        response.setUpdatedAt(item.getUpdatedAt());
        return response;
    }
}