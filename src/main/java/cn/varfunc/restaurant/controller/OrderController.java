package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.OrderForm;
import cn.varfunc.restaurant.domain.model.CustomerOrder;
import cn.varfunc.restaurant.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Get an order instance by given id.
     */
    @GetMapping("/order/{id}")
    public CustomerOrder getOrderById(@PathVariable long id) {
        return orderService.getById(id);
    }

    /**
     * Create an order instance
     */
    @PostMapping("/order")
    public CustomerOrder createOrder(@RequestBody OrderForm form) {
        return orderService.create(form);
    }

    /**
     * Change order status of specific order of given id
     *
     * @param id   id of the order
     * @param form only <code>orderStatus</code> field is required
     */
    @PatchMapping("/order/{id}")
    public CustomerOrder changeStatus(@PathVariable long id, @RequestBody OrderForm form) {
        return orderService.modifyStatus(id, form);
    }
}
