package com.divatt.admin.entity.hsnCode;

import org.springframework.stereotype.Component;

@Component
public class UploadErrorEntity {

	private int rowNumber;
	private int cellNumber;
	private String errorDescription;

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(int cellNumber) {
		this.cellNumber = cellNumber;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

}
