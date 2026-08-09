package com.sabibiz.repository;

import com.sabibiz.entity.Sale;
import com.sabibiz.enums.SaleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends BaseRepository<Sale, Long> {
    Optional<Sale> findByBusinessIdAndSaleNumber(Long businessId, String saleNumber);
    List<Sale> findByBusinessId(Long businessId);
    Page<Sale> findByBusinessId(Long businessId, Pageable pageable);
    List<Sale> findByBusinessIdAndStatus(Long businessId, SaleStatus status);
    List<Sale> findByBusinessIdAndCustomerId(Long businessId, Long customerId);
    List<Sale> findByBusinessIdAndCashierId(Long businessId, Long cashierId);
    List<Sale> findByBusinessIdAndSaleDateBetween(Long businessId, LocalDateTime start, LocalDateTime end);
    Page<Sale> findByBusinessIdAndSaleDateBetween(Long businessId, LocalDateTime start, LocalDateTime end, Pageable pageable);
    Optional<Sale> findByBusinessIdAndId(Long businessId, Long id);
    List<Sale> findByBusinessIdAndPaymentStatus(Long businessId, com.sabibiz.enums.PaymentStatus paymentStatus);
    List<Sale> findByBusinessIdAndPaymentMethod(Long businessId, com.sabibiz.enums.PaymentMethod paymentMethod);
    
    @Query("SELECT SUM(s.totalAmount) FROM Sale s WHERE s.business.id = :businessId AND s.status = :status")
    BigDecimal getTotalSalesAmount(@Param("businessId") Long businessId, @Param("status") SaleStatus status);
    
    @Query("SELECT COUNT(s) FROM Sale s WHERE s.business.id = :businessId AND s.status = :status")
    Long getTotalSalesCount(@Param("businessId") Long businessId, @Param("status") SaleStatus status);
    
    @Query("SELECT s FROM Sale s WHERE s.business.id = :businessId ORDER BY s.saleDate DESC")
    List<Sale> findRecentSalesByBusinessId(@Param("businessId") Long businessId, Pageable pageable);
}