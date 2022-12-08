package com.tcoshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcoshop.entity.ProductVariation;
import com.tcoshop.repository.ProductVariationRepository;
import com.tcoshop.service.ProductVariationService;

@Service
public class ProductVariationServiceImpl implements ProductVariationService{
    @Autowired
    ProductVariationRepository productVariationRepository;
    @Override
    public List<ProductVariation> saveAll(List<ProductVariation> productVariations) {
        return productVariationRepository.saveAll(productVariations);
    }
    @Override
    public List<ProductVariation> findByProductId(Integer productId) {
        return productVariationRepository.findByProductId(productId);
    }
    @Override
    public void deleteByProductId(Integer productId) {
        productVariationRepository.deleteByProductId(productId);
        
    }

}
