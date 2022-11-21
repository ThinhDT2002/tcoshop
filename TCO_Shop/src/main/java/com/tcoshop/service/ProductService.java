package com.tcoshop.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tcoshop.entity.Product;

public interface ProductService {
	
	List<Product> findAll();
	
	Page<Product> findAll(int currentPage);
	
	List<Product> findByCategoryId(String cid);
	
	Page<Product> findBySubcategoryId(String scid, int pageNumber);

	Product findById(Integer id);

	Page<Product> findByCategoryId(String cid, int pageNumber);
	
	Page<Product> findProductPage(int pageNumber);
	

	Product create(Product product);

	Product update(Product product);

	void delete(Integer id);
}
