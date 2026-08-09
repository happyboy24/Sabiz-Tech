package com.sabibiz.service.impl;

import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.dto.response.StockMovementResponse;
import com.sabibiz.entity.Product;
import com.sabibiz.entity.StockMovement;
import com.sabibiz.entity.User;
import com.sabibiz.enums.MovementType;
import com.sabibiz.exception.ResourceNotFoundException;
import com.sabibiz.mapper.StockMovementMapper;
import com.sabibiz.repository.ProductRepository;
import com.sabibiz.repository.StockMovementRepository;
import com.sabibiz.repository.UserRepository;
import com.sabibiz.service.StockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StockMovementServiceImpl implements StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StockMovementMapper stockMovementMapper;

    @Override
    public StockMovementResponse createStockMovement(Long productId, Integer quantity, MovementType type, 
                                                      Integer stockBefore, Integer stockAfter, Long userId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        
        return createStockMovement(product, quantity, type, stockBefore, stockAfter, userId);
    }

    @Override
    public StockMovementResponse createStockMovement(Product product, Integer quantity, MovementType type, 
                                                      Integer stockBefore, Integer stockAfter, Long userId) {
        User createdBy = userId != null ? userRepository.findById(userId).orElse(null) : null;
        
        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setBusiness(product.getBusiness());
        movement.setMovementType(type);
        movement.setQuantity(quantity);
        movement.setStockBefore(stockBefore);
        movement.setStockAfter(stockAfter);
        movement.setCreatedBy(createdBy);
        movement.setMovementDate(LocalDateTime.now());
        movement.setMovementNumber("SM-" + System.currentTimeMillis());
        
        movement = stockMovementRepository.save(movement);
        return stockMovementMapper.toResponse(movement);
    }

    @Override
    public StockMovementResponse createOpeningStock(Product product, Integer quantity) {
        return createStockMovement(product, quantity, MovementType.OPENING_STOCK, 0, quantity, null);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<StockMovementResponse> getByProductId(Long businessId, Long productId, Pageable pageable) {
        Page<StockMovement> page = stockMovementRepository.findByBusinessIdAndProductId(businessId, productId, pageable);
        return PageResponse.of(page.map(stockMovementMapper::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<StockMovementResponse> getByDateRange(Long businessId, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        Page<StockMovement> page = stockMovementRepository.findByBusinessIdAndMovementDateBetween(businessId, start, end, pageable);
        return PageResponse.of(page.map(stockMovementMapper::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<StockMovementResponse> getByType(Long businessId, MovementType type, Pageable pageable) {
        Page<StockMovement> page = stockMovementRepository.findByBusinessIdAndMovementType(businessId, type, pageable);
        return PageResponse.of(page.map(stockMovementMapper::toResponse));
    }
}