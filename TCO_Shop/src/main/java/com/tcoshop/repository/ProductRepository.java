package com.tcoshop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tcoshop.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
//	@Query("Select p From Product p where p.category.id=?1")
//	Page<Product> findByCategoryId(String cid, Pageable pageable);

    Page<Product> findAllByCategoryIdLike(String cid, Pageable pageable);

    @Query("Select p From Product p where p.category.id=?1")
    List<Product> findByCategoryId(String cid);

    @Query("Select p From Product p where p.subcategory.id=?1")
    Page<Product> findBySubcategoryId(String scid, Pageable pageable);
}
