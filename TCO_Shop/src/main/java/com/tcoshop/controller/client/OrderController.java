package com.tcoshop.controller.client;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.tcoshop.entity.User;
import com.tcoshop.service.OrderService;

@Controller
public class OrderController {
    private RestTemplate restTemplate = new RestTemplate();
    
	@Autowired
	OrderService orderService;
	
	@RequestMapping("/checkout")
	public String checkout(Authentication authentication, Model model) {
	    String currentUserUsername = authentication.getName();
	    String url = "http://localhost:8080/api/user/" + currentUserUsername;
	    ResponseEntity<User> entity = restTemplate.getForEntity(url, User.class);
	    User currentUser = entity.getBody();
	    model.addAttribute("currentUser", currentUser);
		return "tco-client/shop/checkout";
	}
	
	@RequestMapping("/order/history")
	public String detail(Model model, HttpServletRequest request) {
		return "tco-client/order/user-history";
	}
	
	@RequestMapping("/order/track")
	public String track(Model model) {
		return "tco-client/shop/track-order";
	}
}
