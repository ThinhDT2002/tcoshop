package com.tcoshop.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tcoshop.service.ReportService;

@RestController
@CrossOrigin("*")
public class ReportAPI {
    @Autowired
    ReportService reportService;
    
    @GetMapping("/api/report/turnover")
    public Double getTurnover() {
        return reportService.getTurnover("DaGiaoHang");
    }
    @GetMapping("/api/report/usercount")
    public Integer getUserCount() {
        return reportService.getUserCount("USER");
    }

    @GetMapping("/api/report/getallproductcount")
    public Integer getAllProductCount() {
        return reportService.getAllProductCount();
    }

    @GetMapping("/api/report/getallordercount")
    public Integer getAllOrderCount() {
        return reportService.getAllOrderCount("HuyBo");
    }
    
    @GetMapping("/api/report/getsalesreport/{year}")
    public List<Integer> getSalesReport(@PathVariable("year") Integer year) {
        List<Integer> reportSales = new ArrayList<>();
        int orderCountMonth1And2 = reportService.getSalesReport(year, 1, 2);
        int orderCountMonth3And4 = reportService.getSalesReport(year, 3, 4);
        int orderCountMonth5And6 = reportService.getSalesReport(year, 5, 6);
        int orderCountMonth7And8 = reportService.getSalesReport(year, 7, 8);
        int orderCountMonth9And10 = reportService.getSalesReport(year, 9, 10);
        int orderCountMonth11And12 = reportService.getSalesReport(year, 11, 12);
        reportSales.add(orderCountMonth1And2);
        reportSales.add(orderCountMonth3And4);
        reportSales.add(orderCountMonth5And6);
        reportSales.add(orderCountMonth7And8);
        reportSales.add(orderCountMonth9And10);
        reportSales.add(orderCountMonth11And12);
        return reportSales;
    }
}
