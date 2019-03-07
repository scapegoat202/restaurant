package cn.varfunc.restaurant.service;

import cn.varfunc.restaurant.domain.form.OrderForm;
import cn.varfunc.restaurant.domain.model.*;
import cn.varfunc.restaurant.domain.repository.CustomerOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class OrderService {
    private final CustomerOrderRepository order;
    private final StoreService storeService;
    private final CustomerService customerService;
    private final OrderItemService orderItemService;

    @Autowired
    public OrderService(CustomerOrderRepository order, StoreService storeService,
                        CustomerService customerService, OrderItemService orderItemService) {
        this.order = order;
        this.storeService = storeService;
        this.customerService = customerService;
        this.orderItemService = orderItemService;
    }

    /**
     * Get an order instance by given id
     */
    public CustomerOrder getById(long id) {
        log.info("Method: getById(), id: {}", id);
        Optional<CustomerOrder> orderOptional = order.findById(id);
        return orderOptional.orElseThrow(() -> new NoSuchElementException("No such order!"));
    }

    /**
     * Create an order instance
     */
    @Transactional
    public CustomerOrder create(@RequestBody OrderForm form) {
        log.info("Method: create(), form: {}", form);

        // TODO: 2019/3/6 VALIDATION is required before creating order

        Store store = storeService.findById(form.getStoreId());
        Customer customer = customerService.findById(form.getCustomerId());
        List<OrderItem> orderItems = new LinkedList<>();
        form.getOrderItems().stream()
                .map(orderItemService::create)
                .forEach(orderItems::add);

        CustomerOrder newCustomerOrder = new CustomerOrder();
        newCustomerOrder.setAmount(form.getAmount())
                .setCustomer(customer)
                .setStore(store)
                .setOrderStatus(OrderStatus.SUBMITTED)
                .setOrderItems(orderItems)
                .setTableNumber(form.getTableNumber());
        return order.save(newCustomerOrder);
    }

    /**
     * Change order status of specific order with given id
     *
     * @param id   id of the order
     * @param form only <code>orderStatus</code> field is required
     */
    public CustomerOrder modifyStatus(long id, OrderForm form) {
        log.info("Method: modifyStatus(), id: {}, form: {}", id, form);
        order.findById(id).ifPresent(it -> {
            it.setOrderStatus(OrderStatus.parse(form.getOrderStatus()));
            order.save(it);
        });
        return order.findById(id).orElseThrow(
                () -> new NoSuchElementException("No such order with id " + id));
    }
}
