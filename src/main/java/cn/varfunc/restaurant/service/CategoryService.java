package cn.varfunc.restaurant.service;

import cn.varfunc.restaurant.domain.model.Category;
import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.domain.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Add a new category.
     */
    public Category create(@NonNull String name, @NonNull Store store) {
        Category newCategory = new Category();
        newCategory.setName(name)
                .setStore(store);
        return categoryRepository.save(newCategory);
    }

    /**
     * Get a category instance by given id.
     *
     * @param id the category's id
     */
    public Category findById(@NonNull long id) {
        Optional<Category> category = this.categoryRepository.findById(id);
        return category.orElseThrow(() -> new NoSuchElementException("No such categoryRepository!"));
    }

    /**
     * Get all categories of a given store
     *
     * @param store the store that owns the categories
     */
    public List<Category> findAllByStore(@NonNull Store store) {
        return categoryRepository.findAllByStore(store);
    }

    /**
     * Get all categories by given collection of ids
     */
    public List<Category> findAllByIds(Iterable<Long> ids) {
        return categoryRepository.findAllById(ids);
    }

    /**
     * Modify specified category's information of given id.
     */
    public Category modify(@NonNull long id, @NonNull String name) {
        Optional<Category> category = categoryRepository.findById(id);
        category.ifPresent(it -> {
            if (!Objects.equals(name, it.getName())) {
                it.setName(name);
                categoryRepository.save(it);
            }
        });
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No such category"));
    }

    /**
     * Delete category by given id
     */
    public void deleteById(@NonNull long id) {
        categoryRepository.deleteById(id);
    }
}
