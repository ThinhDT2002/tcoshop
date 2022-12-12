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
    List<Product> findBySubcategoryId2(String scid);

    @Query("Select p From Product p where p.subcategory.id=?1")
    Page<Product> findBySubcategoryId(String scid, Pageable pageable);

    @Query(value = "select sum(Products.stock) from Products", nativeQuery = true)
    Integer getAllProductsCount();
    
    @Query(value = "select * from Products \r\n"
            + "where Products.Id not in (\r\n"
            + "select Orders_Detail.Product_Id from Orders \r\n"
            + "join Orders_Detail on Orders.Id = Orders_Detail.Order_Id\r\n"
            + "where Month(Orders.Create_Date) = ?1 and Year(Orders.Create_Date) = ?2\r\n"
            + ")", nativeQuery = true)
    List<Product> findProductNotSoldInMonth(int month, int year);
    
    @Query(value = "select * from Products\r\n"
            + "where Id in\r\n"
            + "(\r\n"
            + "select top 3 Products.Id as 'Id'\r\n"
            + "from Orders_Detail\r\n"
            + "join Products on Products.Id = Orders_Detail.Order_Id\r\n"
            + "group by Products.Id order by COUNT(Product_Id) desc\r\n"
            + ")", nativeQuery = true)
    List<Product> findProductBestSold();
    @Query(value = "select top 8 * from Products order by Id desc", nativeQuery = true)
    List<Product> find8NewProducts();
    @Query(value = "select top 8 * from Products order by discount desc", nativeQuery = true)
    List<Product> find8HighDiscountProducts();
    @Query(value = "select * from Products\r\n"
            + "where Id in\r\n"
            + "(\r\n"
            + "select top 8 Products.Id as 'Id'\r\n"
            + "from Orders_Detail\r\n"
            + "join Products on Products.Id = Orders_Detail.Order_Id\r\n"
            + "group by Products.Id order by COUNT(Product_Id) desc\r\n"
            + ")", nativeQuery = true)
    List<Product> find8ProductsBestSold();
    @Query(value = "select top 4 * from Products order by Price asc", nativeQuery = true)
    List<Product> find4CheapProducts();
}
