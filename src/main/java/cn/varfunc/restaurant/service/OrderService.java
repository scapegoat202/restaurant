package cn.varfunc.restaurant.service;

import cn.varfunc.restaurant.domain.model.*;
import cn.varfunc.restaurant.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    public CustomerOrder create(@NonNull Store store, @NonNull Customer customer, @NonNull List<OrderItem> orderItems,
                                @NonNull BigDecimal amount, @NonNull String tableNumber) {
        // TODO: 2019/3/6 VALIDATION is required before creating customer order
        // Update last access time
        CustomerOrder newCustomerOrder = new CustomerOrder();
        newCustomerOrder.setAmount(amount)
                .setCustomer(customer)
                .setStore(store)
                .setOrderStatus(OrderStatus.SUBMITTED)
                .setOrderItems(orderItems)
                .setTableNumber(tableNumber)
                .setTimeCreated(LocalDateTime.now());
        return customerOrderRepository.save(newCustomerOrder);
    }

    /**
     * Change customerOrderRepository status of specific customerOrderRepository with given id
     *
     * @param id id of the customerOrderRepository
     */
    public CustomerOrder modifyStatus(long id, @NonNull OrderStatus status) {
        customerOrderRepository.findById(id).ifPresent(it -> {
            it.setOrderStatus(status)
                    .setTimeFinished(LocalDateTime.now());
            customerOrderRepository.save(it);
        });
        return customerOrderRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("No such customerOrderRepository with id " + id));
    }
}
