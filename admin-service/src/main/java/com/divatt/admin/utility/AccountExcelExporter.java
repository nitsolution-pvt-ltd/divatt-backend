package com.divatt.admin.utility;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import com.divatt.admin.entity.AccountEntity;

public class AccountExcelExporter {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<AccountEntity> listAccount;

	
	public AccountExcelExporter(List<AccountEntity> listAccount) {
		this.listAccount = listAccount;
		workbook = new XSSFWorkbook();
	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else if (value instanceof Float) {
			cell.setCellValue((Float) value);
		} else if (value instanceof Double) {
			cell.setCellValue((Double) value);
		} else if (value instanceof Date) {
			cell.setCellValue((Date) value);
		}else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("Reports");

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(12);
		style.setFont(font);

		createCell(row, 0, "Sl No.", style);
		createCell(row, 1, "Datetime", style);
		
		createCell(row, 2, "Designer Invoice ID", style);
		createCell(row, 3, "Serivce Status", style);
		createCell(row, 4, "Units", style);
		createCell(row, 5, "Service Rate", style);
		createCell(row, 6, "Service Fee", style);
		createCell(row, 7, "Service IGST", style);
		createCell(row, 8, "Service CGST", style);
		createCell(row, 9, "Service SGST", style);
		createCell(row, 10, "TCS", style);
		createCell(row, 11, "Service Total Tax", style);
		createCell(row, 12, "Service Total Aamount", style);
		
		createCell(row, 13, "Govt. Status", style);
		createCell(row, 14, "Govt. Rate", style);
		createCell(row, 15, "Govt. Fee", style);
		createCell(row, 16, "Govt. IGST", style);
		createCell(row, 17, "Govt. CGST", style);
		createCell(row, 18, "Govt. SGST", style);
		createCell(row, 19, "Govt. Total Tax", style);
		createCell(row, 20, "Govt. Total Aamount", style);
		
		createCell(row, 21, "MRP", style);
		createCell(row, 22, "Sale Price", style);
		createCell(row, 23, "Discount", style);
		createCell(row, 24, "HSN Rate", style);
		createCell(row, 25, "HSN Amount", style);
		createCell(row, 26, "HSN IGST", style);
		createCell(row, 27, "HSN CGST", style);
		createCell(row, 28, "HSN SGST", style);
		createCell(row, 29, "Designer Total Tax", style);
		createCell(row, 30, "Designer Total Received", style);
		createCell(row, 31, "Designer Net Payable", style);
		createCell(row, 32, "Designer Status", style);
		createCell(row, 33, "Payment Datetime", style);
		createCell(row, 34, "Order ID", style);
		createCell(row, 35, "Giftwrap Amount", style);
	

	}

	private void writeDataLines() {
		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(10);
		style.setFont(font);

		int count = 1;
		for (AccountEntity rowsAccount : listAccount) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

			createCell(row, columnCount++, count, style);
			createCell(row, columnCount++, rowsAccount.getDatetime(), style);
			
			createCell(row, columnCount++, rowsAccount.getService_charge().getDesigner_invoice_id(), style);
			createCell(row, columnCount++, rowsAccount.getService_charge().getStatus(), style);
			createCell(row, columnCount++, rowsAccount.getService_charge().getUnits(), style);
			createCell(row, columnCount++, rowsAccount.getService_charge().getRate(), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getService_charge().getFee()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getService_charge().getIgst()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getService_charge().getCgst()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getService_charge().getSgst()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getService_charge().getTcs()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getService_charge().getTotal_tax()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getService_charge().getTotal_amount()), style);
			
			createCell(row, columnCount++, rowsAccount.getGovt_charge().get(0).getStatus(), style);
			createCell(row, columnCount++, rowsAccount.getGovt_charge().get(0).getRate(), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getGovt_charge().get(0).getFee()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getGovt_charge().get(0).getIgst()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getGovt_charge().get(0).getCgst()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getGovt_charge().get(0).getSgst()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getGovt_charge().get(0).getTotal_tax()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getGovt_charge().get(0).getTotal_amount()), style);
			
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getDesigner_return_amount().get(0).getMrp()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getDesigner_return_amount().get(0).getSales_price()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getDesigner_return_amount().get(0).getDiscount()), style);
			createCell(row, columnCount++, rowsAccount.getDesigner_return_amount().get(0).getHsn_rate(), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getDesigner_return_amount().get(0).getHsn_amount()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getDesigner_return_amount().get(0).getHsn_igst()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getDesigner_return_amount().get(0).getHsn_cgst()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getDesigner_return_amount().get(0).getHsn_sgst()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getDesigner_return_amount().get(0).getTotal_tax_amount()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getDesigner_return_amount().get(0).getTotal_amount_received()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getDesigner_return_amount().get(0).getNet_payable_designer()), style);
			createCell(row, columnCount++, rowsAccount.getDesigner_return_amount().get(0).getStatus(), style);
			createCell(row, columnCount++, rowsAccount.getDesigner_return_amount().get(0).getPayment_datetime(), style);
			createCell(row, columnCount++, rowsAccount.getDesigner_return_amount().get(0).getOrder_id(), style);
			createCell(row, columnCount++, rowsAccount.getOrder_details().get(0).getGiftWrapAmount(), style);
			
			
			count++;
		}
	}

	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLines();

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();

	}

}
