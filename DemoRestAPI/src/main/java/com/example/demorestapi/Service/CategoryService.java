package com.example.demorestapi.Service;

import com.example.demorestapi.Model.Category;
import com.example.demorestapi.Model.Product;
import com.example.demorestapi.Repository.CategoryRepository;
import com.example.demorestapi.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoriesById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategoriesById(Long id) {
        categoryRepository.deleteById(id);
    }

    public boolean isCategoryExists(Long id) {
        return categoryRepository.existsById(id);
    }
}
