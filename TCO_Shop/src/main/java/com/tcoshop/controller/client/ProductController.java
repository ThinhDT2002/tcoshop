package com.tcoshop.controller.client;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.data.domain.Page;

import com.tcoshop.entity.Product;
import com.tcoshop.entity.ProductVariation;
import com.tcoshop.service.ProductService;
import com.tcoshop.service.ReviewService;
import com.tcoshop.service.SubcategoryService;

@Controller
public class ProductController {
	@Autowired
	ProductService productService;

	@Autowired
	SubcategoryService subcategoryService;

	@Autowired
	ReviewService reviewService;

	private RestTemplate restTemplatet = new RestTemplate();

	@RequestMapping("/product/list")
	public String list() {
		return "tco-client/shop/product-list";
	}

	// trang product detail
	@RequestMapping("/product/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, @RequestParam("pid") Optional<Integer> pid) {
		Product item = productService.findById(id);
		String url = "http://localhost:8080/api/productVariation/" + item.getId();
		ProductVariation[] productVariations = restTemplatet.getForObject(url, ProductVariation[].class);
		List<ProductVariation> productVariationsList = Arrays.asList(productVariations);
		item.setProductVariations(productVariationsList);
		model.addAttribute("item", item);

		return "tco-client/shop/product-gallery-full-width";
	}

	@RequestMapping("/product/favorites")
	public String getFavoriteProducts() {
		return "tco-client/shop/favorite-product";
	}
}
