package com.divatt.user.designerProductEntity;

public class PurchaseEntity {

	private Integer purchaseMin;
	private Integer purchaseMax;
	public PurchaseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PurchaseEntity(Integer purchaseMin, Integer purchaseMax) {
		super();
		this.purchaseMin = purchaseMin;
		this.purchaseMax = purchaseMax;
	}
	public Integer getPurchaseMin() {
		return purchaseMin;
	}
	public void setPurchaseMin(Integer purchaseMin) {
		this.purchaseMin = purchaseMin;
	}
	public Integer getPurchaseMax() {
		return purchaseMax;
	}
	public void setPurchaseMax(Integer purchaseMax) {
		this.purchaseMax = purchaseMax;
	}
	@Override
	public String toString() {
		return "PurchaseEntity [purchaseMin=" + purchaseMin + ", purchaseMax=" + purchaseMax + "]";
	}
	
}
