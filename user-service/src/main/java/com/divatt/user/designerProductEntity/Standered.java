package com.divatt.user.designerProductEntity;

public class Standered {

	private String colour;
	private PriceEntity priceEntity;
	private SizeEntity sizeEntity;
	public Standered() {
		super();
	}
	public Standered(String colour, PriceEntity priceEntity, SizeEntity sizeEntity) {
		super();
		this.colour = colour;
		this.priceEntity = priceEntity;
		this.sizeEntity = sizeEntity;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public PriceEntity getPriceEntity() {
		return priceEntity;
	}
	public void setPriceEntity(PriceEntity priceEntity) {
		this.priceEntity = priceEntity;
	}
	public SizeEntity getSizeEntity() {
		return sizeEntity;
	}
	public void setSizeEntity(SizeEntity sizeEntity) {
		this.sizeEntity = sizeEntity;
	}
}
