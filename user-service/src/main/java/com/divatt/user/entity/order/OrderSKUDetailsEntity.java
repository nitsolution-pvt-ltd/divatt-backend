package com.divatt.user.entity.order;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "tbl_order_sku_details_new")
public class OrderSKUDetailsEntity {
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_order_sku_details_new";
	 
	@Id
	private Integer id;
	
	@NotNull(message = "Username is required!")
	@Field(name = "user_id") 
	private Long userId;
	
	@Field(name = "order_id") 
	private String orderId;
	
	@NotNull(message = "HSN is required!")
	@Field(name = "hsn") 
	private Object hsn;
	
	@NotNull(message = "Designer is required!")
	@Field(name = "designer_id") 
	private int designerId;
	
	@NotNull(message = "Product is required!")
	@Field(name = "productId") 
	private int productId;
	
	@NotNull(message = "Product name is required!")
	@Field(name = "productName") 
	private String productName;
	
	@NotNull(message = "Product Sku is required!")
	@Field(name = "productSku") 
	private String productSku;
	
	@NotNull(message = "Size is required!")
	@Field(name = "size") 
	private String size;
	
	@NotNull(message = "Image is required!")
	@Field(name = "images") 
	private String images;
	
	@NotNull(message = "Colour is required!")
	@Field(name = "colour") 
	private String colour;
	
	@NotNull(message = "Units is required!")
	@Field(name = "units") 
	private Long units;
	
	@NotNull(message = "mrp is required!")
	@Field(name = "mrp") 
	private Long mrp;
	
	@NotNull(message = "Sales price is required!")
	@Field(name = "salesPrice") 
	private Long salesPrice;
	
	@NotNull(message = "Discount is required!")
	@Field(name = "discount") 
	private Long discount;
	
	@NotNull(message = "Tax amount is required!")
	@Field(name = "tax_amount") 
	private Long taxAmount;
	
	@Field(name = "taxType") 
	private String taxType;

	@Field(name= "order_item_status")
	private String orderItemStatus;
	
	@Field(name= "reached_central_hub")
	private String reachedCentralHub;
	
	@Field(name = "created_on") 
	private String createdOn;
	
	@Field(name = "updated_on") 
	private String updatedOn;

	public OrderSKUDetailsEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderSKUDetailsEntity(Integer id, @NotNull(message = "Username is required!") Long userId, String orderId,
			@NotNull(message = "HSN is required!") Object hsn,
			@NotNull(message = "Designer is required!") int designerId,
			@NotNull(message = "Product is required!") int productId,
			@NotNull(message = "Product name is required!") String productName,
			@NotNull(message = "Product Sku is required!") String productSku,
			@NotNull(message = "Size is required!") String size, @NotNull(message = "Image is required!") String images,
			@NotNull(message = "Colour is required!") String colour,
			@NotNull(message = "Units is required!") Long units, @NotNull(message = "mrp is required!") Long mrp,
			@NotNull(message = "Sales price is required!") Long salesPrice,
			@NotNull(message = "Discount is required!") Long discount,
			@NotNull(message = "Tax amount is required!") Long taxAmount, String taxType, String orderItemStatus,
			String reachedCentralHub, String createdOn, String updatedOn) {
		super();
		this.id = id;
		this.userId = userId;
		this.orderId = orderId;
		this.hsn = hsn;
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
	}

	@Override
	public String toString() {
		return "OrderSKUDetailsEntity [id=" + id + ", userId=" + userId + ", orderId=" + orderId + ", hsn=" + hsn
				+ ", designerId=" + designerId + ", productId=" + productId + ", productName=" + productName
				+ ", productSku=" + productSku + ", size=" + size + ", images=" + images + ", colour=" + colour
				+ ", units=" + units + ", mrp=" + mrp + ", salesPrice=" + salesPrice + ", discount=" + discount
				+ ", taxAmount=" + taxAmount + ", taxType=" + taxType + ", orderItemStatus=" + orderItemStatus
				+ ", reachedCentralHub=" + reachedCentralHub + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn
				+ "]";
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

	public Object getHsn() {
		return hsn;
	}

	public void setHsn(Object hsn) {
		this.hsn = hsn;
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

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}


	


}
