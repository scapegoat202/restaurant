package cn.varfunc.restaurant.service;

import cn.varfunc.restaurant.domain.form.CommodityForm;
import cn.varfunc.restaurant.domain.model.Commodity;
import cn.varfunc.restaurant.domain.repository.CommodityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
public class CommodityService {
    private final CommodityRepository commodity;
    private final CategoryService categoryService;

    @Autowired
    public CommodityService(CommodityRepository commodity, CategoryService categoryService) {
        this.commodity = commodity;
        this.categoryService = categoryService;
    }

    /**
     * Get a commodity instance by given id.
     */
    public Commodity getById(long id) {
        log.info("Method: getById(), id: {}", id);
        return commodity.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No such commodity!"));
    }

    /**
     * Add a new commodity instance.
     *
     * @param form Information needed for describing a new commodity, <code>name</code> and
     *             <code>price</code> fields are required.
     */
    public Commodity add(CommodityForm form) {
        log.info("Method: add(), form: {}", form);
        // TODO: 2019/3/7 Validation is required before creating commodity

        Commodity newCommodity = new Commodity();
        newCommodity.setName(form.getName())
                .setPrice(form.getPrice())
                .setInventory(form.getInventory());
        form.getCategories().stream()
                .map(categoryService::findById)
                .forEach(newCommodity.getCategories()::add);
        return commodity.save(newCommodity);
    }

    /**
     * Modify specified commodity's information by given id.
     *
     * @param form only fields that have to be updated are needed.
     */
    public Commodity modifyInfo(long id, CommodityForm form) {
        log.info("Method: modifyInfo(), id: {}, form: {}", id, form);
        commodity.findById(id).ifPresent(it -> {
            if (form.getName() != null && !form.getName().equals(it.getName())) {
                it.setName(form.getName());
            }

            if (form.getPrice() != null && !form.getPrice().equals(it.getPrice())) {
                it.setPrice(form.getPrice());
            }

            if (form.getInventory() != null && !form.getInventory().equals(it.getInventory())) {
                it.setInventory(form.getInventory());
            }

            if (form.getCategories() != null && form.getCategories().size() != 0) {
                log.info("category size pre: {}", it.getCategories().size());
                it.setCategories(categoryService.findAllById(form.getCategories()));
                log.info("category size after: {}", it.getCategories());
            }
            // Save changes to database
            commodity.save(it);
        });

        return commodity.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No such Commodity!"));
    }
}
