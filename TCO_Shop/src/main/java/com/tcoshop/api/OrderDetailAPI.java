package com.tcoshop.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcoshop.entity.OrderDetail;
import com.tcoshop.service.OrderDetailService;

@RestController
@CrossOrigin("*")
public class OrderDetailAPI {
    @Autowired
    OrderDetailService orderDetailService;
    @GetMapping("/api/ordersDetail")
    public List<OrderDetail> getAllOrdersDetail() {
        return orderDetailService.getAllOrderDetail();
    }
    @GetMapping("/api/ordersDetail/findByOrderId")
    public List<OrderDetail> findByOrderId(@RequestParam("orderId") Integer orderId) {
        return orderDetailService.findByOrderId(orderId);
    }
}
