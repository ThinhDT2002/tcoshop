package com.tcoshop.controller.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.tcoshop.entity.User;

@Controller
public class RegisterController {
	private RestTemplate restTemplate = new RestTemplate();
	
	@GetMapping("/register")
	public String getRegister(@ModelAttribute("user") User user) {
		user = new User();
		return "tco-client/user/register.html";
	}
	
	@PostMapping("/register")
	public String doRegister(Model model, @ModelAttribute("user") User user) {
		
		String postUrl = "http://localhost:8080/api/register";
		HttpEntity<User> userEntity = new HttpEntity<>(user);
		ResponseEntity<User> responseEntity = restTemplate.postForEntity(postUrl, userEntity, User.class);
		user = responseEntity.getBody();
		return "tco-client/user/register.html";
	}
}
