package com.tcoshop.controller.client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcoshop.entity.User;
import com.tcoshop.util.PasswordUtil;

@Controller
public class UserController {
    @Autowired
    PasswordUtil passwordUtil;
    private RestTemplate restTemplate = new RestTemplate();
    @GetMapping("/user/change-password")
    public String getChangePasswordForm() {
        return "tco-client/user/change-password.html";
    }
    @PostMapping("/user/change-password")
    public String changePassword(Model model, Authentication authentication,
            @RequestParam("current-password") String currentPassword,
            @RequestParam("new-password") String newPassword,
            @RequestParam("confirm-new-password") String confirmNewPassword,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request, HttpServletResponse resp) {
        boolean valid = true;
        if(!passwordUtil.validatePassword(currentPassword)) {
            valid = false;
            model.addAttribute("errorPassword", "Mật khẩu không hợp lệ");
        }
        if(!passwordUtil.validatePassword(newPassword)) {
            valid = false;
            model.addAttribute("errorNewPassword", "Mật khẩu mới không hợp lệ");
        }
        if(!passwordUtil.validatePassword(confirmNewPassword)) {
            valid = false;
            model.addAttribute("errorConfirmNewPassword", "Xác nhận mật khẩu mới không hợp lệ");
        }
        if(valid == false) {
            model.addAttribute("changePasswordMessage", "Đổi mật khẩu thất bại!");
            return "tco-client/user/change-password.html";
        }
        if(!confirmNewPassword.endsWith(newPassword)) {
            model.addAttribute("changePasswordMessage", "Xác nhận mật khẩu không trùng khớp!");
            return "tco-client/user/change-password.html";
        }
        String username = authentication.getName();
        User user = new User();
        user.setUsername(username);
        user.setPassword(newPassword);
        HttpEntity<User> entityUser = new HttpEntity<>(user);
        String url = "http://localhost:8080/api/user/change-password";
        try {
            ResponseEntity<User> responseEntity = restTemplate.postForEntity(url, entityUser, User.class);
            new SecurityContextLogoutHandler().logout(request, resp, authentication);
            model.addAttribute("message", "Đổi mật khẩu thành công. Vui lòng đăng nhập lại!");
            return "tco-client/user/login.html";
        } catch (HttpClientErrorException httpClientErrorException) {
            String exceptionMessage = httpClientErrorException.getMessage();
            if(exceptionMessage.contains("400") 
                    && exceptionMessage.contains(username) && exceptionMessage.contains(newPassword)) {
                model.addAttribute("changePasswordMessage", "Mật khẩu mới không được trùng với mật khẩu hiện tại!");
                return "tco-client/user/change-password.html";
            }
            System.out.println(exceptionMessage);
        }
        return "tco-client/user/change-password.html";
    }
    
    @RequestMapping("/user/profile")
    public String getUserProfile() {
        return "tco-client/user/user-profile.html";
    }
}
