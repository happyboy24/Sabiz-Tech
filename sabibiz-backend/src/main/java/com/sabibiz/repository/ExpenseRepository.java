package com.sabibiz.repository;

import com.sabibiz.entity.Expense;
import com.sabibiz.enums.ExpenseCategory;
import com.sabibiz.enums.PaymentStatus;
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
public interface ExpenseRepository extends BaseRepository<Expense, Long> {
    List<Expense> findByBusinessId(Long businessId);
    Page<Expense> findByBusinessId(Long businessId, Pageable pageable);
    List<Expense> findByBusinessIdAndCategory(Long businessId, ExpenseCategory category);
    Page<Expense> findByBusinessIdAndCategory(Long businessId, ExpenseCategory category, Pageable pageable);
    List<Expense> findByBusinessIdAndPaymentStatus(Long businessId, PaymentStatus paymentStatus);
    Page<Expense> findByBusinessIdAndPaymentStatus(Long businessId, PaymentStatus paymentStatus, Pageable pageable);
    List<Expense> findByBusinessIdAndExpenseDateBetween(Long businessId, LocalDate start, LocalDate end);
    Page<Expense> findByBusinessIdAndExpenseDateBetween(Long businessId, LocalDate start, LocalDate end, Pageable pageable);
    List<Expense> findByBusinessIdAndSupplierId(Long businessId, Long supplierId);
    Optional<Expense> findByBusinessIdAndId(Long businessId, Long id);
    Optional<Expense> findByBusinessIdAndExpenseNumber(Long businessId, String expenseNumber);
    boolean existsByBusinessIdAndExpenseNumber(Long businessId, String expenseNumber);
    
    @Query("SELECT SUM(e.totalAmount) FROM Expense e WHERE e.business.id = :businessId AND e.paymentStatus = :status")
    BigDecimal getTotalExpenseAmount(@Param("businessId") Long businessId, @Param("status") PaymentStatus status);
}