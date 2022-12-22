package com.tcoshop.controller.client;




import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcoshop.entity.MailInformation;
import com.tcoshop.entity.User;
import com.tcoshop.util.PasswordUtil;

@Controller
public class UserController {
    @Autowired
    HttpSession session;
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
            restTemplate.postForEntity(url, entityUser, User.class);
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
    public String getUserProfile(Model model, Authentication authentication) { 	
    	String username = authentication.getName();
    	String url = "http://localhost:8080/api/user/" + username;
    	ResponseEntity<User> respEntity = restTemplate.getForEntity(url, User.class);
    	User userProfile = respEntity.getBody();
    	model.addAttribute("userProfile", userProfile);
        return "tco-client/user/user-profile.html";
    }
    
    @RequestMapping("/user/add")
    public String getAdd(Model model, Authentication authentication) {
    	
    	String username = authentication.getName();
    	String url = "http://localhost:8080/api/user/" + username;
    	ResponseEntity<User> respEntity = restTemplate.getForEntity(url, User.class);
    	User userEdit = respEntity.getBody();
    	model.addAttribute("userEdit", userEdit);
    	return "tco-client/user/user-add.html";
    }
    
    @RequestMapping("/user/update/{username}")
    public String submitUser(@Valid Model model, Authentication authentication, RedirectAttributes redirectAttributes,
    		@RequestParam("userAvatar") Optional<MultipartFile> multipartFile, @ModelAttribute("userEdit") User user, Errors errors) {
    	
    	boolean updateUserError = false;
        if(errors.hasErrors()) {
        	redirectAttributes.addFlashAttribute("updateUserMessage", "Cập nhật thông tin tài khoản thất bại!");
        	return "redirect:/user/add";
        }
        if (user.getPhone() != null) {
			if(user.getPhone().trim().length() != 0) {
				if (!user.getPhone().matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b")) {
					updateUserError = true;
					redirectAttributes.addFlashAttribute("phoneError", "Không đúng định dạng số điện thoại VN");
				}
			}
		}
        if (updateUserError == true) {
			redirectAttributes.addFlashAttribute("updateUserMessage", "Cập nhật thông tin tài khoản thất bại!");
			return "redirect:/user/add";
		}
    	String username = authentication.getName();
    	String getUrl = "http://localhost:8080/api/user/" + username;
    	ResponseEntity<User> responseEntity = restTemplate.getForEntity(getUrl, User.class);
    	User userAvatar = responseEntity.getBody();
    	user.setAvatar(userAvatar.getAvatar());
    	String putUrl = "http://localhost:8080/api/user/" + username;
    	setAvatar(user, multipartFile);
    	HttpEntity<User> httpEntity = new HttpEntity<User>(user);
    	restTemplate.put(putUrl, httpEntity);
    	redirectAttributes.addFlashAttribute("updateUserMessage","Cập nhật thông tin tài khoản thành công!");
    	redirectAttributes.addFlashAttribute("userEdit", user);
    	
    	return "redirect:/user/add";
    }
    
    
    @GetMapping("/user/forgot")
    public String forgotPassword() {
        return "tco-client/user/forgot-password.html";
    }
    
    @SuppressWarnings("null")
    @PostMapping("/user/forgot")
    public String forgotPassword2(@RequestParam("username") String username, Model model) {
        String url = "http://localhost:8080/api/user/" + username;
        try {
            ResponseEntity<User> responseEntity = restTemplate.getForEntity(url, User.class);
            User fpUser = responseEntity.getBody();
            String emailSubject = "Quên mật khẩu";
            String emailBody = "Mã xác nhận của bạn là: " + fpUser.getForgotPasswordCode();
            String to = fpUser.getEmail();
            MailInformation mailInformation = new MailInformation(to, emailSubject, emailBody);
            HttpEntity<MailInformation> mailEntity = new HttpEntity<>(mailInformation);
            String sendMailUrl = "http://localhost:8080/api/email/send";
            restTemplate.postForEntity(sendMailUrl, mailEntity, MailInformation.class);
            model.addAttribute("retrievePasswordMessage", "Mã xác nhận đã được gửi, hãy kiểm tra email của bạn");
            session.setAttribute("currentfpUser", fpUser.getUsername());
            return "tco-client/user/retrieve-password.html";
        } catch(Exception e) {
            e.printStackTrace();
            model.addAttribute("fpMessage", "Không tìm thấy tài khoản: " + username);
            return "tco-client/user/forgot-password.html";
        }
    }
    
    @GetMapping("/user/retrieve-password")
    public String retrievePassword() {
        return "tco-client/user/retrieve-password.html";
    }
    
    @SuppressWarnings("null")
    @PostMapping("/user/retrieve-password")
    public String retrievePassword2(@RequestParam("forgot-password-code") String forgotPasswordCode,
            @RequestParam("new-password") String newPassword,
            @RequestParam("confirm-new-password") String confirmNewPassword,
            Model model) {
        boolean valid = true;
        if(forgotPasswordCode.trim().length() == 0) {
            valid = false;
            model.addAttribute("errorForgotPasswordCode", "Chưa nhập mã xác nhận");
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
            model.addAttribute("retrievePasswordMessage", "Đặt lại mật khẩu thất bại!");
            return "tco-client/user/retrieve-password.html";
        }
        if(!confirmNewPassword.equals(newPassword)) {
            model.addAttribute("retrievePasswordMessage", "Xác nhận mật khẩu không trùng khớp!");
            return "tco-client/user/retrieve-password.html";
        }
        String username = (String) session.getAttribute("currentfpUser");
        String url = "http://localhost:8080/api/user/" + username;
        try {
            ResponseEntity<User> userResponseEntity = restTemplate.getForEntity(url, User.class);
            User currentfpUser = userResponseEntity.getBody();
            if(!currentfpUser.getForgotPasswordCode().equals(forgotPasswordCode)) {
                model.addAttribute("retrievePasswordMessage", "Mã xác nhận không hợp lệ!");
                return "tco-client/user/retrieve-password.html";
            }
            String retrievePasswordUrl = "http://localhost:8080/api/user/forgot-password";
            User user = new User();
            user.setUsername(currentfpUser.getUsername());
            user.setPassword(newPassword);
            HttpEntity<User> userEntity = new HttpEntity<>(user);
            restTemplate.put(retrievePasswordUrl, userEntity);
            model.addAttribute("retrievePasswordMessage", "Đặt lại mật khẩu thành công");
            return "tco-client/user/retrieve-password.html";                       
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("retrievePasswordMessage", "Đã xảy ra lỗi ngoài ý muốn!");
            return "tco-client/user/retrieve-password.html";
        }                         
    }
    
    private void setAvatar(User user, Optional<MultipartFile> multipartFile) {
    	String fileName = "user.png";
    	if(!multipartFile.get().isEmpty()) {
    		fileName = multipartFile.get().getOriginalFilename();
    	}
    	if(user.getAvatar() == null || user.getAvatar().equals("user.png")) {
    		user.setAvatar(fileName);
    	} else {
    	    if(!fileName.equals(user.getAvatar()) && !fileName.equals("user.png")) {
    	        user.setAvatar(fileName);
    	    } else {
    	        user.setAvatar(user.getAvatar());
    	    }
    		
    	}
    }
}
