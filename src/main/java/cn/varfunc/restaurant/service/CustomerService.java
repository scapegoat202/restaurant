package cn.varfunc.restaurant.service;

import cn.varfunc.restaurant.domain.form.CustomerForm;
import cn.varfunc.restaurant.domain.model.Customer;
import cn.varfunc.restaurant.domain.model.CustomerOrder;
import cn.varfunc.restaurant.domain.model.Gender;
import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.domain.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final OrderService orderService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           OrderService orderService) {
        this.customerRepository = customerRepository;
        this.orderService = orderService;
    }

    /**
     * Get a customerRepository instance by given id.
     */
    public Customer findById(long id) {
        log.info("Method: findById(), id: {}", id);
        Optional<Customer> customer = this.customerRepository.findById(id);
        return customer.orElseThrow(() -> new NoSuchElementException("No such customer!"));
    }

    /**
     * Add a new customerRepository.
     *
     * @param form information needed for describing a new customerRepository, the <code>name</code> and
     *             <code>gender</code> fields are required.
     */
    public Customer addCustomer(CustomerForm form) {
        log.info("Method: addCustomer(), form: {}", form);
        Customer newCustomer = new Customer();
        newCustomer.setName(form.getName())
                .setGender(Gender.parse(form.getGender()))
                .setEmail(form.getEmail())
                .setRegisterDate(LocalDate.now())
                .setLastAccessDate(LocalDateTime.now());
        return customerRepository.save(newCustomer);
    }

    /**
     * Get all by store.
     */
    public List<Customer> findAllByStore(Store store) {
        List<CustomerOrder> orders = orderService.findAllByStore(store);
        List<Customer> customers = new LinkedList<>();
        orders.stream()
                .map(CustomerOrder::getCustomer)
                .collect(Collectors.toMap(Customer::getId, Function.identity(), (a, b) -> a))
                .forEach((k, v) -> customers.add(v));
        return customers;
    }

    /**
     * Record the time of customerRepository's latest visit
     */
    Customer updateLastAccessTime(Customer customer) {
        return this.customerRepository.save(customer.setLastAccessDate(LocalDateTime.now()));
    }

    /**
     * Modify customerRepository information by given id.
     */
    public Customer modifyInformation(long id, CustomerForm form) {
        Customer customer = findById(id);
        boolean isModified = false;

        if (Objects.nonNull(form.getName()) &&
                !Objects.equals(form.getName(), customer.getName())) {
            customer.setName(form.getName());
            isModified = true;
        }

        if (Objects.nonNull(form.getEmail()) &&
                !Objects.equals(form.getEmail(), customer.getEmail())) {
            customer.setEmail(form.getEmail());
            isModified = true;
        }

        Gender gender = Gender.parse(form.getGender());
        if (Objects.nonNull(form.getGender()) &&
                !Objects.equals(gender, customer.getGender())) {
            customer.setGender(gender);
            isModified = true;
        }

        if (isModified) {
            customer = this.customerRepository.save(customer);
        }
        return customer;
    }
}
