package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.OrderForm;
import cn.varfunc.restaurant.domain.model.*;
import cn.varfunc.restaurant.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final StoreService storeService;
    private final CustomerService customerService;
    private final CommodityService commodityService;
    private final OrderItemService orderItemService;

    @Autowired
    public OrderController(OrderService orderService,
                           StoreService storeService,
                           CustomerService customerService,
                           CommodityService commodityService,
                           OrderItemService orderItemService) {
        this.orderService = orderService;
        this.storeService = storeService;
        this.customerService = customerService;
        this.commodityService = commodityService;
        this.orderItemService = orderItemService;
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
    public List<CustomerOrder> getAllOrdersByStore(
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
        final Store store = storeService.findById(form.getStoreId());
        Customer customer = customerService.findById(form.getCustomerId());
        customer = customerService.updateLastAccessTime(customer);

        List<OrderItem> orderItems = new ArrayList<>();
        form.getOrderItems().forEach(it -> {
            Commodity commodity = commodityService.findById(it.getCommodityId());
            OrderItem orderItem = orderItemService.create(commodity, it.getAmount());
            orderItems.add(orderItem);
        });
        return orderService.create(store, customer, orderItems, form.getAmount(), form.getTableNumber());
    }

    /**
     * Change order status of specific order of given id
     *
     * @param id   id of the order
     * @param form only <code>orderStatus</code> field is required
     */
    @PatchMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerOrder changeStatus(@PathVariable long id, @RequestBody OrderForm form) {
        final OrderStatus status = OrderStatus.parse(form.getOrderStatus());
        return orderService.modifyStatus(id, status);
    }
}
