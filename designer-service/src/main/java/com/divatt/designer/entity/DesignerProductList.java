package com.divatt.designer.entity;

public class DesignerProductList {
	 private int designerId;
	 private String images;
	 private int productId;
	 private String orderId;
	 private int salesPrice;
	 private int discount;
	 private int mrp;
	 private int units;
	 private Object updatedOn;
	 private String reachedCentralHub;
	 private int userId;
	 private String createdOn;
	    private String productName;
	    private String productSku;
	    private String colour;
	    private String size;
	    private String orderItemStatus;
	    private Object trackingData;
	    private int id;
	    private int taxAmount;
	    private String taxType;
	    private String productDescription;
		public DesignerProductList() {
			super();
			// TODO Auto-generated constructor stub
		}
		public DesignerProductList(int designerId, String images, int productId, String orderId, int salesPrice,
				int discount, int mrp, int units, Object updatedOn, String reachedCentralHub, int userId,
				String createdOn, String productName, String productSku, String colour, String size,
				String orderItemStatus, Object trackingData, int id, int taxAmount, String taxType,
				String productDescription) {
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
			this.productDescription = productDescription;
		}
		@Override
		public String toString() {
			return "DesignerProductList [designerId=" + designerId + ", images=" + images + ", productId=" + productId
					+ ", orderId=" + orderId + ", salesPrice=" + salesPrice + ", discount=" + discount + ", mrp=" + mrp
					+ ", units=" + units + ", updatedOn=" + updatedOn + ", reachedCentralHub=" + reachedCentralHub
					+ ", userId=" + userId + ", createdOn=" + createdOn + ", productName=" + productName
					+ ", productSku=" + productSku + ", colour=" + colour + ", size=" + size + ", orderItemStatus="
					+ orderItemStatus + ", trackingData=" + trackingData + ", id=" + id + ", taxAmount=" + taxAmount
					+ ", taxType=" + taxType + ", productDescription=" + productDescription + "]";
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
		public Object getTrackingData() {
			return trackingData;
		}
		public void setTrackingData(Object trackingData) {
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
		public String getProductDescription() {
			return productDescription;
		}
		public void setProductDescription(String productDescription) {
			this.productDescription = productDescription;
		}
	    
}
