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
    private final CategoryRepository categoryRepository;
    private final StoreService storeService;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, StoreService store) {
        this.categoryRepository = categoryRepository;
        this.storeService = store;
    }

    /**
     * Add a new categoryRepository.
     *
     * @param form information needed for describing a new categoryRepository, <code>name</code>
     *             and <code>storeId</code> are required
     */
    public Category create(CategoryForm form) {
        log.info("Method: create(), form: {}", form);
        Category newCategory = new Category();
        Store store = this.storeService.findById(form.getStoreId());
        newCategory.setName(form.getName())
                .setStore(store);
        return categoryRepository.save(newCategory);
    }

    /**
     * Get a categoryRepository instance by given id.
     */
    public Category findById(long id) {
        log.info("Method: findById(), id: {}", id);
        Optional<Category> category = this.categoryRepository.findById(id);
        return category.orElseThrow(() -> new NoSuchElementException("No such categoryRepository!"));
    }

    /**
     * Modify specified categoryRepository's information of given id.
     *
     * @param form <code>name</code> are required, <code>storeId</code> can't be updated.
     */
    public Category modifyInformation(long id, CategoryForm form) {
        log.info("Method: modifyInformation(), form: {}", form);
        categoryRepository.findById(id).ifPresent(it -> {
            if (form.getName() != null && !it.getName().equals(form.getName())) {
                it.setName(form.getName());
            }
            categoryRepository.save(it);
        });
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No such categoryRepository!"));
    }

    /**
     * Get all categories of given collection of ids.
     */
    List<Category> findAllByIds(Iterable<Long> ids) {
        log.info("Method: findAllByIds(), ids: {}", ids);
        return categoryRepository.findAllById(ids);
    }
}
