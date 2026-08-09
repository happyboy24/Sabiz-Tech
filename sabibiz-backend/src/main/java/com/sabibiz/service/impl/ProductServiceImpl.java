package com.sabibiz.service.impl;

import com.sabibiz.dto.request.ProductRequest;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.dto.response.ProductResponse;
import com.sabibiz.entity.Product;
import com.sabibiz.enums.MovementType;
import com.sabibiz.exception.ResourceNotFoundException;
import com.sabibiz.mapper.ProductMapper;
import com.sabibiz.repository.CategoryRepository;
import com.sabibiz.repository.ProductRepository;
import com.sabibiz.repository.SupplierRepository;
import com.sabibiz.service.ProductService;
import com.sabibiz.service.StockMovementService;
import com.sabibiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;
    private final StockMovementService stockMovementService;
    private final UserService userService;

    @Override
    public ProductResponse create(Long businessId, ProductRequest request) {
        if (productRepository.existsByBusinessIdAndSku(businessId, request.getSku())) {
            throw new IllegalArgumentException("SKU already exists");
        }
        if (request.getBarcode() != null && productRepository.existsByBusinessIdAndBarcode(businessId, request.getBarcode())) {
            throw new IllegalArgumentException("Barcode already exists");
        }

        Product product = productMapper.toEntity(request);
        product.setBusiness(userService.getBusinessEntity(businessId));
        
        if (request.getCategoryId() != null) {
            product.setCategory(categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found")));
        }
        if (request.getSupplierId() != null) {
            product.setSupplier(supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found")));
        }

        product = productRepository.save(product);
        
        if (product.getTrackStock() && product.getCurrentStock() > 0) {
            stockMovementService.createOpeningStock(product, product.getCurrentStock());
        }
        
        return productMapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getById(Long businessId, Long id) {
        Product product = productRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse update(Long businessId, Long id, ProductRequest request) {
        Product product = productRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (!product.getSku().equals(request.getSku()) 
            && productRepository.existsByBusinessIdAndSku(businessId, request.getSku())) {
            throw new IllegalArgumentException("SKU already exists");
        }
        if (request.getBarcode() != null && !request.getBarcode().equals(product.getBarcode())
            && productRepository.existsByBusinessIdAndBarcode(businessId, request.getBarcode())) {
            throw new IllegalArgumentException("Barcode already exists");
        }

        productMapper.updateEntity(product, request);
        
        if (request.getCategoryId() != null) {
            product.setCategory(categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found")));
        } else {
            product.setCategory(null);
        }
        if (request.getSupplierId() != null) {
            product.setSupplier(supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found")));
        } else {
            product.setSupplier(null);
        }

        product = productRepository.save(product);
        return productMapper.toResponse(product);
    }

    @Override
    public void delete(Long businessId, Long id) {
        Product product = productRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getAll(Long businessId, Pageable pageable) {
        Page<Product> page = productRepository.findByBusinessId(businessId, pageable);
        return PageResponse.of(page.map(productMapper::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getActive(Long businessId, Pageable pageable) {
        Page<Product> page = productRepository.findByBusinessIdAndIsActiveTrue(businessId, pageable);
        return PageResponse.of(page.map(productMapper::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getLowStock(Long businessId, Pageable pageable) {
        Page<Product> page = productRepository.findByBusinessIdAndTrackStockTrueAndCurrentStockLessThanEqual(
            businessId, 10, pageable);
        return PageResponse.of(page.map(productMapper::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getByCategory(Long businessId, Long categoryId, Pageable pageable) {
        Page<Product> page = productRepository.findByBusinessIdAndCategoryId(businessId, categoryId, pageable);
        return PageResponse.of(page.map(productMapper::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> search(Long businessId, String query, Long categoryId, Boolean isActive, Pageable pageable) {
        Page<Product> page = productRepository.searchProducts(businessId, query, categoryId, isActive, pageable);
        return PageResponse.of(page.map(productMapper::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getBySku(Long businessId, String sku) {
        Product product = productRepository.findByBusinessIdAndSku(businessId, sku)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with SKU: " + sku));
        return productMapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getByBarcode(Long businessId, String barcode) {
        Product product = productRepository.findByBusinessIdAndBarcode(businessId, barcode)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with barcode: " + barcode));
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse updateStock(Long businessId, Long id, Integer quantity) {
        Product product = productRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        if (!product.getTrackStock()) {
            throw new IllegalStateException("Stock tracking is disabled for this product");
        }
        
        Integer oldStock = product.getCurrentStock();
        product.setCurrentStock(quantity);
        product = productRepository.save(product);
        
        stockMovementService.createStockMovement(product, quantity, 
            MovementType.STOCK_ADJUSTMENT_IN, oldStock, quantity, userService.getCurrentUser().getId());
        
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse adjustStock(Long businessId, Long id, Integer adjustment, String reason) {
        Product product = productRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        if (!product.getTrackStock()) {
            throw new IllegalStateException("Stock tracking is disabled for this product");
        }
        
        Integer oldStock = product.getCurrentStock();
        Integer newStock = oldStock + adjustment;
        
        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        
        product.setCurrentStock(newStock);
        product = productRepository.save(product);
        
        MovementType type = adjustment > 0 ? MovementType.STOCK_ADJUSTMENT_IN : MovementType.STOCK_ADJUSTMENT_OUT;
        stockMovementService.createStockMovement(product, Math.abs(adjustment), type, oldStock, newStock, 
            userService.getCurrentUser().getId());
        
        return productMapper.toResponse(product);
    }
}