package com.tcoshop.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tcoshop.entity.User;
import com.tcoshop.service.UserService;

@RestController
@CrossOrigin("*")
public class UserRestController {
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
}
