package com.divatt.designer.entity;

public class WaistAndHip {

	private Double min;
	private Double max;

	public WaistAndHip() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WaistAndHip(Double min, Double max) {
		super();
		this.min = min;
		this.max = max;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	@Override
	public String toString() {
		return "Size [min=" + min + ", max=" + max + "]";
	}

}
