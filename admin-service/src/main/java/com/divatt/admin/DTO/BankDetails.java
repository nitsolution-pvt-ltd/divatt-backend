package com.divatt.admin.DTO;

import org.springframework.data.mongodb.core.mapping.Field;

public class BankDetails {
	
	
	@Field(name = "ifsc_code")
	private String ifscCode;
	
	
	private String accountNumber;
	
	private String bankName;

	public BankDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BankDetails(String ifscCode, String accountNumber, String bankName) {
		super();
		this.ifscCode = ifscCode;
		this.accountNumber = accountNumber;
		this.bankName = bankName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Override
	public String toString() {
		return "BankDetails [ifscCode=" + ifscCode + ", accountNumber=" + accountNumber + ", bankName=" + bankName
				+ "]";
	}

	
	
	
}
