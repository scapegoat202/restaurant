package cn.varfunc.restaurant.domain.repository;

import cn.varfunc.restaurant.domain.model.Category;
import cn.varfunc.restaurant.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Category Repository
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByStore(Store store);
}
