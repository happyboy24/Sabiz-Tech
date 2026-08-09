package com.sabibiz.repository;

import com.sabibiz.entity.Debt;
import com.sabibiz.enums.DebtStatus;
import com.sabibiz.enums.DebtType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DebtRepository extends BaseRepository<Debt, Long> {
    List<Debt> findByBusinessId(Long businessId);
    Page<Debt> findByBusinessId(Long businessId, Pageable pageable);
    List<Debt> findByBusinessIdAndDebtType(Long businessId, DebtType debtType);
    List<Debt> findByBusinessIdAndStatus(Long businessId, DebtStatus status);
    List<Debt> findByBusinessIdAndCustomerId(Long businessId, Long customerId);
    List<Debt> findByBusinessIdAndSupplierId(Long businessId, Long supplierId);
    List<Debt> findByBusinessIdAndDueDateBetween(Long businessId, LocalDate start, LocalDate end);
    List<Debt> findByBusinessIdAndIsOverdue(Long businessId, Boolean isOverdue);
    Optional<Debt> findByBusinessIdAndId(Long businessId, Long id);
    Optional<Debt> findByBusinessIdAndDebtNumber(Long businessId, String debtNumber);
    boolean existsByBusinessIdAndDebtNumber(Long businessId, String debtNumber);
    
    @Query("SELECT SUM(d.balanceAmount) FROM Debt d WHERE d.business.id = :businessId AND d.status = :status")
    BigDecimal getTotalBalanceByStatus(@Param("businessId") Long businessId, @Param("status") DebtStatus status);
    
    @Query("SELECT COUNT(d) FROM Debt d WHERE d.business.id = :businessId AND d.isOverdue = true")
    long countOverdue(@Param("businessId") Long businessId);
    
    @Query("SELECT SUM(d.balanceAmount) FROM Debt d WHERE d.business.id = :businessId AND d.isOverdue = true")
    BigDecimal getTotalOverdueAmount(@Param("businessId") Long businessId);
}