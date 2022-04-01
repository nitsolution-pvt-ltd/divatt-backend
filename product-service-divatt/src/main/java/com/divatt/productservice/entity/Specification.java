package com.divatt.productservice.entity;

public class Specification {

	private String productDetails;
	private String fittingInformation;
	private String style;
	private Composition composition;
	private String washingInformation;
	public Specification() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Specification(String productDetails, String fittingInformation, String style, Composition composition,
			String washingInformation) {
		super();
		this.productDetails = productDetails;
		this.fittingInformation = fittingInformation;
		this.style = style;
		this.composition = composition;
		this.washingInformation = washingInformation;
	}
	@Override
	public String toString() {
		return "Specification [productDetails=" + productDetails + ", fittingInformation=" + fittingInformation
				+ ", style=" + style + ", composition=" + composition + ", washingInformation=" + washingInformation
				+ "]";
	}
	public String getProductDetails() {
		return productDetails;
	}
	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}
	public String getFittingInformation() {
		return fittingInformation;
	}
	public void setFittingInformation(String fittingInformation) {
		this.fittingInformation = fittingInformation;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public Composition getComposition() {
		return composition;
	}
	public void setComposition(Composition composition) {
		this.composition = composition;
	}
	public String getWashingInformation() {
		return washingInformation;
	}
	public void setWashingInformation(String washingInformation) {
		this.washingInformation = washingInformation;
	}
	
}