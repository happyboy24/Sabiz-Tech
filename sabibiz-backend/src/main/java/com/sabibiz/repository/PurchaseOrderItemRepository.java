package com.sabibiz.repository;

import com.sabibiz.entity.PurchaseOrderItem;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PurchaseOrderItemRepository extends BaseRepository<PurchaseOrderItem, Long> {
    List<PurchaseOrderItem> findByPurchaseOrderId(Long purchaseOrderId);
    List<PurchaseOrderItem> findByProductId(Long productId);
}