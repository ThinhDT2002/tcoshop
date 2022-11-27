package com.tcoshop.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcoshop.entity.Review;
import com.tcoshop.service.ReviewService;

@Controller
public class ReviewController {
	@Autowired
	ReviewService reviewService;
	
	RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping("/review/add")
	public String getReview(Model model, @ModelAttribute("reviewForm") Review review) {
		review = new Review();
		model.addAttribute("reviewForm", review);
		return "tco-client/shop/product-gallery-full-width";
	}
	
	@RequestMapping("/review/submit")
	public String addReview(Model model, RedirectAttributes redirectAttributes,
			@ModelAttribute("reviews") Review review) {
		
		String getUrl = "http://localhost:8080/api/review/" + review.getId();
		HttpEntity<Review> httpEntity = new HttpEntity<>(review);
		ResponseEntity<Review> reviewEntity = restTemplate.postForEntity(getUrl, httpEntity, Review.class);
		review = reviewEntity.getBody();
		redirectAttributes.addFlashAttribute("message", "Thêm review thành công!");
		redirectAttributes.addFlashAttribute("reviewForm", review);
		return "redirect:/tco-client/shop/product-gallery-full-width";
	}
}
