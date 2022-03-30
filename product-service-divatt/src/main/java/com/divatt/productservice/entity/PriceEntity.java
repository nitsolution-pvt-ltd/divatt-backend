package com.divatt.productservice.entity;

public class PriceEntity {

	private IN indPrice;
	private US usPrice;
	public PriceEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PriceEntity(IN indPrice, US usPrice) {
		super();
		this.indPrice = indPrice;
		this.usPrice = usPrice;
	}
	@Override
	public String toString() {
		return "PriceEntity [indPrice=" + indPrice + ", usPrice=" + usPrice + "]";
	}
	public IN getIndPrice() {
		return indPrice;
	}
	public void setIndPrice(IN indPrice) {
		this.indPrice = indPrice;
	}
	public US getUsPrice() {
		return usPrice;
	}
	public void setUsPrice(US usPrice) {
		this.usPrice = usPrice;
	}
	
}
