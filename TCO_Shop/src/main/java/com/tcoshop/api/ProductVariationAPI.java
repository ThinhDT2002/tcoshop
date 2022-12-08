package com.tcoshop.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tcoshop.entity.ProductVariation;
import com.tcoshop.service.ProductVariationService;

@RestController
@CrossOrigin("*")
public class ProductVariationAPI {
    @Autowired
    ProductVariationService productVariationService;
    
    @PostMapping("/api/productVariation/all")
    public ResponseEntity<List<ProductVariation>> saveAll(@RequestBody List<ProductVariation> productVariations) {
        return ResponseEntity.ok(productVariationService.saveAll(productVariations));
    }
    
    @GetMapping("/api/productVariation/{productId}")
    public ResponseEntity<List<ProductVariation>> findByProductId(@PathVariable("productId") Integer productId) {
        return ResponseEntity.ok(productVariationService.findByProductId(productId));
    }
    
    @DeleteMapping("/api/productVariation/{productId}")
    public void deleteByProductId(@PathVariable("productId") Integer productId) {
        productVariationService.deleteByProductId(productId);
    }
}
