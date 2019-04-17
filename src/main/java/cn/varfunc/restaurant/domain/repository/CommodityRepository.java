package cn.varfunc.restaurant.domain.repository;

import cn.varfunc.restaurant.domain.model.Category;
import cn.varfunc.restaurant.domain.model.Commodity;
import cn.varfunc.restaurant.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Commodity Repository
 */
public interface CommodityRepository extends JpaRepository<Commodity, Long> {
    List<Commodity> findAllByStore(Store store);

    List<Commodity> findAllByCategories(Category category);
}
