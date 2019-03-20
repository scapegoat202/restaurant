package cn.varfunc.restaurant.service;

import cn.varfunc.restaurant.domain.model.OrderItem;
import cn.varfunc.restaurant.domain.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * Get an order item instance by given id
     */
    public OrderItem findById(long id) {
        return orderItemRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("No such order item!"));
    }
}
