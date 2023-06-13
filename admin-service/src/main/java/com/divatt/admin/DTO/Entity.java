package com.divatt.admin.DTO;

import java.util.List;

public class Entity {
	private String id;
	private String entity;
	private String fund_account_id;
	private Double amount;
	private String currency;
	private List<String> notes;
	private Double fees;
	private Double tax;
	private String status;
	private String purpose;
	private String utr;
	private String mode;
	private String reference_id;
	private String narration;
	private String batch_id;
	private Double created_at;
	private String failure_reason;
	private StatusDetails status_details;
	private ErrorDetails error;
	public Entity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Entity(String id, String entity, String fund_account_id, Double amount, String currency, List<String> notes,
			Double fees, Double tax, String status, String purpose, String utr, String mode, String reference_id,
			String narration, String batch_id, Double created_at, String failure_reason, StatusDetails status_details,
			ErrorDetails error) {
		super();
		this.id = id;
		this.entity = entity;
		this.fund_account_id = fund_account_id;
		this.amount = amount;
		this.currency = currency;
		this.notes = notes;
		this.fees = fees;
		this.tax = tax;
		this.status = status;
		this.purpose = purpose;
		this.utr = utr;
		this.mode = mode;
		this.reference_id = reference_id;
		this.narration = narration;
		this.batch_id = batch_id;
		this.created_at = created_at;
		this.failure_reason = failure_reason;
		this.status_details = status_details;
		this.error = error;
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
	public String getFund_account_id() {
		return fund_account_id;
	}
	public void setFund_account_id(String fund_account_id) {
		this.fund_account_id = fund_account_id;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public List<String> getNotes() {
		return notes;
	}
	public void setNotes(List<String> notes) {
		this.notes = notes;
	}
	public Double getFees() {
		return fees;
	}
	public void setFees(Double fees) {
		this.fees = fees;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getUtr() {
		return utr;
	}
	public void setUtr(String utr) {
		this.utr = utr;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
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
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public Double getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Double created_at) {
		this.created_at = created_at;
	}
	public String getFailure_reason() {
		return failure_reason;
	}
	public void setFailure_reason(String failure_reason) {
		this.failure_reason = failure_reason;
	}
	public StatusDetails getStatus_details() {
		return status_details;
	}
	public void setStatus_details(StatusDetails status_details) {
		this.status_details = status_details;
	}
	public ErrorDetails getError() {
		return error;
	}
	public void setError(ErrorDetails error) {
		this.error = error;
	}
	@Override
	public String toString() {
		return "Entity [id=" + id + ", entity=" + entity + ", fund_account_id=" + fund_account_id + ", amount=" + amount
				+ ", currency=" + currency + ", notes=" + notes + ", fees=" + fees + ", tax=" + tax + ", status="
				+ status + ", purpose=" + purpose + ", utr=" + utr + ", mode=" + mode + ", reference_id=" + reference_id
				+ ", narration=" + narration + ", batch_id=" + batch_id + ", created_at=" + created_at
				+ ", failure_reason=" + failure_reason + ", status_details=" + status_details + ", error=" + error
				+ "]";
	}
	
	
	

}
