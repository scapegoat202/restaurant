package cn.varfunc.restaurant.service;

import cn.varfunc.restaurant.domain.form.CustomerForm;
import cn.varfunc.restaurant.domain.model.Customer;
import cn.varfunc.restaurant.domain.model.Gender;
import cn.varfunc.restaurant.domain.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class CustomerService {
    private final CustomerRepository customer;

    @Autowired
    public CustomerService(CustomerRepository customer) {
        this.customer = customer;
    }

    /**
     * Get a customer instance by given id.
     */
    public Customer findById(long id) {
        log.info("Method: findById(), id: {}", id);
        Optional<Customer> customer = this.customer.findById(id);
        return customer.orElseThrow(() -> new NoSuchElementException("没有指定的顾客"));
    }

    /**
     * Add a new customer.
     *
     * @param form information needed for describing a new customer, the <code>name</code> and
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
        return customer.save(newCustomer);
    }

    /**
     * Record the time of customer's latest visit
     */
    public Customer updateLastAccessTime(Customer customer) {
        return this.customer.save(customer.setLastAccessDate(LocalDateTime.now()));
    }

    /**
     * Modify customer information by given id.
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
            customer = this.customer.save(customer);
        }
        return customer;
    }
}
