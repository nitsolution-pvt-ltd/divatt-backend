package com.divatt.user.entity;

public class ProductInvoice {

	private String productSKUId;
	private String productDescription;
	private String quantity;
	private Integer grossAmount;
	private Integer igst;
	private Integer withTaxAmount;
	public ProductInvoice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductInvoice(String productSKUId, String productDescription, String quantity, Integer grossAmount,
			Integer igst, Integer withTaxAmount) {
		super();
		this.productSKUId = productSKUId;
		this.productDescription = productDescription;
		this.quantity = quantity;
		this.grossAmount = grossAmount;
		this.igst = igst;
		this.withTaxAmount = withTaxAmount;
	}
	@Override
	public String toString() {
		return "ProductInvoice [productSKUId=" + productSKUId + ", productDescription=" + productDescription
				+ ", quantity=" + quantity + ", grossAmount=" + grossAmount + ", igst=" + igst + ", withTaxAmount="
				+ withTaxAmount + "]";
	}
	public String getProductSKUId() {
		return productSKUId;
	}
	public void setProductSKUId(String productSKUId) {
		this.productSKUId = productSKUId;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public Integer getGrossAmount() {
		return grossAmount;
	}
	public void setGrossAmount(Integer grossAmount) {
		this.grossAmount = grossAmount;
	}
	public Integer getIgst() {
		return igst;
	}
	public void setIgst(Integer igst) {
		this.igst = igst;
	}
	public Integer getWithTaxAmount() {
		return withTaxAmount;
	}
	public void setWithTaxAmount(Integer withTaxAmount) {
		this.withTaxAmount = withTaxAmount;
	}
	
	
}
