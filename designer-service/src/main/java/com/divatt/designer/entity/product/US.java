package com.divatt.designer.entity.product;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class US {

	private Long mrp;
	private Long dealPrice;
	private String discountType;
	private Integer discountValue;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	private Date dealStart;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	private Date dealEnd;
	public US() {
		super();
		// TODO Auto-generated constructor stub
	}
	public US(Long mrp, Long dealPrice, String discountType, Integer discountValue, Date dealStart, Date dealEnd) {
		super();
		this.mrp = mrp;
		this.dealPrice = dealPrice;
		this.discountType = discountType;
		this.discountValue = discountValue;
		this.dealStart = dealStart;
		this.dealEnd = dealEnd;
	}
	@Override
	public String toString() {
		return "USPrice [mrp=" + mrp + ", dealPrice=" + dealPrice + ", discountType=" + discountType
				+ ", discountValue=" + discountValue + ", dealStart=" + dealStart + ", dealEnd=" + dealEnd
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	public Long getMrp() {
		return mrp;
	}
	public void setMrp(Long mrp) {
		this.mrp = mrp;
	}
	public Long getDealPrice() {
		return dealPrice;
	}
	public void setDealPrice(Long dealPrice) {
		this.dealPrice = dealPrice;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public Integer getDiscountValue() {
		return discountValue;
	}
	public void setDiscountValue(Integer discountValue) {
		this.discountValue = discountValue;
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
	
	
}
