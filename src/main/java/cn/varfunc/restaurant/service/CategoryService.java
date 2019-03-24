package cn.varfunc.restaurant.service;

import cn.varfunc.restaurant.domain.form.CategoryForm;
import cn.varfunc.restaurant.domain.model.Category;
import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.domain.repository.CategoryRepository;
import cn.varfunc.restaurant.domain.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,
                           StoreRepository storeRepository) {
        this.categoryRepository = categoryRepository;
        this.storeRepository = storeRepository;
    }

    /**
     * Add a new categoryRepository.
     *
     * @param form information needed for describing a new categoryRepository, <code>name</code>
     *             and <code>storeId</code> are required
     */
    public Category create(CategoryForm form) {
        Category newCategory = new Category();
        Optional<Store> op = storeRepository.findById(form.getStoreId());
        newCategory.setName(form.getName())
                .setStore(op.orElseThrow(
                        () -> new NoSuchElementException("No such store!")));
        return categoryRepository.save(newCategory);
    }

    /**
     * Get a categoryRepository instance by given id.
     */
    public Category findById(long id) {
        Optional<Category> category = this.categoryRepository.findById(id);
        return category.orElseThrow(() -> new NoSuchElementException("No such categoryRepository!"));
    }

    /**
     * Modify specified categoryRepository's information of given id.
     *
     * @param form <code>name</code> are required, <code>storeId</code> can't be updated.
     */
    public Category modifyInformation(long id, CategoryForm form) {
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
     * Delete category by given id
     */
    public void deleteById(long id) {
        categoryRepository.deleteById(id);
    }
}
