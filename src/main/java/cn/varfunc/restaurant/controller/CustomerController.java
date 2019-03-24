package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.CustomerForm;
import cn.varfunc.restaurant.domain.model.Customer;
import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.service.CustomerService;
import cn.varfunc.restaurant.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
    public Customer newCustomer(@RequestBody CustomerForm form) {
        return customerService.create(form);
    }

    /**
     * Modify the information of given id
     */
    @PatchMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Customer modifyCustomerInformation(@RequestBody CustomerForm form, @PathVariable long id) {
        return customerService.modifyInformation(id, form);
    }
}
