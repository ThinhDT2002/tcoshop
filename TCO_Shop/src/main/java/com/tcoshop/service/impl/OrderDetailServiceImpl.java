package com.tcoshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcoshop.entity.OrderDetail;
import com.tcoshop.repository.OrderDetailRepository;
import com.tcoshop.service.OrderDetailService;
@Service
public class OrderDetailServiceImpl implements OrderDetailService{
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Override
    public List<OrderDetail> getAllOrderDetail() {
        return orderDetailRepository.findAll();
    }
    @Override
    public List<OrderDetail> findByOrderId(Integer orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
    @Override
    public List<OrderDetail> saveAll(List<OrderDetail> orderDetails) {
        return orderDetailRepository.saveAll(orderDetails);
    }
}
