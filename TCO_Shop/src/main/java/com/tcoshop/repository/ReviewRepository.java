package com.tcoshop.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tcoshop.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{
	@Query(value = "select * from Reviews where Product_Id = ?1 \r\n"
	        + "order by Review_Time DESC", nativeQuery = true)
	List<Review> findByProductId(Integer productId);
	
	@Query(value = "select top 4 * from reviews order by Review_Time_Detail desc, Review_Time desc", nativeQuery = true)
	List<Review> findTop10Review();
}
