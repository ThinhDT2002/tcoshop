package com.tcoshop.service;

import java.util.List;

import com.tcoshop.entity.Category;

public interface CategoryService {
	List<Category> findAll();
	Category findById(String categoryId);
	
	Category create(Category category);
	
	Category update(Category category);
	
	void delete(String id);
}
