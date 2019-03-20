package cn.varfunc.restaurant.service;

import cn.varfunc.restaurant.domain.form.OrderForm;
import cn.varfunc.restaurant.domain.form.OrderItemForm;
import cn.varfunc.restaurant.domain.model.*;
import cn.varfunc.restaurant.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {
    private final CustomerOrderRepository customerOrderRepository;
    private final StoreRepository storeRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemRepository orderItemRepository;
    private final CommodityRepository commodityRepository;

    @Autowired
    public OrderService(CustomerOrderRepository customerOrderRepository,
                        StoreRepository storeRepository,
                        CustomerRepository customerRepository,
                        OrderItemRepository orderItemRepository,
                        CommodityRepository commodityRepository) {
        this.customerOrderRepository = customerOrderRepository;
        this.storeRepository = storeRepository;
        this.customerRepository = customerRepository;
        this.orderItemRepository = orderItemRepository;
        this.commodityRepository = commodityRepository;
    }

    /**
     * Get an customerOrderRepository instance by given id
     */
    public CustomerOrder findById(long id) {
        Optional<CustomerOrder> orderOptional = customerOrderRepository.findById(id);
        return orderOptional.orElseThrow(
                () -> new NoSuchElementException("No such customer order!"));
    }

    public List<CustomerOrder> findAllByStore(Store store) {
        return customerOrderRepository.findAllByStore(store);
    }

    /**
     * Get all orders of specified order status
     */
    public List<CustomerOrder> findAllByOrderStatus(OrderStatus status) {
        return customerOrderRepository.findAllByOrderStatus(status);
    }

    /**
     * Create an customerOrderRepository instance
     */
    @Transactional
    public CustomerOrder create(@RequestBody OrderForm form) {
        // TODO: 2019/3/6 VALIDATION is required before creating customer order

        Store store = storeRepository.findById(form.getStoreId())
                .orElseThrow(() -> new NoSuchElementException("No such store! Please check the store Id."));
        Customer customer = customerRepository.findById(form.getCustomerId())
                .orElseThrow(() -> new NoSuchElementException("No such customer!"));
        // Update last access time
        customer = updateLastAccessTime(customer);
        List<OrderItem> orderItems = new LinkedList<>();
        form.getOrderItems().stream()
                .map(this::createOrderItem)
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

    private Customer updateLastAccessTime(Customer customer) {
        customer = customerRepository.save(customer.setLastAccessDate(LocalDateTime.now()));
        return customer;
    }

    /**
     * Change customerOrderRepository status of specific customerOrderRepository with given id
     *
     * @param id   id of the customerOrderRepository
     * @param form only <code>orderStatus</code> field is required
     */
    public CustomerOrder modifyStatus(long id, OrderForm form) {
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

    private OrderItem createOrderItem(OrderItemForm form) {
        Commodity commodity = commodityRepository
                .findById(form.getCommodityId())
                .orElseThrow(() -> new NoSuchElementException(
                        "No such commodity! Please check the commodity Id."));
        OrderItem newOrderItem = new OrderItem();
        newOrderItem.setCommodity(commodity)
                .setAmount(form.getAmount());
        return orderItemRepository.save(newOrderItem);

    }
}
