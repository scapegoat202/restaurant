package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.OrderForm;
import cn.varfunc.restaurant.domain.model.CustomerOrder;
import cn.varfunc.restaurant.domain.model.OrderStatus;
import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.domain.response.ApiResponse;
import cn.varfunc.restaurant.service.OrderService;
import cn.varfunc.restaurant.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CustomerOrder getOrderById(@PathVariable long id) {
        return orderService.findById(id);
    }

    /**
     * Get all orders of specific store
     */
    @GetMapping
    public ApiResponse getAllOrdersByStoreId(
            @RequestParam(name = "storeId") long storeId) {
        Store store = storeService.findById(storeId);
        List<CustomerOrder> orders = orderService.findAllByStore(store);
        return ApiResponse.builder()
                .data(orders)
                .build();
    }

    /**
     * Get all orders with the status of <code>OrderStatus.SUBMITTED</code>
     */
    @GetMapping("/pending")
    public ApiResponse getAllPendingOrders() {
        List<CustomerOrder> orders = orderService.findAllByOrderStatus(OrderStatus.SUBMITTED);
        return ApiResponse.builder()
                .data(orders)
                .build();
    }

    /**
     * Create an order instance
     */
    @PostMapping
    public ApiResponse createOrder(@RequestBody OrderForm form) {
        CustomerOrder order = orderService.create(form);
        return ApiResponse.builder()
                .data(order)
                .build();
    }

    /**
     * Change order status of specific order of given id
     *
     * @param id   id of the order
     * @param form only <code>orderStatus</code> field is required
     */
    @PatchMapping("/{id}")
    public ApiResponse changeStatus(@PathVariable long id, @RequestBody OrderForm form) {
        CustomerOrder order = orderService.modifyStatus(id, form);
        return ApiResponse.builder()
                .data(order)
                .build();
    }
}
