package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.OrderForm;
import cn.varfunc.restaurant.domain.model.CustomerOrder;
import cn.varfunc.restaurant.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Get an order instance by given id.
     */
    @GetMapping("/{id}")
    public CustomerOrder getOrderById(@PathVariable long id) {
        return orderService.getById(id);
    }

    @GetMapping("/{storeId}")
    public List<CustomerOrder> getAllOrdersByStoreId(@PathVariable long storeId) {
        return orderService.getAllByStoreId(storeId);
    }

    /**
     * Create an order instance
     */
    @PostMapping
    public CustomerOrder createOrder(@RequestBody OrderForm form) {
        return orderService.create(form);
    }

    /**
     * Change order status of specific order of given id
     *
     * @param id   id of the order
     * @param form only <code>orderStatus</code> field is required
     */
    @PatchMapping("/{id}")
    public CustomerOrder changeStatus(@PathVariable long id, @RequestBody OrderForm form) {
        return orderService.modifyStatus(id, form);
    }
}
