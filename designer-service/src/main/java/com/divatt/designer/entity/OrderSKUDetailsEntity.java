package com.divatt.designer.entity;

public class OrderSKUDetailsEntity {
	private Integer id;
	private Long userId;
	private String orderId;
	private int designerId;
	private int productId;
	private String productName;
	private String productSku;
	private String size;
	private String images;
	private String colour;
	private Long units;
	private Long mrp;
	private Long salesPrice;
	private Long discount;
	private Long taxAmount;
	private String taxType;
	private String orderItemStatus;
	private String reachedCentralHub;
	private String createdOn;
	private String updatedOn;
	private String displayName;
	private String shippingDate;
	
	public OrderSKUDetailsEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderSKUDetailsEntity(Integer id, Long userId, String orderId, int designerId, int productId,
			String productName, String productSku, String size, String images, String colour, Long units, Long mrp,
			Long salesPrice, Long discount, Long taxAmount, String taxType, String orderItemStatus,
			String reachedCentralHub, String createdOn, String updatedOn, String displayName, String shippingDate) {
		super();
		this.id = id;
		this.userId = userId;
		this.orderId = orderId;
		this.designerId = designerId;
		this.productId = productId;
		this.productName = productName;
		this.productSku = productSku;
		this.size = size;
		this.images = images;
		this.colour = colour;
		this.units = units;
		this.mrp = mrp;
		this.salesPrice = salesPrice;
		this.discount = discount;
		this.taxAmount = taxAmount;
		this.taxType = taxType;
		this.orderItemStatus = orderItemStatus;
		this.reachedCentralHub = reachedCentralHub;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.displayName = displayName;
		this.shippingDate = shippingDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getDesignerId() {
		return designerId;
	}

	public void setDesignerId(int designerId) {
		this.designerId = designerId;
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

	public String getProductSku() {
		return productSku;
	}

	public void setProductSku(String productSku) {
		this.productSku = productSku;
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

	public Long getUnits() {
		return units;
	}

	public void setUnits(Long units) {
		this.units = units;
	}

	public Long getMrp() {
		return mrp;
	}

	public void setMrp(Long mrp) {
		this.mrp = mrp;
	}

	public Long getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Long salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Long getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Long taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public String getOrderItemStatus() {
		return orderItemStatus;
	}

	public void setOrderItemStatus(String orderItemStatus) {
		this.orderItemStatus = orderItemStatus;
	}

	public String getReachedCentralHub() {
		return reachedCentralHub;
	}

	public void setReachedCentralHub(String reachedCentralHub) {
		this.reachedCentralHub = reachedCentralHub;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
	}

	@Override
	public String toString() {
		return "OrderSKUDetailsEntity [id=" + id + ", userId=" + userId + ", orderId=" + orderId + ", designerId="
				+ designerId + ", productId=" + productId + ", productName=" + productName + ", productSku="
				+ productSku + ", size=" + size + ", images=" + images + ", colour=" + colour + ", units=" + units
				+ ", mrp=" + mrp + ", salesPrice=" + salesPrice + ", discount=" + discount + ", taxAmount=" + taxAmount
				+ ", taxType=" + taxType + ", orderItemStatus=" + orderItemStatus + ", reachedCentralHub="
				+ reachedCentralHub + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", displayName="
				+ displayName + ", shippingDate=" + shippingDate + "]";
	}

	
}
