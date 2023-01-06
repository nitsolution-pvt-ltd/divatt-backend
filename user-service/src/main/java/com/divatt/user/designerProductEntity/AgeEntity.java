package com.divatt.user.designerProductEntity;

public class AgeEntity {

	private Integer min;
	private Integer max;
	public AgeEntity() {
		super();
	}
	public AgeEntity(Integer min, Integer max) {
		super();
		this.min = min;
		this.max = max;
	}
	@Override
	public String toString() {
		return "AgeEntity [min=" + min + ", max=" + max + "]";
	}
	public Integer getMin() {
		return min;
	}
	public void setMin(Integer min) {
		this.min = min;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	
	
}
