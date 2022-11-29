package com.tcoshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcoshop.entity.Order;
import com.tcoshop.entity.OrderDetail;
import com.tcoshop.entity.OrderStatusReport;
import com.tcoshop.entity.Product;
import com.tcoshop.entity.SaleReport;
import com.tcoshop.entity.TopProduct;
import com.tcoshop.entity.TurnoverDetailReport;
import com.tcoshop.entity.TurnoverReport;
import com.tcoshop.entity.User;
import com.tcoshop.entity.UserRegistryReport;
import com.tcoshop.entity.UserShoppingReport;
import com.tcoshop.repository.OrderDetailRepository;
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
    @Autowired
    OrderDetailRepository orderDetailRepository;
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
    @Override
    public List<TurnoverDetailReport> getTurnoverDetailReport(Integer year, Integer month) {
        return reportRepository.getTurnoverDetailReport(year, month);
    }
    @Override
    public List<UserRegistryReport> getUserRegistryReport(int year) {
        return reportRepository.getUserRegistryReport(year);
    }
    @Override
    public List<User> getUserRegistryByMonthAndYear(int year, int month) {
        return userRepository.getUserByYearAndMonth(year, month);
    }
    @Override
    public List<Integer> getAllOrderYear() {
        return orderRepository.getAllYearOrder();
    }
    @Override
    public List<Order> findByMonthAndYear(int month, int year) {
        return orderRepository.findByMonthAndYear(month, year);
    }
    @Override
    public Integer findOrderCountByYearAndStatus(int year, String status) {
        return orderRepository.findOrderCountByYearAndStatus(year, status);
    }
    @Override
    public List<Order> findByYearAndStatus(int year, String status) {
        return orderRepository.findByYearAndStatus(year, status);
    }
    @Override
    public List<Product> findProductNotSoldInMonth(int month, int year) {
        return productRepository.findProductNotSoldInMonth(month, year);
    }
    @Override
    public List<Integer> findAllYearUserRegistry() {
        return userRepository.findAllYearUserRegistry();
    }
    @Override
    public List<UserShoppingReport> findAllUserShoppingReport() {
        return reportRepository.getUserShoppingReporot();
    }
    @Override
    public List<Order> findTop5OrderByCreateDate() {
        return orderRepository.findTop5OrderByCreateDate();
    }
    @Override
    public List<OrderDetail> findTop5OrderDetail() {
        return orderDetailRepository.findTop5OrderDetail();
    }
    @Override
    public List<User> findTop6ByCreatedateDESC() {
        return userRepository.findTop6ByCreatedateDESC();
    }
    @Override
    public List<TopProduct> findTopProducts() {
        return reportRepository.getTopProduct();
    }
    @Override
    public List<Product> findProductBestSold() {
        return productRepository.findProductBestSold();
    }
}
