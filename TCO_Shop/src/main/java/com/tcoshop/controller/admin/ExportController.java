package com.tcoshop.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tcoshop.entity.Order;
import com.tcoshop.entity.OrderStatusReport;
import com.tcoshop.entity.SaleReport;
import com.tcoshop.entity.TurnoverDetailReport;
import com.tcoshop.entity.TurnoverReport;
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
	@GetMapping("/exportPDF/orderStatus/{year}")
	public void exportOrderStatusToPDF(HttpServletResponse resp, @PathVariable("year") Integer year) throws IOException {
		List<OrderStatusReport> orderStatusReports = reportService.getTableDataOrderStatusReport(year);
		fileExporter.exportOrderStatusReportToPDF(orderStatusReports, resp, year);
	}
	@GetMapping("/exportPDF/orderStatusYear/{year}/{status}")
	public void exportOrderStatusYearToPDF(HttpServletResponse resp,@PathVariable("year") Integer year, @PathVariable("status") String status ) throws IOException {
		List<Order> orderStatusYearReports = reportService.findByYearAndStatus(year, status);
		fileExporter.exportOrderStatusYearToPDF(orderStatusYearReports, resp, year, status);
	}
	
	@GetMapping("/exportPDF/turnover/{year}")
	public void exportTurnoverSoldToPDF(HttpServletResponse resp, @PathVariable("year") Integer year) throws IOException {
		List<TurnoverReport> turnReports = reportService.getTurnoverReport(year);
		fileExporter.exportTurnoverToPDF(turnReports, resp, year);
	}
	
	@GetMapping("/exportPDF/turnoverMonth/{year}/{month}")
	public void exportTurnoverMonthToPDF(HttpServletResponse resp, @PathVariable("month") Integer month, @PathVariable("year") Integer year) throws IOException {
		List<TurnoverDetailReport> turnReports = reportService.getTurnoverDetailReport(year, month);
		fileExporter.exportTurnoverMonthToPDF(turnReports, resp, month, year);
	}

}
