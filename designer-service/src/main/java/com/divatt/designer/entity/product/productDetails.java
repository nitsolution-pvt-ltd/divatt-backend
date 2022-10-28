package com.divatt.designer.entity.product;

public class productDetails {
private	String productName ;
private	String productDescription ;
private	String fittingInformation ;
private	String occation ;
private	String composition ;
private	String washingInformation ;
public productDetails() {
	super();
	// TODO Auto-generated constructor stub
}
public productDetails(String productName, String productDescription, String fittingInformation, String occation,
		String composition, String washingInformation) {
	super();
	this.productName = productName;
	this.productDescription = productDescription;
	this.fittingInformation = fittingInformation;
	this.occation = occation;
	this.composition = composition;
	this.washingInformation = washingInformation;
}
@Override
public String toString() {
	return "productDetails [productName=" + productName + ", productDescription=" + productDescription
			+ ", fittingInformation=" + fittingInformation + ", occation=" + occation + ", composition=" + composition
			+ ", washingInformation=" + washingInformation + "]";
}
public String getProductName() {
	return productName;
}
public void setProductName(String productName) {
	this.productName = productName;
}
public String getProductDescription() {
	return productDescription;
}
public void setProductDescription(String productDescription) {
	this.productDescription = productDescription;
}
public String getFittingInformation() {
	return fittingInformation;
}
public void setFittingInformation(String fittingInformation) {
	this.fittingInformation = fittingInformation;
}
public String getOccation() {
	return occation;
}
public void setOccation(String occation) {
	this.occation = occation;
}
public String getComposition() {
	return composition;
}
public void setComposition(String composition) {
	this.composition = composition;
}
public String getWashingInformation() {
	return washingInformation;
}
public void setWashingInformation(String washingInformation) {
	this.washingInformation = washingInformation;
}




}
