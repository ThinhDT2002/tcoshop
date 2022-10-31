package com.tcoshop.controller.admin;

import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.tcoshop.entity.User;

@Controller
public class UserManagementController {
    RestTemplate restTemplate = new RestTemplate();
    private String apiUrl = "http://localhost:8080/api/user";

    @RequestMapping("/tco-admin/user/list/grid")
    public String getUsergrid(Model model) {
        User[] users = restTemplate.getForObject(apiUrl, User[].class);
        model.addAttribute("users", users);
        return "tco-admin/user/user-card.html";
    }

    @RequestMapping("/tco-admin/user/list")
    public String getUserList() {
        return "tco-admin/user/user-list.html";
    }

    @RequestMapping("/tco-admin/user/add")
    public String getUserAdd(@ModelAttribute("user") User user) {
        user = new User();
        return "tco-admin/user/add-user.html";
    }

    @PostMapping("/tco-admin/user/add")
    public String addUser(@ModelAttribute("user") User user,
            @RequestParam("userAvatar") Optional<MultipartFile> multipartFile, Model model) {
        setAvatar(user, multipartFile);
        String url = "http://localhost:8080/api/user";
        HttpEntity<User> userEntity = new HttpEntity<>(user);
        ResponseEntity<User> responseEntity = restTemplate.postForEntity(url, userEntity, User.class);
        model.addAttribute("addUserMessage", "Thêm người dùng thành công!");
        return "tco-admin/user/add-user.html";
    }

    @RequestMapping("/tco-admin/user/{username}")
    public String getUserProfile(@PathVariable("username") String username, @ModelAttribute("user") User user,
            Model model) {
        String url = "http://localhost:8080/api/user/" + username;
        user = restTemplate.getForObject(url, User.class);
        model.addAttribute("user", user);
        return "tco-admin/user/user-profile.html";
    }

    private void setAvatar(User user, Optional<MultipartFile> multipartFile) {
        String fileName = "user.png";
        if(!multipartFile.get().isEmpty()) {
			fileName = multipartFile.get().getOriginalFilename();
        }
        if(user.getAvatar() == null || user.getAvatar().equals("user.png")) {
            user.setAvatar(fileName);
        } else {
            user.setAvatar(user.getAvatar());
        }
    }
}
