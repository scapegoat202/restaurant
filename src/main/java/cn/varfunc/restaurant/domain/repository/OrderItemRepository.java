package cn.varfunc.restaurant.domain.repository;

import cn.varfunc.restaurant.domain.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
