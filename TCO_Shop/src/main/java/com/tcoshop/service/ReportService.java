package com.tcoshop.service;

import java.util.List;

import com.tcoshop.entity.Order;
import com.tcoshop.entity.OrderStatusReport;
import com.tcoshop.entity.SaleReport;
import com.tcoshop.entity.TurnoverDetailReport;
import com.tcoshop.entity.TurnoverReport;
import com.tcoshop.entity.User;
import com.tcoshop.entity.UserRegistryReport;

public interface ReportService {
    Double getTurnover(String status);
    Integer getUserCount(String roleId);
    Integer getAllProductCount();
    Integer getAllOrderCount(String status);
    Integer getSalesReport(Integer year, Integer monthFrom, Integer monthTo);
    Integer getOrderCountPerStatus(String status, Integer year, Integer monthFrom, Integer monthTo);
    Double getTurnoverPerYear(Integer year, Integer monthFrom, Integer monthTo);
    Integer getUserRegister(int month, int year);
    List<SaleReport> getTableSaleReport(Integer year);
    List<OrderStatusReport> getTableDataOrderStatusReport(Integer year);
    List<TurnoverReport> getTurnoverReport(Integer year);
    List<TurnoverDetailReport> getTurnoverDetailReport(Integer year, Integer month);
    List<UserRegistryReport> getUserRegistryReport(int year);
    List<User> getUserRegistryByMonthAndYear(int year, int month);
    List<Integer> getAllOrderYear();
    List<Order> findByMonthAndYear(int month, int year);
    Integer findOrderCountByYearAndStatus(int year, String status);
    List<Order> findByYearAndStatus(int year, String status);
}