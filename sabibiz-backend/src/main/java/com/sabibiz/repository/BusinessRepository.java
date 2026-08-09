package com.sabibiz.repository;

import com.sabibiz.entity.Business;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessRepository extends BaseRepository<Business, Long> {
    Optional<Business> findByName(String name);
    Optional<Business> findByBusinessNumber(String businessNumber);
    Optional<Business> findByTaxNumber(String taxNumber);
    Optional<Business> findByRegistrationNumber(String registrationNumber);
    Optional<Business> findByEmail(String email);
    List<Business> findByIsActiveTrue();
    Page<Business> findByIsActiveTrue(Pageable pageable);
    List<Business> findBySubscriptionStatus(String status);
    boolean existsByBusinessNumber(String businessNumber);
    boolean existsByTaxNumber(String taxNumber);
    boolean existsByRegistrationNumber(String registrationNumber);
    boolean existsByEmail(String email);
    
    @Query("SELECT b FROM Business b WHERE b.id = :id AND b.isActive = true")
    Optional<Business> findActiveById(@Param("id") Long id);
}