package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.CustomerForm;
import cn.varfunc.restaurant.domain.model.Customer;
import cn.varfunc.restaurant.domain.model.Gender;
import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.service.CustomerService;
import cn.varfunc.restaurant.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final StoreService storeService;

    @Autowired
    public CustomerController(CustomerService customerService, StoreService storeService) {
        this.customerService = customerService;
        this.storeService = storeService;
    }

    /**
     * Get customer's information by given id, encode the <code>id</code> in the url
     */
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomer(@PathVariable long id) {
        return customerService.findById(id);
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomersByStoreId(@RequestParam(name = "storeId") long storeId) {
        Store store = storeService.findById(storeId);
        return customerService.findAllByStore(store);
    }

    /**
     * Add a new customer's information<br>
     * required field: <code>name</code>, <code>gender</code>
     */
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Customer addCustomer(@RequestBody CustomerForm form) {
        final String name = requireNonNull(form.getName());
        final Gender gender = Gender.parse(form.getGender());
        return customerService.create(name, gender, form.getEmail(), LocalDate.now(), LocalDateTime.now());
    }

    /**
     * Modify the information of given id
     */
    @PatchMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Customer modifyCustomer(@PathVariable long id, @RequestBody CustomerForm form) {
        final Gender gender = Gender.parse(form.getGender());
        return customerService.modify(id, form.getName(), form.getEmail(), gender);
    }
}
