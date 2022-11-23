package com.tcoshop.service;

public interface ReportService {
    Double getTurnover(String status);
    Integer getUserCount(String roleId);
    Integer getAllProductCount();
    Integer getAllOrderCount(String status);
    Integer getSalesReport(Integer year, Integer monthFrom, Integer monthTo);
    Integer getOrderCountPerStatus(String status, Integer year, Integer monthFrom, Integer monthTo);
    Double getTurnoverPerYear(Integer year, Integer monthFrom, Integer monthTo);
    Integer getUserRegister(int month, int year);
}