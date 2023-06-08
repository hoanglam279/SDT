package com.example.demorestapi.Controller;

import com.example.demorestapi.Model.Category;
import com.example.demorestapi.Service.CategoryService;
import com.example.demorestapi.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/categories")
    public List<Category> getCategoryList() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<?> getCategoriesDetails(@PathVariable Long id) {
        Category category = categoryService.getCategoriesById(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Danh mục không tồn tại.");
        }

        return ResponseEntity.ok(category);
    }

    @PostMapping("/categories/save")
    public Category saveCategories(@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    @DeleteMapping("/categories/delete/{id}")
    public ResponseEntity<String> deleteCategories(@PathVariable Long id) {
        boolean hasProducts = productService.hasProductsByCategoryId(id);
        if (hasProducts) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể xóa danh mục vì đã có sản phẩm liên quan.");
        }
        categoryService.deleteCategoriesById(id);

        return ResponseEntity.ok("Xóa thành công.");
    }
}
