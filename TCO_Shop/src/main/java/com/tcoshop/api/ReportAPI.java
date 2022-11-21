package com.tcoshop.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcoshop.repository.OrderRepository;
import com.tcoshop.service.OrderService;
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

    @GetMapping("/api/order/getallordercount")
    public Integer getAllOrderCount() {
        return reportService.getAllOrderCount("HuyBo");
    }
}
