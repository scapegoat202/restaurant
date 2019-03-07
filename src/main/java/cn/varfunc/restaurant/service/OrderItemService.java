package cn.varfunc.restaurant.service;

import cn.varfunc.restaurant.domain.form.OrderItemForm;
import cn.varfunc.restaurant.domain.model.Commodity;
import cn.varfunc.restaurant.domain.model.OrderItem;
import cn.varfunc.restaurant.domain.repository.OrderItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
public class OrderItemService {
    private final OrderItemRepository orderItem;
    private final CommodityService commodityService;

    @Autowired
    public OrderItemService(OrderItemRepository orderItem, CommodityService commodityService) {
        this.orderItem = orderItem;
        this.commodityService = commodityService;
    }

    /**
     * Get an order item instance by given id
     */
    public OrderItem getById(long id) {
        log.info("Method: getById(), id: {}", id);
        return orderItem.findById(id).orElseThrow(() -> new NoSuchElementException("No such order item!"));
    }

    /**
     * Create a order item instance
     *
     * @param form <code>commodityId</code> and <code>amount</code> must be present
     */
    public OrderItem create(OrderItemForm form) {
        log.info("Method: create(), form: {}", form);
        Commodity commodity = commodityService.getById(form.getCommodityId());
        OrderItem newOrderItem = new OrderItem();
        newOrderItem.setCommodity(commodity)
                .setAmount(form.getAmount());
        return orderItem.save(newOrderItem);
    }
}
