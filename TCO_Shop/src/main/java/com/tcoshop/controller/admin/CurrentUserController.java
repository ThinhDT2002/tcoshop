package com.tcoshop.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

import com.tcoshop.entity.User;
// request nao cung chay thang controllerAdvice
@ControllerAdvice
public class CurrentUserController {
	private RestTemplate restTemplate = new RestTemplate();
	@ModelAttribute("currentUser")
	public User currentUser(Authentication authentication) {
		try {
			String currentUserUsername = authentication.getName();
		    String url = "http://localhost:8080/api/user/" + currentUserUsername;
		    ResponseEntity<User> entity = restTemplate.getForEntity(url, User.class);
		    User currentUser = entity.getBody();
		    return currentUser;
		} catch (NullPointerException e) {
			return null;
		}
	}
}
