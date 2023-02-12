package com.example.productservice.service;

import com.example.productservice.entity.Product;
import com.example.productservice.entity.User;
import com.example.productservice.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RestTemplate restTemplate;
    private static String baseUrl =  "http://localhost:8091/api/users/";

    public Product addProduct(Long userId, Product product) {
        product.setUserId(Math.toIntExact(userId));
        return productRepository.save(product);
    }

    public ResponseEntity<List<Product>> getAllProduct(Long userId){
        return ResponseEntity.ok(productRepository.findByUserId(userId));
    }

    public ResponseEntity<Product> updateProduct(Product product) {
        if (productRepository.findById(Long.valueOf(product.getProductId())).isPresent()) {
            return ResponseEntity.ok(productRepository.save(product));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Product> deleteProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            productRepository.deleteById(productId);
            return ResponseEntity.ok(product.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public Optional<Product> getProductById(Long productId){
        return productRepository.findById(productId);
    }

    @Transactional
    public ResponseEntity<Product> deleteAll(Long userId) {
        String url = baseUrl + "findUser?id=" + userId;
        User user = restTemplate.getForObject(url, User.class);

        if(user == null || !user.getRole().toLowerCase().contains("seller")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        productRepository.deleteByUserId(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void updateProductByCart(Product product){
        productRepository.save(product);
    }

}
