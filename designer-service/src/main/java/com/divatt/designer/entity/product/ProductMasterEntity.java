package com.divatt.designer.entity.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
@Document(collection = "tbl_products")
public class ProductMasterEntity {

	@Id
	private Integer productId;
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_products";
	@Field(name = "designer_name")
	private String designerName;
	private Integer designerId;
	//@NotEmpty(message = "Category ID Required")
	private Integer categoryId;
	//@NotEmpty(message = "SUb-Category ID Required")
	private Integer subCategoryId;
	@NotEmpty(message = "Gender Category Required")
	private String gender;
	@NotEmpty(message = "Product Name Required")
	private String productName;
	@NotEmpty(message = "Product Description Required")
	private String productDescription;
	
	private AgeEntity age;
	//@NotEmpty(message = "COD Status Required")
	private Boolean cod;
	//@NotEmpty(message = "Customization Status Required")
	private Boolean customization;
	private PurchaseEntity purchaseQuantity;
	@NotEmpty(message = "Price Type Required")
	private String priceType;
	private Float taxPercentage;
	//@NotEmpty(message = "Tax Status")
	private Boolean taxInclusive;
	private Boolean giftWrap;
	//@NotEmpty(message = "Quantity Required")
	private GiftEntity giftWrapAmount;
	//@NotEmpty(message = "Price Field Required")
	private PriceEntity price;
	//@NotEmpty(message = "Product Images Required")
	private ImagesEntity images[];
	//@NotEmpty(message = "Stock On Hand Required")
	private List<StandardSOH> standeredSOH;
	private Integer customizationSOH;
	private JSONObject extraSpecifications;
	//@NotEmpty(message = "Product Specification Required")
	private Specification specifications;
	//@NotEmpty(message = "Product Approval Required")
	private Boolean isActive;
	private Boolean isDeleted;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	private Date createdOn;
	private String createdBy;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	private Date adminStatusOn;
	private String updatedBy;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	private Date updatedOn;
	private String approvedBy;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	private Date approvedOn;
	private String SKQCode;
	private List<Object> comments = new ArrayList<>();
	 
	private String adminStatus;
	private Object hsnData;
	
	public ProductMasterEntity() {
		super();
	}
	public ProductMasterEntity(Integer productId, String designerName, Integer designerId, Integer categoryId,
			Integer subCategoryId, @NotEmpty(message = "Gender Category Required") String gender,
			@NotEmpty(message = "Product Name Required") String productName,
			@NotEmpty(message = "Product Description Required") String productDescription, AgeEntity age, Boolean cod,
			Boolean customization, PurchaseEntity purchaseQuantity,
			@NotEmpty(message = "Price Type Required") String priceType, Float taxPercentage, Boolean taxInclusive,
			Boolean giftWrap, GiftEntity giftWrapAmount, PriceEntity price, ImagesEntity[] images,
			List<StandardSOH> standeredSOH, Integer customizationSOH, JSONObject extraSpecifications,
			Specification specifications, Boolean isActive, Boolean isDeleted, Date createdOn, String createdBy,
			Date adminStatusOn, String updatedBy, Date updatedOn, String approvedBy, Date approvedOn, String sKQCode,
			List<Object> comments, String adminStatus, Object hsnData) {
		super();
		this.productId = productId;
		this.designerName = designerName;
		this.designerId = designerId;
		this.categoryId = categoryId;
		this.subCategoryId = subCategoryId;
		this.gender = gender;
		this.productName = productName;
		this.productDescription = productDescription;
		this.age = age;
		this.cod = cod;
		this.customization = customization;
		this.purchaseQuantity = purchaseQuantity;
		this.priceType = priceType;
		this.taxPercentage = taxPercentage;
		this.taxInclusive = taxInclusive;
		this.giftWrap = giftWrap;
		this.giftWrapAmount = giftWrapAmount;
		this.price = price;
		this.images = images;
		this.standeredSOH = standeredSOH;
		this.customizationSOH = customizationSOH;
		this.extraSpecifications = extraSpecifications;
		this.specifications = specifications;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.adminStatusOn = adminStatusOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
		this.approvedBy = approvedBy;
		this.approvedOn = approvedOn;
		this.SKQCode = sKQCode;
		this.comments = comments;
		this.adminStatus = adminStatus;
		this.hsnData = hsnData;
	}
	@Override
	public String toString() {
		return "ProductMasterEntity [productId=" + productId + ", designerName=" + designerName + ", designerId="
				+ designerId + ", categoryId=" + categoryId + ", subCategoryId=" + subCategoryId + ", gender=" + gender
				+ ", productName=" + productName + ", productDescription=" + productDescription + ", age=" + age
				+ ", cod=" + cod + ", customization=" + customization + ", purchaseQuantity=" + purchaseQuantity
				+ ", priceType=" + priceType + ", taxPercentage=" + taxPercentage + ", taxInclusive=" + taxInclusive
				+ ", giftWrap=" + giftWrap + ", giftWrapAmount=" + giftWrapAmount + ", price=" + price + ", images="
				+ Arrays.toString(images) + ", standeredSOH=" + standeredSOH + ", customizationSOH=" + customizationSOH
				+ ", extraSpecifications=" + extraSpecifications + ", specifications=" + specifications + ", isActive="
				+ isActive + ", isDeleted=" + isDeleted + ", createdOn=" + createdOn + ", createdBy=" + createdBy
				+ ", adminStatusOn=" + adminStatusOn + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn
				+ ", approvedBy=" + approvedBy + ", approvedOn=" + approvedOn + ", SKQCode=" + SKQCode + ", comments="
				+ comments + ", adminStatus=" + adminStatus + ", hsnData=" + hsnData + "]";
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getDesignerName() {
		return designerName;
	}
	public void setDesignerName(String designerName) {
		this.designerName = designerName;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public AgeEntity getAge() {
		return age;
	}
	public void setAge(AgeEntity age) {
		this.age = age;
	}
	public Boolean getCod() {
		return cod;
	}
	public void setCod(Boolean cod) {
		this.cod = cod;
	}
	public Boolean getCustomization() {
		return customization;
	}
	public void setCustomization(Boolean customization) {
		this.customization = customization;
	}
	public PurchaseEntity getPurchaseQuantity() {
		return purchaseQuantity;
	}
	public void setPurchaseQuantity(PurchaseEntity purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public Float getTaxPercentage() {
		return taxPercentage;
	}
	public void setTaxPercentage(Float taxPercentage) {
		this.taxPercentage = taxPercentage;
	}
	public Boolean getTaxInclusive() {
		return taxInclusive;
	}
	public void setTaxInclusive(Boolean taxInclusive) {
		this.taxInclusive = taxInclusive;
	}
	public Boolean getGiftWrap() {
		return giftWrap;
	}
	public void setGiftWrap(Boolean giftWrap) {
		this.giftWrap = giftWrap;
	}
	public GiftEntity getGiftWrapAmount() {
		return giftWrapAmount;
	}
	public void setGiftWrapAmount(GiftEntity giftWrapAmount) {
		this.giftWrapAmount = giftWrapAmount;
	}
	public PriceEntity getPrice() {
		return price;
	}
	public void setPrice(PriceEntity price) {
		this.price = price;
	}
	public ImagesEntity[] getImages() {
		return images;
	}
	public void setImages(ImagesEntity[] images) {
		this.images = images;
	}
	public List<StandardSOH> getStanderedSOH() {
		return standeredSOH;
	}
	public void setStanderedSOH(List<StandardSOH> standeredSOH) {
		this.standeredSOH = standeredSOH;
	}
	public Integer getCustomizationSOH() {
		return customizationSOH;
	}
	public void setCustomizationSOH(Integer customizationSOH) {
		this.customizationSOH = customizationSOH;
	}
	public JSONObject getExtraSpecifications() {
		return extraSpecifications;
	}
	public void setExtraSpecifications(JSONObject extraSpecifications) {
		this.extraSpecifications = extraSpecifications;
	}
	public Specification getSpecifications() {
		return specifications;
	}
	public void setSpecifications(Specification specifications) {
		this.specifications = specifications;
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
	public Date getAdminStatusOn() {
		return adminStatusOn;
	}
	public void setAdminStatusOn(Date adminStatusOn) {
		this.adminStatusOn = adminStatusOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getApprovedOn() {
		return approvedOn;
	}
	public void setApprovedOn(Date approvedOn) {
		this.approvedOn = approvedOn;
	}
	public String getSKQCode() {
		return SKQCode;
	}
	public void setSKQCode(String sKQCode) {
		SKQCode = sKQCode;
	}
	public List<Object> getComments() {
		return comments;
	}
	public void setComments(List<Object> comments) {
		this.comments = comments;
	}
	public String getAdminStatus() {
		return adminStatus;
	}
	public void setAdminStatus(String adminStatus) {
		this.adminStatus = adminStatus;
	}
	public Object getHsnData() {
		return hsnData;
	}
	public void setHsnData(Object hsnData) {
		this.hsnData = hsnData;
	}
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	
}