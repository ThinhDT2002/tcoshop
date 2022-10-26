package com.tcoshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tcoshop.entity.Subcategory;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, String>{
	@Query("Select s From Subcategory s where s.category.id=?1")
	List<Subcategory> findByCategoryId(String sid);
}
