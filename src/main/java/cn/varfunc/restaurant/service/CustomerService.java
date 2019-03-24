package cn.varfunc.restaurant.service;

import cn.varfunc.restaurant.domain.model.Customer;
import cn.varfunc.restaurant.domain.model.CustomerOrder;
import cn.varfunc.restaurant.domain.model.Gender;
import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.domain.repository.CustomerOrderRepository;
import cn.varfunc.restaurant.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerOrderRepository customerOrderRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           CustomerOrderRepository customerOrderRepository) {
        this.customerRepository = customerRepository;
        this.customerOrderRepository = customerOrderRepository;
    }

    /**
     * Get a customerRepository instance by given id.
     */
    public Customer findById(long id) {
        Optional<Customer> customer = this.customerRepository.findById(id);
        return customer.orElseThrow(() -> new NoSuchElementException("No such customer!"));
    }

    /**
     * Add a new customerRepository.
     */
    public Customer create(@NonNull String name, Gender gender, String email,
                           @NonNull LocalDate registerDate, @NonNull LocalDateTime lastAccessTime) {
        Customer newCustomer = new Customer();
        newCustomer.setName(name)
                .setGender(gender)
                .setEmail(email)
                .setRegisterDate(registerDate)
                .setLastAccessDate(lastAccessTime);
        return customerRepository.save(newCustomer);
    }

    /**
     * Get all by store.
     */
    public List<Customer> findAllByStore(Store store) {
        // TODO: 2019/3/20 Require feasibility validation
        List<CustomerOrder> orders = customerOrderRepository.findAllByStore(store);
        List<Customer> customers = new LinkedList<>();
        orders.stream()
                .map(CustomerOrder::getCustomer)
                .collect(Collectors.toMap(Customer::getId, Function.identity(), (a, b) -> a))
                .forEach((k, v) -> customers.add(v));
        return customers;
    }

    /**
     * Modify customerRepository information by given id.
     */
    public Customer modify(long id, String name, String email, Gender gender) {
        Customer customer = findById(id);
        boolean isModified = false;

        if (Objects.nonNull(name) &&
                !Objects.equals(name, customer.getName())) {
            customer.setName(name);
            isModified = true;
        }

        if (Objects.nonNull(email) &&
                !Objects.equals(email, customer.getEmail())) {
            customer.setEmail(email);
            isModified = true;
        }

        if (Objects.nonNull(gender) &&
                !Objects.equals(gender, customer.getGender())) {
            customer.setGender(gender);
            isModified = true;
        }

        if (isModified) {
            customer = this.customerRepository.save(customer);
        }
        return customer;
    }

    /**
     * Record the very last time of the customer's visit
     */
    public Customer updateLastAccessTime(Customer customer) {
        customer = customerRepository.save(customer.setLastAccessDate(LocalDateTime.now()));
        return customer;
    }

}
