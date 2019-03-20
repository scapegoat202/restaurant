package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.StoreForm;
import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    /**
     * Get a store's information by given id, encode the <code>id</code> in the <code>url</code>
     */
    @GetMapping("/{id}")
    public Store getStore(@PathVariable long id) {
        return storeService.findById(id);
    }

    /**
     * Create a new Store<br>
     * Required field: <code>name</code>
     */
    @PostMapping
    public Store createStore(@RequestBody StoreForm store) {
        return storeService.createStore(store);
    }

    /**
     * Modify specified store's information by given id, only modified fields are needed
     */
    @PatchMapping("/{id}")
    public Store modifyStoreInfo(@RequestBody @Validated StoreForm form, @PathVariable long id) {
        return storeService.modifyInformation(id, form);
    }
}
