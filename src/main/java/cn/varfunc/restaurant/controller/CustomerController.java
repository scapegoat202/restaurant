package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.CustomerForm;
import cn.varfunc.restaurant.domain.model.Customer;
import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.domain.response.ApiResponse;
import cn.varfunc.restaurant.service.CustomerService;
import cn.varfunc.restaurant.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ApiResponse getCustomer(@PathVariable long id) {
        Customer customer = customerService.findById(id);
        return ApiResponse.builder()
                .data(customer)
                .build();
    }

    @GetMapping
    public ApiResponse getAllCustomersByStoreId(@RequestParam(name = "storeId") long storeId) {
        Store store = storeService.findById(storeId);
        List<Customer> customers = customerService.findAllByStore(store);
        return ApiResponse.builder()
                .data(customers)
                .build();
    }

    /**
     * Add a new customer's information<br>
     * required field: <code>name</code>, <code>gender</code>
     */
    @PostMapping
    public ApiResponse newCustomer(@RequestBody CustomerForm form) {
        Customer customer = customerService.addCustomer(form);
        return ApiResponse.builder()
                .data(customer)
                .build();
    }

    /**
     * Modify the information of given id
     */
    @PatchMapping("/{id}")
    public ApiResponse modifyCustomerInformation(@RequestBody CustomerForm form, @PathVariable long id) {
        Customer customer = customerService.modifyInformation(id, form);
        return ApiResponse.builder()
                .data(customer)
                .build();
    }
}
