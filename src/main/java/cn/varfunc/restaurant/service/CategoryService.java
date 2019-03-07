package cn.varfunc.restaurant.service;

import cn.varfunc.restaurant.domain.form.CategoryForm;
import cn.varfunc.restaurant.domain.model.Category;
import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.domain.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class CategoryService {
    private final CategoryRepository category;
    private final StoreService storeService;

    @Autowired
    public CategoryService(CategoryRepository category, StoreService store) {
        this.category = category;
        this.storeService = store;
    }

    /**
     * Add a new category.
     *
     * @param form information needed for describing a new category, <code>name</code>
     *             and <code>storeId</code> are required
     */
    public Category addCategory(CategoryForm form) {
        log.info("Method: addCategory(), form: {}", form);
        Category newCategory = new Category();
        Store store = this.storeService.findById(form.getStoreId());
        newCategory.setName(form.getName())
                .setStore(store);
        return category.save(newCategory);
    }

    /**
     * Get a category instance by given id.
     */
    public Category findById(long id) {
        log.info("Method: findById(), id: {}", id);
        Optional<Category> category = this.category.findById(id);
        return category.orElseThrow(() -> new NoSuchElementException("No such category!"));
    }

    /**
     * Modify specified category's information of given id.
     *
     * @param form <code>name</code> are required, <code>storeId</code> can't be updated.
     */
    public Category modifyInfo(long id, CategoryForm form) {
        log.info("Method: modifyInfo(), form: {}", form);
        category.findById(id).ifPresent(it -> {
            if (form.getName() != null && !it.getName().equals(form.getName())) {
                it.setName(form.getName());
            }
            category.save(it);
        });
        return category.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No such category!"));
    }

    /**
     * Get all categories of given collection of ids.
     */
    public List<Category> findAllById(Iterable<Long> ids) {
        log.info("Method: findAllById(), ids: {}", ids);
        return category.findAllById(ids);
    }
}
