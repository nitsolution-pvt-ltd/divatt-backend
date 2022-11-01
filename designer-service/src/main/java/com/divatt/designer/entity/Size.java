package com.divatt.designer.entity;

public class Size {

	private Double chest;
	private Double neck;
	private WaistAndHip waist;
	private WaistAndHip hip;

	public Size() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Size(Double chest, Double neck, WaistAndHip waist, WaistAndHip hip) {
		super();
		this.chest = chest;
		this.neck = neck;
		this.waist = waist;
		this.hip = hip;
	}

	public Double getChest() {
		return chest;
	}

	public void setChest(Double chest) {
		this.chest = chest;
	}

	public Double getNeck() {
		return neck;
	}

	public void setNeck(Double neck) {
		this.neck = neck;
	}

	public WaistAndHip getWaist() {
		return waist;
	}

	public void setWaist(WaistAndHip waist) {
		this.waist = waist;
	}

	public WaistAndHip getHip() {
		return hip;
	}

	public void setHip(WaistAndHip hip) {
		this.hip = hip;
	}

	@Override
	public String toString() {
		return "Size [chest=" + chest + ", neck=" + neck + ", waist=" + waist + ", hip=" + hip + "]";
	}

}
