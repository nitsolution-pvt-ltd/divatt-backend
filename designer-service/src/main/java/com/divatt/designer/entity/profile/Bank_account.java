package com.divatt.designer.entity.profile;

import java.util.List;

public class Bank_account {
private String name;
    private String ifsc;
	private String account_number;
	private List<String>notes;
	private String bank_name;
	public Bank_account() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Bank_account(String name, String ifsc, String account_number, List<String> notes, String bank_name) {
		super();
		this.name = name;
		this.ifsc = ifsc;
		this.account_number = account_number;
		this.notes = notes;
		this.bank_name = bank_name;
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
	public List<String> getNotes() {
		return notes;
	}
	public void setNotes(List<String> notes) {
		this.notes = notes;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	@Override
	public String toString() {
		return "Bank_account [name=" + name + ", ifsc=" + ifsc + ", account_number=" + account_number + ", notes="
				+ notes + ", bank_name=" + bank_name + "]";
	}

}
