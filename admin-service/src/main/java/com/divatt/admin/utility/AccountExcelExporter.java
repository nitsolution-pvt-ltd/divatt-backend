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

		createCell(row, 0, "Order Number", style);
		createCell(row, 1, "Order Date", style);
		createCell(row, 2, "Unique ID of Designer", style);
		createCell(row, 3, "GSTIN of Designer", style);
		createCell(row, 4, "Invoice Number", style);
		createCell(row, 5, "Invoice Date", style);
		createCell(row, 6, "Transaction Value", style);
		createCell(row, 7, "CGST", style);
		createCell(row, 8, "SGST", style);
		createCell(row, 9, "IGST", style);
		createCell(row, 10, "Gift Wrapping Charges", style);
		createCell(row, 11, "Invoice Value", style);
		createCell(row, 12, "Return Particulars of any merchandise", style);
		createCell(row, 13, "Return Particulars", style);
		createCell(row, 14, "TCS", style);
		createCell(row, 15, "Service Fees Collected by Divatt", style);
		createCell(row, 16, "GST on Service Fees", style);
		createCell(row, 17, "Total Service Fees Component", style);
		createCell(row, 18, "Total Recovery Divatt", style);

	}

	private void writeDataLines() {
		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(10);
		style.setFont(font);
		
		for (AccountEntity rowsAccount : listAccount) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

			createCell(row, columnCount++, rowsAccount.getDesigner_return_amount().get(0).getOrder_id(), style);
			createCell(row, columnCount++, rowsAccount.getOrder_details().get(0).getOrder_date(), style);
			createCell(row, columnCount++, rowsAccount.getDesigner_details().getUid(), style);
			createCell(row, columnCount++, rowsAccount.getDesigner_details().getGst_in(), style);
			createCell(row, columnCount++, rowsAccount.getService_charge().getDesigner_invoice_id(), style);
			createCell(row, columnCount++, rowsAccount.getService_charge().getDate(), style);
			createCell(row, columnCount++, rowsAccount.getOrder_details().get(0).getSales_price(), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getOrder_details().get(0).getHsn_cgst()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getOrder_details().get(0).getHsn_sgst()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getOrder_details().get(0).getHsn_igst()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getOrder_details().get(0).getGiftWrapAmount()), style);
			createCell(row, columnCount++, CommonUtility.iNVValues(rowsAccount), style);
			createCell(row, columnCount++, 0, style);
			createCell(row, columnCount++, 0, style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getService_charge().getTcs()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getService_charge().getFee()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getService_charge().getIgst()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getService_charge().getIgst()+rowsAccount.getService_charge().getFee()), style);
			createCell(row, columnCount++, CommonUtility.duoble(rowsAccount.getService_charge().getTotal_amount()), style);
			
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
