package com.tcoshop.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tcoshop.entity.Authority;
import com.tcoshop.entity.Role;
import com.tcoshop.entity.User;
import com.tcoshop.service.RoleService;
import com.tcoshop.service.UserService;
import com.tcoshop.util.PasswordUtil;

@RestController
@CrossOrigin("*")
public class RegisterAPI {
	@Autowired
	PasswordUtil passwordUtil;
	@Autowired
	RoleService roleService;
	@Autowired
	UserService userService;
	@PostMapping("/api/register")
	public ResponseEntity<User> register(@RequestBody User user) {
		String activateCode = user.getUsername() + String.valueOf(passwordUtil.generatePassword(8));
		user.setActivateCode(activateCode);
		String forgotPassworCode = user.getUsername() + String.valueOf(passwordUtil.generatePassword(9));
		user.setForgotPasswordCode(forgotPassworCode);
		user.setStatus(false);
		user.setAvatar("default-user.png");
		user.setFullname("");
		Role userRole = roleService.getRole("USER");
		Authority authority = new Authority();
		authority.setRole(userRole);
		authority.setUser(user);
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authority);
		user.setAuthorities(authorities);		
		return ResponseEntity.ok(user);
	}
}
