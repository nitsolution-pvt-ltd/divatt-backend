package com.divatt.user.entity.order;

import javax.validation.constraints.NotNull;

import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Document(collection = "tbl_order_sku_details")
public class OrderSKUDetailsEntity {

	@Transient
	public static final String SEQUENCE_NAME = "tbl_order_sku_details";

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

	@Field(name = "cgst")
	private Double cgst;

	@Field(name = "sgst")
	private Double sgst;

	@Field(name = "igst")
	private Double igst;

	@Field(name = "shipping_charge")
	private Double shippingCharge;

	@Field(name = "shipping_cgst")
	private Double shippingCGST;

	@Field(name = "shipping_sgst")
	private Double shippingSGST;

	@Field(name = "shipping_igst")
	private Double shippingIGST;

	@Field(name = "taxType")
	private String taxType;

	@Field(name = "order_item_status")
	private String orderItemStatus;

	@Field(name = "reached_central_hub")
	private String reachedCentralHub;

	@Field(name = "created_on")
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private String createdOn;

	@Field(name = "updated_on")
	private String updatedOn;

	private OrderStatusDetails orderStatusDetails;

	@Field(name = "designer_Customization")
	private JSONObject customObject;

	@Field(name = "customization_status")
	private Boolean customizationStatus;

	@Field(name = "giftwrap_status")
	private Boolean giftwrapStatus;

	private Boolean cancelAcceptable;

	private JSONObject giftWrapObject;

	private String userComment;

	@JsonFormat(shape = Shape.STRING, pattern = "yyyy/dd/MM HH:mm:ss")
	private String shippingDate;

	private String status;

	private HsnData hsnData;

	private JSONObject measurementObject;

	@Field(name = "gift_wrap_amount")
	private Long giftWrapAmount;

	@Field(name = "return_acceptable")
	private Boolean returnAcceptable;
	
	@Field(name = "displayName")
	private String displayName;
	
	private UserInfo userInfo;
	
	private DesignerInfo designerInfo;
	

	public OrderSKUDetailsEntity() {
		super();
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
			@NotNull(message = "Tax amount is required!") Long taxAmount, Double cgst, Double sgst, Double igst,
			Double shippingCharge, Double shippingCGST, Double shippingSGST, Double shippingIGST, String taxType,
			String orderItemStatus, String reachedCentralHub, String createdOn, String updatedOn,
			OrderStatusDetails orderStatusDetails, JSONObject customObject, Boolean customizationStatus,
			Boolean giftwrapStatus, Boolean cancelAcceptable, JSONObject giftWrapObject, String userComment,
			String shippingDate, String status, HsnData hsnData, JSONObject measurementObject, Long giftWrapAmount,
			Boolean returnAcceptable, String displayName, UserInfo userInfo, DesignerInfo designerInfo) {
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
		this.cgst = cgst;
		this.sgst = sgst;
		this.igst = igst;
		this.shippingCharge = shippingCharge;
		this.shippingCGST = shippingCGST;
		this.shippingSGST = shippingSGST;
		this.shippingIGST = shippingIGST;
		this.taxType = taxType;
		this.orderItemStatus = orderItemStatus;
		this.reachedCentralHub = reachedCentralHub;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.orderStatusDetails = orderStatusDetails;
		this.customObject = customObject;
		this.customizationStatus = customizationStatus;
		this.giftwrapStatus = giftwrapStatus;
		this.cancelAcceptable = cancelAcceptable;
		this.giftWrapObject = giftWrapObject;
		this.userComment = userComment;
		this.shippingDate = shippingDate;
		this.status = status;
		this.hsnData = hsnData;
		this.measurementObject = measurementObject;
		this.giftWrapAmount = giftWrapAmount;
		this.returnAcceptable = returnAcceptable;
		this.displayName = displayName;
		this.userInfo = userInfo;
		this.designerInfo = designerInfo;
	}

	@Override
	public String toString() {
		return "OrderSKUDetailsEntity [id=" + id + ", userId=" + userId + ", orderId=" + orderId + ", hsn=" + hsn
				+ ", designerId=" + designerId + ", productId=" + productId + ", productName=" + productName
				+ ", productSku=" + productSku + ", size=" + size + ", images=" + images + ", colour=" + colour
				+ ", units=" + units + ", mrp=" + mrp + ", salesPrice=" + salesPrice + ", discount=" + discount
				+ ", taxAmount=" + taxAmount + ", cgst=" + cgst + ", sgst=" + sgst + ", igst=" + igst
				+ ", shippingCharge=" + shippingCharge + ", shippingCGST=" + shippingCGST + ", shippingSGST="
				+ shippingSGST + ", shippingIGST=" + shippingIGST + ", taxType=" + taxType + ", orderItemStatus="
				+ orderItemStatus + ", reachedCentralHub=" + reachedCentralHub + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + ", orderStatusDetails=" + orderStatusDetails + ", customObject="
				+ customObject + ", customizationStatus=" + customizationStatus + ", giftwrapStatus=" + giftwrapStatus
				+ ", cancelAcceptable=" + cancelAcceptable + ", giftWrapObject=" + giftWrapObject + ", userComment="
				+ userComment + ", shippingDate=" + shippingDate + ", status=" + status + ", hsnData=" + hsnData
				+ ", measurementObject=" + measurementObject + ", giftWrapAmount=" + giftWrapAmount
				+ ", returnAcceptable=" + returnAcceptable + ", displayName=" + displayName + ", userInfo=" + userInfo + ", designerInfo" + designerInfo +"]";
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

	public Double getCgst() {
		return cgst;
	}

	public void setCgst(Double cgst) {
		this.cgst = cgst;
	}

	public Double getSgst() {
		return sgst;
	}

	public void setSgst(Double sgst) {
		this.sgst = sgst;
	}

	public Double getIgst() {
		return igst;
	}

	public void setIgst(Double igst) {
		this.igst = igst;
	}

	public Double getShippingCharge() {
		return shippingCharge;
	}

	public void setShippingCharge(Double shippingCharge) {
		this.shippingCharge = shippingCharge;
	}

	public Double getShippingCGST() {
		return shippingCGST;
	}

	public void setShippingCGST(Double shippingCGST) {
		this.shippingCGST = shippingCGST;
	}

	public Double getShippingSGST() {
		return shippingSGST;
	}

	public void setShippingSGST(Double shippingSGST) {
		this.shippingSGST = shippingSGST;
	}

	public Double getShippingIGST() {
		return shippingIGST;
	}

	public void setShippingIGST(Double shippingIGST) {
		this.shippingIGST = shippingIGST;
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

	public OrderStatusDetails getOrderStatusDetails() {
		return orderStatusDetails;
	}

	public void setOrderStatusDetails(OrderStatusDetails orderStatusDetails) {
		this.orderStatusDetails = orderStatusDetails;
	}

	public JSONObject getCustomObject() {
		return customObject;
	}

	public void setCustomObject(JSONObject customObject) {
		this.customObject = customObject;
	}

	public Boolean getCustomizationStatus() {
		return customizationStatus;
	}

	public void setCustomizationStatus(Boolean customizationStatus) {
		this.customizationStatus = customizationStatus;
	}

	public Boolean getGiftwrapStatus() {
		return giftwrapStatus;
	}

	public void setGiftwrapStatus(Boolean giftwrapStatus) {
		this.giftwrapStatus = giftwrapStatus;
	}

	public Boolean getCancelAcceptable() {
		return cancelAcceptable;
	}

	public void setCancelAcceptable(Boolean cancelAcceptable) {
		this.cancelAcceptable = cancelAcceptable;
	}

	public JSONObject getGiftWrapObject() {
		return giftWrapObject;
	}

	public void setGiftWrapObject(JSONObject giftWrapObject) {
		this.giftWrapObject = giftWrapObject;
	}

	public String getUserComment() {
		return userComment;
	}

	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}

	public String getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public HsnData getHsnData() {
		return hsnData;
	}

	public void setHsnData(HsnData hsnData) {
		this.hsnData = hsnData;
	}

	public JSONObject getMeasurementObject() {
		return measurementObject;
	}

	public void setMeasurementObject(JSONObject measurementObject) {
		this.measurementObject = measurementObject;
	}

	public Long getGiftWrapAmount() {
		return giftWrapAmount;
	}

	public void setGiftWrapAmount(Long giftWrapAmount) {
		this.giftWrapAmount = giftWrapAmount;
	}

	public Boolean getReturnAcceptable() {
		return returnAcceptable;
	}

	public void setReturnAcceptable(Boolean returnAcceptable) {
		this.returnAcceptable = returnAcceptable;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public DesignerInfo getDesignerInfo() {
		return designerInfo;
	}

	public void setDesignerInfo(DesignerInfo designerInfo) {
		this.designerInfo = designerInfo;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	

	
	
	
	
}
