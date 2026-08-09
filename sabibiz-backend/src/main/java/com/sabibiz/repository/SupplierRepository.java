package com.sabibiz.repository;

import com.sabibiz.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends BaseRepository<Supplier, Long> {
    List<Supplier> findByBusinessId(Long businessId);
    Page<Supplier> findByBusinessId(Long businessId, Pageable pageable);
    List<Supplier> findByBusinessIdAndIsActiveTrue(Long businessId);
    Page<Supplier> findByBusinessIdAndIsActiveTrue(Long businessId, Pageable pageable);
    Optional<Supplier> findByBusinessIdAndName(Long businessId, String name);
    Optional<Supplier> findByBusinessIdAndEmail(Long businessId, String email);
    Optional<Supplier> findByBusinessIdAndPhoneNumber(Long businessId, String phoneNumber);
    
    @Query("SELECT s FROM Supplier s WHERE s.business.id = :businessId AND (:query IS NULL OR s.name ILIKE %:query% OR s.email ILIKE %:query% OR s.phoneNumber ILIKE %:query% OR s.contactPerson ILIKE %:query%)")
    List<Supplier> searchSuppliers(@Param("businessId") Long businessId, @Param("query") String query);
    
    @Query("SELECT s FROM Supplier s WHERE s.business.id = :businessId AND (:query IS NULL OR s.name ILIKE %:query% OR s.email ILIKE %:query% OR s.phoneNumber ILIKE %:query% OR s.contactPerson ILIKE %:query%)")
    Page<Supplier> searchSuppliers(@Param("businessId") Long businessId, @Param("query") String query, Pageable pageable);
    
    boolean existsByBusinessIdAndName(Long businessId, String name);
    boolean existsByBusinessIdAndEmail(Long businessId, String email);
    boolean existsByBusinessIdAndPhoneNumber(Long businessId, String phoneNumber);
    long countByBusinessId(Long businessId);
}