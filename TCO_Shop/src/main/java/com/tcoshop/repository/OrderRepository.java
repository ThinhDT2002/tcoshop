package com.tcoshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcoshop.entity.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

}
