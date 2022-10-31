package com.divatt.designer.entity.product;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class Deal {
	private String dealName;
	private String dealType;
	private Integer dealValue;
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy/MM/dd")
	private Date dealStart;
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy/MM/dd")
	private Date dealEnd;
	private Integer salePrice;
	public Deal() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Deal(String dealName, String dealType, Integer dealValue, Date dealStart, Date dealEnd, Integer salePrice) {
		super();
		this.dealName = dealName;
		this.dealType = dealType;
		this.dealValue = dealValue;
		this.dealStart = dealStart;
		this.dealEnd = dealEnd;
		this.salePrice = salePrice;
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
	public Integer getDealValue() {
		return dealValue;
	}
	public void setDealValue(Integer dealValue) {
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
	public Integer getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}

	@Override
	public String toString() {
		return "Deal [dealName=" + dealName + ", dealType=" + dealType + ", dealValue=" + dealValue + ", dealStart="
				+ dealStart + ", dealEnd=" + dealEnd + ", salePrice=" + salePrice + "]";
	}

}
