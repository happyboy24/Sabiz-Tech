package com.sabibiz.repository;

import com.sabibiz.entity.PaymentMethod;
import com.sabibiz.enums.PaymentMethodType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends BaseRepository<PaymentMethod, Long> {
    List<PaymentMethod> findByBusinessId(Long businessId);
    Page<PaymentMethod> findByBusinessId(Long businessId, Pageable pageable);
    List<PaymentMethod> findByBusinessIdAndIsActive(Long businessId, Boolean isActive);
    List<PaymentMethod> findByBusinessIdAndMethodType(Long businessId, PaymentMethodType methodType);
    Optional<PaymentMethod> findByBusinessIdAndId(Long businessId, Long id);
    Optional<PaymentMethod> findByBusinessIdAndCode(Long businessId, String code);
    boolean existsByBusinessIdAndCode(Long businessId, String code);
    Optional<PaymentMethod> findByBusinessIdAndIsDefault(Long businessId, Boolean isDefault);
}