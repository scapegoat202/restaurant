package cn.varfunc.restaurant.controller;

import cn.varfunc.restaurant.domain.form.CategoryForm;
import cn.varfunc.restaurant.domain.model.Category;
import cn.varfunc.restaurant.domain.response.ApiResponse;
import cn.varfunc.restaurant.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Get category's information by given id, encode the <code>ID</code> in the url
     */
    @GetMapping("/{id}")
    public ApiResponse getCategory(@PathVariable long id) {
        Category category = categoryService.findById(id);
        return ApiResponse.builder()
                .data(category)
                .build();
    }

    /**
     * Create a new category
     * Required fields: <code>name</code>, <code>storeId</code>
     */
    @PostMapping
    public ApiResponse createCategory(@RequestBody CategoryForm form) {
        Category category = categoryService.create(form);
        return ApiResponse.builder()
                .data(category)
                .build();
    }

    /**
     * Modify specified category information by given id, encode the <code>id</code> in the url<br>
     * Only the modified field are needed
     */
    @PatchMapping("/{id}")
    public ApiResponse modifyCategoryInfo(@PathVariable long id, @RequestBody CategoryForm form) {
        Category category = categoryService.modifyInformation(id, form);
        return ApiResponse.builder()
                .data(category)
                .build();
    }
}
