package com.tcoshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tcoshop.entity.Subcategory;
import com.tcoshop.service.SubcategoryService;

@Controller
public class SubcategoryManagementController {
	@Autowired
	SubcategoryService subcategoryService;
	
	@RequestMapping("/tco-admin/sub_category")
	public String getSubcategory(Model model,
		 @RequestParam("cid") Optional<String> cid, Subcategory subcategory) {
		
		if(cid.isPresent()) {
			List<Subcategory> list = subcategoryService.findByCategoryId(cid.get());
			model.addAttribute("subs", list);
		}else {
			
			List<Subcategory> list = subcategoryService.findAll();
			model.addAttribute("subs",list);
		}
		return "tco-admin/category/sub-category.html";
	}
	
	
	
}
