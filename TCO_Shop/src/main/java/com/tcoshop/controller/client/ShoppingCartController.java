package com.tcoshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShoppingCartController {
	@RequestMapping("/cart")
	public String cart() {
		return "tco-client/shop/cart";
	}
	
	@RequestMapping("/checkout")
	public String checkout() {
		return "tco-client/shop/checkout";
	}
}
