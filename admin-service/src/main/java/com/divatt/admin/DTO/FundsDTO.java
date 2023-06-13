package com.divatt.admin.DTO;

import java.util.ArrayList;
import java.util.List;

public class FundsDTO {
 private String contact_id;
 private String account_type;
 private Bank_account bank_account;
public FundsDTO() {
	super();
	// TODO Auto-generated constructor stub
}
public FundsDTO(String contact_id, String account_type, Bank_account bank_account) {
	super();
	this.contact_id = contact_id;
	this.account_type = account_type;
	this.bank_account = bank_account;
}
public String getContact_id() {
	return contact_id;
}
public void setContact_id(String contact_id) {
	this.contact_id = contact_id;
}
public String getAccount_type() {
	return account_type;
}
public void setAccount_type(String account_type) {
	this.account_type = account_type;
}
public Bank_account getBank_account() {
	return bank_account;
}
public void setBank_account(Bank_account bank_account) {
	this.bank_account = bank_account;
}
@Override
public String toString() {
	return "FundsDTO [contact_id=" + contact_id + ", account_type=" + account_type + ", bank_account=" + bank_account
			+ "]";
}

 
}
