package com.tcoshop.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.tcoshop.entity.Order;
import com.tcoshop.entity.OrderDetail;
import com.tcoshop.entity.Product;
import com.tcoshop.repository.ProductRepository;
import com.tcoshop.service.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/orders")
public class OrderAPI {
    @Autowired
    OrderService orderService;

    @Autowired
    ProductRepository productRepository;

    @PostMapping()
    public Order create(@RequestBody JsonNode orderData) {
        return orderService.create(orderData);
    }

    @GetMapping()
    public List<Order> getAll() {
        return orderService.findAll();
    }

    @GetMapping("/id/{id}")
    public Order getOrderById(@PathVariable("id") Integer id) {
        return orderService.findById(id);
    }

    @GetMapping("/{username}")
    public List<Order> getByUsername(@PathVariable("username") String username) {
        return orderService.findByUsername(username);
    }

    @PutMapping("/{id}")
    public void updateOrderStatus(@RequestBody Order order, @PathVariable("id") Integer orderId) {
        Order orderInDtb = orderService.findById(orderId);
        if (order.getStatus().equalsIgnoreCase("DaGiaoHang")) {
            orderInDtb.setIsPaid(2);
            orderInDtb.setStatus("DaGiaoHang");
        } else {
            orderInDtb.setIsPaid(order.getIsPaid());
            orderInDtb.setStatus(order.getStatus());
        }
        orderService.update(orderInDtb);
    }

    @PutMapping("/adminCancel/{id}")
    public void adminCancelOrder(@RequestBody Order order, @PathVariable("id") Integer orderId) {
        Order orderInDtb = orderService.findById(orderId);
        orderInDtb.setStatus("HuyBo");
        if (orderInDtb.getIsPaid() == 2) {
            orderInDtb.setIsPaid(3);
        }

        List<OrderDetail> orderDetails = orderInDtb.getOrderDetails();
        for (OrderDetail orderDetail : orderDetails) {
            int productId = orderDetail.getProduct().getId();
            Product product = productRepository.findById(productId).get();
            int remainStock = product.getStock() + orderDetail.getQuantity();
            product.setStock(remainStock);
            productRepository.save(product);
        }

        orderService.update(orderInDtb);
    }

    @PutMapping("/cancel/{id}")
    public void cancelOrder(@PathVariable("id") Integer orderId) {
        Order orderInDtb = orderService.findById(orderId);
        orderInDtb.setStatus("HuyBo");
        if (orderInDtb.getIsPaid() == 2)
            orderInDtb.setIsPaid(3);

        List<OrderDetail> orderDetails = orderInDtb.getOrderDetails();
        for (OrderDetail orderDetail : orderDetails) {
            int productId = orderDetail.getProduct().getId();
            Product product = productRepository.findById(productId).get();
            int remainStock = product.getStock() + orderDetail.getQuantity();
            product.setStock(remainStock);
            productRepository.save(product);
        }

        orderService.update(orderInDtb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id) {
        orderService.delete(id);
    }
}
