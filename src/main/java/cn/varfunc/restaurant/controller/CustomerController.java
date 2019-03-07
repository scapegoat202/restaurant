package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.CustomerForm;
import cn.varfunc.restaurant.domain.model.Customer;
import cn.varfunc.restaurant.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Get customer's information by given id, encode the <code>id</code> in the url
     */
    @GetMapping("/customer/{id}")
    public Customer getCustomer(@PathVariable long id) {
        return customerService.findById(id);
    }

    /**
     * Add a new customer's information<br>
     * required field: <code>name</code>, <code>gender</code>
     */
    @PostMapping("/customer")
    public Customer newCustomer(@RequestBody CustomerForm form) {
        return customerService.addCustomer(form);
    }
}
