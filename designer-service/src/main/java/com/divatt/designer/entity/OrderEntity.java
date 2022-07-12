package com.divatt.designer.entity;

public class OrderEntity {

	//private String orderId;
	private Integer productId;
	private String size;
	private String images;
	private String colour;
	private String productSku;
	private Integer units;
	private String productName;
	private Integer mrp;
	private Integer salesPrice;
	private Integer taxRate;
	private Integer taxAmount;
	private String taxType;
	private Integer designerId;
	public OrderEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderEntity(Integer productId, String size, String images, String colour, String productSku, Integer units,
			String productName, Integer mrp, Integer salesPrice, Integer taxRate, Integer taxAmount, String taxType,
			Integer designerId) {
		super();
		this.productId = productId;
		this.size = size;
		this.images = images;
		this.colour = colour;
		this.productSku = productSku;
		this.units = units;
		this.productName = productName;
		this.mrp = mrp;
		this.salesPrice = salesPrice;
		this.taxRate = taxRate;
		this.taxAmount = taxAmount;
		this.taxType = taxType;
		this.designerId = designerId;
	}
	@Override
	public String toString() {
		return "OrderEntity [productId=" + productId + ", size=" + size + ", images=" + images + ", colour=" + colour
				+ ", productSku=" + productSku + ", units=" + units + ", productName=" + productName + ", mrp=" + mrp
				+ ", salesPrice=" + salesPrice + ", taxRate=" + taxRate + ", taxAmount=" + taxAmount + ", taxType="
				+ taxType + ", designerId=" + designerId + "]";
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public String getProductSku() {
		return productSku;
	}
	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}
	public Integer getUnits() {
		return units;
	}
	public void setUnits(Integer units) {
		this.units = units;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getMrp() {
		return mrp;
	}
	public void setMrp(Integer mrp) {
		this.mrp = mrp;
	}
	public Integer getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(Integer salesPrice) {
		this.salesPrice = salesPrice;
	}
	public Integer getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(Integer taxRate) {
		this.taxRate = taxRate;
	}
	public Integer getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(Integer taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	public Integer getDesignerId() {
		return designerId;
	}
	public void setDesignerId(Integer designerId) {
		this.designerId = designerId;
	}
	
}
