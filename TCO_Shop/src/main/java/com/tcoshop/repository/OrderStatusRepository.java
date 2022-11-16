package com.tcoshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcoshop.entity.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, String>{

}
