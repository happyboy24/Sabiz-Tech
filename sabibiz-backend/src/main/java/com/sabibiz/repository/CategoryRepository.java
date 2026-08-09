package com.sabibiz.repository;

import com.sabibiz.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {
    List<Category> findByBusinessId(Long businessId);
    Page<Category> findByBusinessId(Long businessId, Pageable pageable);
    List<Category> findByBusinessIdAndIsActiveTrue(Long businessId);
    Optional<Category> findByBusinessIdAndName(Long businessId, String name);
    List<Category> findByBusinessIdAndParentIsNull(Long businessId);
    Page<Category> findByBusinessIdAndParentIsNull(Long businessId, Pageable pageable);
    List<Category> findByParentId(Long parentId);
    boolean existsByBusinessIdAndName(Long businessId, String name);
}