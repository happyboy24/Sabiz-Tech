package com.sabibiz.repository;

import com.sabibiz.entity.StockMovement;
import com.sabibiz.enums.MovementType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockMovementRepository extends BaseRepository<StockMovement, Long> {
    List<StockMovement> findByBusinessId(Long businessId);
    Page<StockMovement> findByBusinessId(Long businessId, Pageable pageable);
    List<StockMovement> findByBusinessIdAndProductId(Long businessId, Long productId);
    Page<StockMovement> findByBusinessIdAndProductId(Long businessId, Long productId, Pageable pageable);
    List<StockMovement> findByBusinessIdAndMovementType(Long businessId, MovementType movementType);
    Page<StockMovement> findByBusinessIdAndMovementType(Long businessId, MovementType movementType, Pageable pageable);
    List<StockMovement> findByBusinessIdAndMovementDateBetween(Long businessId, LocalDateTime start, LocalDateTime end);
    Page<StockMovement> findByBusinessIdAndMovementDateBetween(Long businessId, LocalDateTime start, LocalDateTime end, Pageable pageable);
    List<StockMovement> findByBusinessIdAndReferenceTypeAndReferenceId(Long businessId, String referenceType, Long referenceId);
    Optional<StockMovement> findByBusinessIdAndMovementNumber(Long businessId, String movementNumber);
    Optional<StockMovement> findByBusinessIdAndId(Long businessId, Long id);
    
    @Query("SELECT SUM(sm.quantity) FROM StockMovement sm WHERE sm.business.id = :businessId AND sm.product.id = :productId AND sm.movementType IN :inTypes")
    Integer getTotalInQuantity(@Param("businessId") Long businessId, @Param("productId") Long productId, @Param("inTypes") List<MovementType> inTypes);
    
    @Query("SELECT SUM(sm.quantity) FROM StockMovement sm WHERE sm.business.id = :businessId AND sm.product.id = :productId AND sm.movementType IN :outTypes")
    Integer getTotalOutQuantity(@Param("businessId") Long businessId, @Param("productId") Long productId, @Param("outTypes") List<MovementType> outTypes);
}