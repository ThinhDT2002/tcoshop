package com.tcoshop.service;

import java.util.List;

import com.tcoshop.entity.OrderDetail;

public interface OrderDetailService {
    List<OrderDetail> getAllOrderDetail();
    List<OrderDetail> findByOrderId(Integer orderId);
    List<OrderDetail> saveAll(List<OrderDetail> orderDetails);
}
