package com.tcoshop.util;

import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.tcoshop.entity.Order;
import com.tcoshop.entity.OrderDetail;
import com.tcoshop.entity.OrderStatusReport;
import com.tcoshop.entity.Product;
import com.tcoshop.entity.SaleReport;
import com.tcoshop.entity.TurnoverDetailReport;
import com.tcoshop.entity.TurnoverReport;

@Component
public class FileExporter {
	public void setResponHeader(HttpServletResponse resp, String contentType, String extension, String prefix)
			throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
		String timeStamp = dateFormat.format(new Date());
		String fileName = prefix + timeStamp + extension;
		resp.setContentType(contentType);
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName;
		resp.setHeader(headerKey, headerValue);
	}

	public void exportOrderSoldToPDF(List<SaleReport> saleReports, HttpServletResponse resp, Integer year)
			throws IOException {
		setResponHeader(resp, "application/pdf", ".pdf", "SaleReport_");

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, resp.getOutputStream());
		document.open();

		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setSize(13);
		font.setColor(Color.BLACK);
		Paragraph paragraph = new Paragraph("THONG KE DON HANG TRONG " + year, font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(paragraph);

		PdfPTable pdfPTable = new PdfPTable(3);
		pdfPTable.setWidthPercentage(100f);
		pdfPTable.setSpacingBefore(10);
		writeOrderSoldHeader(pdfPTable);
		writeOrderSoldData(pdfPTable, saleReports);
		document.add(pdfPTable);
		document.close();
	}

	public void writeOrderSoldData(PdfPTable pdfPTable, List<SaleReport> saleReports) {
		for (SaleReport saleReport : saleReports) {
			PdfPCell cell = new PdfPCell();
			cell.setPhrase(new Phrase(String.valueOf(saleReport.getMonth())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
			cell.setPhrase(new Phrase(String.valueOf(saleReport.getOrderCount())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
			cell.setPhrase(new Phrase(String.valueOf(saleReport.getProductCount())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
		}
	}

	public void writeOrderSoldHeader(PdfPTable pdfPTable) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(Color.white);

		cell.setPhrase(new Phrase("THANG", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		cell.setPhrase(new Phrase("SO DON HANG", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		cell.setPhrase(new Phrase("SAN PHAM BAN DUOC", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);

	}

	public void exportOrderMonthToPDF(List<Order> orderReports, HttpServletResponse resp, Integer month, Integer year)
			throws IOException {
		setResponHeader(resp, "application/pdf", ".pdf", "SaleReport_");

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, resp.getOutputStream());
		document.open();

		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setSize(13);
		font.setColor(Color.BLACK);
		Paragraph paragraph = new Paragraph("CHI TIET DON HANG TRONG THANG " + month + " " + year, font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(paragraph);

		PdfPTable pdfPTable = new PdfPTable(5);
		pdfPTable.setWidthPercentage(100f);
		float[] widths = new float[] { 5f, 50f, 20f, 15f, 10f };
		pdfPTable.setWidths(widths);
		pdfPTable.setSpacingBefore(10);
		writeOrderMonthHeader(pdfPTable);
		writeOrderMonthData(pdfPTable, orderReports);
		document.add(pdfPTable);
		document.close();
	}

	public void writeOrderMonthData(PdfPTable pdfPTable, List<Order> oderReports) {
		for (Order orderReport : oderReports) {
			PdfPCell cell = new PdfPCell();
			cell.setPhrase(new Phrase(String.valueOf(orderReport.getId())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
			String productsName = "";
			String productsPrice = "";
			List<OrderDetail> orderDetails = orderReport.getOrderDetails();
			DecimalFormat df = new DecimalFormat("#,###");
			df.setMaximumFractionDigits(0);
			
			for(OrderDetail orderDetail : orderDetails) {	
				productsName += orderDetail.getProduct().getName() + " x" + orderDetail.getQuantity() + "\n";
				productsPrice += df.format(orderDetail.getPrice()) + "\n";
			}
			cell.setPhrase(new Phrase(productsName));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
			cell.setPhrase(new Phrase(productsPrice));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String date = dateFormat.format(orderReport.getCreateDate()) + orderReport.getOrderTimeDetail();
			cell.setPhrase(new Phrase(date));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);

			cell.setPhrase(new Phrase(String.valueOf(orderReport.getStatus())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
		}
	}

	public void writeOrderMonthHeader(PdfPTable pdfPTable) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(Color.white);

		cell.setPhrase(new Phrase("ID", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		cell.setPhrase(new Phrase("SAN PHAM", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		cell.setPhrase(new Phrase("GIA", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		cell.setPhrase(new Phrase("NGAY DAT", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		cell.setPhrase(new Phrase("TRANG THAI", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);

	}

	// turnover

	public void exportTurnoverToPDF(List<TurnoverReport> turnoverReports, HttpServletResponse resp, Integer year)
			throws IOException {
		setResponHeader(resp, "application/pdf", ".pdf", "TurnoverReport_");

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, resp.getOutputStream());
		document.open();

		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setSize(13);
		font.setColor(Color.BLACK);
		Paragraph paragraph = new Paragraph("THONG KE DOANH THU TRONG " + year, font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(paragraph);

		PdfPTable pdfPTable = new PdfPTable(2);
		pdfPTable.setWidthPercentage(100f);
		pdfPTable.setSpacingBefore(10);
		writeTurnoverHeader(pdfPTable);
		writeTurnoverData(pdfPTable, turnoverReports);
		document.add(pdfPTable);
		document.close();
	}

	// turnover month
	public void exportTurnoverMonthToPDF(List<TurnoverDetailReport> turnoverDetailReports, HttpServletResponse resp, Integer month,
			Integer year) throws IOException {
		setResponHeader(resp, "application/pdf", ".pdf", "TurnoverMonthReport_");

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, resp.getOutputStream());
		document.open();

		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setSize(13);
		font.setColor(Color.BLACK);
		Paragraph paragraph = new Paragraph("CHI TIET DOANH THU TRONG THANG " + month + " " + year, font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(paragraph);

		PdfPTable pdfPTable = new PdfPTable(3);
		pdfPTable.setWidthPercentage(100f);
		float[] widths = new float[] { 60f, 15f, 25f};
		pdfPTable.setWidths(widths);
		pdfPTable.setSpacingBefore(10);
		writeTurnoverMonthHeader(pdfPTable);
		writeTurnoverMonthData(pdfPTable, turnoverDetailReports);
		document.add(pdfPTable);
		document.close();
	}

	public void writeTurnoverMonthData(PdfPTable pdfPTable, List<TurnoverDetailReport> turnoverDetailReports) {
		for (TurnoverDetailReport TurnoverDetailReport : turnoverDetailReports) {
			PdfPCell cell = new PdfPCell();
			
			cell.setPhrase(new Phrase(String.valueOf(TurnoverDetailReport.getProductName())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
			
			cell.setPhrase(new Phrase(String.valueOf(TurnoverDetailReport.getQuantity())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
		

			DecimalFormat df = new DecimalFormat("#,###");
			df.setMaximumFractionDigits(0);
			cell.setPhrase(new Phrase(String.valueOf(df.format(TurnoverDetailReport.getTurnover()))));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
		}
	}

	public void writeTurnoverData(PdfPTable pdfPTable, List<TurnoverReport> turnoverReports) {
		for (TurnoverReport turnoverReport : turnoverReports) {
			PdfPCell cell = new PdfPCell();
			cell.setPhrase(new Phrase(String.valueOf(turnoverReport.getMonth())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);

			DecimalFormat df = new DecimalFormat("#,###");
			df.setMaximumFractionDigits(0);
			cell.setPhrase(new Phrase(String.valueOf(df.format(turnoverReport.getTurnover()))));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
		}
	}

	public void writeTurnoverHeader(PdfPTable pdfPTable) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(Color.white);

		cell.setPhrase(new Phrase("THANG", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		cell.setPhrase(new Phrase("DOANH THU (VND)", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);

	}

	public void writeTurnoverMonthHeader(PdfPTable pdfPTable) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(Color.white);

		cell.setPhrase(new Phrase("SAN PHAM", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		cell.setPhrase(new Phrase("SO LUONG", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		cell.setPhrase(new Phrase("DOANH THU (VND)", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
	}
	
	public void exportOrderStatusReportToPDF(List<OrderStatusReport> orderStatusReports, HttpServletResponse resp, Integer year) throws IOException {
		setResponHeader(resp, "application/pdf", ".pdf", "SaleReport_");
		
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, resp.getOutputStream());
		document.open();
		
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setSize(13);
		font.setColor(Color.BLACK);
		Paragraph paragraph = new Paragraph("SO DON HANG THEO TRANG THAI " + year, font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(paragraph);
		
		PdfPTable pdfPTable = new PdfPTable(2);
		pdfPTable.setWidthPercentage(100f);
		pdfPTable.setSpacingBefore(10);
		writeOrderStatusHeader(pdfPTable);
		writeOrderStatusData(pdfPTable, orderStatusReports);
		document.add(pdfPTable);
		document.close();
	}
	
	public void writeOrderStatusData(PdfPTable pdfPTable, List<OrderStatusReport> orderStatusReports) {
		for(OrderStatusReport orderStatusReport : orderStatusReports) {
			PdfPCell cell = new PdfPCell();
			cell.setPhrase(new Phrase(String.valueOf(orderStatusReport.getStatus())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
			cell.setPhrase(new Phrase(String.valueOf(orderStatusReport.getOrderPerStatusCount())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
			
		}
	}
	
	public void writeOrderStatusHeader(PdfPTable pdfPTable) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPadding(5);
		
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(Color.white);
		
		cell.setPhrase(new Phrase("TRANG THAI", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		cell.setPhrase(new Phrase("SO DON HANG", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
	}
	
	public void exportOrderStatusYearToPDF(List<Order> orderReports, HttpServletResponse resp, Integer year, String status) throws IOException {
		setResponHeader(resp, "application/pdf", ".pdf", "SaleReport_");
		
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, resp.getOutputStream());
		document.open();
		
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setSize(13);
		font.setColor(Color.BLACK);
		Paragraph paragraph = new Paragraph("CHI TIET DON HANG " + status + " " + year, font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(paragraph);
		
		PdfPTable pdfPTable = new PdfPTable(5);
		pdfPTable.setWidthPercentage(100f);
		float[] widths = new float[] {5f, 50f, 20f, 15f, 10f};
		pdfPTable.setWidths(widths);
		pdfPTable.setSpacingBefore(10);
		writeOrderStatusYearHeader(pdfPTable);
		writeOrderStatusYearData(pdfPTable, orderReports);
		document.add(pdfPTable);
		document.close();
	}
	
	public void writeOrderStatusYearData(PdfPTable pdfPTable, List<Order> oderReports) {
		for(Order orderReport : oderReports) {
			PdfPCell cell = new PdfPCell();
			cell.setPhrase(new Phrase(String.valueOf(orderReport.getId())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
			String productsName = "";
			String productsPrice = "";
			List<OrderDetail> orderDetails = orderReport.getOrderDetails();
			DecimalFormat df = new DecimalFormat("#,###");
			df.setMaximumFractionDigits(0);
			
			for(OrderDetail orderDetail : orderDetails) {	
				productsName += orderDetail.getProduct().getName() + " x" + orderDetail.getQuantity() + "\n";
				productsPrice += df.format(orderDetail.getPrice()) + "\n";
			}
			cell.setPhrase(new Phrase(productsName));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);	
			cell.setPhrase(new Phrase(productsPrice));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String date = dateFormat.format(orderReport.getCreateDate()) + orderReport.getOrderTimeDetail();
			cell.setPhrase(new Phrase(date));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
			
			cell.setPhrase(new Phrase(String.valueOf(orderReport.getStatus())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
		}
	}
	
	public void writeOrderStatusYearHeader(PdfPTable pdfPTable) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPadding(5);
		
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(Color.white);
		
		cell.setPhrase(new Phrase("ID", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		cell.setPhrase(new Phrase("SAN PHAM", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		cell.setPhrase(new Phrase("GIA", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		cell.setPhrase(new Phrase("NGAY DAT", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		cell.setPhrase(new Phrase("TRANG THAI", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		
	}

	public void exportProductStockToPDF(List<Product> productStockReports, HttpServletResponse resp) throws IOException {
		setResponHeader(resp, "application/pdf", ".pdf", "SaleReport_");
		
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, resp.getOutputStream());
		document.open();
		
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setSize(13);
		font.setColor(Color.BLACK);
		Paragraph paragraph = new Paragraph("HANG TON KHO" , font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(paragraph);
		
		PdfPTable pdfPTable = new PdfPTable(3);
		pdfPTable.setWidthPercentage(100f);
		pdfPTable.setSpacingBefore(10);
		writeProductStockHeader(pdfPTable);
		writeProductStockData(pdfPTable, productStockReports);
		document.add(pdfPTable);
		document.close();
	}
	
	public void writeProductStockData(PdfPTable pdfPTable, List<Product> productStockReports) {
		for(Product productStockReport : productStockReports) {
			PdfPCell cell = new PdfPCell();
			cell.setPhrase(new Phrase(String.valueOf(productStockReport.getId())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
			cell.setPhrase(new Phrase(String.valueOf(productStockReport.getName())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
			cell.setPhrase(new Phrase(String.valueOf(productStockReport.getStock())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
			
		}
	}
	
	public void writeProductStockHeader(PdfPTable pdfPTable) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPadding(5);
		
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(Color.white);
		
		cell.setPhrase(new Phrase("MA SAN PHAM", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		cell.setPhrase(new Phrase("TEN SAN PHAM", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		cell.setPhrase(new Phrase("SO LUONG", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
	}
	
	public void exportProductNotSoldToPDF(List<Product> productNotSoldReports, HttpServletResponse resp,Integer month, Integer year) throws IOException {
		setResponHeader(resp, "application/pdf", ".pdf", "SaleReport_");
		
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, resp.getOutputStream());
		document.open();
		
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setSize(13);
		font.setColor(Color.BLACK);
		Paragraph paragraph = new Paragraph("HANG CHUA DUOC BAN TRONG " + month + " " + year, font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(paragraph);
		
		PdfPTable pdfPTable = new PdfPTable(3);
		pdfPTable.setWidthPercentage(100f);
		pdfPTable.setSpacingBefore(10);
		writeProductStockHeader(pdfPTable);
		writeProductStockData(pdfPTable, productNotSoldReports);
		document.add(pdfPTable);
		document.close();
	}
	
	public void writeProductNotSoldData(PdfPTable pdfPTable, List<Product> ProductNotSoldReports) {
		for(Product productNotSoldReport : ProductNotSoldReports) {
			PdfPCell cell = new PdfPCell();
			cell.setPhrase(new Phrase(String.valueOf(productNotSoldReport.getId())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
			cell.setPhrase(new Phrase(String.valueOf(productNotSoldReport.getName())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
			cell.setPhrase(new Phrase(String.valueOf(productNotSoldReport.getStock())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfPTable.addCell(cell);
		}
	}
	
	public void writeProductNotSoldHeader(PdfPTable pdfPTable) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPadding(5);
		
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(Color.white);
		
		cell.setPhrase(new Phrase("MA SAN PHAM", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		cell.setPhrase(new Phrase("TEN SAN PHAM", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		cell.setPhrase(new Phrase("SO LUONG", font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		pdfPTable.addCell(cell);
		
	}
}
