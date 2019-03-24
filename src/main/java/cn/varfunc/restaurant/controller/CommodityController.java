package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.CommodityForm;
import cn.varfunc.restaurant.domain.model.Commodity;
import cn.varfunc.restaurant.service.CommodityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/commodities")
public class CommodityController {
    private final CommodityService commodityService;

    @Autowired
    public CommodityController(CommodityService commodityService) {
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
