package com.divatt.designer.entity;

public class ProductInvoice {

	private String productSKUId;
	private String productDescription;
	private String quantity;
	private Integer grossAmount;
	private Integer igst;
	private Integer withTaxAmount;
	private Integer totalGrossAmount;
	private Integer totalTax;
	private Integer totalwithTaxAmount;
	public ProductInvoice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductInvoice(String productSKUId, String productDescription, String quantity, Integer grossAmount,
			Integer igst, Integer withTaxAmount, Integer totalGrossAmount, Integer totalTax,
			Integer totalwithTaxAmount) {
		super();
		this.productSKUId = productSKUId;
		this.productDescription = productDescription;
		this.quantity = quantity;
		this.grossAmount = grossAmount;
		this.igst = igst;
		this.withTaxAmount = withTaxAmount;
		this.totalGrossAmount = totalGrossAmount;
		this.totalTax = totalTax;
		this. totalwithTaxAmount = totalwithTaxAmount;
	}
	@Override
	public String toString() {
		return "ProductInvoice [productSKUId=" + productSKUId + ", productDescription=" + productDescription
				+ ", quantity=" + quantity + ", grossAmount=" + grossAmount + ", igst=" + igst + ", withTaxAmount="
				+ withTaxAmount + ", totalGrossAmount=" + totalGrossAmount + ", totalTax=" + totalTax
				+ ", totalwithTaxAmount=" + totalwithTaxAmount + "]";
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
	public Integer getTotalGrossAmount() {
		return totalGrossAmount;
	}
	public void setTotalGrossAmount(Integer totalGrossAmount) {
		this.totalGrossAmount = totalGrossAmount;
	}
	public Integer getTotalTax() {
		return totalTax;
	}
	public void setTotalTax(Integer totalTax) {
		this.totalTax = totalTax;
	}
	public Integer getTotalwithTaxAmount() {
		return totalwithTaxAmount;
	}
	public void setTotalwithTaxAmount(Integer totalwithTaxAmount) {
		this.totalwithTaxAmount = totalwithTaxAmount;
	}
	
}
