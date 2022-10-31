package com.tcoshop.controller.client;


import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
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
	
	@SuppressWarnings("null")
    @PostMapping("/register")
	public String doRegister(Model model,@ModelAttribute("user") User user,
	        @RequestParam("confirmPassword") String confirmPassword) {
	    String username = user.getUsername();
	    String email = user.getEmail();
	    String password = user.getPassword();
	    boolean userError = false;
	    
	    if(username.trim().length() == 0) {
	        userError = true;
	        model.addAttribute("usernameError", "Tài khoản không được bỏ trống");
	    } else if(username.contains(" ")) {
	        userError = true;
	        model.addAttribute("usernameError", "Tài khoản không được chứa khoảng trắng");
	    } else if(!username.matches("^[a-zA-Z0-9]+$")) {
	        userError = true;
	        model.addAttribute("usernameError", "Tài khoản không được chứa kí tự đặc biệt");	    
	    } else if(username.length() < 6 || username.length() > 30){
	        userError = true;
	        model.addAttribute("usernameError", "Tài khoản phải từ 6 đến 30 kí tự");
	    }
	    
	    if(password.trim().length() == 0) {
            userError = true;
            model.addAttribute("passwordError", "Mật khẩu không được bỏ trống");
        } else if(password.contains(" ")) {
            userError = true;
            model.addAttribute("passwordError", "Mật khẩu không được chứa khoảng trắng");
        } else if(password.length() < 6 || password.length() > 30){
            userError = true;
            model.addAttribute("passwordError", "Mật khẩu phải từ 6 đến 30 kí tự");
        }
	    
	    if(email.trim().length() == 0) {
	        userError = true;
	        model.addAttribute("emailError", "Email không được bỏ trống");	        
	    } else if(email.trim().contains(" ")) {
	        userError = true;
	        model.addAttribute("emailError", "Email không chứa kí tự khoảng trắng");
	    } else if(!email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
	            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
	        userError = true;
	        model.addAttribute("emailError", "Không đúng định dạng Email");
	    }
	    
	    if(confirmPassword.trim().length() == 0) {
	        userError = true;
	        model.addAttribute("confirmPasswordError", "Hãy xác nhận lại mật khẩu");
	    } else if(!confirmPassword.equals(password)) {
	        userError = true;
	        model.addAttribute("confirmPasswordError", "Xác nhận mật khẩu không chính xác");
	    }
	    
	    if(userError) {
	        model.addAttribute("message", "Đăng ký thất bại!");
	        return "tco-client/user/register.html";
	    }
	    try {
    		String postUrl = "http://localhost:8080/api/register";
    		HttpEntity<User> userEntity = new HttpEntity<>(user);
    		ResponseEntity<User> responseEntity = restTemplate.postForEntity(postUrl, userEntity, User.class);
    		user = responseEntity.getBody();
    		model.addAttribute("message", "Chúng tôi đã gửi một mã xác nhận vào địa chỉ Email của bạn. Vui lòng kiểm tra Email!");
	    } catch (HttpClientErrorException e) {
	        String responseErrorMessage = e.getMessage();
	        if(responseErrorMessage.contains(username) && responseErrorMessage.contains("400")) {
	            model.addAttribute("message", "Tài khoản đã được sử dụng");
	            return "tco-client/user/register.html";
	        }
	    }
		return "tco-client/user/register.html";
	}
	
	@RequestMapping("/register/verify")
	public String verifyUser(Model model, @RequestParam("activateCode") String activateCode) {
	    String url = "http://localhost:8080/api/register/verify/" + activateCode;
	    try {
	        restTemplate.getForEntity(url, User.class);
	        model.addAttribute("message", "Đăng ký thành công!");
	        return "tco-client/user/login.html";
	    } catch (HttpClientErrorException httpCEE) {
	        httpCEE.getMessage();
	        System.out.println(httpCEE);
	        model.addAttribute("message", "Có lỗi xảy ra, vui lòng thử lại!");
	        return "tco-client/user/login.html";
	    }
	    
	}
}
