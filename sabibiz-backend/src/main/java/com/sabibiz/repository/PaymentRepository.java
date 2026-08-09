package com.sabibiz.repository;

import com.sabibiz.entity.Payment;
import com.sabibiz.enums.PaymentMethod;
import com.sabibiz.enums.PaymentStatus;
import com.sabibiz.enums.PaymentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends BaseRepository<Payment, Long> {
    List<Payment> findByBusinessId(Long businessId);
    Page<Payment> findByBusinessId(Long businessId, Pageable pageable);
    List<Payment> findByBusinessIdAndPaymentType(Long businessId, PaymentType paymentType);
    List<Payment> findByBusinessIdAndStatus(Long businessId, PaymentStatus status);
    List<Payment> findByBusinessIdAndPaymentMethod(Long businessId, PaymentMethod paymentMethod);
    List<Payment> findByBusinessIdAndPaymentDateBetween(Long businessId, LocalDate start, LocalDate end);
    List<Payment> findByBusinessIdAndCustomerId(Long businessId, Long customerId);
    List<Payment> findByBusinessIdAndSupplierId(Long businessId, Long supplierId);
    List<Payment> findByBusinessIdAndSaleId(Long businessId, Long saleId);
    List<Payment> findByBusinessIdAndCreatedBy(Long businessId, com.sabibiz.entity.User createdBy);
    Optional<Payment> findByBusinessIdAndId(Long businessId, Long id);
    Optional<Payment> findByBusinessIdAndPaymentNumber(Long businessId, String paymentNumber);
    boolean existsByBusinessIdAndPaymentNumber(Long businessId, String paymentNumber);
    
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.business.id = :businessId AND p.status = :status")
    BigDecimal getTotalAmountByStatus(@Param("businessId") Long businessId, @Param("status") PaymentStatus status);
    
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.business.id = :businessId AND p.paymentDate BETWEEN :start AND :end")
    BigDecimal getTotalAmountByDateRange(@Param("businessId") Long businessId, @Param("start") LocalDate start, @Param("end") LocalDate end);
}