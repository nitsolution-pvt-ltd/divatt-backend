package com.divatt.designer.entity.product;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Document(collection = "tbl2_products")
public class ProductMasterEntity2 {
	@Id
	@Field(name = "_id")
	private Integer productId;
	@Field(name = "SKU")
	@NotEmpty(message = "Product SKU Required")
	private String sku;
	@Transient
	public static final String SEQUENCE_NAME = "tbl2_products";
	@Field(name = "designerId")
	private Integer designerId;
	@Field(name = "categoryId")
	// @NotEmpty(message = "Category ID Required")
	private Integer categoryId;
	@Field(name = "subCategoryId")
	// @NotEmpty(message = "SUb-Category ID Required")
	private Integer subCategoryId;
	@Field(name = "purchase_min_quantity")
	private Integer purchaseMinQuantity;
	@Field(name = "purchase_max_quantity")
	private Integer purchaseMaxQuantity;
	@Field(name = "hsn_code")
	private Integer hsnCode;

	@Field(name = "product_details")
	private productDetails productDetails;

	@Field(name = "design_customization_features")
	private String designCustomizationFeatures;
	@Field(name = "with_customization")
	private Boolean withCustomization;
	@Field(name = "with_design_customization")
	private Boolean withDesignCustomization;
	@Field(name = "with_gift_wrap")
	private Boolean withGiftWrap;
	@Field(name = "return_acceptable")
	private Boolean returnAcceptable;
	@Field(name = "cancel_acceptable")
	private Boolean cancelAcceptable;
	// @NotEmpty(message = "COD Status Required")
	@Field(name = "cod")
	private Boolean cod;
	@NotEmpty(message = "Price Type Required")
	@Field(name = "price_type")
	private String priceType;
	@Field(name = "color")
	@NotEmpty(message = "Price Type Required")
	private String colour;
	@Field(name = "sizes")
	private List<String> sizes;
	@Field(name = "soh")
	private Integer soh;
	@Field(name = "oos")
	private Integer oos;
	@Field(name = "notify")
	private Integer notify;
	@Field(name = "price_code")
	private String priceCode;
	@Field(name = "MRP")
	private Integer mrp;
	@Field(name = "deal")
	private Deal deal;
	@Field(name = "gift_wrap_amount")
	private Integer giftWrapAmount;
	@Field(name = "extraSpecifications")
	private ExtraSpecifications extraSpecifications;
	@Field(name = "product_weight")
	private String productWeight;
	@Field(name = "shipment_time")
	private String shipmentTime;

	@Field(name = "images")
	private ImageEntity image[];
	@Field(name = "is_active")
	private Boolean isActive;
	@Field(name = "is_deleted")
	private Boolean isDeleted;
	@Field(name = "product_stage")
	private String productStage;
	@Field(name = "product_stage_details")
	private ProductStageDetails productStageDetails;

	@Field(name = "created_on")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy/MM/dd")
	private Date createdOn;
	@Field(name = "created_by")
	private String createdBy;
	@Field(name = "updated_on")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy/MM/dd")
	private Date updatedOn;
	@Field(name = "updated_by")
	private String updatedBy;

	public ProductMasterEntity2() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductMasterEntity2(Integer productId, @NotEmpty(message = "Product SKU Required") String sku,
			Integer designerId, Integer categoryId, Integer subCategoryId, Integer purchaseMinQuantity,
			Integer purchaseMaxQuantity, Integer hsnCode,
			com.divatt.designer.entity.product.productDetails productDetails, String designCustomizationFeatures,
			Boolean withCustomization, Boolean withDesignCustomization, Boolean withGiftWrap, Boolean returnAcceptable,
			Boolean cancelAcceptable, Boolean cod, @NotEmpty(message = "Price Type Required") String priceType,
			@NotEmpty(message = "Price Type Required") String colour, List<String> sizes, Integer soh, Integer oos,
			Integer notify, String priceCode, Integer mrp, Deal deal, Integer giftWrapAmount,
			ExtraSpecifications extraSpecifications, String productWeight, String shipmentTime, ImageEntity[] image,
			Boolean isActive, Boolean isDeleted, String productStage, ProductStageDetails productStageDetails,
			Date createdOn, String createdBy, Date updatedOn, String updatedBy) {
		super();
		this.productId = productId;
		this.sku = sku;
		this.designerId = designerId;
		this.categoryId = categoryId;
		this.subCategoryId = subCategoryId;
		this.purchaseMinQuantity = purchaseMinQuantity;
		this.purchaseMaxQuantity = purchaseMaxQuantity;
		this.hsnCode = hsnCode;
		this.productDetails = productDetails;
		this.designCustomizationFeatures = designCustomizationFeatures;
		this.withCustomization = withCustomization;
		this.withDesignCustomization = withDesignCustomization;
		this.withGiftWrap = withGiftWrap;
		this.returnAcceptable = returnAcceptable;
		this.cancelAcceptable = cancelAcceptable;
		this.cod = cod;
		this.priceType = priceType;
		this.colour = colour;
		this.sizes = sizes;
		this.soh = soh;
		this.oos = oos;
		this.notify = notify;
		this.priceCode = priceCode;
		this.mrp = mrp;
		this.deal = deal;
		this.giftWrapAmount = giftWrapAmount;
		this.extraSpecifications = extraSpecifications;
		this.productWeight = productWeight;
		this.shipmentTime = shipmentTime;
		this.image = image;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.productStage = productStage;
		this.productStageDetails = productStageDetails;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getDesignerId() {
		return designerId;
	}

	public void setDesignerId(Integer designerId) {
		this.designerId = designerId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public Integer getPurchaseMinQuantity() {
		return purchaseMinQuantity;
	}

	public void setPurchaseMinQuantity(Integer purchaseMinQuantity) {
		this.purchaseMinQuantity = purchaseMinQuantity;
	}

	public Integer getPurchaseMaxQuantity() {
		return purchaseMaxQuantity;
	}

	public void setPurchaseMaxQuantity(Integer purchaseMaxQuantity) {
		this.purchaseMaxQuantity = purchaseMaxQuantity;
	}

	public Integer getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(Integer hsnCode) {
		this.hsnCode = hsnCode;
	}

	public productDetails getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(productDetails productDetails) {
		this.productDetails = productDetails;
	}

	public String getDesignCustomizationFeatures() {
		return designCustomizationFeatures;
	}

	public void setDesignCustomizationFeatures(String designCustomizationFeatures) {
		this.designCustomizationFeatures = designCustomizationFeatures;
	}

	public Boolean getWithCustomization() {
		return withCustomization;
	}

	public void setWithCustomization(Boolean withCustomization) {
		this.withCustomization = withCustomization;
	}

	public Boolean getWithDesignCustomization() {
		return withDesignCustomization;
	}

	public void setWithDesignCustomization(Boolean withDesignCustomization) {
		this.withDesignCustomization = withDesignCustomization;
	}

	public Boolean getWithGiftWrap() {
		return withGiftWrap;
	}

	public void setWithGiftWrap(Boolean withGiftWrap) {
		this.withGiftWrap = withGiftWrap;
	}

	public Boolean getReturnAcceptable() {
		return returnAcceptable;
	}

	public void setReturnAcceptable(Boolean returnAcceptable) {
		this.returnAcceptable = returnAcceptable;
	}

	public Boolean getCancelAcceptable() {
		return cancelAcceptable;
	}

	public void setCancelAcceptable(Boolean cancelAcceptable) {
		this.cancelAcceptable = cancelAcceptable;
	}

	public Boolean getCod() {
		return cod;
	}

	public void setCod(Boolean cod) {
		this.cod = cod;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public List<String> getSizes() {
		return sizes;
	}

	public void setSizes(List<String> sizes) {
		this.sizes = sizes;
	}

	public Integer getSoh() {
		return soh;
	}

	public void setSoh(Integer soh) {
		this.soh = soh;
	}

	public Integer getOos() {
		return oos;
	}

	public void setOos(Integer oos) {
		this.oos = oos;
	}

	public Integer getNotify() {
		return notify;
	}

	public void setNotify(Integer notify) {
		this.notify = notify;
	}

	public String getPriceCode() {
		return priceCode;
	}

	public void setPriceCode(String priceCode) {
		this.priceCode = priceCode;
	}

	public Integer getMrp() {
		return mrp;
	}

	public void setMrp(Integer mrp) {
		this.mrp = mrp;
	}

	public Deal getDeal() {
		return deal;
	}

	public void setDeal(Deal deal) {
		this.deal = deal;
	}

	public Integer getGiftWrapAmount() {
		return giftWrapAmount;
	}

	public void setGiftWrapAmount(Integer giftWrapAmount) {
		this.giftWrapAmount = giftWrapAmount;
	}

	public ExtraSpecifications getExtraSpecifications() {
		return extraSpecifications;
	}

	public void setExtraSpecifications(ExtraSpecifications extraSpecifications) {
		this.extraSpecifications = extraSpecifications;
	}

	public String getProductWeight() {
		return productWeight;
	}

	public void setProductWeight(String productWeight) {
		this.productWeight = productWeight;
	}

	public String getShipmentTime() {
		return shipmentTime;
	}

	public void setShipmentTime(String shipmentTime) {
		this.shipmentTime = shipmentTime;
	}

	public ImageEntity[] getImage() {
		return image;
	}

	public void setImage(ImageEntity[] image) {
		this.image = image;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getProductStage() {
		return productStage;
	}

	public void setProductStage(String productStage) {
		this.productStage = productStage;
	}

	public ProductStageDetails getProductStageDetails() {
		return productStageDetails;
	}

	public void setProductStageDetails(ProductStageDetails productStageDetails) {
		this.productStageDetails = productStageDetails;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	@Override
	public String toString() {
		return "ProductMasterEntity_2 [productId=" + productId + ", sku=" + sku + ", designerId=" + designerId
				+ ", categoryId=" + categoryId + ", subCategoryId=" + subCategoryId + ", purchaseMinQuantity="
				+ purchaseMinQuantity + ", purchaseMaxQuantity=" + purchaseMaxQuantity + ", hsnCode=" + hsnCode
				+ ", productDetails=" + productDetails + ", designCustomizationFeatures=" + designCustomizationFeatures
				+ ", withCustomization=" + withCustomization + ", withDesignCustomization=" + withDesignCustomization
				+ ", withGiftWrap=" + withGiftWrap + ", returnAcceptable=" + returnAcceptable + ", cancelAcceptable="
				+ cancelAcceptable + ", cod=" + cod + ", priceType=" + priceType + ", colour=" + colour + ", sizes="
				+ sizes + ", soh=" + soh + ", oos=" + oos + ", notify=" + notify + ", priceCode=" + priceCode + ", mrp="
				+ mrp + ", deal=" + deal + ", giftWrapAmount=" + giftWrapAmount + ", extraSpecifications="
				+ extraSpecifications + ", productWeight=" + productWeight + ", shipmentTime=" + shipmentTime
				+ ", image=" + Arrays.toString(image) + ", isActive=" + isActive + ", isDeleted=" + isDeleted
				+ ", productStage=" + productStage + ", productStageDetails=" + productStageDetails + ", createdOn="
				+ createdOn + ", createdBy=" + createdBy + ", updatedOn=" + updatedOn + ", updatedBy=" + updatedBy
				+ "]";
	}

}
