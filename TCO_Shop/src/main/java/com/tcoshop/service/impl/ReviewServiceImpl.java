package com.tcoshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcoshop.entity.Review;
import com.tcoshop.repository.ReviewRepository;
import com.tcoshop.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService{
	@Autowired
	ReviewRepository reviewDao;

	@Override
	public List<Review> findAll() {
		return reviewDao.findAll();
	}
	

	@Override
	public List<Review> findTop10Review() {
		return reviewDao.findTop10Review();
	}

	@Override
	public Review findById(Integer id) {
		return reviewDao.findById(id).get();
	}

	@Override
	public List<Review> findByProductId(Integer pid) {
		return reviewDao.findByProductId(pid);
	}

	@Override
	public Review create(Review review) {
		return reviewDao.save(review);
	}

    @Override
    public Review update(Review review) {
        return reviewDao.save(review);
    }

    @Override
    public void delete(Integer reviewId) {
        reviewDao.deleteById(reviewId);      
    }
	
}
