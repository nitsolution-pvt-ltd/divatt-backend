package com.divatt.designer.entity.product;

import java.util.Date;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class Deal {
	private String dealName;
	private String dealType;
	private Double dealValue;
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy/MM/dd")
	private Date dealStart;
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy/MM/dd")
	private Date dealEnd;
	private Double salePrice;
	private JSONObject taxAmount;
	public Deal() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Deal(String dealName, String dealType, Double dealValue, Date dealStart, Date dealEnd, Double salePrice,
			JSONObject taxAmount) {
		super();
		this.dealName = dealName;
		this.dealType = dealType;
		this.dealValue = dealValue;
		this.dealStart = dealStart;
		this.dealEnd = dealEnd;
		this.salePrice = salePrice;
		this.taxAmount = taxAmount;
	}
	public String getDealName() {
		return dealName;
	}
	public void setDealName(String dealName) {
		this.dealName = dealName;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public Double getDealValue() {
		return dealValue;
	}
	public void setDealValue(Double dealValue) {
		this.dealValue = dealValue;
	}
	public Date getDealStart() {
		return dealStart;
	}
	public void setDealStart(Date dealStart) {
		this.dealStart = dealStart;
	}
	public Date getDealEnd() {
		return dealEnd;
	}
	public void setDealEnd(Date dealEnd) {
		this.dealEnd = dealEnd;
	}
	public Double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	public JSONObject getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(JSONObject taxAmount) {
		this.taxAmount = taxAmount;
	}
	@Override
	public String toString() {
		return "Deal [dealName=" + dealName + ", dealType=" + dealType + ", dealValue=" + dealValue + ", dealStart="
				+ dealStart + ", dealEnd=" + dealEnd + ", salePrice=" + salePrice + ", taxAmount=" + taxAmount + "]";
	}
	
	
}
