package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.CategoryForm;
import cn.varfunc.restaurant.domain.model.Category;
import cn.varfunc.restaurant.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Get category's information by given id, encode the <code>ID</code> in the url
     */
    @GetMapping("/category/{id}")
    public Category getCategory(@PathVariable long id) {
        return categoryService.findById(id);
    }

    /**
     * Create a new category
     * Required fields: <code>name</code>, <code>storeId</code>
     */
    @PostMapping("/category")
    public Category createCategory(@RequestBody CategoryForm form) {
        return categoryService.addCategory(form);
    }

    /**
     * Modify specified category information by given id, encode the <code>id</code> in the url<br>
     * Only the modified field are needed
     */
    @PatchMapping("/category/{id}")
    public Category modifyCategoryInfo(@PathVariable long id, @RequestBody CategoryForm form) {
        return categoryService.modifyInfo(id, form);
    }
}
