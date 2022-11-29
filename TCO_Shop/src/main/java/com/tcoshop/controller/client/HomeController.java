package com.tcoshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/home")
	public String page() {
		return "tco-client/layout-home/home.html";
	}
}
