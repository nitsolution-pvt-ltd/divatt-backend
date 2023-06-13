package com.divatt.designer.entity.profile;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;



public class RazorpayX {
	
	@Field(name="contacts")
	private  List<String> contacts=new ArrayList<>();
	
	private FundsBankAccount fundsBankAccount;
	public RazorpayX() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RazorpayX(List<String> contacts, FundsBankAccount fundsBankAccount) {
		super();
		this.contacts = contacts;
		this.fundsBankAccount = fundsBankAccount;
	}
	public List<String> getContacts() {
		return contacts;
	}
	public void setContacts(List<String> contacts) {
		this.contacts = contacts;
	}
	public FundsBankAccount getFundsBankAccount() {
		return fundsBankAccount;
	}
	public void setFundsBankAccount(FundsBankAccount fundsBankAccount) {
		this.fundsBankAccount = fundsBankAccount;
	}
	@Override
	public String toString() {
		return "RazorpayX [contacts=" + contacts + ", fundsBankAccount=" + fundsBankAccount + "]";
	}

	}
