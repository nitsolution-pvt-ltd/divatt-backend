package com.divatt.designer.entity;

public class SizeWomen {

	private Double uk;
	private Double bust;
	private Double waist;
	private Double hip;

	public SizeWomen() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SizeWomen(Double uk, Double bust, Double waist, Double hip) {
		super();
		this.uk = uk;
		this.bust = bust;
		this.waist = waist;
		this.hip = hip;
	}

	public Double getUk() {
		return uk;
	}

	public void setUk(Double uk) {
		this.uk = uk;
	}

	public Double getBust() {
		return bust;
	}

	public void setBust(Double bust) {
		this.bust = bust;
	}

	public Double getWaist() {
		return waist;
	}

	public void setWaist(Double waist) {
		this.waist = waist;
	}

	public Double getHip() {
		return hip;
	}

	public void setHip(Double hip) {
		this.hip = hip;
	}

	@Override
	public String toString() {
		return "WomenSize [uk=" + uk + ", bust=" + bust + ", waist=" + waist + ", hip=" + hip + "]";
	}

}
