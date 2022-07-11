package com.divatt.user.entity;

public class PurchaseEntity {

	private Integer minQty;
	private Integer maxQty;
	public PurchaseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PurchaseEntity(Integer minQty, Integer maxQty) {
		super();
		this.minQty = minQty;
		this.maxQty = maxQty;
	}
	@Override
	public String toString() {
		return "PurchaseEntity [minQty=" + minQty + ", maxQty=" + maxQty + "]";
	}
	public Integer getMinQty() {
		return minQty;
	}
	public void setMinQty(Integer minQty) {
		this.minQty = minQty;
	}
	public Integer getMaxQty() {
		return maxQty;
	}
	public void setMaxQty(Integer maxQty) {
		this.maxQty = maxQty;
	}
	
}
