package com.tcoshop.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tcoshop.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{
	@Query("Select r From Review r where r.product.id=?1")
	List<Review> findByProductId(Integer pid);
}
