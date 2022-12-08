package com.tcoshop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tcoshop.entity.Subcategory;
import com.tcoshop.entity.Product;
import com.tcoshop.service.ProductService;
import com.tcoshop.service.SubcategoryService;

@Controller
public class SubcategoryController {
	@Autowired
	ProductService productService;
	@Autowired
	SubcategoryService subcategoryService;
	
	@RequestMapping("/category/subcategory/{id}")
	public String getSubcategoryByCategory(@ModelAttribute("item") Product product, Model model,
			@PathVariable("id") Optional<Integer> productId) {
	    List<Subcategory> subcategories = subcategoryService.findByCategoryId(product.getCategory().getId());	
		if(productId.get().equals(-999)) {
			product.setImage1("default-product.png");
			product.setImage2("default-product.png");
			product.setImage3("default-product.png");
			product.setImage4("default-product.png");
			model.addAttribute("sub", subcategories);
			model.addAttribute("item", product);
			return "tco-admin/product/product-add";
		} else {
			Product productImages = productService.findById(productId.get());
			product.setImage1(productImages.getImage1());
			product.setImage2(productImages.getImage2());
			product.setImage3(productImages.getImage3());
			product.setImage4(productImages.getImage4());
			model.addAttribute("sub", subcategories);
			model.addAttribute("item", product);
			return "tco-admin/product/product-add";
		}
	}
	
}
