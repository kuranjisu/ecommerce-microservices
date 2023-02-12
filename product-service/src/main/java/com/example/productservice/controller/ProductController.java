package com.example.productservice.controller;

import com.example.productservice.entity.Product;
import com.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
    public Product addProduct(@RequestParam Long userId, @RequestBody Product product) {
        return productService.addProduct(userId, product);
    }
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProduct(@RequestParam Long userId) {
        return productService.getAllProduct(userId);
    }

    @PutMapping("/updateProductBySeller")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }
    @DeleteMapping("/deleteProductById")
    public ResponseEntity<Product> deleteProductById(@RequestParam Long productId) {
        return productService.deleteProductById(productId);
    }

    @GetMapping("/getProductById")
    public @ResponseBody Optional<Product> getProductById(Long productId){
        return productService.getProductById(productId);
    }

    @DeleteMapping("/deleteAllProduct")
    public ResponseEntity<Product> deleteAllProduct (@RequestParam Long userId) {
        return  productService.deleteAll(userId);
    }
    @PutMapping ("/updateProductByCart")
    public void updateProductByCart(@RequestBody Product product) {
        productService.updateProductByCart(product);
    }
}
