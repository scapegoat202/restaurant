package cn.varfunc.restaurant.service;

import cn.varfunc.restaurant.domain.form.CommodityForm;
import cn.varfunc.restaurant.domain.model.Commodity;
import cn.varfunc.restaurant.domain.repository.CommodityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
@Service
public class CommodityService {
    private final CommodityRepository commodityRepository;
    private final CategoryService categoryService;

    @Autowired
    public CommodityService(CommodityRepository commodityRepository, CategoryService categoryService) {
        this.commodityRepository = commodityRepository;
        this.categoryService = categoryService;
    }

    /**
     * Get a commodityRepository instance by given id.
     */
    public Commodity findById(long id) {
        return commodityRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No such commodityRepository!"));
    }

    /**
     * Add a new commodityRepository instance.
     *
     * @param form Information needed for describing a new commodityRepository, <code>name</code> and
     *             <code>price</code> fields are required.
     */
    public Commodity create(CommodityForm form) {
        log.info("Method: create(), form: {}", form);
        // TODO: 2019/3/7 Validation is required before creating commodity

        Commodity newCommodity = new Commodity();
        newCommodity.setName(form.getName())
                .setPrice(form.getPrice())
                .setInventory(form.getInventory());
        form.getCategories().stream()
                .map(categoryService::findById)
                .forEach(newCommodity.getCategories()::add);
        return commodityRepository.save(newCommodity);
    }

    /**
     * Modify specified commodityRepository's information by given id.
     *
     * @param form only fields that have to be updated are needed.
     */
    public Commodity modifyInformation(long id, CommodityForm form) {
        commodityRepository.findById(id).ifPresent(it -> {
            if (Objects.nonNull(form.getName()) &&
                    !Objects.equals(form.getName(), it.getName())) {
                it.setName(form.getName());
            }

            if (Objects.nonNull(form.getPrice()) &&
                    !Objects.equals(form.getPrice(), it.getPrice())) {
                it.setPrice(form.getPrice());
            }

            if (Objects.nonNull(form.getInventory()) &&
                    !Objects.equals(form.getInventory(), it.getInventory())) {
                it.setInventory(form.getInventory());
            }

            if (Objects.nonNull(form.getCategories()) &&
                    form.getCategories().size() != 0) {
                log.info("category size pre: {}", it.getCategories().size());
                it.setCategories(categoryService.findAllByIds(form.getCategories()));
                log.info("category size after: {}", it.getCategories());
            }
            // Save changes to database
            commodityRepository.save(it);
        });

        return commodityRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No such Commodity!"));
    }
}
