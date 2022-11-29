package com.tcoshop.api;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcoshop.entity.Role;
import com.tcoshop.entity.User;
import com.tcoshop.service.RoleService;
import com.tcoshop.service.UserService;
import com.tcoshop.util.PasswordUtil;

@RestController
@CrossOrigin("*")
public class UserAPI {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PasswordUtil passwordUtil;
    @GetMapping("/api/user")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/api/user/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }
    
    @PostMapping("/api/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        String activateCode = user.getUsername() + String.valueOf(passwordUtil.generatePassword(8));
        user.setActivateCode(activateCode);
        String forgotPassworCode = user.getUsername() + String.valueOf(passwordUtil.generatePassword(9));
        user.setForgotPasswordCode(forgotPassworCode);
        Role userRole = roleService.getRole(user.getRole().getId());
        user.setRole(userRole);
        
        try {
            User userInDatabase = userService.findByUsername(user.getUsername());
            if(userInDatabase != null) {
                return ResponseEntity.badRequest().build();                      
            }
        } catch (NoSuchElementException nSEE) {
            User createdUser = userService.create(user);
            return ResponseEntity.ok(createdUser);
        }
        
        return ResponseEntity.ok(user);
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
    
    @PutMapping("/api/user/forgot-password")
    public void forgotPassword(@RequestBody User user) {
        try {
            User userInDB = userService.findByUsername(user.getUsername());
            userInDB.setPassword(user.getPassword());
            String forgotPassworCode = userInDB.getUsername() + String.valueOf(passwordUtil.generatePassword(9));
            userInDB.setForgotPasswordCode(forgotPassworCode);
            userService.update(userInDB);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException();
        }
    }
    @PutMapping("/api/user/{username}")
    public User update(@PathVariable("username") String username,@RequestBody User user) {
    	User userInDB = userService.findByUsername(username);
    	userInDB.setAvatar(user.getAvatar());
    	userInDB.setFullname(user.getFullname());
    	userInDB.setPhone(user.getPhone());
    	userInDB.setAddress(user.getAddress());
    	userInDB.setIntroduce(user.getIntroduce());
    	return userService.update(userInDB);
    }
    
    @DeleteMapping("/api/user")
    public void delete(@RequestParam("username") String username) {
        userService.deleteByUsername(username);
    }
}
