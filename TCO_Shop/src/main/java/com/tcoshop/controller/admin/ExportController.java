package com.tcoshop.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tcoshop.entity.Order;
import com.tcoshop.entity.SaleReport;
import com.tcoshop.service.ReportService;
import com.tcoshop.util.FileExporter;

@RestController
public class ExportController {
	@Autowired
	FileExporter fileExporter;
	@Autowired
	ReportService reportService;
	
	@GetMapping("/exportPDF/orderSolder/{year}")
	public void exportOrderSoldToPDF(HttpServletResponse resp, @PathVariable("year") Integer year) throws IOException {
		List<SaleReport> saleReports = reportService.getTableSaleReport(year);
		fileExporter.exportOrderSoldToPDF(saleReports, resp, year);
	}
	@GetMapping("/exportPDF/orderMonth/{year}/{month}")
	public void exportOrderMonthToPDF(HttpServletResponse resp, @PathVariable("month") int month, @PathVariable("year") int year) throws IOException {
		List<Order> orderReports = reportService.findByMonthAndYear(month, year);
		fileExporter.exportOrderMonthToPDF(orderReports, resp, month, year);
	}
	
}
