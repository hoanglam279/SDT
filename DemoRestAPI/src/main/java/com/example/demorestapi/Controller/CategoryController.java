package com.example.demorestapi.Controller;

import com.example.demorestapi.Model.Category;
import com.example.demorestapi.Model.Product;
import com.example.demorestapi.Service.CategoryService;
import com.example.demorestapi.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("*")
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;


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

    @PostMapping("/categories/add")
    public ResponseEntity<?> saveCategory(@RequestBody Category category) {
        if (categoryService.isCategoryNameExist(category.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên danh mục đã tốn tại");
        }
        Category savedCategory = categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PutMapping("/categories/edit/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category existingCategory = categoryService.findCategoryById(id);
        if (categoryService.isCategoryNameExist(category.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên danh mục đã tốn tại");
        }
        existingCategory.setName(category.getName());
        Category updatedCategory = categoryService.saveCategory(existingCategory);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
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
