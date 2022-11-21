package com.tcoshop.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.tcoshop.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{

}
