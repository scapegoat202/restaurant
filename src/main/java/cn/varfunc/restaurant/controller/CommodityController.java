package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.CommodityForm;
import cn.varfunc.restaurant.domain.model.Commodity;
import cn.varfunc.restaurant.domain.response.ApiResponse;
import cn.varfunc.restaurant.service.CommodityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ApiResponse getCommodityById(@PathVariable long id) {
        Commodity commodity = commodityService.getById(id);
        return ApiResponse.builder()
                .data(commodity)
                .build();
    }

    /**
     * Add a commodity<br>
     * Required fields: <code>name</code>, <code>price</code>
     */
    @PostMapping
    public ApiResponse addCommodity(@RequestBody CommodityForm form) {
        Commodity commodity = commodityService.add(form);
        return ApiResponse.builder()
                .data(commodity)
                .build();
    }

    /**
     * Modify specified commodity's information by given id, encode the <code>id</code> in the url<br>
     * Only modified fields are needed.
     */
    @PatchMapping("/{id}")
    public ApiResponse modifyCommodityInfo(@PathVariable long id, @RequestBody CommodityForm form) {
        Commodity commodity = commodityService.modifyInfo(id, form);
        return ApiResponse.builder()
                .data(commodity)
                .build();
    }
}
