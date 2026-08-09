package com.sabibiz.mapper;

import com.sabibiz.dto.request.ProductRequest;
import com.sabibiz.dto.response.ProductResponse;
import com.sabibiz.entity.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setBarcode(request.getBarcode());
        product.setDescription(request.getDescription());
        product.setImageUrl(request.getImageUrl());
        product.setCostPrice(request.getCostPrice() != null ? request.getCostPrice() : BigDecimal.ZERO);
        product.setSellingPrice(request.getSellingPrice() != null ? request.getSellingPrice() : BigDecimal.ZERO);
        product.setWholesalePrice(request.getWholesalePrice());
        product.setMinStockLevel(request.getMinStockLevel() != null ? request.getMinStockLevel() : 10);
        product.setMaxStockLevel(request.getMaxStockLevel());
        product.setCurrentStock(request.getCurrentStock() != null ? request.getCurrentStock() : 0);
        product.setTrackStock(request.getTrackStock() != null ? request.getTrackStock() : true);
        product.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        product.setIsService(request.getIsService() != null ? request.getIsService() : false);
        product.setTaxRate(request.getTaxRate() != null ? request.getTaxRate() : BigDecimal.ZERO);
        product.setUnitOfMeasure(request.getUnitOfMeasure() != null ? request.getUnitOfMeasure() : "PCS");
        return product;
    }

    public ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setSku(product.getSku());
        response.setBarcode(product.getBarcode());
        response.setDescription(product.getDescription());
        response.setImageUrl(product.getImageUrl());
        response.setCostPrice(product.getCostPrice());
        response.setSellingPrice(product.getSellingPrice());
        response.setWholesalePrice(product.getWholesalePrice());
        response.setMinStockLevel(product.getMinStockLevel());
        response.setMaxStockLevel(product.getMaxStockLevel());
        response.setCurrentStock(product.getCurrentStock());
        response.setReservedStock(product.getReservedStock());
        response.setAvailableStock(product.getAvailableStock());
        response.setTrackStock(product.getTrackStock());
        response.setIsActive(product.getIsActive());
        response.setIsService(product.getIsService());
        response.setTaxRate(product.getTaxRate());
        response.setUnitOfMeasure(product.getUnitOfMeasure());
        response.setIsLowStock(product.isLowStock());
        
        if (product.getCategory() != null) {
            response.setCategoryId(product.getCategory().getId());
            response.setCategoryName(product.getCategory().getName());
        }
        if (product.getSupplier() != null) {
            response.setSupplierId(product.getSupplier().getId());
            response.setSupplierName(product.getSupplier().getName());
        }
        if (product.getBusiness() != null) {
            response.setBusinessId(product.getBusiness().getId());
            response.setBusinessName(product.getBusiness().getName());
        }
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        return response;
    }

    public void updateEntity(Product product, ProductRequest request) {
        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setBarcode(request.getBarcode());
        product.setDescription(request.getDescription());
        product.setImageUrl(request.getImageUrl());
        if (request.getCostPrice() != null) product.setCostPrice(request.getCostPrice());
        if (request.getSellingPrice() != null) product.setSellingPrice(request.getSellingPrice());
        product.setWholesalePrice(request.getWholesalePrice());
        if (request.getMinStockLevel() != null) product.setMinStockLevel(request.getMinStockLevel());
        product.setMaxStockLevel(request.getMaxStockLevel());
        if (request.getTrackStock() != null) product.setTrackStock(request.getTrackStock());
        if (request.getIsActive() != null) product.setIsActive(request.getIsActive());
        if (request.getIsService() != null) product.setIsService(request.getIsService());
        if (request.getTaxRate() != null) product.setTaxRate(request.getTaxRate());
        if (request.getUnitOfMeasure() != null) product.setUnitOfMeasure(request.getUnitOfMeasure());
    }
}