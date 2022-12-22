package com.divatt.user.entity;


public class ProductDetails {

	    public String colour;
	    public String createdOn;
	    public int designerId;
	    public int discount;
	    public int id;
	    public String images;
	    public int mrp;
	    public String orderId;
	    public String orderItemStatus;
	    public int productId;
	    public String productName;
	    public Object productSku;
	    public String reachedCentralHub;
	    public int salesPrice;
	    public String size;
	    public int taxAmount;
	    public String taxType;
	    public int units;
	    public Object updatedOn;
	    public int userId;
		public ProductDetails() {
			super();
			// TODO Auto-generated constructor stub
		}
		public ProductDetails(String colour, String createdOn, int designerId, int discount, int id, String images,
				int mrp, String orderId, String orderItemStatus, int productId, String productName, Object productSku,
				String reachedCentralHub, int salesPrice, String size, int taxAmount, String taxType, int units,
				Object updatedOn, int userId) {
			super();
			this.colour = colour;
			this.createdOn = createdOn;
			this.designerId = designerId;
			this.discount = discount;
			this.id = id;
			this.images = images;
			this.mrp = mrp;
			this.orderId = orderId;
			this.orderItemStatus = orderItemStatus;
			this.productId = productId;
			this.productName = productName;
			this.productSku = productSku;
			this.reachedCentralHub = reachedCentralHub;
			this.salesPrice = salesPrice;
			this.size = size;
			this.taxAmount = taxAmount;
			this.taxType = taxType;
			this.units = units;
			this.updatedOn = updatedOn;
			this.userId = userId;
		}
		@Override
		public String toString() {
			return "ProductDetails [colour=" + colour + ", createdOn=" + createdOn + ", designerId=" + designerId
					+ ", discount=" + discount + ", id=" + id + ", images=" + images + ", mrp=" + mrp + ", orderId="
					+ orderId + ", orderItemStatus=" + orderItemStatus + ", productId=" + productId + ", productName="
					+ productName + ", productSku=" + productSku + ", reachedCentralHub=" + reachedCentralHub
					+ ", salesPrice=" + salesPrice + ", size=" + size + ", taxAmount=" + taxAmount + ", taxType="
					+ taxType + ", units=" + units + ", updatedOn=" + updatedOn + ", userId=" + userId + "]";
		}
		public String getColour() {
			return colour;
		}
		public void setColour(String colour) {
			this.colour = colour;
		}
		public String getCreatedOn() {
			return createdOn;
		}
		public void setCreatedOn(String createdOn) {
			this.createdOn = createdOn;
		}
		public int getDesignerId() {
			return designerId;
		}
		public void setDesignerId(int designerId) {
			this.designerId = designerId;
		}
		public int getDiscount() {
			return discount;
		}
		public void setDiscount(int discount) {
			this.discount = discount;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getImages() {
			return images;
		}
		public void setImages(String images) {
			this.images = images;
		}
		public int getMrp() {
			return mrp;
		}
		public void setMrp(int mrp) {
			this.mrp = mrp;
		}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public String getOrderItemStatus() {
			return orderItemStatus;
		}
		public void setOrderItemStatus(String orderItemStatus) {
			this.orderItemStatus = orderItemStatus;
		}
		public int getProductId() {
			return productId;
		}
		public void setProductId(int productId) {
			this.productId = productId;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public Object getProductSku() {
			return productSku;
		}
		public void setProductSku(Object productSku) {
			this.productSku = productSku;
		}
		public String getReachedCentralHub() {
			return reachedCentralHub;
		}
		public void setReachedCentralHub(String reachedCentralHub) {
			this.reachedCentralHub = reachedCentralHub;
		}
		public int getSalesPrice() {
			return salesPrice;
		}
		public void setSalesPrice(int salesPrice) {
			this.salesPrice = salesPrice;
		}
		public String getSize() {
			return size;
		}
		public void setSize(String size) {
			this.size = size;
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
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
	    
}
