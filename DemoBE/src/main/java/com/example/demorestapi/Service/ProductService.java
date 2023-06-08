package com.example.demorestapi.Service;

import com.example.demorestapi.Model.Product;
import com.example.demorestapi.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
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
