package com.tcoshop.api;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tcoshop.entity.User;
import com.tcoshop.service.UserService;

@RestController
@CrossOrigin("*")
public class UserAPI {
    @Autowired
    UserService userService;
    @GetMapping("/api/user")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/api/user/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }
    
    @PostMapping("/api/user/change-password") 
    public ResponseEntity<User> changePasword(@RequestBody User user) {
        try {
            User userInDB = userService.findByUsername(user.getUsername());          
            if(user.getPassword().equals(userInDB.getPassword())) {
                return ResponseEntity.badRequest().body(userInDB);
            }
            userInDB.setPassword(user.getPassword());
            userService.update(userInDB);
            return ResponseEntity.ok(userInDB);
        } catch (NoSuchElementException nSEE) {
            System.out.println(nSEE.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
