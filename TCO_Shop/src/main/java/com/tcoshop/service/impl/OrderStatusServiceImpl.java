package com.tcoshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcoshop.entity.OrderStatus;
import com.tcoshop.repository.OrderStatusRepository;
import com.tcoshop.service.OrderStatusService;
@Service
public class OrderStatusServiceImpl implements OrderStatusService{
    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Override
    public List<OrderStatus> getOrderStatus() {
       return orderStatusRepository.findAll();
    }
    
}
