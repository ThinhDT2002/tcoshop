package com.tcoshop.service;

public interface ReportService {
    Double getTurnover(String status);
    Integer getUserCount(String roleId);
    Integer getAllProductCount();
    Integer getAllOrderCount(String status);
    Integer getSalesReport(Integer year, Integer monthFrom, Integer monthTo);
}