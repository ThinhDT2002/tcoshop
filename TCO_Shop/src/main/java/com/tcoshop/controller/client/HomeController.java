package com.tcoshop.controller.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tcoshop.entity.Product;
import com.tcoshop.service.ProductService;

@Controller
public class HomeController {
	@Autowired
	ProductService productService;

	@RequestMapping("/home")
	public String getAll(Model model) {
		return page(model, 1);
	}

	@RequestMapping("/home/{pageNumber}")
	public String page(Model model, @PathVariable("pageNumber") int currentPage) {
		Page<Product> page = productService.findAll(currentPage);
		int totalPages = page.getTotalPages();
//		long totalItems = page.getTotalElements();
		List<Product> list = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("items", list);

		return "tco-client/layout-home/home.html";
	}
}
