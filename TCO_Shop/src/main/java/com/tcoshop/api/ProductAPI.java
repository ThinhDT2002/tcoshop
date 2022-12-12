package com.tcoshop.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcoshop.entity.Favorite;
import com.tcoshop.entity.Product;
import com.tcoshop.service.FavoriteService;
import com.tcoshop.service.ProductService;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/products")
public class ProductAPI {
	@Autowired
	ProductService productService;
	
	@Autowired
	FavoriteService favoriteService;
	
	@GetMapping()
	public List<Product> getAll() {
		return productService.findAll();
	}
	
	@GetMapping("/category/{cid}")
	public List<Product> getCategory(@PathVariable("cid") String cid) {
		return productService.findByCategoryId(cid);
	}
	
	@GetMapping("/subcategory/{scid}")
	public List<Product> getSubCategory(@PathVariable("scid") String scid) {
		return productService.findBySubCategoryId(scid);
	}
	
	
	@GetMapping("{id}")
	public Product getOne(@PathVariable("id") Integer id) {
		return productService.findById(id);
	}
	
	@PostMapping
	public Product create(@RequestBody Product product) {
		return productService.create(product);
	}
	
	@PutMapping("{id}")
	public Product update(@RequestBody Product product) {
		return productService.update(product);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		productService.delete(id);
	}
	
	@GetMapping("/newProducts")
	public List<Product> get8NewProducts() {
	    return productService.find8NewProducts();
	}
	
	@GetMapping("/highDiscountProducts")
	public List<Product> get8HighDiscountProducts() {
	    return productService.find8HighDiscountProducts();
	}
	
	@GetMapping("/bestSoldProducts")
	public List<Product> get8BestSoldProduct() {
	    return productService.find8ProductsBestSold();
	}
	
	@GetMapping("/cheapProducts")
	public List<Product> get4CheapProducts() {
	    return productService.find4CheapProducts();
	}
	
	@PostMapping("/favorite/add")
	public Favorite addFavoriteProduct(@RequestBody Favorite favorite) {
	    return favoriteService.create(favorite);
	}
	@GetMapping("/favorites")
	public List<Favorite> findFavoritesByUsername(@RequestParam("username") String username) {
	    return favoriteService.findByUsername(username);
	}
	@DeleteMapping("/favorite/remove/{favoriteId}")
	public void deleteFavoriteById(@PathVariable("favoriteId") Integer favoriteId) {
	    favoriteService.delete(favoriteId);
	}
}
