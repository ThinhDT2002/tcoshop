package com.tcoshop.service;

import java.util.List;

import com.tcoshop.entity.ProductVariation;

public interface ProductVariationService {
    List<ProductVariation> saveAll(List<ProductVariation> productVariations);
    List<ProductVariation> findByProductId(Integer productId);
    void deleteByProductId(Integer productId);
}
