package com.tcoshop.service;

import java.util.List;

import com.tcoshop.entity.Review;

public interface ReviewService {
	List<Review> findAll();
	
	Review findById(Integer id);
	
	List<Review> findByProductId(Integer pid);

	Review create(Review review);
	
	Review update(Review review);
}
