package com.tcoshop.controller.admin;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		user.setAvatar("user.png");
		return "tco-admin/user/add-user.html";
	}

	@PostMapping("/tco-admin/user/add")
	public String addUser(@Valid @ModelAttribute("user") User user, Errors errors,
			@RequestParam("userAvatar") Optional<MultipartFile> multipartFile, Model model,
			@RequestParam("confirmPassword") String confirmPassword) {
		boolean addUserError = false;
		if (errors.hasErrors()) {
			model.addAttribute("addUserMessage", "Thêm người dùng thất bại!");
			return "tco-admin/user/add-user.html";
		}

		if (user.getEmail().trim().length() == 0) {
			addUserError = true;
			model.addAttribute("emailError", "Email không được bỏ trống");
		} else if (user.getEmail().trim().contains(" ")) {
			addUserError = true;
			model.addAttribute("emailError", "Email không chứa kí tự khoảng trắng");
		} else if (!user.getEmail().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
				+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
			addUserError = true;
			model.addAttribute("emailError", "Không đúng định dạng Email");
		}

		if (user.getPassword().contains(" ")) {
			addUserError = true;
			model.addAttribute("passwordError", "Mật khẩu không được chứa khoảng trắng!");
		}

		if (confirmPassword.trim().length() == 0) {
			addUserError = true;
			model.addAttribute("confirmPasswordError", "Hãy xác nhận lại mật khẩu");
		} else if (!confirmPassword.equals(user.getPassword())) {
			addUserError = true;
			model.addAttribute("confirmPasswordError", "Xác nhận mật khẩu không chính xác");
		}

		if (addUserError == true) {
			model.addAttribute("addUserMessage", "Thêm người dùng thất bại!");
			return "tco-admin/user/add-user.html";
		}
		user.setCreateDate(new Date());
		setAvatar(user, multipartFile);
		String url = "http://localhost:8080/api/user";
		HttpEntity<User> userEntity = new HttpEntity<>(user);
		try {
			ResponseEntity<User> responseEntity = restTemplate.postForEntity(url, userEntity, User.class);
			model.addAttribute("addUserMessage", "Thêm người dùng thành công!");
		} catch (HttpClientErrorException httpClientErrorException) {
			String exceptionMessage = httpClientErrorException.getMessage();
			System.out.println(exceptionMessage);
			if (exceptionMessage.equals("400 : [no body]")) {
				model.addAttribute("addUserMessage", "Tên tài khoản đã được sử dụng");
			}
		}
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

	@RequestMapping("/tco-admin/userEdit/{username}")
	public String getUserEdit(@PathVariable("username") String username, @ModelAttribute("user") User user,
			Model model) {
		String url = "http://localhost:8080/api/user/" + username;
		user = restTemplate.getForObject(url, User.class);
		model.addAttribute("user", user);
		return "tco-admin/user/user-edit.html";
	}

	@RequestMapping("/tco-admin/userProfile")
	public String getUserProfile2(Model model, Authentication auth) {
		String username = auth.getName();
		String url = "http://localhost:8080/api/user/" + username;
		ResponseEntity<User> respEntity = restTemplate.getForEntity(url, User.class);
		User userProfile = respEntity.getBody();
		model.addAttribute("user", userProfile);
		return "tco-admin/user/user-profile.html";
	}

	@RequestMapping("/tco-admin/userUpdate/{username}")
	public String updateUser(@Valid RedirectAttributes redirectAttributes,
			@RequestParam("userAvatar") Optional<MultipartFile> multipartFile, @ModelAttribute("user") User user,
			Errors errors, Model model) {

		boolean updateUserError = false;
		if (errors.hasErrors()) {
			model.addAttribute("updateUserMessage", "Cập nhật thông tin người dùng thất bại!");
			return "tco-admin/user/user-edit.html";
		}
		if (user.getPhone() != null) {
			if(user.getPhone().trim().length() != 0) {
				if (!user.getPhone().matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b")) {
					updateUserError = true;
					model.addAttribute("phoneError", "Không đúng định dạng số điện thoại VN");
				}
			}
		}
		if (updateUserError == true) {
			model.addAttribute("updateUserMessage", "Cập nhật thông tin người dùng thất bại!");
			return "tco-admin/user/user-edit.html";
		}

		String getUrl = "http://localhost:8080/api/user/" + user.getUsername();
		ResponseEntity<User> responseEntity = restTemplate.getForEntity(getUrl, User.class);
		User userIcon = responseEntity.getBody();
		user.setAvatar(userIcon.getAvatar());

		String putUrl = "http://localhost:8080/api/user/" + user.getUsername();
		setAvatar(user, multipartFile);
		HttpEntity<User> httpEntity = new HttpEntity<User>(user);
		restTemplate.put(putUrl, httpEntity);
		redirectAttributes.addFlashAttribute("updateUserMessage", "Cập nhật thông tin người dùng thành công!");
		redirectAttributes.addFlashAttribute("user", user);
		return "redirect:/tco-admin/userEdit/" + user.getUsername();
	}

	@RequestMapping("/tco-admin/userProfileUpdate/{username}")
	public String updateUserProfile(@Valid RedirectAttributes redirectAttributes,
			 @RequestParam("userAvatar") Optional<MultipartFile> multipartFile, @ModelAttribute("user") User user,
			Errors errors, Model model) {
		boolean updateProfileError = false;
		if (errors.hasErrors()) {
			redirectAttributes.addFlashAttribute("updateProfileMessage", "Cập nhật thông tin người dùng thất bại!");
			return "redirect:/tco-admin/user/" + user.getUsername();
		}
		if(user.getPhone() != null) {
			if (user.getPhone().trim().length() != 0) {
				if (!user.getPhone().matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b")) {
					updateProfileError = true;
					redirectAttributes.addFlashAttribute("phoneError", "Không đúng định dạng số điện thoại VN");
				}
			}
		}
		if(updateProfileError == true) {
			redirectAttributes.addFlashAttribute("updateProfileMessage", "Cập nhật thông tin người dùng thất bại!");
			return "redirect:/tco-admin/user/" + user.getUsername();
		}
		String getUrl = "http://localhost:8080/api/user/" + user.getUsername();
		ResponseEntity<User> responseEntity = restTemplate.getForEntity(getUrl, User.class);
		User userIcon = responseEntity.getBody();
		user.setAvatar(userIcon.getAvatar());

		String putUrl = "http://localhost:8080/api/user/" + user.getUsername();
		setAvatar(user, multipartFile);
		HttpEntity<User> httpEntity = new HttpEntity<User>(user);
		restTemplate.put(putUrl, httpEntity);
		redirectAttributes.addFlashAttribute("updateProfileMessage", "Cập nhật thông tin người dùng thành công!");
		redirectAttributes.addFlashAttribute("user", user);
		return "redirect:/tco-admin/user/" + user.getUsername();
	}

	private void setAvatar(User user, Optional<MultipartFile> multipartFile) {
		String fileName = "user.png";
		if (!multipartFile.get().isEmpty()) {
			fileName = multipartFile.get().getOriginalFilename();
		}
		if (user.getAvatar() == null || user.getAvatar().equals("user.png")) {
			user.setAvatar(fileName);
		} else {
			if (!fileName.equals(user.getAvatar()) && !fileName.equals("user.png")) {
				user.setAvatar(fileName);
			} else {
				user.setAvatar(user.getAvatar());
			}
		}

	}
}
