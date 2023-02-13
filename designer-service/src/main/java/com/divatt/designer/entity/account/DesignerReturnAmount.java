package com.divatt.designer.entity.account;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class DesignerReturnAmount {

	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd hh:mm:ss")
	public String datetime;
	public long designer_id;
	public int units;
	public float mrp;
	public float discount;
	public float sales_price;
	public int hsn_rate;
	public float hsn_amount;
	public float hsn_cgst;
	public float hsn_sgst;
	public float hsn_igst;
	public float tcs;
	public float total_tax_amount;
	public float total_amount_received;
	public float net_payable_designer;
	public String status;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd hh:mm:ss")
	public String payment_datetime;
	public String order_id;
	public int product_id;
	public String product_sku;
	public String size;
	public String tax_type;
	public String updated_by;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd hh:mm:ss")
	public String updated_datetime;
	public String remarks;
	
	
	public DesignerReturnAmount() {
		super();
	}


	public String getDatetime() {
		return datetime;
	}


	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}


	public long getDesigner_id() {
		return designer_id;
	}


	public void setDesigner_id(long designer_id) {
		this.designer_id = designer_id;
	}


	public int getUnits() {
		return units;
	}


	public void setUnits(int units) {
		this.units = units;
	}


	public float getMrp() {
		return mrp;
	}


	public void setMrp(float mrp) {
		this.mrp = mrp;
	}


	public float getDiscount() {
		return discount;
	}


	public void setDiscount(float discount) {
		this.discount = discount;
	}


	public float getSales_price() {
		return sales_price;
	}


	public void setSales_price(float sales_price) {
		this.sales_price = sales_price;
	}


	public int getHsn_rate() {
		return hsn_rate;
	}


	public void setHsn_rate(int hsn_rate) {
		this.hsn_rate = hsn_rate;
	}


	public float getHsn_amount() {
		return hsn_amount;
	}


	public void setHsn_amount(float hsn_amount) {
		this.hsn_amount = hsn_amount;
	}


	public float getHsn_cgst() {
		return hsn_cgst;
	}


	public void setHsn_cgst(float hsn_cgst) {
		this.hsn_cgst = hsn_cgst;
	}


	public float getHsn_sgst() {
		return hsn_sgst;
	}


	public void setHsn_sgst(float hsn_sgst) {
		this.hsn_sgst = hsn_sgst;
	}


	public float getHsn_igst() {
		return hsn_igst;
	}


	public void setHsn_igst(float hsn_igst) {
		this.hsn_igst = hsn_igst;
	}


	public float getTcs() {
		return tcs;
	}


	public void setTcs(float tcs) {
		this.tcs = tcs;
	}


	public float getTotal_tax_amount() {
		return total_tax_amount;
	}


	public void setTotal_tax_amount(float total_tax_amount) {
		this.total_tax_amount = total_tax_amount;
	}


	public float getTotal_amount_received() {
		return total_amount_received;
	}


	public void setTotal_amount_received(float total_amount_received) {
		this.total_amount_received = total_amount_received;
	}


	public float getNet_payable_designer() {
		return net_payable_designer;
	}


	public void setNet_payable_designer(float net_payable_designer) {
		this.net_payable_designer = net_payable_designer;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getPayment_datetime() {
		return payment_datetime;
	}


	public void setPayment_datetime(String payment_datetime) {
		this.payment_datetime = payment_datetime;
	}


	public String getOrder_id() {
		return order_id;
	}


	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}


	public int getProduct_id() {
		return product_id;
	}


	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}


	public String getProduct_sku() {
		return product_sku;
	}


	public void setProduct_sku(String product_sku) {
		this.product_sku = product_sku;
	}


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public String getTax_type() {
		return tax_type;
	}


	public void setTax_type(String tax_type) {
		this.tax_type = tax_type;
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
