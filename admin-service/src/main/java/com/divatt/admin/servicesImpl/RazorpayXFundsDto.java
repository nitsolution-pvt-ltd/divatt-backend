package com.divatt.admin.servicesImpl;

import java.util.List;

import com.divatt.admin.DTO.Bank_account;

public class RazorpayXFundsDto {
	private String id;
	private String entity;
	private String contact_id;
	private String account_type;
	private Bank_account bank_account;
	private String batch_id;
	private String active;
	private long created_at;
	public RazorpayXFundsDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RazorpayXFundsDto(String id, String entity, String contact_id, String account_type,
			Bank_account bank_account, String batch_id, String active, long created_at) {
		super();
		this.id = id;
		this.entity = entity;
		this.contact_id = contact_id;
		this.account_type = account_type;
		this.bank_account = bank_account;
		this.batch_id = batch_id;
		this.active = active;
		this.created_at = created_at;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
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
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public long getCreated_at() {
		return created_at;
	}
	public void setCreated_at(long created_at) {
		this.created_at = created_at;
	}
	@Override
	public String toString() {
		return "RazorpayXFundsDto [id=" + id + ", entity=" + entity + ", contact_id=" + contact_id + ", account_type="
				+ account_type + ", bank_account=" + bank_account + ", batch_id=" + batch_id + ", active=" + active
				+ ", created_at=" + created_at + "]";
	}
	
}
