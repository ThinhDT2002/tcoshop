package com.tcoshop.api;

import java.util.NoSuchElementException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tcoshop.entity.Role;
import com.tcoshop.entity.User;
import com.tcoshop.service.MailService;
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
	@Autowired
	MailService mailService;
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
		user.setRole(userRole);
		try {
    		User userInDatabase = userService.findByUsername(user.getUsername());
    		if(userInDatabase != null) {
    		    return ResponseEntity.badRequest().body(userInDatabase);
    		}
		} catch (NoSuchElementException e) {
		    String mailSubject = "Kích hoạt tài khoản đã đăng ký";
		    String verifyURL = "http://localhost:8080/user/verify?activateCode=" + activateCode;
		    String mailBody = "Bấm vào liên kết để xác minh tài khoản của bạn: \r\n" + verifyURL;
		    String to = user.getEmail();
		    try {
		        mailService.send(to, mailSubject, mailBody);
		        System.out.println(activateCode);
		    } catch (MessagingException mE) {
		        mE.printStackTrace();
		        return ResponseEntity.internalServerError().build();
		    }
		    return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user);
	}
}
