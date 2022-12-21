package com.tcoshop.util;

import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.Iterator;
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
import com.tcoshop.entity.SaleReport;
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
	public void exportOrderSoldToPDF(List<SaleReport> saleReports, HttpServletResponse resp, Integer year) throws IOException {
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
		for(SaleReport saleReport : saleReports) {
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
	
	public void exportOrderMonthToPDF(List<Order> orderReports, HttpServletResponse resp, Integer month, Integer year) throws IOException {
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
		float[] widths = new float[] {5f, 50f, 20f, 15f, 10f};
		pdfPTable.setWidths(widths);
		pdfPTable.setSpacingBefore(10);
		writeOrderMonthHeader(pdfPTable);
		writeOrderMonthData(pdfPTable, orderReports);
		document.add(pdfPTable);
		document.close();
	}
	
	public void writeOrderMonthData(PdfPTable pdfPTable, List<Order> oderReports) {
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
				productsName += orderDetail.getProduct().getName() + "x" + orderDetail.getQuantity() + "\n";
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
}
