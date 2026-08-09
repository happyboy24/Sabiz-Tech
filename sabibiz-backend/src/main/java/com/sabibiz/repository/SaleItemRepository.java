package com.sabibiz.repository;

import com.sabibiz.entity.SaleItem;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SaleItemRepository extends BaseRepository<SaleItem, Long> {
    List<SaleItem> findBySaleId(Long saleId);
    List<SaleItem> findByProductId(Long productId);
}