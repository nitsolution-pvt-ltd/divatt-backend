package com.divatt.designer.entity.account;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class ServiceCharge {
	
	public String designer_invoice_id;
    public int rate;
    public float fee;
    public float cgst;
    public float sgst;
    public float igst;
    public int tcs_rate;
    public float tcs;
    public float total_tax;
    public String status;
    @JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd hh:mm:ss")
    public String date;
    public float total_amount;
    public int units;
    public String updated_by;
	public String updated_datetime;
    public String remarks;
    
	public ServiceCharge() {
		super();
	}

	public String getDesigner_invoice_id() {
		return designer_invoice_id;
	}

	public void setDesigner_invoice_id(String designer_invoice_id) {
		this.designer_invoice_id = designer_invoice_id;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public float getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	public float getCgst() {
		return cgst;
	}

	public void setCgst(float cgst) {
		this.cgst = cgst;
	}

	public float getSgst() {
		return sgst;
	}

	public void setSgst(float sgst) {
		this.sgst = sgst;
	}

	public float getIgst() {
		return igst;
	}

	public void setIgst(float igst) {
		this.igst = igst;
	}

	public int getTcs_rate() {
		return tcs_rate;
	}

	public void setTcs_rate(int tcs_rate) {
		this.tcs_rate = tcs_rate;
	}

	public float getTcs() {
		return tcs;
	}

	public void setTcs(float tcs) {
		this.tcs = tcs;
	}

	public float getTotal_tax() {
		return total_tax;
	}

	public void setTotal_tax(float total_tax) {
		this.total_tax = total_tax;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public float getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(float total_amount) {
		this.total_amount = total_amount;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public String getUpdated_datetime() {
		return updated_datetime;
	}

	public void setUpdated_datetime(String updated_datetime) {
		this.updated_datetime = updated_datetime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
    

}
