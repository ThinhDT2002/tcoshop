package com.tcoshop.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcoshop.entity.Review;
import com.tcoshop.service.ReviewService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/reviews")
public class ReviewAPI {
	@Autowired
	ReviewService reviewService;
	
	@GetMapping()
	public ResponseEntity<List<Review>> getReview(){
		return ResponseEntity.ok(reviewService.findAll());
	}
	
	
}
