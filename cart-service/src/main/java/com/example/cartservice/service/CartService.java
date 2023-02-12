package com.example.cartservice.service;

import com.example.cartservice.entity.Cart;
import com.example.cartservice.entity.Product;
import com.example.cartservice.entity.User;
import com.example.cartservice.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    private RestTemplate restTemplate;

    private static String baseUrl =  "http://localhost:8091/api/users/findUser?id=";
    private static String baseUrl1 = "http://localhost:8092/api/products/getProductById?productId=";

    public ResponseEntity<Cart> addToCart(Long userId, Product product){
        String url = baseUrl + userId;
        String url1 = baseUrl1 + product.getProductId();
        User user = restTemplate.getForObject(url, User.class);
        Product product1 = restTemplate.getForObject(url1, Product.class);

        if(user == null || !user.getRole().toLowerCase().contains("customer")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if(product1 == null ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(product.getQuantity() > product1.getQuantity()){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        Cart cart = new Cart();
        cart.setProductId(Math.toIntExact(product1.getProductId()));
        cart.setUserId(Math.toIntExact(userId));
        cart.setProductName(product1.getProductName());
        cart.setProductQuantity(product.getQuantity());
        cart.setPrice(product1.getPrice());
        cart.setProductTotal(product1.getPrice() * product.getQuantity());
        product1.setQuantity(product1.getQuantity() - product.getQuantity());

        RestTemplate restTemplate1 = new RestTemplate();
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Product> request = new HttpEntity<>(product1, headers);
        restTemplate1.exchange(
                "http://localhost:8092/api/products/updateProductByCart",
                HttpMethod.PUT,
                request,
                Void.class);

        return ResponseEntity.ok(cartRepository.save(cart));
    }

    public ResponseEntity<List<Cart>> getAllCheckOut(Long userId) {
        return ResponseEntity.ok(cartRepository.findByUserId(userId));
    }

    public ResponseEntity<Cart> updateCheckOut(Long userId,  Cart cart){
        Optional<Cart> checkout = cartRepository.findById(Long.valueOf(cart.getCheckOutId()));

        if(checkout == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String url = baseUrl + userId;
        User user = restTemplate.getForObject(url, User.class);
        String url1 = baseUrl1 + cart.getProductId();
        Product product = restTemplate.getForObject(url1, Product.class);

        if(user == null || !user.getRole().toLowerCase().contains("customer")) {return null;}

        if(cart.getCheckOutId() > product.getQuantity()){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        if(checkout.isPresent()){
            Cart foundCheckOut = checkout.get();
            product.setQuantity(product.getQuantity() + foundCheckOut.getProductQuantity());
            foundCheckOut.setProductQuantity(cart.getProductQuantity());
            foundCheckOut.setProductTotal(cart.getProductQuantity() * foundCheckOut.getPrice());
            cartRepository.save(foundCheckOut);
            if(foundCheckOut.getProductQuantity() == 0){
                cartRepository.delete(foundCheckOut);
            }
        }

        System.out.println(product.getQuantity());

        product.setQuantity(product.getQuantity() - cart.getProductQuantity());

        RestTemplate restTemplate1 = new RestTemplate();
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Product> request = new HttpEntity<>(product, headers);
        restTemplate1.exchange(
                "http://localhost:8092/api/products/updateProductByCart",
                HttpMethod.PUT,
                request,
                Void.class);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Cart> deleteAll(Long userId){
        String url = baseUrl + userId;
        User user = restTemplate.getForObject(url, User.class);

        if(user == null || !user.getRole().toLowerCase().contains("customer")) {return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);}

        cartRepository.deleteByUserId(userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Cart> deleteCheckOut(Long checkOutId) {
        Optional<Cart> checkOut = cartRepository.findById(checkOutId);
        if (checkOut.isPresent()) {
            cartRepository.deleteById(checkOutId);
            return ResponseEntity.ok(checkOut.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
