package com.tcoshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReviewManagementController {
	@RequestMapping("/tco-admin/review")
	public String getReview() {
		return "tco-admin/review/review-list.html";
	}
}
