package com.divatt.designer.productEntity;

public class Composition {
	private String cotton;
	private String polystar;
	public Composition() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Composition(String cotton, String polystar) {
		super();
		this.cotton = cotton;
		this.polystar = polystar;
	}
	public String getCotton() {
		return cotton;
	}
	public void setCotton(String cotton) {
		this.cotton = cotton;
	}
	public String getPolystar() {
		return polystar;
	}
	public void setPolystar(String polystar) {
		this.polystar = polystar;
	}
	@Override
	public String toString() {
		return "Composition [cotton=" + cotton + ", polystar=" + polystar + "]";
	}
	
}
