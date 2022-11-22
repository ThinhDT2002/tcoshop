package com.tcoshop.controller.admin;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.tcoshop.entity.Review;
import com.tcoshop.service.ReviewService;

@Controller
public class ReviewManagementController {
	@Autowired
	ReviewService reviewService;
	
	
	@RequestMapping("/tco-admin/review")
	public String getReview(Model model,
			 Review review) {

			List<Review> list = reviewService.findAll();
			model.addAttribute("revs", list);
	
		
		return "tco-admin/review/review-list.html";
	}
}
