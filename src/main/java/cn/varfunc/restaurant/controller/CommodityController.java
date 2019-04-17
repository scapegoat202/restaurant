package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.CommodityForm;
import cn.varfunc.restaurant.domain.model.Category;
import cn.varfunc.restaurant.domain.model.Commodity;
import cn.varfunc.restaurant.domain.model.CommodityStatus;
import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.service.CategoryService;
import cn.varfunc.restaurant.service.CommodityService;
import cn.varfunc.restaurant.service.FileService;
import cn.varfunc.restaurant.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/commodities")
public class CommodityController {
    private final StoreService storeService;
    private final CommodityService commodityService;
    private final CategoryService categoryService;
    private final FileService fileService;
    private final String bucketName;

    @Autowired
    public CommodityController(StoreService storeService,
                               CommodityService commodityService,
                               CategoryService categoryService,
                               FileService fileService,
                               @Value("${cn.varfunc.restaurant.minio.bucket-name}") String bucketName) {
        this.storeService = storeService;
        this.commodityService = commodityService;
        this.categoryService = categoryService;
        this.fileService = fileService;
        this.bucketName = bucketName;
    }

    /**
     * Get commodity's information by given id, encode the <code>id</code> in the url
     */
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Commodity getCommodityById(@PathVariable long id) {
        Commodity commodity = commodityService.findById(id);
        String url = fileService.getFileURL(this.bucketName, commodity.getImageUUID());
        return commodity.setImageURL(url);
    }

    /**
     * Get all commodities of given store
     */
    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Commodity> getAllCommodities(@RequestParam(name = "storeId") Long storeId,
                                             @RequestParam(name = "categoryId") Long categoryId) {
        if (storeId != null && categoryId == null) {
            Store store = storeService.findById(storeId);
            List<Commodity> commodities = commodityService.findAllByStore(store);
            return setImageUrl(commodities);
        }

        if (storeId == null && categoryId != null) {
            Category category = categoryService.findById(categoryId);
            List<Commodity> commodities = commodityService.findAllByCategory(category);
            return setImageUrl(commodities);
        }

        throw new IllegalArgumentException("请求参数不正确");
    }

    private List<Commodity> setImageUrl(List<Commodity> commodities) {
        for (Commodity commodity : commodities) {
            String fileURL = fileService.getFileURL(this.bucketName, commodity.getImageUUID());
            commodity.setImageURL(fileURL);
        }
        return commodities;
    }

    /**
     * Add a commodity<br>
     * Required fields: <code>name</code>, <code>price</code>
     */
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Commodity addCommodity(@RequestBody CommodityForm form) {
        final Long storeId = form.getStoreId();
        final List<Long> categoryIds = form.getCategories();
        final Store store = storeService.findById(storeId);
        final List<Category> categories = categoryService.findAllByIds(categoryIds);
        final UUID uuid = UUID.fromString(form.getImageUUID());
        Commodity commodity = commodityService.create(form.getName(),
                form.getPrice(),
                form.getInventory(),
                store,
                categories,
                uuid);
        String url = fileService.getFileURL(this.bucketName, commodity.getImageUUID());
        return commodity.setImageURL(url);
    }

    /**
     * Modify specified commodity's information by given id, encode the <code>id</code> in the url<br>
     * Only modified fields are needed.
     */
    @PatchMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Commodity modifyCommodity(@PathVariable long id, @RequestBody CommodityForm form) {
        List<Category> categories = null;
        if (Objects.nonNull(form.getCategories())) {
            categories = categoryService.findAllByIds(form.getCategories());
        }
        // TODO: 2019/3/25 Optimize this someday
        CommodityStatus status;
        if (Objects.isNull(form.getStatus())) {
            status = null;
        } else {
            CommodityStatus s = CommodityStatus.parse(form.getStatus());
            if (!Objects.equals(s, CommodityStatus.UNKNOWN)) {
                status = s;
            } else {
                status = null;
            }
        }
        Commodity commodity = commodityService.modify(id,
                form.getName(),
                form.getPrice(),
                form.getInventory(),
                categories,
                status);
        String url = fileService.getFileURL(this.bucketName, commodity.getImageUUID());
        return commodity.setImageURL(url);
    }
}
