package com.divatt.productservice.entity;

public class Specification {

	private String productDetails;
	private String fittingInformation;
	private String style;
	private Composition compose;
	private String washingInformation;
	public Specification() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Specification(String productDetails, String fittingInformation, String style, Composition compose,
			String washingInformation) {
		super();
		this.productDetails = productDetails;
		this.fittingInformation = fittingInformation;
		this.style = style;
		this.compose = compose;
		this.washingInformation = washingInformation;
	}
	@Override
	public String toString() {
		return "Specification [productDetails=" + productDetails + ", fittingInformation=" + fittingInformation
				+ ", style=" + style + ", compose=" + compose + ", washingInformation=" + washingInformation + "]";
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
	public Composition getCompose() {
		return compose;
	}
	public void setCompose(Composition compose) {
		this.compose = compose;
	}
	public String getWashingInformation() {
		return washingInformation;
	}
	public void setWashingInformation(String washingInformation) {
		this.washingInformation = washingInformation;
	}
	
}
