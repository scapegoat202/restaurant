package cn.varfunc.restaurant.domain.repository;

import cn.varfunc.restaurant.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Category Repository
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
