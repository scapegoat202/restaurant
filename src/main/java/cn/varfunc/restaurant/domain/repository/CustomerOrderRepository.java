package cn.varfunc.restaurant.domain.repository;

import cn.varfunc.restaurant.domain.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Order Repository
 */
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
}
