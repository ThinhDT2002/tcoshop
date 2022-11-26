package com.tcoshop.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcoshop.entity.OrderStatusReport;
import com.tcoshop.entity.SaleReport;
import com.tcoshop.entity.TurnoverReport;
import com.tcoshop.service.ReportService;

@RestController
@CrossOrigin("*")
public class ReportAPI {
    @Autowired
    ReportService reportService;
    
    @GetMapping("/api/report/turnover")
    public Double getTurnover() {
        Double turnover = reportService.getTurnover("DaGiaoHang");
        if(turnover == null) {
            return 0.0;
        }
        return turnover;
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
    
    @GetMapping("/api/report/getordercountperstatus")
    public List<Integer> getOrderCountPerStatus(@RequestParam("year") Integer year,
            @RequestParam("monthFrom") Integer monthFrom,
            @RequestParam("monthTo") Integer monthTo) {
        List<Integer> orderCountPerStatusReport = new ArrayList<>();
        int orderChoXacNhan = reportService.getOrderCountPerStatus("ChoXacNhan", year, monthFrom, monthTo);
        int orderChuanBi = reportService.getOrderCountPerStatus("ChuanBi", year, monthFrom, monthTo);
        int orderXuatKho = reportService.getOrderCountPerStatus("XuatKho", year, monthFrom, monthTo);
        int orderVanChuyen = reportService.getOrderCountPerStatus("VanChuyen", year, monthFrom, monthTo);
        int orderDaGiaoHang = reportService.getOrderCountPerStatus("DaGiaoHang", year, monthFrom, monthTo);
        int orderHuyBo = reportService.getOrderCountPerStatus("HuyBo", year, monthFrom, monthTo);
        orderCountPerStatusReport.add(orderChoXacNhan);
        orderCountPerStatusReport.add(orderChuanBi);
        orderCountPerStatusReport.add(orderXuatKho);
        orderCountPerStatusReport.add(orderVanChuyen);
        orderCountPerStatusReport.add(orderDaGiaoHang);
        orderCountPerStatusReport.add(orderHuyBo);
        return orderCountPerStatusReport;
    }
    
    @GetMapping("/api/report/getturnoverperyear")
    public List<Double> getTurnOverPerYear(@RequestParam("year") Integer year) {
        List<Double> turnover = new ArrayList<>();
        double turnover1_2 = reportService.getTurnoverPerYear(year, 1, 2);
        double turnover3_4 = reportService.getTurnoverPerYear(year, 3, 4);
        double turnover5_6 = reportService.getTurnoverPerYear(year, 5, 6);
        double turnover7_8 = reportService.getTurnoverPerYear(year, 7, 8);
        double turnover9_10 = reportService.getTurnoverPerYear(year, 9, 10);
        double turnover11_12 = reportService.getTurnoverPerYear(year, 11, 12);
        turnover.add(turnover1_2);
        turnover.add(turnover3_4);
        turnover.add(turnover5_6);
        turnover.add(turnover7_8);
        turnover.add(turnover9_10);
        turnover.add(turnover11_12);
        return turnover;
    }
    
    @GetMapping("/api/report/getuserregister")
    public List<Integer> getUserRegister(@RequestParam("year") Integer year) {
        List<Integer> userRegisterReport = new ArrayList<>();
        int userRegister1 = reportService.getUserRegister(1, year);
        int userRegister2 = reportService.getUserRegister(2, year);
        int userRegister3 = reportService.getUserRegister(3, year);
        int userRegister4 = reportService.getUserRegister(4, year);
        int userRegister5 = reportService.getUserRegister(5, year);
        int userRegister6 = reportService.getUserRegister(6, year);
        int userRegister7 = reportService.getUserRegister(7, year);
        int userRegister8 = reportService.getUserRegister(8, year);
        int userRegister9 = reportService.getUserRegister(9, year);
        int userRegister10 = reportService.getUserRegister(10, year);
        int userRegister11 = reportService.getUserRegister(11, year);
        int userRegister12 = reportService.getUserRegister(12, year);
        userRegisterReport.add(userRegister1);
        userRegisterReport.add(userRegister2);
        userRegisterReport.add(userRegister3);
        userRegisterReport.add(userRegister4);
        userRegisterReport.add(userRegister5);
        userRegisterReport.add(userRegister6);
        userRegisterReport.add(userRegister7);
        userRegisterReport.add(userRegister8);
        userRegisterReport.add(userRegister9);
        userRegisterReport.add(userRegister10);
        userRegisterReport.add(userRegister11);
        userRegisterReport.add(userRegister12);
        return userRegisterReport;
    }
    
    @GetMapping("/api/report/saleReport/tableData")
    public List<SaleReport> getTableData(@RequestParam("year") Integer year) {
        return reportService.getTableSaleReport(year);
    }
    
    @GetMapping("/api/report/ordetStatusReport/tableData")
    public List<OrderStatusReport> getOSRData(@RequestParam("year") Integer year) {
        return reportService.getTableDataOrderStatusReport(year);
    }
    
    @GetMapping("/api/report/turnoverReport/tableData")
    public List<TurnoverReport> getTurnoverReport(@RequestParam("year") Integer year) {
        return reportService.getTurnoverReport(year);
    }
}
