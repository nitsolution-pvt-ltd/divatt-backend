package com.divatt.designer.entity;

import org.json.simple.JSONObject;

public class InvoiceProductList {
    
	public int designerId;
    public String images;
    public int productId;
    public String orderId;
    public int salesPrice;
    public int discount;
    public int mrp;
    public int units;
    public Object updatedOn;
    public String reachedCentralHub;
    public int userId;
    public String createdOn;
    public String productName;
    public String productSku;
    public String colour;
    public String size;
    public String orderItemStatus;
    public JSONObject trackingData;
    public int id;
    public int taxAmount;
    public String taxType;
	public InvoiceProductList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InvoiceProductList(int designerId, String images, int productId, String orderId, int salesPrice,
			int discount, int mrp, int units, Object updatedOn, String reachedCentralHub, int userId, String createdOn,
			String productName, String productSku, String colour, String size, String orderItemStatus,
			JSONObject trackingData, int id, int taxAmount, String taxType) {
		super();
		this.designerId = designerId;
		this.images = images;
		this.productId = productId;
		this.orderId = orderId;
		this.salesPrice = salesPrice;
		this.discount = discount;
		this.mrp = mrp;
		this.units = units;
		this.updatedOn = updatedOn;
		this.reachedCentralHub = reachedCentralHub;
		this.userId = userId;
		this.createdOn = createdOn;
		this.productName = productName;
		this.productSku = productSku;
		this.colour = colour;
		this.size = size;
		this.orderItemStatus = orderItemStatus;
		this.trackingData = trackingData;
		this.id = id;
		this.taxAmount = taxAmount;
		this.taxType = taxType;
	}
	@Override
	public String toString() {
		return "InvoiceProductList [designerId=" + designerId + ", images=" + images + ", productId=" + productId
				+ ", orderId=" + orderId + ", salesPrice=" + salesPrice + ", discount=" + discount + ", mrp=" + mrp
				+ ", units=" + units + ", updatedOn=" + updatedOn + ", reachedCentralHub=" + reachedCentralHub
				+ ", userId=" + userId + ", createdOn=" + createdOn + ", productName=" + productName + ", productSku="
				+ productSku + ", colour=" + colour + ", size=" + size + ", orderItemStatus=" + orderItemStatus
				+ ", trackingData=" + trackingData + ", id=" + id + ", taxAmount=" + taxAmount + ", taxType=" + taxType
				+ "]";
	}
	public int getDesignerId() {
		return designerId;
	}
	public void setDesignerId(int designerId) {
		this.designerId = designerId;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(int salesPrice) {
		this.salesPrice = salesPrice;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public int getMrp() {
		return mrp;
	}
	public void setMrp(int mrp) {
		this.mrp = mrp;
	}
	public int getUnits() {
		return units;
	}
	public void setUnits(int units) {
		this.units = units;
	}
	public Object getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Object updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getReachedCentralHub() {
		return reachedCentralHub;
	}
	public void setReachedCentralHub(String reachedCentralHub) {
		this.reachedCentralHub = reachedCentralHub;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductSku() {
		return productSku;
	}
	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getOrderItemStatus() {
		return orderItemStatus;
	}
	public void setOrderItemStatus(String orderItemStatus) {
		this.orderItemStatus = orderItemStatus;
	}
	public JSONObject getTrackingData() {
		return trackingData;
	}
	public void setTrackingData(JSONObject trackingData) {
		this.trackingData = trackingData;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(int taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
}
