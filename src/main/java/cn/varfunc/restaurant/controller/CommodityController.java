package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.CommodityForm;
import cn.varfunc.restaurant.domain.model.Commodity;
import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.service.CommodityService;
import cn.varfunc.restaurant.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/commodities")
public class CommodityController {
    private final StoreService storeService;
    private final CommodityService commodityService;

    @Autowired
    public CommodityController(StoreService storeService, CommodityService commodityService) {
        this.storeService = storeService;
        this.commodityService = commodityService;
    }

    /**
     * Get commodity's information by given id, encode the <code>id</code> in the url
     */
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Commodity getCommodityById(@PathVariable long id) {
        return commodityService.findById(id);
    }

    /**
     * Get all commodities of given store
     */
    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Commodity> getAllCommodities(@RequestParam(name = "storeId") long storeId) {
        Store store = storeService.findById(storeId);
        return commodityService.findAllByStore(store);
    }


    /**
     * Add a commodity<br>
     * Required fields: <code>name</code>, <code>price</code>
     */
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Commodity addCommodity(@RequestBody CommodityForm form) {
        return commodityService.create(form);
    }

    /**
     * Modify specified commodity's information by given id, encode the <code>id</code> in the url<br>
     * Only modified fields are needed.
     */
    @PatchMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Commodity modifyCommodity(@PathVariable long id, @RequestBody CommodityForm form) {
        return commodityService.modifyInformation(id, form);
    }
}
