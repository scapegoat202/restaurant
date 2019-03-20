package cn.varfunc.restaurant.service;

import cn.varfunc.restaurant.domain.form.CommodityForm;
import cn.varfunc.restaurant.domain.model.Commodity;
import cn.varfunc.restaurant.domain.repository.CategoryRepository;
import cn.varfunc.restaurant.domain.repository.CommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class CommodityService {
    private final CommodityRepository commodityRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CommodityService(CommodityRepository commodityRepository,
                            CategoryRepository categoryRepository) {
        this.commodityRepository = commodityRepository;
        this.categoryRepository = categoryRepository;
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
        // TODO: 2019/3/7 Validation is required before creating commodity

        Commodity newCommodity = new Commodity();
        newCommodity.setName(form.getName())
                .setPrice(form.getPrice())
                .setInventory(form.getInventory());
        form.getCategories().stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException(
                                "No such category! Please check your id.")))
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
                it.setCategories(categoryRepository.findAllById(form.getCategories()));
            }
            // Save changes to database
            commodityRepository.save(it);
        });

        return commodityRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No such Commodity!"));
    }
}
