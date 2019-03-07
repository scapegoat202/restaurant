package cn.varfunc.restaurant.domain.repository;

import cn.varfunc.restaurant.domain.model.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Commodity Repository
 */
public interface CommodityRepository extends JpaRepository<Commodity, Long> {
}
