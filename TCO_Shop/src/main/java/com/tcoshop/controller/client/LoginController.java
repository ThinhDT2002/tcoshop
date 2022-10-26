package com.tcoshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@GetMapping("/login")
	public String getLogin() {
		return "tco-client/user/login.html";
	}
	
	@RequestMapping("/login/failed")
	public String loginFailed(Model model) {
		model.addAttribute("message", "Tài khoản hoặc mật khẩu không chính xác!");
		return "tco-client/user/login.html";
	}
}
