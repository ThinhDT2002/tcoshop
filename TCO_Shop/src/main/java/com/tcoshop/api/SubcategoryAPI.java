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

import com.tcoshop.entity.Subcategory;
import com.tcoshop.service.SubcategoryService;

@RestController
@CrossOrigin("*")
public class SubcategoryAPI {
	@Autowired
	SubcategoryService subcategoryService;
	
	@GetMapping("/api/subcategory")
	public ResponseEntity<List<Subcategory>> getSubcategory(){
		return ResponseEntity.ok(subcategoryService.findAll());
	}
	
	@GetMapping("/api/subcategory/{id}")
	public Subcategory getOne(@PathVariable("id") String id) {
		return subcategoryService.findById(id);
	}
	
	@PostMapping("/api/subcategory")
	public Subcategory create(@RequestBody Subcategory subcategory) {
		return subcategoryService.create(subcategory);
	}
	
	@PutMapping("/api/subcategory/{id}") //Put trả về resp nhưng không dùng
	public Subcategory update(@PathVariable("id") String id,
			@RequestBody Subcategory subcategory) {
		return subcategoryService.update(subcategory);
	}
	
	@DeleteMapping("/api/subcategory/{id}") // Void không trả về dữ liệu
	public void delete(@PathVariable("id") String id) {
		subcategoryService.delete(id);
	}
	
}
