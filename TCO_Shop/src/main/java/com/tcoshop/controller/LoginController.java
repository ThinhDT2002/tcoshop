package com.tcoshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.tcoshop.entity.User;

@Controller
public class LoginController {
    private RestTemplate restTemplate = new RestTemplate();
    
    //client
	@GetMapping("/login")
	public String getLogin() {
		return "tco-client/user/login.html";
	}
	
	@RequestMapping("/login/failed")
	public String loginFailed(Model model) {
		model.addAttribute("message", "Tài khoản hoặc mật khẩu không chính xác!");
		return "tco-client/user/login.html";
	}
	
	@RequestMapping("/logout")
	public String afterLogout(Model model) {
	    model.addAttribute("message", "Bạn đã đăng xuất");
	    return "tco-client/user/login.html";
	}
	//admin
	@RequestMapping("/tco-admin/dashboard")
    public String getAdmin(Authentication authentication, Model model) {
	    String currentUserUsername = authentication.getName();
	    String url = "http://localhost:8080/api/user/" + currentUserUsername;
	    ResponseEntity<User> entity = restTemplate.getForEntity(url, User.class);
	    User currentUser = entity.getBody();
	    model.addAttribute("currentUser", currentUser);
        return "tco-admin/home/dasb.html";
    }
	
	@RequestMapping("/tco-admin")
    public String getAdmin2() {
        return "redirect:/tco-admin/dashboard";
    }
	
	@GetMapping("/tco-admin/login")
	public String getAdminLogin(Authentication authentication) {
	    try {
	        String name = authentication.getName();	        
	    } catch (NullPointerException nPE) {
	        return "tco-admin/user/sign-in.html";
	    }
	    return "redirect:/tco-admin/dashboard";
	}
	
	@RequestMapping("/tco-admin/admin_login/failed")
	public String adminLoginFailed(Model model) {
	    model.addAttribute("adminLoginMessage", "Tài khoản hoặc mật khẩu không chính xác!");
	    return "tco-admin/user/sign-in.html";
	}
	@RequestMapping("/tco-admin/admin_logout_success")
	public String adminLogout(Model model) {
	    model.addAttribute("adminLoginMessage", "Bạn đã đăng xuất!");
	    return "tco-admin/user/sign-in.html";
	}
}
