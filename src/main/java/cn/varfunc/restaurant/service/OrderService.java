package cn.varfunc.restaurant.service;

import cn.varfunc.restaurant.domain.form.OrderForm;
import cn.varfunc.restaurant.domain.model.*;
import cn.varfunc.restaurant.domain.repository.CustomerOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class OrderService {
    private final CustomerOrderRepository customerOrderRepository;
    private final StoreService storeService;
    private final CustomerService customerService;
    private final OrderItemService orderItemService;

    @Autowired
    public OrderService(CustomerOrderRepository customerOrderRepository, StoreService storeService,
                        CustomerService customerService, OrderItemService orderItemService) {
        this.customerOrderRepository = customerOrderRepository;
        this.storeService = storeService;
        this.customerService = customerService;
        this.orderItemService = orderItemService;
    }

    /**
     * Get an customerOrderRepository instance by given id
     */
    public CustomerOrder getById(long id) {
        log.info("Method: getById(), id: {}", id);
        Optional<CustomerOrder> orderOptional = customerOrderRepository.findById(id);
        return orderOptional.orElseThrow(
                () -> new NoSuchElementException("No such customer order!"));
    }

    public List<CustomerOrder> getAllByStoreId(long storeId) {
        Store store = storeService.findById(storeId);
        return customerOrderRepository.findByStore(store);
    }

    /**
     * Create an customerOrderRepository instance
     */
    @Transactional
    public CustomerOrder create(@RequestBody OrderForm form) {
        log.info("Method: create(), form: {}", form);

        // TODO: 2019/3/6 VALIDATION is required before creating customer order

        Store store = storeService.findById(form.getStoreId());
        Customer customer = customerService
                .updateLastAccessTime(customerService
                        .findById(form.getCustomerId())
                );
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
                .setTableNumber(form.getTableNumber())
                .setTimeCreated(LocalDateTime.now());
        return customerOrderRepository.save(newCustomerOrder);
    }

    /**
     * Change customerOrderRepository status of specific customerOrderRepository with given id
     *
     * @param id   id of the customerOrderRepository
     * @param form only <code>orderStatus</code> field is required
     */
    public CustomerOrder modifyStatus(long id, OrderForm form) {
        log.info("Method: modifyStatus(), id: {}, form: {}", id, form);
        customerOrderRepository.findById(id).ifPresent(it -> {
            if (Objects.nonNull(form.getOrderStatus())) {
                it.setOrderStatus(OrderStatus.parse(form.getOrderStatus()))
                        .setTimeFinished(LocalDateTime.now());
                customerOrderRepository.save(it);
            }
        });
        return customerOrderRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("No such customerOrderRepository with id " + id));
    }
}
