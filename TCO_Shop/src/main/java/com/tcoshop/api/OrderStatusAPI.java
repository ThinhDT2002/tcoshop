package com.tcoshop.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcoshop.entity.OrderStatus;
import com.tcoshop.service.OrderStatusService;

@RestController
@CrossOrigin("*")
public class OrderStatusAPI {
    @Autowired
    OrderStatusService orderStatusService;
    @GetMapping("/api/orderStatus")
    public ResponseEntity<List<OrderStatus>> getAllOrderStatus() {
        return ResponseEntity.ok(orderStatusService.getOrderStatus());
    }
}
