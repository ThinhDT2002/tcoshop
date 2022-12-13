package com.tcoshop.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcoshop.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{
    @Query(value = "select * From Orders_Detail where Orders_Detail.Order_Id in\r\n"
            + "(select top 5 Orders.Id from Orders \r\n"
            + "order by Create_Date DESC)", nativeQuery = true)
    List<OrderDetail> findTop5OrderDetail();
    
    @Query("select od From OrderDetail od where od.order.id=?1")
    List<OrderDetail> findByOrderId(Integer orderId);
}
