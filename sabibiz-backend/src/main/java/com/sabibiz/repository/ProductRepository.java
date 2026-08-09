package com.sabibiz.repository;

import com.sabibiz.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
    List<Product> findByBusinessId(Long businessId);
    Page<Product> findByBusinessId(Long businessId, Pageable pageable);
    List<Product> findByBusinessIdAndIsActiveTrue(Long businessId);
    Page<Product> findByBusinessIdAndIsActiveTrue(Long businessId, Pageable pageable);
    List<Product> findByBusinessIdAndCategoryId(Long businessId, Long categoryId);
    Page<Product> findByBusinessIdAndCategoryId(Long businessId, Long categoryId, Pageable pageable);
    Optional<Product> findByBusinessIdAndSku(Long businessId, String sku);
    Optional<Product> findByBusinessIdAndBarcode(Long businessId, String barcode);
    List<Product> findByBusinessIdAndSupplierId(Long businessId, Long supplierId);
    
    @Query("SELECT p FROM Product p WHERE p.business.id = :businessId AND p.trackStock = true AND p.currentStock <= p.minStockLevel")
    List<Product> findLowStockProducts(@Param("businessId") Long businessId);
    
    @Query("SELECT p FROM Product p WHERE p.business.id = :businessId AND p.trackStock = true AND p.currentStock <= :threshold")
    List<Product> findByBusinessIdAndTrackStockTrueAndCurrentStockLessThanEqual(@Param("businessId") Long businessId, @Param("threshold") Integer threshold);
    
    Page<Product> findByBusinessIdAndTrackStockTrueAndCurrentStockLessThanEqual(Long businessId, Integer threshold, Pageable pageable);
    List<Product> findByBusinessIdAndIsServiceTrue(Long businessId);
    List<Product> findByBusinessIdAndIsServiceFalse(Long businessId);
    boolean existsByBusinessIdAndSku(Long businessId, String sku);
    boolean existsByBusinessIdAndBarcode(Long businessId, String barcode);
    Optional<Product> findByBusinessIdAndId(Long businessId, Long id);
    
    @Query("SELECT p FROM Product p WHERE p.business.id = :businessId AND (:query IS NULL OR p.name ILIKE %:query% OR p.sku ILIKE %:query% OR p.barcode ILIKE %:query%) AND (:categoryId IS NULL OR p.category.id = :categoryId) AND (:isActive IS NULL OR p.isActive = :isActive)")
    Page<Product> searchProducts(@Param("businessId") Long businessId, @Param("query") String query, @Param("categoryId") Long categoryId, @Param("isActive") Boolean isActive, Pageable pageable);
}