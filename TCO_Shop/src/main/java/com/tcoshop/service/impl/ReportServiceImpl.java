package com.tcoshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcoshop.repository.OrderRepository;
import com.tcoshop.repository.ProductRepository;
import com.tcoshop.repository.UserRepository;
import com.tcoshop.service.ReportService;
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Override
    public Double getTurnover(String status) {
        return orderRepository.getTurnover(status);
    }
    @Override
    public Integer getUserCount(String roleId) {
        return userRepository.getUserCount(roleId);
    }

    @Override
    public Integer getAllProductCount() {
        return productRepository.getAllProductsCount();
    }

    @Override
    public Integer getAllOrderCount(String status) {
        return orderRepository.getAllOrderCount(status);
    }
    @Override
    public Integer getSalesReport(Integer year, Integer monthFrom, Integer monthTo) {
        return orderRepository.getSalesReport(year, monthFrom, monthTo);
    }
    @Override
    public Integer getOrderCountPerStatus(String status, Integer year, Integer monthFrom, Integer monthTo) {
        return orderRepository.getOrderCountPerStatus(status, year, monthFrom, monthTo);
    }
    @Override
    public Double getTurnoverPerYear(Integer year, Integer monthFrom, Integer monthTo) {
        Double turnover = orderRepository.getTurnoverPerYear(year, monthFrom, monthTo);
        if(turnover == null) {
            return 0.0;
        }
        return turnover;
    }
}
