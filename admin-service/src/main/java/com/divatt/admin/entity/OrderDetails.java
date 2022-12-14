package com.divatt.admin.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class OrderDetails {

	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd hh:mm:ss")
	public String datetime;
	public int designer_id;
	public int user_id;
	public float discount;
	public float mrp;
	public String order_id;
	public int product_id;
	public String product_sku;
	public float sales_price;
	public String size;
	public String tax_type;
	public int units;
	public int hsn_rate;
	public float hsn_amount;
	public float hsn_cgst;
	public float hsn_sgst;
	public float hsn_igst;
	public float total_tax;
	public String order_status;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd hh:mm:ss")
	public String delivery_datetime;
	public String remarks;
	
	public OrderDetails() {
		super();
	}

	public OrderDetails(String datetime, int designer_id, int user_id, float discount, float mrp, String order_id,
			int product_id, String product_sku, float sales_price, String size, String tax_type, int units,
			int hsn_rate, float hsn_amount, float hsn_cgst, float hsn_sgst, float hsn_igst, float total_tax,
			String order_status, String delivery_datetime, String remarks) {
		super();
		this.datetime = datetime;
		this.designer_id = designer_id;
		this.user_id = user_id;
		this.discount = discount;
		this.mrp = mrp;
		this.order_id = order_id;
		this.product_id = product_id;
		this.product_sku = product_sku;
		this.sales_price = sales_price;
		this.size = size;
		this.tax_type = tax_type;
		this.units = units;
		this.hsn_rate = hsn_rate;
		this.hsn_amount = hsn_amount;
		this.hsn_cgst = hsn_cgst;
		this.hsn_sgst = hsn_sgst;
		this.hsn_igst = hsn_igst;
		this.total_tax = total_tax;
		this.order_status = order_status;
		this.delivery_datetime = delivery_datetime;
		this.remarks = remarks;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public int getDesigner_id() {
		return designer_id;
	}

	public void setDesigner_id(int designer_id) {
		this.designer_id = designer_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public float getMrp() {
		return mrp;
	}

	public void setMrp(float mrp) {
		this.mrp = mrp;
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

	public float getSales_price() {
		return sales_price;
	}

	public void setSales_price(float sales_price) {
		this.sales_price = sales_price;
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

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
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

	public float getTotal_tax() {
		return total_tax;
	}

	public void setTotal_tax(float total_tax) {
		this.total_tax = total_tax;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getDelivery_datetime() {
		return delivery_datetime;
	}

	public void setDelivery_datetime(String delivery_datetime) {
		this.delivery_datetime = delivery_datetime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	

	
}
