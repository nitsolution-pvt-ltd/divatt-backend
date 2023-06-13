package com.divatt.designer.entity.profile;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Field;

public class BankDetails {
	
	
	@Field(name = "bank_name") private String bankName;
	
	
	@Field(name = "account_number") private String accountNumber;
	
	
	@Field(name = "ifsc_code") private String ifscCode;

	public BankDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BankDetails(@NotNull String bankName, @NotNull String accountNumber, @NotNull String ifscCode) {
		super();
		this.bankName = bankName;
		this.accountNumber = accountNumber;
		this.ifscCode = ifscCode;
	}

	@Override
	public String toString() {
		return "BankDetails [bankName=" + bankName + ", accountNumber=" + accountNumber + ", ifscCode=" + ifscCode
				+ "]";
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	
	

}
