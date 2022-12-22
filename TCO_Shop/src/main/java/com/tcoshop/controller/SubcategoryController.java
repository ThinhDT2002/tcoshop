package com.tcoshop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.tcoshop.entity.Product;
import com.tcoshop.entity.ProductVariation;
import com.tcoshop.entity.Subcategory;
import com.tcoshop.entity.Variation;
import com.tcoshop.service.ProductService;
import com.tcoshop.service.SubcategoryService;

@Controller
public class SubcategoryController {
	@Autowired
	ProductService productService;
	@Autowired
	SubcategoryService subcategoryService;
	private RestTemplate restTemplate = new RestTemplate();
	@SuppressWarnings("null")
    @RequestMapping("/category/subcategory/{id}")
	public String getSubcategoryByCategory(@ModelAttribute("item") Product product, Model model,
			@PathVariable("id") Optional<Integer> productId) {
	    List<Subcategory> subcategories = subcategoryService.findByCategoryId(product.getCategory().getId());	
		if(productId.get().equals(-999)) {
			product.setImage1("default-product.png");
			product.setImage2("default-product.png");
			product.setImage3("default-product.png");
			product.setImage4("default-product.png");
			Variation[] variations = restTemplate.getForObject("http://localhost:8080/api/variation/all",
	                Variation[].class);
	        List<ProductVariation> productVariations = new ArrayList<>();
	        for (int i = 0; i < variations.length; i++) {
	            ProductVariation productVariation = new ProductVariation();
	            productVariation.setName(variations[i].getName());
	            productVariation.setValue(" ");
	            productVariations.add(productVariation);
	        }
	        product.setProductVariations(productVariations);
	        model.addAttribute("variations", variations);
			model.addAttribute("sub", subcategories);
			model.addAttribute("item", product);
			return "tco-admin/product/product-add";
		} else {
			Product productImages = productService.findById(productId.get());
			product.setImage1(productImages.getImage1());
			product.setImage2(productImages.getImage2());
			product.setImage3(productImages.getImage3());
			product.setImage4(productImages.getImage4());
			Variation[] variations = restTemplate.getForObject("http://localhost:8080/api/variation/all",
	                Variation[].class);
			
			if(!productImages.getProductVariations().isEmpty()) {
			    List<ProductVariation> productVariations = productImages.getProductVariations();
	            product.setProductVariations(productVariations);
			} else {
			    List<ProductVariation> productVariations = new ArrayList<>();
	            for (int i = 0; i < variations.length; i++) {
	                ProductVariation productVariation = new ProductVariation();
	                productVariation.setName(variations[i].getName());
	                productVariation.setValue(" ");
	                productVariations.add(productVariation);
	            }
	            product.setProductVariations(productVariations);
			}
	     
	        model.addAttribute("variations", variations);
			model.addAttribute("sub", subcategories);
			model.addAttribute("item", product);
			return "tco-admin/product/product-add";
		}
	}
	
}
