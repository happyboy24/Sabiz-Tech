package com.sabibiz.repository;

import com.sabibiz.entity.PurchaseOrder;
import com.sabibiz.enums.PurchaseOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends BaseRepository<PurchaseOrder, Long> {
    Optional<PurchaseOrder> findByBusinessIdAndOrderNumber(Long businessId, String orderNumber);
    List<PurchaseOrder> findByBusinessId(Long businessId);
    Page<PurchaseOrder> findByBusinessId(Long businessId, Pageable pageable);
    List<PurchaseOrder> findByBusinessIdAndStatus(Long businessId, PurchaseOrderStatus status);
    List<PurchaseOrder> findByBusinessIdAndSupplierId(Long businessId, Long supplierId);
    List<PurchaseOrder> findByBusinessIdAndOrderDateBetween(Long businessId, LocalDate start, LocalDate end);
    Optional<PurchaseOrder> findByBusinessIdAndId(Long businessId, Long id);
    boolean existsByBusinessIdAndOrderNumber(Long businessId, String orderNumber);
}