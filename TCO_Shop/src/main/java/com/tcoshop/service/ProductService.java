package com.tcoshop.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tcoshop.entity.Product;

public interface ProductService {
	List<Product> findAll();

	List<Product> findByCategoryId(String cid);

	List<Product> findBySubCategoryId(String cid);

	Product findById(Integer id);

	Product create(Product product);

	Product update(Product product);

	void delete(Integer id);

	List<Product> find8NewProducts();

	List<Product> find8HighDiscountProducts();

	List<Product> find8ProductsBestSold();

	List<Product> find4CheapProducts();

}
