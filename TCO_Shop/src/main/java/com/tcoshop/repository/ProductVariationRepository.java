package com.tcoshop.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tcoshop.entity.ProductVariation;

@Repository
public interface ProductVariationRepository extends JpaRepository<ProductVariation, Integer>{
    @Query("Select pv From ProductVariation pv where pv.product.id=?1")
    List<ProductVariation> findByProductId(Integer productId);
    @Transactional
    @Modifying
    @Query(value = "delete from Products_Variations where Product_Id = ?1", nativeQuery = true)
    void deleteByProductId(Integer productId);
}
