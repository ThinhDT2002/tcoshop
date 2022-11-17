package com.tcoshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcoshop.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{

}
