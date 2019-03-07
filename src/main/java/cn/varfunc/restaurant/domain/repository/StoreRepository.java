package cn.varfunc.restaurant.domain.repository;

import cn.varfunc.restaurant.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Store Repository
 */
public interface StoreRepository extends JpaRepository<Store, Long> {
}
