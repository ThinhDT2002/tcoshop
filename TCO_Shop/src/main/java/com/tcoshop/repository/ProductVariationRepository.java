package com.tcoshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcoshop.entity.ProductVariation;

@Repository
public interface ProductVariationRepository extends JpaRepository<ProductVariation, Integer>{

}
