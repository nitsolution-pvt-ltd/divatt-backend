package com.divatt.admin.DTO;

import java.util.List;

public class Bank_account {
	private String name;
    private String ifsc;
	private String account_number;

	public Bank_account() {
		super();
		// TODO Auto-generated constructor stub
	}
public Bank_account(String name, String ifsc, String account_number) {
	super();
	this.name = name;
	this.ifsc = ifsc;
	this.account_number = account_number;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getIfsc() {
	return ifsc;
}
public void setIfsc(String ifsc) {
	this.ifsc = ifsc;
}
public String getAccount_number() {
	return account_number;
}
public void setAccount_number(String account_number) {
	this.account_number = account_number;
}
@Override
public String toString() {
	return "Bank_account [name=" + name + ", ifsc=" + ifsc + ", account_number=" + account_number + "]";
}


}
