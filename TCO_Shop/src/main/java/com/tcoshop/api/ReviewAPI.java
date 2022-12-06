package com.tcoshop.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/top10")
	public ResponseEntity<List<Review>> get10Review(){
		return ResponseEntity.ok(reviewService.findTop10Review());
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable("productId") Integer productId) {
	    return ResponseEntity.ok(reviewService.findByProductId(productId));
	}
	
	@PostMapping
	public Review create(@RequestBody Review review) {
		reviewService.create(review);
		return review;
	}
	
	@PutMapping
	public Review update(@RequestBody Review review) {
	    Review reviewInDB = reviewService.findById(review.getId());
	    reviewInDB.setContent(review.getContent());
	    reviewInDB.setTime(review.getTime());
	    reviewService.update(reviewInDB);
	    return review;
	}
	
	@DeleteMapping
	public void delete(@RequestParam("reviewId") Integer id) {
	    reviewService.delete(id);
	}
	
	
}
