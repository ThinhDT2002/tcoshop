package com.tcoshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tcoshop.entity.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
	@Query("SELECT o FROM Order o WHERE o.user.username=?1")
	List<Order> findByUsername(String username);

	@Query(value = "select od.Price from Orders o join Orders_Detail od on o.Id = od.Order_Id where o.Status =?1",
	 nativeQuery = true)
	Double getTurnover(String status);

	@Query(value = "select count(Orders.Id) from Orders where Orders.Status != ?1", nativeQuery = true)
	Integer getAllOrderCount(String status);
}
