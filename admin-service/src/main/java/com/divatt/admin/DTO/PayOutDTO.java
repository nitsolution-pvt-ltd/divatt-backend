package com.divatt.admin.DTO;

import java.util.ArrayList;
import java.util.List;

public class PayOutDTO {
	private Long designerId;
	private String orderId;
	private int productId;
	private String account_number;
	private String fund_account_id;
	private Integer amount;
	private String currency;
	private String mode;
	private String purpose;
	private Boolean queue_if_low_balance;
	private String reference_id;
	private String narration;
	private List<String> notes = new ArrayList<>();
	public PayOutDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PayOutDTO(Long designerId, String orderId, int productId, String account_number, String fund_account_id,
			Integer amount, String currency, String mode, String purpose, Boolean queue_if_low_balance,
			String reference_id, String narration, List<String> notes) {
		super();
		this.designerId = designerId;
		this.orderId = orderId;
		this.productId = productId;
		this.account_number = account_number;
		this.fund_account_id = fund_account_id;
		this.amount = amount;
		this.currency = currency;
		this.mode = mode;
		this.purpose = purpose;
		this.queue_if_low_balance = queue_if_low_balance;
		this.reference_id = reference_id;
		this.narration = narration;
		this.notes = notes;
	}
	public Long getDesignerId() {
		return designerId;
	}
	public void setDesignerId(Long designerId) {
		this.designerId = designerId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public String getFund_account_id() {
		return fund_account_id;
	}
	public void setFund_account_id(String fund_account_id) {
		this.fund_account_id = fund_account_id;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public Boolean getQueue_if_low_balance() {
		return queue_if_low_balance;
	}
	public void setQueue_if_low_balance(Boolean queue_if_low_balance) {
		this.queue_if_low_balance = queue_if_low_balance;
	}
	public String getReference_id() {
		return reference_id;
	}
	public void setReference_id(String reference_id) {
		this.reference_id = reference_id;
	}
	public String getNarration() {
		return narration;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}
	public List<String> getNotes() {
		return notes;
	}
	public void setNotes(List<String> notes) {
		this.notes = notes;
	}
	@Override
	public String toString() {
		return "PayOutDTO [designerId=" + designerId + ", orderId=" + orderId + ", productId=" + productId
				+ ", account_number=" + account_number + ", fund_account_id=" + fund_account_id + ", amount=" + amount
				+ ", currency=" + currency + ", mode=" + mode + ", purpose=" + purpose + ", queue_if_low_balance="
				+ queue_if_low_balance + ", reference_id=" + reference_id + ", narration=" + narration + ", notes="
				+ notes + "]";
	}
		}
