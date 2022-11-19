package com.tcoshop.controller.client;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.tcoshop.entity.Product;
import com.tcoshop.entity.Review;
import com.tcoshop.entity.Subcategory;
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

    void page(Model model, Page<Product> list, @PathVariable("pageNumber") int currentPage) {
        int totalPages = list.getTotalPages();
        long totalItems = list.getTotalElements();
        List<Product> products = list.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("countries", products);
    }
    
    @GetMapping("/product/list")
    public String getAllPages(Model model, @RequestParam("cid") Optional<String> cid,
            @RequestParam("scid") Optional<String> scid) {
        return list(model, cid, scid, 1);
    }

    // phân trang cho product list
    @RequestMapping("/product/list/page/{pageNumber}")
    public String list(Model model, @RequestParam("cid") Optional<String> cid,
            @RequestParam("scid") Optional<String> scid, @PathVariable("pageNumber") int currentPage) {
        if (cid.isPresent()) {
            Page<Product> list = productService.findByCategoryId(cid.get(), currentPage);
            model.addAttribute("items", list);
            model.addAttribute("cid", cid.get());
            page(model, list, currentPage);
        }else if (scid.isPresent()) {
            Page<Product> list = productService.findBySubcategoryId(scid.get(), currentPage);
            model.addAttribute("items", list);
            model.addAttribute("scid", scid.get());
            page(model, list, currentPage); 
        } else {
            Page<Product> list = productService.findAll(currentPage);
            model.addAttribute("items", list);
            page(model, list, currentPage);
        }
        return "tco-client/shop/shop-banner-full-width";
    }

    // trang product detail
    @RequestMapping("/product/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Product item = productService.findById(id);      
        model.addAttribute("item", item);        
        return "tco-client/shop/product-gallery-full-width";
    }
}
