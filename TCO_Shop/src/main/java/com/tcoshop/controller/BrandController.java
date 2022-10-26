package com.tcoshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BrandController {
	@RequestMapping("/tco-admin/brand")
	public String getBrand() {
		return "tco-admin/brand/brand-list.html";
	}
}
