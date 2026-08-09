package com.sabibiz.repository;

import com.sabibiz.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    List<User> findByBusinessId(Long businessId);
    Page<User> findByBusinessId(Long businessId, Pageable pageable);
    List<User> findByBusinessIdAndIsActiveTrue(Long businessId);
    Page<User> findByBusinessIdAndIsActiveTrue(Long businessId, Pageable pageable);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByBusinessIdAndUsername(Long businessId, String username);
}