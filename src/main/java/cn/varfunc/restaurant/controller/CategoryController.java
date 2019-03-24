package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.CategoryForm;
import cn.varfunc.restaurant.domain.model.Category;
import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.service.CategoryService;
import cn.varfunc.restaurant.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final StoreService storeService;

    @Autowired
    public CategoryController(CategoryService categoryService, StoreService storeService) {
        this.categoryService = categoryService;
        this.storeService = storeService;
    }

    /**
     * Get category's information by given id, encode the <code>ID</code> in the url
     */
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Category getCategory(@PathVariable long id) {
        return categoryService.findById(id);
    }

    /**
     * Get all categories of a given store
     */
    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getAllCategories(@RequestParam(name = "storeId") long storeId) {
        Store store = storeService.findById(storeId);
        return categoryService.findAllByStore(store);
    }

    /**
     * Create a new category
     * Required fields: <code>name</code>, <code>storeId</code>
     */
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody CategoryForm form) {
        final String name = Objects.requireNonNull(form.getName());
        final Long storeId = Objects.requireNonNull(form.getStoreId());
        final Store store = storeService.findById(storeId);
        return categoryService.create(name, store);
    }

    /**
     * Modify specified category information by given id, encode the <code>id</code> in the url<br>
     * Only the modified field are needed
     */
    @PatchMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Category modifyCategory(@PathVariable long id, @RequestBody CategoryForm form) {
        final String name = Objects.requireNonNull(form.getName());
        return categoryService.modify(id, name);
    }

    /**
     * Delete category by given id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long id) {
        categoryService.deleteById(id);
    }
}
