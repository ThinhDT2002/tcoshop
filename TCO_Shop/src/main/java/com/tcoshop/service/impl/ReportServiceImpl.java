package com.tcoshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcoshop.entity.OrderStatusReport;
import com.tcoshop.entity.SaleReport;
import com.tcoshop.entity.TurnoverReport;
import com.tcoshop.repository.OrderRepository;
import com.tcoshop.repository.ProductRepository;
import com.tcoshop.repository.ReportRepository;
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
    @Autowired
    ReportRepository reportRepository;
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
        Integer salesReport = orderRepository.getSalesReport(year, monthFrom, monthTo);
        if(salesReport == null) {
            return 0;
        }
        return salesReport;
    }
    @Override
    public Integer getOrderCountPerStatus(String status, Integer year, Integer monthFrom, Integer monthTo) {
        Integer orderCount = orderRepository.getOrderCountPerStatus(status, year, monthFrom, monthTo);
        if(orderCount == null) {
            return 0;
        }
        return orderCount;
    }
    @Override
    public Double getTurnoverPerYear(Integer year, Integer monthFrom, Integer monthTo) {
        Double turnover = orderRepository.getTurnoverPerYear(year, monthFrom, monthTo);
        if(turnover == null) {
            return 0.0;
        }
        return turnover;
    }
    
    @Override
    public Integer getUserRegister(int month, int year) {
        Integer userRegisterCount = userRepository.getUserRegister(month, year);
        if(userRegisterCount == null) {
            return 0;
        }
        return userRegisterCount;
    }
    @Override
    public List<SaleReport> getTableSaleReport(Integer year) {
        return reportRepository.getSaleReport(year);
    }
    @Override
    public List<OrderStatusReport> getTableDataOrderStatusReport(Integer year) {
        return reportRepository.getOrderStatusReport(year);
    }
    @Override
    public List<TurnoverReport> getTurnoverReport(Integer year) {
        return reportRepository.getTurnoverReport(year);
    }
}
