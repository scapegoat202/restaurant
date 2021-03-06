package cn.varfunc.restaurant.domain.repository;

import cn.varfunc.restaurant.domain.model.CustomerOrder;
import cn.varfunc.restaurant.domain.model.OrderStatus;
import cn.varfunc.restaurant.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Order Repository
 */
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findAllByStore(Store store);

    List<CustomerOrder> findAllByOrderStatus(OrderStatus status);
}
