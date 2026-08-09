package com.sabibiz.repository;

import com.sabibiz.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends BaseRepository<Customer, Long> {
    List<Customer> findByBusinessId(Long businessId);
    Page<Customer> findByBusinessId(Long businessId, Pageable pageable);
    List<Customer> findByBusinessIdAndIsActiveTrue(Long businessId);
    Optional<Customer> findByBusinessIdAndCustomerNumber(Long businessId, String customerNumber);
    Optional<Customer> findByBusinessIdAndEmail(Long businessId, String email);
    Optional<Customer> findByBusinessIdAndPhoneNumber(Long businessId, String phoneNumber);
    List<Customer> findByBusinessIdAndIsBlacklistedFalse(Long businessId);
    List<Customer> findByBusinessIdAndCreditLimitGreaterThan(Long businessId, java.math.BigDecimal limit);
    
    @Query("SELECT c FROM Customer c WHERE c.business.id = :businessId AND (:query IS NULL OR c.name ILIKE %:query% OR c.email ILIKE %:query% OR c.phoneNumber ILIKE %:query% OR c.customerNumber ILIKE %:query%)")
    List<Customer> searchCustomers(@Param("businessId") Long businessId, @Param("query") String query);
    
    @Query("SELECT c FROM Customer c WHERE c.business.id = :businessId AND (:query IS NULL OR c.name ILIKE %:query% OR c.email ILIKE %:query% OR c.phoneNumber ILIKE %:query% OR c.customerNumber ILIKE %:query%)")
    Page<Customer> searchCustomers(@Param("businessId") Long businessId, @Param("query") String query, Pageable pageable);
    
    boolean existsByBusinessIdAndCustomerNumber(Long businessId, String customerNumber);
    boolean existsByBusinessIdAndEmail(Long businessId, String email);
    boolean existsByBusinessIdAndPhoneNumber(Long businessId, String phoneNumber);
    long countByBusinessId(Long businessId);
    Optional<Customer> findByBusinessIdAndId(Long businessId, Long id);
}