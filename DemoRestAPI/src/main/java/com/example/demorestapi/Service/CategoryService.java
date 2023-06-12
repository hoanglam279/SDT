package com.example.demorestapi.Service;

import com.example.demorestapi.Model.Category;
import com.example.demorestapi.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
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
