package com.example.demorestapi.Repository;

import com.example.demorestapi.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByCategoryId(Long categoryId);

    boolean existsByProductCode(String productCode);

    Product findProductById(Long id);

    Product findProductByProductCode(String productCode);
}
