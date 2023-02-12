package com.example.cartservice.controller;

import com.example.cartservice.entity.Cart;
import com.example.cartservice.entity.Product;
import com.example.cartservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @PostMapping("/addToCart")
    public ResponseEntity<Cart> addProduct (@RequestParam Long userId, @RequestBody Product product) {
        return  cartService.addToCart(userId, product);
    }

    @GetMapping("/getAllCheckOut")
    public ResponseEntity<List<Cart>> getAllCheckOut(@RequestParam Long userId) {
        return cartService.getAllCheckOut(userId);
    }

    @PutMapping(path = "/updateCheckOut")
    public ResponseEntity<Cart> updateCheckOut(@RequestParam Long userId, @RequestBody Cart cart) {
        return cartService.updateCheckOut(userId, cart);
    }

    @DeleteMapping("/deleteAllInCart")
    public ResponseEntity<Cart> deleteAllProduct (@RequestParam Long userId) {
        return  cartService.deleteAll(userId);
    }

    @DeleteMapping("/deleteCheckOut")
    public ResponseEntity<Cart> deleteCheckOut(@RequestParam Long checkOutId) {
        return cartService.deleteCheckOut(checkOutId);

    }
}
