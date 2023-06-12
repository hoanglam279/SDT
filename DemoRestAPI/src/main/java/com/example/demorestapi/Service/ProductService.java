package com.example.demorestapi.Service;

import com.example.demorestapi.Model.Product;
import com.example.demorestapi.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public boolean hasProductsByCategoryId(Long categoryId) {
        return productRepository.existsByCategoryId(categoryId);
    }

    public boolean existsProductById(Long id) {
        return productRepository.existsById(id);
    }

    public boolean isProductCodeExists(String productCode) {
        return productRepository.existsByProductCode(productCode);
    }

    public Product findProductById(Long id) {
        return productRepository.findProductById(id);
    }

    public Product findProductByProductCode(String productCode) {
        return productRepository.findProductByProductCode(productCode);
    }

}
