package com.example.userservice.controller;

import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getAllUsers")
    public @ResponseBody List<User> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/findUser")
    public @ResponseBody Optional<User> findUser (@RequestParam Long id) {
        return userService.findUserById(id);
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser (@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser (@RequestParam Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<User> deleteUser (@RequestParam Long id) {
        return userService.deleteUser(id);
    }

}
