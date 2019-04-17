package cn.varfunc.restaurant.service;


import cn.varfunc.restaurant.domain.model.Category;
import cn.varfunc.restaurant.domain.model.Commodity;
import cn.varfunc.restaurant.domain.model.CommodityStatus;
import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.domain.repository.CommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
public class CommodityService {
    private final CommodityRepository commodityRepository;

    @Autowired
    public CommodityService(CommodityRepository commodityRepository) {
        this.commodityRepository = commodityRepository;
    }

    /**
     * Get a commodityRepository instance by given id.
     */
    public Commodity findById(@NonNull long id) {
        return commodityRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No such commodityRepository!"));
    }

    public List<Commodity> findAllByStore(@NonNull Store store) {
        return commodityRepository.findAllByStore(store).stream()
                .filter(it -> it.getStatus() != CommodityStatus.DELETED)
                .collect(Collectors.toList());
    }

    public List<Commodity> findAllByCategory(@NonNull Category category) {
        return category.getCommodities();
    }

    /**
     * Add a new commodityRepository instance.
     */
    public Commodity create(@NonNull String name, @NonNull BigDecimal price, Long inventory, @NonNull Store store,
                            List<Category> categories, UUID uuid) {
        // TODO: 2019/3/7 Validation is required before creating commodity
        Commodity newCommodity = new Commodity();
        newCommodity.setName(requireNonNull(name))
                .setPrice(requireNonNull(price))
                .setInventory(inventory)
                .setStore(requireNonNull(store))
                .setCategories(categories)
                .setStatus(CommodityStatus.NORMAL)
                .setImageUUID(uuid);
        return commodityRepository.save(newCommodity);
    }

    /**
     * Modify specified commodityRepository's information by given id.
     */
    public Commodity modify(@NonNull long id, String name, BigDecimal price, Long inventory,
                            List<Category> categories, CommodityStatus status) {
        commodityRepository.findById(id).ifPresent(it -> {
            if (Objects.nonNull(name) &&
                    !Objects.equals(name, it.getName())) {
                it.setName(name);
            }

            if (Objects.nonNull(price) &&
                    !Objects.equals(price, it.getPrice())) {
                it.setPrice(price);
            }

            if (Objects.nonNull(inventory) &&
                    !Objects.equals(inventory, it.getInventory())) {
                it.setInventory(inventory);
            }

            if (Objects.nonNull(categories) && categories.size() != 0) {
                it.setCategories(categories);
            }

            if (Objects.nonNull(status) &&
                    !Objects.equals(status, it.getStatus())) {
                it.setStatus(status);
            }
            // Save changes to database
            commodityRepository.save(it);
        });

        return commodityRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No such Commodity!"));
    }
}
