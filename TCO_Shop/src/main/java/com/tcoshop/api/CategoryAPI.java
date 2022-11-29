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

import org.springframework.web.bind.annotation.RestController;

import com.tcoshop.entity.Category;
import com.tcoshop.entity.Subcategory;
import com.tcoshop.service.CategoryService;

@RestController
@CrossOrigin("*")

public class CategoryAPI {
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/api/category")
	public ResponseEntity<List<Category>> getCategory(){
		return ResponseEntity.ok(categoryService.findAll());
	}
	
	@GetMapping("/api/categories")
	public List<Category> getAll(){
	    return categoryService.findAll();
	}
	
	@GetMapping("/api/categories/{id}")
	public Category getOne(@PathVariable("id") String id) {
		return categoryService.findById(id);
	}
	
	@PostMapping("/api/categories")
	public Category create(@RequestBody Category category) {
		return categoryService.create(category);
	}
	
	@PutMapping("/api/categories/{id}")
	public Category update(@RequestBody Category category) {
		return categoryService.update(category);
	}
	
	@DeleteMapping("/api/categories/{id}")
	public void delete(@PathVariable("id") String id) {
		categoryService.delete(id);
	}
}
