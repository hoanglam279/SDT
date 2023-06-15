package com.example.demorestapi.Repository;

import com.example.demorestapi.Model.Category;
import com.example.demorestapi.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    Category findCategoryById(Long id);
}
