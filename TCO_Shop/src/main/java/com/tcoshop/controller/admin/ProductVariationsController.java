package com.tcoshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductVariationsController {
    @GetMapping("/tco-admin/product/variations")
    public String getProductVariationManagementPage() {
        return "/tco-admin/product/product-variations-management.html";
    }
}
