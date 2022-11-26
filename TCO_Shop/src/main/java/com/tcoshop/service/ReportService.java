package com.tcoshop.service;

import java.util.List;

import com.tcoshop.entity.OrderStatusReport;
import com.tcoshop.entity.SaleReport;
import com.tcoshop.entity.TurnoverReport;

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
}