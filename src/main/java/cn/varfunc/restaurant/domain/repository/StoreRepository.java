package cn.varfunc.restaurant.domain.repository;

import cn.varfunc.restaurant.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Store Repository
 */
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByUsername(String username);
}
