package com.example.demorestapi.Controller;

import com.example.demorestapi.Model.Product;
import com.example.demorestapi.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public Page<Product> getProductList(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getAllProducts(pageable);
    }


    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductDetails(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sản phẩm không tồn tại.");
        }

        return ResponseEntity.ok(product);
    }

    @PostMapping("/products/add")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        boolean isCodeExists = productService.isProductCodeExists(product.getProductCode());
        if (isCodeExists) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã sản phẩm đã tồn tại.");
        }
        Product savedProduct = productService.saveProduct(product);

        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/products/edit/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product existingProduct = productService.findProductById(id);
        Product productWithSameCode = productService.findProductByProductCode(product.getProductCode());

        if (productWithSameCode != null && !productWithSameCode.getId().equals(existingProduct.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã sản phẩm đã tồn tại.");
        }

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStatus(product.getStatus());
        existingProduct.setProductCode(product.getProductCode());
        existingProduct.setCategory(product.getCategory());

        Product savedProduct = productService.saveProduct(existingProduct);

        return ResponseEntity.ok(savedProduct);
    }

    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        boolean exists = productService.existsProductById(id);
        if (!exists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("sản phầm không tồn tại");
        }
        productService.deleteProductById(id);
        return ResponseEntity.ok("Xóa thành công.");
    }
}