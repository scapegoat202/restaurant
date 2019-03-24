package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.OrderForm;
import cn.varfunc.restaurant.domain.model.CustomerOrder;
import cn.varfunc.restaurant.domain.model.OrderStatus;
import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.service.OrderService;
import cn.varfunc.restaurant.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final StoreService storeService;

    @Autowired
    public OrderController(OrderService orderService, StoreService storeService) {
        this.orderService = orderService;
        this.storeService = storeService;
    }

    /**
     * Get an order instance by given id.
     */
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CustomerOrder getOrderById(@PathVariable long id) {
        return orderService.findById(id);
    }

    /**
     * Get all orders of specific store
     */
    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerOrder> getAllOrdersByStoreId(
            @RequestParam(name = "storeId") long storeId) {
        Store store = storeService.findById(storeId);
        return orderService.findAllByStore(store);
    }

    /**
     * Get all orders with the status of <code>OrderStatus.SUBMITTED</code>
     */
    @GetMapping("/pending")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerOrder> getAllPendingOrders() {
        return orderService.findAllByOrderStatus(OrderStatus.SUBMITTED);
    }

    /**
     * Create an order instance
     */
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerOrder createOrder(@RequestBody OrderForm form) {
        return orderService.create(form);
    }

    /**
     * Change order status of specific order of given id
     *  @param id   id of the order
     * @param form only <code>orderStatus</code> field is required
     */
    @PatchMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerOrder changeStatus(@PathVariable long id, @RequestBody OrderForm form) {
        return orderService.modifyStatus(id, form);
    }
}
