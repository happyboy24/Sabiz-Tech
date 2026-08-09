package com.sabibiz.service.impl;

import com.sabibiz.dto.request.SaleRequest;
import com.sabibiz.dto.response.SaleResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.entity.Customer;
import com.sabibiz.entity.Product;
import com.sabibiz.entity.Sale;
import com.sabibiz.entity.SaleItem;
import com.sabibiz.enums.PaymentMethod;
import com.sabibiz.enums.PaymentStatus;
import com.sabibiz.enums.SaleStatus;
import com.sabibiz.exception.ResourceNotFoundException;
import com.sabibiz.mapper.SaleMapper;
import com.sabibiz.repository.CustomerRepository;
import com.sabibiz.repository.ProductRepository;
import com.sabibiz.repository.SaleRepository;
import com.sabibiz.service.SaleService;
import com.sabibiz.service.StockMovementService;
import com.sabibiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final SaleMapper saleMapper;
    private final StockMovementService stockMovementService;
    private final UserService userService;

    @Override
    public SaleResponse create(Long businessId, SaleRequest request) {
        Customer customer = null;
        if (request.getCustomerId() != null) {
            customer = customerRepository.findByBusinessIdAndId(businessId, request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        }

        Sale sale = new Sale();
        sale.setBusiness(userService.getBusinessEntity(businessId));
        sale.setCustomer(customer);
        sale.setCashier(userService.getCurrentUser());
        sale.setSaleDate(LocalDateTime.now());
        sale.setStatus(SaleStatus.PENDING);
        sale.setPaymentStatus(PaymentStatus.PENDING);
        sale.setDiscountAmount(request.getDiscountAmount() != null ? request.getDiscountAmount() : BigDecimal.ZERO);
        sale.setTaxAmount(request.getTaxAmount() != null ? request.getTaxAmount() : BigDecimal.ZERO);
        sale.setPaymentMethod(request.getPaymentMethod() != null ? 
            PaymentMethod.valueOf(request.getPaymentMethod()) : PaymentMethod.CASH);
        sale.setSaleNumber(request.getReferenceNumber() != null && !request.getReferenceNumber().isBlank() ?
                request.getReferenceNumber() : "SALE-" + System.currentTimeMillis());
        sale.setReferenceNumber(request.getReferenceNumber());
        sale.setNotes(request.getNotes());

        if (request.getItems() != null && !request.getItems().isEmpty()) {
            for (SaleRequest.SaleItemRequest itemRequest : request.getItems()) {
                Product product = productRepository.findByBusinessIdAndId(businessId, itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + itemRequest.getProductId()));
                
                if (product.getTrackStock() && product.getCurrentStock() < itemRequest.getQuantity()) {
                    throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
                }

                SaleItem item = new SaleItem();
                item.setSale(sale);
                item.setProduct(product);
                item.setQuantity(itemRequest.getQuantity());
                item.setUnitPrice(itemRequest.getUnitPrice());
                item.setDiscountPercent(itemRequest.getDiscountPercent() != null ? itemRequest.getDiscountPercent() : BigDecimal.ZERO);
                item.setDiscountAmount(itemRequest.getDiscountAmount() != null ? itemRequest.getDiscountAmount() : BigDecimal.ZERO);
                item.setTaxRate(itemRequest.getTaxRate() != null ? itemRequest.getTaxRate() : BigDecimal.ZERO);
                item.setUnitOfMeasure(itemRequest.getUnitOfMeasure());
                item.calculateLineTotal();
                sale.getItems().add(item);
            }
        }

        sale.calculateTotals();
        sale = saleRepository.save(sale);
        return saleMapper.toResponse(sale);
    }

    @Override
    @Transactional(readOnly = true)
    public SaleResponse getById(Long businessId, Long id) {
        Sale sale = saleRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + id));
        return saleMapper.toResponse(sale);
    }

    @Override
    public SaleResponse update(Long businessId, Long id, SaleRequest request) {
        Sale sale = saleRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + id));

        if (sale.getStatus() != SaleStatus.PENDING) {
            throw new IllegalStateException("Only pending sales can be updated");
        }

        if (request.getCustomerId() != null) {
            Customer customer = customerRepository.findByBusinessIdAndId(businessId, request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
            sale.setCustomer(customer);
        }

        sale.setDiscountAmount(request.getDiscountAmount() != null ? request.getDiscountAmount() : BigDecimal.ZERO);
        sale.setTaxAmount(request.getTaxAmount() != null ? request.getTaxAmount() : BigDecimal.ZERO);
        sale.setPaymentMethod(request.getPaymentMethod() != null ? 
            PaymentMethod.valueOf(request.getPaymentMethod()) : PaymentMethod.CASH);
        sale.setReferenceNumber(request.getReferenceNumber());
        sale.setNotes(request.getNotes());

        sale.getItems().clear();
        if (request.getItems() != null && !request.getItems().isEmpty()) {
            for (SaleRequest.SaleItemRequest itemRequest : request.getItems()) {
                Product product = productRepository.findByBusinessIdAndId(businessId, itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + itemRequest.getProductId()));
                
                SaleItem item = new SaleItem();
                item.setSale(sale);
                item.setProduct(product);
                item.setQuantity(itemRequest.getQuantity());
                item.setUnitPrice(itemRequest.getUnitPrice());
                item.setDiscountPercent(itemRequest.getDiscountPercent() != null ? itemRequest.getDiscountPercent() : BigDecimal.ZERO);
                item.setDiscountAmount(itemRequest.getDiscountAmount() != null ? itemRequest.getDiscountAmount() : BigDecimal.ZERO);
                item.setTaxRate(itemRequest.getTaxRate() != null ? itemRequest.getTaxRate() : BigDecimal.ZERO);
                item.setUnitOfMeasure(itemRequest.getUnitOfMeasure());
                item.calculateLineTotal();
                sale.getItems().add(item);
            }
        }

        sale.calculateTotals();
        sale = saleRepository.save(sale);
        return saleMapper.toResponse(sale);
    }

    @Override
    public void delete(Long businessId, Long id) {
        Sale sale = saleRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + id));
        
        if (sale.getStatus() != SaleStatus.PENDING && sale.getStatus() != SaleStatus.CANCELLED) {
            throw new IllegalStateException("Only pending or cancelled sales can be deleted");
        }
        saleRepository.delete(sale);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<SaleResponse> getAll(Long businessId, Pageable pageable) {
        Page<Sale> page = saleRepository.findByBusinessId(businessId, pageable);
        return PageResponse.of(page.map(saleMapper::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<SaleResponse> getByStatus(Long businessId, SaleStatus status, Pageable pageable) {
        List<Sale> sales = saleRepository.findByBusinessIdAndStatus(businessId, status);
        Page<Sale> page = new org.springframework.data.domain.PageImpl<>(sales, pageable, sales.size());
        return PageResponse.of(page.map(saleMapper::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<SaleResponse> getByDateRange(Long businessId, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        Page<Sale> page = saleRepository.findByBusinessIdAndSaleDateBetween(businessId, start, end, pageable);
        return PageResponse.of(page.map(saleMapper::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<SaleResponse> getByCustomerId(Long businessId, Long customerId, Pageable pageable) {
        List<Sale> sales = saleRepository.findByBusinessIdAndCustomerId(businessId, customerId);
        Page<Sale> page = new org.springframework.data.domain.PageImpl<>(sales, pageable, sales.size());
        return PageResponse.of(page.map(saleMapper::toResponse));
    }

    @Override
    public SaleResponse completeSale(Long businessId, Long id) {
        Sale sale = saleRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + id));

        if (sale.getStatus() != SaleStatus.PENDING) {
            throw new IllegalStateException("Only pending sales can be completed");
        }

        for (SaleItem item : sale.getItems()) {
            Product product = item.getProduct();
            if (product.getTrackStock()) {
                Integer oldStock = product.getCurrentStock();
                Integer newStock = oldStock - item.getQuantity();
                if (newStock < 0) {
                    throw new IllegalStateException("Insufficient stock for product: " + product.getName());
                }
                product.setCurrentStock(newStock);
                productRepository.save(product);
                stockMovementService.createStockMovement(product.getId(), item.getQuantity(), 
                    com.sabibiz.enums.MovementType.SALE, oldStock, newStock, userService.getCurrentUser().getId());
            }
        }

        sale.setStatus(SaleStatus.COMPLETED);
        sale.setCompletedAt(LocalDateTime.now());
        sale.updatePaymentStatus();
        sale = saleRepository.save(sale);
        return saleMapper.toResponse(sale);
    }

    @Override
    public SaleResponse cancelSale(Long businessId, Long id) {
        Sale sale = saleRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + id));

        if (sale.getStatus() == SaleStatus.COMPLETED) {
            throw new IllegalStateException("Completed sales cannot be cancelled, use refund instead");
        }

        sale.setStatus(SaleStatus.CANCELLED);
        sale = saleRepository.save(sale);
        return saleMapper.toResponse(sale);
    }

    @Override
    public SaleResponse refundSale(Long businessId, Long id, String reason) {
        Sale sale = saleRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + id));

        if (sale.getStatus() != SaleStatus.COMPLETED) {
            throw new IllegalStateException("Only completed sales can be refunded");
        }

        for (SaleItem item : sale.getItems()) {
            Product product = item.getProduct();
            if (product.getTrackStock()) {
                Integer oldStock = product.getCurrentStock();
                Integer newStock = oldStock + item.getQuantity();
                product.setCurrentStock(newStock);
                productRepository.save(product);
                stockMovementService.createStockMovement(product.getId(), item.getQuantity(), 
                    com.sabibiz.enums.MovementType.SALE_RETURN, oldStock, newStock, userService.getCurrentUser().getId());
            }
        }

        sale.setStatus(SaleStatus.REFUNDED);
        sale.setNotes((sale.getNotes() != null ? sale.getNotes() + "\n" : "") + "Refund reason: " + reason);
        sale = saleRepository.save(sale);
        return saleMapper.toResponse(sale);
    }
}