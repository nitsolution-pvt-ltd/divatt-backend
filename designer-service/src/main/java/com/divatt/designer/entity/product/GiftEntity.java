package com.divatt.designer.entity.product;

public class GiftEntity {

	private Integer INAmount;
	private Integer USAmount;
	public GiftEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GiftEntity(Integer iNAmount, Integer uSAmount) {
		super();
		INAmount = iNAmount;
		USAmount = uSAmount;
	}
	public Integer getINAmount() {
		return INAmount;
	}
	public void setINAmount(Integer iNAmount) {
		INAmount = iNAmount;
	}
	public Integer getUSAmount() {
		return USAmount;
	}
	public void setUSAmount(Integer uSAmount) {
		USAmount = uSAmount;
	}
	@Override
	public String toString() {
		return "GiftEntity [INAmount=" + INAmount + ", USAmount=" + USAmount + "]";
	}
	
}
