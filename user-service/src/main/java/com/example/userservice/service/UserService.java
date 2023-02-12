package com.example.userservice.service;

import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
    public ResponseEntity<User> addUser(User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }
    public List<User> getAllUser(){
        return userRepository.findAll();
    }
    public Optional<User> findUserById(Long id){
        return userRepository.findById(id);
    }
    public ResponseEntity<User> updateUser(Long id, User user){
        Optional<User> userUpdate = findUserById(id);
        if(userUpdate.isPresent()){
            User foundUser = userUpdate.get();
            foundUser.setUserName(user.getUserName());
            foundUser.setPassword(user.getPassword());
            foundUser.setRole(user.getRole());
            userRepository.save(foundUser);

            return ResponseEntity.ok(userUpdate.get());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    public ResponseEntity<User> deleteUser(Long id) {
        Optional<User> user = findUserById(id);

        if(user.isPresent()) {
            User foundUser = user.get();

            if(foundUser.getRole().toLowerCase().contains("seller")) {
                String url = "http://localhost:8092/api/products/deleteAllProduct?userId=" + foundUser.getUserId();
                restTemplate.delete(url);
            } else {
                String url2 = "http://localhost:8093/api/cart/deleteAllInCart?userId=" + foundUser.getUserId();
                restTemplate.delete(url2);
            }
            userRepository.delete(foundUser);
            return ResponseEntity.ok(user.get());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
